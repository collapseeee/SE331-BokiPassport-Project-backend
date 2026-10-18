package se331.bokipassport.security.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se331.bokipassport.entity.User;
import se331.bokipassport.repository.UserRepository;
import se331.bokipassport.security.config.JwtService;
import se331.bokipassport.security.token.Token;
import se331.bokipassport.security.token.TokenRepository;
import se331.bokipassport.security.token.TokenType;
import se331.bokipassport.security.user.Role;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.security.user.UserAccountRepository;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final UserAccountRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final JsonMapper jsonMapper;

  public AuthenticationResponse register(RegisterRequest request) {
    User user = userRepository.save(User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .province(request.getProvince())
            .build());
    UserAccount userAccount = UserAccount.builder()
            .profile(user)
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(List.of(Role.ROLE_USER))
            .enabled(true)
            .build();
    var savedUser = repository.save(userAccount);
    var jwtToken = jwtService.generateToken(userAccount);
    var refreshToken = jwtService.generateRefreshToken(userAccount);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
    UserAccount userAccount = repository.findByUsername(request.getUsername())
            .orElseThrow();

    String jwtToken = jwtService.generateToken(userAccount);
    String refreshToken = jwtService.generateRefreshToken(userAccount);
//    revokeAllUserTokens(user);
    saveUserToken(userAccount, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  private void saveUserToken(UserAccount userAccount, String jwtToken) {
    Token token = Token.builder()
            .userAccount(userAccount)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserAccount userAccount) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(userAccount.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      UserAccount userAccount = this.repository.findByUsername(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, userAccount)) {
        String accessToken = jwtService.generateToken(userAccount);
        revokeAllUserTokens(userAccount);
        saveUserToken(userAccount, accessToken);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        jsonMapper.writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}

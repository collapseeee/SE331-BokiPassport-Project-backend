package se331.bokipassport.dao;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.User;
import se331.bokipassport.repository.UserRepository;

@AllArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    final UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}

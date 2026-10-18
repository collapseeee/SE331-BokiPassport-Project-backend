package se331.bokipassport.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.User;

public interface UserService {
    Page<User> findAll(Pageable pageable);
}

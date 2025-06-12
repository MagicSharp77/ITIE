package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService {
    void changePhone(String userId, String phone, String newPhone, String captcha);
    void changePassword(String userId, String password, String newPassword);
    User addUserByPhone(String phone);
    void addUser(User user);
    boolean existsByPhone(String phone);
    User selectByPhone(String phone);
}

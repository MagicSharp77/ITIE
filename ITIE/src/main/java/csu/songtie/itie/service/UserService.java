package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void changePhone(String phone, String newPhone, String captcha);
    void changePassword(String userId, String password, String newPassword);
}

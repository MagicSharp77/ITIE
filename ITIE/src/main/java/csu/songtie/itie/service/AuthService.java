package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public boolean registerByPhone(String phone);
    public String sendCode(String phone);
}

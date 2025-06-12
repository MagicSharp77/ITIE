package csu.songtie.itie.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public String registerByPhone(String phone, String captcha);
    public String loginByPhone(String phone, String captcha);
    public String loginByPassword(String phone, String password);
    public void sendCaptcha(String phone);
    public String addUserByPhone(String phone);
    public boolean existUserByPhone(String phone);
}

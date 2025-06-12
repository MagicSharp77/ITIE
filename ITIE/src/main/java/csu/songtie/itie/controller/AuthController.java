package csu.songtie.itie.controller;

import csu.songtie.itie.annotation.LogAction;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.dto.PhoneCaptchaDTO;
import csu.songtie.itie.domain.dto.PhonePasswordDTO;
import csu.songtie.itie.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/captcha")
    public CommonResponse<String> sendCaptcha(@RequestParam("phone") String phone) {
        authService.sendCaptcha(phone);
        return CommonResponse.createForSuccess(
                ResponseCode.GET_CAPTCHA_SUCCESS.getCode(),
                ResponseCode.GET_CAPTCHA_SUCCESS.getDescription());
    }

    @LogAction("用户注册")
    @PostMapping("/registrations")
    public CommonResponse<String> registerByPhone(@RequestBody PhoneCaptchaDTO request) {
        String token = authService.registerByPhone(request.getPhone(), request.getCaptcha());
        return CommonResponse.createForSuccess(
                ResponseCode.USER_REGISTER_SUCCESS.getCode(),
                ResponseCode.USER_REGISTER_SUCCESS.getDescription(),
                token);
    }

    @LogAction("用户登陆")
    @PostMapping("/sessions/by-captcha")
    public CommonResponse<String> loginByPhone(@RequestBody PhoneCaptchaDTO request) {
        String token = authService.loginByPhone(request.getPhone(), request.getCaptcha());
        return CommonResponse.createForSuccess(
                ResponseCode.USER_LOGIN_SUCCESS.getCode(),
                ResponseCode.USER_LOGIN_SUCCESS.getDescription(),
                token);
    }

    @LogAction("用户登陆")
    @PostMapping("/sessions/by-password")
    public CommonResponse<String> loginByPassword(@RequestBody PhonePasswordDTO request) {
        String token = authService.loginByPassword(request.getPhone(), request.getPassword());
        return CommonResponse.createForSuccess(
                ResponseCode.USER_LOGIN_SUCCESS.getCode(),
                ResponseCode.USER_LOGIN_SUCCESS.getDescription(),
                token);
    }

    @LogAction("用户登出")
    @DeleteMapping("/sessions")
    public CommonResponse<String> logout(HttpServletRequest request) {
        authService.logout(request);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_LOGOUT_SUCCESS.getCode(),
                ResponseCode.USER_LOGOUT_SUCCESS.getDescription());
    }

    @GetMapping("/phone-availability")
    public CommonResponse<Boolean> checkPhone(@RequestParam("phone") String phone) {
        boolean exist = authService.existUserByPhone(phone);
        return CommonResponse.createForSuccess(
                ResponseCode.OPERATION_SUCCESS.getCode(),
                ResponseCode.OPERATION_SUCCESS.getDescription(),
                exist);
    }
}

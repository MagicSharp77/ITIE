package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/sendCaptcha")
    public CommonResponse<String> sendCaptcha(@RequestParam("phone") String phone) {
        authService.sendCaptcha(phone);
        return CommonResponse.createForSuccess(
                ResponseCode.GET_CAPTCHA_SUCCESS.getCode(),
                ResponseCode.GET_CAPTCHA_SUCCESS.getDescription());
    }

    @GetMapping("/registerByPhone")
    public CommonResponse<String> registerByPhone(@RequestParam("phone") String phone, @RequestParam("captcha") String captcha) {
        String userId = authService.registerByPhone(phone, captcha);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_REGISTER_SUCCESS.getCode(),
                ResponseCode.USER_REGISTER_SUCCESS.getDescription(),
                userId);
    }

    @GetMapping("/loginByPhone")
    public CommonResponse<String> loginByPhone(@RequestParam("phone") String phone, @RequestParam("captcha") String captcha) {
        String userId = authService.loginByPhone(phone, captcha);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_LOGIN_SUCCESS.getCode(),
                ResponseCode.USER_LOGIN_SUCCESS.getDescription(),
                userId);
    }

    @GetMapping("/loginByPassword")
    public CommonResponse<String> loginByPassword(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        String userId = authService.loginByPassword(phone, password);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_LOGIN_SUCCESS.getCode(),
                ResponseCode.USER_LOGIN_SUCCESS.getDescription(),
                userId);
    }

    @GetMapping("/checkPhone")
    public CommonResponse<Boolean> checkPhone(@RequestParam("phone") String phone) {
        boolean exist = authService.existUserByPhone(phone);
        return CommonResponse.createForSuccess(
                ResponseCode.OPERATION_SUCCESS.getCode(),
                ResponseCode.OPERATION_SUCCESS.getDescription(),
                exist);
    }
}

package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("auth/sendCaptcha")
    public CommonResponse<String> sendCaptcha(@RequestParam("phone") String phone) {
        // 检查验证码是否存在
        String captcha = redisTemplate.opsForValue().get(phone);
        if(captcha != null && !captcha.isEmpty()) return CommonResponse.createForError(1,"验证码已发送");// 验证码已发送

        captcha = authService.sendCode(phone);
        return switch (captcha) {
            case "1" -> CommonResponse.createForError(1,"手机号已被注册");// 手机号已被注册
            case "0" -> CommonResponse.createForError(0,"发送失败");// 发送失败
            default -> {
                // 发送成功，将验证码存入redis,设置过期时间为2分钟
                redisTemplate.opsForValue().set(phone, captcha, 2, TimeUnit.MINUTES);
                yield CommonResponse.createForSuccess(ResponseCode.GET_CAPTCHA_SUCCESS.getCode(), ResponseCode.GET_CAPTCHA_SUCCESS.getDescription());
            }
        };
    }

    @PostMapping("auth/registerByPhone")
    public CommonResponse<String> registerByPhone(@RequestParam("phone") String phone, @RequestParam("captcha") String captcha) {
        //判断验证码是否正确
        String Captcha = redisTemplate.opsForValue().get(phone);
        if (Captcha == null || Captcha.isEmpty()) return CommonResponse.createForError(1,"验证码未发送");// 验证码未发送
        if (!Captcha.equals(captcha)) return CommonResponse.createForError(1,"验证码错误");// 验证码错误

        // 验证通过，删除验证码
        redisTemplate.delete(phone);
        authService.registerByPhone(phone);
        return null;
    }
}

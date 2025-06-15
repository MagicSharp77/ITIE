package csu.songtie.itie.service.Impl;

import cn.hutool.core.util.RandomUtil;
import com.auth0.jwt.interfaces.Claim;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.User;
import csu.songtie.itie.exception.BusinessException;
import csu.songtie.itie.service.*;
import csu.songtie.itie.util.JwtUtil;
import csu.songtie.itie.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    final UserService userService;
    final UserLogService userLogService;
    final RedisTemplate<String, String> redisTemplate;
    final JwtUtil jwtUtil;
    final SmsService smsService;

    @Autowired
    public AuthServiceImpl(UserServiceImpl userService, UserLogServiceImpl userLogService, RedisTemplate<String, String> redisTemplate, JwtUtil jwtUtil, SmsService smsService) {
        this.userService = userService;
        this.userLogService = userLogService;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
        this.smsService = smsService;
    }

    @Override
    public String registerByPhone(String phone, String captcha) {
        verifyCaptcha(phone, captcha);

        return addUserByPhone(phone);
    }

    @Override
    public String loginByPhone(String phone, String captcha) {
        verifyCaptcha(phone, captcha);

        if (userService.existsByPhone(phone)){
            // 账户已注册，直接登录
            User user = userService.selectByPhone(phone);
            return jwtUtil.generateToken(user);
        } else {
            // 账户未注册，注册后登录
            return addUserByPhone(phone);
        }
    }

    @Override
    public String loginByPassword(String phone, String password) {
        User user = userService.selectByPhone(phone);
        if (user == null)
            throw new BusinessException(ResponseCode.PHONE_NOT_REGISTERED);
        // 判断密码是否正确
        if (!PasswordUtil.matches(password, user.getPasswordHash()))
            throw new BusinessException(ResponseCode.PASSWORD_INCORRECT);
        return jwtUtil.generateToken(user);
    }

    @Override
    public void sendCaptcha(String phone) {
        // 检查验证码是否存在
        String oldCaptcha = redisTemplate.opsForValue().get(phone);
        if (oldCaptcha != null && !oldCaptcha.isEmpty())
            throw new BusinessException(ResponseCode.CAPTCHA_HAS_SENT);

        String captcha = RandomUtil.randomNumbers(6).toString();
        Map<String, Object> param = Map.of("code", captcha);
        // 为节约短信发送次数，暂时不发送短信
        System.out.println(captcha);
        // smsService.send(param, phone);

        // 发送成功，将验证码存入redis,设置过期时间为2分钟
        redisTemplate.opsForValue().set(phone, captcha, 2, TimeUnit.MINUTES);
    }

    @Override
    public void verifyCaptcha(String phone, String captcha) {
        String redisCaptcha = redisTemplate.opsForValue().get(phone);
        if (redisCaptcha == null || redisCaptcha.isEmpty()) {
            throw new BusinessException(ResponseCode.CAPTCHA_EXPIRED);
        }
        if (!redisCaptcha.equals(captcha)) {
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        }
        // 验证通过，立即删除验证码
        redisTemplate.delete(phone);
    }

    @Override
    @Transactional
    public String addUserByPhone(String phone) {
        return jwtUtil.generateToken(userService.addUserByPhone(phone));
    }

    @Override
    public boolean existUserByPhone(String phone) {
        return userService.existsByPhone(phone);
    }

    @Override
    public void logout(HttpServletRequest request) {
        // 从请求头中获取令牌
        final String token = request.getHeader("Authorization");
        // 如果令牌不存在，直接返回
        if (token == null) {
            return;
        }
        // 获取令牌的过期时间
        Map<String, Claim> tokenData = jwtUtil.verifyToken(token);
        Date expirationDate = tokenData.get("exp").asDate();
        long remainingTime  = expirationDate.getTime() - System.currentTimeMillis();

        if (remainingTime > 0) {
            // 令牌未过期，将其添加到 Redis 黑名单中
            redisTemplate.opsForValue().set(token, "blacklist", remainingTime, TimeUnit.MILLISECONDS);
        }
    }
}

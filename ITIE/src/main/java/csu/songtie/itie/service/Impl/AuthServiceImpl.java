package csu.songtie.itie.service.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.User;
import csu.songtie.itie.domain.entity.UserInfo;
import csu.songtie.itie.exception.BusinessException;
import csu.songtie.itie.mapper.UserInfoMapper;
import csu.songtie.itie.mapper.UserMapper;
import csu.songtie.itie.service.AuthService;
import csu.songtie.itie.service.SmsService;
import csu.songtie.itie.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SmsService smsService;

    @Override
    public String registerByPhone(String phone, String captcha) {
        //判断验证码是否正确
        String Captcha = redisTemplate.opsForValue().get(phone);
        if (Captcha == null || Captcha.isEmpty())
            throw new BusinessException(ResponseCode.CAPTCHA_EXPIRED);
        if (!Captcha.equals(captcha))
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        // 验证通过，删除验证码
        redisTemplate.delete(phone);

        return addUserByPhone(phone);
    }

    @Override
    public String loginByPhone(String phone, String captcha) {
        //判断验证码是否正确
        String Captcha = redisTemplate.opsForValue().get(phone);
        if (Captcha == null || Captcha.isEmpty())
            throw new BusinessException(ResponseCode.CAPTCHA_EXPIRED);
        if (!Captcha.equals(captcha))
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        // 验证通过，删除验证码
        redisTemplate.delete(phone);

        if (userMapper.existsByPhone(phone)){
            //账户已注册，直接登录
            return userMapper.selectUserIdByPhone(phone);
        } else {
            //账户未注册，注册后登录
            return addUserByPhone(phone);
        }
    }

    @Override
    public String loginByPassword(String phone, String password) {
        User user = userMapper.selectByPhone(phone);
        if (user == null)
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);
        //判断密码是否正确
        if (!PasswordUtil.matches(password, user.getPasswordHash()))
            throw new BusinessException(ResponseCode.PASSWORD_INCORRECT);
        return userMapper.selectUserIdByPhone(phone);
    }

    @Override
    public void sendCaptcha(String phone) {
        // 检查验证码是否存在
        String oldCaptcha = redisTemplate.opsForValue().get(phone);
        if (oldCaptcha != null && !oldCaptcha.isEmpty())
            throw new BusinessException(ResponseCode.CAPTCHA_HAS_SENT);

        String captcha = RandomUtil.randomNumbers(6).toString();
        Map<String, Object> param = Map.of("code", captcha);
        boolean result = true;
        System.out.println(captcha);
        //boolean result = smsService.send(param, phone);
        if (result) {
            // 发送成功，将验证码存入redis,设置过期时间为2分钟
            redisTemplate.opsForValue().set(phone, captcha, 2, TimeUnit.MINUTES);
        } else {
            // 发送失败，抛出异常
            throw new BusinessException(ResponseCode.CAPTCHA_SEND_FAILED);
        }
    }

    @Override
    public String addUserByPhone(String phone) {
        User user = new User();
        UserInfo userInfo = new UserInfo();
        //设置初始用户
        String userId = IdUtil.simpleUUID();// 使用UUID
        user.setUserId(userId);
        user.setPhone(phone);
        user.setRole(1);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        //设置初始密码,初始密码为itie加号码后8位
        String encoded = PasswordUtil.encode("itie" + phone.substring(phone.length() - 8));
        user.setPasswordHash(encoded);

        //设置初始用户信息
        userInfo.setUserId(userId);
        userInfo.setUsername("用户" + userId.substring(0, 6));// 设置默认用户名
        userInfo.setAvatarUrl("default_avatar.png");// 设置默认头像
        userInfo.setAllowEmailNotify(true);
        userInfo.setAllowSMSNotify(true);
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdatedAt(LocalDateTime.now());

        //插入用户和用户信息
        userMapper.insert(user);
        userInfoMapper.insert(userInfo);

        return userId;
    }

    @Override
    public boolean existUserByPhone(String phone) {
        return userMapper.existsByPhone(phone);
    }
}

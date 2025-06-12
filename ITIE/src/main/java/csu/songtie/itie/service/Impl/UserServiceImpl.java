package csu.songtie.itie.service.Impl;

import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.User;
import csu.songtie.itie.exception.BusinessException;
import csu.songtie.itie.mapper.UserInfoMapper;
import csu.songtie.itie.mapper.UserLogMapper;
import csu.songtie.itie.mapper.UserMapper;
import csu.songtie.itie.mapper.WishListMapper;
import csu.songtie.itie.service.UserService;
import csu.songtie.itie.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserLogMapper userLogMapper;
    @Autowired
    private WishListMapper wishListMapper;

    @Override
    public void changePhone(String phone, String newPhone, String captcha) {
        //判断验证码是否正确
        String Captcha = redisTemplate.opsForValue().get(newPhone);
        if (Captcha == null || Captcha.isEmpty())
            throw new BusinessException(ResponseCode.CAPTCHA_EXPIRED);
        if (!Captcha.equals(captcha))
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        // 验证通过，删除验证码
        redisTemplate.delete(phone);

        //更新数据库
        User user = userMapper.selectByPhone(phone);
        user.setPhone(newPhone);
        userMapper.updateById(user);
    }

    @Override
    public void changePassword(String userId, String password, String newPassword) {
        User user = userMapper.selectByUserId(userId);
        if (user == null)
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);
        if (!PasswordUtil.matches(password, user.getPasswordHash()))
            throw new BusinessException(ResponseCode.PASSWORD_INCORRECT);
        String encoded = PasswordUtil.encode(newPassword);
        user.setPasswordHash(encoded);
        userMapper.updateById(user);
    }

}

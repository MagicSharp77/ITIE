package csu.songtie.itie.service.Impl;

import cn.hutool.core.util.IdUtil;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.User;
import csu.songtie.itie.domain.entity.UserInfo;
import csu.songtie.itie.domain.entity.UserLog;
import csu.songtie.itie.exception.BusinessException;
import csu.songtie.itie.mapper.UserInfoMapper;
import csu.songtie.itie.mapper.UserLogMapper;
import csu.songtie.itie.mapper.UserMapper;
import csu.songtie.itie.service.UserService;
import csu.songtie.itie.util.IPUtil;
import csu.songtie.itie.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    final UserMapper userMapper;
    final RedisTemplate<String, String> redisTemplate;
    final UserLogMapper userLogMapper;
    final UserInfoMapper userInfoMapper;

    // 用于设置初始用户
    final static int normalUser = 1;
    final static String defaultAvatar = "default_avatar.png";


    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisTemplate<String, String> redisTemplate, UserLogMapper userLogMapper, UserInfoMapper userInfoMapper) {
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
        this.userLogMapper = userLogMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    @Transactional
    public void changePhone(String userId, String phone, String newPhone, String captcha) {
        // 判断手机号与id是否匹配
        User user = userMapper.selectByUserId(userId);
        if (user == null)
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);
        if (!user.getPhone().equals(phone))
            throw new BusinessException(ResponseCode.PHONE_NOT_MATCH);

        // 判断验证码是否正确
        String Captcha = redisTemplate.opsForValue().get(newPhone);
        if (Captcha == null || Captcha.isEmpty())
            throw new BusinessException(ResponseCode.CAPTCHA_EXPIRED);
        if (!Captcha.equals(captcha))
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        // 验证通过，删除验证码
        redisTemplate.delete(newPhone);

        //更新数据库
        user.setPhone(newPhone);
        userMapper.updateById(user);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public User addUserByPhone(String phone) {
        User user = new User();
        UserInfo userInfo = new UserInfo();
        // 设置初始用户
        String userId = IdUtil.simpleUUID();// 使用UUID
        user.setUserId(userId);
        user.setPhone(phone);
        user.setRole(normalUser);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 设置初始密码,初始密码为itie加号码后8位
        String encoded = PasswordUtil.encode("itie" + phone.substring(phone.length() - 8));
        user.setPasswordHash(encoded);

        // 设置初始用户信息
        userInfo.setUserId(userId);
        userInfo.setUsername("用户" + userId.substring(0, 6));// 设置默认用户名
        userInfo.setAvatarUrl(defaultAvatar);// 设置默认头像
        userInfo.setAllowEmailNotify(true);
        userInfo.setAllowSMSNotify(true);
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdatedAt(LocalDateTime.now());

        // 记录用户注册日志
        UserLog userLog = new UserLog();
        userLog.setUserId(userId);
        userLog.setAction("用户注册");
        userLog.setCreatedAt(LocalDateTime.now());
        userLog.setUpdatedAt(LocalDateTime.now());
        // 获取IP
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            userLog.setIp(IPUtil.getIp(request));
        } else {
            userLog.setIp("Unknown");
        }
        userLog.setLocation("暂未实现");

        userLogMapper.insert(userLog);

        // 插入用户和用户信息
        userMapper.insert(user);
        userInfoMapper.insert(userInfo);

        return user;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userMapper.existsByPhone(phone);
    }

    @Override
    public User selectByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }
}

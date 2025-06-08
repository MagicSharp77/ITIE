package csu.songtie.itie.service.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import csu.songtie.itie.domain.entity.User;
import csu.songtie.itie.domain.entity.UserInfo;
import csu.songtie.itie.mapper.UserInfoMapper;
import csu.songtie.itie.mapper.UserMapper;
import csu.songtie.itie.service.AuthService;
import csu.songtie.itie.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SmsService smsService;

    @Override
    public boolean registerByPhone(String phone) {
        User user = new User();
        UserInfo userInfo = new UserInfo();
        //设置初始用户
        String userId = IdUtil.simpleUUID();// 使用UUID
        user.setUserId(userId);
        user.setPhone(phone);
        user.setRole(1);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        //设置初始用户信息
        userInfo.setUserId(userId);
        userInfo.setUsername("用户" + userId.substring(0, 6));// 设置默认用户名
        userInfo.setAvatarUrl("default_avatar.png");// 设置默认头像
        userInfo.setAllowEmailNotify(true);
        userInfo.setAllowSMSNotify(true);
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdatedAt(LocalDateTime.now());

        return userMapper.insert(user) > 0 && userInfoMapper.insert(userInfo) > 0;
    }

    @Override
    public String sendCode(String phone) {
        if (userMapper.existsByPhone(phone))
            return "1";// 手机号已被注册
        String code = RandomUtil.randomNumbers(6).toString();
        Map<String, Object> param = Map.of("code", code);
        boolean result = true;
        System.out.println(code);
        //boolean result = smsService.send(param, phone);
        if (result)
            return code;
        else
            return "0";// 发送失败
    }
}

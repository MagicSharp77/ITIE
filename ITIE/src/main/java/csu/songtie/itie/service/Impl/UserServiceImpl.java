package csu.songtie.itie.service.Impl;

import csu.songtie.itie.domain.entity.User;
import csu.songtie.itie.mapper.UserInfoMapper;
import csu.songtie.itie.mapper.UserLogMapper;
import csu.songtie.itie.mapper.UserMapper;
import csu.songtie.itie.mapper.WishListMapper;
import csu.songtie.itie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserLogMapper userLogMapper;
    @Autowired
    private WishListMapper wishListMapper;
}

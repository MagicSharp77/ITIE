package csu.songtie.itie.service.Impl;

import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.UserInfo;
import csu.songtie.itie.exception.BusinessException;
import csu.songtie.itie.mapper.UserInfoMapper;
import csu.songtie.itie.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    final UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if (userInfo == null)
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);
        return userInfo;
    }

    @Override
    @Transactional
    public void updateUserInfo(String userId, UserInfo newUserInfo) {
        // 取出用户信息
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if (userInfo == null)
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);

        // 更新用户信息
        userInfo.setUsername(newUserInfo.getUsername());
        userInfo.setLastName(newUserInfo.getLastName());
        userInfo.setFirstName(newUserInfo.getFirstName());
        userInfo.setMajor(newUserInfo.getMajor());
        userInfo.setAllowEmailNotify(newUserInfo.getAllowEmailNotify());
        userInfo.setAllowSMSNotify(newUserInfo.getAllowSMSNotify());
        userInfo.setAvatarUrl(newUserInfo.getAvatarUrl());

        // 保存用户信息
        int rows = userInfoMapper.updateById(userInfo);
    }

    @Override
    @Transactional
    public void addUserInfo(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }
}

package csu.songtie.itie.service.Impl;

import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.UserInfo;
import csu.songtie.itie.exception.BusinessException;
import csu.songtie.itie.mapper.UserInfoMapper;
import csu.songtie.itie.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfo(String userId) {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if (userInfo == null)
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);
        return userInfo;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        int result = userInfoMapper.updateById(userInfo);
        if (result == 0)
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);
    }
}

package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    public UserInfo getUserInfo(String userId);
    public void updateUserInfo(UserInfo userInfo);
}

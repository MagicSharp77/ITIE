package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserInfoService {
    public UserInfo getUserInfo(String userId);
    public void updateUserInfo(String userId, UserInfo userInfo);
    void addUserInfo(UserInfo userInfo);
}

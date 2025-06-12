package csu.songtie.itie.service.Impl;

import csu.songtie.itie.domain.entity.UserLog;
import csu.songtie.itie.mapper.UserLogMapper;
import csu.songtie.itie.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public void saveUserLog(UserLog userLog) {
        userLogMapper.insert(userLog);
    }
}

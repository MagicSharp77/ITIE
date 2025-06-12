package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.UserLog;
import org.springframework.stereotype.Service;

@Service
public interface UserLogService {
    public void saveUserLog(UserLog userLog);
}

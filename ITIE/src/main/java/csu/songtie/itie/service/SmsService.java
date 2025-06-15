package csu.songtie.itie.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SmsService {
    public void send(Map<String, Object> param, String phone);
}

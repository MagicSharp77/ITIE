package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.UserInfo;
import csu.songtie.itie.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/")
    public CommonResponse<UserInfo> getProfile(@RequestParam("userId") String userId) {
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        return CommonResponse.createForSuccess(
                ResponseCode.GET_USER_INFO_SUCCESS.getCode(),
                ResponseCode.GET_USER_INFO_SUCCESS.getDescription(),
                userInfo);
    }

    @PutMapping("/")
    public CommonResponse<String> updateProfile(@RequestBody UserInfo userInfo) {
        userInfoService.updateUserInfo(userInfo);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_INFO_UPDATE_SUCCESS.getCode(),
                ResponseCode.USER_INFO_UPDATE_SUCCESS.getDescription(),
                "");
    }
}

package csu.songtie.itie.controller;

import csu.songtie.itie.annotation.LogAction;
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

    @LogAction("查看用户信息")
    @GetMapping
    public CommonResponse<UserInfo> getProfile(@RequestAttribute("userId") String userId) {
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        return CommonResponse.createForSuccess(
                ResponseCode.GET_USER_INFO_SUCCESS.getCode(),
                ResponseCode.GET_USER_INFO_SUCCESS.getDescription(),
                userInfo);
    }

    @LogAction("更新用户信息")
    @PutMapping
    public CommonResponse<String> updateProfile(@RequestAttribute("userId") String userId, @RequestBody UserInfo userInfo) {
        userInfoService.updateUserInfo(userId, userInfo);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_INFO_UPDATE_SUCCESS.getCode(),
                ResponseCode.USER_INFO_UPDATE_SUCCESS.getDescription());
    }
}

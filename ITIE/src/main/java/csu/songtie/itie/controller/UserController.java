package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/Phone")
    public CommonResponse<String> changePhone(@RequestParam("phone") String phone,
                                              @RequestParam("newPhone") String newPhone,
                                              @RequestParam("captcha") String captcha) {
        userService.changePhone(phone, newPhone, captcha);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_PHONE_CHANGE_SUCCESS.getCode(),
                ResponseCode.USER_PHONE_CHANGE_SUCCESS.getDescription());
    }

    @PutMapping("/Password")
    public CommonResponse<String> changePassword(@RequestParam("userId") String userId,
                                                 @RequestParam("phone") String phone,
                                                 @RequestParam("newPassword") String newPassword) {
        userService.changePassword(userId, phone, newPassword);
        return CommonResponse.createForSuccess(
                ResponseCode.USER_PASSWORD_CHANGE_SUCCESS.getCode(),
                ResponseCode.USER_PASSWORD_CHANGE_SUCCESS.getDescription());
    }
}

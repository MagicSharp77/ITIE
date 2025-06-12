package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.dto.ChangePhoneDTO;
import csu.songtie.itie.domain.dto.ChangePasswordDTO;
import csu.songtie.itie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/phone")
    public CommonResponse<String> changePhone(@RequestAttribute("userId") String userId,
                                              @RequestBody ChangePhoneDTO request) {
        userService.changePhone(userId, request.getPhone(), request.getNewPhone(), request.getCaptcha());
        return CommonResponse.createForSuccess(
                ResponseCode.USER_PHONE_CHANGE_SUCCESS.getCode(),
                ResponseCode.USER_PHONE_CHANGE_SUCCESS.getDescription());
    }

    @PutMapping("/password")
    public CommonResponse<String> changePassword(@RequestAttribute("userId") String userId,
                                                 @RequestBody() ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(userId, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return CommonResponse.createForSuccess(
                ResponseCode.USER_PASSWORD_CHANGE_SUCCESS.getCode(),
                ResponseCode.USER_PASSWORD_CHANGE_SUCCESS.getDescription());
    }
}

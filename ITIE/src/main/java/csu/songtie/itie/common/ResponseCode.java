package csu.songtie.itie.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 成功码为正值，从 1000 开始
    // --- 10xx: 用户业务成功状态码 (Business Success Codes) ---
    OPERATION_SUCCESS(1000, "操作成功"), // 通用操作成功
    // --- 101x: 用户认证业务成功状态码 (Business Success Codes) ---
    USER_REGISTER_SUCCESS(1001, "用户注册成功"),
    USER_LOGIN_SUCCESS(1002, "用户登录成功"),
    USER_LOGOUT_SUCCESS(1003, "用户登出成功"),
    GET_CAPTCHA_SUCCESS(1004, "获取验证码成功"),
    USER_PHONE_CHANGE_SUCCESS(1005, "用户手机号修改成功"),
    // --- 102x: 用户信息业务成功状态码 (Business Success Codes) ---
    USER_INFO_UPDATE_SUCCESS(1021, "用户信息更新成功"),
    GET_USER_INFO_SUCCESS(1023, "获取用户信息成功"),
    USER_PASSWORD_CHANGE_SUCCESS(1024, "用户密码修改成功"),
    // --- 103x: 用户日志业务成功状态码 (Business Success Codes) ---
    // --- 104x: 用户愿望单业务成功状态码 (Business Success Codes) ---

    // --- 20xx: 用户业务失败状态码 (Business Failure Codes) ---
    INVALID_PARAMETER(2000, "请求参数无效"),     // 通用客户端错误
    USER_NOT_FOUND(2001, "找不到用户"),
    // --- 201x: 用户认证业务失败状态码 ---
    CAPTCHA_HAS_SENT(2010, "验证码已发送"),
    CAPTCHA_ERROR(2011, "验证码错误"),
    CAPTCHA_EXPIRED(2012, "验证码已过期"),
    PASSWORD_INCORRECT(2013, "密码错误"),
    // --- 202x: 用户信息业务失败状态码 ---


    // --- 30xx: 用户业务异常状态码 (Business Exception Codes) ---
    INTERNAL_SERVER_ERROR(3000, "服务器内部错误"),  // 通用服务器错误
    // --- 301x: 用户认证业务异常状态码 (Business Exception Codes) ---
    CAPTCHA_SEND_FAILED(3000, "验证码发送失败"),

    ;


    // 码为正值，从 1000 开始


    private final int code;
    private final String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

}

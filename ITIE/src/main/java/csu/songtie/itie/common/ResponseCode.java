package csu.songtie.itie.common;

import lombok.Getter;

@Getter
public enum ResponseCode {
    // 成功码为正值，从 1000 开始

    SUCCESS(1,"成功"),

    // --- 10xx: 用户业务成功状态码 (Business Success Codes) ---
    OPERATION_SUCCESS(1000, "操作成功"), // 通用操作成功
    USER_REGISTER_SUCCESS(1001, "用户注册成功"),
    USER_LOGIN_SUCCESS(1002, "用户登录成功"),
    USER_LOGOUT_SUCCESS(1003, "用户登出成功"),
    USER_INFO_UPDATE_SUCCESS(1004, "用户信息更新成功"),
    GET_CAPTCHA_SUCCESS(1005, "获取验证码成功"),
    GET_USER_INFO_SUCCESS(1006, "获取用户信息成功"),
    USER_PASSWORD_CHANGE_SUCCESS(1007, "用户密码修改成功"),
    OLD_PASSWORD_CORRECT(1008, "原密码正确"), // 注意：这个通常是验证结果，不应是最终的"成功"状态
    // 如果"原密码正确"只是中间校验步骤，通常会在接口最终成功时返回 USER_PASSWORD_CHANGE_SUCCESS
    // 否则，它更像是一个提示信息，而非独立的成功状态码。这里先保留，但需注意其使用场景。

    // --- 11xx:课程业务成功状态码
    CATEGORY_VO_FETCH_SUCCESS(1100, "获取分类信息成功"),
    COURSE_VO_FETCH_SUCCESS(1110,"获取课程信息成功"),
    CHAPTER_VO_FETCH_SUCCESS(1120, "获取章节信息成功"),
    LESSON_VO_FETCH_SUCCESS(1130, "获取课信息成功"),

    // --- 12xx:订单业务成功状态码
    ORDER_GET_SUCCESS(1200,"获取订单信息成功"),
    ORDER_LIST_GET_SUCCESS(1201,"获取订单列表信息成功"),
    ORDER_CREATE_SUCCESS(1202,"订单创建成功"),
    ORDER_PAY_SUCCESS(1203,"订单支付成功"),


    // 错误码从 2000 开始，命名规则与成功码相同

    ERROR(2,"错误"),

    // --- 22xx:订单业务失败状态码
    ORDER_EXIST_ERROR(2200,"订单号错误"),
    ORDER_LIST_EXIST_ERROR(2201,"该用户没有购买历史"),
    ORDER_LINEITEM_EMPTY_ERROR(2202,"订单内容不能为空"),

    // 支付相关错误码 (2000-2099)
    PAYMENT_CREATE_FAILED(2000, "创建支付订单失败"),
    PAYMENT_NOTIFY_FAILED(2001, "处理支付回调通知失败"),
    PAYMENT_QUERY_FAILED(2002, "查询支付订单状态失败"),
    PAYMENT_CLOSE_FAILED(2003, "关闭支付订单失败"),
    PAYMENT_AMOUNT_INVALID(2004, "支付金额无效"),
    PAYMENT_ORDER_NOT_FOUND(2005, "支付订单不存在"),
    PAYMENT_SIGNATURE_INVALID(2006, "支付签名验证失败"),
    PAYMENT_ALREADY_PAID(2007, "订单已支付"),
    PAYMENT_ALREADY_CLOSED(2008, "订单已关闭");

    private final int code;
    private final String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

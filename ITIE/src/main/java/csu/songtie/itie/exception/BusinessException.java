package csu.songtie.itie.exception;

import csu.songtie.itie.common.ResponseCode;
import lombok.Getter;

/**
 * 自定义业务异常类
 * 用于在服务层抛出可预知的业务逻辑错误
 * 继承RuntimeException实现非受检异常
 */
@Getter
public class BusinessException extends RuntimeException{
    /**
     * 业务错误码
     * 对应ResponseCode中预定义的错误代码
     */
    private final int errorCode;

    /**
     * 通过响应码枚举构造异常
     * @param responseCode 预定义的响应码枚举，包含错误码和描述信息
     */
    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.errorCode = responseCode.getCode();
    }
}

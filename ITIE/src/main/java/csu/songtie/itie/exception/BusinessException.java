package csu.songtie.itie.exception;

import csu.songtie.itie.common.ResponseCode;
import lombok.Data;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int errorCode;
    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.errorCode = responseCode.getCode();
    }
}

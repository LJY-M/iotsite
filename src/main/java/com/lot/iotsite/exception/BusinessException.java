package com.lot.iotsite.exception;


import com.lot.iotsite.constant.ResultCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private ResultCode resultCode;

    public BusinessException(ResultCode code) {
       this.resultCode=code;
    }
}

package com.lot.iotsite.handler;

import com.lot.iotsite.constant.ResultCode;
import com.lot.iotsite.exception.BusinessException;
import com.lot.iotsite.utils.Result;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExpectionHandler {
    private Integer otherErrorCode=5001;
    private Integer missRequestParamCode=5002;
    private Integer illegalArgumentCode=5003;

    @ExceptionHandler(Exception.class)
    public Result errorHandler(Exception ex) {
        ex.printStackTrace();
        return Result.failure(otherErrorCode,ex.getMessage());
    }

    @ExceptionHandler(Error.class)
    public Result SystemErrorHandler(Exception ex) {
        ex.printStackTrace();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        e.printStackTrace();
        return Result.failure(e.getResultCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result ArgumentHandler(Exception ex) {
        ex.printStackTrace();
        return Result.failure(illegalArgumentCode,ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result RequestParameterHandler(Exception ex) {
        ex.printStackTrace();
        return Result.failure(missRequestParamCode,ex.getMessage());
    }
}

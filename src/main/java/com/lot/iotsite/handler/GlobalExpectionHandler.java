package com.lot.iotsite.handler;

import com.lot.iotsite.constant.ResultCode;
import com.lot.iotsite.exception.BusinessException;
import com.lot.iotsite.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
@Slf4j
@RestControllerAdvice
public class GlobalExpectionHandler {
    private Integer otherErrorCode=5001;
    private Integer missRequestParamCode=5002;
    private Integer illegalArgumentCode=5003;
    private Integer methodArgumentNotValid=5004;

    @ExceptionHandler(Exception.class)
    public Result errorHandler(Exception ex) {
        log.error(ex.getMessage());
        return Result.failure(otherErrorCode,ex.getMessage());
    }

    @ExceptionHandler(Error.class)
    public Result SystemErrorHandler(Exception ex) {
        log.error(ex.getMessage());
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.error(e.getMessage());
        return Result.failure(e.getResultCode());
    }



    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result RequestParameterHandler(Exception ex) {
        log.error(ex.getMessage());
        return Result.failure(missRequestParamCode,ex.getMessage());
    }

    //
    /**
     *  捕捉shiro的异常:
     *        登录不成功
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return Result.failure(ResultCode.USER_LOGIN_FAIL);
    }

    /**
     * 处理Assert的异常
     */
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) throws IOException {
        log.error("Assert断言异常:-------------->{}",e.getMessage());
        return Result.failure(illegalArgumentCode,e.getMessage());
    }

    /**
     * @Validated 校验错误异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) throws IOException {
        log.error("实体校验异常:-------------->",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.failure(methodArgumentNotValid,objectError.getDefaultMessage());
    }

}

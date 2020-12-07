package com.lot.iotsite.utils;


import com.lot.iotsite.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public void setResultCode(ResultCode code){
        this.code=code.code();
        this.msg=code.message();
    }
    public static <T>Result<T> success(){
        Result<T> result=new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static <T>Result<T> success(T data){
        Result<T> result=new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;

    }
    public static <T>Result<T> failure(ResultCode resultCode){
        Result<T> result=new Result<>();
        result.setResultCode(resultCode);
        return result;
    }

    public static <T>Result<T> failure(ResultCode resultCode,T data){
        Result<T> result=new Result<>();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }
    public static <T>Result<T> failure(Integer code,String msg){
        Result<T> result=new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}

package com.lot.iotsite.constant;

import io.swagger.models.auth.In;

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(200, "success"),

    /* 用户错误：2001-2999*/
    USER_NOT_LOGGED_IN(2001, "用户未登录"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003, "账号已被禁用"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_PASSWORD_FAIL(2005, "密码错误"),
    USER_HAS_EXISTED(2006, "用户已存在"),
    USER_LOGIN_FAIL(2007,"登录失败"),
    USER_LOGIN_OVER_TIME(2008,"登录过期，请重新登录"),

    /* 系统错误：3001- 3999*/
    SYSTEM_INNER_ERROR(3001, "系统繁忙，请稍后重试"),

    /* 数据错误：4001-49999 */
    RESULE_DATA_NONE(4001, "数据未找到"),
    DATA_IS_WRONG(4002, "数据有误"),
    DATA_ALREADY_EXISTED(4003, "数据已存在"),

    /* 权限错误：5001-5999 */
    PERMISSION_NO_ACCESS(5001, "无访问权限");

    private Integer code;
    private String message;
    ResultCode(Integer code, String message){
        this.code=code;
        this.message=message;
    }
    public Integer code(){
        return code;
    }
    public String message(){
        return message;
    }
    public static String getMessage(Integer code){
        for(ResultCode item:ResultCode.values()){
            if(item.name().equals(code)){
                return item.message;
            }
        }
        return null;
    }

    public static Integer getCode(String name){
        for(ResultCode item: ResultCode.values()){
            if(item.name().equals(name)){
                return item.code;
            }
        }
        return null;

    }
}

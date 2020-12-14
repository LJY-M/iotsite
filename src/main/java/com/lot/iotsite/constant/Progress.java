package com.lot.iotsite.constant;

import io.swagger.models.auth.In;

public enum Progress {
    CREATED(101,"已创建"),
    UNDERWAY(102,"进行中"),
    FINISH(103,"已完成");
    private Integer code;
    private String status;
    Progress(Integer code,String status){
        this.code=code;
        this.status=status;
    }
     public Integer code(){return code;}
     public String status(){return status;}

     public static Integer getCode(String name){
        for(Progress item:Progress.values()){
            if(item.name().equals(name)){
                return item.code();
            }
        }
         return null;
     }
     public static String getStatus(Integer code){
        for(Progress item:Progress.values()){
            if(item.name().equals(code)){
                return item.status();
            }
        }
        return null;
     }
}

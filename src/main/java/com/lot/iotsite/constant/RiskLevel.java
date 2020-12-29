package com.lot.iotsite.constant;

import lombok.Data;
public enum RiskLevel {
    MID_RISK(0,"轻微风险"),
    GENERAL_RISK(1,"一般风险"),
    HIGHER_RISK(2,"高位风险");
    private Integer code;
    private String level;

    RiskLevel(Integer code,String level){
        this.code=code;
        this.level=level;
    }

    public Integer code(){return code;}
    public String status(){return level;}

    public static Integer getCode(String level){
        for(RiskLevel item:RiskLevel.values()){
            if(item.level.equals(level)){
                return item.code();
            }

        }
        return null;
    }
    public static String getStatus(Integer code){
        for(RiskLevel item:RiskLevel.values()){
            if(item.code.equals(code)){
                return item.status();
            }
        }
        return null;
    }
}

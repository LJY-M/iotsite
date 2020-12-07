package com.lot.iotsite.controller;

import com.lot.iotsite.constant.ResultCode;
import com.lot.iotsite.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hellowd {
    @GetMapping("/user")
    public String getUser(){
        if(4/2==2)throw new BusinessException(ResultCode.SYSTEM_INNER_ERROR);
        return "sbfjashid";
    }
}

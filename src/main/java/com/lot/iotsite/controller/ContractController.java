package com.lot.iotsite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractController {

    @GetMapping("/contract")
    public String test(){
        return "hellow";
    }
}

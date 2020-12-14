package com.lot.iotsite.controller;

import com.lot.iotsite.constant.Progress;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/combox")
public class ComboxController {

    /**
     * 状态下拉框
     * @return
     */
    @GetMapping("/status_combox")
    public Map<Integer,String> getProgress(){
        Map<Integer,String> map=new HashMap<>();
        Progress[] progresses=Progress.values();
        for(Progress progress:progresses){
            map.put(progress.code(),progress.status());
        }
        return map;
    }
}

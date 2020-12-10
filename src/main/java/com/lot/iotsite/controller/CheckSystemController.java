package com.lot.iotsite.controller;

import com.lot.iotsite.domain.CheckSystem;
import com.lot.iotsite.dto.CheckSystemDto;
import com.lot.iotsite.queryParam.CheckSystemParam;
import com.lot.iotsite.service.CheckSystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/check_system")
public class CheckSystemController{

    @Autowired
    private CheckSystemService checkSystemService;

    /**
     * 获取所有检查体系
     * @return
     */
    @GetMapping("/check_systems")
    public List<CheckSystemDto> allCheckSystems(){
        return checkSystemService.getAllCheckSystem();
    }

    /**
     * 新增一个检查体系
     * @param checkSystemParam
     * @return
     */
    @PostMapping("/check_system")
    public Boolean addCheckSystem(@SpringQueryMap CheckSystemParam checkSystemParam){
        Assert.notNull(checkSystemParam.getName(),"检查体系名字不能为空！");
        Assert.notNull(checkSystemParam.getWeight(),"检查体系权重不能为空！");
        CheckSystem checkSystem=new CheckSystem();
        BeanUtils.copyProperties(checkSystemParam,checkSystem);
        if(null==checkSystemParam.getFatherId()) checkSystem.setFatherId(0L);
        return checkSystemService.insertCheckSystem(checkSystem);
    }

    /**
     * 获取一级体系下的二级体系
     * @param id
     * @return
     */
    @GetMapping("/{id}/sub_check_systems")
    public List<CheckSystemDto> getSubCheckSystems(@PathVariable("id") Long id){
        List<CheckSystem> checkSystems=checkSystemService.getSubCheckSystemById(id);
        List<CheckSystemDto> checkSystemDtos=new ArrayList<>();
        for(CheckSystem item:checkSystems){
            CheckSystemDto checkSystemDto=new CheckSystemDto();
            BeanUtils.copyProperties(item,checkSystemDto);
            checkSystemDtos.add(checkSystemDto);
        }
        return checkSystemDtos;
    }

    

}

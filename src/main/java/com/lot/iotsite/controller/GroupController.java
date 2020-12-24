package com.lot.iotsite.controller;

import com.lot.iotsite.domain.Group;
import com.lot.iotsite.dto.SimpleGroupDto;
import com.lot.iotsite.queryParam.GroupParam;
import com.lot.iotsite.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/group")
    public List<SimpleGroupDto> getGroupNames(@RequestParam("name")String name){
        return groupService.getGroupNames(name);
    }

    @PostMapping("/create_group")
    public Boolean saveGroup(@SpringQueryMap @RequestBody GroupParam groupParam){
        org.springframework.util.Assert.notNull(groupParam.getName(),"项目组名不能为空！");
        Group group = new Group();
        BeanUtils.copyProperties(groupParam, group);
        return groupService.save(group);
    }

    @PostMapping("/delete_group/{id}")
    public Boolean deleteUserById(@PathVariable("id") Long id){
        return groupService.delete(id);
    }

    @PostMapping("/update_group/{id}")
    public Boolean updateUserById(@PathVariable("id") Long id,
                                  @SpringQueryMap @RequestBody GroupParam groupParam){
        /**可以更改的用户信息：
         * private String name;
         */
        Group group = groupService.getGroupById(id);
        BeanUtils.copyProperties(groupParam, group);
        return groupService.update(group);
    }
}

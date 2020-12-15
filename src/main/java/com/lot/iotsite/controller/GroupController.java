package com.lot.iotsite.controller;

import com.lot.iotsite.dto.SimpleGroupDto;
import com.lot.iotsite.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/group_names")
    public List<SimpleGroupDto> getGroupNames(){
        return groupService.getGroupNames();
    }
}

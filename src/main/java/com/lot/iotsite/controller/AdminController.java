package com.lot.iotsite.controller;


import com.lot.iotsite.dto.UserDto;
import com.lot.iotsite.service.GroupService;
import com.lot.iotsite.service.UserService;
import com.lot.iotsite.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/check_user")
    public List<UserDto> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/delete_user/{id}")
    public Boolean deleteUserById(Long id){
        return userService.delete(id);
    }

}

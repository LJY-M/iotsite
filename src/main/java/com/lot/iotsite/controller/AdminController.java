package com.lot.iotsite.controller;


import cn.hutool.crypto.SecureUtil;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.UserDto;
import com.lot.iotsite.queryParam.UserParam;
import com.lot.iotsite.service.UserGroupService;
import com.lot.iotsite.service.UserService;
import com.lot.iotsite.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/check_user")
    public List<UserDto> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/delete_user/{id}")
    public Boolean deleteUserById(@PathVariable("id") Long id){
        return userService.delete(id);
    }

    @PostMapping("/update_user/{id}")
    public Boolean updateUserById(@PathVariable("id") Long id,
                                  @SpringQueryMap @RequestBody UserParam userParam){
        /**可以更改的用户信息：
         *     private Long account;
         *     private String name;
         *     private String password;
         *     private String sex;
         *     private Integer userLimit;
         *     private String major;
         *     private String academic;
         *     private String nativePlace;
         *     private String address;
         *     private Integer telephone;
         *     private String job;
         */
        User user = userService.getUserById(id);
        userParam.setPassword(SecureUtil.md5(userParam.getPassword()));
        BeanUtils.copyProperties(userParam,user);
        return userService.update(user);
    }
}

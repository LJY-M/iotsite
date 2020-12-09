package com.lot.iotsite.controller;


import com.lot.iotsite.domain.User;
import com.lot.iotsite.service.UserService;
import com.lot.iotsite.utils.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author isHuangXin
 * @since 2020-12-04
 */
@RestController
@RequestMapping("/user")
@ResponseBody
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public User index(){
        User user = userService.getById(1234);
        return user;
    }

    /**
     * 测试实体校验
     * @param user
     * @return
     */
    @PostMapping("/save")
    public Boolean save(@Validated @RequestBody User user){
        return true;
    }



}

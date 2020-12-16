package com.lot.iotsite.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.SimpleUserDto;
import com.lot.iotsite.queryParam.LoginParam;
import com.lot.iotsite.service.UserService;
import com.lot.iotsite.utils.JwtUtils;
import com.lot.iotsite.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Map<Object,Object> login(@RequestBody LoginParam loginParam,
                                    HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("account", loginParam.getAccount()));
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginParam.getPassword()))) {
            System.out.println("密码错误");
            return null;
        }

        String jwt = jwtUtils.generateToken(user.getId(), user.getUserLimit());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return MapUtil.builder()
                .put("id", user.getId())
                .put("account", user.getAccount())
                .put("name", user.getName())
                .map();
    }

//    @PostMapping("/register")
//    public boolean register(@Validated @RequestBody User user,
//                            HttpServletResponse response){
//        User is_user = userService.getOne(new QueryWrapper<User>().eq("account", user.getAccount()));
//        Assert.notNull(is_user, "用户已存在");
//
//    }

    // 退出
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

    @GetMapping("/user_names")
    public List<SimpleUserDto> getUserNames(){
        return userService.getUserNames();
    }

}

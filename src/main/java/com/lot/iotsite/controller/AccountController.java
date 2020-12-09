package com.lot.iotsite.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.constant.ResultCode;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.dto.LoginDto;
import com.lot.iotsite.exception.BusinessException;
import com.lot.iotsite.service.UserService;
import com.lot.iotsite.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import javax.servlet.http.HttpServletResponse;


@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/login")
    public Boolean login(@RequestParam("account") String account,@RequestParam("password") String password,
                                    HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq(User.ACCOUNT, account));
        if(null==user) throw new BusinessException(ResultCode.USER_NOT_EXIST);
        if(!user.getPassword().equals(SecureUtil.md5(password)))
             throw new BusinessException(ResultCode.USER_PASSWORD_FAIL);
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return true;
    }

    // 退出
    @RequiresAuthentication
    @GetMapping("/logout")
    public Boolean logout() {
        SecurityUtils.getSubject().logout();
        return true;
    }
}
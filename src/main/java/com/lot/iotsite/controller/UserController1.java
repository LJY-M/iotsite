//package com.lot.iotsite.controller;
//
//import cn.hutool.core.lang.Assert;
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.crypto.SecureUtil;
//import com.lot.iotsite.domain.User;
//import com.lot.iotsite.queryParam.UserParam;
//import com.lot.iotsite.service.UserService;
//import com.lot.iotsite.utils.AccountUtils;
//import com.lot.iotsite.utils.JwtUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.annotation.RequiresAuthentication;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.openfeign.SpringQueryMap;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/user")
//public class UserController1 {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @Autowired
//    AccountUtils accountUtils;
//
//    @PostMapping("/test")
//    public void test(){
//        System.out.println("检测是否进入UserController");
//    }
//
//    @PostMapping("/login")
//    public Map<Object,Object> login(@Validated @RequestParam(value = "account") Long account,
//                                    @RequestParam(value = "password") String password,
//                                    HttpServletResponse response) {
//        //User user = userService.getOne(new QueryWrapper<User>().eq("account", account));
//        User user = userService.getUserByAccount(account);
//        Assert.notNull(user, "用户不存在");
//        if (!user.getPassword().equals(SecureUtil.md5(password))) {
//            System.out.println("密码错误");
//            return null;
//        }
//        String jwt = jwtUtils.generateToken(user.getId(), user.getUserLimit());
//        response.setHeader("Authorization", jwt);
//        response.setHeader("Access-Control-Expose-Headers", "Authorization");
//        // 用户可以另一个接口
//        return MapUtil.builder()
//                .put("id", user.getId())
//                .put("account", user.getAccount())
//                .put("name", user.getName())
//                .put("sex", user.getSex())
//                .put("user_limit", user.getUserLimit())
//                .put("major", user.getMajor())
//                .put("academic", user.getAcademic())
//                .put("native_place", user.getNativePlace())
//                .put("address", user.getAddress())
//                .put("tellphone", user.getTelephone())
//                .put("job", user.getJob())
//                .map();
//    }
//
//    @PostMapping("/register")
//    public boolean register(@SpringQueryMap @RequestBody UserParam userParam,
//                            HttpServletResponse response){
//        org.springframework.util.Assert.notNull(userParam.getAccount(),"用户账号不能为空！");
//        org.springframework.util.Assert.notNull(userParam.getName(),"用户姓名不能为空！");
//        org.springframework.util.Assert.notNull(userParam.getPassword(),"用户密码不能为空！");
//        // 对密码进行md5加密
//        userParam.setPassword(SecureUtil.md5(userParam.getPassword()));
//        User user = new User();
//        BeanUtils.copyProperties(userParam,user);
//        return userService.save(user);
//    }
//
//    @RequiresAuthentication
//    @GetMapping("/logout")
//    public boolean logout() {
//        SecurityUtils.getSubject().logout();
//        return true;
//    }
//
////    // RequiresAuthentication保证只有用户登录成功后才能进入此界面
////    @RequiresAuthentication
////    @PostMapping("/index")
////    public boolean index(@SpringQueryMap @RequestBody UserParam userParam,
////                         HttpServletResponse response, HttpServletRequest request) throws Exception {
////        Long userID = AccountUtils.getCurrentUser(request);
////        User user = userService.getUserById(userID);
////
////
////        // 将个人信息页修改的参数加入到userParam中，然后拷贝到user再进行用户信息更新
////        BeanUtils.copyProperties(userParam,user);
////
////        return userService.update(user);
////    }
//
//}
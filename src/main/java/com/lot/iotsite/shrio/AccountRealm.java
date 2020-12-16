package com.lot.iotsite.shrio;

import cn.hutool.core.bean.BeanUtil;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.service.UserService;
import com.lot.iotsite.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token){
        System.out.println("为了让realm支持jwt的凭证校验");
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("进入角色授权");
//        String userName = (String) principals.getPrimaryPrincipal();
//        Set<User> roleSet =  userService.findUserByUserName(userName).getRoleSet();
//        // 角色名的集合
//        Set<String> roles = new HashSet<String>();
//        // 权限名的集合
//        Set<String> permissions = new HashSet<String>();
//        Iterator<User> it = roleSet.iterator();
//        while(it.hasNext()){
//            roles.add(it.next().getName());
//            for(Permission per:it.next().getPermissionSet()){
//                permissions.add(per.getName());
//            }
//        }
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.addRoles(roles);
//        authorizationInfo.addStringPermissions(permissions);
//        return authorizationInfo;
        return null;
    }

    @Override
     public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进入登录认证");
        JwtToken jwtToken = (JwtToken) token;
        Long userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).get("userId",Long.class);
        User user = userService.getById(userId);
        if(user == null){
            throw new UnknownAccountException("账户不存在");
        }
        if(user.getDeleted() == -1){
            throw new LockedAccountException("账户已被删除");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}

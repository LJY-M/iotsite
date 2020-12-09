package com.lot.iotsite.shrio;

import cn.hutool.core.bean.BeanUtil;
import com.lot.iotsite.domain.User;
import com.lot.iotsite.service.UserService;
import com.lot.iotsite.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) token;

        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(Long.valueOf(userId));
        if(user == null){
            throw new UnknownAccountException("账户不存在");
        }

//        if(user.getSatus() == -1){
//            throw new LockedAccountException("账户已被锁定");
//        }

        System.out.println("===================");

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);

        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());

    }
}

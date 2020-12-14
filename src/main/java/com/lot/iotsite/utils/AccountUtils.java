package com.lot.iotsite.utils;

import com.lot.iotsite.shrio.AccountRealm;
import com.lot.iotsite.shrio.JwtFilter;
import com.lot.iotsite.shrio.JwtToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component
public class AccountUtils {
    @Autowired
    private static AccountRealm accountRealm;

    @Autowired
    private static JwtUtils jwtUtils;
    public static Long getCurrentUser(HttpServletRequest request)throws Exception {
        String jwt = request.getHeader("Authorazation");
        AuthenticationToken token=new JwtToken(jwt);
        JwtToken jwtToken = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        return Long.valueOf(userId);
    }
}

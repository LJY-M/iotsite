package com.lot.iotsite.utils;

import com.alibaba.fastjson.JSONObject;
import com.lot.iotsite.shrio.AccountProfile;
import com.lot.iotsite.shrio.AccountRealm;
import com.lot.iotsite.shrio.JwtFilter;
import com.lot.iotsite.shrio.JwtToken;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class AccountUtils {

    private static AccountRealm accountRealm;

    public AccountRealm getAccountRealm() {
        return accountRealm;
    }

    @Autowired
    public void setAccountRealm(AccountRealm accountRealm) {
        this.accountRealm = accountRealm;
    }

    public static Long getCurrentUser(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        AuthenticationToken token=new JwtToken(jwt);
        SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo)accountRealm.doGetAuthenticationInfo(token);
        List list = authenticationInfo.getPrincipals().asList();
        AccountProfile user=(AccountProfile)list.get(0);
        return user.getId();
    }
}

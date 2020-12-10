package com.lot.iotsite.utils;

import com.lot.iotsite.shrio.AccountRealm;
import com.lot.iotsite.shrio.JwtToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountUtils {
    @Autowired
    private static AccountRealm accountRealm;
}

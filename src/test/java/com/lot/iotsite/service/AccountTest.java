package com.lot.iotsite.service;

import com.lot.iotsite.shrio.JwtFilter;
import com.lot.iotsite.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountTest {
    @Autowired
    JwtUtils jwtUtils;

    @Test
    public void createToken(){
        //System.out.print(jwtUtils.generateToken(2020L));
    }
}

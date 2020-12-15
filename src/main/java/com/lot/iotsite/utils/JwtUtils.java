package com.lot.iotsite.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "whut.jwt")
@PowerMockIgnore("javax.crypto.*")
public class JwtUtils {

    private String secret = "f4e2e52034348f86b67cde581c0f9eb5";
    private long expire = 604800;
    private String header = "token";

    /**
     * 生成jwt, 得到token
     */
    public String generateToken(long userId, int userLimit) {
        System.out.println(secret+" "+expire+" "+header);
        Date nowDate= new Date();
        Date expireDate = new Date((nowDate).getTime()+expire*1000);
        // 把用户id和用户limit加入到token中去
        Map<String, Object> claims = new HashMap<>();
        claims.put("userLimit",userLimit);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setClaims(claims) // 在token中自定义添加数据
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 校验 jwt, 获取token的信息
     */
    public Claims getClaimByToken(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            System.out.println(" validate is token error : " + e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}

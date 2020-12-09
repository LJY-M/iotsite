package com.lot.iotsite.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "whut.jwt")
@PowerMockIgnore("javax.crypto.*")
public class JwtUtils {

    private String secret = "f4e2e52034348f86b67cde581c0f9eb5";
    private long expire = 604800;
    private String header = "token";

    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {
        System.out.println(secret+" "+expire+" "+header);
        Date nowDate= (Date) new Date();
        // 过期时间
        Date expireDate = new Date(((Date) nowDate).getTime()+expire*1000);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(userId+"")
                .setIssuedAt((Date) nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 获取jwt的信息
    /**
     * 校验 jwt token
     */
    public Claims getClaimByToken(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            System.out.println(" validate is token error " + e);
//            log.debug(" validate is token error ", e);
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

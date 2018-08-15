package com.silita.commons.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
public class JWTUtil {
    // 过期时间30分钟
    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    public static String lifeCycleTemp = null;


    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userName", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(ServletRequest request) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String authorization = httpServletRequest.getHeader("Authorization");
            if(!StringUtils.isEmpty(authorization)) {
                DecodedJWT jwt = JWT.decode(authorization);
                return jwt.getClaim("userName").asString();
            } else {
                return null;
            }
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,30min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        try {
            //密码MD5加密
            Object md5Password = new SimpleHash("MD5", secret, username, 2);
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(String.valueOf(md5Password));
            // 附带username信息
            return JWT.create()
                    .withClaim("userName", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}

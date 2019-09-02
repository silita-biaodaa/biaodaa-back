package com.silita.service;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-14 12:25
 */
public class MD5Test {
    public static void main(String[] args) {
        //加密方式
        String hashAlgorithmName = "MD5";
        //密码
        String credentials = "12345678";
        //加密次数
        int hashIterations = 2;
        //盐（用用户手机号）
        ByteSource credentialsSalt = ByteSource.Util.bytes("18774885504");
        Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
        System.out.println(obj);

        //d9a7cf53dbc8204afc639a8dc356e1bc
    }
}

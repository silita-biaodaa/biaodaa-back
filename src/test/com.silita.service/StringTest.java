package com.silita.service;

import com.silita.utils.PinYinUtil;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:21
 */
public class StringTest {
    public static void main(String[] args) {
//        System.out.println(DataHandlingUtil.getUUID());
//        System.out.println(PinYinUtil.cn2py("合理低价法"));
        System.out.println("hunan_pdmode_" + PinYinUtil.cn2py("合理低价法") + "_" +System.currentTimeMillis());
    }
}

package com.silita.service;

import com.silita.utils.DataHandlingUtil;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:21
 */
public class StringTest {
    public static void main(String[] args) {
        System.out.println(DataHandlingUtil.getTimeStamp());
        System.out.println(DataHandlingUtil.getTimeStamp().length());

        System.out.println(DataHandlingUtil.getTimeStamp() + "_" + DataHandlingUtil.getNumberRandom(4));
        System.out.println((DataHandlingUtil.getTimeStamp() + "_" + DataHandlingUtil.getNumberRandom(4)).length());
    }
}

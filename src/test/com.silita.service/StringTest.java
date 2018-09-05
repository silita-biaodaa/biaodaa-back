package com.silita.service;

import com.silita.utils.WordProcessingUtil;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:21
 */
public class StringTest {
    public static void main(String[] args) {
//        System.out.println(DataHandlingUtil.getTimeStamp());
//        System.out.println(DataHandlingUtil.getTimeStamp().length());
//
//        System.out.println(DataHandlingUtil.getTimeStamp() + "_" + DataHandlingUtil.getNumberRandom(4));
//        System.out.println((DataHandlingUtil.getTimeStamp() + "_" + DataHandlingUtil.getNumberRandom(4)).length());


        String html = "<span style=\"font-size: 14pt; font-family: 宋体; font-weight: normal; line-height: 150%; mso-spacerun: 'yes'; mso-font-kerning: 1.0000pt\"><font face=\"宋体\">开标时间：<span style=\"font-size: 14pt\">2018年4月24日9时30分</span></font></span>";
        String text = WordProcessingUtil.getTextFromHtml(html);
        System.out.println(text);
    }
}

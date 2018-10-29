package com.silita.service;

import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-10 16:21
 */
public class StringTest {
    public static void main(String[] args) throws ParseException {
//        System.out.println(DataHandlingUtil.getTimeStamp());
//        System.out.println(DataHandlingUtil.getTimeStamp().length());
//
//        System.out.println(DataHandlingUtil.getTimeStamp() + "_" + DataHandlingUtil.getNumberRandom(4));
//        System.out.println((DataHandlingUtil.getTimeStamp() + "_" + DataHandlingUtil.getNumberRandom(4)).length());


//        String str = StringUtils.HumpToUnderline("pbMode");
//        System.out.println(str);
//
//        String str2 = StringUtils.UnderlineToHump("pb_mode");
//        System.out.println(str2);

//        String str = "0000-00-00 00:00:00";
//        String str = "2018-09-25 11:15:18.0";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = simpleDateFormat.parse(str);
//        long ts = date.getTime();
//        System.out.println(ts);


//        String str = "1537845318000";
//        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(new Date(Long.parseLong(str)));

//        String groupRegex = "G4";
//        String[] blockQual = groupRegex.split("\\|");
//        System.out.println(blockQual.toString());

//        String finalUuid = "4c7f9474-2934-11e5-a311-63b86f04c8dd|21";
//        System.out.println(finalUuid.substring(0, finalUuid.indexOf("|")));
//        System.out.println(finalUuid.substring(finalUuid.indexOf("|")));

        String groupRegex = "1540778640192&1540778640250|1540778640272";
        Pattern p = Pattern.compile("[^(&)|(\\|)]");
        String[] strs = p.split("");
        for (String str : strs) {
//            System.out.println(str);
        }

        String grouprRelType = groupRegex.replaceAll("[^(&)|(\\|)]", "");
        char[] chars = grouprRelType.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.println(chars[i]);
        }
    }
}

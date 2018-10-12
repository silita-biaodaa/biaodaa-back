package com.silita.service;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-12 15:49
 */
public class JsonParseTest {
    public static void main(String[] args) {
        String jsonStr = "[ { \"name\": [ { \"label\": \"老板\", \"rela\": \"或\" } ], \"label\": \"老板\", \"rela\": \"和\" }, { \"name\": [ { \"label\": \"老板\", \"rela\": \"和\" }, { \"label\": \"经理\", \"rela\": \"或\" } ], \"label\": \"老板\", \"rela\": \"和\" }, { \"name\": [ { \"label\": \"老板\", \"rela\": \"和\" } ], \"label\": \"老板\" } ]";
        System.out.println(jsonStr);


    }
}

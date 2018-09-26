package com.silita.service;

import com.silita.model.TbNtTenders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-09-26 10:00
 */
public class ModelTest {
    public static void main(String[] args) {
        TbNtTenders tbNtTenders = new TbNtTenders();
        tbNtTenders.setSource("湖南");
        tbNtTenders.setAuditTime(new Date());
        tbNtTenders.setIsFlow(true);
        tbNtTenders.setControllSum(111.1);
        try {
            reflectTest(tbNtTenders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reflectTest(Object model) throws Exception {
        //获取实体类的所有属性，返回Field数组
        Field[] fields = model.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            String name = fields[j].getName();

            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String keyName = com.silita.utils.stringUtils.StringUtils.HumpToUnderline(name).substring(1);
            String type = fields[j].getGenericType().toString();
            System.out.println("key值为：" + name + "-----" +  keyName);
            if (type.equals("class java.lang.String")) {
                Method m = model.getClass().getMethod("set" + name);
                m.invoke(model, "反射修改");
            }
            if (type.equals("class java.lang.Double")) {
                Method m = model.getClass().getMethod("get" + name);
                Double value = (Double) m.invoke(model);
                System.out.println("数据类型为：Double");
                if (value != null) {
                    System.out.println("属性值为：" + value);
                }
            }
            if (type.equals("class java.lang.Boolean")) {
                Method m = model.getClass().getMethod("get" + name);
                Boolean value = (Boolean) m.invoke(model);
                System.out.println("数据类型为：Boolean");
                if (value != null) {
                    System.out.println("属性值为：" + value);
                }
            }
            if (type.equals("class java.util.Date")) {
                Method m = model.getClass().getMethod("get" + name);
                Date value = (Date) m.invoke(model);
                System.out.println("数据类型为：Date");
                if (value != null) {
                    System.out.println("属性值为：" + value);
                }
            }
        }
    }

}

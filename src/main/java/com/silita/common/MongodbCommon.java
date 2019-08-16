package com.silita.common;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.silita.utils.PropertiesUtils;
import com.silita.utils.mongdbUtlis.MongoUtils;
import org.apache.commons.collections.MapUtils;
import tk.mybatis.mapper.util.StringUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MongodbCommon {

    /**
     * 获取用户状态 ：1:付费、2及以上：续费 else 注册
     * @return
     */
    public static Map<String,Integer> getUserType(){
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        List<String> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    & (orderStatus == 9)){
                listMap.add(MapUtils.getString(map, "userId"));
            }
        }

        Map<String,Integer> maps = new HashMap<>();
        for (String user : listMap) {
            if (null != maps.get(user)){
                int count = maps.get(user);
                count ++;
                maps.put(user,count);
            }else {
                maps.put(user,1);
            }
        }

        return maps;
    }

    public static Map<String,Integer> getUserPayCount(){
        Map<String,Integer> maps = new HashMap<>();
        //今日
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = sdf.format(date);
        //昨日
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -2);
        String yesterdayDate = sdf.format(c.getTime());
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        int yesterdatCount = 0;
        int todayCount = 0;
        int totalCount=0;
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)){
                totalCount++;
                Date createTime =(Date) map.get("createTime");
                String timeCycle = sdf.format(createTime);
                if (timeCycle.equals(yesterdayDate)){
                    yesterdatCount++;
                }else if(timeCycle.equals(todayDate)){
                    todayCount++;
                }

            }
        }
        maps.put("yesterdayPay",yesterdatCount);
        maps.put("todayPay",todayCount);
        maps.put("totalPayUser",totalCount);
        return maps;
    }

    public static Map<String,Object> getPastDue(){
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        Map<String,Object> maps = new HashMap<>();
        for (DBObject dbObject : dbObjects) {

            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)){
                maps.put(MapUtils.getString(map,"userId"),MapUtils.getString(map,"userId"));
            }

        }
        return maps;
    }


    public static String fenToYuan(String amount){
        NumberFormat format = NumberFormat.getInstance();
        try{
            Number number = format.parse(amount);
            double temp = number.doubleValue() / 100.0;
            format.setGroupingUsed(false);
            // 设置返回的小数部分所允许的最大位数
            format.setMaximumFractionDigits(2);
            amount = format.format(temp);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return amount;
    }

    public static Map<String,Object> getOrderCount(){
        //今日
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = sdf.format(date);
        //昨日
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -2);
        String yesterdayDate = sdf.format(c.getTime());

        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        Map<String,Object> maps = new HashMap<>();
        //昨日订单
        int yesterdayOrderCount = 0;
        //今日订单
        int todayOrderCount = 0;
        //昨日已付
        double yesterdayPaidCount = 0;
        double yesterdayPaidCountIos = 0;
        //今日已付
        double todayPaidCount = 0;
        double todayPaidCountIos = 0;
        //今日应收
        double todayReceivableCount = 0;
        double todayReceivableCountIos = 0;
        //今日实收
        double todayTrueMoneyCount = 0;
        double todayTrueMoneyCountIos = 0;
        //总金额
        double totalMoney = 0;
        double totalMoneyIos = 0;

        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            String tradeType = MapUtils.getString(map, "tradeType");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)){
                Date createTime =(Date) map.get("createTime");
                String timeCycle = sdf.format(createTime);
                Integer fee = MapUtils.getInteger(map, "fee");
                if (timeCycle.equals(yesterdayDate)){
                    //昨日订单
                    yesterdayOrderCount++;
                    //昨日已付
                    if(fee != null && fee > 0){
                        if(StringUtil.isNotEmpty(tradeType)){
                            if(tradeType.equals("ios app")){
                                yesterdayPaidCountIos=yesterdayPaidCountIos+fee;
                            }else{
                                yesterdayPaidCount=yesterdayOrderCount+fee;
                            }
                        }

                    }
                }else if(timeCycle.equals(todayDate)){
                    //今日订单
                    todayOrderCount++;
                    //今日已付
                    if(fee != null && fee > 0){
                        if(StringUtil.isNotEmpty(tradeType)){
                            if(tradeType.equals("ios app")){
                                todayPaidCountIos=todayPaidCountIos+fee;
                                todayReceivableCountIos=todayReceivableCountIos+fee;
                                todayTrueMoneyCountIos=todayTrueMoneyCountIos+fee;
                            }else{
                                todayPaidCount=todayPaidCount+fee;
                                todayReceivableCount=todayReceivableCount+fee;
                                todayTrueMoneyCount=todayTrueMoneyCount+fee;
                            }
                        }

                    }

                }
                if(fee != null && fee > 0){
                    if(StringUtil.isNotEmpty(tradeType)){
                        if(tradeType.equals("ios app")){
                            totalMoneyIos=totalMoneyIos+fee;
                        }else{
                            totalMoney=totalMoney+fee;
                        }
                    }

                }

            }
        }


        //ios  68%
        yesterdayPaidCountIos = yesterdayPaidCountIos * 0.68;
        todayPaidCountIos = todayPaidCountIos * 0.68;
        todayReceivableCountIos = todayReceivableCountIos * 0.68;
        todayTrueMoneyCountIos = todayTrueMoneyCountIos * 0.68;
        totalMoneyIos = totalMoneyIos * 0.68;

        String one = yesterdayPaidCountIos+"";
        String two = todayPaidCountIos+"";
        String three = yesterdayPaidCountIos+"";
        String four = todayTrueMoneyCountIos+"";
        String five = totalMoneyIos+"";

        String yesterdayIos = MongodbCommon.fenToYuan(one);
        String todaypaidIos = MongodbCommon.fenToYuan(two);
        String receivableIos = MongodbCommon.fenToYuan(three);
        String trueMoneyIos = MongodbCommon.fenToYuan(four);
        String zongIos = MongodbCommon.fenToYuan(five);





        //昨日已付  分转元
        String yesterdayPaid = yesterdayPaidCount+"";
        String yesterday = MongodbCommon.fenToYuan(yesterdayPaid);
        //今日已付  分转元
        String todayPaid = todayPaidCount+"";
        String today = MongodbCommon.fenToYuan(todayPaid);
        //今日应收 分转元
        String receivable = todayReceivableCount+"";
        String yin = MongodbCommon.fenToYuan(receivable);
        //今日实收 分转元
        String trueMoney = todayTrueMoneyCount+"";
        String shi = MongodbCommon.fenToYuan(trueMoney);
        //总金额  分转元
        String total = totalMoney+"";
        String zong = MongodbCommon.fenToYuan(total);
        //ios
        double yesterdayIosOne = Double.parseDouble(yesterdayIos);
        double todaypaidIosOne = Double.parseDouble(todaypaidIos);
        double receivableIosOne = Double.parseDouble(receivableIos);
        double trueMoneyIosOne = Double.parseDouble(trueMoneyIos);
        double zongIosOne = Double.parseDouble(zongIos);
        //其他
        double yesterdayOne = Double.parseDouble(yesterday);
        double todayOne = Double.parseDouble(today);
        double yinOne = Double.parseDouble(yin);
        double shiOne = Double.parseDouble(shi);
        double zongOne = Double.parseDouble(zong);

        yesterdayOne=yesterdayOne+yesterdayIosOne;
        todayOne=todayOne+todaypaidIosOne;
        yinOne=yinOne+receivableIosOne;
        shiOne=shiOne+trueMoneyIosOne;
        zongOne=zongOne+zongIosOne;



        maps.put("yesterdayOrder",yesterdayOrderCount);
        maps.put("todayOrder",todayOrderCount);
        maps.put("yesterdayPaid",yesterdayOne);
        maps.put("todayPaid",todayOne);
        maps.put("todayReceivable",yinOne);
        maps.put("todayTrueMoney",shiOne);
        maps.put("totalMoney",zongOne);

        return maps;
    }
}

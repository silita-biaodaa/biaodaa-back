package com.silita.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.model.Filters;
import com.silita.common.MongodbCommon;
import com.silita.common.MyRedisTemplate;
import com.silita.utils.PropertiesUtils;
import com.silita.utils.mongdbUtlis.MongoUtils;
import org.apache.commons.collections.MapUtils;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RedisTest extends ConfigTest {

    @Autowired
    MyRedisTemplate myRedisTemplate;

    @Autowired
    IUserInfoService iUserInfoService;

    @Test
    public void test() {
        // myRedisTemplate.setObject("ceshikey","filter_map");

        Object sys_area_region = myRedisTemplate.getObject("sys_area_region");

        Map<String, Object> map = (Map<String, Object>) sys_area_region;

        String countyCode = (String) map.get("xiangtan");

        System.out.println("取到值了：" + countyCode);


        for (String s : map.keySet()) {
            System.out.println(s);
        }


    }

    @Test
    public void test2() {
      /*  DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        //6. 查询各岗位内包含的员工个数小于2的岗位名、岗位内包含员工名字、个数
        //db.emp.aggregate({
        //              "$group":{"_id":"$post","count":{"$sum":1},"names":{"$push":"$name"}}
//      },
        //{"$match":{"count":{"$lt":2}}},
        // {"$project":{"_id":0,"names":1,"count":1}})



        String mongidb = "{\n" +
                "    \"$group\":{\"_id\":\"$userId\",\"count\":{\"$sum\":1}}\n" +

                "}";



        DBCursor dbObjects = dbCollection.find();
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map<String, Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            if (stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter")) {
                mongodbMap.put("stdCode", stdCode);
                mongodbMap.put("userId", MapUtils.getString(map, "userId"));
                listMap.add(mongodbMap);
            }
        }
        for (Map<String, Object> map : listMap) {
            System.out.println("map:" + map);
        }
        System.out.println("count:" + listMap.size());
        int count = 1;
        for (Map<String, Object> map : listMap) {
            Map<String,Object> countUserIdMap = new HashMap<>();
            String userId = MapUtils.getString(map, "userId");
            for (Map<String, Object> map1 : listMap) {


                String userId1 = MapUtils.getString(map1, "userId");
                if(userId.equals(userId)){
                    count++;
                }
            }
            map.put("count",count);
        }

        for (Map<String, Object> map : listMap) {
            System.out.println("maps:"+map);
        }*/
    }

   /* @Test
    public void test3(){
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();

        List<Map<String, Object>> listMap = new ArrayList<>();
        int count =1;
        for (DBObject dbObject : dbObjects) {
            Map<String, Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            if (stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter")) {
                if(MapUtils.getString(mongodbMap, "userId").equals(MapUtils.getString(map,"userId"))){
                    count=count+1;
                    mongodbMap.put("userId", MapUtils.getString(map, "userId"));
                    mongodbMap.put("count",count);

                }else{

                }
                listMap.add(mongodbMap);

            }
        }
        for (Map<String, Object> map : listMap) {
            Map<String,Object> maps = new HashMap<>();
            if(MapUtils.getString(map,"userId").equals(MapUtils.getString(maps,"userId"))){
                Integer count1 = MapUtils.getInteger(map, "count");
                count1=count1+1;
                map.put("count",count);
            }else{
                maps.put("userId",MapUtils.getString(map,"userId"));
                maps.put("count",1);
            }
        }
    }*/


    @Test
    public void test4() {
   /*     DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();

        List<Map<String, Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map<String, Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            if (stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter")) {
                mongodbMap.put("userId", MapUtils.getString(map, "userId"));
                mongodbMap.put("count", 1);
                listMap.add(mongodbMap);

            }
        }
        Map<String, Object> maps = new HashMap<>();
        for (Map<String, Object> map : listMap) {
            if (maps.get(MapUtils.getString(map, "userId")) != null) {
                map.remove("userId",MapUtils.getString(map,"userId"));
            } else {
                maps.put(MapUtils.getString(map, "userId"), MapUtils.getString(map, "userId"));
            }
        }*/
   /*     int a=1;
        for (Map<String, Object> map : listMap) {
            System.out.println("map:"+map);
            Integer count = MapUtils.getInteger(map, "count");
            if(count == 1){
                a++;
            }
        }
        System.out.println("a:"+a);*/
        //System.out.println(listMap.size());
    }

    @Test
    public void test5(){
  /*      DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map<String,Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            mongodbMap.put("userId", MapUtils.getString(map, "userId"));
            listMap.add(mongodbMap);
        }

     *//*   for (Map<String, Object> map : listMap) {
            System.out.println("map:"+map);
        }
*//*


        List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();

        for(int i=0; i<listMap.size(); i++){
            Map<String,Object> oldMap = listMap.get(i);
            if(newList.size()>0){
                boolean isContain = false;
                for(int j=0; j<newList.size();j++){
                    Map<String,Object> newMap = newList.get(j);
                    if(newMap.get("userId").equals(oldMap.get("userId"))){
                        for(String key :oldMap.keySet()){
                            newMap.put(key, oldMap.get(key));
                        }
                        isContain = true;
                        break;
                    }
                }

                if(!isContain){
                    newList.add(oldMap);
                }

            }else{
                newList.add(oldMap);
            }
        }

        for (Map<String, Object> map : newList) {
            System.out.println("map:"+map);
        }


        System.out.println("listMap:"+listMap.size());
        System.out.println("newList:"+newList.size());*/
    }


    @Test
    public void test6(){
       /* DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map<String,Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            mongodbMap.put("userId", MapUtils.getString(map, "userId"));
            mongodbMap.put("count", 1);
            listMap.add(mongodbMap);
        }
        List<Map<String,Object>> newListMap = new ArrayList<>();

        Map<String,Object> maps = new HashMap<>();
        for (Map<String, Object> map : listMap) {
            Map<String,Object> newMap = new HashMap<>();
            if(newListMap != null && newListMap.size() >0){
                String userId = MapUtils.getString(maps, MapUtils.getString(map, "userId"));
                if(StringUtil.isNotEmpty(userId)){
                    for (Map<String, Object> map2 : newListMap) {
                            Integer count = MapUtils.getInteger(map2, "count");
                            count=count+1;
                            map2.put("count",count);
                    }

                }else{
                    maps.put(MapUtils.getString(map,"userId"),MapUtils.getString(map,"userId"));
                    newMap.put("userId",MapUtils.getString(map,"userId"));
                    newMap.put("count",MapUtils.getInteger(map,"count"));
                }
                newListMap.add(newMap);
            }else{
                maps.put(MapUtils.getString(map,"userId"),MapUtils.getString(map,"userId"));
                newMap.put("userId",MapUtils.getString(map,"userId"));
                newMap.put("count",MapUtils.getInteger(map,"count"));
                newListMap.add(newMap);
            }
        }

        for (Map<String, Object> map : newListMap) {
            System.out.println("map:"+map);
        }
        System.out.println(newListMap.size());*/
    }


    @Test
    public void  test7(){
       /* DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map<String,Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
            & (orderStatus == 9)){
                mongodbMap.put("userId", MapUtils.getString(map, "userId"));
                mongodbMap.put("count", 1);
                listMap.add(mongodbMap);
            }
        }
        List<Map<String,Object>> newListMap = new ArrayList<>();

        Map<String,Object> maps = new HashMap<>();
        for (Map<String, Object> map : listMap) {
            Map<String,Object> newMap = new HashMap<>();
            if(newListMap != null && newListMap.size() >0){
                String userId = MapUtils.getString(maps, MapUtils.getString(map, "userId"));
                if(StringUtil.isNotEmpty(userId)){
                    for (Map<String, Object> map2 : newListMap) {
                        String userId1 = MapUtils.getString(map2, "userId");
                        String userId2 = MapUtils.getString(map, "userId");
                        if(StringUtil.isNotEmpty(userId1) && userId1.equals(userId2)){
                            Integer count = MapUtils.getInteger(map2, "count");
                            count=count+1;
                            map2.put("count",count);
                        }
                    }

                }else{
                    maps.put(MapUtils.getString(map,"userId"),MapUtils.getString(map,"userId"));
                    newMap.put("userId",MapUtils.getString(map,"userId"));
                    newMap.put("count",MapUtils.getInteger(map,"count"));
                    newListMap.add(newMap);
                }

            }else{
                maps.put(MapUtils.getString(map,"userId"),MapUtils.getString(map,"userId"));
                newMap.put("userId",MapUtils.getString(map,"userId"));
                newMap.put("count",MapUtils.getInteger(map,"count"));
                newListMap.add(newMap);
            }
        }

        for (Map<String, Object> map : newListMap) {
            System.out.println("map:"+map);
        }
        System.out.println(newListMap.size());*/
    }



    @Test
    public void  test9(){
       /* DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
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

        System.out.println("map:"+maps);*/
    }


    @Test
    public void test10(){
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map<String,Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)){
            }else if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 2)){

            }
        }
    }

    @Test
    public void test11() throws ParseException {
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
              //  Date startDate = sdf.parse(createTime);
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


        System.out.println("maps:"+maps);



    }

    @Test
    public void test12(){


        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        Map<String,Object> mongodbMap = new HashMap<>();
        for (DBObject dbObject : dbObjects) {

            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)){
                mongodbMap.put(MapUtils.getString(map,"userId"),MapUtils.getString(map,"userId"));
            }

        }
    }


    @Test
    public void test13(){

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

        System.out.println("maps:"+maps);

    }

    @Test
    public void test14(){
        double yesterdayPaidCountIos=100;
        yesterdayPaidCountIos=yesterdayPaidCountIos * 0.68;
        System.out.println(yesterdayPaidCountIos);


    }

    @Test
    public void test15(){
        double a = 500.12;
        double b = 600.00;

        System.out.println( a=a+b);


    }


}
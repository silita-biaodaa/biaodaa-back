package com.silita.service;

//import com.mongodb.*;

import com.silita.common.MyRedisTemplate;
import com.silita.model.OrderInfo;
import com.silita.service.mongodb.MongodbService;
import com.silita.utils.dateUtils.MyDateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RedisTest extends ConfigTest {

    @Autowired
    MyRedisTemplate myRedisTemplate;

    @Autowired
    IUserInfoService iUserInfoService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongodbService mongodbService;


    @Test
    public void test(){

        Object sys_area_region = myRedisTemplate.getObject("sys_area_region");

        Map<String, Object> map = (Map<String, Object>) sys_area_region;

        String countyCode = (String) map.get("xiangtan");

        System.out.println("取到值了：" + countyCode);


        for (String s : map.keySet()) {
            System.out.println(s);
        }


    }

    @Test
    public void test44() {
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2015-06-01");//定义起始日期

            Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2016-05-01");//定义结束日期

            Calendar dd = Calendar.getInstance();//定义日期实例

            dd.setTime(d1);//设置日期起始时间

            while (dd.getTime().before(d2)) {//判断是否到结束日期

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd");

                String str = sdf.format(dd.getTime());

                System.out.println(str);//输出日期结果

                dd.add(Calendar.DAY_OF_YEAR, 1);//进行当前日期月份加1
            }
        }catch (Exception e){
            e.printStackTrace();
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
    public void test5() {
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
    public void test6() {
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
    public void test7() {
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
                String createTime = MapUtils.getString(map, "createTime");
                Integer vipDays = MapUtils.getInteger(map, "vipDays");
                String activeDates = MyDateUtils.getActiveDates(createTime);
                mongodbMap.put("createTime",activeDates);
                mongodbMap.put("vipDays",vipDays);
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
                    newListMap.add(map);
                }
            }else{
                maps.put(MapUtils.getString(map,"userId"),MapUtils.getString(map,"userId"));
                newListMap.add(map);
            }
        }

        for (Map<String, Object> map : newListMap) {
            System.out.println("map:"+map);
        }
        System.out.println(newListMap.size());*/
    }


    @Test
    public void test9() {
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
    public void test10() {
       /* DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map<String, Object> mongodbMap = new HashMap<>();
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)) {
            } else if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 2)) {

            }
        }*/
    }

    @Test
    public void test11() throws ParseException {
      /*  Map<String, Integer> maps = new HashMap<>();
        //今日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        int totalCount = 0;
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)) {
                totalCount++;
                Date createTime = (Date) map.get("createTime");
                //  Date startDate = sdf.parse(createTime);
                String timeCycle = sdf.format(createTime);
                if (timeCycle.equals(yesterdayDate)) {
                    yesterdatCount++;
                } else if (timeCycle.equals(todayDate)) {
                    todayCount++;
                }

            }
        }
        maps.put("yesterdayPay", yesterdatCount);
        maps.put("todayPay", todayCount);
        maps.put("totalPayUser", totalCount);


        System.out.println("maps:" + maps);
*/

    }

    @Test
    public void test12() {

/*
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        Map<String, Object> mongodbMap = new HashMap<>();
        for (DBObject dbObject : dbObjects) {

            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)) {
                mongodbMap.put(MapUtils.getString(map, "userId"), MapUtils.getString(map, "userId"));
            }

        }*/
    }


/*    @Test
    public void test13() {

        //今日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = sdf.format(date);
        //昨日
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -2);
        String yesterdayDate = sdf.format(c.getTime());

        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        Map<String, Object> maps = new HashMap<>();
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
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter"))
                    && (orderStatus == 9)) {
                Date createTime = (Date) map.get("createTime");
                String timeCycle = sdf.format(createTime);
                Integer fee = MapUtils.getInteger(map, "fee");
                if (timeCycle.equals(yesterdayDate)) {
                    //昨日订单
                    yesterdayOrderCount++;
                    //昨日已付
                    if (fee != null && fee > 0) {
                        if (StringUtil.isNotEmpty(tradeType)) {
                            if (tradeType.equals("ios app")) {
                                yesterdayPaidCountIos = yesterdayPaidCountIos + fee;
                            } else {
                                yesterdayPaidCount = yesterdayOrderCount + fee;
                            }
                        }

                    }
                } else if (timeCycle.equals(todayDate)) {
                    //今日订单
                    todayOrderCount++;
                    //今日已付
                    if (fee != null && fee > 0) {
                        if (StringUtil.isNotEmpty(tradeType)) {
                            if (tradeType.equals("ios app")) {
                                todayPaidCountIos = todayPaidCountIos + fee;
                                todayReceivableCountIos = todayReceivableCountIos + fee;
                                todayTrueMoneyCountIos = todayTrueMoneyCountIos + fee;
                            } else {
                                todayPaidCount = todayPaidCount + fee;
                                todayReceivableCount = todayReceivableCount + fee;
                                todayTrueMoneyCount = todayTrueMoneyCount + fee;
                            }
                        }

                    }

                }
                if (fee != null && fee > 0) {
                    if (StringUtil.isNotEmpty(tradeType)) {
                        if (tradeType.equals("ios app")) {
                            totalMoneyIos = totalMoneyIos + fee;
                        } else {
                            totalMoney = totalMoney + fee;
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

        String one = yesterdayPaidCountIos + "";
        String two = todayPaidCountIos + "";
        String three = yesterdayPaidCountIos + "";
        String four = todayTrueMoneyCountIos + "";
        String five = totalMoneyIos + "";

        String yesterdayIos = MongodbCommon.fenToYuan(one);
        String todaypaidIos = MongodbCommon.fenToYuan(two);
        String receivableIos = MongodbCommon.fenToYuan(three);
        String trueMoneyIos = MongodbCommon.fenToYuan(four);
        String zongIos = MongodbCommon.fenToYuan(five);


        //昨日已付  分转元
        String yesterdayPaid = yesterdayPaidCount + "";
        String yesterday = MongodbCommon.fenToYuan(yesterdayPaid);
        //今日已付  分转元
        String todayPaid = todayPaidCount + "";
        String today = MongodbCommon.fenToYuan(todayPaid);
        //今日应收 分转元
        String receivable = todayReceivableCount + "";
        String yin = MongodbCommon.fenToYuan(receivable);
        //今日实收 分转元
        String trueMoney = todayTrueMoneyCount + "";
        String shi = MongodbCommon.fenToYuan(trueMoney);
        //总金额  分转元
        String total = totalMoney + "";
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

        yesterdayOne = yesterdayOne + yesterdayIosOne;
        todayOne = todayOne + todaypaidIosOne;
        yinOne = yinOne + receivableIosOne;
        shiOne = shiOne + trueMoneyIosOne;
        zongOne = zongOne + zongIosOne;


        maps.put("yesterdayOrder", yesterdayOrderCount);
        maps.put("todayOrder", todayOrderCount);
        maps.put("yesterdayPaid", yesterdayOne);
        maps.put("todayPaid", todayOne);
        maps.put("todayReceivable", yinOne);
        maps.put("todayTrueMoney", shiOne);
        maps.put("totalMoney", zongOne);

        System.out.println("maps:" + maps);

    }*/

    @Test
    public void test14() {
        double yesterdayPaidCountIos = 100;
        yesterdayPaidCountIos = yesterdayPaidCountIos * 0.68;
        System.out.println(yesterdayPaidCountIos);


    }

    @Test
    public void test15() {
        double a = 500.12;
        double b = 600.00;

        System.out.println(a = a + b);


    }

    @Test
    public void test16() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dates = sdf.parse("2019-09-18");
            Calendar c = Calendar.getInstance();
            c.setTime(dates);
            c.add(Calendar.DATE, 1);

            String date = sdf.format(c.getTime());
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test17() {
        List<Map<String, Object>> listMap = new ArrayList<>();


        Map<String, Object> map1 = new HashMap<>();
        map1.put("userId", "2018-01-01");
        map1.put("b", 2);
        listMap.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("userId", "2018-01-03");
        map2.put("b", 2);
        listMap.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("userId", "2018-01-02");
        map3.put("b", 2);
        listMap.add(map3);
        for (Map<String, Object> map : listMap) {
            System.out.println("排序前：" + map);
        }

        Collections.sort(listMap, new Comparator<Map<String, Object>>() {

            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = (String) o1.get("userId");//name1是从你list里面拿出来的一个
                String name2 = (String) o2.get("userId"); //name1是从你list里面拿出来的第二个name
                return name1.compareTo(name2);
            }
        });

        for (Map<String, Object> map : listMap) {
            System.out.println("排序后:" + map);
        }

    }

    @Test
    public void test18() {
      /*  BasicDBObject forceEnd = new BasicDBObject();
        forceEnd.put("orderNo", "201908220504211823T26VA");*/

       /* try {
            DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
            DBCursor dbObjects = dbCollection.find();
            List<Map<String, Object>> listMap = new ArrayList<>();
            for (DBObject dbObject : dbObjects) {
                Map map = dbObject.toMap();
                Map<String, Object> maps = new HashMap<>();
                maps.put("orderNo", MapUtils.getString(map, "orderNo"));
                maps.put("createTime", MapUtils.getString(map, "createTime"));
                listMap.add(maps);
            }


            for (Map<String, Object> map : listMap) {
                System.out.println(map);
                String createTime = MapUtils.getString(map, "createTime");
                System.out.println(createTime);
                SimpleDateFormat sdff = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                Date d = sdff.parse(createTime);
                String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
                System.out.println("formatDate:"+formatDate);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void test19() {
        try {
            String date = "Thu Aug 27 18:05:49 CST 2015";
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date d = sdf.parse(date);
            String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
            System.out.println(formatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
/*
    @Test
    public void test19() {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", "161a12ff4fc140799786187b10f8f1c5");
        List<Map<String, Object>> topUpListMap = MongodbCommon.getTopUp(param);
        for (Map<String, Object> map : topUpListMap) {
            System.out.println(map);
        }
    }*/
/*
    @Test
    public void test20() {

        BasicDBList endList = new BasicDBList();
        BasicDBObject forceEnd = new BasicDBObject();
        forceEnd.put("orderStatus", 9);
        forceEnd.put("stdCode", "month");

        BasicDBList condList = new BasicDBList();
        BasicDBObject autoEnd = new BasicDBObject();
        condList.add(new BasicDBObject("orderStatus", 9));
        condList.add(new BasicDBObject("endTime", new BasicDBObject("$lt", new Date())));
        autoEnd.put("$and", condList);

        endList.add(forceEnd);
        endList.add(autoEnd);
        BasicDBObject objects = new BasicDBObject();
        objects.put("$or", endList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String dateStr = "2019-06-28";
            Date parse = null;
            // 解析字符串时间
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            parse = formats.parse(formats.format(date));


            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM DD HH:mm:ss z yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            DecimalFormat dFormat = new DecimalFormat("0.00");
            DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
            BasicDBObject object = new BasicDBObject();

            object.put("createTime", new BasicDBObject("$gt", parse));
            //object.put("orderStatus", 9);

            BasicDBObject timeStart = new BasicDBObject(new BasicBSONObject("createTime", new BasicBSONObject("$gt", new Date(2019 - 1900, 6 - 1, 1))));
            BasicDBObject timeEnd = new BasicDBObject(new BasicBSONObject("createTime", new BasicDBObject("$lt", MyDateUtils.strToDate("2019-05-28", "yyyy-MM-dd"))));
            DBCursor dbObjects = dbCollection.find(object);
            List<Map<String, Object>> listMap = new ArrayList<>();
            for (DBObject dbObject : dbObjects) {
                Map map = dbObject.toMap();
                Map<String, Object> maps = new HashMap<>();
                String stdCode = MapUtils.getString(map, "stdCode");
                String tradeType = MapUtils.getString(map, "tradeType");
                String createTime = MapUtils.getString(map, "createTime");
                Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
                Integer vipDays = MapUtils.getInteger(map, "vipDays");
                Integer fee = MapUtils.getInteger(map, "fee");

                maps.put("userId", MapUtils.getString(map, "userId"));
                maps.put("orderStatus", MapUtils.getString(map, "orderStatus"));
                maps.put("orderNo", MapUtils.getString(map, "orderNo"));
                maps.put("stdCode", MapUtils.getString(map, "stdCode"));
                final String format2 = sdf2.format(sdf1.parse(createTime));
                maps.put("createTime", sdf2.format(sdf1.parse(createTime)));
                if (vipDays != null && vipDays == 30 && stdCode.equals("month")) {
                    maps.put("orderType", "充值一个月");
                } else if (vipDays != null && vipDays == 90 && stdCode.equals("quarter")) {
                    maps.put("orderType", "充值一个季度");
                } else if (vipDays != null && vipDays == 180 && stdCode.equals("hlafYear")) {
                    maps.put("orderType", "充值半年");
                } else if (vipDays != null && vipDays == 365 && stdCode.equals("year")) {
                    maps.put("orderType", "充值一年");
                } else if (stdCode.equals("report_com")) {
                    maps.put("orderType", "普通用户");
                } else if (stdCode.equals("report_vip")) {
                    maps.put("orderType", "会员用户");
                }
                if (orderStatus != null && orderStatus == 9) {
                    maps.put("payStatus", "已付款");
                } else if (orderStatus != null && orderStatus == 2) {
                    maps.put("payStatus", "未付款");
                } else {
                    maps.put("payStatus", "已退款");
                }

                double iosMoney = fee;
                String iosMoneytow = iosMoney + "";
                String iosMoneyThree = MongodbCommon.fenToYuan(iosMoneytow);
                double iosMoneyFour = Double.parseDouble(iosMoneyThree);
                String format = dFormat.format(iosMoneyFour);


                maps.put("money", format);

                if (tradeType != null && tradeType.equals("APP")) {
                    maps.put("tradeType", "安卓");
                    maps.put("truePay", format);
                } else if (tradeType != null && tradeType.equals("ios app")) {
                    maps.put("tradeType", "苹果");

                    double iosMoneyFive = iosMoneyFour * 0.68;
                    String format1 = dFormat.format(iosMoneyFive);
                    maps.put("truePay", format1);
                } else if (tradeType != null && tradeType.equals("NATIVE")) {
                    maps.put("tradeType", "网页");
                    maps.put("truePay", format);
                }
                listMap.add(maps);
            }

            for (Map<String, Object> map : listMap) {
                System.out.println("map:" + map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void test21() {
        String bt = "2019-06-05";
        String et = "2019-08-05";
        String time = "2019-08-04";
        boolean b = false;
        if ((time.compareTo(bt) > 0 || time.equals(bt)) && (et.compareTo(time) > 0 || et.equals(time))) {
            b = true;
            System.out.println(b);
        }

    }

    @Test
    public void test22() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dates = sdf.parse("2019-09-18 11:08:17");
            Calendar c = Calendar.getInstance();
            c.setTime(dates);
            c.add(Calendar.DATE, 1);
            String tomorrow = sdf.format(c.getTime());
            System.out.println(tomorrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test23() {
        String todays = MyDateUtils.getTodays();
        String yesterdays = MyDateUtils.getYesterdays();
        System.out.println("今日：" + todays + ";" + "昨日：" + yesterdays);
    }

    @Test
    public void test24() {
       /* DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");

        BasicDBObject obj = new BasicDBObject();

        obj.put("userId", "c0ece7f4a6b944158646ef4626544472");
        obj.put("orderNo", "20190409031821333VEEEEE");
        obj.put("channelNo", 1001);
        obj.put("orderType", 1);
        obj.put("stdCode", "month");
        obj.put("vipDays", 30);
        obj.put("fee", 31800);
        obj.put("deviceInfo", "WEB");
        obj.put("tradeType", "APP");
        obj.put("prepayId", "wx09151821581598f9fcb3152d0850275346");
        obj.put("tradeType", 9);

        String todays = MyDateUtils.getTodayss();
        Date transitionDate = MyDateUtils.getTransitionDate(todays);
        obj.put("createTime", transitionDate);
        obj.put("wxpayParam", 0);
        obj.put("_class", "com.silita.pay.vo.OrderInfo");
        obj.put("updateTime", transitionDate);


        dbCollection.insert(obj);*/
    }

    @Test
    public void test30() {

        List<OrderInfo> all = mongoTemplate.findAll(OrderInfo.class);
        for (OrderInfo orderInfo : all) {
            System.out.println(orderInfo.getChannelNo());
        }

    }

    @Test
    public void test31() {
      /*  Criteria criteria = null;
        criteria = Criteria.where("orderStatus").is(9).and("stdCode").is("month");*/
        //criteria.

        //System.out.println("userId:"+orderInfo.getUserId()+";  订单状态:"+orderInfo.getOrderStatus()+";  支付时长："+orderInfo.getStdCode());

        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("orderStatus").is(9),
                        new Criteria().orOperator(
                                Criteria.where("stdCode").is("month"),
                                Criteria.where("stdCode").is("quarter"),
                                Criteria.where("stdCode").is("hlafYear"),
                                Criteria.where("stdCode").is("year")
                        )));
        List<OrderInfo> orderInfos = mongoTemplate.find(query, OrderInfo.class);
        Map<String, Integer> maps = new HashMap<>();
        //今日时间
        String todayDate = MyDateUtils.getTodays();
        //昨日时间
        String yesterdayDate = MyDateUtils.getYesterdays();
        int yesterdatCount = 0;
        int todayCount = 0;
        int totalCount = 0;
        Map<String, Integer> map = new HashMap<>();
        for (OrderInfo orderInfo : orderInfos) {
            String timeCycle = MyDateUtils.getTimeZone(orderInfo.getCreateTime(), "yyy-MM-dd");
            //System.out.println("orderNo:"+orderInfo.getOrderNo()+"; userId:"+orderInfo.getUserId()+"; createTime:"+timeCycle+"; Time:"+orderInfo.getCreateTime());
            totalCount++;
            if (timeCycle.equals(todayDate)) {
                todayCount++;
            } else if (timeCycle.equals(yesterdayDate)) {
                yesterdatCount++;
            }

            if (null != map.get(orderInfo.getUserId())) {
                int count = map.get(orderInfo.getUserId());
                count++;
                map.put(orderInfo.getUserId(), count);
            } else {
                map.put(orderInfo.getUserId(), 1);
            }
        }
        maps.put("payUser", map.size());
        maps.put("yesterdayPay", yesterdatCount);
        maps.put("todayPay", todayCount);
        maps.put("totalPayUser", totalCount);

        System.out.println(maps);
    }

    @Test
    public void test40() {
        Criteria criteria = new Criteria("orderStatus");
        criteria.lt(9);
        Query query = Query.query(criteria);
        List<OrderInfo> orderInfos = mongoTemplate.find(query, OrderInfo.class);
        System.out.println(orderInfos.size());
    }

    @Test
    public void test41() {
        Query query = new Query();

        // Date parsetow = MyDateUtils.getTransitionDate(tomorrowTime);

        query.addCriteria(Criteria.where("orderStatus").gte(2));

        String start = "2019-07-27";

        String end = "2019-08-01";

        Date startDate = MyDateUtils.getStringTrueDate(start);
        Date endDate = MyDateUtils.getStringTrueDate(end);


        List<OrderInfo> orderInfos = mongoTemplate.find(query, OrderInfo.class);
        for (OrderInfo orderInfo : orderInfos) {

            System.out.println("a:" + orderInfo.getOrderStatus() + "; b:" + orderInfo.getStdCode());
            /*String timeZones = MyDateUtils.getTimeZones(orderInfo.getCreateTime().toString());
            String timeZone = MyDateUtils.getTimeZone(orderInfo.getCreateTime().toString());
            Date dates = MyDateUtils.getStringTrueDate(timeZones);
            Boolean compareStartDate = MyDateUtils.getCompareStartDate(dates, startDate);
            Boolean compareEndDate = MyDateUtils.getCompareEndDate(dates, endDate);
            if(compareStartDate == true && compareEndDate == true){
                System.out.println("userId:"+orderInfo.getUserId()+"; 创建时间："+timeZone);
            }*/

            //System.out.println("userId:"+orderInfo.getUserId()+"; 创建时间："+timeZone);


        }
    }

    @Test
    public void test50() {

        Map<String, Integer> userType = mongodbService.getUserType();
        System.out.println(userType);

        Query query = new Query();

        /*Query query = new Query();
        List<OrderInfo> orderInfos = mongoTemplate.find(query, OrderInfo.class);
        for (OrderInfo orderInfo : orderInfos) {

        }*/
    }

    @Test
    public void test51() {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("orderStatus").is(9),
                        new Criteria().orOperator(
                                Criteria.where("stdCode").is("month"),
                                Criteria.where("stdCode").is("quarter"),
                                Criteria.where("stdCode").is("hlafYear"),
                                Criteria.where("stdCode").is("year")
                        )));
        List<OrderInfo> orderInfos = mongoTemplate.find(query, OrderInfo.class);
        for (OrderInfo orderInfo : orderInfos) {
//            System.out.println("{orderNo:"+orderInfo.getOrderNo()+", createTime:"+orderInfo.getCreateTime()+"}");
//            String timeZone = MyDateUtils.getTimeZone(orderInfo.getCreateTime().toString(), "yyyy-MM-dd HH:mm:ss");
//            System.out.println("{orderNo:"+orderInfo.getOrderNo()+", createTime:"+orderInfo.getCreateTime()+", createdTime:"+timeZone+"}");

        }
    }


}





package com.silita.service.mongodb;

import com.mongodb.*;
import com.silita.utils.PropertiesUtils;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.mongdbUtlis.MongoUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MongodbService {
    /**
     * 获取用户状态 ：1:付费、2及以上：续费 else 注册
     *
     * @return
     */
    public Map<String, Integer> getUserType() {
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        List<String> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter") || stdCode.equals("hlafYear"))
                    && (orderStatus == 9)) {
                listMap.add(MapUtils.getString(map, "userId"));
            }
        }

        Map<String, Integer> maps = new HashMap<>();
        for (String user : listMap) {
            if (null != maps.get(user)) {
                int count = maps.get(user);
                count++;
                maps.put(user, count);
            } else {
                maps.put(user, 1);
            }
        }

        return maps;
    }

    /**
     * 用户统计  今日/昨日  count付费
     *
     * @return
     */
    public Map<String, Integer> getUserPayCount() {
        Map<String, Integer> maps = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       /* //今日
        Date date = new Date();
        String todayDate = sdf.format(date);*/
        String todayDate = MyDateUtils.getTodays();
        //昨日
       /* Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -2);
        String yesterdayDate = sdf.format(c.getTime());*/
        String yesterdayDate = MyDateUtils.getYesterdays();
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        int yesterdatCount = 0;
        int todayCount = 0;
        int totalCount = 0;
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter") || stdCode.equals("hlafYear"))
                    && (orderStatus == 9)) {
                totalCount++;
                Date createTime = (Date) map.get("createTime");
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
        return maps;
    }

    //用户统计
    public Map<String, Object> getPastDue() {
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        DBCursor dbObjects = dbCollection.find();
        Map<String, Object> maps = new HashMap<>();
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter") || stdCode.equals("hlafYear"))
                    && (orderStatus == 9)) {
                maps.put(MapUtils.getString(map, "userId"), MapUtils.getString(map, "userId"));
            }

        }
        return maps;
    }

    /**
     * 分转元
     *
     * @param amount
     * @return
     */
    public String fenToYuan(String amount) {
        NumberFormat format = NumberFormat.getInstance();
        try {
            Number number = format.parse(amount);
            double temp = number.doubleValue() / 100.0;
            format.setGroupingUsed(false);
            // 设置返回的小数部分所允许的最大位数
            format.setMaximumFractionDigits(2);
            amount = format.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * 订单统计
     *
     * @return
     */
    public Map<String, Object> getOrderCount() {
        //今日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = MyDateUtils.getTodays();
        String yesterdayDate = MyDateUtils.getYesterdays();

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
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter") || stdCode.equals("hlafYear"))
                    && (orderStatus == 9)) {
                //Date createTime = (Date) map.get("createTime");
                //String timeCycle = sdf.format(createTime);
                String createTime = MapUtils.getString(map, "createTime");
                String timeCycle = MyDateUtils.getDatezh(createTime);
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

        String yesterdayIos = fenToYuan(one);
        String todaypaidIos = fenToYuan(two);
        String receivableIos = fenToYuan(three);
        String trueMoneyIos = fenToYuan(four);
        String zongIos = fenToYuan(five);


        //昨日已付  分转元
        String yesterdayPaid = yesterdayPaidCount + "";
        String yesterday = fenToYuan(yesterdayPaid);
        //今日已付  分转元
        String todayPaid = todayPaidCount + "";
        String today = fenToYuan(todayPaid);
        //今日应收 分转元
        String receivable = todayReceivableCount + "";
        String yin = fenToYuan(receivable);
        //今日实收 分转元
        String trueMoney = todayTrueMoneyCount + "";
        String shi = fenToYuan(trueMoney);
        //总金额  分转元
        String total = totalMoney + "";
        String zong = fenToYuan(total);
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

        return maps;
    }

    /**
     * 会员信息 订单查询
     *
     * @param param
     * @return
     */
    public List<Map<String, Object>> getTopUp(Map<String, Object> param) {
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        String userId = MapUtils.getString(param, "userId");
        BasicDBObject cond = null;
        cond = new BasicDBObject();
        cond.append("userId", userId);
        DBCursor dbObjects = dbCollection.find(cond);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            Map<String, Object> maps = new HashMap<>();
            String stdCode = MapUtils.getString(map, "stdCode");
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            Integer vipDays = MapUtils.getInteger(map, "vipDays");
            if ((stdCode.equals("year") || stdCode.equals("month") || stdCode.equals("quarter") || stdCode.equals("hlafYear"))
                    && (orderStatus == 9)) {
                maps.put("userId", MapUtils.getString(map, "userId"));
                String createTime = MapUtils.getString(map, "createTime");
                String dates = MyDateUtils.getDates(createTime);
                maps.put("vipDays",MapUtils.getInteger(map,"vipDays"));
                maps.put("created", dates);
                maps.put("vipDay", "充值" + MapUtils.getString(map, "vipDays") + "天会员");
                if (vipDays != null && vipDays == 30) {
                    maps.put("behavior", "充值一个月");
                } else if (vipDays != null && vipDays == 90) {
                    maps.put("behavior", "充值一个季度");
                } else if (vipDays != null && vipDays == 365) {
                    maps.put("behavior", "充值一年");
                } else if (vipDays != null && vipDays == 180) {
                    maps.put("behavior", "充值半年");
                }
                listMap.add(maps);
            }
        }
        return listMap;
    }

    /**
     * 订单列表
     *
     * @return
     */
    public List<Map<String, Object>> getOrderList(Map<String, Object> param) {
        //DecimalFormat dFormat = new DecimalFormat("0.00");
        DBCollection dbCollection = MongoUtils.init(PropertiesUtils.getProperty("mongodb.order.ip"), PropertiesUtils.getProperty("mongodb.order.host"), "biaodaa-pay").getDB().getCollection("order_info");
        BasicDBList endList = new BasicDBList();
        //用于  或（or）
        BasicDBList condList = new BasicDBList();
        //订单列表
        String orderType = MapUtils.getString(param, "orderType");
        //付款状态
        String payStatus = MapUtils.getString(param, "payStatus");
        //渠道
        String tradeTypes = MapUtils.getString(param, "tradeType");
        //开始时间
        String orderStart = MapUtils.getString(param, "orderStart");
        //结束时间
        String orderEnd = MapUtils.getString(param, "orderEnd");
        if (StringUtil.isNotEmpty(orderStart)) {
            Date parse = MyDateUtils.getTransitionDate(orderStart);
            BasicDBObject forceEnd = new BasicDBObject();
            forceEnd.append("createTime", new BasicDBObject("$gte", parse));
            endList.add(forceEnd);
        }


        if (StringUtil.isNotEmpty(orderEnd)) {
            String tomorrowTime = MyDateUtils.getTomorrowTime(orderEnd);
            Date parsetow = MyDateUtils.getTransitionDate(tomorrowTime);
            BasicDBObject forceEnd = new BasicDBObject();
            forceEnd.put("createTime", new BasicDBObject("$lte", parsetow));
            endList.add(forceEnd);
        }

        if (StringUtil.isNotEmpty(orderType)) {
            BasicDBObject autoEnd = new BasicDBObject();
            if (orderType.equals("充值会员") || orderType.equals("续费会员")) {
                condList.add(new BasicDBObject("stdCode", "month"));
                condList.add(new BasicDBObject("stdCode", "year"));
                condList.add(new BasicDBObject("stdCode", "quarter"));
                condList.add(new BasicDBObject("stdCode", "hlafYear"));
                autoEnd.put("$or", condList);
                endList.add(autoEnd);
            } else if (orderType.equals("综合查询")) {
                condList.add(new BasicDBObject("stdCode", "report_com"));
                condList.add(new BasicDBObject("stdCode", "report_vip"));
                autoEnd.put("$or", condList);
                endList.add(autoEnd);
            }
        }
        if (StringUtil.isNotEmpty(payStatus)) {
            BasicDBObject forceEnd = new BasicDBObject();
            if (payStatus.equals("已付款")) {
                forceEnd.put("orderStatus", 9);
                endList.add(forceEnd);
            } else if (payStatus.equals("未付款")) {
                forceEnd.put("orderStatus", 1);
                endList.add(forceEnd);
            } else if (payStatus.equals("已退款")) {
                forceEnd.put("orderStatus", 10);
                endList.add(forceEnd);
            }
        }
        if (StringUtil.isNotEmpty(tradeTypes)) {
            BasicDBObject tradeTypeObject = new BasicDBObject();
            if (tradeTypes.equals("安卓")) {
                tradeTypeObject.put("tradeType", "APP");
                endList.add(tradeTypeObject);
            } else if (tradeTypes.equals("苹果")) {
                tradeTypeObject.put("tradeType", "ios app");
                endList.add(tradeTypeObject);
            } else if (tradeTypes.equals("扫码")) {
                tradeTypeObject.put("tradeType", "NATIVE");
                endList.add(tradeTypeObject);
            } else {
                tradeTypeObject.put("tradeType", "MWEB");
                endList.add(tradeTypeObject);
            }
        }

        BasicDBObject objects = new BasicDBObject();
        if ((StringUtil.isNotEmpty(orderType) && (orderType.equals("充值会员") || orderType.equals("综合查询"))) ||
                StringUtil.isNotEmpty(payStatus) || StringUtil.isNotEmpty(tradeTypes) || StringUtil.isNotEmpty(orderStart) || StringUtil.isNotEmpty(orderEnd)) {
            objects.put("$and", endList);
        }
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("createTime", -1);
        DBCursor dbObjects = dbCollection.find(objects).sort(basicDBObject);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (DBObject dbObject : dbObjects) {
            Map map = dbObject.toMap();
            Integer orderStatus = MapUtils.getInteger(map, "orderStatus");
            if (orderStatus != null && (orderStatus == 9 || orderStatus == 1 || orderStatus == 10)) {
                Map<String, Object> maps = new HashMap<>();
                String stdCode = MapUtils.getString(map, "stdCode");
                String tradeType = MapUtils.getString(map, "tradeType");
                Integer vipDays = MapUtils.getInteger(map, "vipDays");
                Integer fee = MapUtils.getInteger(map, "fee");
                maps.put("userId", MapUtils.getString(map, "userId"));
                maps.put("orderNo", MapUtils.getString(map, "orderNo"));
                maps.put("orderStatus", orderStatus);
                String createTime = MapUtils.getString(map, "createTime");
                String dates = MyDateUtils.getDates(createTime);
                maps.put("createTime", dates);
                maps.put("stdCode", stdCode);
                maps.put("count", 1);
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
                } else {
                    maps.put("orderType", "其他");
                }
                if (orderStatus != null && orderStatus == 9) {
                    maps.put("payStatus", "已付款");
                } else if (orderStatus != null && orderStatus == 1) {
                    maps.put("payStatus", "未付款");
                } else if (orderStatus != null && orderStatus == 10) {
                    maps.put("payStatus", "已退款");
                } else {
                    maps.put("payStatus", "其他");
                }
                double iosMoney = fee;
                String iosMoneytow = iosMoney + "";
                String iosMoneyThree = fenToYuan(iosMoneytow);
                double iosMoneyFour = Double.parseDouble(iosMoneyThree);
                //String format = dFormat.format(iosMoneyFour);
                maps.put("money", iosMoneyThree);
                if (tradeType != null && tradeType.equals("APP")) {
                    maps.put("tradeType", "安卓");
                    maps.put("truePay", iosMoneyThree);
                } else if (tradeType != null && tradeType.equals("ios app")) {
                    maps.put("tradeType", "苹果");
                    iosMoneyFour = iosMoneyFour * 0.68;
                    //String format1 = dFormat.format(iosMoneyFive);
                    maps.put("truePay", iosMoneyFour);
                } else if (tradeType != null && tradeType.equals("NATIVE")) {
                    maps.put("tradeType", "扫码");
                    maps.put("truePay", iosMoneyThree);
                } else {
                    maps.put("truePay", iosMoneyThree);
                    maps.put("tradeType", "Wap");
                }
                listMap.add(maps);
            }
        }
        return listMap;
    }
}

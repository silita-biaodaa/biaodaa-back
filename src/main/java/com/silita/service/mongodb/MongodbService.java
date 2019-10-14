package com.silita.service.mongodb;


import com.silita.model.OrderInfo;
import com.silita.utils.dateUtils.MyDateUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class MongodbService {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 获取用户状态 ：统计次数为 1:付费、统计次数 2及以上：续费 没有 则是 注册
     *
     * @return
     */
    public Map<String, Integer> getUserType() {
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
        for (OrderInfo orderInfo : orderInfos) {
            if (null != maps.get(orderInfo.getUserId())) {
                int count = maps.get(orderInfo.getUserId());
                count++;
                maps.put(orderInfo.getUserId(), count);
            } else {
                maps.put(orderInfo.getUserId(), 1);
            }
        }
        return maps;
    }

    /**
     * 总付费用户
     *
     * @return
     */
    public List<Map<String, Object>> getUserPayCounts() {
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

        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            Map<String, Object> map = new HashMap<>();
            if (null != maps.get(orderInfo.getUserId())) {
                int count = maps.get(orderInfo.getUserId());
                count++;
                maps.put(orderInfo.getUserId(), count);
            } else {
                maps.put(orderInfo.getUserId(), 1);
                map.put("userId", orderInfo.getUserId());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 用户统计  统计今日/昨日  付费用户  及   总付费用户 和 付费用户
     *
     * @return
     */
    public Map<String, Integer> getUserPayCount() {
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
        Map<String, Integer> map = new HashMap<>();
        for (OrderInfo orderInfo : orderInfos) {
            String timeCycle = MyDateUtils.getTimeZone(orderInfo.getCreateTime(),"yyyy-MM-dd");
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
        maps.put("yesterdayPay", yesterdatCount);
        maps.put("todayPay", todayCount);
        maps.put("totalPayUser", map.size());
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
        String todayDate = MyDateUtils.getTodays();
        String yesterdayDate = MyDateUtils.getYesterdays();
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
        for (OrderInfo orderInfo : orderInfos) {
            String stdCode = orderInfo.getStdCode();
            String tradeType = orderInfo.getTradeType();
            Integer orderStatus = orderInfo.getOrderStatus();
            if (StringUtil.isNotEmpty(stdCode) && StringUtil.isNotEmpty(tradeType) && orderStatus != null) {
                String timeCycle = MyDateUtils.getTimeZone(orderInfo.getCreateTime(),"yyyy-MM-dd");
                Integer fee = orderInfo.getFee();
                if (timeCycle.equals(yesterdayDate)) {
                    //昨日订单
                    yesterdayOrderCount++;
                    //昨日已付
                    if (fee != null && fee > 0) {
                        if (StringUtil.isNotEmpty(tradeType)) {
                            if (tradeType.equals("ios app")) {
                                yesterdayPaidCountIos = yesterdayPaidCountIos + fee;
                            } else {
                                yesterdayPaidCount = yesterdayPaidCount + fee;
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
        String three = todayReceivableCountIos + "";
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
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("orderStatus").is(9), new Criteria().and("userId").is(MapUtils.getString(param, "userId")),
                        new Criteria().orOperator(
                                Criteria.where("stdCode").is("month"),
                                Criteria.where("stdCode").is("quarter"),
                                Criteria.where("stdCode").is("hlafYear"),
                                Criteria.where("stdCode").is("year")
                        )));
        List<OrderInfo> orderInfos = mongoTemplate.find(query, OrderInfo.class);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            Map<String, Object> maps = new HashMap<>();
            String stdCode = orderInfo.getStdCode();
            String tradeType = orderInfo.getTradeType();
            Integer orderStatus = orderInfo.getOrderStatus();
            if (StringUtil.isNotEmpty(stdCode) && StringUtil.isNotEmpty(tradeType) && orderStatus != null) {
                String dates = MyDateUtils.getTimeZone(orderInfo.getCreateTime(),"yyyy-MM-dd HH:mm:ss");
                Integer vipDays = orderInfo.getVipDays();
                maps.put("orderNo", orderInfo.getOrderNo());
                maps.put("userId", orderInfo.getUserId());
                maps.put("created", dates);
                maps.put("vipDay", "充值" + orderInfo.getVipDays() + "天会员");
                maps.put("vipDays",orderInfo.getVipDays());
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
    public List<Map<String, Object>> getOrderList(Map<String,Object> param) {
        Query query = new Query();
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

        //
        Criteria criteria = new Criteria();


        if (StringUtil.isNotEmpty(orderStart) && StringUtil.isNotEmpty(orderEnd)) {
            Date parse = MyDateUtils.getTransitionDate(orderStart);
            String tomorrowTime = MyDateUtils.getTomorrowTime(orderEnd);
            Date parsetow = MyDateUtils.getTransitionDate(tomorrowTime);
            criteria.andOperator(Criteria.where("createTime").gte(parse), criteria.where("createTime").lte(parsetow));
        }
        if (StringUtil.isNotEmpty(orderType)) {
            if (orderType.equals("充值会员") || orderType.equals("续费会员")) {
                criteria.orOperator(
                        Criteria.where("stdCode").is("month"),
                        Criteria.where("stdCode").is("year"),
                        Criteria.where("stdCode").is("quarter"),
                        Criteria.where("stdCode").is("hlafYear")
                );
            } else if (orderType.equals("综合查询")) {
                criteria.orOperator(
                        Criteria.where("stdCode").is("report_com"),
                        Criteria.where("stdCode").is("report_vip")
                );
            }
        }
        if (StringUtil.isNotEmpty(payStatus)) {
            if (payStatus.equals("已付款")) {
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("orderStatus").is(9);
                } else {
                    criteria.orOperator(Criteria.where("orderStatus").is(9));
                }
            } else if (payStatus.equals("未付款")) {
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("orderStatus").is(1);
                } else {
                    criteria.orOperator(Criteria.where("orderStatus").is(1));
                }

            } else if (payStatus.equals("已退款")) {
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("orderStatus").is(10);
                } else {
                    criteria.orOperator(Criteria.where("orderStatus").is(10));
                }
            }else if(payStatus.equals("已失效")){
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("orderStatus").is(2);
                } else {
                    criteria.orOperator(Criteria.where("orderStatus").is(2));
                }
            }
        }
        if (StringUtil.isNotEmpty(tradeTypes)) {
            if (tradeTypes.equals("安卓")) {
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("tradeType").is("APP");
                } else {
                    criteria.andOperator(Criteria.where("tradeType").is("APP"),
                            Criteria.where("tradeType").ne("ios app"),
                            Criteria.where("tradeType").ne("NATIVE"),
                            Criteria.where("tradeType").ne("MWEB"));
                }
            } else if (tradeTypes.equals("苹果")) {
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("tradeType").is("ios app");
                } else {
                    criteria.andOperator(Criteria.where("tradeType").is("ios app"),
                            Criteria.where("tradeType").ne("APP"),
                            Criteria.where("tradeType").ne("NATIVE"),
                            Criteria.where("tradeType").ne("MWEB"));
                }

            } else if (tradeTypes.equals("扫码")) {
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("tradeType").is("NATIVE");
                } else {
                    criteria.andOperator(Criteria.where("tradeType").is("NATIVE"),
                            Criteria.where("tradeType").ne("APP"),
                            Criteria.where("tradeType").ne("ios app"),
                            Criteria.where("tradeType").ne("MWEB"));
                }
            } else {
                if ((orderType.equals("充值会员") || orderType.equals("综合查询")) || (StringUtil.isNotEmpty(orderStart) &&
                        StringUtil.isNotEmpty(orderEnd))) {
                    criteria.and("tradeType").is("MWEB");
                } else {
                    criteria.andOperator(
                            Criteria.where("tradeType").is("MWEB"),
                            Criteria.where("tradeType").ne("APP"),
                            Criteria.where("tradeType").ne("ios app"),
                            Criteria.where("tradeType").ne("NATIVE"));
                }

            }
        }
        if ((StringUtil.isNotEmpty(orderType) && (orderType.equals("充值会员") || orderType.equals("综合查询"))) ||
                StringUtil.isNotEmpty(payStatus) || StringUtil.isNotEmpty(tradeTypes) || StringUtil.isNotEmpty(orderStart) || StringUtil.isNotEmpty(orderEnd)) {
            query.addCriteria(criteria);
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        List<OrderInfo> orderInfos = mongoTemplate.find(query, OrderInfo.class);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {
            Integer orderStatus = orderInfo.getOrderStatus();
            String tradeType = orderInfo.getTradeType();
            String stdCode = orderInfo.getStdCode();
            if (null != orderStatus && StringUtil.isNotEmpty(tradeType) && StringUtil.isNotEmpty(stdCode)) {
                if (orderStatus != null && (orderStatus == 9 || orderStatus == 1 || orderStatus == 10 || orderStatus == 2)
                        && (tradeType.equals("APP") || tradeType.equals("ios app") || tradeType.equals("NATIVE")
                        || tradeType.equals("MWEB")) && (stdCode.equals("month") || stdCode.equals("year") || stdCode.equals("quarter")
                        || stdCode.equals("hlafYear") || stdCode.equals("report_com") || stdCode.equals("report_vip"))) {
                    Map<String, Object> maps = new HashMap<>();
                    Integer vipDays = orderInfo.getVipDays();
                    Integer fee = orderInfo.getFee();
                    maps.put("userId", orderInfo.getUserId());
                    maps.put("orderNo", orderInfo.getOrderNo());
                    maps.put("orderStatus", orderStatus);
                    String dates = MyDateUtils.getTimeZone(orderInfo.getCreateTime(),"yyyy-MM-dd HH:mm:ss");
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
                    }
                    if (orderStatus != null && orderStatus == 9) {
                        maps.put("payStatus", "已付款");
                    } else if (orderStatus != null && orderStatus == 1) {
                        maps.put("payStatus", "未付款");
                    } else if (orderStatus != null && orderStatus == 10) {
                        maps.put("payStatus", "已退款");
                    }else if(orderStatus != null && orderStatus == 2){
                        maps.put("payStatus", "已失效");
                    }
                    double iosMoney = fee;
                    String iosMoneytow = iosMoney + "";
                    String iosMoneyThree = fenToYuan(iosMoneytow);
                    double iosMoneyFour = Double.parseDouble(iosMoneyThree);
                    maps.put("money", iosMoneyThree);
                    if (tradeType != null && tradeType.equals("APP")) {
                        maps.put("tradeType", "安卓");
                        maps.put("truePay", iosMoneyThree);
                    } else if (tradeType != null && tradeType.equals("ios app")) {
                        maps.put("tradeType", "苹果");
                        iosMoneyFour = iosMoneyFour * 0.68;
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
        }
        return listMap;
    }

    /**
     * 判空
     */
    public void isNull(Map<String,Object> param){
        String orderType = MapUtils.getString(param, "orderType");
        //付款状态
        String payStatus = MapUtils.getString(param, "payStatus");
        //渠道
        String tradeTypes = MapUtils.getString(param, "tradeType");
        //开始时间
        String orderStart = MapUtils.getString(param, "orderStart");
        //结束时间
        String orderEnd = MapUtils.getString(param, "orderEnd");
        String createStartDate = MapUtils.getString(param, "createStartDate");
        String createdEndData = MapUtils.getString(param, "createdEndData");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String phoneNo = MapUtils.getString(param, "phoneNo");
        String phone = MapUtils.getString(param, "phone");
        String state = MapUtils.getString(param, "state");
        String type = MapUtils.getString(param, "type");

        if(StringUtil.isEmpty(createStartDate)){
            param.put("createStartDate","");
        }
        if(StringUtil.isEmpty(createdEndData)){
            param.put("createdEndData","");
        }
        if(StringUtil.isEmpty(startDate)){
            param.put("startDate","");
        }
        if(StringUtil.isEmpty(endDate)){
            param.put("endDate","");
        }
        if(StringUtil.isEmpty(phoneNo)){
            param.put("phoneNo","");
        }
        if(StringUtil.isEmpty(orderType)){
            param.put("orderType","");
        }
        if(StringUtil.isEmpty(payStatus)){
            param.put("payStatus","");
        }
        if(StringUtil.isEmpty(tradeTypes)){
            param.put("tradeTypes","");
        }
        if(StringUtil.isEmpty(orderStart)){
            param.put("orderStart","");
        }
        if(StringUtil.isEmpty(orderEnd)){
            param.put("orderEnd","");
        }
        if(StringUtil.isEmpty(type)){
            param.put("type","");
        }
        if(StringUtil.isEmpty(state)){
            param.put("state","");
        }
        if(StringUtil.isEmpty(phone)){
            param.put("phone","");
        }

    }


}

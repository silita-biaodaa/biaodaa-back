package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.common.RegionCommon;
import com.silita.dao.*;
import com.silita.model.*;
import com.silita.service.INoticeService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.stringUtils.WordProcessingUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("noticeService")
public class NoticeServiceImpl extends AbstractService implements INoticeService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    TwfDictMapper twfDictMapper;

    @Autowired
    TbNtChangeMapper tbNtChangeMapper;
    @Autowired
    TbNtMianMapper tbNtMianMapper;
    @Autowired
    TbNtRecycleHunanMapper recycleHunanMapper;
    @Autowired
    TbNtTextHunanMapper tbNtTextHunanMapper;
    @Autowired
    TbNtAssociateGpMapper tbNtAssociateGpMapper;
    @Autowired
    TbNtSiteMapper tbNtSiteMapper;
    @Autowired
    TbSiteCountMapper tbSiteCountMapper;

    @Override
    public Map<String, Object> addNotice(TbNtMian mian) {
        if (null == mian) {
            return new HashMap<>();
        }
        Map<String, Object> resultMap = new HashMap<>();
        mian.setTableName(DataHandlingUtil.SplicingTable(mian.getClass(), mian.getSource()));
        mian.setNtStatus("1");
        mian.setIsEnable("1");
        mian.setPkid(DataHandlingUtil.getUUID());
        int cunt = tbNtMianMapper.queryNtMainCount(mian);
        if (cunt > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        int count = tbNtMianMapper.insertNtMian(mian);
        if (count > 0) {
            TbNtText tbNtText = new TbNtText();
            tbNtText.setPkid(DataHandlingUtil.getUUID());
            tbNtText.setTableName(DataHandlingUtil.SplicingTable(tbNtText.getClass(), mian.getSource()));
            tbNtText.setContent(mian.getContent());
            tbNtText.setNtId(mian.getPkid());
            tbNtTextHunanMapper.inertNtText(tbNtText);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void updateNtText(TbNtText tbNtText) {
        tbNtText.setTableName(DataHandlingUtil.SplicingTable(TbNtText.class, tbNtText.getSource()));
        String compress = WordProcessingUtil.getTextFromHtml(tbNtText.getContent());
        tbNtText.setCompress(compress);
        tbNtTextHunanMapper.updateNtText(tbNtText);
    }

    @Override
    public void updateNtMainStatus(TbNtMian tbNtMian) {
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
        //更新公告状态
        tbNtMianMapper.updateCategoryAndStatusByPkId(tbNtMian);
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(tbNtAssociateGp.getClass(), tbNtMian.getSource()));
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setRelType(tbNtMian.getNtCategory());
        //更新关联状态
        tbNtAssociateGpMapper.updateRelTypeByBtId(tbNtAssociateGp);
    }

    @Override
    public void delNtMainInfo(TbNtMian main, String username) {
        main.setTableName(DataHandlingUtil.SplicingTable(main.getClass(), main.getSource()));
        main.setUpdateBy(username);
        TbNtRecycle recycle = new TbNtRecycle();
        recycle.setPkid(DataHandlingUtil.getUUID());
        recycle.setNtId(main.getPkid());
        recycle.setSource(main.getSource());
        recycle.setCreateBy(username);
        recycle.setDelType("4");
        //将公告数据填进回收站
        if ("1".equals(main.getNtCategory())) {
            recycleHunanMapper.inertRecycleForNtMain(recycle);
        } else {
            recycleHunanMapper.inertRecycleForBids(recycle);
        }
        tbNtMianMapper.deleteNtMainByPkId(main);
    }

    @Override
    public Map<String, Object> listNtMain(TbNtMian tbNtMian) {
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
        //去除标题中的特殊字符
        tbNtMian.setTitle(tbNtMian.getTitle().replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "%"));
        Map result = new HashMap<String, Object>();
        result.put("areaCode", sysAreaMapper.getPkIdByAreaCode(tbNtMian.getSource()));
        result.put("datas", tbNtMianMapper.listNtMain(tbNtMian));
        result.put("total", tbNtMianMapper.countNtMian(tbNtMian));
        return super.handlePageCount(result, tbNtMian);
    }

    @Override
    public Map<String, Object> listBulletinStatus() {
        return DataHandlingUtil.getBulletinStatus();
    }

    @Override
    @Cacheable(value = "listFixedEditDataCache")
    public Map<String, Object> listFixedEditData() {
        Map result = new HashMap<String, Object>();
        //开标人员
        result.put("bidOpeningPersonnel", twfDictMapper.listTwfDictNameByType(5));
        //项目类型
        result.put("projectType", twfDictMapper.listTwfDictNameByType(4));
        //招标类型
        result.put("biddingType", twfDictMapper.listTwfDictNameByType(1));
        //平台备案要求
        result.put("filingRequirements", twfDictMapper.listTwfDictNameByType(6));
        //招标状态
        result.put("biddingStatus", twfDictMapper.listTwfDictNameByType(2));
        //公告类目
        result.put("categoryType", twfDictMapper.listTwfDictNameByType(3));
        return result;
    }

    @Override
    public List<Map<String, Object>> listDicCommonNameByType(DicCommon dicCommon) {
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pbmode");
        return dicCommonMapper.listDicCommonNameByType(dicCommon);
    }

    @Override
    public List<Map<String, Object>> listSysAreaByParentId(SysArea sysArea) {
        return sysAreaMapper.listSysAreaByParentId(sysArea.getAreaParentId());
    }

    @Override
    public void saveTbNtChange(TbNtChange tbNtChange) {
//        Integer count = tbNtChangeMapper.countTbNtChangeByNtIdAndNtEditIdAndfieldName(tbNtChange);
//        if(count == 0) {
        tbNtChange.setPkid(DataHandlingUtil.getUUID());
        tbNtChangeMapper.insertTbNtChange(tbNtChange);
//        } else {
//            tbNtChangeMapper.updateTbNtChangeByNtIdAndNtEditId(tbNtChange);
//        }

       /* Map map = new HashMap<String, Object>(5);
        map.put("tableName", DataHandlingUtil.SplicingTable(TbNtTenders.class, tbNtChange.getSource()));
        //把下划线命名转为驼峰命名
        map.put("fieldName", com.silita.utils.stringUtils.StringUtils.HumpToUnderline(tbNtChange.getFieldName()));
        map.put("fieldValue", tbNtChange.getFieldValue());
        map.put("pkid", tbNtChange.getNtEditId());
        //添加变更信息时，同时标段表对应字段一起改变
        tbNtTendersMapper.updateChangeFieldValue(map);*/
    }


    public static void main(String[] args) {
        Map<String, String> regionSource = RegionCommon.regionSource;
        for (String s : regionSource.keySet()) {
            System.out.println(s);
        }
    }


    /**
     * 获取公告统计
     *
     * @return
     */
    @Override
    public Map<String, Object> getNoticeCount() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("yesterday", MyDateUtils.getYesterdays());
            param.put("today", MyDateUtils.getTodays());
            Map<String, String> regionSource = RegionCommon.regionSource;
            int yesterdayCounts = 0;
            int todayCounts = 0;
            int totalCounts = 0;
            for (String source : regionSource.keySet()) {
                param.put("source", source);
                Map<String, Object> map = tbNtMianMapper.queryNoticeCount(param);
                Integer yesterdayCount = MapUtils.getInteger(map, "yesterdayCount");
                yesterdayCounts = yesterdayCounts + yesterdayCount;
                Integer todayCount = MapUtils.getInteger(map, "todayCount");
                todayCounts = todayCounts + todayCount;
                Integer totalCount = MapUtils.getInteger(map, "totalCount");
                totalCounts = totalCounts + totalCount;
            }
            resultMap.put("yesterdayCounts", yesterdayCounts);
            resultMap.put("todayCounts", todayCounts);
            resultMap.put("totalCounts", totalCounts);
        } catch (Exception e) {
            logger.error("获取公告统计", e);
        }
        return resultMap;
    }


    /**
     * 公告站点统计 添加进siteCount表中
     *
     * @param param
     * @return
     */
    @Override
    public void insertSiteCounts(Map<String, Object> param) {
        //Map<String, Object> resultMap = new HashMap<>();
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01");//定义起始日期
            Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-10-16");//定义结束日期
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(d1);//设置日期起始时间
            while (dd.getTime().before(d2)) {//判断是否到结束日期

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd");

                String str = sdf.format(dd.getTime());

                System.out.println(str);//输出日期结果
                param.put("startDate", str);
                param.put("endDate", str);
                param.put("pubDate", str);
                List<Map<String, Object>> list1 = tbNtSiteMapper.querySiteUtl(param);//获取站点url
                Map<String, String> regionSource = RegionCommon.regionSource; //获取地区
                for (Map<String, Object> map : list1) {
                    String name = MapUtils.getString(map, "name");
                    for (String s : regionSource.keySet()) {
                        param.put("source", s);
                        siteCounts(map, param, name);
                    }
                }
                siteCounts(list1);
                //resultMap.put("list", list1);
                param.put("list1", list1);
                tbSiteCountMapper.insertSiteCount(param);
                dd.add(Calendar.DAY_OF_YEAR, 1);//进行当前日期日天数加1
            }

        } catch (Exception e) {
            logger.error("公告站点统计异常", e);
        }
    }

    /**
     * 公告站点统计
     *
     * @param param
     * @return
     */

    @Override
    public Map<String, Object> getCount(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int count = 0;
            String source = MapUtils.getString(param, "source");
            if (StringUtil.isEmpty(source)) {
                List<Map<String, Object>> list1 = tbNtSiteMapper.querySiteUtl(param);
                Map<String, String> regionSource = RegionCommon.regionSource;
                for (Map<String, Object> map : list1) {
                    String name = MapUtils.getString(map, "name");
                    for (String s : regionSource.keySet()) {
                        param.put("source", s);
                        count = siteCount(map, param, count, name);
                    }
                }
                siteCount(list1);
                resultMap.put("sumTotal", count);
                resultMap.put("list", list1);
                return resultMap;
            }
            param.put("sourced", RegionCommon.regionSource.get(source));
            List<Map<String, Object>> list1 = tbNtSiteMapper.querySiteUtl(param);
            for (Map<String, Object> map : list1) {
                String name = MapUtils.getString(map, "name");
                count = siteCount(map, param, count, name);
            }
            siteCount(list1);
            resultMap.put("sumTotal", count);
            resultMap.put("list", list1);
        } catch (Exception e) {
            logger.error("公告站点统计异常", e);
        }
        return resultMap;
    }

    @Override
    public void insertSite() {
        Map<String, Object> param = new HashMap<>();
        List<String> lists = new ArrayList<>();
//        lists.add("beij");
//        lists.add("fuj");
//        lists.add("gans");
//        lists.add("guangd");
//        lists.add("guangx");
//        lists.add("guiz");
//        lists.add("hain");
//        lists.add("hebei");
//        lists.add("henan");
//        lists.add("jiangs");
//        lists.add("jiangx");
//        lists.add("jil");
//        lists.add("liaon");
//        lists.add("neimg");
//        lists.add("ningx");
//        lists.add("qingh");
//        lists.add("sanx");
//        lists.add("shand");
//        lists.add("shangh");
//        lists.add("shanxi");
//        lists.add("sichuan");
//        lists.add("tianj");
//        lists.add("xinjiang");
//        lists.add("xizang");
//        lists.add("yunn");
//        lists.add("zhej");
//        lists.add("chongq");
//        lists.add("hubei");
        lists.add("heilj");
        lists.add("hunan");
        for (String listsoutce : lists) {
            String source = listsoutce;
            param.put("source", source);
            param.put("sources", source);
            List<String> list = tbNtMianMapper.queryNoticeSitePubDate(param);//获取地区时间
            for (String s : list) {
                param.put("startDate", s);
                param.put("pubDate", s);
                param.put("sourced", RegionCommon.regionSource.get(source));
                List<Map<String, Object>> list1 = tbNtSiteMapper.querySiteUtl(param);
                for (Map<String, Object> map : list1) {
                    String name = MapUtils.getString(map, "name");
                    siteCounts(map, param, name);
                }
                siteCount(list1);
                param.put("list1", list1);
                tbSiteCountMapper.insertSiteCount(param);
            }
        }
    }

    public void siteCounts(Map<String, Object> map, Map<String, Object> param, String name) {

        List<Map<String, Object>> list = tbNtMianMapper.querySiteNoticeCounts(param);
        if (null != list && list.size() > 0) {
            for (Map<String, Object> stringObjectMap : list) {
                String srcSite = MapUtils.getString(stringObjectMap, "srcSite");
                if (name.equals(srcSite)) {
                    Integer siteCount = MapUtils.getInteger(stringObjectMap, "siteCount");
                    map.put("siteCount", siteCount);
                }
            }
        }

    }

    public Integer siteCount(Map<String, Object> map, Map<String, Object> param, int count, String name) {

        List<Map<String, Object>> list = tbNtMianMapper.querySiteNoticeCount(param);
        for (Map<String, Object> stringObjectMap : list) {
            String srcSite = MapUtils.getString(stringObjectMap, "srcSite");
            if (name.equals(srcSite)) {
                Integer siteCount = MapUtils.getInteger(stringObjectMap, "siteCount");
                map.put("siteCount", siteCount);

                count = count + siteCount;

            }
        }
        return count;
    }

    public void siteCounts(List<Map<String, Object>> list1) {
        for (Map<String, Object> map : list1) {
            Integer siteCount = MapUtils.getInteger(map, "siteCount");
            if (null == siteCount) {
                map.put("siteCount", 0);
            }
        }
    }

    public void siteCount(List<Map<String, Object>> list1) {
        for (Map<String, Object> map : list1) {
            Integer siteCount = MapUtils.getInteger(map, "siteCount");
            if (null == siteCount) {
                map.put("siteCount", 0);
            }
        }
    }
}

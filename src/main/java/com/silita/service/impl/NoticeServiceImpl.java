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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if("1".equals(main.getNtCategory())) {
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

}

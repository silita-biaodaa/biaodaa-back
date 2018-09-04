package com.silita.service.impl;

import com.silita.dao.*;
import com.silita.model.*;
import com.silita.service.INoticeZhaoBiaoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:52
 */
@Service("noticeZhaoBiaoService")
public class NoticeZhaoBiaoServiceImpl extends AbstractService implements INoticeZhaoBiaoService {

    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    TwfDictMapper twfDictMapper;
    @Autowired
    TbNtMianMapper tbNtMianMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    TbNtTendersMapper tbNtTendersMapper;
    @Autowired
    TbNtChangeMapper tbNtChangeMapper;
    @Autowired
    SysFilesMapper sysFilesMapper;
    @Autowired
    TbNtRecycleHunanMapper recycleHunanMapper;

    @Override
    @Cacheable(value = "TwfDictNameCache")
    public Map<String, Object> listFixedEditData() {
        Map result = new HashMap<String, Object>();
        result.put("bidOpeningPersonnel", twfDictMapper.listTwfDictNameByType(3));
        result.put("projectType", twfDictMapper.listTwfDictNameByType(4));
        result.put("biddingType", twfDictMapper.listTwfDictNameByType(5));
        result.put("filingRequirements", twfDictMapper.listTwfDictNameByType(6));
        result.put("biddingStatus", twfDictMapper.listTwfDictNameByType(7));
        return result;
    }

    @Override
    public Map<String, Object> listBulletinStatus() {
        return DataHandlingUtil.getBulletinStatus();
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
    public HSSFWorkbook listTendersDetail(TbNtMian tbNtMian) {
        int indexRow = 0;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        Row row = sheet.createRow(indexRow++);
        String[] headers = {
                "项目名称", "公告状态", "标段", "公式日期","项目地区",
                "项目县市", "招标类型", "项目类型", "资质","招标控制价",
                "项目金额", "项目工期", "评标办法", "保证金金额","保证金截至时间",
                "报名截止时间", "报名地点", "资格审查截止时间", "投标截止时间",
                "开标地点", "平台备案要求", "招标状态", "开标人员要求", "变更信息"
        };
        //创建标题
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
        List<LinkedHashMap<String, Object>> details = tbNtMianMapper.listTendersDetail(tbNtMian);
        //一行数据
        for (int i = 0; i < details.size(); i++) {
            int indexCell = 0;
            row = sheet.createRow(indexRow++);
            Map<String, Object> detail = details.get(i);
            //一列数据
            for (Map.Entry<String, Object> entry : detail.entrySet()) {
                if(!entry.getKey().equals("url")) {
                    row.createCell(indexCell++).setCellValue(String.valueOf(entry.getValue()));
                }
            }
            //标题设置超链接
            link.setAddress(String.valueOf(detail.get("url")));
            row.getCell(0).setHyperlink(link);
            System.out.println( row.getCell(0).getHyperlink().getAddress());
        }
        return wb;
    }

    @Override
    public void updateNtMainStatus(TbNtMian tbNtMian) {
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
        tbNtMianMapper.updateNtMainCategoryAndStatusByPkId(tbNtMian);
    }


    @Override
    public String insertNtTenders(TbNtTenders tbNtTenders) {
        String msg = "";
        //更新招标主表状态
        TbNtMian tbNtMian = new TbNtMian();
        tbNtMian.setPkid(tbNtTenders.getNtId());
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtTenders.getSource()));
        tbNtMian.setBinessType(tbNtTenders.getBinessType());
        tbNtMian.setNtCategory(tbNtTenders.getNtCategory());
        tbNtMian.setNtType(tbNtTenders.getNtType());
        tbNtMian.setControllSum(tbNtTenders.getControllSum());
        tbNtMian.setProSum(tbNtTenders.getProSum());
        tbNtMian.setProDuration(tbNtTenders.getProDuration());
        tbNtMian.setPbMode(tbNtTenders.getPbMode());
        tbNtMian.setTitle(tbNtTenders.getTitle());
        tbNtMian.setPubDate(tbNtTenders.getPubDate());
        tbNtMian.setUrl(tbNtTenders.getUrl());
        tbNtMianMapper.updateNtMainByPkId(tbNtMian);
        //添加标段
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        Integer count = tbNtTendersMapper.countNtTendersByNtIdAndSegment(tbNtTenders);
        if (count == 0) {
            tbNtTenders.setPkid(DataHandlingUtil.getUUID());
            tbNtTendersMapper.insertNtTenders(tbNtTenders);
            msg = "添加标段成功！";
        }
        return msg;
    }

    @Override
    public void updateNtTenders(TbNtTenders tbNtTenders) {
        //更新招标主表状态
        TbNtMian tbNtMian = new TbNtMian();
        tbNtMian.setPkid(tbNtTenders.getNtId());
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtTenders.getSource()));
        tbNtMian.setBinessType(tbNtTenders.getBinessType());
        tbNtMian.setNtCategory(tbNtTenders.getNtCategory());
        tbNtMian.setNtType(tbNtTenders.getNtType());
        tbNtMian.setControllSum(tbNtTenders.getControllSum());
        tbNtMian.setProSum(tbNtTenders.getProSum());
        tbNtMian.setProDuration(tbNtTenders.getProDuration());
        tbNtMian.setPbMode(tbNtTenders.getPbMode());
        tbNtMian.setTitle(tbNtTenders.getTitle());
        tbNtMian.setPubDate(tbNtTenders.getPubDate());
        tbNtMian.setUrl(tbNtTenders.getUrl());
        tbNtMianMapper.updateNtMainByPkId(tbNtMian);
        //更新标段
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        tbNtTendersMapper.updateNtTendersByPkId(tbNtTenders);
    }

    @Override
    public List<TbNtTenders> listNtTenders(TbNtTenders tbNtTenders) {
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        return tbNtTendersMapper.listNtTendersByNtId(tbNtTenders);
    }

    @Override
    public void deleteNtTendersByPkId(TbNtTenders tbNtTenders) {
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        tbNtTendersMapper.deleteNtTendersByPkId(tbNtTenders);
    }


    @Override
    public void insertTbNtChange(TbNtChange tbNtChange) {
        tbNtChange.setPkid(DataHandlingUtil.getUUID());
        tbNtChangeMapper.insertTbNtChange(tbNtChange);
    }

    @Override
    public void updateTbNtChangeByPkId(TbNtChange tbNtChange) {
        tbNtChangeMapper.updateTbNtChangeByPkId(tbNtChange);
    }


    @Override
    public List<SysFiles> listZhaoBiaoFilesByPkid(SysFiles sysFiles) {
        return sysFilesMapper.listSysFilesByBizId(sysFiles);
    }

    @Override
    public void insertZhaoBiaoFiles(SysFiles sysFiles) {
        sysFiles.setPkid(DataHandlingUtil.getUUID());
        sysFilesMapper.insertSysFiles(sysFiles);
    }

    @Override
    public void deleteZhaoBiaoFilesByPkid(SysFiles sysFiles) {
        sysFilesMapper.deleteSysFilesByPkid(sysFiles);
    }

    @Override
    public void delNtMainInfo(TbNtMian main, String username) {
        main.setTableName(DataHandlingUtil.SplicingTable(main.getClass(),main.getSource()));
        main.setUpdateBy(username);
        TbNtRecycle recycle = new TbNtRecycle();
        recycle.setPkid(DataHandlingUtil.getUUID());
        recycle.setNtId(main.getPkid());
        recycle.setSource(main.getSource());
        recycle.setCreateBy(username);
        recycle.setDelType("4");
        //将公告数据填进回收站
        recycleHunanMapper.inertRecycleForNtMain(recycle);
        tbNtMianMapper.deleteNtMainByPkId(main);
    }

}

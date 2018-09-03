package com.silita.service.impl;

import com.silita.dao.*;
import com.silita.model.DicCommon;
import com.silita.model.SysArea;
import com.silita.model.TbNtMian;
import com.silita.model.TbNtTenders;
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

    @Override
    @Cacheable(value = "TwfDictNameCache")
    public Map<String, String> listFixedEditData() {
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
        Map result = new HashMap<String, Object>();
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
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        tbNtTendersMapper.updateNtTendersByPkId(tbNtTenders);
    }

    @Override
    public List<TbNtTenders> listNtTenders(TbNtTenders tbNtTenders) {
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        return tbNtTendersMapper.listNtTendersByNtId(tbNtTenders);
    }

}

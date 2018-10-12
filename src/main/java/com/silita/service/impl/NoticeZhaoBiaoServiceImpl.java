package com.silita.service.impl;

import com.silita.dao.*;
import com.silita.model.*;
import com.silita.service.INoticeZhaoBiaoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.WordProcessingUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:52
 */
@Service("noticeZhaoBiaoService")
public class NoticeZhaoBiaoServiceImpl extends AbstractService implements INoticeZhaoBiaoService {

    Logger logger = LoggerFactory.getLogger(NoticeZhaoBiaoServiceImpl.class);

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
    @Autowired
    TbNtAssociateGpMapper tbNtAssociateGpMapper;
    @Autowired
    TbNtTextHunanMapper tbNtTextHunanMapper;
    @Autowired
    TbNtRegexGroupMapper tbNtRegexGroupMapper;
    @Autowired
    TbNtQuaGroupMapper tbNtQuaGroupMapper;
    @Autowired
    TbNtRegexQuaMapper tbNtRegexQuaMapper;

    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    @Cacheable(value = "TwfDictNameCache")
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
        HSSFRow row = sheet.createRow(indexRow++);
        String[] headers = {
                "项目名称", "公告状态", "标段", "公示日期", "项目地区",
                "项目县市", "招标类型", "项目类型", "资质", "招标控制价",
                "项目金额", "项目工期", "评标办法", "保证金金额", "保证金截至时间",
                "报名截止时间", "报名地点", "资格审查截止时间", "投标截止时间",
                "开标地点", "平台备案要求", "招标状态", "开标人员要求"
        };
        //创建标题
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        List<LinkedHashMap<String, Object>> details = tbNtMianMapper.listTendersDetail(tbNtMian);
        //一行数据
        for (int i = 0; i < details.size(); i++) {
            int indexCell = 0;
            row = sheet.createRow(indexRow++);
            Map<String, Object> detail = details.get(i);
            //获取招标编辑明细变更信息
            TbNtChange tbNtChange = new TbNtChange();
            tbNtChange.setNtId(String.valueOf(detail.get("nt_id")));
            tbNtChange.setNtEditId(String.valueOf(detail.get("pkid")));
            List<Map<String, Object>> changeFields = tbNtChangeMapper.listFieldNameAndFieldValueByNtEditId(tbNtChange);
            Map<String, String> changeField = new HashMap();
            if (changeFields != null && changeFields.size() > 0) {
                TwfDict twfDict = new TwfDict();
                //去除旧的变更信息
                for (Map<String, Object> map : changeFields) {
                    String tempKey = com.silita.utils.stringUtils.StringUtils.HumpToUnderline(String.valueOf(map.get("field_name")));
                    String tempValue = String.valueOf(map.get("field_value"));
                    if ("biness_type".equals(tempKey)) {
                        twfDict.setCode(tempValue);
                        twfDict.setType(1);
                        tempValue = twfDictMapper.getNameByCodeAndType(twfDict);
                    } else if ("pro_type".equals(tempKey)) {
                        twfDict.setCode(tempValue);
                        twfDict.setType(4);
                        tempValue = twfDictMapper.getNameByCodeAndType(twfDict);
                    } else if ("filing_pfm".equals(tempKey)) {
                        twfDict.setCode(tempValue);
                        twfDict.setType(6);
                        tempValue = twfDictMapper.getNameByCodeAndType(twfDict);
                    } else if ("nt_type".equals(tempKey)) {
                        twfDict.setCode(tempValue);
                        twfDict.setType(2);
                        tempValue = twfDictMapper.getNameByCodeAndType(twfDict);
                    } else if ("pb_mode".equals(tempKey)) {
                        tempValue = dicCommonMapper.getNameByCode(tempValue);
                    } else if ("enroll_end_time".equals(tempKey) || "bid_end_time".equals(tempKey) || "bid_bonds_end_time".equals(tempKey) || "audit_time".equals(tempKey) || "completion_time".equals(tempKey)) {
                        tempValue = simple.format(new Date(Long.parseLong(tempValue)));
                    }
                    changeField.put(tempKey, tempValue);
                }
            }
            detail.remove("pkid");
            detail.remove("nt_id");
            //一列数据
            for (Map.Entry<String, Object> entry : detail.entrySet()) {
                //替换变更后的值
                if (changeField.size() > 0) {
                    for (Map.Entry<String, String> temp : changeField.entrySet()) {
                        String tempKey = temp.getKey();
                        String tempValue = temp.getValue();
                        if (tempKey.equals(entry.getKey())) {
                            entry.setValue(tempValue);
                        }
                    }
                }
                if (!entry.getKey().equals("url")) {
                    HSSFCell cell = row.createCell(indexCell++);
                    //标题要带超链接
                    if (entry.getKey().equals("title")) {
                        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//                        cell.setCellStyle(linkStyle);
                    } else {
                        cell.setCellValue(String.valueOf(entry.getValue()));
                    }
                }
            }
            row.getCell(0).setCellFormula("HYPERLINK(\"" + String.valueOf(detail.get("url")) + "\",\"" + String.valueOf(detail.get("title")) + "\")");
        }
        return wb;
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
    public String saveNtTenders(TbNtTenders tbNtTenders) {
        String msg = "";
        //更新招标主表状态
        TbNtMian tbNtMian = new TbNtMian();
        tbNtMian.setPkid(tbNtTenders.getNtId());
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtTenders.getSource()));
        tbNtMian.setBinessType(tbNtTenders.getBinessType());
        tbNtMian.setTitle(tbNtTenders.getTitle());
        tbNtMian.setPubDate(tbNtTenders.getPubDate());
        tbNtMian.setUrl(tbNtTenders.getUrl());
        tbNtMian.setCityCode(tbNtTenders.getCityCode());
        tbNtMian.setCountyCode(tbNtTenders.getCountyCode());
        tbNtMianMapper.updateNtMainByPkId(tbNtMian);
        if (StringUtils.isEmpty(tbNtTenders.getSegment())) {
            tbNtTenders.setSegment("1");
        }
        //添加或更新标段信息
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        Integer count = tbNtTendersMapper.countNtTendersByNtIdAndSegment(tbNtTenders);
        if (count == 0) {
            tbNtMian.setNtStatus("1");
            //公告状态改为未审核
            tbNtMianMapper.updateCategoryAndStatusByPkId(tbNtMian);
//            公告标段数+1
//            tbNtTenders.setSegment("1");
//            tbNtMianMapper.updateSegCountByPkid(tbNtMian);

            tbNtTenders.setPkid(DataHandlingUtil.getUUID());
            tbNtTenders.setEditCode("td" + System.currentTimeMillis() + DataHandlingUtil.getNumberRandom(2));
            tbNtTendersMapper.insertNtTenders(tbNtTenders);
            msg = "添加标段信息成功！";
        } else {
            tbNtTendersMapper.updateNtTendersByNtIdAndSegment(tbNtTenders);
            msg = "更新标段信息成功！";
        }
        return msg;
    }


    @Override
    public Object listNtTenders(TbNtTenders tbNtTenders) {
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        List<TbNtTenders> lists = tbNtTendersMapper.listNtTendersByNtId(tbNtTenders);
        if (null != lists && lists.size() > 0) {
            TbNtTenders tempTbNtTenders;
            for (int i = 0; i < lists.size(); i++) {
                tempTbNtTenders = lists.get(i);
                //前端要的特定数据
                if (!StringUtils.isEmpty(tempTbNtTenders.getCityCodeName())) {
                    SysArea sysArea = new SysArea();
                    sysArea.setAreaName(tempTbNtTenders.getCityCodeName());
                    sysArea.setAreaCode(tbNtTenders.getSource());
                    String areaPkId = sysAreaMapper.getPkIdByAreaNameAndParentId(sysArea);
                    tempTbNtTenders.setCountys(sysAreaMapper.listCodeAndNameByParentId(areaPkId));
                }
            }
            return lists;
        } else {
            Map map = new HashMap(2);
            TbNtMian tbNtMian = new TbNtMian();
            tbNtMian.setPkid(tbNtTenders.getNtId());
            tbNtMian.setSource(tbNtTenders.getSource());
            tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
            String status = tbNtMianMapper.getNtStatusByPkId(tbNtMian);
            map.put("status", status);
            return map;
        }
    }

    @Override
    public TbNtTenders getNtTendersByNtIdByPkId(TbNtTenders tbNtTenders) {
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
        //获取标段最新的、不重复的变更信息
        TbNtChange tbNtChange = new TbNtChange();
        tbNtChange.setNtId(tbNtTenders.getNtId());
        tbNtChange.setNtEditId(tbNtTenders.getPkid());
        List<Map<String, Object>> fields = tbNtChangeMapper.listFieldNameAndFieldValueByNtEditId(tbNtChange);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        if (fields != null && fields.size() > 0) {
            Map<String, String> field = new HashMap();
            for (Map<String, Object> map : fields) {
                field.put((String) map.get("field_name"), (String) map.get("field_value"));
            }
            for (Map.Entry<String, String> entry : field.entrySet()) {
                sb1.append(entry.getKey()).append(",");
                sb2.append(entry.getValue()).append(",");
            }
        }
        TbNtTenders tbNtTenders1 = tbNtTendersMapper.getNtTendersByNtIdByPkId(tbNtTenders);
        String fieldName = sb1.toString();
        String fieldValue = sb2.toString();
        if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(fieldValue)) {
            tbNtTenders1.setFieldName(fieldName.substring(0, fieldName.lastIndexOf(",")));
            tbNtTenders1.setFieldValue(fieldValue.substring(0, fieldValue.lastIndexOf(",")));
        }
        //前端要的特定数据
        if (!StringUtils.isEmpty(tbNtTenders1.getCityCodeName())) {
            SysArea sysArea = new SysArea();
            sysArea.setAreaName(tbNtTenders1.getCityCodeName());
            sysArea.setAreaCode(tbNtTenders.getSource());
            String areaPkId = sysAreaMapper.getPkIdByAreaNameAndParentId(sysArea);
            tbNtTenders1.setCountys(sysAreaMapper.listCodeAndNameByParentId(areaPkId));
        }
        return tbNtTenders1;
    }


    @Override
    public void deleteNtTendersByPkId(Map params) {
        String idStr = (String) params.get("idsStr");
        String source = (String) params.get("source");
        String tableName = DataHandlingUtil.SplicingTable(TbNtTenders.class, source);
        String[] ids = idStr.split("\\|");
        Set set = new HashSet<String>();
        for (String id : ids) {
            set.add(id);
        }
        if (set != null && set.size() > 0) {
            TbNtTenders tbNtTenders = new TbNtTenders();
            tbNtTenders.setPkid(ids[0]);
            tbNtTenders.setSource(source);
            tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
            //获取公告pkid用于判断公共是否还有标段信息
            String ntId = tbNtTendersMapper.getNtIdByNtId(tbNtTenders);
            //删除变更信息
            tbNtChangeMapper.deleteTbNtChangeByNtEditId(set.toArray());
            //删除编辑明细
            tbNtTendersMapper.deleteNtTendersByPkId(tableName, set.toArray());
            //获取招标编辑明细个数
            tbNtTenders.setNtId(ntId);
            Integer count = tbNtTendersMapper.countNtTendersByNtId(tbNtTenders);
            if (count == 0) {
                TbNtMian tbNtMian = new TbNtMian();
                tbNtMian.setPkid(ntId);
                tbNtMian.setNtStatus("0");
                tbNtMian.setSource(source);
                tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtMian.getSource()));
                //更新公告状态为 新建
                tbNtMianMapper.updateCategoryAndStatusByPkId(tbNtMian);
            }
        }
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
    public void deleteZhaoBiaoFilesByPkid(Map<String, Object> param) {
        String idsStr = (String) param.get("idsStr");
        String source = (String) param.get("source");
        String[] ids = idsStr.split("\\|");
        Set set = new HashSet<String>();
        for (String id : ids) {
            set.add(id);
        }
        if (set != null && set.size() > 0) {
            sysFilesMapper.deleteSysFilesByPkid(set.toArray(), source);
        }
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
        recycleHunanMapper.inertRecycleForNtMain(recycle);
        tbNtMianMapper.deleteNtMainByPkId(main);
    }

    @Override
    public void updateNtText(TbNtText tbNtText) {
        tbNtText.setTableName(DataHandlingUtil.SplicingTable(TbNtText.class, tbNtText.getSource()));
        String compress = WordProcessingUtil.getTextFromHtml(tbNtText.getContent());
        tbNtText.setCompress(compress);
        tbNtTextHunanMapper.updateNtText(tbNtText);
    }

    @Override
    public String insertNtAssociateGp(Map params) {
        String msg = "关联成功！";
        String idsStr = (String) params.get("idsStr");
        String createBy = (String) params.get("createBy");
        String source = (String) params.get("source");
        String[] ids = idsStr.split("\\|");
        //数据库中已存在的
        List<TbNtAssociateGp> TbNtAssociateGps = tbNtAssociateGpMapper.getRelGpByNtIds(ids, DataHandlingUtil.SplicingTable(TbNtAssociateGp.class, source));
        Set set = new HashSet<String>();
        for (TbNtAssociateGp tbNtAssociateGp : TbNtAssociateGps) {
            set.add(tbNtAssociateGp.getNtId());
        }

        TbNtMian tbNtMian;
        TbNtAssociateGp tbNtAssociateGp;
        List tbNtAssociateGpList = new ArrayList<TbNtAssociateGp>(ids.length);
        if (set.size() > 0) {
            //数据库中已经存在，则新来的关联公告与已经存在的公告同属一个组
            String relGp = TbNtAssociateGps.get(0).getRelGp();
            for (int i = 0; i < ids.length; i++) {
                if (!set.contains(ids[i])) {
                    //获取项目类型
                    tbNtMian = new TbNtMian();
                    tbNtMian.setPkid(String.valueOf(ids[i]));
                    tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), source));
                    String ntCategory = tbNtMianMapper.getNtCategoryByPkId(tbNtMian);
                    //
                    tbNtAssociateGp = new TbNtAssociateGp();
                    tbNtAssociateGp.setPkid(DataHandlingUtil.getUUID());
                    tbNtAssociateGp.setNtId(ids[i]);
                    tbNtAssociateGp.setRelType(ntCategory);
                    tbNtAssociateGp.setRelGp(relGp);
                    tbNtAssociateGp.setPx(String.valueOf(i));
                    tbNtAssociateGp.setCreateBy(createBy);
                    tbNtAssociateGpList.add(tbNtAssociateGp);
                } else {
                    System.out.println("11");
                }
            }
        } else {
            //时间戳 + 随机数
            String relGp = DataHandlingUtil.getTimeStamp() + "_" + DataHandlingUtil.getNumberRandom(6);
            for (int i = 0; i < ids.length; i++) {
                //获取项目类型
                tbNtMian = new TbNtMian();
                tbNtMian.setPkid(String.valueOf(ids[i]));
                tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), source));
                String ntCategory = tbNtMianMapper.getNtCategoryByPkId(tbNtMian);
                //
                tbNtAssociateGp = new TbNtAssociateGp();
                tbNtAssociateGp.setPkid(DataHandlingUtil.getUUID());
                tbNtAssociateGp.setNtId(ids[i]);
                tbNtAssociateGp.setRelType(ntCategory);
                tbNtAssociateGp.setRelGp(relGp);
                tbNtAssociateGp.setPx(String.valueOf(i));
                tbNtAssociateGp.setCreateBy(createBy);
                tbNtAssociateGpList.add(tbNtAssociateGp);
            }
        }
        String tableName = DataHandlingUtil.SplicingTable(TbNtAssociateGp.class, source);
        if (tbNtAssociateGpList.size() != 0) {
            tbNtAssociateGpMapper.batchInsertNtAssociateGp(tbNtAssociateGpList, tableName);
        }
        return msg;
    }

    @Override
    public void deleteNtAssociateGp(List<Map<String, Object>> tbNtAssociateGps) {
        for (int i = 0; i < tbNtAssociateGps.size(); i++) {
            TbNtAssociateGp TbNtAssociateGp = new TbNtAssociateGp();
            TbNtAssociateGp.setNtId((String) tbNtAssociateGps.get(i).get("ntId"));
            TbNtAssociateGp.setRelGp((String) tbNtAssociateGps.get(i).get("relGp"));
            TbNtAssociateGp.setSource((String) tbNtAssociateGps.get(i).get("source"));
            TbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(TbNtAssociateGp.class, TbNtAssociateGp.getSource()));
            tbNtAssociateGpMapper.deleteNtAssociateGpByNtIdAndRelGp(TbNtAssociateGp);
        }
    }

    @Override
    public Map<String, Object> listNtAssociateGp(TbNtAssociateGp tbNtAssociateGp) {
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(TbNtAssociateGp.class, tbNtAssociateGp.getSource()));
//        String relGp = tbNtAssociateGpMapper.getRelGpByNtId(tbNtAssociateGp);
        Map result = new HashMap<String, Object>();
//        tbNtAssociateGp.setRelGp(relGp);
        result.put("datas", tbNtAssociateGpMapper.listNtAssociateGpByNtId(tbNtAssociateGp));
        result.put("total", tbNtAssociateGpMapper.countNtAssociateGpByNtId(tbNtAssociateGp));
        return super.handlePageCount(result, tbNtAssociateGp);
    }

    @Override
    public void insertNtRegexQua(TbNtRegexQua tbNtRegexQua) {
        TbNtRegexGroup tempRegexGroup = new TbNtRegexGroup();
        tempRegexGroup.setNtId(tbNtRegexQua.getNtId());
        tempRegexGroup.setNtEditId(tbNtRegexQua.getNtEditId());
        //获取资质组关系表达式
        TbNtRegexGroup tbNtRegexGroup = tbNtRegexGroupMapper.getNtRegexGroupByNtIdAndNtEditId(tempRegexGroup);
        String groupRegex = tbNtRegexGroup.getGroupRegex();
        List qualRegexList = new ArrayList(20);
        //1、把资质组关系表达式 拆分成资质组块表达式 （G1&G2&G4|G3 = G1&G2&G4  G3）
        String[] blockQual = groupRegex.split("\\|");
        for (int i = 0; i < blockQual.length; i++) {
            String block = blockQual[i];
            //2、遍历资质块表达式，获取资质块表达式中的各个资质小组表达式(G1&G2&G4 = G1,G2,G3)
            List<String> singleList = this.splitBlockQualGroup(block);
            if (singleList.size() > 0) {
                List<String> temp = null;
                List<String> mergeList = null;
                Map<String, List> tempMap = new TreeMap();
                if (singleList.size() > 1) {
                    //3、遍历资质小组表达式，生成各个资质小组表达式组内关系（A1,A2,A3 = A1A2A3）
                    for (int j = 0; j < singleList.size(); j++) {
                        //获取小组内资质
                        List<TbNtQuaGroup> tbNtQuaGroups = tbNtQuaGroupMapper.listTbNtQuaGroupByGroupId(singleList.get(j));
                        temp = this.mergeSingleQualGroup(tbNtQuaGroups);
                        tempMap.put("list" + j, temp);
                    }
                    //4、合并资质组块表达式中小组之间的关系 **不能超过5组**（((A1&,A2&,A3&) (B1|,B2|,B3|)) = (A1A2A3B1,A1A2A3B2,A1A2A3B3)）
                    if (tempMap.size() == 2) {
                        mergeList = this.merge(tempMap.get("list0"), tempMap.get("list1"));
                    } else if (tempMap.size() == 3) {
                        mergeList = this.merge(tempMap.get("list0"), tempMap.get("list1"), tempMap.get("list2"));
                    } else if (tempMap.size() == 4) {
                        mergeList = this.merge(tempMap.get("list0"), tempMap.get("list1"), tempMap.get("list2"), tempMap.get("list3"));
                    } else if (tempMap.size() == 5) {
                        mergeList = this.merge(tempMap.get("list0"), tempMap.get("list1"), tempMap.get("list2"), tempMap.get("list3"), tempMap.get("list4"));
                    }
                } else {
                    //获取小组内资质
                    List<TbNtQuaGroup> tbNtQuaGroups = tbNtQuaGroupMapper.listTbNtQuaGroupByGroupId(singleList.get(0));
                    //3、生成各个资质小组表达式组内关系
                    temp = this.mergeSingleQualGroup(tbNtQuaGroups);
                    //4、合并资质组块表达式中小组之间的关系（A1&,A2&,A3& = A1A2A3）
                    mergeList = temp;
                }
                //5、合并各个资质组块表达式生成的资质关系
                qualRegexList.addAll(mergeList);
            }
        }
        String qualRegex = StringUtils.collectionToDelimitedString(qualRegexList, ",");
        tbNtRegexQua.setPkid(DataHandlingUtil.getUUID());
        tbNtRegexQua.setQuaRegex(qualRegex);
        tbNtRegexQuaMapper.insertTbNtRegexQua(tbNtRegexQua);
    }




    /**
     * 把资质块 拆分成单个小组 （G1&G2&G4 = G1,G2,G3）
     *
     * @param blockQual
     * @return list
     */
    private List splitBlockQualGroup(String blockQual) {
        List list = new ArrayList<String>(10);
        if (blockQual.contains("&")) {
            String[] combinationQual = blockQual.split("\\&");
            for (String tempQual : combinationQual) {
                list.add(tempQual);
            }
        } else {
            list.add(blockQual);
        }
        return list;
    }

    /**
     * 组合小组内关系 返回list（A1&A2&A3 = A1A2A3， B1|B2|B3 = B1,B2,B3）
     * @param tbNtQuaGroups
     * @return
     */
    private List mergeSingleQualGroup(List<TbNtQuaGroup> tbNtQuaGroups) {
        List<String> result = new ArrayList<>(10);
        if(null != tbNtQuaGroups && tbNtQuaGroups.size() > 0) {
            TbNtQuaGroup tbNtQuaGroup;
            if(tbNtQuaGroups.size() == 1) {
                tbNtQuaGroup = tbNtQuaGroups.get(0);
                result.add(tbNtQuaGroup.getQuaId());
            } else {
                TbNtQuaGroup tbNtQuaGroup1;
                String relType = tbNtQuaGroups.get(tbNtQuaGroups.size() - 1).getRelType();
                if("&".equals(relType)) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < tbNtQuaGroups.size(); i++) {
                        tbNtQuaGroup1 = tbNtQuaGroups.get(i);
                        sb.append(tbNtQuaGroup1.getQuaId());
                    }
                    result.add(sb.toString());
                } else {
                    for (int i = 0; i < tbNtQuaGroups.size(); i++) {
                        tbNtQuaGroup1 = tbNtQuaGroups.get(i);
                        result.add(tbNtQuaGroup1.getQuaId());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 拼接生成小组间资质关系 最多5个小组
     *
     * @param list
     * @return
     */
    private List merge(List... list) {
        List result = new ArrayList<String>(20);
        StringBuilder sb;
        if (list.length == 2) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    sb = new StringBuilder();
                    sb = sb.append(list[0].get(i)).append(list[1].get(j));
                    result.add(sb.toString());
                }
            }
            return result;
        }
        if (list.length == 3) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    for (int k = 0; k < list[2].size(); k++) {
                        sb = new StringBuilder();
                        sb = sb.append(list[0].get(i)).append(list[1].get(j)).append(list[2].get(k));
                        result.add(sb.toString());
                    }
                }
            }
            return result;
        }
        if (list.length == 4) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    for (int k = 0; k < list[2].size(); k++) {
                        for (int l = 0; l < list[3].size(); l++) {
                            sb = new StringBuilder();
                            sb = sb.append(list[0].get(i)).append(list[1].get(j)).append(list[2].get(k)).append(list[3].get(l));
                            result.add(sb.toString());
                        }
                    }
                }
            }
            return result;
        }
        if (list.length == 5) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    for (int k = 0; k < list[2].size(); k++) {
                        for (int l = 0; l < list[3].size(); l++) {
                            for (int m = 0; m < list[4].size(); m++) {
                                sb = new StringBuilder();
                                sb = sb.append(list[0].get(i)).append(list[1].get(j)).append(list[2].get(k)).append(list[3].get(l)).append(list[4].get(m));
                                result.add(sb.toString());
                            }
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }
}

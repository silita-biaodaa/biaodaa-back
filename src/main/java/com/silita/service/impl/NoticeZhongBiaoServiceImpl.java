package com.silita.service.impl;

import com.silita.dao.*;
import com.silita.model.*;
import com.silita.service.INoticeZhongBiaoService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-09-17 16:29
 */
@Service("noticeZhongBiaoService")
public class NoticeZhongBiaoServiceImpl extends AbstractService implements INoticeZhongBiaoService {

    @Autowired
    DicCommonMapper dicCommonMapper;
    @Autowired
    TbNtTendersMapper tbNtTendersMapper;
    @Autowired
    TbNtAssociateGpMapper tbNtAssociateGpMapper;
    @Autowired
    TbNtChangeMapper tbNtChangeMapper;
    @Autowired
    SysFilesMapper sysFilesMapper;
    @Autowired
    TbNtBidsMapper tbNtBidsMapper;
    @Autowired
    TbNtMianMapper tbNtMianMapper;
    @Autowired
    TbNtBidsCandMapper tbNtBidsCandMapper;
    @Autowired
    TbNtRecycleHunanMapper recycleHunanMapper;


    @Override
    public List<TbNtTenders> listNtTenders(TbNtMian tbNtMian) {
        List lists = null;
        try {
            // 1、根据中标公告id获取关联的招标公告id
            TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
            tbNtAssociateGp.setNtId(tbNtMian.getPkid());
            tbNtAssociateGp.setSource(tbNtMian.getSource());
            tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(tbNtAssociateGp.getClass(), tbNtAssociateGp.getSource()));
            List<String> ntIds = tbNtAssociateGpMapper.getNtIdByNtId(tbNtAssociateGp);
            if (null != ntIds && ntIds.size() > 0) {
                lists = new ArrayList<TbNtTenders>(ntIds.size());
                for (int i = 0; i < ntIds.size(); i++) {
                    String ntId = ntIds.get(i);
                    TbNtTenders tbNtTenders = new TbNtTenders();
                    tbNtTenders.setNtId(ntId);
                    tbNtTenders.setSource(tbNtMian.getSource());
                    tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtTenders.getSource()));
                    //判断公告是否存在招标编辑明细
                    Integer count = tbNtTendersMapper.countNtTendersByNtId(tbNtTenders);
                    if (count > 0) {
                        // 2、根据招标公告id获取招标标段信息
                        List<TbNtTenders> tbNtTendersList = tbNtTendersMapper.listNtTendersByNtId(tbNtTenders);
                        for (int j = 0; j < tbNtTendersList.size(); j++) {
                            TbNtTenders tempTenders = tbNtTendersList.get(j);
                            //获取标段最新的、不重复的变更信息
                            TbNtChange tbNtChange = new TbNtChange();
                            tbNtChange.setNtId(String.valueOf(tempTenders.getNtId()));
                            tbNtChange.setNtEditId(String.valueOf(tempTenders.getPkid()));
                            List<Map<String, Object>> fields = tbNtChangeMapper.listFieldNameAndFieldValueByNtEditId(tbNtChange);
                            Map<String, String> tempMap = new HashMap();
                            if (fields != null && fields.size() > 0) {
                                for (Map<String, Object> map : fields) {
                                    String tempKey = com.silita.utils.stringUtils.StringUtils.HumpToUnderline(String.valueOf(map.get("field_name")));
                                    String tempValue = String.valueOf(map.get("field_value"));
                                    tempMap.put(tempKey, tempValue);
                                }
                            }
                            Field[] field = tempTenders.getClass().getDeclaredFields();
                            //遍历field、替换变更后的值
                            for (int k = 0; k < field.length; k++) {
                                String name = field[k].getName();
                                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                                String keyName = com.silita.utils.stringUtils.StringUtils.HumpToUnderline(name).substring(1);
                                String type = field[k].getGenericType().toString();
                                //替换变更后的值
                                if (tempMap.size() > 0) {
                                    for (Map.Entry<String, String> temp : tempMap.entrySet()) {
                                        String tempKey = temp.getKey();
                                        String tempValue = temp.getValue();
                                        if (tempKey.equals(keyName)) {
                                            if (type.equals("class java.lang.String")) {
                                                Method m = tempTenders.getClass().getMethod("set" + name, String.class);
                                                m.invoke(tempTenders, String.valueOf(tempValue));
                                            }
                                            if (type.equals("class java.lang.Double")) {
                                                Method m = tempTenders.getClass().getMethod("set" + name, Double.class);
                                                m.invoke(tempTenders, Double.valueOf(tempValue));
                                            }
                                            if (type.equals("class java.lang.Boolean")) {
                                                Method m = tempTenders.getClass().getMethod("set" + name, Boolean.class);
                                                m.invoke(tempTenders, Boolean.valueOf(tempValue));
                                            }
                                            if (type.equals("class java.util.Date")) {
                                                Method m = tempTenders.getClass().getMethod("set" + name, Date.class);
                                                m.invoke(tempTenders, new Date(Long.parseLong(tempValue)));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        lists.addAll(tbNtTendersList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
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
            // 1、删除变更信息
            tbNtChangeMapper.deleteTbNtChangeByNtEditId(set.toArray());
            // 2、删除招标编辑明细
            tbNtTendersMapper.deleteNtTendersByPkId(tableName, set.toArray());
            // 3、获取招标编辑明细个数
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
    public Map<String, Object> listNtAssociateGp(TbNtMian tbNtMian) {
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setSource(tbNtMian.getSource());
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(TbNtAssociateGp.class, tbNtAssociateGp.getSource()));
        Map result = new HashMap<String, Object>();
        result.put("datas", tbNtAssociateGpMapper.listNtAssociateGpByNtId(tbNtAssociateGp));
        result.put("total", tbNtAssociateGpMapper.countNtAssociateGpByNtId(tbNtAssociateGp));
        return super.handlePageCount(result, tbNtAssociateGp);
    }

    @Override
    public List<SysFiles> listZhaoBiaoFilesByPkId(TbNtMian tbNtMian) {
        List<SysFiles> lists = null;
        // 1、根据中标公告id获取关联的招标公告id
        TbNtAssociateGp tbNtAssociateGp = new TbNtAssociateGp();
        tbNtAssociateGp.setNtId(tbNtMian.getPkid());
        tbNtAssociateGp.setSource(tbNtMian.getSource());
        tbNtAssociateGp.setTableName(DataHandlingUtil.SplicingTable(tbNtAssociateGp.getClass(), tbNtAssociateGp.getSource()));
        List<String> ntIds = tbNtAssociateGpMapper.getNtIdByNtId(tbNtAssociateGp);
        if (null != ntIds && ntIds.size() > 0) {
            lists = new ArrayList<SysFiles>(ntIds.size());
            for (int i = 0; i < ntIds.size(); i++) {
                String ntId = ntIds.get(i);
                SysFiles sysFiles = new SysFiles();
                sysFiles.setBizId(ntId);
                sysFiles.setSource(tbNtMian.getSource());
                //判断公告是否存在招标文件列表
                Integer count = sysFilesMapper.countSysFilesByBizIdAndSource(sysFiles);
                if (count > 0) {
                    //2、根据招标公告id获取文件列表
                    lists.addAll(sysFilesMapper.listSysFilesByBizId(sysFiles));
                }
            }
        }
        return lists;
    }

    public String saveNtBids(TbNtBids tbNtBids) {
        String msg = null;
        //更新招标主表状态
        TbNtMian tbNtMian = new TbNtMian();
        tbNtMian.setPkid(tbNtBids.getNtId());
        tbNtMian.setTableName(DataHandlingUtil.SplicingTable(tbNtMian.getClass(), tbNtBids.getSource()));
        tbNtMian.setBinessType(tbNtBids.getBinessType());
        tbNtMian.setTitle(tbNtBids.getTitle());
        tbNtMian.setPubDate(tbNtBids.getPubDate());
        tbNtMian.setCityCode(tbNtBids.getCityCode());
        tbNtMian.setCountyCode(tbNtBids.getCountyCode());
        tbNtMianMapper.updateNtMainByPkId(tbNtMian);
        //更新招标标段表信息
        TbNtTenders tbNtTenders = new TbNtTenders();
        tbNtTenders.setNtId(tbNtBids.getNtId());
        tbNtTenders.setEditCode(tbNtBids.getTdEditCode());
        tbNtTenders.setProType(tbNtBids.getProType());
        tbNtTenders.setPbMode(tbNtBids.getPbMode());
        tbNtTenders.setSource(tbNtBids.getSource());
        tbNtTenders.setTableName(DataHandlingUtil.SplicingTable(tbNtTenders.getClass(), tbNtBids.getSource()));
        tbNtTendersMapper.updateProTypeAndPbModeByNtIdAndEditCode(tbNtTenders);
        if (StringUtils.isEmpty(tbNtTenders.getSegment())) {
            tbNtBids.setSegment("1");
        }
        //中标标段
        tbNtBids.setTableName(DataHandlingUtil.SplicingTable(tbNtBids.getClass(), tbNtBids.getSource()));
        Integer count = tbNtBidsMapper.countNtBidsByNtIdAndSegment(tbNtBids);
        if (count == 0) {
            tbNtMian.setNtStatus("1");
            //公告状态改为未审核
            tbNtMianMapper.updateCategoryAndStatusByPkId(tbNtMian);
            //添加中标标段基本信息
            tbNtBids.setPkid(DataHandlingUtil.getUUID());
            tbNtBids.setEditCode("td" + System.currentTimeMillis() + DataHandlingUtil.getNumberRandom(2));
            tbNtBidsMapper.insertTbNtBids(tbNtBids);
            //批量添加中标候选人
            List<TbNtBidsCand> tbNtBidsCands = tbNtBids.getBidsCands();
            if (null != tbNtBidsCands && tbNtBidsCands.size() > 0) {
                for (int i = 0; i < tbNtBidsCands.size(); i++) {
                    tbNtBidsCands.get(i).setPkid(DataHandlingUtil.getUUID());
                    tbNtBidsCands.get(i).setNtBidsId(tbNtBids.getPkid());
                    tbNtBidsCands.get(i).setCreateBy(tbNtBids.getCreateBy());
                }
                tbNtBidsCandMapper.batchInsertNtBidsCand(tbNtBidsCands);
            }
            msg = "添加标段信息成功！";
        } else {
            //更新中标标段基本信息
            tbNtBidsMapper.updateTbNtBidsByNtIdAndSegment(tbNtBids);
            //批量更新中标候选人
            List<TbNtBidsCand> tbNtBidsCands = tbNtBids.getBidsCands();
            if (null != tbNtBidsCands && tbNtBidsCands.size() > 0) {
                tbNtBidsCandMapper.batchUpdateNtBidsCand(tbNtBidsCands);
            }
            msg = "更新标段信息成功！";
        }
        return msg;
    }

    @Override
    public List<TbNtBids> listTbNtBidsByNtId(TbNtBids tbNtBids) {
        tbNtBids.setTableName(DataHandlingUtil.SplicingTable(tbNtBids.getClass(), tbNtBids.getSource()));
        //获取中标编辑明细
        List<TbNtBids> lists = tbNtBidsMapper.listNtBidsByNtId(tbNtBids);
        //添加编辑明细变更信息
        if(null != lists && lists.size() > 0) {
            TbNtBids tempNtBids;
            for (int i = 0; i < lists.size(); i++) {
                tempNtBids = lists.get(i);
                TbNtChange tbNtChange = new TbNtChange();
                tbNtChange.setNtId(tempNtBids.getNtId());
                tbNtChange.setNtEditId(tempNtBids.getPkid());
                List<Map<String, Object>> changeFields = tbNtChangeMapper.listFieldNameAndFieldValueByNtEditId(tbNtChange);
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                if (changeFields != null && changeFields.size() > 0) {
                    Map<String, String> changeField = new HashMap();
                    for (Map<String, Object> map : changeFields) {
                        changeField.put((String) map.get("field_name"), (String) map.get("field_value"));
                    }
                    for (Map.Entry<String, String> entry : changeField.entrySet()) {
                        sb1.append(entry.getKey()).append(",");
                        sb2.append(entry.getValue()).append(",");
                    }
                }
                String changeFieldName = sb1.toString();
                String changeFieldValue = sb2.toString();
                if (!StringUtils.isEmpty(changeFieldName) && !StringUtils.isEmpty(changeFieldValue)) {
                    tempNtBids.setChangeFieldName(changeFieldName.substring(0, changeFieldName.lastIndexOf(",")));
                    tempNtBids.setChangeFieldValue(changeFieldValue.substring(0, changeFieldValue.lastIndexOf(",")));
                }
                List<TbNtBidsCand> bidsCands = tempNtBids.getBidsCands();
                //添加中标候选人编辑明细
                if(null != bidsCands && bidsCands.size() > 0) {
                    TbNtBidsCand tempNtBidsCand;
                    for (int j = 0; j < bidsCands.size(); j++) {
                        tempNtBidsCand = bidsCands.get(j);
                        tbNtChange = new TbNtChange();
                        tbNtChange.setNtId(tempNtBidsCand.getNtId());
                        tbNtChange.setNtEditId(tempNtBidsCand.getPkid());
                        List<Map<String, Object>> candChangeFields = tbNtChangeMapper.listFieldNameAndFieldValueByNtEditId(tbNtChange);
                        StringBuilder CandidateSb1 = new StringBuilder();
                        StringBuilder CandidateSb2 = new StringBuilder();
                        if (candChangeFields != null && candChangeFields.size() > 0) {
                            Map<String, String> candChangeField = new HashMap();
                            for (Map<String, Object> map : candChangeFields) {
                                candChangeField.put((String) map.get("field_name"), (String) map.get("field_value"));
                            }
                            for (Map.Entry<String, String> entry : candChangeField.entrySet()) {
                                CandidateSb1.append(entry.getKey()).append(",");
                                CandidateSb2.append(entry.getValue()).append(",");
                            }
                        }
                        String CandidateChangeFieldName = CandidateSb1.toString();
                        String CandidateChangeFieldValue = CandidateSb2.toString();
                        if (!StringUtils.isEmpty(CandidateChangeFieldName) && !StringUtils.isEmpty(CandidateChangeFieldValue)) {
                            tempNtBidsCand.setChangeFieldName(CandidateChangeFieldName.substring(0, CandidateChangeFieldName.lastIndexOf(",")));
                            tempNtBidsCand.setChangeFieldValue(CandidateChangeFieldValue.substring(0, CandidateChangeFieldValue.lastIndexOf(",")));
                        }
                    }
                }

            }
        }
        return lists;
    }

    @Override
    public void deleteTbNtBidsByPkId(Map params) {
        String idStr = (String) params.get("idsStr");
        String source = (String) params.get("source");
        String tableName = DataHandlingUtil.SplicingTable(TbNtBids.class, source);
        String[] ids = idStr.split("\\|");
        Set set = new HashSet<String>();
        for (String id : ids) {
            set.add(id);
        }
        if (set != null && set.size() > 0) {
            //删除变更信息
            tbNtChangeMapper.deleteTbNtChangeByNtEditId(set.toArray());
            //删除编辑明细
            tbNtBidsMapper.batchDeleteNtBidsByPkId(tableName, set.toArray());
            //删除中标候选人
            tbNtBidsCandMapper.batchDeleteNtBidsCandByNtBidsId(set.toArray());
        }
    }

    @Override
    public HSSFWorkbook listBids(TbNtMian tbNtMian) {
        int indexRow = 0;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow(indexRow++);
        String[] headers = {
                "项目名称", "标段", "公示时间", "招标控制价", "项目金额",
                "项目工期", "计划竣工时间", "项目地区", "项目县市", "评标办法",
                "招标类型", "项目类型", "资质要求", "单位名称(1)", "报价(1)",
                "项目负责人(1)", "技术负责人(1)", "施工员(1)", "安全员(1)",
                "质量员(1)", "单位名称(2)", "报价(2)", "项目负责人(2)", "技术负责人(2)",
                "施工员(2)", "安全员(2)", "质量员(2)", "单位名称(3)", "报价(3)",
                "项目负责人(3)", "技术负责人(3)", "施工员(3)", "安全员(3)",
                "质量员(3)"
        };
        //创建标题
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        List<LinkedHashMap<String, Object>> details = tbNtMianMapper.listBidsDetail(tbNtMian);
        //一行数据
        for (int i = 0; i < details.size(); i++) {
            int indexCell = 0;
            row = sheet.createRow(indexRow++);
            Map<String, Object> detail = details.get(i);
            String ntBidsId = String.valueOf(detail.get("pkid"));
            detail.remove("pkid");
            //拼接3个中标候选人数据
            for (int j = 1; j < 4; j++) {
                TbNtBidsCand tbNtBidsCand = new TbNtBidsCand();
                tbNtBidsCand.setNtBidsId(ntBidsId);
                tbNtBidsCand.setNumber(j);
                LinkedHashMap<String, Object> temp = tbNtBidsCandMapper.getNtBidsCandByNtBidsIdAndNumber(tbNtBidsCand);
                if (temp == null) {
                    for (int k = 1; k <= 7; k++) {
                        detail.put("fill_" + j + "_" + k, "");
                    }
                } else {
                    detail.putAll(temp);
                }
            }
            //一列数据
            for (Map.Entry<String, Object> entry : detail.entrySet()) {
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
    public void saveTbNtChange(TbNtChange tbNtChange) {
        tbNtChange.setPkid(DataHandlingUtil.getUUID());
        tbNtChangeMapper.insertTbNtChange(tbNtChange);
    }

    @Override
    public void delNtMain(TbNtMian main) {
        main.setTableName(DataHandlingUtil.SplicingTable(main.getClass(), main.getSource()));
        TbNtRecycle recycle = new TbNtRecycle();
        recycle.setPkid(DataHandlingUtil.getUUID());
        recycle.setNtId(main.getPkid());
        recycle.setSource(main.getSource());
        recycle.setCreateBy(main.getCreateBy());
        recycle.setDelType("4");
        //将公告数据填进回收站
        recycleHunanMapper.inertRecycleForBids(recycle);
        tbNtMianMapper.deleteNtMainByPkId(main);
    }

}

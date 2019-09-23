package com.silita.service.impl;

import com.silita.common.BasePageModel;
import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.model.DicAlias;
import com.silita.service.IAliasService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AliasServiceImpl extends AbstractService implements IAliasService {

    @Autowired
    DicAliasMapper dicAliasMapper;


/*    @Override
    public List<DicAlias> getAliasList(Map<String, Object> param) {
        DicAlias dicAlias = new DicAlias();
        dicAlias.setName(MapUtils.getString(param, "name"));
        dicAlias.setStdCode(MapUtils.getString(param, "stdCode"));
        dicAlias.setStdType(MapUtils.getString(param, "stdType"));
        return dicAliasMapper.listDicAliasByStdCode(dicAlias);
    }*/

    /**
     * 根据quaCode获取资质别名
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> gitAliasListStdCode(Map<String, Object> param) {
        DicAlias dicAlias = new DicAlias();
        dicAlias.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
        dicAlias.setPageSize(MapUtils.getInteger(param, "pageSize"));
        dicAlias.setStdCode(MapUtils.getString(param, "code"));
        dicAlias.setName(MapUtils.getString(param, "name"));
        dicAlias.setStdType(MapUtils.getString(param, "type"));
        Map<String, Object> params = new HashMap<>();
        params.put("list", dicAliasMapper.queryAliasListCode(dicAlias));
        params.put("total", dicAliasMapper.queryAliasListCodeCount(dicAlias));
        return super.handlePageCount(params, dicAlias);
    }

    /**
     * 删除别名
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> delAilasByIds(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        String ids = MapUtils.getString(param, "ids");
        String[] split = ids.split(",");
        List<String> id = Arrays.asList(split);
        param.put("ids", id);
        dicAliasMapper.delAilasByIds(param);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> insertAilas(Sheet sheet, String stdCode, String fileName) throws IOException, Exception {
       /* int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            return null;
        }
        Cell cell;
        Row row;
        List<Map<String, Object>> excelList = new ArrayList<>();
        Map<String, Object> excelMap;
        StringBuffer sbf;
        String comId;
        String level;
        String provCode;
        String cityCode;
        String issueDate = null;
        boolean isError = true;
        for (int i = 1; i <= rowCount; i++) {
            row = sheet.getRow(i);
            excelMap = new HashMap<>();
            sbf = new StringBuffer();
            //企业名称
            cell = row.getCell(0);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    comId = tbCompanyMapper.queryComIdByName(cell.getStringCellValue());
                    if (comId == null) {
                        sbf.append("企业不存在");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("comId", comId);
                    }
                    excelMap.put("comName", cell.getStringCellValue());
                }
            }
            //奖项名称
            cell = row.getCell(1);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    level = ConstantMap.AWARDSMAP.get(cell.getStringCellValue().trim());
                    if (level == null) {
                        sbf.append("，奖项错误");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("level", level);
                    }
                    excelMap.put("levelName", cell.getStringCellValue());
                }
            }
            //省
            cell = row.getCell(2);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    provCode = sysAreaMapper.queryAreaCode(cell.getStringCellValue());
                    if (null == provCode) {
                        sbf.append("，省份错误");
                        if (isError) {
                            isError = false;
                        }
                    }
                    excelMap.put("provCode", provCode);
                    excelMap.put("prov", cell.getStringCellValue());
                }
            }
            //市
            cell = row.getCell(3);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    cityCode = sysAreaMapper.queryAreaCode(cell.getStringCellValue());
                    if (null == cityCode) {
                        sbf.append("，市错误");
                        if (isError) {
                            isError = false;
                        }
                    }
                    excelMap.put("cityCode", cityCode);
                    excelMap.put("city", cell.getStringCellValue());
                }
            }
            //奖项名称
            cell = row.getCell(4);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    excelMap.put("awdName", cell.getStringCellValue());
                }
            }
            //年度
            cell = row.getCell(5);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    excelMap.put("year", cell.getStringCellValue());
                }
            }
            //项目名称
            cell = row.getCell(6);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    excelMap.put("proName", cell.getStringCellValue());
                }
            }
            //项目类型
            cell = row.getCell(7);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    excelMap.put("proTypeName", cell.getStringCellValue());
                }
            }
            //发布时间
            cell = row.getCell(8);
            if (null != cell) {
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    if (!MyDateUtils.checkDate(cell.getStringCellValue())) {
                        sbf.append("，发布日期格式不正确(yyyy-MM-dd)");
                        if (isError) {
                            isError = false;
                        }
                    }
                    excelMap.put("issueDate", issueDate);
                }
            }
            if (null != sbf && !"".equals(sbf.toString())) {
                excelMap.put("sdf", StringUtils.trimFirstAndLastChar(sbf.toString(), '，'));
            }
            excelMap.put("pkid", DataHandlingUtil.getUUID());
            excelMap.put("createBy", username);
            excelList.add(excelMap);
        }
        Map<String, Object> resultMap = new HashMap<>();
        if (!isError) {
            String fileUrl = uploadExcel(excelList, "import",fileName);
            resultMap.put("code", Constant.CODE_WARN_405);
            resultMap.put("msg", Constant.MSG_WARN_405);
            resultMap.put("data", fileUrl);
            return resultMap;
        }
        //去重
        List<TbCompanyAwards> list = doWeight(excelList);
        if (null != list && list.size() > 0) {
            tbCompanyAwardsMapper.batchInsertCompanyAwrds(list);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);*/
        return null;
    }

    /**
     * 添加等级别名
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> insertLevelAilas(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        Integer integer = dicAliasMapper.queryName(param);
        if (null != integer && integer != 0) {
            resultMap.put("code", "0");
            resultMap.put("msg", "别名已存在");
            return resultMap;
        }
        String name = MapUtils.getString(param, "name");
        param.put("id", DataHandlingUtil.getUUID());
       // param.put("stdType", Constant.GRADE_STD_TYPE);
        param.put("code", "alias_grade_" + PinYinUtil.cn2py(name) + "_" + System.currentTimeMillis());
        dicAliasMapper.insertLevelAilas(param);
        resultMap.put("code",Constant.CODE_SUCCESS);
        resultMap.put("msg",Constant.MSG_SUCCESS);
        return resultMap;
    }


}

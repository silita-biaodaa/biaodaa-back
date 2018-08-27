package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.model.DicAlias;
import com.silita.service.IQualService;
import com.silita.service.IUploadService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadServiceImpl implements IUploadService {

    @Autowired
    DicAliasMapper dicAliasMapper;
    @Autowired
    IQualService qualService;

    @Override
    public Map<String, Object> analysisQuaGrade(MultipartFile file, Map<String, Object> param) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            resultMap.put("code", Constant.CODE_WARN_405);
            resultMap.put("msg", Constant.MSG_WARN_405);
            return resultMap;
        }
        if (null == workbook) {
            resultMap.put("code", Constant.CODE_WARN_404);
            resultMap.put("msg", Constant.MSG_WARN_404);
            return resultMap;
        }
        List<DicAlias> dicAliasList = new ArrayList<>();
        DicAlias dicAlias = null;
        String code = null;
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        int celCount = 0;
        Row row = null;
        Cell cell = null;
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                row = sheet.getRow(i);
                celCount = row.getLastCellNum();
                if (celCount > 1) {
                    resultMap.put("code", Constant.CODE_WARN_405);
                    resultMap.put("msg", Constant.MSG_WARN_405);
                    return resultMap;
                }
                cell = row.getCell(0);
                if (null != cell) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (null != cell.getStringCellValue()) {
                        dicAlias = new DicAlias();
                        dicAlias.setStdType(Constant.QUAL_LEVEL_PARENT);
                        dicAlias.setStdCode(MapUtils.getString(param, "quaCode"));
                        dicAlias.setCreateBy(MapUtils.getString(param, "username"));
                        dicAlias.setName(cell.getStringCellValue());
                        dicAlias.setId(DataHandlingUtil.getUUID());
                        code = "alias_qual_" + PinYinUtil.cn2py(cell.getStringCellValue()) + "_" + System.currentTimeMillis();
                        dicAliasList.add(dicAlias);
                    }
                }
            }
        }
        if (null != dicAliasList && dicAliasList.size() > 0) {
            qualService.addQuaAlias(dicAliasList);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
            return resultMap;
        }
        resultMap.put("code", Constant.CODE_WARN_400);
        resultMap.put("msg", Constant.MSG_WARN_400);
        return resultMap;
    }
}

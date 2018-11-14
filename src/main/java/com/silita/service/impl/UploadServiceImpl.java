package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.SysFilesMapper;
import com.silita.model.DicAlias;
import com.silita.model.SysFiles;
import com.silita.service.*;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import com.silita.utils.PropertiesUtils;
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

import java.io.File;
import java.io.IOException;
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
    @Autowired
    SysFilesMapper sysFilesMapper;
    @Autowired
    PropertiesUtils propertiesUtils;
    @Autowired
    ICompanyAwardsService companyAwardsService;
    @Autowired
    ICompanyHighwayGradeService companyHighwayGradeService;
    @Autowired
    ICompanySecurityCertService companySecurityCertService;
    @Autowired
    ICompanyBadService companyBadService;

    @Override
    public Map<String, Object> analysisQuaGrade(MultipartFile file, Map<String, Object> param) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        Workbook workbook = getWorkbook(fileName, inputStream);
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
                        dicAlias.setCode(code);
                        dicAliasList.add(dicAlias);
                    }
                }
            }
        }
        List<DicAlias> aliasList = new ArrayList<>();
        if (null != dicAliasList && dicAliasList.size() > 0) {
            Integer count = 0;
            Map<String, Object> params = new HashMap<>();
            params.put("stdType", Constant.QUAL_LEVEL_PARENT);
            for (DicAlias alias : dicAliasList) {
                param.put("name", alias.getName());
                count = dicAliasMapper.queryAliasByName(param);
                if (count <= 0) {
                    aliasList.add(alias);
                }
            }
        }
        if (null != aliasList && aliasList.size() > 0) {
            qualService.addQuaAlias(aliasList);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
            return resultMap;
        }
        resultMap.put("code", Constant.CODE_WARN_400);
        resultMap.put("msg", Constant.MSG_WARN_400);
        return resultMap;
    }

    @Override
    public void insertZhaoBiaoFiles(MultipartFile[] files, SysFiles sysFiles) throws Exception {
        for (int i = 0; i < files.length; i++) {
            MultipartFile zhaobiaoFile = files[i];
            String fileName = zhaobiaoFile.getOriginalFilename();
            String savePathStr = System.currentTimeMillis() + fileName;
            File uploadFile = new File(propertiesUtils.getFilePath() + File.separatorChar + savePathStr);
            zhaobiaoFile.transferTo(uploadFile);
            //数据库保存上传记录
            sysFiles.setPkid(DataHandlingUtil.getUUID());
            sysFiles.setType("1");
//                sysFiles.setOssObj("");
            sysFiles.setFileName(fileName);
            sysFiles.setOrderNo(String.valueOf(i));
            sysFiles.setFilePath(propertiesUtils.getFilePath() + savePathStr);
            sysFilesMapper.insertSysFiles(sysFiles);
        }
    }

    @Override
    public Map<String, Object> uploadCompanyFile(MultipartFile file, String username, String tabType) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        Workbook workbook = getWorkbook(fileName, inputStream);
        if (null == workbook) {
            resultMap.put("code", Constant.CODE_WARN_404);
            resultMap.put("msg", Constant.MSG_WARN_404);
            return resultMap;
        }
        if ("win_record".equals(tabType)) {
            resultMap = companyAwardsService.batchImportCompanyAwards(workbook.getSheetAt(0), username, fileName);
        } else if ("highway_grade".equals(tabType)) {
            resultMap = companyHighwayGradeService.batchImportCompanyHighwayGrade(workbook.getSheetAt(0), username, fileName);
        } else if ("safety_permission_cert".equals(tabType)) {
            resultMap = companySecurityCertService.batchImportCompanySecurity(workbook.getSheetAt(0), username, fileName,tabType);
        } else if ("undesirable".equals(tabType)) {
            resultMap = companyBadService.batchImportCompanyBad(workbook.getSheetAt(0), username, fileName);
        }else if("safety_cert".equals(tabType)){
            resultMap = companySecurityCertService.batchImportCompanySafetyCert(workbook.getSheetAt(0), username, fileName,tabType);
        }
        return resultMap;
    }

    private Workbook getWorkbook(String fileName, InputStream inputStream) throws IOException {
        Workbook workbook = null;
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }
}

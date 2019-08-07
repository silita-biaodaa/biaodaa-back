package com.silita.service;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicCommon;
import com.silita.model.DicQua;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by zhushuai on 2019/7/16.
 */
public class AliasTest extends ConfigTest {

    @Autowired
    DicAliasMapper dicAliasMapper;
    @Autowired
    DicQuaMapper dicQuaMapper;
    @Autowired
    DicCommonMapper dicCommonMapper;

    @Test
    public void test() throws IOException {
        List<Map<String, Object>> qualList = dicQuaMapper.queryBenchName();
        Map<String, Object> param = new HashedMap();
        for (Map<String, Object> map : qualList) {
            StringBuffer qualCode = new StringBuffer(MapUtils.getString(map, "quaCode"));
            StringBuffer benchName = new StringBuffer(MapUtils.getString(map, "benchName"));
            param.put("name", "建筑行业（建筑工程）设计");
            int count = dicAliasMapper.queryAliasByName(param);
            if (count > 0) {
                continue;
            }
            DicAlias alias = new DicAlias();
            alias.setId(DataHandlingUtil.getUUID());
            alias.setName("建筑行业（建筑工程）设计");
            alias.setCode("alias_grade_" + PinYinUtil.cn2py("建筑行业（建筑工程）设计") + "_" + System.currentTimeMillis());
            alias.setStdCode(qualCode.toString());
            alias.setStdType("1");
            alias.setCreateBy("system");
            dicAliasMapper.insertDicAlias(alias);
        }

        Reader in = new FileReader("");
        in.read();
    }

    @Test
    public void gradeAlias() throws IOException {
        File file = new File("E:\\朱帅\\耀邦\\资质\\新建 XLSX 工作表.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        int rows = sheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            String value = cell.getStringCellValue();
            Cell ce = row.getCell(1);
            String val = ce.getStringCellValue();
            if (null != value && !"".equals(value)) {
                DicCommon dicCommon = dicCommonMapper.queryQuaGrade(value);
                if (null != dicCommon) {
                    DicAlias alias = new DicAlias();
                    alias.setId(DataHandlingUtil.getUUID());
                    alias.setName(val);
                    alias.setCode("alias_grade_" + PinYinUtil.cn2py(val) + "_" + System.currentTimeMillis());
                    alias.setStdCode(dicCommon.getCode());
                    alias.setStdType("3");
                    alias.setCreateBy("system");
                    dicAliasMapper.insertDicAlias(alias);
                }
            }
        }
    }
}

package com.silita.controller;

import com.silita.common.Constant;
import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.service.IUploadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class UploadController {

    private static Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    IUploadService uploadService;

    /**
     * 解析excel资质别名
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/quaAlias", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> uploadQuaAlias(HttpServletRequest request, MultipartFile file, String quaCode) {
        Map<String, Object> resultMap = new HashMap<>();
        if (null == file) {
            resultMap.put("code", Constant.CODE_WARN_404);
            resultMap.put("msg", Constant.MSG_WARN_404);
            return resultMap;
        }
        if(null == quaCode || "".equals(quaCode)){
            resultMap.put("code", Constant.CODE_WARN_404);
            resultMap.put("msg", Constant.MSG_WARN_404);
            return resultMap;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("username", JWTUtil.getUsername(request));
        param.put("quaCode", quaCode);
        try {
            return uploadService.analysisQuaGrade(file, param);
        } catch (Exception e) {
            LOGGER.info("excel解析失败!", e);
            resultMap.put("code", Constant.CODE_ERROR_500);
            resultMap.put("msg", Constant.MSG_ERROR_500);
            return resultMap;
        }
    }
}

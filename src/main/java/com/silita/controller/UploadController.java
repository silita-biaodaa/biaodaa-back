package com.silita.controller;

import com.silita.common.Constant;
import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.model.SysFiles;
import com.silita.service.IUploadService;
import com.silita.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class UploadController {

    private static Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    IUploadService uploadService;
    @Autowired
    PropertiesUtils propertiesUtils;


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

    @ResponseBody
    @RequestMapping(value = "/uploadZhaoBiaoFile", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> uploadZhaoBiaoFile(HttpServletRequest request, @RequestParam() MultipartFile[] files, String bizId) {
        Map resultMap = new HashMap<String, Object>();
        String userName = JWTUtil.getUsername(request);

        SysFiles sysFiles = new SysFiles();
        sysFiles.setBizId(bizId);
        sysFiles.setCreateBy(userName);
        try {
            uploadService.insertZhaoBiaoFiles(files, sysFiles);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg",Constant.MSG_SUCCESS);
        } catch (Exception e) {
            resultMap.put("code", Constant.CODE_ERROR_500);
            resultMap.put("msg", Constant.MSG_ERROR_500);
            LOGGER.error(e.getMessage());
        }
       return resultMap;
    }
}

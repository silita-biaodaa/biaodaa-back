package com.silita.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 上传文件service
 */
public interface IUploadService {

    /**
     * excel解析资质别名
     * @param file
     * @return
     */
    Map<String,Object> analysisQuaGrade(MultipartFile file,Map<String,Object> param) throws Exception;
}

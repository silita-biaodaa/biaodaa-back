package com.silita.service;

import com.silita.model.SysFiles;
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

    /**
     * 上传文件后添加文件记录
     * @param sysFiles
     */
    void insertZhaoBiaoFiles(MultipartFile[] files, SysFiles sysFiles) throws Exception;
}

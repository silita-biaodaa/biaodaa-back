package com.silita.service;

import com.silita.model.SysFiles;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.MarshalledObject;
import java.util.Map;

/**
 * 上传文件service
 */
public interface IUploadService {

    /**
     * excel解析资质别名
     *
     * @param file
     * @return
     */
    Map<String, Object> analysisQuaGrade(MultipartFile file, Map<String, Object> param) throws Exception;

    /**
     * 上传文件后添加文件记录
     *
     * @param sysFiles
     */
    void insertZhaoBiaoFiles(MultipartFile[] files, SysFiles sysFiles) throws Exception;

    /**
     * 上传企业信誉
     *
     * @param files
     * @param username
     * @return
     * @throws Exception
     */
    Map<String, Object> uploadCompanyFile(MultipartFile files, String username, String tabType) throws Exception;

    /**
     * 图片上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    Map<String, Object> uploadImage(MultipartFile file) throws Exception;
}

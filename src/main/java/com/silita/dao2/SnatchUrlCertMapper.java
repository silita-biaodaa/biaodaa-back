package com.silita.dao2;

import com.silita.model.SnatchUrlCert;
import com.silita.utils.MyMapper;

import java.util.List;

public interface SnatchUrlCertMapper extends MyMapper<SnatchUrlCert> {

    /**
     * 批量添加公告资质
     * @param snatchUrlCerts
     * @return
     */
    Integer batchInsertSnatchUrlCert(List<SnatchUrlCert> snatchUrlCerts);

    /**
     * 批量更新公告资质
     * @param snatchUrlCerts
     * @return
     */
    Integer batchUpdateSnatchUrlCert(List<SnatchUrlCert> snatchUrlCerts);

    /**
     * 根据pkid删除资质
     * @param snatchUrlCerts
     * @return
     */
    Integer deleteSnatchUrlCertById(SnatchUrlCert snatchUrlCerts);

    /**
     * 根据公告pkid删除资质
     * @param snatchUrlCerts
     * @return
     */
    Integer deleteSnatchUrlCertByContId(SnatchUrlCert snatchUrlCerts);
}
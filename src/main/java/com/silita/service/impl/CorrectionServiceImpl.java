package com.silita.service.impl;

import com.silita.dao2.*;
import com.silita.model.*;
import com.silita.service.ICorrectionService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-18 14:26
 */
@Service("correctionService")
public class CorrectionServiceImpl extends AbstractService implements ICorrectionService {

    @Autowired
    private AllZhMapper allZhMapper;
    @Autowired
    private AptitudeDictionaryMapper aptitudeDictionaryMapper;

    @Autowired
    private SnatchurlMapper snatchurlMapper;
    @Autowired
    private ZhaobiaoDetailOthersMapper zhaobiaoDetailOthersMapper;
    @Autowired
    private ZhongbiaoDetailOthersMapper zhongbiaoDetailOthersMapper;
    @Autowired
    private SnatchUrlCertMapper snatchUrlCertMapper;

    @Override
    public List<AllZh> ListAllZhByName(AllZh allZh) {
        return allZhMapper.listAllZhByName(allZh);
    }

    @Override
    public Map<String, Object> listSnatchurl(Snatchurl snatchurl) {
        if(!"hunan".equals(snatchurl.getSource())) {
            Map result = new HashMap<String, Object>();
            result.put("datas", snatchurlMapper.listSnatchurl(snatchurl));
            result.put("total", snatchurlMapper.countSnatchurl(snatchurl));
            return super.handlePageCount(result, snatchurl);
        }
        return null;
    }

    @Override
    public Integer updateSnatchurlIsShowById(Snatchurl snatchurl) {
        return snatchurlMapper.updateSnatchurlIsShowById(snatchurl);
    }

    @Override
    public List<ZhaobiaoDetailOthers> listZhaobiaoDetailBySnatchUrlId(ZhaobiaoDetailOthers zhaobiaoDetailOthers) {
        return zhaobiaoDetailOthersMapper.listZhaobiaoDetailOthersBySnatchUrlId(zhaobiaoDetailOthers);
    }

    @Override
    public Integer updateZhaobiaoDetailById(ZhaobiaoDetailOthers zhaobiaoDetailOthers) {
        List<SnatchUrlCert> snatchUrlCerts = zhaobiaoDetailOthers.getSnatchUrlCerts();
        if(snatchUrlCerts.size() > 0) {
            for (int i = 0; i < snatchUrlCerts.size(); i++) {
                SnatchUrlCert snatchUrlCert = snatchUrlCerts.get(i);
                if(StringUtils.isEmpty(snatchUrlCert.getTempMainUuid())) {
                    //资质id为空时，删除本条资质
                    snatchUrlCertMapper.deleteSnatchUrlCertById(snatchUrlCert);
                    snatchUrlCerts.remove(i);
                } else {
                    AptitudeDictionary aptitudeDictionary = aptitudeDictionaryMapper.getAptitudeDictionaryByMajorUUid(snatchUrlCert.getTempMainUuid());
                    snatchUrlCert.setCertificate(aptitudeDictionary.getMajorName() + CommonUtil.spellRank(snatchUrlCert.getTempRank()));
                    snatchUrlCert.setCertificateUuid(CommonUtil.spellUuid(snatchUrlCert.getTempMainUuid(), snatchUrlCert.getTempRank()));
                }
            }
            if(snatchUrlCerts.size() > 0) {
                //修改资质
                snatchUrlCertMapper.batchUpdateSnatchUrlCert(snatchUrlCerts);
            }
        }
        //修改招标编辑明细
        zhaobiaoDetailOthersMapper.updateZhaobiaoDetailOthersById(zhaobiaoDetailOthers);
        Snatchurl snatchurl = new Snatchurl();
        snatchurl.setTitle(zhaobiaoDetailOthers.getProjName());
        snatchurl.setSource(zhaobiaoDetailOthers.getSource());
        snatchurl.setId(zhaobiaoDetailOthers.getId());
        //修改公告主表
        snatchurlMapper.updateSnatchurlById(snatchurl);
        return null;
    }

}

package com.silita.service.impl;

import com.silita.dao2.SnatchurlMapper;
import com.silita.model.Snatchurl;
import com.silita.service.ICorrectionService;
import com.silita.service.abs.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private SnatchurlMapper snatchurlMapper;

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

}

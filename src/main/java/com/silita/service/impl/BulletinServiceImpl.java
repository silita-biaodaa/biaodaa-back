package com.silita.service.impl;

import com.silita.dao.DicCommonMapper;
import com.silita.model.DicCommon;
import com.silita.service.IBulletinService;
import com.silita.utils.DataHandlingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:52
 */
@Service("bulletinService")
public class BulletinServiceImpl implements IBulletinService {

    @Autowired
    DicCommonMapper dicCommonMapper;

    @Override
    public Map<String, String> listFixedEditData() {
        Map result = new HashMap<String, Object>();
        result.put("bidOpeningPersonnel", DataHandlingUtil.getBidOpeningPersonnel());
        result.put("projectType", DataHandlingUtil.getProjectType());
        result.put("biddingType", DataHandlingUtil.getBiddingType());
        result.put("filingRequirements", DataHandlingUtil.getFilingRequirements());
        result.put("biddingStatus", DataHandlingUtil.getBiddingStatus());
        return result;
    }

    @Override
    public List<String> listDicCommonNameByType(DicCommon dicCommon) {
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pbmode");
        return dicCommonMapper.listDicCommonNameByType(dicCommon);
    }

}

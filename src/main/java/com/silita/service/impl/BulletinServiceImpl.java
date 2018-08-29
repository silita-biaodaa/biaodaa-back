package com.silita.service.impl;

import com.silita.dao.DicCommonMapper;
import com.silita.dao.TwfDictMapper;
import com.silita.model.DicCommon;
import com.silita.service.IBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Autowired
    TwfDictMapper twfDictMapper;

    @Override
    @Cacheable(value="TwfDictNameCache")
    public Map<String, String> listFixedEditData() {
        Map result = new HashMap<String, Object>();
        result.put("bidOpeningPersonnel", twfDictMapper.listTwfDictNameByType(3));
        result.put("projectType", twfDictMapper.listTwfDictNameByType(4));
        result.put("biddingType", twfDictMapper.listTwfDictNameByType(5));
        result.put("filingRequirements", twfDictMapper.listTwfDictNameByType(6));
        result.put("biddingStatus", twfDictMapper.listTwfDictNameByType(7));
        return result;
    }

    @Override
    public List<String> listDicCommonNameByType(DicCommon dicCommon) {
        String type = dicCommon.getType();
        dicCommon.setType(type + "_pbmode");
        return dicCommonMapper.listDicCommonNameByType(dicCommon);
    }

}

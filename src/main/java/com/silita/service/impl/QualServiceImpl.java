package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.model.DicAlias;
import com.silita.model.DicQua;
import com.silita.service.IQualService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资质实现类
 */
@Service
public class QualServiceImpl implements IQualService {

    @Autowired
    DicQuaMapper dicQuaMapper;
    @Autowired
    DicAliasMapper dicAliasMapper;

    @Override
    public void addQual(DicQua qua, String username) {
        if (null != qua.getParentId()) {
            qua.setLevel(Constant.QUAL_LEVEL_SUB);
        } else {
            qua.setLevel(Constant.QUAL_LEVEL_PARENT);
        }
        if (null != qua.getId()) {
            qua.setUpdateTime(new Date());
            qua.setUpdateBy(username);
            dicQuaMapper.updateDicQual(qua);
        } else {
            qua.setCreateBy(username);
            String qualCode = "qual" + "_" + PinYinUtil.cn2py(qua.getQuaName()) + "_" + System.currentTimeMillis();
            qua.setQuaCode(qualCode);
            qua.setBizType(Constant.BIZ_TYPE_ALL);
            qua.setId(DataHandlingUtil.getUUID());
            qua.setCreateTime(new Date());
            dicQuaMapper.insertDicQual(qua);
        }
    }

    @Override
    public void delQual(String id) {
        dicQuaMapper.delDicQual(id);
    }

    @Override
    public List<Map<String, Object>> getQualCateList() {
        return dicQuaMapper.queryQualCateList();
    }

    @Override
    public List<DicQua> getDicQuaList(Map<String, Object> param) {
        return dicQuaMapper.queryDicQuaList(param);
    }

    @Override
    public void aliasAdd(DicAlias alias) {
        alias.setId(DataHandlingUtil.getUUID());
        String code = "alias_qual_" + PinYinUtil.cn2py(alias.getName()) + "_" + System.currentTimeMillis();
        alias.setCode(code);
        alias.setStdType(Constant.QUAL_LEVEL_PARENT);
        dicAliasMapper.insertDicAlias(alias);
    }
}

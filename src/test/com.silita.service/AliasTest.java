package com.silita.service;

import com.silita.dao.DicAliasMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.model.DicAlias;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.stringUtils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zhushuai on 2019/7/16.
 */
public class AliasTest extends ConfigTest {

    @Autowired
    DicAliasMapper dicAliasMapper;
    @Autowired
    DicQuaMapper dicQuaMapper;

    @Test
    public void test() {
        List<Map<String, Object>> qualList = dicQuaMapper.queryBenchName();
        Map<String, Object> param = new HashedMap();
        for (Map<String, Object> map : qualList) {
            StringBuffer qualCode = new StringBuffer(MapUtils.getString(map, "quaCode"));
            StringBuffer benchName = new StringBuffer(MapUtils.getString(map, "benchName"));
            param.put("name", "工程设计综合");
            int count = dicAliasMapper.queryAliasByName(param);
            if (count > 0) {
                continue;
            }
            DicAlias alias = new DicAlias();
            alias.setId(DataHandlingUtil.getUUID());
            alias.setName(benchName.toString());
            alias.setCode("alias_grade_" + PinYinUtil.cn2py(benchName.toString()) + "_" + System.currentTimeMillis());
            alias.setStdCode(qualCode.toString());
            alias.setStdType("1");
            alias.setCreateBy("system");
            dicAliasMapper.insertDicAlias(alias);
        }
    }

}

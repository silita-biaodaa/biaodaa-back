package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.common.IsNullCommon;
import com.silita.dao.IRoleMapper;
import com.silita.dao.TbRoleModuleMapper;
import com.silita.model.TbRole;
import com.silita.service.IRoleService;
import com.silita.service.abs.AbstractService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends AbstractService implements IRoleService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private IRoleMapper roleMapper;
    @Autowired
    private TbRoleModuleMapper tbRoleModuleMapper;

    /**
     * 添加角色 及 赋角色权限
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> addRole(Map<String, Object> param) {
        IsNullCommon.isNull(param);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String desc1 = roleMapper.queryDesc(param);//获取角色名称
            if (StringUtil.isNotEmpty(desc1)) {//判断角色是否存在
                resultMap.put("msg", Constant.MSG_DESC);
                resultMap.put("code", Constant.CODE_DESC);
                return resultMap;
            }
            roleMapper.addRole(param);//添加角色
            String ids = MapUtils.getString(param, "ids");
            Integer integer = roleMapper.queryRoleMaxRid();//获取最大角色id
            if (StringUtil.isNotEmpty(ids)) {
                String[] split = ids.split(",");
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (String s : split) {
                    String[] split1 = s.split("\\/");
                    Map<String, Object> maps = new HashMap<>();
                    maps.put("rid", integer);
                    maps.put("id", split1[0]);
                    maps.put("optiond", split1[1]);
                    mapList.add(maps);
                }
                param.put("list", mapList);
                tbRoleModuleMapper.insertRoleModule(param);//添加管理员权限
            }
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        } catch (Exception e) {
            logger.error("添加角色及赋权：", e);
        }
        return resultMap;
    }

    /**
     * 修改角色及权限
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> updateRole(Map<String, Object> param) {
        IsNullCommon.isNull(param);
        Map<String, Object> resultMap = new HashMap<>();
        String desc = roleMapper.queryDescRid(param);//根据id查询角色名称
        String desc1 = MapUtils.getString(param, "desc");

        if (desc.equals(desc1)) {
            roleMapper.updateRole(param);//修改角色
            roleModule(param);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
            return resultMap;
        } else {
            String roleName = roleMapper.queryDesc(param);//获取角色名称
            if (StringUtil.isNotEmpty(roleName)) {//判断角色是否存在
                resultMap.put("msg", Constant.MSG_DESC);
                resultMap.put("code", Constant.CODE_DESC);
                return resultMap;
            }
            roleMapper.updateRole(param);//修改角色
            roleModule(param);//编辑权限
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        }
        return resultMap;
    }

    /**
     * 编辑权限
     *
     * @param param
     */
    public void roleModule(Map<String, Object> param) {
        String rid = MapUtils.getString(param, "rid");
        String ids = MapUtils.getString(param, "ids");
        //先删除管理员权限
        tbRoleModuleMapper.deleteRoleModule(param);
        if (StringUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (String s2 : split) {
                String[] split1 = s2.split("\\/");
                Map<String, Object> maps = new HashMap<>();
                maps.put("id", split1[0]);
                maps.put("optiond", split1[1]);
                maps.put("rid", rid);
                mapList.add(maps);
            }
            param.put("list", mapList);
            //再添加新的权限
            tbRoleModuleMapper.insertRoleModule(param);
        }
    }

    /**
     * 查询角色列表
     *
     * @param role
     * @return
     */
    @Override
    public Map<String, Object> getRoleList(TbRole role) {
        String desc = role.getDesc();
        if (StringUtil.isEmpty(desc)) {
            role.setDesc("");
        }
        Map resultMap = new HashMap();
        try {
            resultMap.put("list", roleMapper.queryRoleList(role));
            resultMap.put("total", roleMapper.queryRoleListCount(role));
        } catch (Exception e) {
            logger.error("查询角色列表:", e);
        }
        return super.handlePageCount(resultMap, role);
    }

    /**
     * 获取所有角色
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getRoleAll() {
        return roleMapper.queryRoleAll();
    }


}

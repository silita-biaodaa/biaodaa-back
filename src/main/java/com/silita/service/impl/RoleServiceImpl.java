package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.IRoleMapper;
import com.silita.dao.SysLogsMapper;
import com.silita.dao.TbRoleModuleMapper;
import com.silita.model.TbRole;
import com.silita.service.IRoleService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends AbstractService implements IRoleService {
    @Autowired
    private IRoleMapper roleMapper;
    @Autowired
    private TbRoleModuleMapper tbRoleModuleMapper;
    @Autowired
    private SysLogsMapper sysLogsMapper;

    /**
     * 添加角色 及 赋角色权限
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> addRole(Map<String, Object> param) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            String desc1 = roleMapper.queryDesc(param);
            if (StringUtil.isNotEmpty(desc1)) {
                resultMap.put("msg", Constant.MSG_DESC);
                resultMap.put("code", Constant.CODE_DESC);
                return resultMap;
            }
            roleMapper.addRole(param);
            String desc = MapUtils.getString(param, "desc");
            String ids = MapUtils.getString(param, "ids");
            Integer integer = roleMapper.queryRoleMaxRid();

            if (StringUtil.isNotEmpty(ids)) {
                String[] split = ids.split(",");
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (String s : split) {
                    String[] split1 = s.split("\\/");
                    Map<String, Object> maps = new HashMap<>();
                    maps.put("rid", integer);
                    maps.put("id", split1[0]);
                    maps.put("optiond",split1[1]);
                    mapList.add(maps);
                }
                param.put("list", mapList);
                tbRoleModuleMapper.insertRoleModule(param);
            }
            param.put("pid", CommonUtil.getUUID());
            param.put("optType", "角色信息");
            param.put("optDesc", "添加角色:" + desc);
            param.put("operand", "");
            sysLogsMapper.insertLogs(param);//添加操作日志
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 修改角色及权限
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> updateRole(Map<String, Object> param) {
        Map<String,Object> resultMap = new HashMap<>();
        String desc = roleMapper.queryDescRid(param);
        String desc1 = MapUtils.getString(param, "desc");

        if(desc.equals(desc1)){
            roleMapper.updateRole(param);
            roleModule(param);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
            return resultMap;
        }else{
            String s = roleMapper.queryDesc(param);
            if (StringUtil.isNotEmpty(s)) {
                resultMap.put("msg", Constant.MSG_DESC);
                resultMap.put("code", Constant.CODE_DESC);
                return resultMap;
            }
            roleMapper.updateRole(param);
            roleModule(param);
            resultMap.put("code", Constant.CODE_SUCCESS);
            resultMap.put("msg", Constant.MSG_SUCCESS);
        }
        return resultMap;
    }

    /**
     * 编辑权限
     * @param param
     */
    public void roleModule(Map<String,Object> param){
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
                maps.put("optiond",split1[1]);
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
     * @param role
     * @return
     */
    @Override
    public Map<String, Object> getRoleList(TbRole role) {
        Map resultMap = new HashMap();
        try {
            resultMap.put("list", roleMapper.queryRoleList(role));
            resultMap.put("total", roleMapper.queryRoleListCount(role));
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.handlePageCount(resultMap,role);
    }

    /**
     * 获取所有角色
     * @return
     */
    @Override
    public List<Map<String, Object>> getRoleAll() {
        return roleMapper.queryRoleAll();
    }


}

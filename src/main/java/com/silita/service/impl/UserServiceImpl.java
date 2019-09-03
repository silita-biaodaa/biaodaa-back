package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.dao.IUserMapper;
import com.silita.dao.TbUserMapper;
import com.silita.model.TbPermission;
import com.silita.model.TbRole;
import com.silita.model.TbUser;
import com.silita.service.IUserService;
import com.silita.service.abs.AbstractService;
import com.silita.service.mongodb.MongodbService;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
@Service("userService")
public class UserServiceImpl extends AbstractService implements IUserService {

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    MongodbService mongodbService;

    int hashIterations = 2;

    @Override
    public Integer existssqlPhone(TbUser tbUser) {
        Map<String,Object> param = new HashMap<>();
        param.put("phone",tbUser.getPhone());
        Integer integer = tbUserMapper.querySingleUserPhone(param);
        return integer;
    }

    @Override
    public TbUser login(TbUser tbUser) {
        Object md5Password = new SimpleHash("MD5", tbUser.getPassword(), tbUser.getPhone(), 2);
        tbUser.setPassword(md5Password.toString());
        TbUser login = tbUserMapper.login(tbUser);
        return login;
    }

    @Override
    public TbUser getUserByUserName(String userName) {
        return this.userMapper.getUserByUserName(userName);
    }

    @Override
    public Map<String, Object> getRolesAndPermissionsByUserName(String userName) {
        TbRole role = null;
        TbPermission permission = null;
        Set<String> roles = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        Map<String, Object> map = new HashMap<String, Object>();
        TbUser vo = this.userMapper.getRolesAndPermissionsByUserName(userName);
        for (int i = 0; i < vo.getRoles().size(); i++) {
            role = vo.getRoles().get(i);
            roles.add(role.getRoleName());
            for (int j = 0; j < role.getPermissions().size(); j++) {
                permission = role.getPermissions().get(i);
                permissions.add(permission.getPermissionName());
            }
        }
        map.put("allRoles", roles);
        map.put("allPermissions", permissions);
        return map;
    }

    /**
     * 用户锁定或解锁
     *
     * @param param
     */
    @Override
    public void updateLock(Map<String, Object> param) {
        tbUserMapper.updateLock(param);
    }

    /**
     * 修改密码
     *
     * @param param
     */
    @Override
    public Map<String, Object> updatePassword(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer integer = tbUserMapper.querySingleUserPhone(param);
            if (null == integer || integer == 0) {
                resultMap.put("msg", Constant.MSG_PHONE);
                resultMap.put("code", Constant.CODE_PHONE);
                return resultMap;
            }
            String password = MapUtils.getString(param, "password");
            String phone = MapUtils.getString(param, "phone");
            hashIterations = 2;
            Object obj = new SimpleHash("MD5", password, phone, hashIterations);
            param.put("password", obj.toString());
            Integer integer1 = tbUserMapper.querySingleUserPassword(param);
            if (null == integer1 || integer1 == 0) {
                resultMap.put("msg", Constant.MSG_PASSWORD);
                resultMap.put("code", Constant.CODE_PASSWORD);
                return resultMap;
            }
            String newPassword = MapUtils.getString(param, "newpassword");
            Object md5Password = new SimpleHash("MD5", newPassword, phone, hashIterations);
            param.put("newpassword", md5Password.toString());
            tbUserMapper.updatePassword(param);
            resultMap.put("msg", Constant.CODE_SUCCESS);
            resultMap.put("code", Constant.MSG_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 重置密码
     *
     * @param param
     */
    @Override
    public void updateResetPassword(Map<String, Object> param) {
        String password = MapUtils.getString(param, "password");
        String phone = MapUtils.getString(param, "phone");
        Object md5Password = new SimpleHash("MD5", password, phone, hashIterations);
        param.put("password", md5Password.toString());//md5加密
        tbUserMapper.updateResetPassword(param);
    }

    /**
     * 账号管理查询及筛选
     *
     * @param tbUser
     * @return
     */
    @Override
    public Map<String, Object> getAccountList(TbUser tbUser) {
        String phone = tbUser.getPhone();
        if (StringUtil.isEmpty(phone)) {
            tbUser.setPhone("");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", tbUserMapper.queryAccountList(tbUser));
        resultMap.put("total", tbUserMapper.queryAccountListCount(tbUser));
        return super.handlePageCount(resultMap, tbUser);
    }

    /**
     *
     * @param param
     */
    @Override
    public void updateUser(Map<String, Object> param) {
       tbUserMapper.updateUser(param);
    }

    @Override
    public void addAdministrator(Map<String, Object> param) {
        tbUserMapper.insertAdministrator(param);
        Integer integer = tbUserMapper.queryMaxUId();
    }
}

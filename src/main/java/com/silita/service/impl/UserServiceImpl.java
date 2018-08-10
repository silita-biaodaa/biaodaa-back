package com.silita.service.impl;

import com.silita.dao.IUserMapper;
import com.silita.model.TbPermission;
import com.silita.model.TbRole;
import com.silita.model.TbUser;
import com.silita.service.IUserService;
import com.silita.service.abs.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @Override
    public TbUser getUserByUserName(String userName) {
        return this.userMapper.getUserByUserName(userName);
    }

    @Override
    public Map<String, Object> getRolesAndPermissionsByUserName(String userName) {
        TbRole role = null;
        TbPermission permission = null;
        Set<String> roles = new HashSet<String>();
        Set<String > permissions = new HashSet<String>();
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

}

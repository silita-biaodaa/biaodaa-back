package com.silita.commons.shiro;

import com.silita.commons.shiro.token.JWTToken;
import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.model.TbUser;
import com.silita.service.IUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
public class MyRealm extends AuthorizingRealm {
    private Logger log = Logger.getLogger(MyRealm.class);

    @Autowired
    private IUserService userService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        Map<String, Object> map = null;
        try {
            map = this.userService.getRolesAndPermissionsByUserName(userName);
            auth.setRoles((Set<String>) map.get("allRoles"));
            auth.setStringPermissions((Set<String>) map.get("allPermissions"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth;
    }

    /**
     * 认证
     *
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得userName，用于和数据库进行对比
        String userName = JWTUtil.getUsername(token);
        TbUser vo = this.userService.getUserByUserName(userName);
        if (vo == null) {
            throw new UnknownAccountException("该用户名称不存在！");
        } else if (vo.getLock() == null || vo.getLock().equals(1)) {
            throw new UnknownAccountException("该用户已经被锁定了！");
        } else if(!JWTUtil.verify(token, userName, vo.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        } else {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token, token, "MyRealm");
            return authcInfo;
        }
    }
}

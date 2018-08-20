package com.silita.commons.shiro.filter;

import com.silita.common.Constant;
import com.silita.commons.shiro.token.JWTToken;
import com.silita.utils.CrossDomainHandler;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-16 8:55
 * 自定义认证过滤器 代替原authc拦截
 */
public class JWTAuthenticationFilter extends AuthenticatingFilter {

    //不知道怎么获取applicationContext-shiro配置文件里面的，现在写死
    private final String loginUrl = "/authorize/login";
    private final String druidUrl = "/manager/druid";

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("Authorization");
        return new JWTToken(token);
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        String previous = "/authorize/login";
        if (previous != null) {
            this.appliedPaths.remove(previous);
        }

        super.setLoginUrl(loginUrl);

        this.appliedPaths.put(this.getLoginUrl(), (Object)null);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String contextPath = WebUtils.getPathWithinApplication(WebUtils.toHttp(servletRequest));
        //登录、druid监控不拦截
        if(contextPath.contains(loginUrl) || contextPath.contains(druidUrl)) {
            return true;
        }
        AuthenticationToken token = this.createToken(servletRequest, servletResponse);
        if(token.getPrincipal() == null) {
            unauthenticated(servletResponse);
            return false;
        } else {
            try {
                Subject subject = this.getSubject(servletRequest, servletResponse);
                subject.login(token);
                return true;
            } catch (AuthenticationException e) {
                onLoginFail(servletResponse);
                return false;
            }
        }
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Map result = CrossDomainHandler.preHandle(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) result.get("request");
        HttpServletResponse httpServletResponse = (HttpServletResponse) result.get("response");
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(httpServletRequest, httpServletResponse);
    }

    /**
     * 没有token
     * @param response
     */
    private void unauthenticated(ServletResponse response){
        try {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.getWriter().write("{\"code\":"+Constant.CODE_WARN_401+", \"msg\":\""+Constant.MSG_WARN_401+"\"}");
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //token验证失败
    private void onLoginFail(ServletResponse response){
        try {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.getWriter().write("{\"code\":"+Constant.CODE_WARN_402+", \"msg\":\""+Constant.MSG_WARN_402+"\"}");
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

package com.silita.utils.exceptionHandler;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
@ControllerAdvice
public class UnauthorizedExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Map<String, Object> handle401() {
        Map result = new HashMap();
        result.put("code", 0);
        result.put("data", "您没有权限！");
        return result;
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public Map<String, Object> authenticationException(Exception ex) {
        Map result = new HashMap();
        result.put("code", 0);
        result.put("data", "请登录后再访问！");
        return result;
    }
}

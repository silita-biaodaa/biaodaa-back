package com.silita.commons.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/7/18.
 */
@ControllerAdvice
public class UnauthorizedExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String handle401() {
       return "403";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String globalException(Exception ex) {
        return "mei renzhen";
//        return ex.getMessage();
    }
}

package com.silita.utils;

import com.silita.commons.shiro.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-14 16:35
 */
@Component("propertiesUtils")
public class PropertiesUtils {
    @Value("${token.lifeCycle}")
    private String lifeCycle;

    @Value("${token.publicKey}")
    private String publicKey;

    @PostConstruct
    public void init() {
        getLifeCycle();
        getPublicKey();
        JWTUtil.lifeCycleTemp = getLifeCycle();
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public String getPublicKey() {
        return publicKey;
    }
}

package com.silita.utils;

import com.silita.commons.elasticSearch.InitESClient;
import com.silita.commons.shiro.utils.JWTUtil;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

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

    @Value("${upload.filePath}")
    private String filePath;

    @Value("${elasticSearch.ip}")
    private String ip;
    @Value("${elasticSearch.clusterName}")
    private String clusterName;
    @Value("${elasticSearch.port}")
    private int port;
    @Value("${localhost.server}")
    private String localhostServer;
    @Value("${server}")
    private String server;



    @PostConstruct
    public void init() {
        getLifeCycle();
        getPublicKey();
        JWTUtil.tokenLifeCycle = getLifeCycle();

        getFilePath();

        InitESClient.ip = getIp();
        InitESClient.clusterName = getClusterName();
        InitESClient.port = getPort();
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getIp() {
        return ip;
    }

    public String getClusterName() {
        return clusterName;
    }

    public int getPort() {
        return port;
    }

    public String getLocalhostServer() {
        return localhostServer;
    }

    public String getServer() {
        return server;
    }






    private static Logger logger = Logger.getLogger(PropertiesUtils.class);

    public static final String[] PROPERTIES = new String[] {"config/biaodaa-back.properties"};

    private static Properties properties = new Properties();


    static {
        try {
            for (String str : PROPERTIES) {
                properties.load(getResourceAsStream(str));
            }
        } catch (IOException e) {
            logger.error("配置文件加载失败！", e);
        }
    }

    private PropertiesUtils() {
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }


    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    private static ClassLoader getContextClassLoader() {
        ClassLoader classLoader = null;

        if (classLoader == null) {
            try {
                Method method = Thread.class.getMethod("getContextClassLoader", (Class[]) null);
                try {
                    classLoader = (ClassLoader) method.invoke(Thread.currentThread(), (Class[]) null);
                } catch (IllegalAccessException e) {
                    ; // ignore
                } catch (InvocationTargetException e) {

                    if (e.getTargetException() instanceof SecurityException) {
                        ; // ignore
                    } else {
                        throw new LogConfigurationException("Unexpected InvocationTargetException",
                                e.getTargetException());
                    }
                }
            } catch (NoSuchMethodException e) {
                // Assume we are running on JDK 1.1
                ; // ignore
            }
        }

        if (classLoader == null) {
            classLoader = PropertiesUtils.class.getClassLoader();
        }

        // Return the selected class loader
        return classLoader;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static InputStream getResourceAsStream(final String name) {
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                ClassLoader threadCL = getContextClassLoader();

                if (threadCL != null) {
                    return threadCL.getResourceAsStream(name);
                } else {
                    return ClassLoader.getSystemResourceAsStream(name);
                }
            }
        });
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtils.getProperty("mongodb.order.ip"));
    }
}

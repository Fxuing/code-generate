package com.obanks.codegenerate.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.12 16:21
 * @Description:
 */
@Slf4j
public class PropertiesUtils {

    public static String getValue(String name) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("application.yml");
            return properties.getProperty(name, "");
        } catch (IOException e) {
            log.error("get value error");
        }
        return "";
    }
}

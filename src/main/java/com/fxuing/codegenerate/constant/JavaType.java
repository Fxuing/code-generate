package com.fxuing.codegenerate.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.11 18:42
 * @Description:
 */
public class JavaType {
    private static final Map<String, String> TYPE = new HashMap<>(16);

    static {
        TYPE.put("LocalDateTime", "java.time.LocalDateTime");
    }

    public static String get(String type) {
        return TYPE.get(type);
    }
}

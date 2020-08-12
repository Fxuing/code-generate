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
    public static final String
            LOCAL_DATE_TIME = "LocalDateTime",
            PAGINATE = "Paginate",
            REPOSITORY = "Repository",
            LIST = "List",
            AUTOWIRED = "Autowired",
            SERVICE = "Service",
            PROPAGATION = "Propagation",
            TRANSACTIONAL = "Transactional",
            POST_MAPPING = "PostMapping",
            REQUEST_MAPPING = "RequestMapping",
            REST_CONTROLLER = "RestController";

    static {
        TYPE.put(LOCAL_DATE_TIME, "java.time.LocalDateTime");
        TYPE.put(PAGINATE, "com.obanks.common.Paginate");
        TYPE.put(REPOSITORY, "org.springframework.stereotype.Repository");
        TYPE.put(LIST, "java.util.List");
        TYPE.put(AUTOWIRED, "org.springframework.beans.factory.annotation.Autowired");
        TYPE.put(SERVICE, "org.springframework.stereotype.Service");
        TYPE.put(PROPAGATION, "org.springframework.transaction.annotation.Propagation");
        TYPE.put(TRANSACTIONAL, "org.springframework.transaction.annotation.Transactional");
        TYPE.put(POST_MAPPING, "org.springframework.web.bind.annotation.PostMapping");
        TYPE.put(REQUEST_MAPPING, "org.springframework.web.bind.annotation.RequestMapping");
        TYPE.put(REST_CONTROLLER, "org.springframework.web.bind.annotation.RestController");
    }

    public static String get(String type) {
        return TYPE.get(type);
    }
}

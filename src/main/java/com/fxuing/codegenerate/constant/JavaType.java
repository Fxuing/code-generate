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
            DATE = "Date",
            PAGINATE = "Paginate",
            REPOSITORY = "Repository",
            LIST = "List",
            BASE_MAPPER = "BaseMapper",
            I_Service = "IService",
            SERVICE_IMPL = "ServiceImpl",
            AUTOWIRED = "Autowired",
            SERVICE = "Service",
            PROPAGATION = "Propagation",
            TRANSACTIONAL = "Transactional",
            POST_MAPPING = "PostMapping",
            REQUEST_MAPPING = "RequestMapping",
            REST_CONTROLLER = "RestController",
            REQUEST = "Request",
            RESULT = "Result",
            BigDecimal = "BigDecimal",
            PARAM = "Param",
            SERIALIZABLE = "Serializable";

    static {
        TYPE.put(LOCAL_DATE_TIME, "java.time.LocalDateTime");
        TYPE.put(DATE, "java.time.LocalDateTime");
        TYPE.put(PAGINATE, "com.fxuing.codegenerate.common.Paginate");
        TYPE.put(REPOSITORY, "org.springframework.stereotype.Repository");
        TYPE.put(LIST, "java.util.List");
        TYPE.put(AUTOWIRED, "org.springframework.beans.factory.annotation.Autowired");
        TYPE.put(SERVICE, "org.springframework.stereotype.Service");
        TYPE.put(PROPAGATION, "org.springframework.transaction.annotation.Propagation");
        TYPE.put(TRANSACTIONAL, "org.springframework.transaction.annotation.Transactional");
        TYPE.put(POST_MAPPING, "org.springframework.web.bind.annotation.PostMapping");
        TYPE.put(REQUEST_MAPPING, "org.springframework.web.bind.annotation.RequestMapping");
        TYPE.put(REST_CONTROLLER, "org.springframework.web.bind.annotation.RestController");
        TYPE.put(REQUEST, "com.fxuing.codegenerate.common.Request");
        TYPE.put(RESULT, "com.fxuing.codegenerate.common.Result");
        TYPE.put(BigDecimal, "java.math.BigDecimal");
        TYPE.put(SERIALIZABLE, "java.io.Serializable");
        TYPE.put(PARAM, "org.apache.ibatis.annotations.Param");
        TYPE.put(BASE_MAPPER, "com.baomidou.mybatisplus.core.mapper.BaseMapper");
        TYPE.put(I_Service, "com.baomidou.mybatisplus.extension.service.IService");
        TYPE.put(SERVICE_IMPL, "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
    }

    public static String get(String type) {
        return TYPE.get(type);
    }
}

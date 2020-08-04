package com.fxuing.codegenerate.service;

import com.fxuing.codegenerate.core.config.GenerateConfig;

import java.sql.SQLException;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 14:16
 * @Description:
 */
public interface GenerateService {
    /**
     * 配置
     *
     * @param config
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:52
     * @Description:
     */
    void setConfig(GenerateConfig config);

    /**
     * 生成模型
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:52
     * @Description:
     * @throws SQLException SQL异常
     */
    void generateModel(String tableName) throws SQLException;

    /**
     * 生成dao
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:52
     * @Description:
     */
    void generateDao(String tableName);

    /**
     * 生成 xml 映射
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     * @throws SQLException SQL异常
     */
    void generateMapper(String tableName) throws SQLException;

    /**
     * 生成service
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     */
    void generateService(String tableName);

    /**
     * 生成实现类
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     */
    void generateServiceImpl(String tableName);

    /**
     * 生成controller
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     */
    void generateController(String tableName);
}

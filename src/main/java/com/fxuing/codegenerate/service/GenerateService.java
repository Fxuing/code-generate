package com.fxuing.codegenerate.service;

import com.fxuing.codegenerate.core.config.GenerateConfig;

import java.sql.SQLException;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 14:16
 * @Description:
 */
public interface GenerateService {
    void setConfig(GenerateConfig config);
    /** 生成模型 */
    void generateModel(String tableName) throws SQLException;
    /** 生成dao */
    void generateDao(String tableName);
    /** 生成mapper */
    void generateMapper(String tableName) throws SQLException;

    void generateService(String tableName);
    void generateServiceImpl(String tableName);
    void generateController(String tableName);
}

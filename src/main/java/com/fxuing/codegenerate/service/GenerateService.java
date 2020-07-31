package com.fxuing.codegenerate.service;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 14:16
 * @Description:
 */
public interface GenerateService {
    /** 生成模型 */
    void generateModel(String tableName);
    /** 生成dao */
    void generateDao(String tableName);
    /** 生成mapper */
    void generateMapper(String tableName);

    void generateService(String tableName);
    void generateServiceImpl(String tableName);
    void generateController(String tableName);
}

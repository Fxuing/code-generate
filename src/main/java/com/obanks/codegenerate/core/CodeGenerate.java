package com.obanks.codegenerate.core;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.obanks.codegenerate.constant.Sql;
import com.obanks.codegenerate.core.config.GenerateConfig;
import com.obanks.codegenerate.model.SqlInfo;
import com.obanks.codegenerate.service.GenerateService;
import com.obanks.codegenerate.service.impl.GenerateServiceImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 16:25
 * @Description:
 */
@Component
@Data
public class CodeGenerate {
    private static GenerateService generateService;
    private static GenerateConfig config;
    private static volatile CodeGenerate SINGLETON;

    @Autowired
    private void setGenerateService(GenerateService generateService) {
        CodeGenerate.generateService = generateService;
    }

    public static CodeGenerate getInstance(GenerateConfig config) {
        CodeGenerate.config = config;
        if (SINGLETON == null) {
            synchronized (CodeGenerate.class) {
                if (SINGLETON == null) {
                    SINGLETON = new CodeGenerate();
                }
            }
        }
        return SINGLETON;
    }

    private CodeGenerate() {
    }

    public CodeGenerate config(GenerateConfig config) {
        if (generateService == null) {
            generateService = new GenerateServiceImpl();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(config.getUrl());
            hikariConfig.setUsername(config.getUsername());
            hikariConfig.setPassword(config.getPassword());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(new HikariDataSource(hikariConfig));
            config.setJdbcTemplate(jdbcTemplate);
        }
        generateService.setConfig(config);
        for (String table : config.getTable()) {
            checkTableIsExist(table);
        }
        return this;
    }

    private void checkTableIsExist(String table){
        List<String> tables = generateService.getConfig().getJdbcTemplate().queryForList(Sql.SHOW_TABLES, String.class);
        tables = tables.stream().map(String::toLowerCase).collect(Collectors.toList());
        if (!tables.contains(table)) {
            throw new RuntimeException(String.format("Table '%s' doesn't exist", table));
        }
    }


    private Config apolloConfig() {
        return ConfigService.getAppConfig();
    }

    public CodeGenerate entity() throws SQLException {
        for (String table : config.getTable()) {
            generateService.generateModel(table);
        }
        return this;
    }

    public CodeGenerate dao() throws SQLException {
        for (String table : config.getTable()) {
            generateService.generateDao(table);
            generateService.generateMapper(table);
        }
        return this;
    }

    public CodeGenerate service() {
        for (String table : config.getTable()) {
            generateService.generateService(table);
            generateService.generateServiceImpl(table);
        }
        return this;
    }

    public CodeGenerate controller() {
        for (String table : config.getTable()) {
            generateService.generateController(table);
        }
        return this;
    }

    public CodeGenerate sql(){
        List<SqlInfo> sqlInfoList = new ArrayList<>();
        SqlInfo id = SqlInfo.builder().autoIncrement(true).length(18).columnsName("id").comment("id").notEmpty(true).type("bigint").primary(true).build();
        SqlInfo name = SqlInfo.builder().autoIncrement(false).columnsName("name").comment("姓名").type("varchar").notEmpty(false).length(255).primary(false).build();
        SqlInfo age = SqlInfo.builder().autoIncrement(false).columnsName("age").comment("年龄").type("int").primary(false).notEmpty(false).length(11).build();
        Collections.addAll(sqlInfoList, id, name, age);
        generateService.generateSqlScript(sqlInfoList,"test_age");
        return this;
    }
}

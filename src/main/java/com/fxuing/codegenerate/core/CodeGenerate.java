package com.fxuing.codegenerate.core;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.fxuing.codegenerate.core.config.GenerateConfig;
import com.fxuing.codegenerate.service.GenerateService;
import com.fxuing.codegenerate.service.impl.GenerateServiceImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.sql.SQLException;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 16:25
 * @Description:
 */
@Component
@Data
public class CodeGenerate {
    private static GenerateService generateService ;
    private static GenerateConfig config;
    private static volatile CodeGenerate SINGLETON;
    @Autowired
    private void setGenerateService(GenerateService generateService){
        CodeGenerate.generateService = generateService;
    }
    public static CodeGenerate getInstance(GenerateConfig config){
        CodeGenerate.config = config;
        if(SINGLETON == null){
            synchronized(CodeGenerate.class){
                if(SINGLETON == null){
                    SINGLETON = new CodeGenerate();
                }
            }
        }
        return SINGLETON;
    }

    private CodeGenerate(){}

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
        return this;
    }
   /* private String username(){
        StandardServletEnvironment environment = new StandardServletEnvironment();
        environment.initPropertySources(null, null);
        String username = environment.getProperty("spring.datasource.username");
        return username ;
    }*/

    private Config apolloConfig(){
        return ConfigService.getAppConfig();
    }

    public CodeGenerate entity() throws SQLException {
        generateService.generateModel(config.getTableName());
        return this;
    }

    public CodeGenerate dao() throws SQLException {
        generateService.generateDao(config.getTableName());
        generateService.generateMapper(config.getTableName());
        return this;
    }

    public CodeGenerate service() {
        generateService.generateService(config.getTableName());
        generateService.generateServiceImpl(config.getTableName());
        return this;
    }

    public CodeGenerate controller() {
        generateService.generateController(config.getTableName());
        return this;
    }
}

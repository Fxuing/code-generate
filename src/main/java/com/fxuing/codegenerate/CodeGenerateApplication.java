package com.fxuing.codegenerate;

import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.core.CodeGenerate;
import com.fxuing.codegenerate.core.config.GenerateConfig;
import com.fxuing.codegenerate.service.GenerateService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.sql.SQLException;

@SpringBootApplication
@MapperScan(basePackages ={"com.fxuing.codegenerate.test.mapper"} )
public class CodeGenerateApplication implements CommandLineRunner  {

    @Autowired
    GenerateService generateService;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(CodeGenerateApplication.class, args);
        /*GenerateConfig config = new GenerateConfig();
        config.setTableName("pboc_bank_demand");
        config.setOutputPath("F:/data/generate/");
        config.setUrl("jdbc:mysql://192.168.50.31:3306/fop_new_data?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        config.setUsername("fop_new_logic");
        config.setPassword("Jhjf@123");
        config.setOperType(OperType.CDUR);
        config.setPackageName("com.odp.api");
        CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
        codeGenerate.config(config).entity().dao().service().controller();*/
    }

    @Override
    public void run(String... args) throws Exception {
        GenerateConfig config = new GenerateConfig();
        config.setTableName("sms_sending_record");
        config.setOutputPath("F:/data/generate/");
        config.setUrl("jdbc:mysql://192.168.50.31:3306/fop_new_data?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        config.setUsername("fop_new_logic");
        config.setPassword("Jhjf@123");
        config.setOperType(OperType.CDUR);
        config.setPackageName("com.fxuing.codegenerate.test");
        CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
        codeGenerate.config(config).entity().dao().service().controller();
    }
}

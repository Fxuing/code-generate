package com.fxuing.codegenerate;

import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.core.CodeGenerate;
import com.fxuing.codegenerate.core.config.GenerateConfig;
import com.fxuing.codegenerate.service.GenerateService;
import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

/**
 * @author Hou_fx
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.fxuing.codegenerate.test.mapper", "com.fxuing.codegenerate.mapper"})
public class CodeGenerateApplication implements CommandLineRunner {

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
        GenerateConfig config = GenerateConfig.getInstance();
        config.setTables("t_cgzx_bidproject");
        config.setOperType(OperType.CDUR);
        config.setPackageName("com.fxuing.bocagent");
        config.setOutputType(GenerateConfig.FILE);
        CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
        //codeGenerate.config(config).entity().dao().service().controller();

        //codeGenerate.sql();
        DocsConfig docsConfig = new DocsConfig();
        // 项目根目录
        docsConfig.setProjectPath("src");
        // 项目名称
        docsConfig.setProjectName("code-generate");
        // 声明该API的版本
        docsConfig.setApiVersion("V1.0");
        // 生成API 文档所在目录
        docsConfig.setDocsPath("F:\\data\\generate");
        // 配置自动生成
        docsConfig.setAutoGenerate(Boolean.TRUE);
        //docsConfig.addPlugin(new MarkdownDocPlugin());
        // 执行生成文档
        Docs.buildHtmlDocs(docsConfig);
    }
}

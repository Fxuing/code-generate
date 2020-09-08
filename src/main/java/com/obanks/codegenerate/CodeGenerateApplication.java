package com.obanks.codegenerate;

import com.obanks.codegenerate.constant.OperType;
import com.obanks.codegenerate.core.CodeGenerate;
import com.obanks.codegenerate.core.config.GenerateConfig;
import com.obanks.codegenerate.service.GenerateService;
import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
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
@MapperScan(basePackages = {"com.obanks.codegenerate.test.mapper", "com.obanks.codegenerate.mapper"})
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
        config.setTables("t_cgzx_bidproject", "t_corp_blankcheckblacklist", "t_corp_environmenadminpunish", "t_corp_environmentalapproval", "t_corp_hightechpark", "t_corp_patentassistance", "t_corp_scienttechcapital", "t_corp_scienttechprojectapp", "t_corp_socialsecurity", "t_corp_socialsecurityowe", "t_corp_trademark", "t_kcw_hightech_list", "t_skx_project_announcement_entlist", "t_swjt_import_ent_water");
        config.setOperType(OperType.CDUR);
        config.setPackageName("com.obanks.bocagent");
        config.setOutputType(GenerateConfig.FILE);
        CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
        codeGenerate.config(config).entity().dao().service().controller();

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

package com.obanks.codegenerate.core.config;

import com.obanks.codegenerate.constant.OperType;
import com.obanks.codegenerate.core.CodeGenerate;
import com.obanks.codegenerate.utils.PropertiesUtils;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 16:26
 * @Description:
 */
@Data
public class GenerateConfig {
    public static final String FILE = "file", STREAM = "STREAM";
    private String packageName;
    private String url;
    private String username;
    private String password;
    private String outputPath;
    private String outputType;
    private String tableName;
    private OperType operType;
    private JdbcTemplate jdbcTemplate;
    private String[] table;
    private Writer writer;
    private Map<String,Map<String,String>> previewData;
    public void setTables(String... tables) {
        table = tables;
    }

    private static volatile GenerateConfig SINGLETON;
    public static GenerateConfig getInstance(){
        if(SINGLETON == null){
            synchronized(CodeGenerate.class){
                if(SINGLETON == null){
                    SINGLETON = new GenerateConfig();
                    SINGLETON.url = PropertiesUtils.getValue("url");
                    SINGLETON.username = PropertiesUtils.getValue("username");
                    SINGLETON.password = PropertiesUtils.getValue("password");
                }
            }
        }
        return SINGLETON;
    }
}

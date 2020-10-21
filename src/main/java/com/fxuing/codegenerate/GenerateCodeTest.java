package com.fxuing.codegenerate;



import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.core.CodeGenerate;
import com.fxuing.codegenerate.core.config.GenerateConfig;

import java.sql.SQLException;

/**
 * @author Hou_fx
 * @date 2020.10.21 15:02
 * @description
 */
public class GenerateCodeTest {
    public static void main(String[] args) throws SQLException {
        GenerateConfig config = GenerateConfig.getInstance();
        config.setUrl("jdbc:mysql://192.168.50.31:3306/fop_new_data?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        config.setUsername("fop_new_logic");
        config.setPassword("Jhjf@123");
        config.setTables("third_part_login");
        config.setOperType(OperType.CDUR);
        config.setPackageName("com.fxuing.codegenerate.abcde");
        config.setOutputType(GenerateConfig.FILE);
        config.setGenerateType(GenerateConfig.MYBATIS_PLUS);
        CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
        codeGenerate.config(config).entity().dao().service();
    }
}

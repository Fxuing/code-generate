package com.fxuing.codegenerate.core.config;

import com.fxuing.codegenerate.constant.OperType;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 16:26
 * @Description:
 */
@Data
public class GenerateConfig {
    private String packageName;
    private String url;
    private String username;
    private String password;
    private String outputPath;
    private String tableName;
    private OperType operType;
    private JdbcTemplate jdbcTemplate;
}

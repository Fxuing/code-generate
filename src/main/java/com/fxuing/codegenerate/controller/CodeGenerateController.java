package com.fxuing.codegenerate.controller;


import com.fxuing.codegenerate.common.Paginate;
import com.fxuing.codegenerate.common.Request;
import com.fxuing.codegenerate.common.Result;
import com.fxuing.codegenerate.constant.Sql;
import com.fxuing.codegenerate.core.CodeGenerate;
import com.fxuing.codegenerate.core.config.GenerateConfig;
import com.fxuing.codegenerate.dto.Tables;
import com.fxuing.codegenerate.service.GenerateService;
import com.fxuing.codegenerate.vo.GenerateVO;
import com.fxuing.codegenerate.vo.TablesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.17 11:04
 * @Description:
 */
@Controller
@RequestMapping("generate/code")
public class CodeGenerateController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GenerateService generateService;

    @PostMapping("tables")
    @ResponseBody
    public Result<Paginate<List<Tables>>> tables(@RequestBody Request<TablesVO> request) {
        return Result.success(generateService.search(request.getBody()));
    }

    @PostMapping("allTable")
    @ResponseBody
    public Result<List<Tables>> allTable(@RequestBody Request<TablesVO> request) {
        List<Tables> data = jdbcTemplate.query(Sql.SHOW_TABLES_STATUS, (resultSet, i) -> Tables.getInstance(resultSet));
        return Result.success(data);
    }


    /**
     * 生成代码
     *
     * @param generateVO 参数
     * @return com.fxuing.codegenerate.common.Result<java.lang.String>
     * @author Hou_fx
     * @date 2020.8.17 12:11
     */
    @PostMapping("generate")
    @ResponseBody
    public Result<String> generate(@RequestBody GenerateVO generateVO) throws SQLException {
        GenerateConfig config = GenerateConfig.getInstance();
        config.setTables(generateVO.getTables());
        config.setOperType(generateVO.getOperType());
        config.setPackageName(generateVO.getPackageName());
        config.setOutputType(GenerateConfig.FILE);
        CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
        codeGenerate.config(config).entity().dao().service().controller();
        return Result.success();
    }

    /**
     * 生成代码
     *
     * @param request 参数
     * @param response   返回
     * @return com.fxuing.codegenerate.common.Result<java.lang.String>
     * @author Hou_fx
     * @date 2020.8.17 12:11
     */
    @PostMapping("stream/generate")
    @ResponseBody
    public Result<Map<String, Map<String,String>>> generate(HttpServletResponse response, @RequestBody Request<GenerateVO> request) throws SQLException, IOException {
        GenerateVO generateVO = request.getBody();
        GenerateConfig config = GenerateConfig.getInstance();
        config.setTables(generateVO.getTables());
        config.setOperType(generateVO.getOperType());
        config.setPackageName(generateVO.getPackageName());
        config.setOutputType(GenerateConfig.STREAM);
        config.setPreviewData(new HashMap<>(16));
        Writer writer = null;
        try {
            writer = new StringWriter(1024);
            config.setWriter(writer);
            CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
            codeGenerate.config(config).entity().dao().service().controller();
        } finally {
            assert writer != null;
            writer.flush();
            writer.close();
        }
        return Result.success(config.getPreviewData());
    }


    /**
     * 生成代码
     *
     * @param generateVO 参数
     * @param response   返回
     * @author Hou_fx
     * @date 2020.8.17 12:11
     */
    @PostMapping("download/generate")
    public void downloadGenerate(HttpServletResponse response,@RequestBody GenerateVO generateVO) throws SQLException, IOException {
        response.setHeader("contentType", "charset=utf-8");
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=generate.java");
        response.setCharacterEncoding("utf-8");
        GenerateConfig config = GenerateConfig.getInstance();
        config.setTables(generateVO.getTables());
        config.setOperType(generateVO.getOperType());
        config.setPackageName(generateVO.getPackageName());
        config.setOutputType(GenerateConfig.STREAM);
        config.setWriter(response.getWriter());
        CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
        codeGenerate.config(config).entity().dao().service().controller();
    }
}

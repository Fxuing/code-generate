package com.fxuing.codegenerate.controller;

import com.fxuing.codegenerate.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.24 15:59
 * @Description:
 */
@RestController
@RequestMapping("generate")
public class GenerateController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GenerateService generateService;

    @GetMapping("show/tables")
    public Object showTable() {
        List<String> tables = jdbcTemplate.queryForList("show tables", String.class);
        return tables;
    }

    @GetMapping("show/{tableName}")
    public Object tableInfo(@PathVariable String tableName, HttpServletResponse response) throws SQLException {
        generateService.generateModel(tableName);
        generateService.generateDao(tableName);
        generateService.generateMapper(tableName);
        generateService.generateService(tableName);
        generateService.generateServiceImpl(tableName);
        generateService.generateController(tableName);
        return "OK";
    }
}

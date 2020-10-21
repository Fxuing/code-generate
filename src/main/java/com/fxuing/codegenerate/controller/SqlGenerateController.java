package com.fxuing.codegenerate.controller;

import com.fxuing.codegenerate.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.17 14:49
 * @Description:
 */
@Controller
@RequestMapping("sql")
public class SqlGenerateController {

    @PostMapping("create/table")
    public Result<String> createTable() {

        return Result.success();
    }
}

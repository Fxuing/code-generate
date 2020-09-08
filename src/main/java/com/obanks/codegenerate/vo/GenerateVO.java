package com.obanks.codegenerate.vo;

import com.obanks.codegenerate.constant.OperType;
import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.17 12:05
 * @Description:
 */
@Data
public class GenerateVO {
    private String[] tables;
    private OperType operType;
    private String packageName;
}

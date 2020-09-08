package com.obanks.codegenerate.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.14 11:28
 * @Description:
 */
@Data
@Builder
public class SqlInfo {
    /**
     * 字段名
     */
    private String columnsName;
    /**
     * 类型
     */
    private String type;
    /**
     * 长度
     */
    private Integer length;
    /**
     * 小数点
     */
    private Integer decimals;
    /**
     * 非空 默认空
     */
    private Boolean notEmpty = Boolean.FALSE;
    /**
     * 主键
     */
    private Boolean primary = Boolean.FALSE;
    /**
     * 自动递增
     */
    private Boolean autoIncrement = Boolean.FALSE;

    /**
     * 注释
     */
    private String comment;
}

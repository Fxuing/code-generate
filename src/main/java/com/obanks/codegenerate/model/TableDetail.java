package com.obanks.codegenerate.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 14:11
 * @Description:
 */
@Data
public class TableDetail {
    /**
     * 字段名
     */
    private String field;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 字符集（mysql 5.0以上有）
     */
    private String collation;
    /**  是否可以为NULL */
    private String empty;
    /**
     * 索引（PRI,unique,index)
     */
    private String key;
    /** 缺省值 */
    private String defaults;
    /**
     * 额外（是否 auto_increment)
     */
    private String extra;
    /**
     * 权限
     */
    private String privileges;
    /**
     * 备注（mysql 5.0以上有)
     */
    private String comment;

    public static TableDetail getInstance(ResultSet resultSet) throws SQLException {
        TableDetail detail = new TableDetail();
        detail.setCollation(resultSet.getString("Collation"));
        detail.setComment(resultSet.getString("Comment"));
        detail.setDefaults(resultSet.getString("Default"));
        detail.setEmpty(resultSet.getString("Null"));
        detail.setExtra(resultSet.getString("Extra"));
        detail.setField(resultSet.getString("Field"));
        detail.setKey(resultSet.getString("Key"));
        detail.setPrivileges(resultSet.getString("Privileges"));
        detail.setType(resultSet.getString("Type"));
        return detail;
    }
}

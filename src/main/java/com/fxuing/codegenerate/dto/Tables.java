package com.fxuing.codegenerate.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hou_fx
 * @date 2020.8.17 11:10
 */
@Data
public class Tables implements Serializable {
    private static final long serialVersionUID = -6684065565960728565L;

    /**
     * 表名
     */
    private String name;
    /**
     * 注释
     */
    private String comment;

    public static Tables getInstance(ResultSet resultSet) throws SQLException {
        Tables t = new Tables();
        t.setComment(resultSet.getString("Comment"));
        t.setName(resultSet.getString("Name"));
        return t;
    }
}

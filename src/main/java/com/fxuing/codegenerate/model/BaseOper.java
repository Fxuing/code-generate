package com.fxuing.codegenerate.model;

import com.fxuing.codegenerate.constant.OperType;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 11:15
 * @Description:
 */
@Data
public class BaseOper {
    private static final  String TRUE = "1", FALSE = "0";
    private Boolean insert;
    private Boolean update;
    private Boolean delete;
    private Boolean select;
    private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

    /**
     * 增 查 改 删
     * C  R  U  D
     */
    public BaseOper(OperType operType) {
        char[] crud = operType.getValue().toCharArray();
        for (int i = 0; i < crud.length; i++) {
            boolean eq = String.valueOf(crud[i]).equals(TRUE);
            switch (i) {
                case 0: insert = eq; break;
                case 1: select = eq; break;
                case 2: update = eq; break;
                case 3: delete = eq; break;
                default: break;
            }
        }
    }
}

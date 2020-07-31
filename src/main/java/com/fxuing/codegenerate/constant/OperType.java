package com.fxuing.codegenerate.constant;

import lombok.Getter;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 17:49
 * @Description: 操作类型
 */
public enum  OperType {
    /**
     * 增 查 改 删
     * C  R  U  D
     */
//-----------------------------------------------------------------
    /** 增 */
    C("1000"),
    /** 删 */
    D("0001"),
    /** 改 */
    U("0010"),
    /** 查 */
    R("0100"),
    /** 增删 */
    CD("1001"),
    /** 增改 */
    CU("1010"),
    /** 增查 */
    CR("1100"),
    /** 删改 */
    DU("0011"),
    /** 删查 */
    DR("0101"),
    /** 改查 */
    UR("0110"),
    /** 增删改 */
    CDU("1011"),
    /** 增删查 */
    CDR("1101"),
    /** 增改查 */
    CUR("1110"),
    /** 删改查 */
    DUR("0111"),
    /** 增删改查 */
    CDUR("1111"),
    ;
    @Getter
    private final String value;
    OperType(String value){
        this.value = value;
    }

}

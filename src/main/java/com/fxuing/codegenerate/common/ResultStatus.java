package com.fxuing.codegenerate.common;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.14 11:49
 * @Description:
 */
public enum ResultStatus {

    /** 状态码 */
    SUCCESS("1","成功"),
    FAIL("0","失败"),
    ;

    private String code;
    private String message;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 结果状态
     */
    ResultStatus(String code, String message) {
        this.code =code;
        this.message =message;
    };
}

package com.obanks.codegenerate.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.14 11:47
 * @Description:
 */
@Data
public class Result<T> {
    private String status;
    private String message;
    private T data;

    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.setStatus(ResultStatus.SUCCESS.getCode());
        result.setMessage(ResultStatus.SUCCESS.getMessage());
        return result;
    }
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setStatus(ResultStatus.SUCCESS.getCode());
        result.setMessage(ResultStatus.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }
}

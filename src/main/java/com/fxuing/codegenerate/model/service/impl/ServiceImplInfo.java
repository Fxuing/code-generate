package com.fxuing.codegenerate.model.service.impl;

import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.model.BaseOper;
import com.fxuing.codegenerate.utils.StringSimpleUtil;
import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 15:13
 * @Description:
 */
@Data
public class ServiceImplInfo extends BaseOper {
    private String modelName;
    private String packageName;

    public String getModelNameLower(){
        return StringSimpleUtil.underlineToHump(this.modelName);
    }
    public ServiceImplInfo(OperType operType){
        super(operType);
    }
}

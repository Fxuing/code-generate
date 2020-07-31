package com.fxuing.codegenerate.model.service;

import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.model.BaseOper;
import com.fxuing.codegenerate.utils.StringSimpleUtil;
import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 14:50
 * @Description:
 */
@Data
public class ServiceInfo extends BaseOper {
    private String packageName;
    private String modelName;

    public String getModelNameLower(){
        return StringSimpleUtil.underlineToHump(this.modelName);
    }

    public ServiceInfo(OperType operType) {
        super(operType);
    }
}

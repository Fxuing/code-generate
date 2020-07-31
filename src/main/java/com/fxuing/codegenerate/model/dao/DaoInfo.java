package com.fxuing.codegenerate.model.dao;

import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.model.BaseOper;
import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 16:38
 * @Description:
 */
@Data
public class DaoInfo extends BaseOper {
    private String packageName;
    private String modelName;
    private String modelNameLower;

    public DaoInfo(OperType operType){
        super(operType);
    }
}

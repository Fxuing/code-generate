package com.fxuing.codegenerate.model;

import com.fxuing.codegenerate.constant.OperType;
import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.3 11:55
 * @Description:
 */
@Data
public class ClassInfo extends BaseOper {
    private String modelName;
    private String packageName;
    private String modelNameLower;

    public ClassInfo(OperType operType) {
        super(operType);
    }

    public String getModelNameLower(){
        return modelName.substring(0, 1).toUpperCase() + modelName.substring(1);
    }

    public static ClassInfo getInstance(OperType operType, String modelName, String packageName) {
        ClassInfo classInfo = new ClassInfo(operType);
        classInfo.setModelName(modelName);
        classInfo.setPackageName(packageName);
        return classInfo;
    }
    public static ClassInfo getInstance(OperType operType, String modelName, String packageName,String modelNameLower) {
        ClassInfo classInfo = new ClassInfo(operType);
        classInfo.setModelName(modelName);
        classInfo.setPackageName(packageName);
        classInfo.setModelNameLower(modelNameLower);
        return classInfo;
    }
}

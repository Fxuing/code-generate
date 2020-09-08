package com.obanks.codegenerate.model.mapper;

import com.obanks.codegenerate.constant.OperType;
import com.obanks.codegenerate.model.BaseOper;
import com.obanks.codegenerate.utils.StringSimpleUtil;
import lombok.Data;

import java.util.List;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.31 12:13
 * @Description:
 */
@Data
public class MapperInfo extends BaseOper {
    private String tableName;
    private String namespace;
    private String modelName;
    private List<PropertiesInfo> propertiesInfos;

    public MapperInfo(OperType operType) {
        super(operType);
    }

    @Data
    public static class PropertiesInfo{
        private String columns;
        private String properties;

        public String getProperties(){
            return StringSimpleUtil.underlineToHump(columns);
        }
    }
}

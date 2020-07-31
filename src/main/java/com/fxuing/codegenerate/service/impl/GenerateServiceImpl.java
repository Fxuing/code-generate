package com.fxuing.codegenerate.service.impl;

import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.model.TableDetail;
import com.fxuing.codegenerate.model.TableInfo;
import com.fxuing.codegenerate.model.dao.DaoInfo;
import com.fxuing.codegenerate.model.mapper.MapperInfo;
import com.fxuing.codegenerate.model.service.ServiceInfo;
import com.fxuing.codegenerate.model.service.impl.ServiceImplInfo;
import com.fxuing.codegenerate.service.GenerateService;
import com.fxuing.codegenerate.utils.StringSimpleUtil;
import com.fxuing.codegenerate.utils.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 14:18
 * @Description:
 */
@Service
public class GenerateServiceImpl implements GenerateService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String OUTPUT_PATH = "F:/data/generate/";
    @Override
    public void generateModel(String tableName) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        tableInfo.setModelName(modelName.substring(0,1).toUpperCase() + modelName.substring(1));
        List<TableDetail> tableDetail = jdbcTemplate.query("show full columns from " + tableName, (resultSet, i) -> TableDetail.getInstance(resultSet));
        List<TableInfo.Field> fieldList = new ArrayList<>();
        tableDetail.forEach(f -> fieldList.add(TableInfo.Field.getInstance(f)));
        tableInfo.setFieldList(fieldList);
        Map<String, Object> param = new HashMap<>(16);
        param.put("table", tableInfo);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process("model.txt", context, OUTPUT_PATH + tableInfo.getModelName() + ".java");
    }

    @Override
    public void generateDao(String tableName) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        DaoInfo daoInfo = new DaoInfo(OperType.CDUR);
        daoInfo.setModelName(modelName.substring(0,1).toUpperCase() + modelName.substring(1));
        daoInfo.setPackageName(tableName);
        daoInfo.setModelNameLower(modelName);
        Map<String, Object> param = new HashMap<>(16);
        param.put("daoInfo", daoInfo);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process("dao.txt", context, OUTPUT_PATH + daoInfo.getModelName() + "Mapper.java");
    }

    @Override
    public void generateMapper(String tableName) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        MapperInfo mapperInfo = new MapperInfo(OperType.CDUR);
        mapperInfo.setTableName(tableName);
        mapperInfo.setNamespace(tableName);
        List<TableDetail> tableDetail = jdbcTemplate.query("show full columns from " + tableName, (resultSet, i) -> TableDetail.getInstance(resultSet));
        mapperInfo.setPropertiesInfos(tableDetail.stream().map(m -> {
            MapperInfo.PropertiesInfo propertiesInfo = new MapperInfo.PropertiesInfo();
            propertiesInfo.setColumns(m.getField().toUpperCase());
            return propertiesInfo;
        }).collect(Collectors.toList()));
        Map<String, Object> param = new HashMap<>(16);
        param.put("mapper", mapperInfo);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process("mapper.txt", context, OUTPUT_PATH + modelName + "Mapper.xml");
    }

    @Override
    public void generateService(String tableName) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        ServiceInfo serviceInfo = new ServiceInfo(OperType.CDUR);
        serviceInfo.setModelName(modelName.substring(0,1).toUpperCase() + modelName.substring(1));
        serviceInfo.setPackageName(tableName);
        Map<String, Object> param = new HashMap<>(16);
        param.put("service", serviceInfo);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process("service.txt", context, OUTPUT_PATH + modelName + "Service.java");
    }

    @Override
    public void generateServiceImpl(String tableName) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        ServiceImplInfo serviceImpl = new ServiceImplInfo(OperType.CDUR);
        serviceImpl.setModelName(modelName.substring(0,1).toUpperCase() + modelName.substring(1));
        serviceImpl.setPackageName(tableName);
        Map<String, Object> param = new HashMap<>(16);
        param.put("serviceImpl", serviceImpl);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process("service_impl.txt", context, OUTPUT_PATH + modelName + "ServiceImpl.java");
    }

    @Override
    public void generateController(String tableName) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        ServiceImplInfo serviceImpl = new ServiceImplInfo(OperType.CDUR);
        serviceImpl.setModelName(modelName.substring(0,1).toUpperCase() + modelName.substring(1));
        serviceImpl.setPackageName(tableName);
        Map<String, Object> param = new HashMap<>(16);
        param.put("controller", serviceImpl);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process("controller.txt", context, OUTPUT_PATH + modelName + "Controller.java");
    }

}

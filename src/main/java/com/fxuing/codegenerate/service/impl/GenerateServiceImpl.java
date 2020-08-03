package com.fxuing.codegenerate.service.impl;

import com.fxuing.codegenerate.constant.Sql;
import com.fxuing.codegenerate.constant.Template;
import com.fxuing.codegenerate.core.config.GenerateConfig;
import com.fxuing.codegenerate.model.ClassInfo;
import com.fxuing.codegenerate.model.TableDetail;
import com.fxuing.codegenerate.model.TableInfo;
import com.fxuing.codegenerate.model.mapper.MapperInfo;
import com.fxuing.codegenerate.service.GenerateService;
import com.fxuing.codegenerate.utils.StringSimpleUtil;
import com.fxuing.codegenerate.utils.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.File;
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
    GenerateConfig config;
    private static final Map<String, String> FILE_TYPE = new HashMap<>();
    private static final Map<String, String> DIR_TYPE = new HashMap<>();
    private static final String TABLE = "table";
    private static final String CLASS_INFO = "classInfo";
    private static final String MAPPER = "mapper";

    static {
        FILE_TYPE.put(Template.MODEL, ".java");
        FILE_TYPE.put(Template.CONTROLLER, "Controller.java");
        FILE_TYPE.put(Template.DAO, "Mapper.java");
        FILE_TYPE.put(Template.MAPPER, "Mapper.xml");
        FILE_TYPE.put(Template.SERVICE, "Service.java");
        FILE_TYPE.put(Template.SERVICE_IMPL, "ServiceImpl.java");

        DIR_TYPE.put(Template.MODEL, "entity");
        DIR_TYPE.put(Template.CONTROLLER, "controller");
        DIR_TYPE.put(Template.DAO, "mapper");
        DIR_TYPE.put(Template.MAPPER, "mapper\\orm");
        DIR_TYPE.put(Template.SERVICE, "service");
        DIR_TYPE.put(Template.SERVICE_IMPL, "service\\impl");
    }

    @Override
    public void setConfig(GenerateConfig config) {
        this.config = config;
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = config.getJdbcTemplate();
        }
    }

    @Override
    public void generateModel(String tableName) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        tableInfo.setModelName(modelName.substring(0, 1).toUpperCase() + modelName.substring(1));
        tableInfo.setPackageName(this.config.getPackageName() + "." + tableInfo.getModelName());
        List<TableDetail> tableDetail = jdbcTemplate.query(String.format(Sql.QUERY_TABLE_DETAIL, tableName), (resultSet, i) -> TableDetail.getInstance(resultSet));
        List<TableInfo.Field> fieldList = new ArrayList<>();
        tableDetail.forEach(f -> fieldList.add(TableInfo.Field.getInstance(f)));
        tableInfo.setFieldList(fieldList);
        Map<String, Object> param = new HashMap<>(16);
        param.put(TABLE, tableInfo);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process(Template.MODEL, context, createDir(DIR_TYPE.get(Template.MODEL)) + tableInfo.getModelName() + FILE_TYPE.get(Template.MODEL));
    }

    @Override
    public void generateDao(String tableName) {
        this.exec(tableName, Template.DAO, createDir(DIR_TYPE.get(Template.DAO)));
    }

    @Override
    public void generateMapper(String tableName) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        MapperInfo mapperInfo = new MapperInfo(this.config.getOperType());
        mapperInfo.setTableName(tableName);
        List<TableDetail> tableDetail = jdbcTemplate.query(String.format(Sql.QUERY_TABLE_DETAIL, tableName), (resultSet, i) -> TableDetail.getInstance(resultSet));
        mapperInfo.setPropertiesInfos(tableDetail.stream().map(m -> {
            MapperInfo.PropertiesInfo propertiesInfo = new MapperInfo.PropertiesInfo();
            propertiesInfo.setColumns(m.getField().toUpperCase());
            return propertiesInfo;
        }).collect(Collectors.toList()));
        Map<String, Object> param = new HashMap<>(16);
        param.put(MAPPER, mapperInfo);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        modelName = modelName.substring(0, 1).toUpperCase() + modelName.substring(1);
        mapperInfo.setNamespace(this.config.getPackageName() + ".mapper." + modelName + "Mapper");
        TemplateUtil.process(Template.MAPPER, context, createDir(DIR_TYPE.get(Template.MAPPER)) + modelName + FILE_TYPE.get(Template.MAPPER));
    }

    @Override
    public void generateService(String tableName) {
        this.exec(tableName, Template.SERVICE, createDir(DIR_TYPE.get(Template.SERVICE)));
    }

    @Override
    public void generateServiceImpl(String tableName) {
        this.exec(tableName, Template.SERVICE_IMPL, createDir(DIR_TYPE.get(Template.SERVICE_IMPL)));
    }

    @Override
    public void generateController(String tableName) {
        this.exec(tableName, Template.CONTROLLER, createDir(DIR_TYPE.get(Template.CONTROLLER)));
    }

    private void exec(String tableName, String template, String outputPath) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        modelName = modelName.substring(0, 1).toUpperCase() + modelName.substring(1);
        Map<String, Object> param = new HashMap<>(16);
        param.put(CLASS_INFO, ClassInfo.getInstance(this.config.getOperType(), modelName, this.config.getPackageName()));
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process(template, context, outputPath + modelName + FILE_TYPE.get(template));
    }

    private String createDir(String template) {
        String path = DIR_TYPE.get(Template.MAPPER).equals(template) ? "%s\\src\\main\\resources\\orm" : "%s\\src\\main\\java\\%s\\%s";
        String packagePath = this.config.getPackageName().replace(".", "\\");
        File filePath = new File(String.format(path, System.getProperty("user.dir"), packagePath, template));
        if (!filePath.exists()) {
            System.out.println(filePath.getPath() + " directory is not exists, create directory ...");
            filePath.mkdirs();
        }
        return filePath.getPath() + "\\";
    }
}

package com.fxuing.codegenerate.service.impl;

import com.fxuing.codegenerate.constant.JavaType;
import com.fxuing.codegenerate.constant.OperType;
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

import static com.fxuing.codegenerate.constant.JavaType.*;

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
    private static final String IMPORTS = "imports";
    private static final String CLASS_INFO = "classInfo";
    private static final String MAPPER = "mapper";
    private static final String SERVICE_FORMAT = "%s.service.%sService";
    private static final String MAPPER_FORMAT = "%s.mapper.%sMapper";
    private static final String ENTITY_FORMAT = "%s.entity.%s";
    private static final String USER_DIR = System.getProperty("user.dir");

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
        tableInfo.setPackageName(this.config.getPackageName() + ".entity");
        List<TableDetail> tableDetail = jdbcTemplate.query(String.format(Sql.QUERY_TABLE_DETAIL, tableName), (resultSet, i) -> TableDetail.getInstance(resultSet));
        List<TableInfo.Field> fieldList = new ArrayList<>();
        tableDetail.forEach(f -> fieldList.add(TableInfo.Field.getInstance(f)));
        tableInfo.setFieldList(fieldList);
        Map<String, Object> param = new HashMap<>(16);
        param.put(TABLE, tableInfo);
        param.put(IMPORTS, importsPackage(param, Template.MODEL));
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
        mapperInfo.setNamespace(String.format(MAPPER_FORMAT, this.config.getPackageName(), modelName));
        mapperInfo.setModelName(String.format(ENTITY_FORMAT, this.config.getPackageName(), modelName));
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

    private Set<String> importsPackage(Map<String, Object> param, String template) {
        Set<String> res = new HashSet<>();
        ClassInfo classInfo = (ClassInfo) Optional.ofNullable(param.get(CLASS_INFO)).orElse(new ClassInfo(OperType.CDUR));
        Optional.ofNullable(classInfo.getJavaType()).ifPresent(res::addAll);
        switch (template) {
            case Template.MODEL:
                TableInfo tableInfo = (TableInfo) Optional.ofNullable(param.get(TABLE)).orElse(new TableInfo());
                Optional.ofNullable(tableInfo.getFieldList()).ifPresent(p -> p.forEach(f -> Optional.ofNullable(JavaType.get(f.getJavaType())).ifPresent(res::add)));
                break;
            case Template.DAO:
                Collections.addAll(res, JavaType.get(REPOSITORY), JavaType.get(PAGINATE), JavaType.get(LIST));
                break;
            case Template.SERVICE:
                res.add(JavaType.get(PAGINATE));
                break;
            case Template.SERVICE_IMPL:
                Collections.addAll(res,
                        String.format(MAPPER_FORMAT, classInfo.getPackageName(), classInfo.getModelName()),
                        String.format(SERVICE_FORMAT,classInfo.getPackageName(),classInfo.getModelName()),
                        JavaType.get(AUTOWIRED), JavaType.get(PAGINATE), JavaType.get(SERVICE),
                        JavaType.get(PROPAGATION), JavaType.get(TRANSACTIONAL)
                        );
                break;
            case Template.CONTROLLER:
                Collections.addAll(res,
                        String.format(SERVICE_FORMAT,classInfo.getPackageName(),classInfo.getModelName()),
                        JavaType.get(PAGINATE), JavaType.get(AUTOWIRED), JavaType.get(POST_MAPPING),
                        JavaType.get(REQUEST_MAPPING), JavaType.get(REST_CONTROLLER)
                        );
                break;
            default:
        }
        return res;
    }

    private <E> void add(Collection<E> c, E ... o) {
        Collections.addAll(c, o);
    }

    private void exec(String tableName, String template, String outputPath) {
        String modelName = StringSimpleUtil.underlineToHump(tableName);
        modelName = modelName.substring(0, 1).toUpperCase() + modelName.substring(1);
        Map<String, Object> param = new HashMap<>(16);
        param.put(CLASS_INFO, ClassInfo.getInstance(this.config.getOperType(), modelName, this.config.getPackageName()));
        param.put(IMPORTS, importsPackage(param, template));
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process(template, context, outputPath + modelName + FILE_TYPE.get(template));
    }

    private String createDir(String template) {
        String path = DIR_TYPE.get(Template.MAPPER).equals(template) ? "%s\\src\\main\\resources\\orm" : "%s\\src\\main\\java\\%s\\%s";
        String packagePath = this.config.getPackageName().replace(".", "\\");
        File filePath = new File(String.format(path, USER_DIR, packagePath, template));
        if (!filePath.exists()) {
            System.out.println(filePath.getPath() + " directory is not exists, create directory ...");
            filePath.mkdirs();
        }
        return filePath.getPath() + "\\";
    }
}

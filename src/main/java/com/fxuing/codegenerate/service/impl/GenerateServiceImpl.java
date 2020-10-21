package com.fxuing.codegenerate.service.impl;


import com.fxuing.codegenerate.common.Paginate;
import com.fxuing.codegenerate.constant.JavaType;
import com.fxuing.codegenerate.constant.OperType;
import com.fxuing.codegenerate.constant.Sql;
import com.fxuing.codegenerate.constant.Template;
import com.fxuing.codegenerate.core.config.GenerateConfig;
import com.fxuing.codegenerate.dto.Tables;
import com.fxuing.codegenerate.mapper.SearchMapper;
import com.fxuing.codegenerate.model.ClassInfo;
import com.fxuing.codegenerate.model.SqlInfo;
import com.fxuing.codegenerate.model.TableDetail;
import com.fxuing.codegenerate.model.TableInfo;
import com.fxuing.codegenerate.model.mapper.MapperInfo;
import com.fxuing.codegenerate.service.GenerateService;
import com.fxuing.codegenerate.utils.StringSimpleUtil;
import com.fxuing.codegenerate.utils.TemplateUtil;
import com.fxuing.codegenerate.vo.TablesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.StringWriter;
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
    SearchMapper searchMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    GenerateConfig config;
    private static final Map<String, String> FILE_TYPE = new HashMap<>();
    private static final Map<String, String> DIR_TYPE = new HashMap<>();
    private static final String TABLE = "table";
    private static final String GENERATE_TYPE = "generateType";
    private static final String IMPORTS = "imports";
    private static final String CLASS_INFO = "classInfo";
    private static final String MAPPER = "mapper";
    private static final String SERVICE_FORMAT = "%s.service.%sService";
    private static final String MAPPER_FORMAT = "%s.mapper.%sMapper";
    private static final String ENTITY_FORMAT = "%s.entity.%s";
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String ENTITY="entity",DAO="dao",SERVICE="service",IMPL="impl", CONTROLLER = "controller";

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
        config.setJdbcTemplate(this.jdbcTemplate);
    }

    @Override
    public GenerateConfig getConfig() {
        return this.config;
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
        if (GenerateConfig.FILE.equals(config.getOutputType())) {
            TemplateUtil.process(Template.MODEL, context, createDir(DIR_TYPE.get(Template.MODEL),this.config.getOutputType()) + tableInfo.getModelName() + FILE_TYPE.get(Template.MODEL));
        } else if (GenerateConfig.STREAM.equals(config.getOutputType())) {
            config.setWriter(new StringWriter(1024));
            TemplateUtil.process(Template.MODEL,context,config.getWriter());
            setPreviewData(tableName, Template.MODEL);
        }
    }

    private void setPreviewData(String tableName,String template) {
        Map<String, String> previewData = config.getPreviewData().get(tableName) == null ? new HashMap<>(16) : config.getPreviewData().get(tableName);
        previewData.put(template.split(".txt")[0], config.getWriter().toString());
        config.getPreviewData().put(tableName, previewData);
    }

    @Override
    public void generateDao(String tableName) {
        this.exec(tableName, Template.DAO, createDir(DIR_TYPE.get(Template.DAO), this.config.getOutputType()));
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
        param.put(GENERATE_TYPE, this.config.getGenerateType());
        param.put("paramName", modelName);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        modelName = modelName.substring(0, 1).toUpperCase() + modelName.substring(1);
        mapperInfo.setModelName(String.format(ENTITY_FORMAT, this.config.getPackageName(), modelName));
        mapperInfo.setNamespace(String.format(MAPPER_FORMAT, this.config.getPackageName(), modelName));
        if (GenerateConfig.FILE.equals(config.getOutputType())) {
            TemplateUtil.process(Template.MAPPER, context, createDir(DIR_TYPE.get(Template.MAPPER),this.config.getOutputType()) + modelName + FILE_TYPE.get(Template.MAPPER));
        } else if (GenerateConfig.STREAM.equals(config.getOutputType())) {
            config.setWriter(new StringWriter(1024));
            TemplateUtil.process(Template.MAPPER, context, config.getWriter());
            setPreviewData(tableName, Template.MAPPER);
        }

    }

    @Override
    public void generateService(String tableName) {
        this.exec(tableName, Template.SERVICE, createDir(DIR_TYPE.get(Template.SERVICE),this.config.getOutputType()));
    }

    @Override
    public void generateServiceImpl(String tableName) {
        this.exec(tableName, Template.SERVICE_IMPL, createDir(DIR_TYPE.get(Template.SERVICE_IMPL),this.config.getOutputType()));
    }

    @Override
    public void generateController(String tableName) {
        this.exec(tableName, Template.CONTROLLER, createDir(DIR_TYPE.get(Template.CONTROLLER),this.config.getOutputType()));
    }

    @Override
    public void generateSqlScript(List<SqlInfo> sqlInfos, String tableName) {
        List<String> primaryList = sqlInfos.stream().filter(SqlInfo::getPrimary).map(SqlInfo::getColumnsName).collect(Collectors.toList());
        Map<String, Object> param = new HashMap<>(16);
        param.put("sqlInfos", sqlInfos);
        param.put("tableName", tableName);
        param.put("primaryList", primaryList);
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        TemplateUtil.process(Template.SQL, context, "F:\\data\\webapps\\test.sql");
    }

    @Override
    public Paginate<List<Tables>> search(TablesVO tablesVO) {
        Paginate<List<Tables>> paginate = tablesVO.getPaginate();
        paginate.setCondition(tablesVO.getKeyword());
        Long count = searchMapper.tablesCount(paginate);
        if (count == 0) {
            return paginate;
        }
        paginate.setTotal(count);
        paginate.setData(searchMapper.tablesData(paginate));
        return paginate;
    }

    private Set<String> importsPackage(Map<String, Object> param, String template) {
        Set<String> res = new HashSet<>();
        ClassInfo classInfo = (ClassInfo) Optional.ofNullable(param.get(CLASS_INFO)).orElse(new ClassInfo(OperType.CDUR));
        Optional.ofNullable(classInfo.getJavaType()).ifPresent(res::addAll);
        switch (template) {
            case Template.MODEL:
                TableInfo tableInfo = (TableInfo) Optional.ofNullable(param.get(TABLE)).orElse(new TableInfo());
                Optional.ofNullable(tableInfo.getFieldList()).ifPresent(p -> p.forEach(f -> Optional.ofNullable(JavaType.get(f.getJavaType())).ifPresent(res::add)));
                res.add(JavaType.get(JavaType.SERIALIZABLE));
                break;
            case Template.DAO:
                Collections.addAll(res,
                        JavaType.get(JavaType.REPOSITORY),
                        JavaType.get(JavaType.PAGINATE),
                        JavaType.get(JavaType.LIST),
                        JavaType.get(JavaType.BASE_MAPPER),
                        JavaType.get(JavaType.PARAM)
                );
                break;
            case Template.SERVICE:
                Collections.addAll(res,
                        JavaType.get(JavaType.I_Service),
                        JavaType.get(JavaType.PAGINATE)
                );
                break;
            case Template.SERVICE_IMPL:
                Collections.addAll(res,
                        String.format(MAPPER_FORMAT, classInfo.getPackageName(), classInfo.getModelName()),
                        String.format(SERVICE_FORMAT,classInfo.getPackageName(),classInfo.getModelName()),
                        JavaType.get(JavaType.AUTOWIRED), JavaType.get(JavaType.PAGINATE), JavaType.get(JavaType.SERVICE),
                        JavaType.get(JavaType.PROPAGATION),
                        JavaType.get(JavaType.SERVICE_IMPL),
                        JavaType.get(JavaType.TRANSACTIONAL)
                        );
                break;
            case Template.CONTROLLER:
                Collections.addAll(res,
                        String.format(SERVICE_FORMAT,classInfo.getPackageName(),classInfo.getModelName()),
                        JavaType.get(JavaType.PAGINATE), JavaType.get(JavaType.AUTOWIRED), JavaType.get(JavaType.POST_MAPPING),
                        JavaType.get(JavaType.REQUEST_MAPPING), JavaType.get(JavaType.REST_CONTROLLER),JavaType.get(JavaType.RESULT)
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
        param.put(GENERATE_TYPE, this.config.getGenerateType());
        Context context = new Context(Locale.CHINA);
        context.setVariables(param);
        if (GenerateConfig.FILE.equals(config.getOutputType())) {
            TemplateUtil.process(template, context, outputPath + modelName + FILE_TYPE.get(template));
        } else if (GenerateConfig.STREAM.equals(config.getOutputType())) {
            config.setWriter(new StringWriter(1024));
            TemplateUtil.process(template, context, config.getWriter());
            setPreviewData(tableName, template);
        }
    }

    private String createDir(String template,String outputType) {
        if (GenerateConfig.FILE.equals(config.getOutputType())) {
            String path = DIR_TYPE.get(Template.MAPPER).equals(template) ? "%s\\src\\main\\resources\\orm" : "%s\\src\\main\\java\\%s\\%s";
            String packagePath = this.config.getPackageName().replace(".", "\\");
            File filePath = new File(String.format(path, USER_DIR, packagePath, template));
            if (!filePath.exists()) {
                System.out.println(filePath.getPath() + " directory is not exists, create directory ...");
                filePath.mkdirs();
            }
            return filePath.getPath() + "\\";
        }else{
            return "";
        }
    }
}

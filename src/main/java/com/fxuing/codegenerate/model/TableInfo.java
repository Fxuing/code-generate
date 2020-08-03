package com.fxuing.codegenerate.model;

import com.fxuing.codegenerate.utils.StringSimpleUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.24 18:21
 * @Description:
 */
@Data
public class TableInfo {
    private String tableName;
    private String modelName;
    private String packageName;
    private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    private List<Field> fieldList;

    @Data
    public static class Field {
        private static final Map<String, String> TYPE_MAPPER = new HashMap<>(16);
        private static final String LEFT_PARENTHESIS = "(";

        static {
            TYPE_MAPPER.put("varchar", "String");
            TYPE_MAPPER.put("char", "String");
            TYPE_MAPPER.put("bigint", "Long");
            TYPE_MAPPER.put("bit", "Boolean");
            TYPE_MAPPER.put("int", "Integer");
            TYPE_MAPPER.put("decimal", "BigDecimal");
            TYPE_MAPPER.put("datetime", "LocalDateTime");
            TYPE_MAPPER.put("timestamp", "LocalDateTime");
        }

        private String name;
        private String camelName;

        private String javaType;
        private String jdbcType;
        private String comment;

        public String getCamelName() {
            return StringSimpleUtil.underlineToHump(this.name.toLowerCase());
        }

        public String getJavaType() {
            String javaType = "Object";
            if (this.jdbcType != null && !"".equals(this.jdbcType)) {
                javaType = Optional.ofNullable(TYPE_MAPPER.get(mySqlType(this.jdbcType))).orElse("");
            }
            return javaType;
        }

        private String mySqlType(String s) {
            AtomicReference<String> r = new AtomicReference<>("");
            TYPE_MAPPER.forEach((k, v) -> {
                int ind = s.indexOf(LEFT_PARENTHESIS);
                if ((ind == -1 ? s : s.substring(0, s.indexOf(LEFT_PARENTHESIS))).toLowerCase().equals(k)) {
                    r.set(k);
                }
            });
            return r.get();
        }

        public static Field getInstance(TableDetail detail) {
            Field field = new Field();
            field.setName(detail.getField());
            field.setJdbcType(detail.getType());
            field.setComment(detail.getComment());
            return field;
        }
    }
}

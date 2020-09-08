package com.obanks.codegenerate.constant;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.3 14:21
 * @Description:
 */
public class Sql {
    public static final String QUERY_TABLE_DETAIL = "SHOW FULL COLUMNS FROM %s";
    public static final String SHOW_TABLES = "SHOW TABLES";
    public static final String SHOW_TABLES_STATUS = "SHOW TABLE STATUS";
    public static final String SHOW_TABLES_STATUS_LIMIT = "SELECT table_name as Name,Engine,Version,Row_format,table_rows,Avg_row_length,\n" +
            "Data_length,Max_data_length,Index_length,Data_free,Auto_increment,\n" +
            "Create_time,Update_time,Check_time,table_collation,Checksum,\n" +
            "Create_options,table_comment as Comment FROM information_schema.tables\n" +
            "WHERE table_schema = DATABASE() LIMIT %d,%d";
}

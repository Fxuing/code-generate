package com.fxuing.codegenerate.service;


import com.fxuing.codegenerate.common.Paginate;
import com.fxuing.codegenerate.core.config.GenerateConfig;
import com.fxuing.codegenerate.dto.Tables;
import com.fxuing.codegenerate.model.SqlInfo;
import com.fxuing.codegenerate.vo.TablesVO;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.30 14:16
 * @Description:
 */
public interface GenerateService {
    /**
     * 配置
     *
     * @param config
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:52
     * @Description:
     */
    void setConfig(GenerateConfig config);

    /**
     * get config
     *
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:54
     * @Description:
     */
    GenerateConfig getConfig();

    /**
     * 生成模型
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:52
     * @Description:
     * @throws SQLException SQL异常
     */
    void generateModel(String tableName) throws SQLException;

    /**
     * 生成dao
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:52
     * @Description:
     */
    void generateDao(String tableName);

    /**
     * 生成 xml 映射
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     * @throws SQLException SQL异常
     */
    void generateMapper(String tableName) throws SQLException;

    /**
     * 生成service
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     */
    void generateService(String tableName);

    /**
     * 生成实现类
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     */
    void generateServiceImpl(String tableName);

    /**
     * 生成controller
     *
     * @param tableName
     * @Author: Hou_fx
     * @Date: 2020.8.4 10:53
     * @Description:
     */
    void generateController(String tableName);


    /**
     * 生成SQL脚本
     *
     * @param sqlInfos
     * @Author: Hou_fx
     * @Date: 2020.8.14 16:32
     * @Description:
     */
    void generateSqlScript(List<SqlInfo> sqlInfos, String tableName);

    /**
     * 表数据分页查询
     *
     * @param tablesVO
     * @return com.fxuing.codegenerate.common.Paginate<com.fxuing.codegenerate.dto.Tables>
     * @author Hou_fx
     * @date 2020.8.18 16:01
     */
    Paginate<List<Tables>> search(TablesVO tablesVO);
}

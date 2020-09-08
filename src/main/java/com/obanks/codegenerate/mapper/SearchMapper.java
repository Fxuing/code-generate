package com.obanks.codegenerate.mapper;

import com.obanks.codegenerate.common.Paginate;
import com.obanks.codegenerate.dto.Tables;
import com.obanks.codegenerate.vo.TablesVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.18 15:52
 * @Description:
 */
@Repository
public interface SearchMapper {
    Long tablesCount(Paginate<List<Tables>> tablesVO);

    List<Tables> tablesData(Paginate<List<Tables>> tablesVO);
}

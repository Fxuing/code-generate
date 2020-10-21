package com.fxuing.codegenerate.mapper;

import com.fxuing.codegenerate.common.Paginate;
import com.fxuing.codegenerate.dto.Tables;
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

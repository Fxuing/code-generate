package com.fxuing.codegenerate.vo;

import com.fxuing.codegenerate.common.Paginate;
import com.fxuing.codegenerate.dto.Tables;
import lombok.Data;

import java.util.List;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.18 14:37
 * @Description:
 */
@Data
public class TablesVO {
    private String keyword;
    private Paginate<List<Tables>> paginate;
}

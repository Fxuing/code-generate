package com.obanks.codegenerate.vo;

import com.obanks.codegenerate.common.Paginate;
import com.obanks.codegenerate.dto.Tables;
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

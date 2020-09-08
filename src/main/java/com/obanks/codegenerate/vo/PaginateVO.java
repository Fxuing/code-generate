package com.obanks.codegenerate.vo;

import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.18 14:37
 * @Description:
 */
@Data
public class PaginateVO {
    protected Integer recordsPerPage ;
    protected Integer currentPageNum ;
}

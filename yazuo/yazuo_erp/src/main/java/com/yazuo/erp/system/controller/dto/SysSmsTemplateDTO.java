package com.yazuo.erp.system.controller.dto;

import com.yazuo.erp.system.vo.SysSmsTemplateVO;

/**
 *
 */
public class SysSmsTemplateDTO extends SysSmsTemplateVO {
    private int pageSize;
    private int pageNumber;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}

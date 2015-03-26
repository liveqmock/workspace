package com.yazuo.erp.system.controller.dto;

import com.yazuo.erp.system.vo.SysKnowledgeProveVO;

/**
 */
public class SysKnowledgeProveDTO extends SysKnowledgeProveVO {
    private Integer pageSize;
    private Integer pageNumber;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "SysKnowledgeProveDTO{" +
                "pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }
}

package com.yazuo.erp.api.service;

import com.yazuo.erp.api.vo.SysReportParams;

/**
 */
public interface SysReportParamsService {

    int insert(SysReportParams record);

    int delete(String key);

    int update(SysReportParams record);

    SysReportParams get(String key);
}

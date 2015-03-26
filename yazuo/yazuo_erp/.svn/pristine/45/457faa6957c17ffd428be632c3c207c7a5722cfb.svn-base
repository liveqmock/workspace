package com.yazuo.erp.api.service.impl;

import com.yazuo.erp.api.dao.SysReportParamsDao;
import com.yazuo.erp.api.service.SysReportParamsService;
import com.yazuo.erp.api.vo.SysReportParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 */
@Service
public class SysReportParamsServiceImpl implements SysReportParamsService{

    @Resource
    private SysReportParamsDao sysReportParamsDao;

    @Override
    public int insert(SysReportParams record) {
        return this.sysReportParamsDao.insert(record);
    }

    @Override
    public int delete(String key) {
        return this.sysReportParamsDao.delete(key);
    }

    @Override
    public int update(SysReportParams record) {
        return this.sysReportParamsDao.update(record);
    }

    @Override
    public SysReportParams get(String key) {
        return this.sysReportParamsDao.get(key);
    }
}

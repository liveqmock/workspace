package com.yazuo.erp.train.quartz;

import com.yazuo.erp.train.service.StudentService;

import javax.annotation.Resource;

/**
 *
 */
public class ExpireTimer {
    @Resource
    private StudentService studentService;

    public void synchronizeExpiredState() {
        this.studentService.synchronizeExpiredState();
    }
}

package com.yazuo.erp.bes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.service.BesMonthlyReportService;

/**
 *
 */
@Controller
@RequestMapping("besMonthlyApi")
public class BesMonthlyApiController {
    @Resource
    private BesMonthlyReportService besMonthlyReportService;

    @RequestMapping(value = "saveOrCheckSendMonthlyReport")
    @ResponseBody
    public Object saveOrCheckSendMonthlyReport() {
        this.besMonthlyReportService.saveOrCheckSendMonthlyReport();
        return new JsonResult(true, "发送月报检查完成");
    }

    @RequestMapping(value = "checkIfCompletedWorkPlan")
    @ResponseBody
    public Object checkIfCompletedWorkPlan() {
        this.besMonthlyReportService.checkIfCompletedWorkPlan();
        return new JsonResult(true, "月报回访工作计划检查完成");
    }
}

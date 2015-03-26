package com.yazuo.erp.bes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.service.BesRequirementService;

/**
 * 后台接口API
 */
@Controller
@RequestMapping("besApi")
public class BesApiController {
    @Resource
    private BesRequirementService besRequirementService;

    @RequestMapping(value = "saveReqRemindForTimer")
    @ResponseBody
    public Object saveReqRemindForTimer() {
        this.besRequirementService.saveReqRemindForTimer();
        return new JsonResult(true, "提醒保存完成！");
    }

}

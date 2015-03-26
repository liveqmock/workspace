package com.yazuo.erp.api.controller;

import com.yazuo.erp.api.service.RenewCardtypeService;
import com.yazuo.erp.base.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 */
@Controller
@RequestMapping("renewCard")
public class RenewCardController {
    @Resource
    private RenewCardtypeService renewCardtypeService;

    @RequestMapping("statistic")
    @ResponseBody
    public JsonResult staticForRenewCard() {
        this.renewCardtypeService.statics();
        return new JsonResult(true, "统计完成");
    }
}

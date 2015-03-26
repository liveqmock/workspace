package com.yazuo.erp.api.controller;

import com.yazuo.erp.api.controller.dto.ReportDataDTO;
import com.yazuo.erp.api.service.ReportDataService;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.service.SysSmsTemplateService;
import com.yazuo.erp.system.vo.SysSmsTemplateVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("report")
public class H5ReportDataController {
    @Resource
    private ReportDataService reportDataService;

    @Resource
    private SysSmsTemplateService sysSmsTemplateService;

    /**
     * H5月报推送服务
     *
     * @param dataDTO
     * @return
     */
    @RequestMapping("marketing")
    @ResponseBody
    public JsonResult getH5ReportData(@RequestBody ReportDataDTO dataDTO) {
        Map<String, Object> result = this.reportDataService.getMarketingReportData(dataDTO.getKey());
        return new JsonResult(true, null).setData(result);
    }

    @RequestMapping("mobiles")
    @ResponseBody
    public JsonResult getMobiles(Integer merchantId) {
        List<String> result = this.sysSmsTemplateService.getAllMobiles(merchantId, SysSmsTemplateVO.TPL_TYPE.MONTHLY);
        return new JsonResult(true, null).setData(result);
    }
}

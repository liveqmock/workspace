package com.yazuo.erp.system.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.controller.dto.SysKnowledgeProveDTO;
import com.yazuo.erp.system.service.SysKnowledgeProveService;
import com.yazuo.erp.system.vo.SysKnowledgeProveVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 */
@Controller
@RequestMapping("knowledgeProve")
public class SysKnowledgeProveController extends AbstractBasicController {

    @Resource
    private SysKnowledgeProveService sysKnowledgeProveService;

    @RequestMapping("add")
    @ResponseBody
    public JsonResult addKnowledgeProve(@RequestBody SysKnowledgeProveVO knowledgeProveVO, HttpSession session) {
        knowledgeProveVO.setUserId(this.getUserId(session));
        this.sysKnowledgeProveService.saveKnowledgeProveVO(knowledgeProveVO);
        Integer knowledgeId = knowledgeProveVO.getKnowledgeId();
        Map<Boolean, Integer> knowledgeProved = this.sysKnowledgeProveService.getStaticForProved(knowledgeId);
        double score = this.sysKnowledgeProveService.getStatForScore(knowledgeId);
        return new JsonResult(true, "保存完成").putData("knowledgeProved", knowledgeProved).putData("score", score);
    }

    @RequestMapping("search")
    @ResponseBody
    public JsonResult searchKnowledgeProve(@RequestBody SysKnowledgeProveDTO knowledgeProveDTO) {
        PageHelper.startPage(knowledgeProveDTO.getPageNumber(), knowledgeProveDTO.getPageSize(), true, true);
        Page<SysKnowledgeProveVO> pagination = this.sysKnowledgeProveService.getAllKnowledgeProves(knowledgeProveDTO);
        // 只查询列表
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.ROWS, pagination.getResult());
        result.put(Constant.TOTAL_SIZE, pagination.getTotal());
        return new JsonResult(true, "查询完成").putData("resultSet",result);
    }
}

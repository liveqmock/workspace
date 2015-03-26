package com.yazuo.erp.system.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.controller.dto.SysCommentaryDTO;
import com.yazuo.erp.system.service.SysCommentaryService;
import com.yazuo.erp.system.service.SysKnowledgeProveService;
import com.yazuo.erp.system.vo.SysCommentaryVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 */
@Controller
@RequestMapping("commentary")
public class SysCommentaryController extends AbstractBasicController {
    @Resource
    private SysCommentaryService sysCommentaryService;

    @Resource
    private SysKnowledgeProveService sysKnowledgeProveService;

    @RequestMapping("add")
    @ResponseBody
    public JsonResult addCommentary(@RequestBody SysCommentaryDTO commentaryDTO, HttpSession session) {
        commentaryDTO.setUserId(this.getUserId(session));
        this.sysCommentaryService.saveCommentaryAndScore(commentaryDTO);
        double score = this.sysKnowledgeProveService.getStatForScore(commentaryDTO.getKnowledgeId());
        return new JsonResult(true, null).putData("score",score);
    }

    @RequestMapping("search")
    @ResponseBody
    public JsonResult searchCommentary(@RequestBody SysCommentaryDTO commentaryDTO) {
        PageHelper.startPage(commentaryDTO.getPageNumber(), commentaryDTO.getPageSize(), true, true);
        Page<SysCommentaryVO> commentaryVOPage = this.sysCommentaryService.getCommentaries(commentaryDTO.getKnowledgeId());
        return new JsonResult(true, null)
                .putData(Constant.ROWS, commentaryVOPage.getResult())
                .putData(Constant.TOTAL_SIZE, commentaryVOPage.getTotal());
    }
}

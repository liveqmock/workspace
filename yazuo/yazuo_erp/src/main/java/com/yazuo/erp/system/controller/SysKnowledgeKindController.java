package com.yazuo.erp.system.controller;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.service.SysKnowledgeKindService;
import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("knowledgeKind")
public class SysKnowledgeKindController {
    @Resource
    private SysKnowledgeKindService sysKnowledgeKindService;

    @RequestMapping("save")
    @ResponseBody
    public JsonResult saveKnowledgeKind(HttpSession session, @RequestBody SysKnowledgeKindVO knowledgeKindVO) {
        SysKnowledgeKindVO persistKnowledgeVO = this.sysKnowledgeKindService.saveKnowledgeKind(knowledgeKindVO);
        return new JsonResult(true, "添加完成").setData(persistKnowledgeVO);
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult updateKnowledgeKind(HttpSession session, @RequestBody SysKnowledgeKindVO knowledgeKindVO) {
        SysKnowledgeKindVO persistKnowledgeVO = this.sysKnowledgeKindService.updateKnowledgeKind(knowledgeKindVO);
        return new JsonResult(true, "更新完成").setData(persistKnowledgeVO);
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult deleteKnowledgeKind(HttpSession session, @RequestBody SysKnowledgeKindVO knowledgeKindVO) {
        Integer knowledgeKindId = knowledgeKindVO.getId();
        boolean hasChildren = this.sysKnowledgeKindService.hasChildren(knowledgeKindId);
        if (hasChildren) {
            return new JsonResult(false, "存在子类不可删除").putData("success", false).putData("erroCode", 1);
        } else if (this.sysKnowledgeKindService.hasKnowledge(knowledgeKindId)) {
            return new JsonResult(false, "存在知识记录不可删除").putData("success", false).putData("erroCode", 2);
        } else {
            SysKnowledgeKindVO kindVO = this.sysKnowledgeKindService.deleteKnowledgeKind(knowledgeKindVO.getId());
            return new JsonResult(true, "删除成功").setData(kindVO);
        }
    }

    @RequestMapping("getAll")
    @ResponseBody
    public JsonResult getAllKnowledgeKinds() {
        List<SysKnowledgeKindVO> knowledgeKindVOs = this.sysKnowledgeKindService.getAllKnowledgeKinds();
        return new JsonResult(true, "查询完成").setData(knowledgeKindVOs);
    }

    @RequestMapping("search")
    @ResponseBody
    public JsonResult searchKnowledgeKinds(@RequestBody SysKnowledgeKindVO knowledgeKindVO) {
        // 包含父分类
        List<SysKnowledgeKindVO> knowledgeKindVOs = this.sysKnowledgeKindService.searchKnowledgeKinds(knowledgeKindVO);
        return new JsonResult(true, null).setData(knowledgeKindVOs);
    }
}

package com.yazuo.erp.system.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.mkt.service.MktBusinessTypeService;
import com.yazuo.erp.mkt.vo.MktBusinessTypeVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.service.*;
import com.yazuo.erp.system.vo.*;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Controller
@RequestMapping("knowledge")
public class SysKnowledgeController extends AbstractBasicController {
    @Resource
    private SysKnowledgeService sysKnowledgeService;

    @Resource
    private SysKnowledgeKindService sysKnowledgeKindService;

    @Resource
    private MktBusinessTypeService mktBusinessTypeService;

    @Resource
    private SysKnowledgeProveService sysKnowledgeProveService;

    @Resource
    private SysCommentaryService sysCommentaryService;

    @Resource
    private SysAttachmentService sysAttachmentService;

    @Resource
    private SynMerchantService synMerchantService;

    @RequestMapping("save")
    @ResponseBody
    public JsonResult saveKnowledge(HttpSession session, @RequestBody SysKnowledgeVO sysKnowledgeVO) {
        if (sysKnowledgeVO.getId() == null) {
            return this.addKnowledge(session, sysKnowledgeVO);
        } else {
            return this.updateKnowledge(session, sysKnowledgeVO);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult deleteKnowledge(HttpSession session, @RequestBody SysKnowledgeVO sysKnowledgeVO) {
        Integer knowledgeId = sysKnowledgeVO.getId();
        this.sysKnowledgeService.deleteKnowledge(knowledgeId, this.getApplicationRoot(session));
        return new JsonResult(true, "删除完成");
    }


    @RequestMapping("get")
    @ResponseBody
    public JsonResult getKnowledge(HttpSession session, @RequestBody SysKnowledgeVO sysKnowledgeVO) {
        Integer knowledgeId = sysKnowledgeVO.getId();
        boolean hasId = knowledgeId != null;
        List<SysKnowledgeKindVO> knowledgeKindVOs = this.sysKnowledgeKindService.getAllKnowledgeKinds();
        List<MktBusinessTypeVO> businessTypeVOs = this.mktBusinessTypeService.getAllTopMktBusinessTypes();
        JsonResult result = new JsonResult(true, "查询完成")
                .putData("knowledgeKinds", knowledgeKindVOs)
                .putData("businessTypes", businessTypeVOs);
        if (hasId) {
            SysKnowledgeVO knowledgeVO = this.sysKnowledgeService.getKnowledge(knowledgeId);
            result.putData("knowledge", knowledgeVO);
            this.loadAttach(knowledgeVO);
            this.loadMerchantName(knowledgeVO);
        }
        return result;
    }

    @RequestMapping("view")
    @ResponseBody
    public JsonResult viewKnowledge(HttpSession session, @RequestBody SysKnowledgeVO sysKnowledgeVO) {
        List<SysKnowledgeKindVO> knowledgeKindVOs = this.sysKnowledgeKindService.getAllKnowledgeKinds();
        List<MktBusinessTypeVO> businessTypeVOs = this.mktBusinessTypeService.getAllTopMktBusinessTypes();
        double score = this.sysKnowledgeProveService.getStatForScore(sysKnowledgeVO.getId());
        Map<Boolean, Integer> proved = this.sysKnowledgeProveService.getStaticForProved(sysKnowledgeVO.getId());
        Map<Object, Object> statsMap = new HashMap<Object, Object>();
        statsMap.put("score", score);
        statsMap.put("approved", proved);

        //添加浏览次数，并返回相应的知识记录
        SysKnowledgeVO knowledgeVO = this.addViewTimes(sysKnowledgeVO.getId());
        Integer knowledgeId = knowledgeVO.getId();

        Integer userId = this.getUserId(session);
        //是否评论过
        boolean hasProved = this.sysKnowledgeProveService.hasRecordForProved(userId, knowledgeId);
        boolean hasScore = this.sysKnowledgeProveService.hasRecordForScore(userId, knowledgeId);

        PageHelper.startPage(sysKnowledgeVO.getPageNumber(), sysKnowledgeVO.getPageSize(), true, true);
        Page<SysCommentaryVO> commentaryVOs = this.sysCommentaryService.getCommentaries(knowledgeId);
        Map<String, Object> commentaries = new HashMap<String, Object>();
        commentaries.put(Constant.ROWS, commentaryVOs.getResult());
        commentaries.put(Constant.TOTAL_SIZE, commentaryVOs.getTotal());
        // 查询附件信息
        this.loadAttach(knowledgeVO);
        this.loadScore(knowledgeVO, userId);
        //返回结果
        JsonResult jsonResult = new JsonResult(true, null);
        jsonResult.putData("knowledgeKinds", knowledgeKindVOs)
                .putData("businessTypes", businessTypeVOs)
                .putData("stats", statsMap)
                .putData("knowledge", knowledgeVO)
                .putData("provedAvail", !hasProved)
                .putData("scoreAvail", !hasScore)
                .putData("commentaries", commentaries);
        return jsonResult;
    }


    @RequestMapping("search")
    @ResponseBody
    public JsonResult searchKnowledge(HttpSession session, @RequestBody SysKnowledgeVO sysKnowledgeVO) {
        PageHelper.startPage(sysKnowledgeVO.getPageNumber(), sysKnowledgeVO.getPageSize(), true, true);
        Page<SysKnowledgeVO> knowledgeVOs = this.sysKnowledgeService.getKnowledges(sysKnowledgeVO);
        Map<String, Object> resultSet = new HashMap<String, Object>();
        resultSet.put(Constant.ROWS, knowledgeVOs.getResult());
        resultSet.put(Constant.TOTAL_SIZE, knowledgeVOs.getTotal());

        this.setKindCn(knowledgeVOs); // 添加中文
        return new JsonResult(true, "查询完成").putData("resultSet", resultSet);
    }

    @RequestMapping("getAll")
    @ResponseBody
    public JsonResult getAllKnowledge(@RequestBody SysKnowledgeVO sysKnowledgeVO) {
        PageHelper.startPage(sysKnowledgeVO.getPageNumber(), sysKnowledgeVO.getPageSize(), true, true);
        Page<SysKnowledgeVO> knowledgeVOs = this.sysKnowledgeService.getKnowledges(sysKnowledgeVO);
        Map<String, Object> resultSet = new HashMap<String, Object>();
        resultSet.put(Constant.ROWS, knowledgeVOs.getResult());
        resultSet.put(Constant.TOTAL_SIZE, knowledgeVOs.getTotal());
        this.setKindCn(knowledgeVOs);
        return new JsonResult(true, "查询完成")
                .putData("resultSet", resultSet)
                .putData("allKnowledgeKinds", this.sysKnowledgeKindService.getAllKnowledgeKinds());
    }


    @RequestMapping("upload")
    @ResponseBody
    public JsonResult uploadAttachment(MultipartFile file, HttpServletRequest request) {
        Integer userId = this.getUserId(request);
        String fileDirectory = this.getApplicationRoot(request.getSession());
        // 上传文件
        SysAttachmentVO attachmentVO = this.sysAttachmentService.saveAttachmentAndFile(file, "sys", "knowledge", "3", userId, fileDirectory);
        attachmentVO.setFileFullPath(attachmentVO.getAttachmentPath() + "/" + attachmentVO.getAttachmentName());
        // 返回相应的参数(包括URL)
        return new JsonResult(true, null).setData(attachmentVO);
    }

    protected SysKnowledgeVO addViewTimes(Integer knowledgeId) {
        SysKnowledgeVO sysKnowledgeVO = this.sysKnowledgeService.getKnowledge(knowledgeId);
        Integer viewedTimes = sysKnowledgeVO.getViewedTimes();
        sysKnowledgeVO.setViewedTimes((viewedTimes == null ? 0 : viewedTimes) + 1);
        this.sysKnowledgeService.updateKnowledge(sysKnowledgeVO);
        return sysKnowledgeVO;
    }

    private JsonResult addKnowledge(HttpSession session, @RequestBody SysKnowledgeVO sysKnowledgeVO) {
        Integer userId = this.getUserId(session);
        sysKnowledgeVO.setCreatedBy(userId);
        sysKnowledgeVO.setCreatedTime(new Date());
        sysKnowledgeVO.setUpdatedTime(new Date());
        sysKnowledgeVO.setUpdatedBy(userId);
        SysKnowledgeVO knowledgeVO = this.sysKnowledgeService.saveKnowledge(sysKnowledgeVO);
        return new JsonResult(true, "保存完成");
    }

    private JsonResult updateKnowledge(HttpSession session, @RequestBody SysKnowledgeVO sysKnowledgeVO) {
        sysKnowledgeVO.setUpdatedTime(new Date());
        sysKnowledgeVO.setUpdatedBy(this.getUserId(session));
        SysKnowledgeVO resultKnowledgeVO = this.sysKnowledgeService.updateKnowledgeAndFile(sysKnowledgeVO, this.getApplicationRoot(session));
        return new JsonResult(true, "更新");
    }

    /**
     * 设置知识分类的类型
     *
     * @param knowledgeVOs
     */
    private void setKindCn(List<SysKnowledgeVO> knowledgeVOs) {
        List<SysKnowledgeKindVO> knowledgeKindVOs = this.sysKnowledgeKindService.getAllKnowledgeKinds();
        Map<Integer, String> idToTitleMap = new HashMap<Integer, String>();
        for (SysKnowledgeKindVO kindVO : knowledgeKindVOs) {
            idToTitleMap.put(kindVO.getId(), kindVO.getTitle());
        }

        for (SysKnowledgeVO knowledgeVO : knowledgeVOs) {
            knowledgeVO.setKindCn(idToTitleMap.get(knowledgeVO.getKindId()));
        }
    }

    /**
     * 加截知识记录的附件信息
     *
     * @param knowledgeVO
     */
    private void loadAttach(SysKnowledgeVO knowledgeVO) {
        if (knowledgeVO.getAttachmentId() != null) {
            SysAttachmentVO attachmentVO = this.sysAttachmentService.getSysAttachmentById(knowledgeVO.getAttachmentId());
            attachmentVO.setFileFullPath("/attachment/download.do?attachmentId=" + attachmentVO.getId());
            knowledgeVO.setAttachment(attachmentVO);
        }
    }

    private void loadMerchantName(SysKnowledgeVO knowledgeVO) {
        if (knowledgeVO.getMerchantId() != null) {
            SynMerchantVO merchantVO = this.synMerchantService.getSynMerchantById(knowledgeVO.getMerchantId());
            knowledgeVO.setMerchantName(merchantVO.getMerchantName());
        }
    }

    /**
     * 加载分数
     * @param knowledgeVO
     * @param userId
     */
    private void loadScore(SysKnowledgeVO knowledgeVO, Integer userId) {
        SysKnowledgeProveVO proveVO = this.sysKnowledgeProveService.getScore(userId, knowledgeVO.getId());
        if (proveVO != null) {
            knowledgeVO.setScore(proveVO.getScore());
        }else{
            knowledgeVO.setScore(5);
        }
    }
}

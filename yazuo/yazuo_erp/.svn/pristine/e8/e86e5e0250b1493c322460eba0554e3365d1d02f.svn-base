package com.yazuo.erp.sys.service;

import com.yazuo.erp.BasicServiceTest;
import com.yazuo.erp.system.service.SysKnowledgeService;
import com.yazuo.erp.system.vo.SysKnowledgeVO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 */
public class SysKnowledgeServiceTest extends BasicServiceTest {

    @Resource
    private SysKnowledgeService knowledgeService;

    @Test
    public void testSaveKnowledgeVO() {
        SysKnowledgeVO knowledgeVO = new SysKnowledgeVO();
        knowledgeVO.setTitle("知识记录名称");
        knowledgeVO.setAnalysis("分析");
        knowledgeVO.setKindId(7);
        knowledgeVO.setMerchantId(1314);
        knowledgeVO.setBusinessTypeId(1);
        knowledgeVO.setDescription("描述");
        knowledgeVO.setUpdatedBy(999999);
        knowledgeVO.setUpdatedTime(new Date());
        knowledgeVO.setCreatedBy(999999);
        knowledgeVO.setCreatedTime(new Date());
        knowledgeVO.setViewedTimes(0);
        this.knowledgeService.saveKnowledge(knowledgeVO);
    }
}

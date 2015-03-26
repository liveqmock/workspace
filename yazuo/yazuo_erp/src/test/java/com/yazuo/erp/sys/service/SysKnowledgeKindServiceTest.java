package com.yazuo.erp.sys.service;

import com.yazuo.erp.BasicServiceTest;
import com.yazuo.erp.system.service.SysKnowledgeKindService;
import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
public class SysKnowledgeKindServiceTest extends BasicServiceTest {
    @Resource
    private SysKnowledgeKindService sysKnowledgeKindService;

    @Test
    public void testSaveKnowledgeKind() {
        SysKnowledgeKindVO knowledgeKindVO = new SysKnowledgeKindVO();
        knowledgeKindVO.setParentId(null);
        knowledgeKindVO.setTitle("一级分类2");
        this.sysKnowledgeKindService.saveKnowledgeKind(knowledgeKindVO);
    }

    @Test
    public void testParentSaveKnowledgeKind() {
        SysKnowledgeKindVO knowledgeKind = new SysKnowledgeKindVO();
        knowledgeKind.setTitle("一级分类");
        this.sysKnowledgeKindService.saveKnowledgeKind(knowledgeKind);

        SysKnowledgeKindVO knowledgeKindVO = new SysKnowledgeKindVO();
        knowledgeKindVO.setParentId(knowledgeKind.getId());
        knowledgeKindVO.setTitle("三级分类");
        this.sysKnowledgeKindService.saveKnowledgeKind(knowledgeKindVO);
    }

    @Test
    public void testUpdateKnowledgeKind() {
        SysKnowledgeKindVO knowledgeKindVO = this.sysKnowledgeKindService.getKnowledgeKind(6);
        knowledgeKindVO.setTitle("更新分类标题2");
        this.sysKnowledgeKindService.updateKnowledgeKind(knowledgeKindVO);
    }

    @Test
    public void testDeleteKnowledgeKind() {
        SysKnowledgeKindVO vo = this.createKnowledgeVO();
        SysKnowledgeKindVO knowledgeKindVO = this.sysKnowledgeKindService.deleteKnowledgeKind(vo.getId());
        SysKnowledgeKindVO other = this.sysKnowledgeKindService.getKnowledgeKind(knowledgeKindVO.getId());
    }

    @Test
    public void testExistChildren() {
        List<SysKnowledgeKindVO> childrenKnowledgeKinds = this.sysKnowledgeKindService.getChildrenKnowledgeKinds(4);
        System.out.println(childrenKnowledgeKinds);
    }

    @Test
    public void testHasChildren() {
        boolean hasChildren = this.sysKnowledgeKindService.hasChildren(4);
        System.out.println(hasChildren);
    }

    @Test
    public void testHasKnowledge() {
        Assert.assertFalse(this.sysKnowledgeKindService.hasKnowledge(4));
    }

    private SysKnowledgeKindVO createKnowledgeVO() {
        SysKnowledgeKindVO knowledgeKindVO = new SysKnowledgeKindVO();
        knowledgeKindVO.setParentId(null);
        knowledgeKindVO.setTitle("三级分类");
        this.sysKnowledgeKindService.saveKnowledgeKind(knowledgeKindVO);
        return knowledgeKindVO;
    }
}

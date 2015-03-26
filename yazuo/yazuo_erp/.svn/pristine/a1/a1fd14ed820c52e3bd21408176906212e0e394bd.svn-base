package com.yazuo.erp.system.service.impl;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.dao.SysKnowledgeDao;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysKnowledgeKindService;
import com.yazuo.erp.system.service.SysKnowledgeService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import com.yazuo.erp.system.vo.SysKnowledgeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 */
@Service
public class SysKnowledgeServiceImpl implements SysKnowledgeService {
    @Resource
    private SysKnowledgeKindService sysKnowledgeKindService;

    @Resource
    private SysKnowledgeDao sysKnowledgeDao;

    @Resource
    private SysAttachmentService sysAttachmentService;

    @Override
    public SysKnowledgeVO getKnowledge(Integer id) {
        return this.sysKnowledgeDao.getKnowledge(id);
    }

    @Override
    public SysKnowledgeVO updateKnowledge(SysKnowledgeVO knowledgeVO) {
        SysKnowledgeVO persistVO = this.sysKnowledgeDao.getKnowledge(knowledgeVO.getId());
        BeanUtils.copyProperties(knowledgeVO, persistVO, new String[]{"createdTime", "createdBy", "updatedBy", "updatedTime"});
        int num = this.sysKnowledgeDao.updateKnowledge(persistVO);
        return num > 0 ? persistVO : knowledgeVO;
    }

    @Override
    public SysKnowledgeVO updateKnowledgeAndFile(SysKnowledgeVO knowledgeVO,String appRoot) {
        SysKnowledgeVO persistVO = this.sysKnowledgeDao.getKnowledge(knowledgeVO.getId());
        Integer originalAttachmentId = persistVO.getAttachmentId();
        BeanUtils.copyProperties(knowledgeVO, persistVO, new String[]{"createdTime", "createdBy", "updatedBy", "updatedTime"});
        int num = this.sysKnowledgeDao.updateKnowledge(persistVO);

        // 如果更新attachmentId，则删除附件
        boolean needDelete = originalAttachmentId != null && !originalAttachmentId.equals(knowledgeVO.getAttachmentId());
        if (needDelete) {
            this.sysAttachmentService.deleteSysAttachmentAndFile(appRoot, originalAttachmentId);
        }
        return num > 0 ? persistVO : knowledgeVO;
    }

    @Override
    public SysKnowledgeVO deleteKnowledge(Integer id, String appRoot) {
        SysKnowledgeVO knowledgeVO = this.sysKnowledgeDao.getKnowledge(id);
        // 评分信息
        this.sysKnowledgeDao.deleteKnowledgeProve(id);
        // 删除评论信息
        this.sysKnowledgeDao.deleteKnowledgeCommentary(id);
        this.sysKnowledgeDao.deleteKnowledge(id);
        //删除附件
        if (knowledgeVO.getAttachmentId() != null) {
            SysAttachmentVO attachmentVO = this.sysAttachmentService.getSysAttachmentById(knowledgeVO.getAttachmentId());
            if (attachmentVO != null) {
                this.sysAttachmentService.deleteSysAttachmentAndFile(appRoot,attachmentVO.getId());
            }
        }
        return knowledgeVO;
    }

    @Override
    public SysKnowledgeVO saveKnowledge(SysKnowledgeVO knowledgeVO) {
        knowledgeVO.setViewedTimes(0);
        int num = this.sysKnowledgeDao.saveKnowledge(knowledgeVO);
        return num > 0 ? knowledgeVO : null;
    }

    @Override
    public SysAttachmentVO getAttachment(Integer knowledgeId) {
        return this.sysKnowledgeDao.getAttachment(knowledgeId);
    }

    @Override
    public Page<SysKnowledgeVO> getKnowledges(SysKnowledgeVO knowledgeVO) {
        return this.sysKnowledgeDao.getKnowledges(knowledgeVO);
    }

    @Override
    public List<SysKnowledgeKindVO> getKnowledgeKinds(Integer knowledgeId) {
        SysKnowledgeKindVO sysKnowledgeKindVO = this.sysKnowledgeDao.getKnowledgeKind(knowledgeId);
        return this.sysKnowledgeKindService.getAncestors(sysKnowledgeKindVO.getId());
    }
}

package com.yazuo.erp.system.service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import com.yazuo.erp.system.vo.SysKnowledgeVO;

import java.util.List;

public interface SysKnowledgeService {
    /**
     * 得到知识记录
     *
     * @param id
     * @return
     */
    SysKnowledgeVO getKnowledge(Integer id);

    /**
     * 更新知识记录
     *
     * @param knowledgeVO
     * @return
     */
    SysKnowledgeVO updateKnowledge(SysKnowledgeVO knowledgeVO);

    /**
     *
     * @param knowledgeVO
     * @param appRoot
     * @return
     */
    SysKnowledgeVO updateKnowledgeAndFile(SysKnowledgeVO knowledgeVO, String appRoot);

    /**
     * 删除知识记录
     *
     * @param id
     * @param appRoot
     * @return
     */
    SysKnowledgeVO deleteKnowledge(Integer id, String appRoot);

    /**
     * 创建知识记录
     *
     * @param knowledgeVO
     * @return
     */
    SysKnowledgeVO saveKnowledge(SysKnowledgeVO knowledgeVO);

    /**
     * 附件
     *
     * @param knowledgeId
     * @return
     */
    SysAttachmentVO getAttachment(Integer knowledgeId);

    /**
     * 知识记录信息
     *
     * @param knowledgeVO
     * @return
     */
    Page<SysKnowledgeVO> getKnowledges(SysKnowledgeVO knowledgeVO);

    /**
     * 知识记录所属分类
     *
     * @param knowledgeId
     * @return
     */
    List<SysKnowledgeKindVO> getKnowledgeKinds(Integer knowledgeId);
}
package com.yazuo.erp.system.dao;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import com.yazuo.erp.system.vo.SysKnowledgeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysKnowledgeDao {
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
    int updateKnowledge(@Param("knowledge") SysKnowledgeVO knowledgeVO);

    /**
     * 删除知识记录
     *
     * @param id
     * @return
     */
    int deleteKnowledge(Integer id);

    int deleteKnowledgeProve(Integer knowledgeId);

    int deleteKnowledgeCommentary(Integer knowledgeId);
    /**
     * 创建知识记录
     *
     * @param knowledgeVO
     * @return
     */
    int saveKnowledge(SysKnowledgeVO knowledgeVO);

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
    SysKnowledgeKindVO getKnowledgeKind(Integer knowledgeId);
}
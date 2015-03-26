package com.yazuo.erp.system.dao;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import com.yazuo.erp.system.vo.SysKnowledgeProveVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysKnowledgeProveDao {
    /**
     * 添加评分或是否有用信息
     * @param knowledgeProveVO
     * @return
     */
    int saveKnowledgeProveVO(SysKnowledgeProveVO knowledgeProveVO);

    /**
     * 得到一个知识记录的评论
     * @param knowledgeProveVO
     * @return
     */
    Page<SysKnowledgeProveVO> getAllKnowledgeProves(SysKnowledgeProveVO knowledgeProveVO);

    /**
     * 是否有用评分
     * @param knowledgeId
     * @return
     */
    List<Map<String,Object>> getStaticForProved(Integer knowledgeId);

    /**
     * 评分统计信息
     * @param knowledgeId
     * @return
     */
    Double getStatForScore(Integer knowledgeId);

    /**
     * 是否存在评论
     *
     * @param userId
     * @return
     */
    List<SysKnowledgeProveVO> getRecordForProved(@Param("userId") Integer userId, @Param("knowledgeId") Integer knowledgeId);

    /**
     * 是否有评分记录
     * @param userId
     * @return
     */
    List<SysKnowledgeProveVO> getRecordForScore(@Param("userId") Integer userId, @Param("knowledgeId") Integer knowledgeId);
}
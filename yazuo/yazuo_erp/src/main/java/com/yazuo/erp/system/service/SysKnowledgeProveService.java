package com.yazuo.erp.system.service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysKnowledgeProveVO;

import java.util.Map;

public interface SysKnowledgeProveService {
    /**
     * 添加评分或是否有用信息
     * @param knowledgeProveVO
     * @return
     */
    SysKnowledgeProveVO saveKnowledgeProveVO(SysKnowledgeProveVO knowledgeProveVO);

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
    Map<Boolean, Integer> getStaticForProved(Integer knowledgeId);

    /**
     * 评分统计信息
     * @param knowledgeId
     * @return
     */
    double getStatForScore(Integer knowledgeId);

    /**
     * 是否存在评论
     * @param userId
     * @param knowledgeId
     * @return
     */
    boolean hasRecordForProved(Integer userId, Integer knowledgeId);

    /**
     * 是否有评分记录
     * @param userId
     * @param knowledgeId
     * @return
     */
    boolean hasRecordForScore(Integer userId, Integer knowledgeId);

    /**
     *
     * @param userId
     * @param knowledgeId
     * @return
     */
    SysKnowledgeProveVO getScore(Integer userId, Integer knowledgeId);

}
package com.yazuo.erp.system.service;

import com.yazuo.erp.system.vo.SysKnowledgeKindVO;

import java.util.List;

/**
 * 知识分类Service接口
 */
public interface SysKnowledgeKindService {
    /**
     * 添加知识分类
     *
     * @param knowledgeKindVO
     * @return
     */
    SysKnowledgeKindVO saveKnowledgeKind(SysKnowledgeKindVO knowledgeKindVO);

    /**
     * 得到知识分类
     *
     * @param id
     * @return
     */
    SysKnowledgeKindVO getKnowledgeKind(Integer id);

    /**
     * 更新知识分类
     *
     * @param knowledgeKind
     * @return
     */
    SysKnowledgeKindVO updateKnowledgeKind(SysKnowledgeKindVO knowledgeKind);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    SysKnowledgeKindVO deleteKnowledgeKind(Integer id);

    /**
     * 得到所有的分类
     *
     * @return
     */
    List<SysKnowledgeKindVO> getAllKnowledgeKinds();

    /**
     * 模糊查询知识记录
     *
     * @param knowledgeKindVO
     * @return
     */
    List<SysKnowledgeKindVO> searchKnowledgeKinds(SysKnowledgeKindVO knowledgeKindVO);


    /**
     * 得到子分类
     *
     * @param parentId
     * @return
     */
    List<SysKnowledgeKindVO> getChildrenKnowledgeKinds(Integer parentId);

    List<SysKnowledgeKindVO> getAncestors(Integer knowledgeKindId);

    /**
     * 是否  不存在子分类
     *
     * @param id
     * @return
     */
    boolean hasNoChildren(Integer id);

    /**
     * 是否有子分类
     *
     * @param id
     * @return
     */
    boolean hasChildren(Integer id);

    /**
     * 分类下面是否有知识记录
     *
     * @param id
     * @return
     */
    boolean hasKnowledge(Integer id);

    /**
     * 分类下面是否 不存在知识记录
     *
     * @param id
     * @return
     */
    boolean hasNoKnowledge(Integer id);
}
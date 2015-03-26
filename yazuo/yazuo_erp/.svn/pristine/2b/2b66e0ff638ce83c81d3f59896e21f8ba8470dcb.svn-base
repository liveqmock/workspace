package com.yazuo.erp.system.dao;

import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysKnowledgeKindDao {
    int saveKnowledgeKind(SysKnowledgeKindVO knowledgeKindVO);
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
    int updateKnowledgeKindById(SysKnowledgeKindVO knowledgeKind);

    int deleteKnowledgeKind(Integer id);

    /**
     * 得到所有的分类
     *
     * @return
     */
    List<SysKnowledgeKindVO> getAllKnowledgeKinds();

    /**
     * 按ID列表得到所有的知识分类
     *
     * @param knowledgeKindIds
     * @return
     */
    List<SysKnowledgeKindVO> getAllKnowledgeKindByIds(@Param("knowledgeKindIds") List<Integer> knowledgeKindIds);

    /**
     * 模糊查询知识记录
     *
     * @param knowledgeKindVO
     * @return
     */
    List<SysKnowledgeKindVO> searchKnowledgeKinds(SysKnowledgeKindVO knowledgeKindVO);

    List<SysKnowledgeKindVO> getKnowledgeKinds(@Param("ids")List<Integer> ids);

    /**
     * 得到子分类
     *
     * @return
     * @param parentId
     */
    List<SysKnowledgeKindVO> getChildrenKnowledgeKinds(Integer parentId);

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
}
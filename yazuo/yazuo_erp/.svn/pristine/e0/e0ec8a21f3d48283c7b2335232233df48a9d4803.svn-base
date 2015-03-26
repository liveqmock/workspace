package com.yazuo.erp.system.dao;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysCommentaryVO;
import org.springframework.stereotype.Repository;

@Repository
public interface SysCommentaryDao {
    /**
     * 保存评论信息
     * @param commentaryVO
     * @return
     */
    int saveCommentary(SysCommentaryVO commentaryVO);

    /**
     * 得到评论信息
     * @param knowledgeId
     * @return
     */
    Page<SysCommentaryVO> getCommentaries(Integer knowledgeId);
}
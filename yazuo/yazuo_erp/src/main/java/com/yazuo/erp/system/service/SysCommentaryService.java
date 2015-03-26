package com.yazuo.erp.system.service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.controller.dto.SysCommentaryDTO;
import com.yazuo.erp.system.vo.SysCommentaryVO;

public interface SysCommentaryService {
    /**
     * 保存评论信息
     * @param commentaryVO
     * @return
     */
    SysCommentaryVO saveCommentary(SysCommentaryVO commentaryVO);

    /**
     * 保存评论内容与分数
     * @param commentaryDTO
     * @return
     */
    SysCommentaryVO saveCommentaryAndScore(SysCommentaryDTO commentaryDTO);

    /**
     * 得到评论信息
     * @param knowledgeId
     * @return
     */
    Page<SysCommentaryVO> getCommentaries(Integer knowledgeId);
}
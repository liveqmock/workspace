package com.yazuo.erp.system.service.impl;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.controller.dto.SysCommentaryDTO;
import com.yazuo.erp.system.dao.SysCommentaryDao;
import com.yazuo.erp.system.dao.SysKnowledgeProveDao;
import com.yazuo.erp.system.service.SysCommentaryService;
import com.yazuo.erp.system.service.SysKnowledgeProveService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysCommentaryVO;
import com.yazuo.erp.system.vo.SysKnowledgeProveVO;
import com.yazuo.erp.system.vo.SysUserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 */
@Service
public class SysCommentaryServiceImpl implements SysCommentaryService {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysCommentaryDao sysCommentaryDao;

    @Resource
    private SysKnowledgeProveDao sysKnowledgeProveDao;

    @Override
    public SysCommentaryVO saveCommentary(SysCommentaryVO commentaryVO) {
        this.sysCommentaryDao.saveCommentary(commentaryVO);
        return commentaryVO;
    }

    @Override
    public SysCommentaryVO saveCommentaryAndScore(SysCommentaryDTO commentaryVO) {
        if (commentaryVO.getScore() != null) {
            SysKnowledgeProveVO knowledgeProveVO = new SysKnowledgeProveVO();
            knowledgeProveVO.setKnowledgeId(commentaryVO.getKnowledgeId());
            knowledgeProveVO.setScore(commentaryVO.getScore());
            knowledgeProveVO.setUserId(commentaryVO.getUserId());
            this.sysKnowledgeProveDao.saveKnowledgeProveVO(knowledgeProveVO);
        }
        commentaryVO.setCreatedTime(new Date());
        this.sysCommentaryDao.saveCommentary(commentaryVO);
        return commentaryVO;
    }

    @Override
    public Page<SysCommentaryVO> getCommentaries(Integer knowledgeId) {

        Page<SysCommentaryVO> commentaryVOs = this.sysCommentaryDao.getCommentaries(knowledgeId);
        this.loadUsername(commentaryVOs);
        return commentaryVOs;
    }

    private void loadUsername(List<SysCommentaryVO> commentaryVOList) {
        if (commentaryVOList.size() > 0) {
            Set<Integer> userIds = new HashSet<Integer>();
            for (SysCommentaryVO commentaryVO : commentaryVOList) {
                userIds.add(commentaryVO.getUserId());
            }
            List<SysUserVO> users = this.sysUserService.getUsersByIds(new ArrayList<Integer>(userIds));

            for (SysCommentaryVO commentaryVO : commentaryVOList) {
                for (SysUserVO userVO : users) {
                    if (userVO.getId().equals(commentaryVO.getUserId())) {
                        commentaryVO.setUsername(userVO.getUserName());
                    }
                }
            }
        }
    }
}

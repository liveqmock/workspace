package com.yazuo.erp.system.service.impl;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.dao.SysKnowledgeProveDao;
import com.yazuo.erp.system.service.SysKnowledgeProveService;
import com.yazuo.erp.system.vo.SysKnowledgeProveVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Service
public class SysKnowledgeProveServiceImpl implements SysKnowledgeProveService {
    @Resource
    private SysKnowledgeProveDao sysKnowledgeProveDao;

    @Override
    public SysKnowledgeProveVO saveKnowledgeProveVO(SysKnowledgeProveVO knowledgeProveVO) {
        this.sysKnowledgeProveDao.saveKnowledgeProveVO(knowledgeProveVO);
        return knowledgeProveVO;
    }

    @Override
    public Page<SysKnowledgeProveVO> getAllKnowledgeProves(SysKnowledgeProveVO knowledgeProveVO) {
        return this.sysKnowledgeProveDao.getAllKnowledgeProves(knowledgeProveVO);
    }

    @Override
    public Map<Boolean, Integer> getStaticForProved(Integer knowledgeId) {
        List<Map<String, Object>> result = this.sysKnowledgeProveDao.getStaticForProved(knowledgeId);
        Map<Boolean, Integer> resultMap = new HashMap<Boolean, Integer>();
        for (Map<String, Object> e : result) {
            resultMap.put((Boolean) e.get("approved"),  ((Long)e.get("num")).intValue());
        }

        if (resultMap.get(true) == null) {
            resultMap.put(true, 0);
        }
        if (resultMap.get(false) == null) {
            resultMap.put(false, 0);
        }
        return resultMap;
    }

    @Override
    public double getStatForScore(Integer knowledgeId) {
        Double score =  this.sysKnowledgeProveDao.getStatForScore(knowledgeId);
        return score == null ? 0.0d : score;
    }

    @Override
    public boolean hasRecordForProved(Integer userId, Integer knowledgeId) {
        List<SysKnowledgeProveVO> knowledgeProveVOs = this.sysKnowledgeProveDao.getRecordForProved(userId, knowledgeId);
        return knowledgeProveVOs.size() > 0;
    }

    @Override
    public boolean hasRecordForScore(Integer userId, Integer knowledgeId) {
        List<SysKnowledgeProveVO> knowledgeProveVOs = this.sysKnowledgeProveDao.getRecordForScore(userId, knowledgeId);
        return knowledgeProveVOs.size() > 0;
    }

    @Override
    public SysKnowledgeProveVO getScore(Integer userId, Integer knowledgeId) {
        List<SysKnowledgeProveVO> knowledgeProveVOs = this.sysKnowledgeProveDao.getRecordForScore(userId, knowledgeId);
        return knowledgeProveVOs.size() > 0 ? knowledgeProveVOs.get(0) : null;
    }
}

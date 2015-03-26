package com.yazuo.erp.system.service.impl;

import com.yazuo.erp.system.dao.SysKnowledgeKindDao;
import com.yazuo.erp.system.service.SysKnowledgeKindService;
import com.yazuo.erp.system.vo.SysKnowledgeKindVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 */
@Service
public class SysKnowledgeKindServiceImpl implements SysKnowledgeKindService {

    private static String SEPARATOR = "/";

    @Resource
    private SysKnowledgeKindDao knowledgeKindDao;

    @Override
    public SysKnowledgeKindVO saveKnowledgeKind(SysKnowledgeKindVO knowledgeKindVO) {
        this.knowledgeKindDao.saveKnowledgeKind(knowledgeKindVO);
        this.syncAncestors(knowledgeKindVO);
        return knowledgeKindVO;
    }

    @Override
    public SysKnowledgeKindVO getKnowledgeKind(Integer id) {
        return this.knowledgeKindDao.getKnowledgeKind(id);
    }

    @Override
    public SysKnowledgeKindVO updateKnowledgeKind(SysKnowledgeKindVO knowledgeKind) {
        this.knowledgeKindDao.updateKnowledgeKindById(knowledgeKind);
        this.syncAncestors(knowledgeKind);
        return knowledgeKind;
    }

    @Override
    public SysKnowledgeKindVO deleteKnowledgeKind(Integer id) {
        SysKnowledgeKindVO knowledgeKindVO = this.knowledgeKindDao.getKnowledgeKind(id);
        this.knowledgeKindDao.deleteKnowledgeKind(id);
        return knowledgeKindVO;

    }

    @Override
    public List<SysKnowledgeKindVO> getAllKnowledgeKinds() {
        return this.knowledgeKindDao.getAllKnowledgeKinds();
    }

    @Override
    public List<SysKnowledgeKindVO> searchKnowledgeKinds(SysKnowledgeKindVO knowledgeKindVO) {
        List<SysKnowledgeKindVO> result = this.knowledgeKindDao.searchKnowledgeKinds(knowledgeKindVO);
        result = this.withParents(result);
        return result;
    }

    @Override
    public List<SysKnowledgeKindVO> getChildrenKnowledgeKinds(Integer parentId) {
        return this.knowledgeKindDao.getChildrenKnowledgeKinds(parentId);
    }

    @Override
    public List<SysKnowledgeKindVO> getAncestors(Integer knowledgeKindId) {
        SysKnowledgeKindVO sysKnowledgeKindVO = this.knowledgeKindDao.getKnowledgeKind(knowledgeKindId);
        String ancestorsStr = sysKnowledgeKindVO.getAncestors();
        String[] idStrs = ancestorsStr.split(SEPARATOR);
        Integer[] ids = new Integer[idStrs.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = Integer.parseInt(idStrs[i], 10);
        }
        return this.knowledgeKindDao.getKnowledgeKinds(Arrays.asList(ids));
    }

    @Override
    public boolean hasChildren(Integer id) {
        return this.knowledgeKindDao.hasChildren(id);
    }

    @Override
    public boolean hasKnowledge(Integer id) {
        return this.knowledgeKindDao.hasKnowledge(id);
    }

    @Override
    public boolean hasNoChildren(Integer id) {
        return !this.hasChildren(id);
    }

    @Override
    public boolean hasNoKnowledge(Integer id) {
        return !this.hasKnowledge(id);
    }


    private void syncAncestors(SysKnowledgeKindVO knowledgeKindVO) {
        if (knowledgeKindVO.getParentId() == null) {
            knowledgeKindVO.setAncestors(String.valueOf(knowledgeKindVO.getId()));
        } else {
            SysKnowledgeKindVO parent = this.knowledgeKindDao.getKnowledgeKind(knowledgeKindVO.getParentId());
            knowledgeKindVO.setAncestors(parent.getAncestors() + SEPARATOR + knowledgeKindVO.getId());
        }
        this.knowledgeKindDao.updateKnowledgeKindById(knowledgeKindVO);
    }

    private List<SysKnowledgeKindVO> withParents(List<SysKnowledgeKindVO> knowledgeKindVOs) {
        if (CollectionUtils.isEmpty(knowledgeKindVOs)) {
            return Collections.emptyList();
        }
        Set<Integer> kindIds = new HashSet<Integer>();
        for (SysKnowledgeKindVO knowledgeKindVO : knowledgeKindVOs) {
            String ancestors = knowledgeKindVO.getAncestors();
            String[] ids = ancestors.split(SEPARATOR);
            for (String id : ids) {
                kindIds.add(Integer.parseInt(id, 10));
            }
        }
        List<SysKnowledgeKindVO> resultKnowledgeKinds = this.knowledgeKindDao.getKnowledgeKinds(new ArrayList<Integer>(kindIds));
        return resultKnowledgeKinds;
    }

}

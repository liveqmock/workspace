/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.syn.service.impl;

import java.util.*;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.syn.vo.*;
import com.yazuo.erp.syn.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.syn.service.SynMembershipCardService;

@Service
public class SynMembershipCardServiceImpl implements SynMembershipCardService {

	@Resource
	private SynMembershipCardDao synMembershipCardDao;

	public int saveSynMembershipCard(SynMembershipCardVO synMembershipCard) {
		return synMembershipCardDao.saveSynMembershipCard(synMembershipCard);
	}

	public int batchInsertSynMembershipCards(Map<String, Object> map) {
		return synMembershipCardDao.batchInsertSynMembershipCards(map);
	}

	public int updateSynMembershipCard(SynMembershipCardVO synMembershipCard) {
		return synMembershipCardDao.updateSynMembershipCard(synMembershipCard);
	}

	public int batchUpdateSynMembershipCardsToDiffVals(Map<String, Object> map) {
		return synMembershipCardDao.batchUpdateSynMembershipCardsToDiffVals(map);
	}

	public int batchUpdateSynMembershipCardsToSameVals(Map<String, Object> map) {
		return synMembershipCardDao.batchUpdateSynMembershipCardsToSameVals(map);
	}

	public int deleteSynMembershipCardById(Integer id) {
		return synMembershipCardDao.deleteSynMembershipCardById(id);
	}

	public int batchDeleteSynMembershipCardByIds(List<Integer> ids) {
		return synMembershipCardDao.batchDeleteSynMembershipCardByIds(ids);
	}

	public SynMembershipCardVO getSynMembershipCardById(Integer id) {
		return synMembershipCardDao.getSynMembershipCardById(id);
	}

	public List<SynMembershipCardVO> getSynMembershipCards(SynMembershipCardVO synMembershipCard) {
		return synMembershipCardDao.getSynMembershipCards(synMembershipCard);
	}

	public List<Map<String, Object>> getSynMembershipCardsMap(SynMembershipCardVO synMembershipCard) {
		return synMembershipCardDao.getSynMembershipCardsMap(synMembershipCard);
	}

	@Override
	public List<Map<String, Object>> getSynMembershipCardInfo(Integer brandId, int pageNumber, int pageSize) {
		Page<Map<String, Object>> list = null;
		PageHelper.startPage(pageNumber, pageSize, true);
		list = (Page<Map<String, Object>>) synMembershipCardDao.getSynMembershipCardInfo(brandId);
		return list;
	}

	@Override
	public long getSynMembershipCardCount(Integer brandId) {
		return synMembershipCardDao.getSynMembershipCardCount(brandId);
	}
}

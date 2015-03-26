package com.yazuo.erp.syn.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.syn.dao.MerchantCardInfoCrmDao;
import com.yazuo.erp.syn.dao.SynMembershipCardDao;
import com.yazuo.erp.syn.service.SynMembershipCardService;
import com.yazuo.erp.syn.vo.SynMembershipCardVO;

@Service
public class SynMembershipCardServiceImpl implements SynMembershipCardService {

	@Resource
	private MerchantCardInfoCrmDao merchantCardInfoCrmDao;
	@Resource
	private SynMembershipCardDao synMerchantCardInfoDao;

	Log log = LogFactory.getLog(this.getClass());

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchUpdateSynMembershipCard() {

		// 1、查询CRM商户卡信息
		List<Map<String, Object>> crmlist = merchantCardInfoCrmDao.getMerchantCardInfo();
		Map<String, Object> crmMap = new HashMap<String, Object>();
		for (Map<String, Object> map : crmlist) {
			crmMap.put(map.get("merchant_id").toString() + "|" + map.get("ID"), map);
		}

		// 2、查询ERP商户会员卡信息表
		List<Map<String, Object>> synMembershipCardList = synMerchantCardInfoDao.getSynMembershipCard();
		Map<String, Object> synMembershipCardMap = new HashMap<String, Object>();
		for (Map<String, Object> map : synMembershipCardList) {
			synMembershipCardMap.put(map.get("merchant_id").toString() + "|" + map.get("cardtype_id"), map);
		}

		// 3、比较两集合
		Set<String> crmKeySet = crmMap.keySet();
		Set<String> erpKeySet = synMembershipCardMap.keySet();

		Collection disjunction = CollectionUtils.disjunction(crmKeySet, erpKeySet);
		Collection addList = CollectionUtils.intersection(disjunction, crmKeySet);
		Collection deleteList = CollectionUtils.intersection(disjunction, erpKeySet);
		Collection updateList = CollectionUtils.intersection(crmKeySet, erpKeySet);

		// 4、需要新增的记录
		List<SynMembershipCardVO> addSynMembershipCardList = new ArrayList<SynMembershipCardVO>();
		if (!CollectionUtils.isEmpty(addList)) {
			log.info("新增记录数：" + addList.size());
			SynMembershipCardVO synMerchantCardInfoVO;
			for (Object object : addList) {
				Map<String, Object> mapAdd = (Map<String, Object>) crmMap.get(object);
				synMerchantCardInfoVO = new SynMembershipCardVO();
				synMerchantCardInfoVO.setMerchantId((Integer) mapAdd.get("merchant_id"));
				synMerchantCardInfoVO.setMerchantName((String) mapAdd.get("merchant_name"));
				synMerchantCardInfoVO.setBrandId((null != mapAdd.get("brand_id")) ? (Integer) mapAdd.get("brand_id") : null);
				synMerchantCardInfoVO.setMerchantType((String) mapAdd.get("merchant_type"));
				synMerchantCardInfoVO.setCardtypeId((null != mapAdd.get("ID")) ? (Integer) mapAdd.get("ID") : null);
				synMerchantCardInfoVO.setCardCount((null != mapAdd.get("card_count")) ? new BigDecimal((Long) mapAdd
						.get("card_count")) : null);
				synMerchantCardInfoVO.setCardPrice((null != mapAdd.get("price")) ? (BigDecimal) mapAdd.get("price") : null);
				synMerchantCardInfoVO.setCardType((String) mapAdd.get("membershipGrade"));
				synMerchantCardInfoVO.setCardName((String) mapAdd.get("cardtype"));
				Integer cardBatch = null;
				if (null != mapAdd.get("cardBatch")) {
					cardBatch = (Integer) mapAdd.get("cardBatch");
				}

				boolean is_solid = false;
				if (null != cardBatch) {
					is_solid = (cardBatch.intValue() == 3) ? true : false;
					synMerchantCardInfoVO.setIsSoldCard(String.valueOf(is_solid));
				}

				synMerchantCardInfoVO.setUpdateTime(new Date());
				addSynMembershipCardList.add(synMerchantCardInfoVO);
			}
		}

		// 5、需要修改的记录
		List<SynMembershipCardVO> updateSynMembershipCardList = new ArrayList<SynMembershipCardVO>();
		if (!CollectionUtils.isEmpty(updateList)) {
			SynMembershipCardVO synMerchantCardInfoVO1;
			for (Object object : updateList) {
				if (object.toString().equals("1119|null")) {
					System.out.println(crmMap.get(object));
				}
				Map<String, Object> mapUpdate = (Map<String, Object>) crmMap.get(object);

				synMerchantCardInfoVO1 = new SynMembershipCardVO();
				synMerchantCardInfoVO1.setMerchantId((Integer) mapUpdate.get("merchant_id"));
				synMerchantCardInfoVO1.setMerchantName((String) mapUpdate.get("merchant_name"));
				synMerchantCardInfoVO1.setBrandId((null != mapUpdate.get("brand_id")) ? (Integer) mapUpdate.get("brand_id")
						: null);
				synMerchantCardInfoVO1.setMerchantType((String) mapUpdate.get("merchant_type"));
				synMerchantCardInfoVO1.setCardtypeId((null != mapUpdate.get("ID")) ? (Integer) mapUpdate.get("ID") : null);
				synMerchantCardInfoVO1.setCardCount((null != mapUpdate.get("card_count")) ? new BigDecimal((Long) mapUpdate
						.get("card_count")) : null);
				synMerchantCardInfoVO1
						.setCardPrice((null != mapUpdate.get("price")) ? (BigDecimal) mapUpdate.get("price") : null);
				synMerchantCardInfoVO1.setCardType((String) mapUpdate.get("membershipGrade"));
				synMerchantCardInfoVO1.setCardName((String) mapUpdate.get("cardtype"));
				Integer cardBatch = null;
				if (null != mapUpdate.get("cardBatch")) {
					cardBatch = (Integer) mapUpdate.get("cardBatch");
				}

				boolean is_solid = false;
				if (null != cardBatch) {
					is_solid = (cardBatch.intValue() == 3) ? true : false;
					synMerchantCardInfoVO1.setIsSoldCard(String.valueOf(is_solid));
				}

				synMerchantCardInfoVO1.setUpdateTime(new Date());
				updateSynMembershipCardList.add(synMerchantCardInfoVO1);

			}
		}

		// 6、需要删除的记录
		if (!CollectionUtils.isEmpty(deleteList)) {
			log.info("删除记录数：" + deleteList.size());
			for (Object object : deleteList) {
				Map<String, Object> mapDelete = (Map<String, Object>) synMembershipCardMap.get(object);
				Integer merchant_id = (Integer) mapDelete.get("merchant_id");
				Object cardtype_id = mapDelete.get("cardtype_id");
				synMerchantCardInfoDao.deleteSynMembershipCard(merchant_id, cardtype_id);
			}
		}

		if (null != addSynMembershipCardList) {
			// 批量增加
			batchInsertSynMembershipCard(addSynMembershipCardList);
		}

		if (null != updateSynMembershipCardList) {
			// 批量修改
			batchUpdateSynMembershipCard(updateSynMembershipCardList);
		}

	}

	private void batchUpdateSynMembershipCard(List<SynMembershipCardVO> updateSynMembershipCardList) {

		if (!CollectionUtils.isEmpty(updateSynMembershipCardList)) {
			int size = updateSynMembershipCardList.size();
			if (size > 1000) {
				List<SynMembershipCardVO> synMembershipCardsplit = new ArrayList<SynMembershipCardVO>();
				for (int i = 0; i < size; i++) {
					synMembershipCardsplit.add(updateSynMembershipCardList.get(i));
					if (synMembershipCardsplit.size() == 1000) {
						synMerchantCardInfoDao.batchUpdateSynMembershipCard(synMembershipCardsplit);
						synMembershipCardsplit.clear();
					}
				}
				synMerchantCardInfoDao.batchUpdateSynMembershipCard(synMembershipCardsplit);
			} else {
				synMerchantCardInfoDao.batchUpdateSynMembershipCard(updateSynMembershipCardList);
			}
		}
	}

	private void batchInsertSynMembershipCard(List<SynMembershipCardVO> addSynMembershipCardList) {

		if (!CollectionUtils.isEmpty(addSynMembershipCardList)) {
			int size = addSynMembershipCardList.size();
			if (size > 1000) {
				List<SynMembershipCardVO> synMembershipCardsplit = new ArrayList<SynMembershipCardVO>();
				for (int i = 0; i < size; i++) {
					synMembershipCardsplit.add(addSynMembershipCardList.get(i));
					if (synMembershipCardsplit.size() == 1000) {
						synMerchantCardInfoDao.batchInsertSynMembershipCard(synMembershipCardsplit);
						synMembershipCardsplit.clear();
					}
				}
				synMerchantCardInfoDao.batchInsertSynMembershipCard(synMembershipCardsplit);
			} else {
				synMerchantCardInfoDao.batchInsertSynMembershipCard(addSynMembershipCardList);
			}
		}

	}
}

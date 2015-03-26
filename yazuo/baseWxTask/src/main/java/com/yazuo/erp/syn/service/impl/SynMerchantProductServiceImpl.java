package com.yazuo.erp.syn.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.syn.dao.MerchantProductDao;
import com.yazuo.erp.syn.dao.SynMerchantProductDao;
import com.yazuo.erp.syn.service.SynMerchantProductService;
import com.yazuo.erp.syn.vo.SynMerchantProductVO;

@Service
public class SynMerchantProductServiceImpl implements SynMerchantProductService {

	@Resource
	private MerchantProductDao merchantProductDao;

	@Resource
	private SynMerchantProductDao synMerchantProductDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchUpdateSynMerchantProduct() {

		// 1、查询CRM商户开通的产品
		List<Map<String, Object>> crmList = merchantProductDao.getProductsAllMerchant();
		Map<String, Object> crmMap = new HashMap<String, Object>();
		for (Map<String, Object> map : crmList) {
			crmMap.put(map.get("merchant_id").toString() + "|" + map.get("product_id").toString(), map);
		}

		// 2、查询ERP商户产品表
		List<Map<String, Object>> erpList = synMerchantProductDao.getSynMerchantProduct();
		Map<String, Object> erpMap = new HashMap<String, Object>();
		for (Map<String, Object> map : erpList) {
			erpMap.put(map.get("merchant_id").toString() + "|" + map.get("product_id").toString(), map);
		}

		Set<String> crmKeySet = crmMap.keySet();
		Set<String> erpKeySet = erpMap.keySet();
		Collection disjunction = CollectionUtils.disjunction(crmKeySet, erpKeySet);
		Collection addList = CollectionUtils.intersection(disjunction, crmKeySet);
		Collection deleteList = CollectionUtils.intersection(disjunction, erpKeySet);
		Collection updateList = CollectionUtils.intersection(erpKeySet, crmKeySet);

		// 添加
		List<SynMerchantProductVO> addSynMerchantProductList = new ArrayList<SynMerchantProductVO>();
		SynMerchantProductVO addSynMerchantProductVO;
		if (null != addList) {
			for (Object object : addList) {
				addSynMerchantProductVO = new SynMerchantProductVO();
				Map<String, Object> mapAdd = (Map<String, Object>) crmMap.get(object);
				addSynMerchantProductVO.setMerchantId((Integer) mapAdd.get("merchant_id"));
				addSynMerchantProductVO.setProductId((Integer) mapAdd.get("product_id"));
				addSynMerchantProductVO.setProductName((String) mapAdd.get("product_name"));
				addSynMerchantProductVO.setUpdateTime(new Date());
				addSynMerchantProductList.add(addSynMerchantProductVO);
			}
		}

		// 修改
		List<SynMerchantProductVO> updateSynMerchantProductList = new ArrayList<SynMerchantProductVO>();
		SynMerchantProductVO updateSynMerchantProductVO;
		if (null != updateList) {
			for (Object object : updateList) {
				updateSynMerchantProductVO = new SynMerchantProductVO();
				Map<String, Object> mapUpdate = (Map<String, Object>) crmMap.get(object);
				updateSynMerchantProductVO.setMerchantId((Integer) mapUpdate.get("merchant_id"));
				updateSynMerchantProductVO.setProductId((Integer) mapUpdate.get("product_id"));
				updateSynMerchantProductVO.setProductName((String) mapUpdate.get("product_name"));
				updateSynMerchantProductVO.setUpdateTime(new Date());
				updateSynMerchantProductList.add(updateSynMerchantProductVO);
			}
		}

		// 删除
		if (null != deleteList) {
			for (Object object : deleteList) {
				Map<String, Object> mapDetele = (Map<String, Object>) erpMap.get(object);
				Integer merchant_id = (Integer) mapDetele.get("merchant_id");
				Integer product_id = (Integer) mapDetele.get("product_id");
				// 删除记录
				synMerchantProductDao.deleteSynMerchantProduct(merchant_id, product_id);
			}
		}

		// 批量增加
		if (null != addSynMerchantProductList) {
			batchInsertSynMerchantProduct(addSynMerchantProductList);
		}

		// 批量修改
		if (null != updateSynMerchantProductList) {
			batchUpdateSynMerchantProduct(updateSynMerchantProductList);
		}
	}

	private void batchUpdateSynMerchantProduct(List<SynMerchantProductVO> updateSynMerchantProductList) {

		if (!CollectionUtils.isEmpty(updateSynMerchantProductList)) {
			int size = updateSynMerchantProductList.size();
			if (size > 6000) {
				List<SynMerchantProductVO> synMerchantProductsplit = new ArrayList<SynMerchantProductVO>();
				for (int i = 0; i < size; i++) {
					synMerchantProductsplit.add(updateSynMerchantProductList.get(i));
					if (synMerchantProductsplit.size() == 6000) {
						synMerchantProductDao.batchUpdateSynMerchantProduct(synMerchantProductsplit);
						synMerchantProductsplit.clear();
					}
				}
				synMerchantProductDao.batchUpdateSynMerchantProduct(synMerchantProductsplit);
			} else {
				synMerchantProductDao.batchUpdateSynMerchantProduct(updateSynMerchantProductList);
			}
		}
	}

	private void batchInsertSynMerchantProduct(List<SynMerchantProductVO> addSynMerchantProductList) {

		if (!CollectionUtils.isEmpty(addSynMerchantProductList)) {
			int size = addSynMerchantProductList.size();
			if (size > 6000) {
				List<SynMerchantProductVO> synMerchantProductsplit = new ArrayList<SynMerchantProductVO>();
				for (int i = 0; i < size; i++) {
					synMerchantProductsplit.add(addSynMerchantProductList.get(i));
					if (synMerchantProductsplit.size() == 6000) {
						synMerchantProductDao.batchInsertSynMerchantProduct(synMerchantProductsplit);
						synMerchantProductsplit.clear();
					}
				}
				synMerchantProductDao.batchInsertSynMerchantProduct(synMerchantProductsplit);
			} else {
				synMerchantProductDao.batchInsertSynMerchantProduct(addSynMerchantProductList);
			}
		}

	}
}

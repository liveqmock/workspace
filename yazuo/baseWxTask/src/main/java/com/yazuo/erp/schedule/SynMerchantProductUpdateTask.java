package com.yazuo.erp.schedule;

import java.util.Map;

import javax.annotation.Resource;

import com.yazuo.erp.syn.service.SynMerchantProductService;
import com.yazuo.task.BaseTask;

public class SynMerchantProductUpdateTask extends BaseTask {
	@Resource
	private SynMerchantProductService synMerchantProductService;

	@Override
	public void execute1(Map params) {
		synMerchantProductService.batchUpdateSynMerchantProduct();
	}

}

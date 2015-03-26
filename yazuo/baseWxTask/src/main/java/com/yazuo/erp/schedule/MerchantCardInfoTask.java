package com.yazuo.erp.schedule;

import java.util.Map;

import javax.annotation.Resource;

import com.yazuo.erp.syn.service.SynMembershipCardService;
import com.yazuo.task.BaseTask;

public class MerchantCardInfoTask extends BaseTask {

	@Resource
	private SynMembershipCardService synMembershipCardService;

	@Override
	public void execute1(Map params) {
		synMembershipCardService.batchUpdateSynMembershipCard();
	}

}

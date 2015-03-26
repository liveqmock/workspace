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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.sys.dao.SysRemindDao;
import com.yazuo.erp.sys.vo.SysRemindVO;
import com.yazuo.utils.DateUtil;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-18 下午6:30:58
 */
@Service
public class SynMerchantServiceImpl implements SynMerchantService {

	@Resource
	private SynMerchantDao synMerchantDao;

	@Resource
	private SysRemindDao sysRemindDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void querySynMerchantList() throws Exception {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formerday = DateUtil.formerday(format.format(today), 30);

		List<SynMerchantVO> merchantList = synMerchantDao.querySynMerchantList("1", formerday);
		List<SysRemindVO> sysRemindVoList = new ArrayList<SysRemindVO>();
		SysRemindVO sysRemindVO;
		if (!CollectionUtils.isEmpty(merchantList)) {
			for (SynMerchantVO synMerchantVO : merchantList) {
				sysRemindVO = new SysRemindVO();
				sysRemindVO.setMerchantId(synMerchantVO.getMerchantId());
				sysRemindVO.setUserId(synMerchantVO.getUserId());
				sysRemindVO.setPriorityLevelType("01");
				sysRemindVO.setItemType("05");
				sysRemindVO.setItemContent("该商户合同于"
						+ new SimpleDateFormat("yyyy年MM月dd日").format(synMerchantVO.getServiceEndTime()) + "到期，请联系续费");
				sysRemindVO.setItemStatus("1");
				sysRemindVO.setProcessId(null);
				sysRemindVO.setIsEnable("1");
				sysRemindVO.setInsertBy(Constant.DEFAULTUSERID);
				sysRemindVO.setInsertTime(new Date());
				sysRemindVO.setUpdateBy(Constant.DEFAULTUSERID);
				sysRemindVO.setUpdateTime(new Date());
				sysRemindVoList.add(sysRemindVO);
			}
		}

		if (!CollectionUtils.isEmpty(sysRemindVoList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindVoList);
		}
	}

}

package com.yazuo.weixin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.weixin.dao.WeixinLogDao;
import com.yazuo.weixin.service.WeixinLogAnalysisService;

/**
* @ClassName WeixinLogAnalysisImpl
* @Description 日志分析
* @author sundongfeng@yazuo.com
* @date 2014-7-2 下午3:16:58
* @version 1.0
*/
@Service
@Scope("prototype")
public class WeixinLogAnalysisImpl implements WeixinLogAnalysisService {
	private static final Log log = LogFactory.getLog( WeixinLogAnalysisImpl.class);
	@Autowired
	private WeixinLogDao weixinLogDao;
	
	public int insertOrUpdateLog(Map<String, ?> map) throws Exception {
		return weixinLogDao.insertOrUpdateLog(map);
	}
	public List<Map<String, Object>> queryLog(String beginDate, String endDate)
			throws Exception {
		return weixinLogDao.queryLog(beginDate, endDate);
	}
	@Transactional(rollbackFor=Exception.class)
	public synchronized int insertOrUpdateMallLog(Map<String, ?> map) throws Exception {
		return weixinLogDao.insertOrUpdateMallLog(map);
	}
	
	public List<Map<String, Object>> queryMallLog(String beginDate,
			String endDate, String brandId) throws Exception {
		return weixinLogDao.queryMallLog(beginDate, endDate, brandId);
	}
	public List<Map<String, Object>> queryMallBrand() {
		return weixinLogDao.queryMallBrand();
	}

}

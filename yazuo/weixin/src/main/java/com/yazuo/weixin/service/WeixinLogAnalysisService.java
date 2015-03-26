package com.yazuo.weixin.service;

import java.util.List;
import java.util.Map;

public interface WeixinLogAnalysisService {
	public int insertOrUpdateLog(Map<String,?> map) throws Exception;
	public List<Map<String,Object>> queryLog(String beginDate,String endDate)throws Exception;
	public int insertOrUpdateMallLog(Map<String,?> map) throws Exception;
	public List<Map<String,Object>> queryMallLog(String beginDate,String endDate,String brandId)throws Exception;
	public List<Map<String,Object>> queryMallBrand();
}

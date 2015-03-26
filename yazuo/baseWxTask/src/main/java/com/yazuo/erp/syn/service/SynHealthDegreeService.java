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
package com.yazuo.erp.syn.service;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-12 下午1:36:58
 */
public interface SynHealthDegreeService {

	/** 首次同步本月之前商户健康度 */
	boolean batchInsertSynHealthDegreeByIndexId(int indexId) throws Exception;

	/** 首次同步当月商户健康度 */
	boolean batchInsertCurrentMonthSynHealthDegreeByIndexId(int indexId) throws Exception;

	/** 更新本月之前商户健康度 */
	boolean batchUpdateSynHealthDegreeByIndexId(int indexId) throws Exception;

	/** 更新当月商户健康度 */
	boolean batchUpdateCurrentMonthSynHealthDegreeByIndexId(int indexId) throws Exception;

}

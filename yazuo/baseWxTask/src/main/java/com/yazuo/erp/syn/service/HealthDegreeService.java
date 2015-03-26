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

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-12 上午11:23:12
 */
public interface HealthDegreeService {

	List<Map<String, Object>> getTargetValueByIndexId(Integer indexId, String month) throws Exception;
}

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
package com.yazuo.external.active.service;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-19 下午5:28:09
 */
public interface ActiveCrmService {
	List<Map<String, Object>> getActiveExecutiving(Integer brandId);
}

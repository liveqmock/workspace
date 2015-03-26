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
package com.yazuo.external.active.dao;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-19 下午5:25:57
 */
public interface ActiveCrmDao {
	List<Map<String, Object>> getActiveExecutiving(Integer brandId);

}

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

package com.yazuo.erp.minierp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description TODO
 * @author erp team
 * @date
 */

@Repository
public interface PlansDao {

	List<Map<String, Object>> getPlansList(@Param(value = "limit") int limit, @Param(value = "offset") int offset);

	int getPlansCount();

}

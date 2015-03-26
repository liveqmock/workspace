package com.yazuo.erp.trade.dao;

import com.yazuo.erp.trade.vo.TradeSmsTmpFieldVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface TradeSmsTmpFieldDao {
	/**
	 * 得到模板字段列表
	 * 
	 * @param transCodes
	 * @return
	 */
	List<TradeSmsTmpFieldVO> getSmsTmpFields(List<String> transCodes);

	/**
	 * 获得所有模板字段列表
	 * 
	 * @param
	 * @return
	 */
	List<TradeSmsTmpFieldVO> getTradeSmsTmpFeilds();
}

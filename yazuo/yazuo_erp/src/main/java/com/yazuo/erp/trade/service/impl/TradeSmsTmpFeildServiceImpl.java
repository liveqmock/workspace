package com.yazuo.erp.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.trade.dao.TradeSmsTmpFieldDao;
import com.yazuo.erp.trade.service.TradeSmsTmpFeildService;
import com.yazuo.erp.trade.vo.TradeSmsTmpFieldVO;

/**
 * @Description 会员卡
 * @author erp team
 * @date
 */

@Service
public class TradeSmsTmpFeildServiceImpl implements TradeSmsTmpFeildService {

	@Resource
	private TradeSmsTmpFieldDao tradeSmsTmpFieldDao;

	public List<TradeSmsTmpFieldVO> listTradeSmsTmpFeilds() {
		return tradeSmsTmpFieldDao.getTradeSmsTmpFeilds();
	}

}

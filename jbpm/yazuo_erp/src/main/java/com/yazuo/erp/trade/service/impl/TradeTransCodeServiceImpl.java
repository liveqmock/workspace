package com.yazuo.erp.trade.service.impl;

import com.yazuo.erp.trade.dao.TradeTransCodeDao;
import com.yazuo.erp.trade.service.TradeTransCodeService;
import com.yazuo.erp.trade.vo.TradeTransCodeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class TradeTransCodeServiceImpl  implements TradeTransCodeService{
    @Resource
    private TradeTransCodeDao tradeTransCodeDao;
    @Override
    public List<TradeTransCodeVO> getAllTransCodes() {
        return this.tradeTransCodeDao.getAllTransCodes();
    }
}

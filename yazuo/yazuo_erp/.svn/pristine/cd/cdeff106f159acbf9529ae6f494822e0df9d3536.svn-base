package com.yazuo.erp.trade.service;

import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.vo.TradeCardtypeVO;

import java.util.List;

/**
 * @Description 会员卡服务类
 * @author erp team
 * @date 
 */
public interface TradeCardtypeService{

    /**
     * 得到会员卡及奖励规则
     * @param merchantId
     * @return
     */
    List<TradeCardtypeVO> getTradeCardtypesAndAwardRules(Integer merchantId);

    /**
     * 保存会员卡及奖励规则
     * @param tradeCardtypeVOs
     * @return
     */
    List<TradeCardtypeVO> saveCardtypesAndAwardRules(List<TradeCardtypeVO> tradeCardtypeVOs);

    int synchronize(List<TradeCardtypeVO> cardtypeVOs);

    /**
     * 1.保存卡类型和奖励规则
     * 2.生成流水
     * 3.查询上流流程步骤状态等信息，封装结果并返回
     */
	SysOperationLogVO saveCardAndOperatoinLogs(List<TradeCardtypeVO> tradeCardtypeVOs, SysUserVO sessionUser);
}

package com.yazuo.erp.trade.dao;

import com.yazuo.erp.trade.vo.TradeAwardRuleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description 奖励规则类
 * @author erp team
 * @date
 */
@Repository
public interface TradeAwardRuleDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveTradeAwardRule (TradeAwardRuleVO tradeAwardRule);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateTradeAwardRule (TradeAwardRuleVO tradeAwardRule);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateTradeAwardRulesToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateTradeAwardRulesToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteTradeAwardRuleById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteTradeAwardRuleByIds (List<Integer> ids);
    /**
     * 按cardtypID删除多个对象
     */
    int deleteAwardRuleByCardtypeIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	TradeAwardRuleVO getTradeAwardRuleById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<TradeAwardRuleVO> getTradeAwardRules (TradeAwardRuleVO tradeAwardRule);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getTradeAwardRulesMap (TradeAwardRuleVO tradeAwardRule);

    /**
     * 批量插入奖励规则
     * @param tradeAwardRuleVOs
     * @return
     */
    int batchInsertTradeAwardRules (List<TradeAwardRuleVO> tradeAwardRuleVOs);

    /**
     * 批量更新奖励规则
     * @param tradeAwardRuleVOs
     * @return
     */
    int batchUpdateTradeAwardRules (@Param("awardRuleVOs")List<TradeAwardRuleVO> tradeAwardRuleVOs);

    List<TradeAwardRuleVO> getAwardRulesByCardtypeIds(@Param("cardtypeIds") List<Integer> cardtypeIds);

}

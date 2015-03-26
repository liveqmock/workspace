package com.yazuo.erp.trade.dao;

import com.yazuo.erp.trade.vo.TradeCardtypeVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description 会员卡DAO类
 * @author erp team
 * @date
 */
@Repository
public interface TradeCardtypeDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveTradeCardtype (TradeCardtypeVO tradeCardtype);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateTradeCardtype (TradeCardtypeVO tradeCardtype);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateTradeCardtypesToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateTradeCardtypesToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteTradeCardtypeById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteTradeCardtypeByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	TradeCardtypeVO getTradeCardtypeById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getTradeCardtypesMap (TradeCardtypeVO tradeCardtype);

    /**
     * 批量更新
     * @param tradeCardtypeVOs
     */
    void batchInsertTradeCardtypes(List<TradeCardtypeVO> tradeCardtypeVOs);

    /**
     * 插入
     * @param tradeCardtypeVO
     */
    void insertTradeCardtypeVO(TradeCardtypeVO tradeCardtypeVO);

    /**
     * 批量更新
     * @param tradeCardtypeVOs
     */
    void batchUpdateTradeCardtypes(List<TradeCardtypeVO> tradeCardtypeVOs);
    /**
     * 查询会员卡信息 带有奖励信息
     * @param merchantId
     * @return
     */
    List<TradeCardtypeVO> getTradeCardtypesAndAwardRules(Integer merchantId);

    /**
     * 查询会员卡信息
     * @param merchantId
     * @return
     */
    List<TradeCardtypeVO> getTradeCardtypes(Integer merchantId);

    int updateIsSynchronizeForCardtypes(List<TradeCardtypeVO> cardtypeVOs);

}

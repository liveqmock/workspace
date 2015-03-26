package com.yazuo.erp.trade.dao;

import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description 短信模板DAO类
 * @author erp team
 * @date
 */
@Repository
public interface TradeMessageTemplateDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveTradeMessageTemplate (TradeMessageTemplateVO tradeMessageTemplate);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertTradeMessageTemplates (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateTradeMessageTemplate (TradeMessageTemplateVO tradeMessageTemplate);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateTradeMessageTemplatesToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateTradeMessageTemplatesToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteTradeMessageTemplateById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteTradeMessageTemplateByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	TradeMessageTemplateVO getTradeMessageTemplateById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<TradeMessageTemplateVO> getTradeMessageTemplates (TradeMessageTemplateVO tradeMessageTemplate);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getTradeMessageTemplatesMap (TradeMessageTemplateVO tradeMessageTemplate);

    /**
     * 插入模板
     * @param messageTemplateVOs
     */
    void insertTradeMessageTemplateVOs(List<TradeMessageTemplateVO> messageTemplateVOs);

    /**
     * 添加
     * @param messageTemplateVOs
     */
    void insertTradeMessageTemplateVO(TradeMessageTemplateVO messageTemplateVO);

    /**
     * 更新模板
     * @param messageTemplateVOs
     */
    void updateTradeMessageTemplateVOs(List<TradeMessageTemplateVO> messageTemplateVOs);

    /**
     * 查询模板
     * @param merchantId
     */
    List<TradeMessageTemplateVO> listTradeMessageTemplates(String merchantNo);

    /**
     * 更新同步状态
     * @param messageTemplateVOs
     * @return
     */
    int updateIsSynchronize(List<TradeMessageTemplateVO> messageTemplateVOs);
}

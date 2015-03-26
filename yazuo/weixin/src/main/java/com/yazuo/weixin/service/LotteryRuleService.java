/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-24
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: LotteryRuleService
 * @Description: 处理抽奖规则逻辑接口
 */
public interface LotteryRuleService {

	/**
	 * 
	 * <p>
	 * Description:根据厂家id和类型查询合法的抽奖规则
	 * </p>
	 * 
	 */
	public Map<String, Object> getLotteryRule(int brandId, int type)
			throws Exception;

	/**取办卡抽奖的活动规则*/
	public Map<String, Object> getCardLotterRule(Integer brandId, Integer activtiyType);
	
	/**根据活动规则id将是否已下发短信状态置为1（已下发短信）*/
	public int updateLotterRuleSmsStatus(Integer id);
	
	/**根据活动规则取预警信息*/
	public String getMonitorMsgByRuleId (Integer ruleId);
	/**查询某个商户下同时在进行的活动*/
	public List<Map<String, Object>> getLotteryRuleOfBrand(Integer brandId, int activeType, String weixinId, String path);
}

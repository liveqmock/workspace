/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-27
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: LotteryItemCouponService
 * @Description: 处理奖项跟券类型逻辑接口
 */
public interface LotteryItemCouponService {

	/**
	 * 
	 * <p>
	 * Description:根据奖项id查询对应批券次号
	 * </p>
	 * 
	 */
	public List<Map<String, Object>> getLotteryItemCoupon(int lottery_item_id);

}

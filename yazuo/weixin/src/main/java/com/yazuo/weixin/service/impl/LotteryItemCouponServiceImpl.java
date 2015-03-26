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
package com.yazuo.weixin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.LotteryItemCouponDao;
import com.yazuo.weixin.service.LotteryItemCouponService;

/**
 * @InterfaceName: LotteryItemCouponServiceImpl
 * @Description: 处理奖项跟券类型逻辑的实现
 */
@Service("lotteryItemCouponService")
public class LotteryItemCouponServiceImpl implements LotteryItemCouponService {

	@Resource
	private LotteryItemCouponDao lotteryItemCouponDao;

	public List<Map<String, Object>> getLotteryItemCoupon(int lottery_item_id) {
		return lotteryItemCouponDao.getLotteryItemCoupon(lottery_item_id);
	}

}

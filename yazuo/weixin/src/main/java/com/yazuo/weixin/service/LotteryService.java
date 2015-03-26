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

import java.util.Map;

import com.yazuo.weixin.vo.MemberAwardVO;

/**
 * @ClassName: LotteryService
 * @Description: 处理抽奖逻辑接口
 */
public interface LotteryService {

	/**
	 * 
	 * <p>
	 * Description:幸运抽奖 只有会员才能抽奖（在微信的clink事件去判断，本方法不做判断）
	 * </p>
	 * 
	 */
	public Map<String, Object> luckyDraw(MemberAwardVO member) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:是否可以抽奖
	 * </p>
	 * 
	 */
	public Map<String, Object> whetherLuckyDraw(MemberAwardVO member)
			throws Exception;

}

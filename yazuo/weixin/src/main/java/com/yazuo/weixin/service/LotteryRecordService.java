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

import com.yazuo.weixin.vo.AwardVO;
import com.yazuo.weixin.vo.MemberAwardVO;

/**
 * @ClassName: LotteryRecordService
 * @Description: 处理中奖记录逻辑接口
 */
public interface LotteryRecordService {

	/**
	 * 
	 * <p>
	 * Description:根据起始时间(如果查询所有起始时间为null，表示的是一共可以抽几次)，手机号,规则id和商户id查询抽了几次奖
	 * </p>
	 * 
	 */
	public int getLotteryRecord(String phoneNo, int brandId,
			String lottery_rule_id, String begintime, String endtime, int lotteryRecordType)
			throws Exception;

	/**
	 * <p>
	 * Description:保存中奖记录
	 * </p>
	 * 
	 * @param isAward
	 *            :是否中奖
	 * 
	 * 
	 * @return
	 */
	public int insertLotteryRecord(MemberAwardVO member, AwardVO award,
			boolean isAward);
	
	/**对应实物奖品保存中奖记录*/
	public int insertLotteryRecord(MemberAwardVO member, AwardVO award);
	
	/**根据brand_id和weixin_id取中奖记录*/
	public List<Map<String, Object>> getLotteryRecordByBrandIdAndWeixinId (String weixinId, Integer brandId);

}

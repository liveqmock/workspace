/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-28
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service;

import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;

/**
 * @ClassName: LotteryMessageVerifyService
 * @Description: 处理微信抽奖消息的逻辑接口
 */
public interface LotteryMessageVerifyService {

	/**
	 * <p>
	 * Description:返回给微信平台的message
	 * </p>
	 * 
	 * @param messageIn
	 * 
	 * 
	 * @return
	 */
	public Message message(Message messageIn, MemberVO member,
			BusinessVO business, String path);

}

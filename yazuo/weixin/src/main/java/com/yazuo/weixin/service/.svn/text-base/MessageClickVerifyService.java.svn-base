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

import java.util.Map;

import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;

/**
 * @ClassName: MessageClickVerifyService
 * @Description: 处理click逻辑接口
 */
public interface MessageClickVerifyService {

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
	public Message message(Message messageIn, MemberVO member, BusinessVO business);
	
	/**
	 * <p>
	 * Description:获得一键上网的内容
	 * </p>
	 * 
	 * @param map
	 * 
	 * 
	 * @return
	 */
	public String getServiceContent(Map<String, Object> map);

}

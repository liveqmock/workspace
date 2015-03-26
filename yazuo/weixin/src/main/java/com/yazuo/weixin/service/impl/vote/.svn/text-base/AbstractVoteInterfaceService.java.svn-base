/**
* Created with MyElipse.<br/>
* User: huijun.zheng<br/>
* Date: 2013-12-25<br/>
* Time: 下午3:17:00<br/>
*
* To change this template use File | Settings | File Templates.<br/>
*/
package com.yazuo.weixin.service.impl.vote;

import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.Message;

/**投票服务类的基类
 * Created with MyElipse.<br/>
 * User: huijun.zheng<br/>
 * Date: 2013-12-25<br/>
 * Time: 下午3:17:00<br/>
 *<br/>
 */
public abstract class AbstractVoteInterfaceService
{
	public static final String CONTENT_2014 = "2013中国好味道活动已结束，谢谢亲的关注！";
	
	protected Message executeotherNew(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent(CONTENT_2014);
		messageOut.setFuncFlag("0");
		return messageOut;
	}
}

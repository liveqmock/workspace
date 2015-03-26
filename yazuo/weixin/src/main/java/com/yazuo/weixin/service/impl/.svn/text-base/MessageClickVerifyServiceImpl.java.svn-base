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
package com.yazuo.weixin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.service.MessageClickVerifyService;
import com.yazuo.weixin.service.ServiceConfigService;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.ParamConfigVO;
import com.yazuo.weixin.vo.ServiceConfigVO;

/**
 * @ClassName: LotteryMessageVerifyServiceImpl
 * @Description: 处理微信抽奖消息的逻辑接口
 */
@Service("messageClickVerifyService")
public class MessageClickVerifyServiceImpl implements MessageClickVerifyService {

	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;

	@Resource
	private ServiceConfigService serviceConfigService;

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
			BusinessVO business) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandId", business.getBrandId());
		map.put("openid", messageIn.getFromUserName());
		map.put("devid", messageIn.getToUserName());
		String content = getServiceContent(map);
		if (!StringUtils.isBlank(content)) {
			return getMessage(messageIn, content);
		} else {
			return getErrorMessage(messageIn);
		}

	}

	/**
	 * 拼接跳转服务的content
	 * 
	 * 例如， "欢迎来到新辣道鱼火锅"，<a href='{$url}'>请点击这里</a>进行一键上网。(上网仅限店内使用)";
	 */
	public String getServiceContent(Map<String, Object> map) {
		StringBuffer c = new StringBuffer(512);
		Integer brandId = Integer.parseInt(map.get("brandId") + "");
		String openid = map.get("openid") + "";
		String devid = map.get("devid") + "";
		try {
			StringBuffer sb = new StringBuffer(512);

			Map<String, Object> m = serviceConfigService
					.queryServiceConfig(brandId);
			List<ServiceConfigVO> serviceConfig = (List<ServiceConfigVO>) m
					.get("serviceConfig");
			String content1 = null;
			String content2 = null;
			String content3 = null;
			if (serviceConfig != null && serviceConfig.size() > 0) {
				ServiceConfigVO ser = serviceConfig.get(0);
				String url = ser.getUrl();
				content1 = ser.getContent1();
				content2 = ser.getContent2();
				content3 = ser.getContent3();

				sb.append(url).append("?");

				List<ParamConfigVO> paramConfigList = (List<ParamConfigVO>) m
						.get("paramConfigList");
				if (paramConfigList != null && paramConfigList.size() > 0) {
					for (int i = 0; i < paramConfigList.size(); i++) {
						ParamConfigVO param = paramConfigList.get(i);
						String paramName = param.getParamName();
						String paramType = param.getParamType();
						String isDefault = param.getIsDefault();
						String paramValue = param.getParamValue();

						if ("0".equals(isDefault)) {// 动态的
							if ("1".equals(paramType)) {// 个人微信id（openid）
								sb.append(paramName).append("=").append(openid);
							} else if ("2".equals(paramType)) {// 商户微信id（devid）
								sb.append(paramName).append("=").append(devid);
							}
						} else if ("1".equals(isDefault)) {// 默认值
							sb.append(paramName).append("=").append(paramValue);
						}

						sb.append("&");
					}
				}
			}

			// 欢迎来到新辣道鱼火锅<a href='{$url}'>请点击这里</a>进行一键上网。(上网仅限店内使用)
			if (content1 != null && content1.trim().length() > 0) {
				c.append(content1);
			}

			if (content2 != null && content2.trim().length() > 0) {
				String url = "";
				if (!StringUtils.isBlank(sb.toString())
						&& sb.toString().length() > 1) {
					url = sb.toString()
							.substring(0, sb.toString().length() - 1);
				} else {
					url = sb.toString();
				}
				c.append("<a href=\"").append(url).append("\">")
						.append(content2).append("</a>");
			}

			if (content3 != null && content3.trim().length() > 0) {
				c.append(content3);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c.toString();
	}

	/**
	 * <p>
	 * Description:获得message
	 * </p>
	 * 
	 */
	private Message getMessage(Message messageIn, String content) {

		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent(content);

		messageOut.setFuncFlag("1");
		return messageOut;

	}

	/**
	 * <p>
	 * Description:不合法的情况
	 * </p>
	 * 
	 */
	private Message getErrorMessage(Message messageIn) {

		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setFuncFlag("0");
		messageOut.setContent("感谢您的关注！");
		return messageOut;

	}

}

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
package com.yazuo.weixin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.LotteryRuleDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.service.LotteryRuleService;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.MemberVO;

/**
 * @InterfaceName: LotteryRuleServiceImpl
 * @Description: 处理抽奖规则逻辑的实现
 */
@Service("lotteryRuleService")
public class LotteryRuleServiceImpl implements LotteryRuleService {

	@Resource
	private LotteryRuleDao lotteryRuleDao;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Resource
	private MemberDao memberDao;

	public Map<String, Object> getLotteryRule(int brandId, int type)
			throws Exception {

		return lotteryRuleDao.getLotteryRule(brandId, type);
	}

	@Override
	public Map<String, Object> getCardLotterRule(Integer brandId, Integer activtiyType) {
		List<Map<String, Object>> list = lotteryRuleDao.getCardLotterRule(brandId, activtiyType);
		if (list !=null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int updateLotterRuleSmsStatus(Integer id) {
		return lotteryRuleDao.updateRuleSmsStatus(id);
	}

	@Override
	public String getMonitorMsgByRuleId(Integer ruleId) {
		List<Map<String, Object>> list = lotteryRuleDao.getMonitorMsgByRuleId(ruleId);
		StringBuilder sb = new StringBuilder();
		if (list !=null && list.size()>0) {
			for (Map<String, Object> map : list) {
				String mobile = map.get("receiver_mobile")+"";
				if (!StringUtil.isNullOrEmpty(mobile)) {
					sb.append(mobile).append(",");
				}
			}
		}
		if (!StringUtil.isNullOrEmpty(sb.toString())) {
			return sb.toString().substring(0, sb.toString().length()-1);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getLotteryRuleOfBrand(Integer brandId, int activeType, String weixinId, String path) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		MemberVO member = memberDao.getMember(weixinId, brandId);
		List<Map<String, Object>> list = lotteryRuleDao.getLotteryRuleOfBrand(brandId, activeType);
		if (list !=null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				int type = Integer.parseInt(map.get("type")+""); 
				StringBuilder sb = new StringBuilder(serverIp).append(path);
				if (activeType == 22) { // 普通的抽奖
					sb.append("/weixin/consumerLottery/");
				} else if (activeType ==28) { // 幸运抽奖
					sb.append("/caffe/cardLottery/");
				}
				String name = "";
				if (type == 1) {// 刮刮卡
					name = "scratchCard.do?type=1";
					resultMap.put("title", "刮刮卡");
					resultMap.put("type", 1);
				} else if (type == 2) {// 大转盘
					name = "luckRoulette.do?type=2";
					resultMap.put("title", "大转盘");
					resultMap.put("type", 2);
				} else if (type == 3) {// 老虎机
					name = "slotMachine.do?type=3";
					resultMap.put("title", "老虎机");
					resultMap.put("type", 3);
				} else { // 如果活动未创建时进错误页面
					name = "/caffe/cardLottery/errorPage.do";
				}
				sb.append(name).append("&brandId=").append(member.getBrandId()).append("&weixinId=").append(
						weixinId).append("&membershipId=").append(member.getMembershipId()).append("&phoneNo="
				).append(member.getPhoneNo());
				resultMap.put("url", sb.toString());
				resultList.add(resultMap);
			}
		}
		return resultList;
	}
	
	
}

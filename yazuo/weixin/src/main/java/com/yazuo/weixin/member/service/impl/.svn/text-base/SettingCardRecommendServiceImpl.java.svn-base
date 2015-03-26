/** 
* @author kyy
* @version 创建时间：2015-1-13 上午11:47:37 
* 类说明 
*/ 
package com.yazuo.weixin.member.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.es.vo.CardTypeVo;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.member.dao.SettingCardRecommendDao;
import com.yazuo.weixin.member.service.SettingCardRecommendService;
import com.yazuo.weixin.member.vo.SettingCardRecommendVo;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;

@Service
public class SettingCardRecommendServiceImpl implements SettingCardRecommendService {
	private static final Log LOG = LogFactory.getLog("weixin");
	
	@Value("#{propertiesReader['getCardType']}")
	private String getCardTypeUrl; // 某个商户下已的卡类型
	
	@Resource
	private SettingCardRecommendDao settingCardRecommendDao;

	
	@Override
	public List<CardTypeVo> getCardTypeByBrandId(Integer brandId) {
		List<CardTypeVo> cardList = new ArrayList<CardTypeVo>();
		String input = "{\"merchantId\":"+brandId+",\"isParent\":"+false+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(getCardTypeUrl, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			if (jo != null) {
				boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
				if (success) {
					JSONArray array = jo.getJSONObject("data").getJSONObject("result").getJSONArray("cardtypeList");
					Iterator it = array.iterator();
					while (it.hasNext()) {
						CardTypeVo cardType = (CardTypeVo)JSONObject.toBean(JSONObject.fromObject(it.next()), CardTypeVo.class);
						cardList.add(cardType);
					}
				}
			}
		}
		return cardList;
	}


	@Override
	public boolean saveCardType(Integer brandId, String[] cardTypeIds) {
		// 先删除再保存
		int delCount = settingCardRecommendDao.deleteSettingByBrandId(brandId);
		// 保存选择的卡类型id
		if (cardTypeIds !=null && cardTypeIds.length > 0) {
			List<SettingCardRecommendVo> list = new ArrayList<SettingCardRecommendVo>();
			for (int i=0; i < cardTypeIds.length; i++) {
				SettingCardRecommendVo card = new SettingCardRecommendVo();
				card.setBrandId(brandId);
				card.setCardTypeId(Integer.parseInt(cardTypeIds[i]));
				list.add(card);
			}
			int count = settingCardRecommendDao.saveSettingCard(list);
			if (count >0) {
				return true;
			}
		} else {
			if (delCount > 0) {
				return true;
			}
		}
		return false;
	}


	@Override
	public List<Integer> getSettingCardByBrandId(Integer brandId) {
		List<Integer> list = new ArrayList<Integer>();
		List<Map<String, Object>> resultList = settingCardRecommendDao.getSettingCardByBrandId(brandId);
		if (resultList !=null && resultList.size()>0) {
			for (Map<String, Object> m : resultList) {
				Integer cardTypeId = Integer.parseInt(String.valueOf(m.get("card_type_id")));
				list.add(cardTypeId);
			}
		}
		return list;
	}
	
}

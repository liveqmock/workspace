package com.yazuo.weixin.member.service;

import java.util.List;

import com.yazuo.weixin.es.vo.CardTypeVo;

public interface SettingCardRecommendService {

	/**取某个商户已有的卡*/
	public List<CardTypeVo> getCardTypeByBrandId(Integer brandId);
	
	/**保存某个商户选择的卡类型*/
	public boolean saveCardType(Integer brandId, String [] cardTypeIds);
	
	/**取某个商户已的卡类型*/
	public List<Integer> getSettingCardByBrandId(Integer brandId);
	
}

package com.yazuo.weixin.card.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yazuo.weixin.es.vo.BrandVO;
import com.yazuo.weixin.es.vo.CardTypeVo;
import com.yazuo.weixin.vo.CouponVO2;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.TransWaterPage;

public interface CardService {
	public List<MemberCardVO> queryCardList(Integer brandId,String mobile);
	public List<CouponVO2> getCouponList(Integer brandId,String mobile,String cardNo);
	public void showCouponList(List<Message> articles,List<MemberCardVO> cardList,Integer brandId,String mobile,String weixinId, String path);
	public BrandVO getMerchant(Integer brandId);
	public CardTypeVo getCardType(Integer cardTypeId);
	public String getCouponDesc(int batchNo)throws Exception;
	
	/**从搜索引擎中取卡列表*/
	public List<MemberCardVO> queryCardList(Integer brandId, Integer membershipId);
	
	/**根据多个卡类型id取多个卡信息*/
	public List<CardTypeVo> getCardType(List<Integer> cardTypeIdList);
	public Map<Integer,TransWaterPage> queryEvaluate(Integer brandId,Set<Integer> masterIds);
	public Map<String,String> queryMerchant(Set<String> transMerchantIds);
}

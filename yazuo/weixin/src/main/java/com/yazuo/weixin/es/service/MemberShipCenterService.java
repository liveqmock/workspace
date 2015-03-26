package com.yazuo.weixin.es.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yazuo.weixin.vo.CouponVO2;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.PageVO;

/**
* @ClassName MemberShipCenterService
* @Description es搜索引擎Service
* @author sundongfeng@yazuo.com
* @date 2014-11-26 上午10:43:25
* @version 1.0
*/
public interface MemberShipCenterService {
	public MemberVO queryMemberByMsId(String brandId,String memberShipId);
	public List<MemberCardVO> queryMemberCardList(String brandId,Object... memberShipId);
	public Map<String,List<CouponVO2>> getCardCouponList(String brandId,String cardno,String mobile);
	
	/**通过搜索引擎取券信息*/
	public  List<Object> queryRemainOrderCouponByEs(Integer brandId, Object... couponIdStr);
	/**通过手机号查询搜索引擎会员id*/
	public Set<Object> queryMemberShipsByMobile(String brandId,String mobile);
	public Map<String, Object> queryTransWaterRecordByEs(String cardno,String memberShipId,String brandId,PageVO page);
}

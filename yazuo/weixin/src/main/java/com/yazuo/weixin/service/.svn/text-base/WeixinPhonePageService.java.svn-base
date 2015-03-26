package com.yazuo.weixin.service;

import java.util.List;

import javax.imageio.stream.FileImageInputStream;

import com.yazuo.weixin.vo.ActivityConfigVO;
import com.yazuo.weixin.vo.ActivityRecordVO;
import com.yazuo.weixin.vo.CardVO;
import com.yazuo.weixin.vo.CouponVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.SubbranchVO;

public interface WeixinPhonePageService {
	public DishesVO getDishesById(Integer id);

	public List<DishesVO> getDishesListByBrandId(Integer brandId);

	public PreferenceVO getPreferenceById(Integer id);

	public List<PreferenceVO> getPreferenceListByBrandId(Integer brandId);

	public SubbranchVO getSubbranchById(Integer id);

	public List<SubbranchVO> getSubbranchListByBrandId(Integer brandId);

	public FileImageInputStream getImageStream(Integer brandId, String name);
	
	public String getNewImageUrl(Integer brandId, String name);//add by sundongfeng
	
	public boolean hasImage(Integer brandId, String name);

	public MemberVO getMemberByWeixinIdAndBrandId(Integer brandId,
			String weixinId);

	public CardVO getCard(String cardNo);
	
	public boolean insertMember(MemberVO member);
	
	public List<ActivityConfigVO> getEffectiveActivityConfigListByBrandId(Integer brandId);
	
	public ActivityRecordVO getCouponCode(Integer brandId,String weixinId,List<CouponVO> guaGuaCouponList);
	
	public boolean isAllow(Integer brandId,String weixinId);
	
	public boolean modifyActivityRecordStatus(ActivityRecordVO activityRecord);
	
	public ActivityRecordVO getLastActivityRecord(Integer brandId,String weixinId);
	
	/**crm中取该卡是否需要输入密码*/
	public Object judgeNeedPwd(String cardNo, Integer brandId);
	
	/**根据membershipId从crm取会员信息*/
	public MemberVO getMemberMessageBymembershipId(Integer membershipId, Integer brandId);
}

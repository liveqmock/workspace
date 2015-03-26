package com.yazuo.weixin.member.service;

import java.util.List;
import java.util.Map;

import com.yazuo.weixin.member.vo.WeixinSettingResourceVo;

public interface ResourceManagerService {

	/**保存设置资源信息*/
	public int saveResource(WeixinSettingResourceVo resourceVo);
	
	/**修改设置资源信息*/
	public int updateResource(WeixinSettingResourceVo resourceVo);
	
	/**取设置资源信息*/
	public List<WeixinSettingResourceVo> getResourceList(boolean isResourceList);
	
	/**保存资源与门店信息*/
	public int saveMerchantResource(List<String> resourceList, Integer brandId);
	
	/**获取某个商户设置的资源*/
	public List<Map<String, Object>> getResourceByBrandId(Integer brandId);
	
	/**根据id取设置资源信息*/
	public WeixinSettingResourceVo getResourceById(Integer id);
	
	/**判断某个类型值是否在设置资源已经存在*/
	public boolean judgeExistsSameTypeValue(Integer typeValue, Integer resourceId);
	
	/**判断某个商户是否存在资源中的某一种*/
	public boolean judgeExistsResourceOfBrand(Integer typeValue, Integer brandId);
	
	/**取某个商户配置的功能*/
	public List<Map<String, Object>> getResourceOfBrandId(Integer brandId, String resourceSource);
	
	/**wifi设置卡推荐页同时开启微信的会员权益权限*/
	public Map<String, Object> operateMemberRight(String param);
}

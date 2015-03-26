package com.yazuo.weixin.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.member.dao.ResourceManagerDao;
import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.member.vo.MerchantSettingResourceVo;
import com.yazuo.weixin.member.vo.WeixinSettingResourceVo;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;
@Service
public class ResourceManagerServiceImpl implements ResourceManagerService {
	private static final Log LOG = LogFactory.getLog("weixin");
	@Resource
	private ResourceManagerDao resourceManagerDao;

	@Override
	public int saveResource(WeixinSettingResourceVo resourceVo) {
		return resourceManagerDao.saveResource(resourceVo);
	}

	@Override
	public int updateResource(WeixinSettingResourceVo resourceVo) {
		return resourceManagerDao.updateSettingResource(resourceVo);
	}

	@Override
	public List<WeixinSettingResourceVo> getResourceList(boolean isResourceList) {
		return resourceManagerDao.getAllResource(isResourceList);
	}

	@Override
	public int saveMerchantResource(List<String> resourceList, Integer brandId) {
		// 先将原数据删除再保存
		resourceManagerDao.deleteResourceByBrandId(brandId);
		List<MerchantSettingResourceVo> list = new ArrayList<MerchantSettingResourceVo>();
		if (resourceList !=null && resourceList.size() > 0) {
			for (int i=0; i<resourceList.size(); i++) {
				MerchantSettingResourceVo m = new MerchantSettingResourceVo();
				m.setBrandId(brandId);
				m.setResourceId(Integer.parseInt(resourceList.get(i)));
				m.setDataSource(Constant.MEMBERSOURCE_1); // 微信设置的来源
				list.add(m);
			}
			return resourceManagerDao.saveResourceMerchant(list);
		}
		return 1;
	}

	@Override
	public List<Map<String, Object>> getResourceByBrandId(Integer brandId) {
		return resourceManagerDao.getResourceByBrandId(brandId);
	}

	@Override
	public WeixinSettingResourceVo getResourceById(Integer id) {
		return resourceManagerDao.getResourceById(id);
	}

	@Override
	public boolean judgeExistsSameTypeValue(Integer typeValue, Integer resourceId) {
		Map<String, Object> map = resourceManagerDao.getExistsCount(typeValue);
		
		if (resourceId !=null && resourceId!=0) { // 修改判断是否有重复
			if(map.get("id").equals(resourceId)) return false;
			else return true;
		} else {
			if(map!=null && map.size()>0)return true;
			else return false;
		}
	}

	@Override
	public boolean judgeExistsResourceOfBrand(Integer typeValue, Integer brandId) {
		long count = resourceManagerDao.judgeHasResourceOfBrand(brandId, typeValue);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getResourceOfBrandId(Integer brandId,
			String resourceSource) {
		return resourceManagerDao.getResourceOfBrandId(brandId, resourceSource);
	}

	
	
	@Override
	public Map<String, Object> operateMemberRight(String param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (!StringUtil.isNullOrEmpty(param)) {
			JSONObject jo = JSONObject.fromObject(param);
			MerchantSettingResourceVo msr = new MerchantSettingResourceVo();
			msr.setBrandId(jo.getInt("brandId"));
			msr.setResourceId(Constant.MEMBER_RIGHT_RESOURCE_ID); // 会员权益的resourceId
			msr.setDataSource(Constant.MEMBERSOURCE_14); // wifi设置的
			// 操作类型
			String operation = jo.getString("operation");
			if (operation.equals("add")) { // 添加操作
				if (!resourceManagerDao.judgeHasMemberRightOfBrand(msr.getBrandId(), msr.getResourceId())) { // 微信未设置会员权益
					List<MerchantSettingResourceVo> list = new ArrayList<MerchantSettingResourceVo>();
					list.add(msr);
					int count = resourceManagerDao.saveResourceMerchant(list);
					resultMap.put("flag", count>0);
					resultMap.put("message", count>0?"添加成功":"添加失败");
				} else {
					resultMap.put("flag", true);
					resultMap.put("message", "此权限已设置");
				}
			} else if(operation.equals("del")){ // 删除操作 
				boolean isExists = resourceManagerDao.judgeWifiResourceByBrandId(msr); // 删除之前查询是否存在
				if (isExists) {
					int count = resourceManagerDao.deleteWifiResourceByBrandId(msr);
					resultMap.put("flag", count>0);
					resultMap.put("message", count>0?"删除成功":"删除失败");
				} else {
					resultMap.put("flag", true);
					resultMap.put("message", "数据不存在，不需要删除");
				}
			}
		} else {
			resultMap.put("flag", 0);
			resultMap.put("message", "参数不能为空");
		}
		LOG.info("超级wifi调用设置卡推荐页返回结果："+JSONObject.fromObject(resultMap));
		return resultMap;
	}
}

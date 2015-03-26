/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author gaoshan
 * @date 2014-04-15
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.service.ServiceConfigService;
import com.yazuo.weixin.vo.ParamConfigVO;
import com.yazuo.weixin.vo.ServiceConfigVO;

/**
 * @ClassName: ServiceConfigController
 * @Description: 服务配置管理
 */
@Controller
@RequestMapping("/weixin/serviceConfig")
public class ServiceConfigController {
	@Autowired
	private ServiceConfigService serviceConfigService;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 添加商户相关的服务配置
	 * 
	 * @return
	 */
	@RequestMapping(value = "addServiceConfig", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public int addServiceConfig(@RequestParam(value = "params", required = true) String params) {
		try {
			Map<String, Object> map = jsonJieXi(params);// 由json转为Map
			ServiceConfigVO serviceConfig = (ServiceConfigVO) map.get("serviceConfig");
			List<ParamConfigVO> paramConfiglist = (List<ParamConfigVO>) map.get("paramConfigList");

			// 添加商户的服务配置
			boolean insertBol = serviceConfigService.insertServiceConfig(serviceConfig);
			if (!insertBol) {
				log.info("添加商户跳转服务配置失败");
				return 0;
			}

			List<ServiceConfigVO> list = serviceConfigService.queryServiceConfigByBrandId(serviceConfig.getBrandId());
			ServiceConfigVO s = list.get(0);
			Integer serviceId = s.getServiceId();

			if (paramConfiglist != null && paramConfiglist.size() > 0) {
				// 添加商户的参数配置
				insertBol = serviceConfigService.insertParamConfig(paramConfiglist, serviceId);
				if (!insertBol) {
					log.info("添加商户跳转服务的参数配置失败");
					return 0;
				}
			}

			return 1;
		} catch (Exception e) {
			log.error("删除商户跳转服务配置错误", e);
			return 0;
		}
	}

	/**
	 * 修改商户相关的服务配置
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateServiceConfig", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public int updateServiceConfig(@RequestParam(value = "params", required = true) String params,
			@RequestParam(value = "serviceId", required = true) Integer serviceId) {
		try {
			Map<String, Object> map = jsonJieXi(params);// 由json转为Map
			ServiceConfigVO serviceConfig = (ServiceConfigVO) map.get("serviceConfig");
			serviceConfig.setServiceId(serviceId);

			List<ParamConfigVO> paramConfiglist = (List<ParamConfigVO>) map.get("paramConfigList");

			// 添加商户的服务配置
			boolean insertBol = serviceConfigService.updateServiceConfig(serviceConfig);
			if (!insertBol) {
				log.info("修改商户跳转服务配置失败");
				return 0;
			}

			// 根据商户ID删除服务的参数配置
			boolean deleteBol = serviceConfigService.deleteParamConfig(serviceConfig);
			if (!deleteBol) {
				log.info("根据商户ID删除服务的参数配置失败");
				return 0;
			}

			if (paramConfiglist != null && paramConfiglist.size() > 0) {
				// 添加商户的参数配置
				insertBol = serviceConfigService.insertParamConfig(paramConfiglist, serviceId);
				if (!insertBol) {
					log.info("添加商户跳转服务的参数配置失败");
					return 0;
				}				
			}

			return 1;
		} catch (Exception e) {
			log.error("修改商户跳转服务配置错误", e);
			return 0;
		}
	}

	/**
	 * 删除商户相关的服务配置
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteServiceConfig", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public int deleteServiceConfig(@RequestParam(value = "brandId", required = true) Integer brandId) {
		try {
			ServiceConfigVO serviceConfigVO = new ServiceConfigVO();
			serviceConfigVO.setBrandId(brandId);

			// 根据商户ID删除服务参数配置
			boolean deleteBol = serviceConfigService.deleteParamConfig(serviceConfigVO);
			if (!deleteBol) {
				log.error("删除商户跳转服务配置错误");
				return 0;
			}

			// 根据商户ID删除服务配置
			deleteBol = serviceConfigService.deleteServiceConfig(serviceConfigVO);
			if (!deleteBol) {
				log.error("删除商户跳转服务参数配置错误");
				return 0;
			}

			return 1;
		} catch (Exception e) {
			log.error("删除商户跳转服务配置错误", e);
			return 0;
		}
	}

	/**
	 * 根据商户ID查询相关的服务配置和参数配置（一个商户ID仅有一个服务地址）
	 * 
	 * @return
	 */
	@RequestMapping(value = "serviceConfig", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView serviceConfig(@RequestParam(value = "brandId", required = true) Integer brandId) {
		try {
			// 根据商户ID查询相关的服务配置
			List<ServiceConfigVO> serviceConfigList = serviceConfigService.queryServiceConfigByBrandId(brandId);

			// 根据商户ID查询相关的参数配置
			List<ParamConfigVO> paramConfigList = serviceConfigService.queryParamConfigByBrandId(brandId);

			ModelAndView mav = new ModelAndView("serviceConfig/serviceConfigDtl");
			mav.addObject("serviceConfigList", serviceConfigList);
			mav.addObject("paramConfigList", paramConfigList);
			mav.addObject("brandId", brandId);
			return mav;
		} catch (Exception e) {
			log.error("一键跳转服务配置页面错误", e);
			ModelAndView mav = new ModelAndView("serviceConfig/error");
			mav.addObject("error", "系统异常，请稍后再试");
			return mav;
		}
	}

	/**
	 * 解析json，取出第一个为服务配置信息，第二个为服务参数配置信息
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> jsonJieXi(String params) {
		Map<String, Object> map = new HashMap<String, Object>();

		JSONArray json = JSONArray.fromObject(params);
		JSONArray js = JSONArray.fromObject(json.get(0));

		// 组织服务配置信息
		ServiceConfigVO serviceConfig = new ServiceConfigVO();
		if (null != js && 0 < js.size()) {
			for (int i = 0; i < js.size(); i++) {
				String url = js.getJSONObject(i).getString("url");
				int brandId = js.getJSONObject(i).getInt("brandId");
				String content1 = js.getJSONObject(i).getString("content1");
				String content2 = js.getJSONObject(i).getString("content2");
				String content3 = js.getJSONObject(i).getString("content3");

				serviceConfig.setUrl(url);
				serviceConfig.setBrandId(brandId);
				serviceConfig.setContent1(content1);
				serviceConfig.setContent2(content2);
				serviceConfig.setContent3(content3);

				map.put("serviceConfig", serviceConfig);
			}
		} else {
			log.info("没有传递服务配置信息");
		}

		// 组织信息
		JSONArray ja = JSONArray.fromObject(json.get(1));
		List<ParamConfigVO> paramConfigList = new ArrayList<ParamConfigVO>();
		if (null != ja && 0 < ja.size()) {
			for (int i = 0; i < ja.size(); i++) {
				ParamConfigVO paramConfig = new ParamConfigVO();
				String paramName = ja.getJSONObject(i).getString("param_name");
				String paramType = ja.getJSONObject(i).getString("param_type");
				String paramDescription = ja.getJSONObject(i).getString("param_description");
				String isDefault = ja.getJSONObject(i).getString("is_default");
				String paramValue = ja.getJSONObject(i).getString("param_value");

				paramConfig.setParamName(paramName);
				paramConfig.setParamType(paramType);
				paramConfig.setParamDescription(paramDescription);
				paramConfig.setIsDefault(isDefault);
				paramConfig.setParamValue(paramValue);

				paramConfigList.add(paramConfig);
			}
		} else {
			log.info("没有传递服务参数的信息");
		}

		map.put("paramConfigList", paramConfigList);
		return map;
	}
}
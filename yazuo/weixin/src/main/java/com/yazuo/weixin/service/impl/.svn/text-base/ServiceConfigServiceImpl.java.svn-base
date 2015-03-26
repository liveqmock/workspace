/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2013-11-25
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.ServiceConfigDao;
import com.yazuo.weixin.service.ServiceConfigService;
import com.yazuo.weixin.vo.ParamConfigVO;
import com.yazuo.weixin.vo.ServiceConfigVO;

/**
 * @InterfaceName: ServiceConfigServiceImpl
 * @Description: 服务配置管理
 */
@Scope("prototype")
@Service
public class ServiceConfigServiceImpl implements ServiceConfigService {

	@Autowired
	private ServiceConfigDao serviceConfigDao;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 根据商户ID查询跳转服务的配置
	 */
	@Override
	public List<ServiceConfigVO> queryServiceConfigByBrandId(Integer brandId) throws Exception {
		List<ServiceConfigVO> serviceConfigList = serviceConfigDao.queryServiceConfigByBrandId(brandId);
		return serviceConfigList;
	}

	/**
	 * 添加商户的服务配置
	 */
	@Override
	public boolean insertServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		boolean insertBol = serviceConfigDao.insertServiceConfig(serviceConfigVO);
		return insertBol;
	}

	/**
	 * 添加商户的参数配置
	 */
	@Override
	public boolean insertParamConfig(List<ParamConfigVO> paramConfiglist, Integer serviceId) throws Exception {
		boolean insertBol = serviceConfigDao.insertParamConfig(paramConfiglist, serviceId);
		return insertBol;
	}

	/**
	 * 根据商户ID删除服务配置
	 */
	@Override
	public boolean deleteServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		boolean deleteBol = serviceConfigDao.deleteServiceConfig(serviceConfigVO);
		return deleteBol;
	}

	/**
	 * 根据商户ID删除服务参数配置
	 */
	@Override
	public boolean deleteParamConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		boolean deleteBol = serviceConfigDao.deleteParamConfig(serviceConfigVO);
		return deleteBol;
	}

	/**
	 * 根据商户ID查询相关的参数配置
	 */
	@Override
	public List<ParamConfigVO> queryParamConfigByBrandId(Integer brandId) throws Exception {
		List<ParamConfigVO> paramConfigList = serviceConfigDao.queryParamConfigByBrandId(brandId);
		return paramConfigList;
	}

	/**
	 * 根据服务ID修改服务配置
	 */
	@Override
	public boolean updateServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		boolean updateBol = serviceConfigDao.updateServiceConfig(serviceConfigVO);
		return updateBol;
	}

	/**
	 * 根据参数ID修改服务参数配置
	 */
	@Override
	public boolean updateParamConfig(ParamConfigVO paramConfigVO) throws Exception {
		boolean updateBol = serviceConfigDao.updateParamConfig(paramConfigVO);
		return updateBol;
	}

	/**
	 * 根据商户ID查询跳转服务及参数信息
	 */
	@Override
	public Map<String, Object> queryServiceConfig(Integer brandId) throws Exception {
		// 根据商户ID查询跳转服务的配置
		List<ServiceConfigVO> serviceConfig = serviceConfigDao.queryServiceConfigByBrandId(brandId);

		// 根据商户ID查询相关的参数配置
		List<ParamConfigVO> paramConfigList = serviceConfigDao.queryParamConfigByBrandId(brandId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceConfig", serviceConfig);
		map.put("paramConfigList", paramConfigList);
		return map;
	}

}

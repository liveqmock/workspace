package com.yazuo.weixin.service;

import java.util.List;
import java.util.Map;

import com.yazuo.weixin.vo.ParamConfigVO;
import com.yazuo.weixin.vo.ServiceConfigVO;

public interface ServiceConfigService {

	/**
	 * 根据商户ID查询跳转服务的配置
	 * 
	 * @param brandId
	 * @return
	 * @throws Exception
	 */
	List<ServiceConfigVO> queryServiceConfigByBrandId(Integer brandId) throws Exception;

	/**
	 * 添加商户的服务配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 * @throws Exception
	 */
	boolean insertServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception;

	/**
	 * 添加商户的参数配置
	 * 
	 * @param paramConfiglist
	 * @param serviceId
	 * @return
	 * @throws Exception
	 */
	boolean insertParamConfig(List<ParamConfigVO> paramConfiglist, Integer serviceId) throws Exception;

	/**
	 * 根据商户ID删除服务配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 * @throws Exception
	 */
	boolean deleteServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception;

	/**
	 * 根据商户ID删除服务参数配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 * @throws Exception
	 */
	boolean deleteParamConfig(ServiceConfigVO serviceConfigVO) throws Exception;

	/**
	 * 根据商户ID查询相关的参数配置
	 * 
	 * @param brandId
	 * @return
	 * @throws Exception
	 */
	List<ParamConfigVO> queryParamConfigByBrandId(Integer brandId) throws Exception;

	/**
	 * 根据服务ID修改服务配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 * @throws Exception
	 */
	boolean updateServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception;

	/**
	 * 根据参数ID修改服务参数配置
	 * 
	 * @param paramConfigVO
	 * @return
	 * @throws Exception
	 */
	boolean updateParamConfig(ParamConfigVO paramConfigVO) throws Exception;

	/**
	 * 根据商户ID查询跳转服务及参数信息
	 * 
	 * @param brandId
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryServiceConfig(Integer brandId) throws Exception;

}

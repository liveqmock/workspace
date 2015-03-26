package com.yazuo.weixin.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.vo.ParamConfigVO;
import com.yazuo.weixin.vo.ServiceConfigVO;

@Repository
public class ServiceConfigDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 根据商户ID查询跳转服务的配置
	 * 
	 * @param brandId
	 * @return
	 */
	public List<ServiceConfigVO> queryServiceConfigByBrandId(Integer brandId) throws Exception {
		StringBuffer sql = new StringBuffer(512);
		sql.append(" SELECT                ");
		sql.append(" 	s.service_id,      ");
		sql.append(" 	s.url,             ");
		sql.append(" 	s.brand_id,        ");
		sql.append(" 	s.content1,        ");
		sql.append(" 	s.content2,        ");
		sql.append(" 	s.content3,        ");
		sql.append(" 	s.insert_time,     ");
		sql.append(" 	s.update_time      ");
		sql.append(" FROM                  ");
		sql.append(" 	service_config s   ");
		sql.append(" WHERE                 ");
		sql.append(" 	s.brand_id = ?;    ");
		log.info(sql.toString() + "[" + brandId.toString() + "]");
		return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ServiceConfigVO>(ServiceConfigVO.class), brandId);
	}

	/**
	 * 添加商户的服务配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 */
	public boolean insertServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		StringBuffer sql = new StringBuffer(512);
		sql.append(" INSERT INTO service_config(     ");
		sql.append(" 		url,                     ");
		sql.append(" 		brand_id,                ");
		sql.append(" 		content1,                ");
		sql.append(" 		content2,                ");
		sql.append(" 		content3,                ");
		sql.append(" 		insert_time,             ");
		sql.append(" 		update_time              ");
		sql.append(" 	)                            ");
		sql.append(" 	VALUES                       ");
		sql.append(" 		(?,?,?,?,?,now(),now()); ");
		log.info(sql.toString() + "[" + serviceConfigVO.toString() + "]");
		return jdbcTemplate.update(sql.toString(), serviceConfigVO.getUrl(), serviceConfigVO.getBrandId(), serviceConfigVO.getContent1(),
				serviceConfigVO.getContent2(), serviceConfigVO.getContent3()) != -1 ? true : false;
	}

	/**
	 * 添加商户的参数配置
	 * 
	 * @param paramConfigVO
	 * @param serviceId
	 * @return
	 */
	public boolean insertParamConfig(List<ParamConfigVO> paramConfiglist, Integer serviceId) throws Exception {
		int length = paramConfiglist.size();
		String[] sqls = new String[length];
		for (int i = 0; i < length; i++) {
			StringBuffer sql = new StringBuffer(512);
			ParamConfigVO paramConfigVO = paramConfiglist.get(i);
			sql.append(" INSERT INTO param_config (service_id,param_name,param_type,param_description,is_default,param_value,insert_time,update_time) VALUES ("
					+ serviceId + ",'" + paramConfigVO.getParamName() + "','" + paramConfigVO.getParamType() + "','" + paramConfigVO.getParamDescription()
					+ "','" + paramConfigVO.getIsDefault() + "','" + paramConfigVO.getParamValue() + "',now(),now()); ");
			sqls[i] = sql.toString();
		}
		log.info(sqls.toString() + "[serviceId : " + serviceId + "," + paramConfiglist.toString() + "]");
		return jdbcTemplate.batchUpdate(sqls) != null ? true : false;
	}

	/**
	 * 根据商户ID删除服务配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 */
	public boolean deleteServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		StringBuffer sql = new StringBuffer(512);
		sql.append(" DELETE               ");
		sql.append(" FROM                 ");
		sql.append(" 	service_config s  ");
		sql.append(" WHERE                ");
		sql.append(" 	s.brand_id = ?; ");
		log.info(sql.toString() + "[" + serviceConfigVO.toString() + "]");
		return jdbcTemplate.update(sql.toString(), serviceConfigVO.getBrandId()) != -1 ? true : false;
	}

	/**
	 * 根据商户ID删除服务参数配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 */
	public boolean deleteParamConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		StringBuffer sql = new StringBuffer(512);
		sql.append(" DELETE                                  ");
		sql.append(" FROM                                    ");
		sql.append(" 	param_config pa                      ");
		sql.append(" WHERE                                   ");
		sql.append(" 	pa.param_id IN(                      ");
		sql.append(" 		SELECT                           ");
		sql.append(" 			P .param_id                  ");
		sql.append(" 		FROM                             ");
		sql.append(" 			param_config P,              ");
		sql.append(" 			service_config s             ");
		sql.append(" 		WHERE                            ");
		sql.append(" 			s.service_id = P .service_id ");
		sql.append(" 		AND s.brand_id = ?               ");
		sql.append(" 	);                                   ");
		log.info(sql.toString() + "[" + serviceConfigVO.toString() + "]");
		return jdbcTemplate.update(sql.toString(), serviceConfigVO.getBrandId()) != -1 ? true : false;
	}

	/**
	 * 根据商户ID查询相关的参数配置
	 * 
	 * @param brandId
	 * @return
	 */
	public List<ParamConfigVO> queryParamConfigByBrandId(Integer brandId) throws Exception {
		StringBuffer sql = new StringBuffer(512);
		sql.append(" SELECT                           ");
		sql.append(" 	P .param_id,                  ");
		sql.append(" 	P .service_id,                ");
		sql.append(" 	P .param_name,                ");
		sql.append(" 	P .param_type,                ");
		sql.append(" 	P .param_description,         ");
		sql.append(" 	P .is_default,                ");
		sql.append(" 	P .param_value,               ");
		sql.append(" 	P .insert_time,               ");
		sql.append(" 	P .update_time                ");
		sql.append(" FROM                             ");
		sql.append(" 	param_config P,               ");
		sql.append(" 	service_config s              ");
		sql.append(" WHERE                            ");
		sql.append(" 	1 = 1                         ");
		sql.append(" AND s.service_id = P .service_id ");
		sql.append(" AND s.brand_id = ?;              ");
		log.info(sql.toString() + "[" + brandId.toString() + "]");
		return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ParamConfigVO>(ParamConfigVO.class), brandId);
	}

	/**
	 * 根据服务ID修改服务配置
	 * 
	 * @param serviceConfigVO
	 * @return
	 */
	public boolean updateServiceConfig(ServiceConfigVO serviceConfigVO) throws Exception {
		StringBuffer sql = new StringBuffer(512);
		sql.append(" UPDATE service_config ");
		sql.append(" SET URL = ?,          ");
		sql.append("  BRAND_ID = ?,        ");
		sql.append("  CONTENT1 = ?,        ");
		sql.append("  CONTENT2 = ?,        ");
		sql.append("  CONTENT3 = ?,        ");
		sql.append("  UPDATE_TIME = now()  ");
		sql.append(" WHERE                 ");
		sql.append(" 	service_id = ?     ");
		log.info(sql.toString() + "[" + serviceConfigVO.toString() + "]");
		return jdbcTemplate.update(sql.toString(), serviceConfigVO.getUrl(), serviceConfigVO.getBrandId(), serviceConfigVO.getContent1(),
				serviceConfigVO.getContent2(), serviceConfigVO.getContent3(), serviceConfigVO.getServiceId()) != -1 ? true : false;
	}

	/**
	 * 根据参数ID修改服务参数配置
	 * 
	 * @param paramConfigVO
	 * @return
	 */
	public boolean updateParamConfig(ParamConfigVO paramConfigVO) throws Exception {
		StringBuffer sql = new StringBuffer(512);
		sql.append(" UPDATE param_config       ");
		sql.append(" SET SERVICE_ID = ?,       ");
		sql.append("  PARAM_NAME = ?,          ");
		sql.append("  PARAM_TYPE = ?,          ");
		sql.append("  PARAM_DESCRIPTION = ?,   ");
		sql.append("  IS_DEFAULT = ?,          ");
		sql.append("  PARAM_VALUE = ?,         ");
		sql.append("  UPDATE_TIME = now()      ");
		sql.append(" WHERE                     ");
		sql.append(" 	PARAM_ID = ?;          ");
		log.info(sql.toString() + "[" + paramConfigVO.toString() + "]");
		return jdbcTemplate.update(sql.toString(), paramConfigVO.getServiceId(), paramConfigVO.getParamName(), paramConfigVO.getParamType(),
				paramConfigVO.getParamDescription(), paramConfigVO.getIsDefault(), paramConfigVO.getParamValue(), paramConfigVO.getParamId()) != -1 ? true
				: false;
	}
}

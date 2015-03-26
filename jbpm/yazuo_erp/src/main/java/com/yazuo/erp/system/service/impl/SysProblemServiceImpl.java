/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.dao.SysProblemDao;
import com.yazuo.erp.system.dao.SysRemindDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysProblemCommentsService;
import com.yazuo.erp.system.service.SysProblemService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysProblemCommentsVO;
import com.yazuo.erp.system.vo.SysProblemVO;
import com.yazuo.erp.system.vo.SysRemindVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description 问题管理
 * @author erp team
 * @date
 */

@Service
public class SysProblemServiceImpl implements SysProblemService {

	@Resource
	private SysProblemDao sysProblemDao;

	@Resource
	private SysRemindDao sysRemindDao;

	public int saveSysProblem(SysProblemVO sysProblem, SysUserVO user) {
		sysProblem.setIsEnable(Constant.IS_ENABLE);
		sysProblem.setInsertBy(user.getId());
		sysProblem.setUpdateBy(user.getId());
		sysProblem.setInsertTime(new Date());
		sysProblem.setUpdateTime(new Date());
		return sysProblemDao.saveSysProblem(sysProblem);
	}

	public int batchInsertSysProblems(Map<String, Object> map) {
		return sysProblemDao.batchInsertSysProblems(map);
	}

	public int updateSysProblem(SysProblemVO sysProblem, SysUserVO user) {
		sysProblem.setUpdateBy(user.getId());
		sysProblem.setUpdateTime(new Date());
		if (!StringUtils.isEmpty(sysProblem.getSolvingType())) {
			sysProblem.setSolvingTime(new Date());
		}
		if (StringUtils.equals(sysProblem.getProblemStatus(), "2")) {
			// 指派处理人时，发提醒事项
			SysRemindVO sysRemindVO = getSysRemindVo(sysProblem, user);
			sysRemindDao.saveSysRemind(sysRemindVO);
		}

		if (StringUtils.equals(sysProblem.getProblemStatus(), "6")) {
			// 问题状态为6-重新激活时，新增一条记录
			SysProblemVO entity = sysProblemDao.getSysProblemById(sysProblem.getId());
			entity.setInsertBy(user.getId());
			entity.setUpdateBy(user.getId());
			entity.setSolveredBy(null);
			entity.setSolvingTime(null);
			entity.setSolvingType(null);
			entity.setInsertTime(new Date());
			entity.setUpdateTime(new Date());
			entity.setProblemStatus("2");
			sysProblemDao.saveSysProblem(entity);
		}
		return sysProblemDao.updateSysProblem(sysProblem);
	}

	/**
	 * @param sysProblem
	 * @param user
	 * @return
	 * @return SysRemindVO
	 * @throws
	 */
	private SysRemindVO getSysRemindVo(SysProblemVO sysProblem, SysUserVO user) {
		SysRemindVO sysRemindVO = new SysRemindVO();
		sysRemindVO.setUserId(sysProblem.getSolveredBy());
		sysRemindVO.setMerchantId(sysProblem.getMerchantId());
		sysRemindVO.setPriorityLevelType("01");
		sysRemindVO.setItemType("06");
		String content = "您有一个问题'" + sysProblem.getTitle() + "'需要处理，请尽快安排。请在【问题管理】查看详情。";
		sysRemindVO.setItemContent(content);
		sysRemindVO.setItemStatus("1");
		sysRemindVO.setIsEnable("1");
		sysRemindVO.setInsertBy(user.getId());
		sysRemindVO.setInsertTime(new Date());
		sysRemindVO.setUpdateBy(user.getId());
		sysRemindVO.setUpdateTime(new Date());
		return sysRemindVO;
	}

	public int batchUpdateSysProblemsToDiffVals(Map<String, Object> map) {
		return sysProblemDao.batchUpdateSysProblemsToDiffVals(map);
	}

	public int batchUpdateSysProblemsToSameVals(Map<String, Object> map) {
		return sysProblemDao.batchUpdateSysProblemsToSameVals(map);
	}

	public int deleteSysProblemById(Integer id) {
		return sysProblemDao.deleteSysProblemById(id);
	}

	public int batchDeleteSysProblemByIds(List<Integer> ids) {
		return sysProblemDao.batchDeleteSysProblemByIds(ids);
	}

	public SysProblemVO getSysProblemById(Integer id) {
		return sysProblemDao.getSysProblemById(id);
	}

	public List<SysProblemVO> getSysProblems(SysProblemVO sysProblem) {
		return sysProblemDao.getSysProblems(sysProblem);
	}

	public List<Map<String, Object>> getSysProblemsMap(SysProblemVO sysProblem) {
		return sysProblemDao.getSysProblemsMap(sysProblem);
	}
	@Resource SysDictionaryService sysDictionaryService;
	@Resource SysProblemCommentsService sysProblemCommentsService;
	@Override
	public Page<Map<String, Object>> listSysProblems(Map<String, Object> paramMap,  SysUserVO sessionUser) {
		Integer pageNumber = (Integer) paramMap.get("pageNumber");
		Integer pageSize = (Integer) paramMap.get("pageSize");
		PageHelper.startPage(pageNumber, pageSize, true); 
		Object status = paramMap.get(SysProblemVO.COLUMN_PROBLEM_STATUS);
		if(status!=null && "7".equals(status.toString())){ //我的问题 ， 特殊处理
			paramMap.put(SysProblemVO.COLUMN_PROBLEM_STATUS, null);
			paramMap.put(SysProblemVO.COLUMN_INSERT_BY, sessionUser.getId());
		}
		Page<Map<String, Object>> sysProblemsList = (Page<Map<String, Object>>) sysProblemDao.getSysProblemsInfo(paramMap);
		CollectionUtils.forAllDo(sysProblemsList, new Closure() {
			@SuppressWarnings("serial")
			public void execute(Object input) {
				Map<String, Object> map = (Map<String, Object>)input;
				String productName = map.get("product_name").toString();
				String source = map.get("source").toString();
				Map<String, Object> mapProductName = sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000120", productName);
				Map<String, Object> mapSource = sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000121", source);
				map.put("productNameText", !mapProductName.containsKey("text") ? "": mapProductName.get("text").toString());
				map.put("sourceText", !mapSource.containsKey("text") ? "":  mapSource.get("text").toString());
				//查询回复
			    final Integer problemId = (Integer)map.get("id");
				final List<SysProblemCommentsVO> sysProblemCommentss = 
					sysProblemCommentsService.getSysProblemCommentss(new SysProblemCommentsVO(){{setProblemId(problemId);}});
				Collections.sort(sysProblemCommentss, 
						ComparatorUtils.reversedComparator(new BeanComparator(SysProblemCommentsVO.COLUMN_SOLVING_TIME)));
				map.put("sysProblemCommentss", sysProblemCommentss);
			}
		});
		return sysProblemsList;
	}

	@Override
	public long getSysProblemsCount(Integer merchantId) {
		return sysProblemDao.getSysProblemCount(merchantId);
	}

}

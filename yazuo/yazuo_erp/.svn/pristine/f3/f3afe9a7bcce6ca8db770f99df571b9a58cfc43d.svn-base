/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.erp.mkt.dao.MktTeamDao;
import com.yazuo.erp.mkt.service.MktTeamService;
import com.yazuo.erp.mkt.vo.MktTeamVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */

@Service
public class MktTeamServiceImpl implements MktTeamService {
	private static final Log LOG = LogFactory.getLog(MktTeamServiceImpl.class);
	
	@Resource
	private MktTeamDao mktTeamDao;
	
	public int saveMktTeam (MktTeamVO mktTeam) {
		return mktTeamDao.saveMktTeam(mktTeam);
	}
	public int batchInsertMktTeams (Map<String, Object> map){
		return mktTeamDao.batchInsertMktTeams(map);
	}
	public int updateMktTeam (MktTeamVO mktTeam, SysUserVO user){
		mktTeam.setUpdateBy(user.getId());
		mktTeam.setUpdateTime(new Date());
		return mktTeamDao.updateMktTeam(mktTeam);
	}
	public int batchUpdateMktTeamsToDiffVals (Map<String, Object> map){
		return mktTeamDao.batchUpdateMktTeamsToDiffVals(map);
	}
	public int batchUpdateMktTeamsToSameVals (Map<String, Object> map){
		return mktTeamDao.batchUpdateMktTeamsToSameVals(map);
	}
	public int deleteMktTeamById (Integer id){
		return mktTeamDao.deleteMktTeamById(id);
	}
	public int batchDeleteMktTeamByIds (List<Integer> ids){
		return mktTeamDao.batchDeleteMktTeamByIds(ids);
	}
	public MktTeamVO getMktTeamById(Integer id){
		return mktTeamDao.getMktTeamById(id);
	}
	public List<MktTeamVO> getMktTeams (MktTeamVO mktTeam){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date reportDate = mktTeam.getDateTime(); // 传入的月份
		 //获取某个月第一天：
        Calendar c = Calendar.getInstance();    
        c.setTime(reportDate);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        Date startDate = c.getTime();
        
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.setTime(reportDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        Date endDate = ca.getTime();
        LOG.info("开始时间：" + sdf.format(startDate) + ";结束时间：" + sdf.format(endDate));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", startDate);
		paramMap.put("endDate", endDate);  
        
        
		return mktTeamDao.getMktTeams(paramMap);
	}
	
	public List<Map<String, Object>>  getMktTeamsMap (MktTeamVO mktTeam){
		return mktTeamDao.getMktTeamsMap(mktTeam);
	}
	
	@Override
	public List<SysUserVO> getSupportUserByTeamId(Integer teamId) {
		return mktTeamDao.getSupportUserByTeamId(teamId);
	}
	@Override
	public int deleteSupportByTeamIdAndUserId(Map<String, Object> paramMap) {
		return mktTeamDao.deleteSupportByTeamIdAndUserId(paramMap);
	}
	
	@Override
	public int saveSupporter(List<SysUserVO> supportList, SysUserVO user, Integer teamId) {
		// 将原数据删除
		mktTeamDao.deleteSupportByTeamId(teamId);
		// 添加新数据
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (SysUserVO u : supportList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("teamId", teamId);
			map.put("supporterId", u.getId());
			map.put("insertBy", user.getId());
			map.put("insertTime", new Date());
			list.add(map);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("list", list);
		// 批量添加
		if (list!=null && list.size()>0) {
			return mktTeamDao.batchInsertMktTeamSupport(paramMap);
		} else {
			return 1;
		}
	}
	@Override
	public List<SysUserVO> getNoSupportCurrentTeamUser(SysUserVO user) {
		return mktTeamDao.getNoSupportCurrentTeamUser(user);
	}
	
	@Override
	public long getMktTeamSupportCount(Integer teamId) {
		return mktTeamDao.getMktTeamSupportCount(teamId);
	}
	
	
}

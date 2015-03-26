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

package com.yazuo.erp.system.service.impl;

import java.util.*;

import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @author erp team
 * @date 
 */
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.system.service.SysWhiteListService;
import com.yazuo.util.EmailUtil;

@Service
public class SysWhiteListServiceImpl implements SysWhiteListService {
	
	@Resource private SysWhiteListDao sysWhiteListDao;
	@Resource private EmailUtil emailUtil;
	
	public int saveSysWhiteList (List<SysWhiteListVO> list) {
		return sysWhiteListDao.saveSysWhiteList(list);
	}
	
	public int updateSysWhiteList (SysWhiteListVO entity){
		int row =  sysWhiteListDao.updateSysWhiteList(entity);
		//以下是测试代码， 测试 期望： 当email发送失败的时候业务不回滚
//		String context = "<div class=\"app-inner-l fn-left\"><a class=\"logo-l\" href=\"http://localhost:8080/yazuo_erp/\">雅座ERP</a></div>";
//		String[] contacts = new String[]{"songfuyu@yazuo.com"};
//		try {
//			//发送邮件， 有异常不处理
//			emailUtil.sendEmailAfterMerchantOnline("fff@dd.com", context, contacts);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return row;
	}
	
	public int deleteSysWhiteList(Integer id){
		return sysWhiteListDao.deleteSysWhiteList(id);
	}
	
	public SysWhiteListVO getSysWhiteListById(Integer id){
		return sysWhiteListDao.getSysWhiteListById(id);
	}

	public List<SysWhiteListVO> getSysWhiteLists(SysWhiteListVO sysWhiteListVO){

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", sysWhiteListVO.getUserId());
		return sysWhiteListDao.getSysWhiteLists(map);
	}

	public List<Map<String, Object>>  getSysWhiteListsMap(Map<String, Object> map){
		return sysWhiteListDao.getSysWhiteListsMap(map);
	}
	
	public List<Map<String, Object>> getAllSysWhiteLists(){
		return sysWhiteListDao.getAllSysWhiteLists();
	}

}

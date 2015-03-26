/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2013-11-29
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.erp.demo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.erp.demo.service.RunScriptService;
import com.yazuo.erp.demo.vo.SQLAdapter;
import com.yazuo.erp.system.dao.SysUserDao;

/**
 * @InterfaceName: DemoServiceImpl
 * @Description: 处理demo逻辑的实现
 */
@Service
public class RunSqlScriptServiceImpl implements RunScriptService {

	@Resource SysUserDao sysUserDao;
	private static final Log LOG = LogFactory.getLog(RunSqlScriptServiceImpl.class);
	
	@Override
	public List<Map<String, String>> updateSql(SQLAdapter sql) {
		List<Map<String, String>> runSqlScript = sysUserDao.runSqlScript(sql);
		LOG.info("脚本执行结果: ");
		for (Map<String, String> map : runSqlScript) {
			LOG.info(map);
		}
		return runSqlScript;
	}
}

/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.demo.service.RunScriptService;
import com.yazuo.erp.demo.vo.SQLAdapter;

/**
 * @author song
 * @date 2014-9-18 下午2:58:22
 */
public class RunScriptTest extends JUnitDaoBaseWithTrans {

	@Resource
	private RunScriptService runScriptService;

	@Test
	@Rollback(false)
	public void testRunScript() throws SQLException, IOException {
		String script = "select * from sys.sys_user ";
		 script = "update fes.fes_remark set remark = '55' where id = 51";
		this.runScriptService.updateSql(new SQLAdapter(script));
	}
}

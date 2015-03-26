package system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.controller.SysGroupController;
import com.yazuo.erp.system.dao.SysWhiteListDao;
import com.yazuo.erp.system.service.SysGroupService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.system.vo.SysWhiteListVO;

import fes.service.FesOnlineProcessServiceTest;

public class SysUserServiceImplTest extends JUnitDaoBaseWithTrans {

	@Resource
	private SysWhiteListDao sysWhiteListDao;
	@Resource
	private SysUserService userService;
	@Resource
	private SysGroupService sysGroupService;
	@Resource
	private SysGroupController sysGroupController;

	private static final Log LOG = LogFactory.getLog(FesOnlineProcessServiceTest.class);
	/**
	 * 测试mac地址数据库中唯一
	 */
	@Test
	public void getSysWhiteLists() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mac", "A0-1D-48-FC-5C-7F");
		List<SysWhiteListVO> list = sysWhiteListDao.getSysWhiteLists(map);
		if(list!=null&&list.size()>1){//查出来多于一个
			
		}else if(list.size()==1){
			SysWhiteListVO sysWhiteListVO = list.get(0);
			
		}
		this.printJson(list.size());
	}
	@Test
	public void getSysUserByTel() {
		SysUserVO sysUserVO = userService.getSysUserByTel("18340490116");
		this.printJson(sysUserVO);
	}
	/**
	 * 查找下属
	 * @param map{}
	 */
	@Test
	public void getSubordinateEmployees() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.PAGE_NUMBER, 1);
		map.put(Constant.PAGE_SIZE, 10);
		map.put("baseUserId", 109);//谁的下属
		map.put("subUserName", "13529999999");//下属的名字
//		String desc = "我的主页, 我的下属";
//		this.genTestReport(map, "group/getSubordinateEmployees", desc);
		JsonResult subordinateEmployees = sysGroupController.getSubordinateEmployees(map);
		this.printJson(subordinateEmployees);
	}
	@Test
	@Ignore
	public void testVerifyPassword() {
		Boolean result = userService.toVerifyPassword("aaa", "aaa");
		// 因为有AOP，所以结果应该为false
		System.out.println(result);
	}
}

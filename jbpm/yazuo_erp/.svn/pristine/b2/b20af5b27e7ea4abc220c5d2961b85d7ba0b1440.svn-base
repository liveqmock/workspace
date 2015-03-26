package system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import base.JUnitDaoBase;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.dao.SysRoleDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.vo.SysRoleVO;

public class SysRoleDaoTest extends JUnitDaoBase {

	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysUserDao sysUserDao;
	
	@Test
	public void testSaveGroupManagersRelation() {
		List<Map<String, Object>> managerList = new ArrayList<Map<String,Object>>();
			for(int i=0; i<3; i++){
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId",1);
				params.put("insertBy", 1);
				params.put("insertTime", new Date());
				params.put("baseUserId", 1);
				params.put("groupId", 5);
				params.put("managerType", Constant.MANAGER_MINUS__USER);
				managerList.add(params);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", managerList);
		Assert.assertNotNull(this.sysUserDao.saveGroupManagersRelation(map));
	}
	@Test
	public void testSaveSysRole() {
		SysRoleVO vo = new SysRoleVO();
		vo.setInsertBy(1);
//		vo.setInsertTime(new Date().toString());
		vo.setUpdateBy(1);
//		vo.setUpdateTime(new Date().toString());
		vo.setIsEnable("1");
		vo.setRemark("1111111");
		vo.setRoleName("tttt");
		Assert.assertNotNull(this.sysRoleDao.saveSysRole(vo));
	}

	@Test
	public void testUpdateSysRole() {
		SysRoleVO vo = new SysRoleVO();
		vo.setInsertBy(1);
//		vo.setInsertTime(new Date().toString());
		vo.setUpdateBy(1);
//		vo.setUpdateTime(new Date().toString());
		vo.setIsEnable("1");
		vo.setRemark("1111111");
		vo.setRoleName("admin555");
		vo.setId(2);
		Assert.assertNotNull(this.sysRoleDao.updateSysRole(vo));
	}

	@Test
	public void testDeleteSysRole() {
		Assert.assertNotNull(this.sysRoleDao.deleteSysRole(4));
	}

	@Test
	public void testGetSysRoleById() {
		Assert.assertNotNull(this.sysRoleDao.getSysRoleById(2));
	}

	@Test
	public void testGetSysRoles() {
		List<SysRoleVO> list = this.sysRoleDao.getSysRoles();
		for (SysRoleVO sysRoleVO : list) {
			System.out.println(sysRoleVO.getId());
		}
	}
	@Test
	public void testGetAll() {
//		List<Map> list = this.sysRoleDao.getAllSysRoles();
//		for (Map map : list) {
//			System.out.println("map print   " +  map.get("id"));
//		}
	}
}

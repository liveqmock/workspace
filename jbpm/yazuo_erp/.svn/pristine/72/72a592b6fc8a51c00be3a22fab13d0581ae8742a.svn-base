package system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.controller.SysUserController;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;

public class SysResourceDaoTest extends JUnitDaoBaseWithTrans {

	@Resource
	private SysResourceDao sysResourceDao;
	@Resource
	private SysUserController sysUserController;
	
	@Test
	@Rollback(false)
	public void testSaveSysResource() {
		for (int i = 0; i < 9; i++) {
			SysResourceVO vo = new SysResourceVO();
			vo.setInsertBy(1);
			vo.setInsertTime(new Date());
			vo.setUpdateBy(1);
			vo.setUpdateTime(new Date());
			vo.setParentId(0);
			vo.setParentTreeCode("001");
			vo.setResourceName("培训考试");
			vo.setResourceType("1");
			vo.setResourceUrl("http://localhost:8080/test"+i);
			vo.setSort(1);
			vo.setTreeCode("00100"+i);
			Assert.assertNotNull(this.sysResourceDao.saveSysResource(vo));
		}
	}

	@Test
	public void testUpdateSysResource() {
		SysResourceVO vo = new SysResourceVO();
		vo.setInsertBy(1);
		vo.setInsertTime(new Date());
		vo.setUpdateBy(1);
		vo.setUpdateTime(new Date());
		vo.setParentId(0);
		vo.setParentTreeCode("001");
		vo.setResourceName("培训考试");
		vo.setResourceType("1");
		vo.setResourceUrl("http://localhost:8080/test");
		vo.setSort(1);
		vo.setTreeCode("001001");
		Assert.assertNotNull(this.sysResourceDao.updateSysResource(vo));
	}

	@Test
	public void testDeleteSysResource() {
		Assert.assertNotNull(this.sysResourceDao.deleteSysResource(1));
	}

	@Test
	public void testGetSysResourceById() {
		SysResourceVO sysResourceVO = this.sysResourceDao.getSysResourceById(1);
		System.out.println(sysResourceVO);
		System.out.println(sysResourceVO);
		Assert.assertNotNull(sysResourceVO);
	}

	@Test
	public void testGetSysResources() {
	    List<Map<String, Object>> logs = sysResourceDao.getAllSysResources();
		for (Map<String, Object> map : logs) {
			System.out.print(map.get("id")+"  ");
		}
	}
	@Test
	public void getSysResourcesByLevel() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 1; i < 4; i++) {
			System.out.println("begin");
			map.put("level", i);
			map.put("sortColumns", "sort");
			List<SysResourceVO> list = this.sysResourceDao.getSysResourcesByLevel(map);
			for (SysResourceVO sysResourceVO : list) {
				System.out.print(sysResourceVO+"  ");
			}
			System.out.println("");
		}
	}
}

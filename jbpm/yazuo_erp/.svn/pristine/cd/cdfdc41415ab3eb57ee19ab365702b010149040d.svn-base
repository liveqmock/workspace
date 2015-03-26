package system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.vo.SysResourceVO;

public class PageHelperTest extends JUnitDaoBaseWithTrans {

	@Resource
	private SysResourceDao sysResourceDao;

	@Resource
	private SqlSessionFactory sqlSessionFactory;
	@Test
	public void testPageHelperByStartPage() throws Exception {
	    //不进行count查询，第三个参数设为false
	    PageHelper.startPage(1, 5, true);
	    //返回结果是Page<SysResourceVO>     
	    //该对象除了包含返回结果外，还包含了分页信息，可以直接按List使用
	    List<Map<String, Object>> list = (List<Map<String, Object>>)sysResourceDao.getAllSysResources();
		for (Map<String, Object> map : list) {
			System.out.print(map.get("id")+"  ");
			System.out.print(map.get("resource_name")+"  ");
		}
	    Assert.assertEquals(5, list.size());

	    //当第三个参数没有或者为true的时候，进行count查询
	    PageHelper.startPage(1, 5);
	    //返回结果是Page<SysResourceVO>     
	    //该对象除了包含返回结果外，还包含了分页信息，可以直接按List使用
	    Page<Map<String, Object>> pageList = (Page<Map<String, Object>>) sysResourceDao.getAllSysResources();

		for (Map<String, Object> map : pageList) {
			System.out.println("page print");
			System.out.print(map.get("id")+"  ");
			System.out.print(map.get("resource_name")+"  ");
		}
		System.out.println("pageList.getTotal()= "+pageList.getTotal());
	    Assert.assertEquals(5, pageList.size());

	    //进行count查询，返回结果total>0
	    Assert.assertTrue(pageList.getTotal() > 0);
	}

	@Test
	public void testPageHelperByRowbounds() throws Exception {
	    //使用RowBounds方式，不需要PageHelper.startPage
	    //RowBounds方式默认不进行count查询
	    //返回结果是Page<SysResourceVO>
	    //该对象除了包含返回结果外，还包含了分页信息，可以直接按List使用
	    List<Map<String, Object>> logs = sysResourceDao.getAllSysResources();
	    Assert.assertEquals(5, logs.size());
	    //这里进行了强制转换，实际上并没有必要
	    Page<Map<String, Object>> logs2 = (Page<Map<String, Object>>) sysResourceDao.getAllSysResources();
	    Assert.assertEquals(5, logs2.size());
	}

	@Test
	public void testPageHelperByNamespaceAndRowBounds() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
	    //没有RowBounds不进行分页
	    List<SysResourceVO> logs = sqlSession.selectList("findSysLoginLog2");
	    Assert.assertEquals(5, logs.size());

	    //使用RowBounds分页
	    List<SysResourceVO> logs2 = sqlSession
	            .selectList("findSysLoginLog2",null,new RowBounds(0,5));
	    Assert.assertEquals(5, logs2.size());
	}
}

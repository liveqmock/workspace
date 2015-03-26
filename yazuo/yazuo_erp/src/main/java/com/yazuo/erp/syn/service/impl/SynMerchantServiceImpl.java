/**
 * @Description Copyright Copyright (c) 2014 Company 雅座在线（北京）科技发展有限公司 author date description
 *              —————————————————————————————————————————————
 */

package com.yazuo.erp.syn.service.impl;

import static com.yazuo.erp.base.Constant.DropDownList.children;
import static com.yazuo.erp.base.Constant.DropDownList.firstOption;
import static com.yazuo.erp.base.Constant.DropDownList.isSelected;
import static com.yazuo.erp.base.Constant.DropDownList.pValue;
import static com.yazuo.erp.base.Constant.DropDownList.text;
import static com.yazuo.erp.base.Constant.DropDownList.value;
import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.PathConfig;
import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.minierp.service.BrandsService;
import com.yazuo.erp.minierp.vo.BrandsVO;
import com.yazuo.erp.mkt.service.MktBrandInterviewService;
import com.yazuo.erp.mkt.service.MktFormerSalesMerchantService;
import com.yazuo.erp.syn.dao.SynMerchantBrandDao;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.ComplexSynMerchantVO;
import com.yazuo.erp.syn.vo.ExternalMerchantInfoVO;
import com.yazuo.erp.syn.vo.SynMerchantBrandVO;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysAttachmentDao;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysSalesmanMerchantDao;
import com.yazuo.erp.system.dao.SysToDoListDao;
import com.yazuo.erp.system.dao.SysUserMerchantDao;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysSalesmanMerchantService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysSalesmanMerchantVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.external.account.dao.MerchantDao;
import com.yazuo.external.account.service.MerchantService;
import com.yazuo.external.account.vo.MerchantVO;
import com.yazuo.util.DateUtil;

/**
 * @author erp team
 * @date
 */

@Service
public class SynMerchantServiceImpl implements SynMerchantService {

	private static final Log LOG = LogFactory.getLog(SynMerchantServiceImpl.class);
	@Resource
	private SynMerchantDao synMerchantDao;
	@Resource
	private MerchantService merchantService;

	@Resource
	private SynMerchantBrandDao synMerchantBrandDao;

	@Resource
	private SysSalesmanMerchantDao sysSalesmanMerchantDao;

	@Resource
	private SysToDoListDao sysToDoListDao;

	@Resource
	private BrandsService brandsService;

	@Resource
	private SysAttachmentDao sysAttachmentDao;

	@Resource
	private SysUserMerchantDao sysUserMerchantDao;
	@Resource
	private SysResourceDao sysResourceDao;

	@Resource
	private MerchantDao merchantDao;

	@Value("${miniERPLogoTempPath}")
	private String miniERPLogoTempPath;

	@Value("${merchantLogoPath}")
	private String merchantLogoPath;

	@Value("${merchantLogoTempPath}")
	private String merchantLogoTempPath;
	
	@Override
	public List<Object> getCascateSelectMerchants(BesRequirementVO besRequirementVO){
		Integer merchantId = besRequirementVO.getMerchantId();
		List<Map<String, Object>> merchantsOfBrand = this.merchantDao.getMerchantsOfBrand(merchantId);
		if(CollectionUtils.isEmpty(merchantsOfBrand)) return new ArrayList<Object>();
		Map<String, Object> merchant = merchantsOfBrand.get(0);
		String firstParent = merchant.get("parent").toString();
		assertEquals("第一条记录商户的parent应该为0", "0", firstParent);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(isSelected, "0");
		result.put(value, merchant.get("merchant_id"));
		result.put(pValue, firstParent);
		result.put(children, new LinkedList<Object>());
		this.getChildRenNode(result, merchantsOfBrand, besRequirementVO);
		return (List<Object>)result.get(children);
	}
	
	/**
	 * 递归
	 * @param result
	 * @param merchantsOfBrand  内存中所有的商户
	 */
	private void getChildRenNode(final Map<String, Object> result, final List<Map<String, Object>> merchantsOfBrand
			, final BesRequirementVO besRequirementVO) {
		
		final Integer storeId = besRequirementVO.getStoreId(); //编辑的时候用于判断 选中状态
		final Collection<?> selected = CollectionUtils.select(merchantsOfBrand, new Predicate() { //筛选满足条件的
			public boolean evaluate(Object object) {
				Map<String, Object> map = (Map<String, Object>) object;
				return new EqualsBuilder().append(result.get(value),map.get("parent")).isEquals();
			}
		});
		final List<Object> list = (List<Object>)result.get(children);
		//有子集显示请选择
//		if(CollectionUtils.isNotEmpty(selected)){
//			list.add(new HashMap<String, Object>() {
//				{
//					this.put(isSelected, "0");
//					this.put(value, 0);
//					this.put(text, firstOption);//"请选择"
//					this.put(children,  new LinkedList<Object>());
//				}
//			});
//		} 
		CollectionUtils.forAllDo(selected, new Closure() {
			public void execute(Object input) {
				final Map<String, Object> dbMap = (Map<String, Object>) input;
				Map<String, Object> parameMap = new HashMap<String, Object>();
				parameMap.put(isSelected, storeId!=null && storeId.equals(dbMap.get("merchant_id"))? "1": "0");
				parameMap.put(value, dbMap.get("merchant_id"));
				parameMap.put(pValue, dbMap.get("parent"));
				parameMap.put(text, dbMap.get("merchant_name"));
				parameMap.put(children,  new LinkedList<Object>());
				getChildRenNode(parameMap, merchantsOfBrand, besRequirementVO);
				list.add(parameMap);
				//更新请选择的parent
				((Map<String, Object>)list.get(0)).put(pValue, dbMap.get("parent"));
			}
		});
	}

	public int saveSynMerchant(Map<String, Object> map) {
		// 1、添加商戶
		SynMerchantVO synVo = getSynMerVO(map);
		synMerchantDao.saveSynMerchant(synVo);

		// 2 、添加销售-商户关系表
		map.put("userId", Constant.DEFAULT_ADD_USER);
		SysSalesmanMerchantVO synSalesmanMerchantVO = getSysSalesmanMerchantVo(map);
		sysSalesmanMerchantDao.saveSysSalesmanMerchant(synSalesmanMerchantVO);

		// 3、创建待办事项 
		//2014-11-14 11:12:36 逻辑变为 给后端客服发送待办事项
		SysToDoListVO sysToDoListVO = getSysToDoListVo(map);
		//查找所有需要创建待办事项的用户
		SysResourceVO sysResourceVO = new SysResourceVO();
		
		//2014-11-17 17:06:39改为， 为所有的有后端客服的用户创建待办事项
		sysResourceVO.setRemark("end_custom_service"); //由前台传过来
		List<SysUserVO> listToDoUsers = sysResourceDao.getAllUsersByResourceCode(sysResourceVO);
		for (SysUserVO sysUserVO : listToDoUsers) {
			sysToDoListVO.setUserId(sysUserVO.getId());
			sysToDoListDao.saveSysToDoList(sysToDoListVO);
		}
		return 1;
	}

	/**
	 * @param map
	 * @return
	 * @return SynMerchantVO
	 * @throws
	 */
	private SynMerchantVO getSynMerVO(Map<String, Object> map) {
		SynMerchantVO synMerchant = new SynMerchantVO();
		synMerchant.setMerchantId((Integer) map.get("merchant_id"));
		synMerchant.setMerchantName((String) map.get("merchant_name"));
		synMerchant.setBrand((String) map.get("brand"));
		synMerchant.setMerchantNo((String) map.get("merchant_no"));
		synMerchant.setIsFaceShop((Boolean) map.get("is_face_shop"));
		synMerchant.setAdColumn((String) map.get("ad_column"));
		synMerchant.setPromptBar((String) map.get("prompt_bar"));
		synMerchant.setParent((Integer) map.get("parent"));
		synMerchant.setStatus((Integer) map.get("status"));
		synMerchant.setBrandShortPinyin((String) map.get("brand_short_pinyin"));
		synMerchant.setBrandId((Integer) map.get("brand_id"));
		synMerchant.setThirdpartyMerchantNo((String) map.get("thirdparty_merchant_no"));
		Integer serviceYear = (Integer) map.get("service_year");
		if (null != serviceYear) {
			synMerchant.setServiceYear(new BigDecimal(serviceYear));
		}
		Integer freeMonth = (Integer) map.get("free_month");
		if (null != freeMonth) {
			synMerchant.setFreeMonth(new BigDecimal(freeMonth));
		}
		synMerchant.setMerchantStatus("2");
		synMerchant.setUpdateTime(new Date());
		return synMerchant;
	}

	public int batchInsertSynMerchants(Map<String, Object> map) {
		return synMerchantDao.batchInsertSynMerchants(map);
	}
	
	@Override
	public int updateSynMerchant(SynMerchantVO synMerchantVO){
		return this.synMerchantDao.updateSynMerchant(synMerchantVO);
	}

	public int updateSynMerchant(Map<String, Object> map) throws ParseException {
		// 1、获得修改的VO
		SynMerchantVO updateSynMerchantVO = getUpdateSynMerchantVO(map);
		synMerchantDao.updateSynMerchant(updateSynMerchantVO);

		if (null != map.get("user_id")) {
			// 2、修改销售-商户关系表
			SysSalesmanMerchantVO vo = getUpdateSysSalesmanMerchantVo(map);
			sysSalesmanMerchantDao.updateSysSalesmanMerchant(vo);

			// 3、查询商户的待办事项
			List<SysToDoListVO> sysToDoLists = queryToDoList(map);
			if (null != sysToDoLists && sysToDoLists.size() > 0) {
				SysToDoListVO entity = sysToDoLists.get(0);
				entity.setUserId((Integer) map.get("user_id"));
				entity.setUpdateBy((Integer) map.get("userId"));
				entity.setUpdateTime(new Date());

				sysToDoListDao.updateSysToDoList(entity);
			}
		}

		return 1;
	}

	private List<SysToDoListVO> queryToDoList(Map<String, Object> map) {
		SysToDoListVO sysToDoList = new SysToDoListVO();
		sysToDoList.setMerchantId((Integer) map.get("merchant_id"));
		sysToDoList.setItemType("03");
		sysToDoList.setItemStatus("0");
		sysToDoList.setIsEnable("1");
		List<SysToDoListVO> sysToDoLists = sysToDoListDao.getSysToDoLists(sysToDoList);
		return sysToDoLists;
	}

	/**
	 * @param map
	 * @return
	 * @return SysSalesmanMerchantVO
	 * @throws
	 */
	private SysSalesmanMerchantVO getUpdateSysSalesmanMerchantVo(Map<String, Object> map) {

		SysSalesmanMerchantVO synsSalesmanMerchantVO = new SysSalesmanMerchantVO();
		synsSalesmanMerchantVO.setMerchantId((Integer) map.get("merchant_id"));

		List<SysSalesmanMerchantVO> list = sysSalesmanMerchantDao.getSysSalesmanMerchants(synsSalesmanMerchantVO);
		SysSalesmanMerchantVO entity = new SysSalesmanMerchantVO();
		if (null != list && list.size() > 0) {
			entity = list.get(0);
		}
		entity.setUserId((Integer) map.get("user_id"));
		entity.setInsertBy((Integer) map.get("userId"));
		entity.setInsertTime(new Date());
		return entity;
	}

	/**
	 * @throws ParseException
	 * @Description TODO
	 * @param map
	 * @return
	 * @return SynMerchantVO
	 * @throws
	 */
	private SynMerchantVO getUpdateSynMerchantVO(Map<String, Object> map) throws ParseException {
		SynMerchantVO entity = synMerchantDao.getSynMerchantById((Integer) map.get("merchant_id"));
		Assert.notNull(entity, "该商户不存在");
		SynMerchantVO synMerchant = new SynMerchantVO();
		synMerchant.setMerchantId((Integer) map.get("merchant_id"));
		synMerchant.setMerchantName((String) map.get("merchant_name"));
		synMerchant.setBrand((String) map.get("brand"));
		synMerchant.setMerchantNo((String) map.get("merchant_no"));
		synMerchant.setIsFaceShop((Boolean) map.get("is_face_shop"));
		synMerchant.setAdColumn((String) map.get("ad_column"));
		synMerchant.setPromptBar((String) map.get("prompt_bar"));
		synMerchant.setParent((Integer) map.get("parent"));
		synMerchant.setStatus((Integer) map.get("status"));
		synMerchant.setBrandShortPinyin((String) map.get("brand_short_pinyin"));
		synMerchant.setBrandId((Integer) map.get("brand_id"));
		synMerchant.setThirdpartyMerchantNo((String) map.get("thirdparty_merchant_no"));
		Integer serviceYear = (Integer) map.get("service_year");
		if (null != serviceYear) {
			synMerchant.setServiceYear(new BigDecimal(serviceYear));
		}

		Integer freeMonth = (Integer) map.get("free_month");
		if (null != freeMonth) {
			synMerchant.setFreeMonth(new BigDecimal(freeMonth));
		}
		synMerchant.setUpdateTime(new Date());
		synMerchant.setMerchantStatus((String) map.get("merchant_status"));
		if (null != map.get("service_start_time")) {
			String serviceStartTime = (String) map.get("service_start_time");
			synMerchant.setServiceStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(serviceStartTime));
		}

		if (null != map.get("service_end_time")) {
			String serviceEndTime = (String) map.get("service_end_time");
			synMerchant.setServiceEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(serviceEndTime));
		}

		if (StringUtils.equals(entity.getMerchantStatus(), "1")) {
			if (null != freeMonth || null != serviceYear) {
				Date serviceStartTime = entity.getServiceStartTime();
				serviceYear = (null != serviceYear) ? serviceYear : entity.getServiceYear().intValue();
				freeMonth = (null != freeMonth) ? freeMonth : entity.getFreeMonth().intValue();
				if (null != serviceStartTime) {
					String serviceEndTime = DateUtil.formermonth(new SimpleDateFormat("yyyy-MM-dd").format(serviceStartTime),
							serviceYear * 12 + freeMonth);
					synMerchant.setServiceEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(serviceEndTime));
				}
			}

		}

		return synMerchant;
	}

	public int batchUpdateSynMerchantsToDiffVals(Map<String, Object> map) {
		return synMerchantDao.batchUpdateSynMerchantsToDiffVals(map);
	}

	public int batchUpdateSynMerchantsToSameVals(Map<String, Object> map) {
		return synMerchantDao.batchUpdateSynMerchantsToSameVals(map);
	}

	public int deleteSynMerchantById(Integer id) {
		return synMerchantDao.deleteSynMerchantById(id);
	}

	public int batchDeleteSynMerchantByIds(List<Integer> ids) {
		return synMerchantDao.batchDeleteSynMerchantByIds(ids);
	}

	public SynMerchantVO getSynMerchantById(Integer id) {
		return synMerchantDao.getSynMerchantById(id);
	}

	public List<SynMerchantVO> getSynMerchants(SynMerchantVO synMerchant) {
		//如果是一键上线查询
		synMerchant.setIsAment("1");
		return synMerchantDao.getSynMerchants(synMerchant);
	}

	public List<Map<String, Object>> getSynMerchantsMap(SynMerchantVO synMerchant) {
		return synMerchantDao.getSynMerchantsMap(synMerchant);
	}

	/**
	 * @Title getSynMerchantByUserId
	 * @Description 根据用户ID查询自己所管理的商户的列表（前端咨询人员-商户）
	 * 	    2014-12-24 14:19:19 过滤掉 不可用的商户  
	 * @param userId
	 * @return
	 * @see com.yazuo.erp.syn.service.SynMerchantService#getSynMerchantByUserId(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> getSynMerchantByUserId(Integer userId) {
		List<Map<String, Object>> list = synMerchantDao.getSynMerchantByUserId(userId);
		return list;
	}

	/**
	 * @Title getSynMerchantCount
	 * @Description
	 * @return
	 * @see com.yazuo.erp.syn.service.SynMerchantService#getSynMerchantCount()
	 */
	@Override
	public long getSynMerchantCount() {
		return synMerchantDao.getSynMerchantCount();
	}

	/**
	 * 根据商户名称查找所有的商户
	 * @param merchantName
	 */
	@Override
	public List<Map<String, Object>> getSynMerchantInfo(String merchantName) {
		return synMerchantDao.getSynMerchantInfo(merchantName);
	}

	public List<Map<String, Object>> getSynMerchantInfoByUserId(Map<String, Object> map) {
		return synMerchantDao.getSynMerchantInfoByUserId(map);
	}

	 @Async  //用此方法登录完成的时候异步调用，产生商户信息第一页的缓存
	 @Override
	public void getComplexSynMerchantsForCache(final Integer userId){
		this.getComplexSynMerchantsForCache(new HashMap<String, Object>() {
			{
				put("userId", userId);
				put("businessTypes", "2");
				put(Constant.PAGE_NUMBER, 1);
				put(Constant.PAGE_SIZE, 10);
			}
		}, new SysUserVO() {
			{
				setId(userId);
			}
		});
	}


	@Override
	public Map<Integer, String> getMerchantNames(List<Integer> merchantIds) {
		List<Map<String,Object>> merchants = this.merchantDao.getMerchantsInfo(merchantIds, null);

		Map<Integer, String> result = new HashMap<Integer, String>();
		for (Map<String, Object> merchant : merchants) {
			result.put((Integer)merchant.get("merchant_id"), (String)merchant.get("merchant_name"));
		}
		return result;
	}

	@Override
	public Map<Integer, String> getMerchantNamesFromErp(List<Integer> merchantIds) {
		if (CollectionUtils.isEmpty(merchantIds)) {
			return Collections.emptyMap();
		}
		List<SynMerchantVO> merchantVOs = this.synMerchantDao.getMerchantNamesFromErp(merchantIds);
		Map<Integer, String> result = new HashMap<Integer, String>();
		for (SynMerchantVO merchant : merchantVOs) {
			result.put(merchant.getMerchantId(), merchant.getMerchantName());
		}
		return result;
	}

	@Override
	public List<Integer> getAllAvailMerchantIds() {
		return this.synMerchantDao.getAllAvailMerchantIds();
	}

	/**
	 * 返回前端我的主页查询出来的所有商户信息
	 */
	@Override
	@Deprecated //缺陷, 查询条件太多, 所有的查询条件都应作为缓存的key拼接
	@CachePut(value="merchantCache", key="'getComplexSynMerchants'+ #inputMap['pageNumber'] + #inputMap['userId']+" +
		"#inputMap['merchantName']+ #inputMap['merchantStatusType']+ #inputMap['merchantStatus']")
	public List<ComplexSynMerchantVO> getComplexSynMerchantsForCache(Map<String, Object> inputMap, SysUserVO sessionUser){
		LOG.info("get data from database!");
		int pageNumber = (Integer) inputMap.get(Constant.PAGE_NUMBER);
		int pageSize = (Integer) inputMap.get(Constant.PAGE_SIZE);
		PageHelper.startPage(pageNumber, pageSize, true, true);
		inputMap.put("sessionUserId", sessionUser.getId());
		return synMerchantDao.getComplexSynMerchants(inputMap);
	}
	
	/**
	 * 返回前端我的主页查询出来的所有商户信息
	 */
	@Override
	@Cacheable(value="merchantCache", key="'getComplexSynMerchants'+ #inputMap['pageNumber'] + #inputMap['userId']+ " +
		"#inputMap['merchantName']+ #inputMap['merchantStatusType']+ #inputMap['merchantStatus']")
	public List<ComplexSynMerchantVO> getComplexSynMerchants(Map<String, Object> inputMap, SysUserVO sessionUser){
		System.out.println("invoked");
		int pageNumber = (Integer) inputMap.get(Constant.PAGE_NUMBER);
		int pageSize = (Integer) inputMap.get(Constant.PAGE_SIZE);
		PageHelper.startPage(pageNumber, pageSize, true, true);
		inputMap.put("sessionUserId", sessionUser.getId());
		return synMerchantDao.getComplexSynMerchants(inputMap);
	}

	/**
	 * ERP上线前人工匹配
	 * 
	 * @throws ParseException
	 */
	@Override
	public boolean saveSynMerchantForOnlineBefore(Map<String, Object> map, HttpServletRequest request) throws Exception {
		Assert.notNull(map.get("merchant_status"), "上线状态不能为空");
		Assert.hasText((String) map.get("merchant_status"), "上线状态不能为空");
		Assert.notNull(map.get("merchant_id"), "商户ID不能为空");
		Assert.notNull(map.get("mini_merchant_id"), "miniErp商户ID不能为空");
		Assert.notNull(map.get("service_start_time"), "服务开始时间不能为空");
		Integer merchantId = (Integer) map.get("merchant_id");

		// 1、根据miniERP brandId 查询logo信息
		BrandsVO brandsVO = brandsService.getBrandsById((Integer) map.get("mini_merchant_id"));
		Integer id = brandsVO.getId();// 品牌Id
		String logoFileName = brandsVO.getLogoFileName();// logo名称
		String logoContentType = brandsVO.getLogoContentType();// logo类型
		Integer logoFileSize = brandsVO.getLogoFileSize();// logo大小
		Long size = null != logoFileSize ? Long.parseLong(String.valueOf(logoFileSize)) : null;
		// 2、移动logo图片到指定目录
		String newFileName = moveLogo(id, logoFileName, merchantId, request);

		// 3、创建附件
		SysAttachmentVO sysAttachmentVO = getSysAttachmentVo(merchantId, newFileName, logoFileName, size, map);
		sysAttachmentDao.saveSysAttachment(sysAttachmentVO);
		Integer attachmentId = sysAttachmentVO.getId();// 附件Id

		// 4、添加商户
		SynMerchantVO synMerchant = getSynMerchantVo(map, attachmentId);
		synMerchantDao.saveSynMerchant(synMerchant);

		// 5、添加商户-minierp商户关系表
		SynMerchantBrandVO synMerchantBrandVO = getSynMerchantBrandVo(map);
		synMerchantBrandDao.saveSynMerchantBrand(synMerchantBrandVO);

		// 6 、添加销售-商户关系表
		SysSalesmanMerchantVO synSalesmanMerchantVO = getSysSalesmanMerchantVo(map);
		sysSalesmanMerchantDao.saveSysSalesmanMerchant(synSalesmanMerchantVO);

		// 7、添加前端顾问
		if (StringUtils.equals("1", (String) map.get("merchant_status"))) {
			Assert.notNull(map.get("front_user_id"), "前端销售不能为空");
			SysUserMerchantVO sysUserMerchantVO = getSysUserMerchantVo(map);
			sysUserMerchantDao.saveSysUserMerchant(sysUserMerchantVO);
		}

		// 8、若上线状态为0-未上线，创建待办事项
		if (StringUtils.equals("2", (String) map.get("merchant_status"))) {
			SysToDoListVO sysToDoListVO = getSysToDoListVo(map);
			sysToDoListDao.saveSysToDoList(sysToDoListVO);
		}

		return true;
	}

	/**
	 * @Description TODO
	 * @param map
	 * @return
	 * @return SysUserMerchantVO
	 * @throws
	 */
	private SysUserMerchantVO getSysUserMerchantVo(Map<String, Object> map) {
		SysUserMerchantVO sysUserMerchantVO = new SysUserMerchantVO();
		sysUserMerchantVO.setUserId((Integer) map.get("front_user_id"));
		sysUserMerchantVO.setMerchantId((Integer) map.get("merchant_id"));
		sysUserMerchantVO.setInsertBy((Integer) map.get("userId"));
		sysUserMerchantVO.setInsertTime(new Date());
		return sysUserMerchantVO;
	}

	/**
	 * @Description TODO
	 * @param newFileName
	 * @param logoFileName
	 * @param logoFileSize
	 * @return
	 * @return SysAttachmentVO
	 * @throws
	 */
	private SysAttachmentVO getSysAttachmentVo(Integer merchantId, String newFileName, String logoFileName, Long logoFileSize,
			Map<String, Object> map) {
		SysAttachmentVO sysAttachmentVO = new SysAttachmentVO();
		sysAttachmentVO.setAttachmentName(newFileName);
		sysAttachmentVO.setOriginalFileName(logoFileName);
		sysAttachmentVO.setAttachmentType("4");
		sysAttachmentVO.setAttachmentSize(String.valueOf(logoFileSize));
		String suffix = logoFileName.substring(logoFileName.lastIndexOf('.') + 1, logoFileName.length());
		sysAttachmentVO.setAttachmentSuffix(suffix);
		sysAttachmentVO.setModuleType(map.get("moduleType") + "");
		sysAttachmentVO.setAttachmentPath(String.valueOf(merchantId));
		sysAttachmentVO.setIsEnable("1");
		sysAttachmentVO.setIsDownloadable("1");
		sysAttachmentVO.setInsertBy((Integer) map.get("userId"));
		sysAttachmentVO.setInsertTime(new Date());
		sysAttachmentVO.setUpdateBy((Integer) map.get("userId"));
		sysAttachmentVO.setUpdateTime(new Date());
		return sysAttachmentVO;
	}

	/**
	 * @throws IOException
	 * @Description TODO
	 * @param id
	 * @param logoFileName
	 * @param request
	 * @return void
	 * @throws
	 */
	private String moveLogo(Integer id, String logoFileName, Integer merchantId, HttpServletRequest request) throws IOException {
		String brandId = String.format("%03d", id);
		String orignPath = request.getSession().getServletContext().getRealPath(miniERPLogoTempPath) + "/" + brandId
				+ "/original/" + logoFileName;// 原路径
		String destPath = request.getSession().getServletContext().getRealPath(merchantLogoPath) + "/" + merchantId;// 目标文件夹

		File resFile = new File(orignPath);
		File distFile = new File(destPath);
		FileUtils.copyFileToDirectory(resFile, distFile, true);// 移动文件

		String endSuffix = logoFileName.substring(logoFileName.lastIndexOf('.') + 1, logoFileName.length());
		String newFileName = UUID.randomUUID().toString() + "." + endSuffix;
		renameFile(destPath, newFileName, logoFileName);// 文件重命名
		return newFileName;
	}

	public boolean renameFile(String destPath, String newFileName, String oldFileName) {
		String newFilePath = destPath + "/" + newFileName;
		File resFile = new File(destPath + "/" + oldFileName);
		File newFile = new File(newFilePath);
		return resFile.renameTo(newFile);
	}

	/**
	 * @Description 给销售创建待办事项
	 * @param map
	 * @return
	 * @return SysToDoListVO
	 * @throws
	 */
	private SysToDoListVO getSysToDoListVo(Map<String, Object> map) {
		SysToDoListVO sysToDoListVO = new SysToDoListVO();
		sysToDoListVO.setUserId((Integer) map.get("sales_user_id"));
		sysToDoListVO.setMerchantId((Integer) map.get("merchant_id"));
		sysToDoListVO.setPriorityLevelType("01");
		sysToDoListVO.setItemType("03");//商户调研
		sysToDoListVO.setItemContent("商户访谈单及通讯录信息创建，请尽快完成。");
		sysToDoListVO.setItemStatus("0");
		sysToDoListVO.setRelatedId(null);
		sysToDoListVO.setRelatedType(null);
		sysToDoListVO.setRemark("");
		sysToDoListVO.setIsEnable("1");
		sysToDoListVO.setBusinessType("1");
		sysToDoListVO.setInsertBy((Integer) map.get("userId"));
		sysToDoListVO.setInsertTime(new Date());
		sysToDoListVO.setUpdateBy((Integer) map.get("userId"));
		sysToDoListVO.setUpdateTime(new Date());
		return sysToDoListVO;
	}

	/**
	 * @Description TODO
	 * @param map
	 * @return
	 * @return SysSalesmanMerchantVO
	 * @throws
	 */
	private SysSalesmanMerchantVO getSysSalesmanMerchantVo(Map<String, Object> map) {
		SysSalesmanMerchantVO synsSalesmanMerchantVO = new SysSalesmanMerchantVO();

		synsSalesmanMerchantVO.setMerchantId((Integer) map.get("merchant_id"));
		synsSalesmanMerchantVO.setUserId((Integer) map.get("sales_user_id"));
		synsSalesmanMerchantVO.setInsertBy((Integer) map.get("userId"));
		synsSalesmanMerchantVO.setInsertTime(new Date());
		return synsSalesmanMerchantVO;
	}

	/**
	 * @Description TODO
	 * @param map
	 * @return
	 * @return SynMerchantBrandVO
	 * @throws
	 */
	private SynMerchantBrandVO getSynMerchantBrandVo(Map<String, Object> map) {
		SynMerchantBrandVO synMerchantBrandVO = new SynMerchantBrandVO();
		synMerchantBrandVO.setMerchantId((Integer) map.get("merchant_id"));
		synMerchantBrandVO.setMiniMerchantId((Integer) map.get("mini_merchant_id"));
		synMerchantBrandVO.setInsertBy((Integer) map.get("userId"));
		synMerchantBrandVO.setInsertTime(new Date());
		return synMerchantBrandVO;
	}

	private SynMerchantVO getSynMerchantVo(Map<String, Object> map, Integer attachmentId) throws ParseException {
		MerchantVO merchantVO = merchantService.getMerchantVO((Integer) map.get("merchant_id"));
		Assert.notNull(merchantVO, "商户信息不存在");
		SynMerchantVO synMerchant = getSynMerchantVO(map, attachmentId, merchantVO);
		return synMerchant;
	}

	private SynMerchantVO getSynMerchantVO(Map<String, Object> map, Integer attachmentId, MerchantVO merchantVO)
			throws ParseException {
		SynMerchantVO synMerchant = new SynMerchantVO();
		synMerchant.setMerchantId((Integer) map.get("merchant_id"));
		synMerchant.setMerchantName(merchantVO.getMerchantName());
		synMerchant.setBrand(merchantVO.getBrand());
		synMerchant.setMerchantNo(merchantVO.getMerchantNo());
		synMerchant.setIsFaceShop(merchantVO.getIsFaceShop());
		synMerchant.setAdColumn(merchantVO.getAdColumn());
		synMerchant.setPromptBar(merchantVO.getPromptBar());
		synMerchant.setParent(merchantVO.getParent());
		synMerchant.setStatus(merchantVO.getStatus());
		synMerchant.setBrandShortPinyin(merchantVO.getBrandShortPinyin());
		synMerchant.setBrandId(merchantVO.getBrandId());
		synMerchant.setThirdpartyMerchantNo(merchantVO.getThirdpartyMerchantNo());
		synMerchant.setAttachmentId(attachmentId);
		Integer serviceYear = (Integer) map.get("service_year");
		synMerchant.setServiceYear(new BigDecimal(serviceYear));
		Integer freeMonth = (Integer) map.get("free_month");
		synMerchant.setFreeMonth(new BigDecimal(freeMonth));
		String serviceStartTime = formatDate(map.get("service_start_time"));
		synMerchant.setServiceStartTime(new SimpleDateFormat("yyyy-MM-dd").parse(serviceStartTime));
		String serviceEndTime = DateUtil.formermonth(serviceStartTime, serviceYear * 12 + freeMonth);
		synMerchant.setServiceEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(serviceEndTime));
		synMerchant.setMerchantStatus((String) map.get("merchant_status"));
		synMerchant.setUpdateTime(new Date());

		return synMerchant;
	}

	// 格式化数据
	private String formatDate(Object time) {
		long second = (Long) time;
		Date date = new Date();
		date.setTime(second);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date); // 格式化
	}

	@Override
	public ExternalMerchantInfoVO getMerchantInfoByOrder(Map<String, Object> paramMap) {
		List<ExternalMerchantInfoVO> list = null;
		String isRelate = String.valueOf(paramMap.get("isRelateInterview"));
		if (isRelate.trim().equals("true")) {
			list = synMerchantDao.getMerchantInfoByOrder(paramMap);
		} else {
			list = synMerchantDao.getMerchantInfoNoneInterviewByOrder(paramMap);
		}
		ExternalMerchantInfoVO info = null;
		if (list != null && list.size() > 0) {
			info = list.get(0);
		} else {
			return null;
		}
		String onlineStatus = info.getMerchant_status();
		// 判断商户状态
		if (onlineStatus.equals("0")) { // 未上线 直接取品牌访谈里面的门店数

		} else { // 上线 调用其他接口获取门店数
			Integer brandId = Integer.parseInt(paramMap.get("merchantId").toString());
			Long count = merchantService.getMerchantFaceShopCount(brandId); // 门店数
			info.setStore_number(count + "");
		}
		return info;
	}

	@Override
	public Object uploadLogo(MultipartFile[] myfiles, Integer merchantId, HttpServletRequest request) throws IOException {
		return FileUploaderUtil.uploadFile(myfiles, "merchantLogoTempPath", null, 0, request);
	}

	@Override
	public boolean saveLogo(Map<String, Object> paramMap, HttpServletRequest request) {

		String fileName = (String) paramMap.get("fileName");// ERP生成的文件名
		Integer merchantId = (Integer) paramMap.get("merchantId");
		String originalFileName = (String) paramMap.get("originalFileName");// 实际文件名
		Long fileSize = (Long) paramMap.get("fileSize");

		// 1、移动临时文件到指定路径
		moveFile(fileName, String.valueOf(merchantId), request);

		// 2、创建附件
		SysAttachmentVO sysAttachmentVo = getSysAttachmentVo(merchantId, fileName, originalFileName, fileSize, paramMap);
		sysAttachmentDao.saveSysAttachment(sysAttachmentVo);
		Integer attachmentId = sysAttachmentVo.getId();

		// 3、修改商户
		SynMerchantVO entity = synMerchantDao.getSynMerchantById(merchantId);
		entity.setAttachmentId(attachmentId);
		entity.setUpdateTime(new Date());
		synMerchantDao.updateSynMerchant(entity);
		return true;
	}

	public boolean moveFile(String fileName, String merchantId, HttpServletRequest request) {
		String orignPath = request.getSession().getServletContext().getRealPath(merchantLogoTempPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(merchantLogoPath) + "/" + merchantId;
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件夹
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	@Override
	public boolean updateLogo(Map<String, Object> paramMap, HttpServletRequest request) {
		String fileName = (String) paramMap.get("fileName");// ERP生成的文件名
		Integer merchantId = (Integer) paramMap.get("merchantId");
		String originalFileName = (String) paramMap.get("originalFileName");// 实际文件名
		Long fileSize = (Long) paramMap.get("fileSize");
		Integer attachmentId = (Integer) paramMap.get("attachmentId");

		// 1、删除原有文件
		deleteFile(fileName, String.valueOf(merchantId), request);

		// 2、移动临时文件到指定路径
		moveFile(fileName, String.valueOf(merchantId), request);

		// 3、修改附件
		SysAttachmentVO entity = sysAttachmentDao.getSysAttachmentById(attachmentId);
		entity.setAttachmentName(fileName);
		entity.setOriginalFileName(originalFileName);
		String suffix = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.length());
		entity.setAttachmentSuffix(suffix);
		entity.setAttachmentSize(String.valueOf(fileSize));
		sysAttachmentDao.updateSysAttachment(entity);
		return true;
	}

	public boolean deleteFile(String fileName, String merchantId, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(merchantLogoPath) + "/" + merchantId;
		File destFile = new File(destPath + "/" + fileName); // 目标文件夹
		return destFile.delete();
	}

	@Override
	public Map<String, Object> getMerchantLogo(Integer merchantId) {
		Map<String, Object> map = new HashMap<String, Object>();
		SynMerchantVO entity = synMerchantDao.getSynMerchantById(merchantId);
		Assert.notNull(entity, "该商户不存在");
		Integer attachmentId = entity.getAttachmentId();
		if (null != attachmentId) {
			SysAttachmentVO attachmentVO = sysAttachmentDao.getSysAttachmentById(attachmentId);
			Assert.notNull(attachmentVO, "该附件不存在");
			String attachmentName = attachmentVO.getAttachmentName();// ERP生成的文件名
			String originalFileName = attachmentVO.getOriginalFileName();// 实际文件名
			Integer id = attachmentVO.getId();
			map.put("attachmentName", attachmentName);
			map.put("originalFileName", originalFileName);
			map.put("attachmentId", id);
			map.put("relativePath", merchantLogoPath + "/" + merchantId);
		}
		return map;
	}

	@Override
	public boolean saveSynMerchantExtend(Map<String, Object> map, HttpServletRequest request) throws Exception {
		Assert.notNull(map.get("merchant_id"), "商户ID不能为空");

		// 1、判断该商户在ERP是否已经存在，存在则报错
		Integer merchantId = (Integer) map.get("merchant_id");
		SynMerchantVO synMerchantVO = synMerchantDao.getSynMerchantById(merchantId);
		Assert.isNull(synMerchantVO, "该商户在ERP已经存在");

		// 2、不存在时，去CRM查询是否存在，查询到则添加到erp
		MerchantVO merchantVO = merchantDao.getMerchantById(merchantId);
		Assert.notNull(merchantVO, "商户信息不存在");

		// 3、上传LOGO图片时，保存logo，附件表
		Integer attachmentId = null;
		if (null != map.get("fileName")) {
			String fileName = (String) map.get("fileName");
			String originalFileName = (String) map.get("originalFileName");
			Object fileSizeObject = map.get("fileSize");
			Long fileSize = null;
			if (fileSizeObject instanceof Long) {
				fileSize = (Long) map.get("fileSize");
			} else if (fileSizeObject instanceof Integer) {
				Integer size = (Integer) map.get("fileSize");
				fileSize = Long.parseLong(size.toString());
			}
			moveFile(fileName, String.valueOf(merchantId), request);

			// 3、创建附件
			SysAttachmentVO sysAttachmentVO = getSysAttachmentVo(merchantId, fileName, originalFileName, fileSize, map);
			sysAttachmentDao.saveSysAttachment(sysAttachmentVO);
			attachmentId = sysAttachmentVO.getId();// 附件Id
		}

		// 4、添加商户
		SynMerchantVO synMerchant = getSynMerchantVO(map, attachmentId, merchantVO);
		synMerchantDao.saveSynMerchant(synMerchant);

		// 5 、添加销售-商户关系表
		SysSalesmanMerchantVO synSalesmanMerchantVO = getSysSalesmanMerchantVo(map);
		sysSalesmanMerchantDao.saveSysSalesmanMerchant(synSalesmanMerchantVO);

		// 6、添加前端顾问
		if (StringUtils.equals("1", (String) map.get("merchant_status"))) {
			Assert.notNull(map.get("front_user_id"), "前端销售不能为空");
			SysUserMerchantVO sysUserMerchantVO = getSysUserMerchantVo(map);
			sysUserMerchantDao.saveSysUserMerchant(sysUserMerchantVO);
		}

		// 7、若上线状态为0-未上线，创建待办事项
		if (StringUtils.equals("2", (String) map.get("merchant_status"))) {
			SysToDoListVO sysToDoListVO = getSysToDoListVo(map);
			sysToDoListDao.saveSysToDoList(sysToDoListVO);
		}

		return true;
	}
	/**
	 * 测试方法 从运营平台同步一条数据
	 * 需要更改  MerchantDaoImpl类中的方法 getMerchantsForSurvey() ， 得到门店的数据
	 * @deprecated 
	 */
	@Override
	public Map<String, Object> saveTestMerchant() {

		Integer merchantId = this.synMerchantDao.getMaxSynMerchantId()+1;
		Map<String, Object> merchantInfo = new HashMap<String, Object>();
        merchantInfo.put("merchant_id", merchantId);
        merchantInfo.put("merchant_name", "商户名称测试-"+merchantId);
        merchantInfo.put("brand", "brand-"+merchantId);
        merchantInfo.put("merchant_no", "brand-"+merchantId);
        merchantInfo.put("is_face_shop", false);
//        merchantInfo.put("ad_column", merchant.getAdColumn());
//        merchantInfo.put("prompt_bar", merchant.getPromptBar());
        merchantInfo.put("parent", 0);
        merchantInfo.put("status", 1);
        merchantInfo.put("brand_short_pinyin", "brand-"+merchantId);
        merchantInfo.put("brand_id", merchantId);
//        merchantInfo.put("thirdparty_merchant_no", merchant.getThirdpartyMerchantNo());
        merchantInfo.put("service_year", 1);
        merchantInfo.put("free_month", 1);
        merchantInfo.put("sales_user_id", merchantId);
        this.saveSynMerchant(merchantInfo);
        return merchantInfo;
	}
	@Resource SysToDoListService sysToDoListService;
	@Override
	public JsonResult deleteMerchantAndCloseToDo(SynMerchantVO synMerchant, SysUserVO sessionUser) {
		synMerchant.setStatus(2);//不可用
		synMerchant.setUpdateTime(new Date());
		this.synMerchantDao.updateSynMerchant(synMerchant);
		//关闭待办事项
		SysToDoListVO inputSysToDoListVO = new SysToDoListVO();
		inputSysToDoListVO.setMerchantId(synMerchant.getMerchantId());
		int row = this.sysToDoListService.batchUpdateCloseSysToDoLists(inputSysToDoListVO);
		LOG.info("关闭了"+row+"条待办事项");
		return new JsonResult(synMerchant, true);
	}
	/**
	 * 生成工具类map， match模块num和附件路径
	 */
	private Map<String, String> initMatchNumAndPath() {
		Map<String, String> stepNumMatchFilePath = new HashMap<String, String>();
		stepNumMatchFilePath.put("1", merchantLogoPath);  //商户logo路径
		stepNumMatchFilePath.put("2", PathConfig.BES_REQ_ATTA);  //后端需求附件
		return stepNumMatchFilePath;
	}
	
	@Override
	public JsonResult uploadCommonMethod(MultipartFile myfile, String realPath, Object object, String flag, Integer merchantId,
			SysUserVO sessionUser) {
		
		JsonResult fileInfo = null;
		Map<String, String> initMatchNumAndPath = this.initMatchNumAndPath();
		try {
			if("1".equals(flag)){
				fileInfo = FileUploaderUtil.uploadFile(myfile, realPath + initMatchNumAndPath.get(flag)+"/"+merchantId, null, 0L);
				Map<String, Object> file = (Map<String, Object>) fileInfo.getData();
				file.put("fileFullPath", initMatchNumAndPath.get(flag)+"/"+merchantId + "/"+file.get("attachmentName"));
			}else{

				fileInfo = FileUploaderUtil.uploadFile(myfile, realPath + initMatchNumAndPath.get(flag), null, 0L);
				Map<String, Object> file = (Map<String, Object>) fileInfo.getData();
				file.put("fileFullPath", initMatchNumAndPath.get(flag) + "/"+file.get("attachmentName"));
			}
		} catch (IOException e) {
			throw new FesBizException("文件读写错误" + e);
		}

		if (fileInfo.getFlag() == 1) {// 上传成功
			return new JsonResult(this.saveAttachement( sessionUser.getId(), fileInfo, flag, merchantId), true);
		}
		return null;
	}
	
	@Resource SysAttachmentService sysAttachmentService;
	private SysAttachmentVO saveAttachement( Integer userId, JsonResult fileInfo,String flag, Integer merchantId) {
		Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
		//上传附件每次保存一个附件，对应表中插入一条记录
		SysAttachmentVO sysAttachment = new SysAttachmentVO();	
		sysAttachment.setFileFullPath(file.get("fileFullPath").toString());
		sysAttachment.setAttachmentName(file.get("attachmentName").toString());
		sysAttachment.setOriginalFileName(file.get("originalFileName").toString());
		sysAttachment.setAttachmentType("4");// 文件类型
		sysAttachment.setAttachmentSuffix(file.get("fileSuffix").toString());
		sysAttachment.setModuleType(Constant._FES);
		//后端需求
		if("2".equals(flag))sysAttachment.setModuleType(Constant._BES);
		sysAttachment.setIsEnable(Constant.IS_ENABLE);
		sysAttachment.setIsDownloadable("1");
		Object fileSizeObject = file.get("fileSize");
		if(fileSizeObject instanceof Long){
			Long fileSize = (Long)fileSizeObject;
			sysAttachment.setAttachmentSize(Long.toString(fileSize));
		}else{
			Integer fileSize = (Integer)fileSizeObject;
			sysAttachment.setAttachmentSize(fileSize.toString());
		}
		Object attachmentPath = file.get("attachmentPath");
		if(merchantId !=null) attachmentPath = merchantId.toString();
		sysAttachment.setAttachmentPath(attachmentPath==null? null: attachmentPath.toString());
		sysAttachment.setInsertBy(userId);
		sysAttachment.setUpdateBy(userId);
		sysAttachment.setInsertTime(new Date());
		sysAttachment.setUpdateTime(new Date());
		this.sysAttachmentService.saveSysAttachment(sysAttachment);
		return sysAttachment;
	}
}

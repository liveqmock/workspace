/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.service.impl;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.bes.dao.BesMonthlyReportDao;
import com.yazuo.erp.bes.dao.BesRequirementDao;
import com.yazuo.erp.bes.service.BesCallRecordService;
import com.yazuo.erp.bes.service.BesMonthlyReportService;
import com.yazuo.erp.bes.service.BesRequirementService;
import com.yazuo.erp.bes.vo.BesCallRecordVO;
import com.yazuo.erp.bes.vo.BesMonthlyReportVO;
import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.fes.service.FesPlanAttachmentService;
import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.erp.fes.vo.FesPlanAttachmentVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.*;
import com.yazuo.erp.system.service.impl.SysOperationLogServiceImpl;
import com.yazuo.erp.system.vo.*;
import com.yazuo.util.DateUtil;
import com.yazuo.util.StringUtil;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static junit.framework.Assert.assertNotNull;

@Service
@SuppressWarnings("unused")
public class BesRequirementServiceImpl implements BesRequirementService {
	 
	@Resource private BesRequirementDao besRequirementDao;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private SysUserDao sysuser;
	@Resource private ResourceService resourceService;
	@Resource private SysOperationLogService sysOperationLogService;
	@Resource private SynMerchantService synMerchantService;
	@Resource private MktContactService mktContactService;
	@Resource private SysUserMerchantService sysUserMerchantService;
	@Resource private FesPlanService fesPlanService;
	@Resource private FesPlanAttachmentService fesPlanAttachmentService;
	@Resource private SysAttachmentService sysAttachmentService;
	@Resource private BesMonthlyReportService besMonthlyReportService;
	@Resource private SysUserDao sysUserDao;
	@Resource private BesCallRecordService besCallRecordService;
	@Resource private SysDocumentService sysDocumentService;
	@Resource private BesMonthlyReportDao besMonthlyReportDao;
	@Resource private SysRemindService sysRemindService;
	
	private static final Log LOG = LogFactory.getLog(BesRequirementServiceImpl.class);
	private static String splitMark = "@"; //日志分隔符
	//页面显示图标
    static class Icon {
    	private static String markName = "iconCat"; //图标分类
		private static int start = 1;
		private static int running = 2;
		private static int todo = 3;
		private static int close = 4;
	}
	@Override
	public BesRequirementVO saveOrUpdateBesRequirement(BesRequirementVO besRequirement, SysUserVO sessionUser) {
		Integer id = besRequirement.getId();
		Integer[] ids = besRequirement.getIds();
		Integer userId = sessionUser.getId();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(userId);
		String userName = sysUserVO.getUserName();
		int row = -1;
		if (id == null && (ids == null || ids.length == 0)) { // 新增
			besRequirement.setInsertBy(userId);
			besRequirement.setInsertTime(new Date());
			besRequirement.setUpdateBy(userId);
			besRequirement.setUpdateTime(new Date());
			besRequirement.setIsEnable(Constant.Enable);
			row = besRequirementDao.saveBesRequirement(besRequirement);
		} else if (ids != null && ids.length >= 0) { // 批量更新处理人和处理状态
			for (Integer reqId : ids) {
				BesRequirementVO besRequirement1 = new BesRequirementVO();
				assertNotNull("处理人不能为空! ", besRequirement.getHandlerId());
				String contactName = this.sysUserDao.getSysUserById(besRequirement.getHandlerId()).getUserName();
				String description = userName + " 将需求指派给了"+ contactName;
				String fesLogType = "18";// 月报，需求指派
				besRequirement1.setId(reqId);
				besRequirement1.setHandlerId(besRequirement.getHandlerId());
				besRequirement1.setStatus("2"); // 未完成
				besRequirement1.setUpdateBy(userId);
				besRequirement1.setUpdateTime(new Date());
				besRequirement1.setIsEnable(Constant.Enable);
				this.addOperationLogs(reqId, besRequirement1, description, fesLogType, 1);
				row = besRequirementDao.updateBesRequirement(besRequirement1);
			}
		}
		return besRequirement;
	}
	/**
	 * 保存新增的操作流水记录
	 */
	private void addOperationLogs(Integer reqId, BesRequirementVO besRequirement1,String description,String fesLogType, int flag) {
		BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		Integer[] operationLogIds = besRequirementById.getOperationLogIds();
		assertNotNull( "操作人不允许为空 !", besRequirement1.getUpdateBy());
		//保存流水 
		int newOperId = sysOperationLogService.saveSysOperationLogForMonthlyReport(besRequirementById, 
				description, fesLogType, besRequirement1.getUpdateBy(), flag);
		if(operationLogIds!=null){
			operationLogIds = (Integer[]) ArrayUtils.add(operationLogIds, newOperId);
			besRequirement1.setOperationLogIds(operationLogIds);
		}else{
			besRequirement1.setOperationLogIds(new Integer[]{newOperId});
		}
	}
	public int saveBesRequirement (BesRequirementVO besRequirement) {
		return besRequirementDao.saveBesRequirement(besRequirement);
	}
	public int batchInsertBesRequirements (Map<String, Object> map){
		return besRequirementDao.batchInsertBesRequirements(map);
	}
	public int updateBesRequirement (BesRequirementVO besRequirement){
		return besRequirementDao.updateBesRequirement(besRequirement);
	}
	public int batchUpdateBesRequirementsToDiffVals (Map<String, Object> map){
		return besRequirementDao.batchUpdateBesRequirementsToDiffVals(map);
	}
	public int batchUpdateBesRequirementsToSameVals (Map<String, Object> map){
		return besRequirementDao.batchUpdateBesRequirementsToSameVals(map);
	}
	public int deleteBesRequirementById (Integer id){
		return besRequirementDao.deleteBesRequirementById(id);
	}
	public int batchDeleteBesRequirementByIds (List<Integer> ids){
		return besRequirementDao.batchDeleteBesRequirementByIds(ids);
	}
	@Override 
	public BesRequirementVO getBesRequirementById(Integer id, final SysUserVO sessionUser){
		 final BesRequirementVO besRequirementById = besRequirementDao.getBesRequirementById(id);
		 //封装 需求信息 
		 this.encapsulateBesReq(sessionUser, besRequirementById);
		 //封装流水
		List<Map<String, Object>> operstionLogs = this.encapsulateOperationLog(besRequirementById);
		this.handleOperationLogs(besRequirementById, operstionLogs);
		besRequirementById.setOperstionLogs(operstionLogs);
	    return besRequirementById;
	}
	
	/**
	 * 操作流水添加按钮信息
	 * 
	 * note: 此操作不更改数据库中的流水，只是更新TODO流水和最后一步按钮显示文字
	 */
	@SuppressWarnings("serial")
	private void handleOperationLogs(final BesRequirementVO besRequirement, final List<Map<String, Object>> operstionLogs) {
		final BesRequirementVO besRequirementById = this.getBesRequirementById(besRequirement.getId());
		// 添加下一步月报回访待办事项
		@SuppressWarnings("rawtypes")
		final Map[] buttons = { ArrayUtils.toMap(new String[][] { { "type", "1" }, { "text", "回访" } }),
				ArrayUtils.toMap(new String[][] { { "type", "2" }, { "text", "再次回访" } }),
				ArrayUtils.toMap(new String[][] { { "type", "3" }, { "text", "放弃" } }),
				ArrayUtils.toMap(new String[][] { { "type", "4" }, { "text", "完成" } }),
				ArrayUtils.toMap(new String[][] { { "type", "5" }, { "text", "指派负责人" } })
				};
		final Integer merchantId = besRequirementById.getMerchantId();
		
		if ("1".equals(besRequirementById.getStatus())) {// 未指派
			operstionLogs.add(new HashMap<String, Object>() {
				{
					put(Icon.markName, Icon.todo);
					put(SysOperationLogVO.COLUMN_DESCRIPTION, "未指派处理人");
					put("buttons", ArrayUtils.subarray(buttons, 4, 5));
				}
			});
		}else if ("3".equals(besRequirementById.getStatus())) { // 已完成
			// 系统关闭图标没有区分是放弃还是完成
			Map<String, Object> mapOperationLogs = operstionLogs.get(operstionLogs.size() - 1);
			mapOperationLogs.put(Icon.markName, Icon.close);
		} else {
			operstionLogs.add(new HashMap<String, Object>() {
				{
					put(Icon.markName, Icon.todo);
					put(SysOperationLogVO.COLUMN_DESCRIPTION, "请对商户进行月报回访");
					// 存在回访记录
					if (BesRequirementServiceImpl.this.existMonthlyReport(merchantId,"2", DateUtil.fromMonth(), new Date())) {
						put(SysOperationLogVO.COLUMN_DESCRIPTION, "是否需要再次回访，无需回访，请选择完成");
						Object[] addAll = ArrayUtils.addAll(ArrayUtils.subarray(buttons, 1, 2),
								ArrayUtils.subarray(buttons, 3, 4));
						put("buttons", addAll);
					} else {
						put("buttons", ArrayUtils.subarray(buttons, 0, 1));
						// 如果有点击过电话未接通, 最后一条TODO需要特殊处理
						Map<String, Object> mapOperationLogs = operstionLogs.get(operstionLogs.size() - 1);
						if (SysOperationLogServiceImpl.connected.equals(mapOperationLogs.get(SysOperationLogVO.COLUMN_REMARK))) {
							put("buttons", ArrayUtils.subarray(buttons, 1, 3));
						}
					}
				}
			});
		}
		//处理流水提示语
		CollectionUtils.collect(operstionLogs, new Transformer() {
			@SuppressWarnings("unchecked")
			public Object transform(Object input) {
				Map<String, Object> inputMap = (Map<String, Object>)input;
				String description = inputMap.get(SysOperationLogVO.COLUMN_DESCRIPTION).toString();
				String[] descs = description.split(splitMark);
				inputMap.put(SysOperationLogVO.COLUMN_DESCRIPTION,descs[0]);
				if(descs.length>1){
					List<Map<String, String>> tipsList = new ArrayList<Map<String,String>>();
					for (String tip : (String[])ArrayUtils.subarray(descs, 1, descs.length)) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("text", tip);
						map.put("path", "");
						tipsList.add(map);
					}
					inputMap.put("tips" , tipsList);
				}
//				LOG.info(JsonResult.getJsonString(inputMap));
				return inputMap;
			}
		});
	}

	/**
	 * 是否包含发送月报的记录
	 */
    private boolean existMonthlyReport(Integer merchantId,String accessFactor,Date beginTime,Date endTime) {
        BesMonthlyReportVO monthlyReportVO = new BesMonthlyReportVO();
        monthlyReportVO.setMerchantId(merchantId);
        monthlyReportVO.setAccessFactor(accessFactor);
        monthlyReportVO.setStatus("1");//发送
        monthlyReportVO.setBeginTime(beginTime);
        monthlyReportVO.setEndTime(endTime);
        List<BesMonthlyReportVO> monthlyReportVOs = this.besMonthlyReportDao.getBesMonthlyReports(monthlyReportVO);
        return monthlyReportVOs.size() > 0;
    }
	/**
	 * 封装流水
	 * 
	 * 1.查询操作流水封装成map的形式
	 * 2.添加显示图标标识
	 */
	private List<Map<String, Object>> encapsulateOperationLog(BesRequirementVO besRequirementById) {
		besRequirementById = this.getBesRequirementById(besRequirementById.getId());
		Integer[] operationLogIds = besRequirementById.getOperationLogIds();
		 List<Map<String, Object>> operstionLogs = new ArrayList<Map<String,Object>>();
		 if(operationLogIds==null){
			 return operstionLogs;
		 }
		 int i=0;
		 for (Integer logId : operationLogIds) {
			SysOperationLogVO sysOperationLogById = this.sysOperationLogService.getSysOperationLogById(logId);
			Map<String, Object> map = new HashMap<String, Object>();
			if(i==0){
				map.put(Icon.markName, Icon.start);
			}else{
				map.put(Icon.markName, Icon.running);
			}
			map.put(SysOperationLogVO.COLUMN_ID, sysOperationLogById.getId());
			map.put(SysOperationLogVO.COLUMN_OPERATING_TIME, sysOperationLogById.getOperatingTime());
			map.put(SysOperationLogVO.COLUMN_DESCRIPTION, sysOperationLogById.getDescription());
			//判断 是否保存了接通回访电话 特殊处理
			map.put(SysOperationLogVO.COLUMN_REMARK, sysOperationLogById.getRemark()); 
			operstionLogs.add(map);
			i++;
		}
		return operstionLogs;
	}

//	@Resource EhCacheCacheManager cacheManager;
	@Override
	public List<BesRequirementVO> getBesRequirements (BesRequirementVO besRequirement, final SysUserVO sessionUser){
//		 besRequirement.setBaseUserId(sessionUser.getId());
		 List<BesRequirementVO> besRequirements = besRequirementDao.getBesRequirements(besRequirement);
		 //添加数据字典
		 for (final BesRequirementVO besRequirementVO : besRequirements) {
			 this.encapsulateBesReq(sessionUser, besRequirementVO);
		 }
		 /*
		Collection<String> cacheNames = cacheManager.getCacheNames();
		LOG.info("size" + cacheNames.size());
		for (String string : cacheNames) {
			LOG.info("cache name: "+ string);
			LOG.info(cacheManager.getCache(string));
			CacheManager ehcacheManager = cacheManager.getCacheManager();
			Ehcache ehcache = ehcacheManager.getEhcache(string);
			List keys = ehcache.getKeys();
			LOG.info("ddd "+ keys.size());
			for (Object object : keys) {
				LOG.info(" fff  "+object.getClass() + " "+object);
				
			}
		}*/
		return besRequirements;
	}
	
	private void encapsulateBesReq(final SysUserVO sessionUser, final BesRequirementVO besRequirementVO) {
        besRequirementVO.setDicRowSourceCat(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000103", besRequirementVO.getSourceCat()));
        besRequirementVO.setDicRowSourceContent(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000104", besRequirementVO.getSourceContent()));
        besRequirementVO.setDicRowContactCat(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000102", besRequirementVO.getContactCat()));
        if (besRequirementVO != null && !StringUtil.isNullOrEmpty(besRequirementVO.getStatus())) {
            besRequirementVO.setDicRowReqStatus(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000113", besRequirementVO.getStatus()));
        }
        //处理人
        final SysUserVO sysUserById = sysuser.getSysUserById(besRequirementVO.getHandlerId());
        @SuppressWarnings("serial")
        Map<String, Object> dicRowHandler = new HashMap<String, Object>() {
            {
                if (sysUserById != null) {
                    put("userId", sysUserById.getId());
                    if (sessionUser.getUserName().equals(sysUserById.getUserName())) {
                        put("userName", "自己");
                    } else {
                        put("userName", sysUserById.getUserName());
                    }
                    put("tel", sysUserById.getTel());
                }
            }

        };
        besRequirementVO.setDicRowHandler(dicRowHandler);
        //添加资源描述
        @SuppressWarnings("serial")
        List<SysResourceVO> resources = this.resourceService.getSysResources(new HashMap<String, Object>() {{
            put("remark", besRequirementVO.getResourceRemark());
        }});
        String resourceName = ((SysResourceVO) resources.get(0)).getResourceName();
        @SuppressWarnings("serial")
        List<SysResourceVO> resources1 = this.resourceService.getSysResources(new HashMap<String, Object>() {{
            put("remark", besRequirementVO.getResourceExtraRemark());
        }});
        String resourceName1 = ((SysResourceVO) resources1.get(0)).getResourceName();
        besRequirementVO.setResourceName(resourceName);
        besRequirementVO.setResourceExtraName(resourceName1);
        //前端负责任人
        Integer merchantId = besRequirementVO.getMerchantId();
        SysUserVO fesUser = this.sysUserMerchantService.getFesUserByMerchantId(merchantId);
        besRequirementVO.setFesUserName(fesUser.getUserName());
        //联系人
        SynMerchantVO synMerchantById = synMerchantService.getSynMerchantById(merchantId);
        String merchantName = synMerchantById.getMerchantName();
        MktContactVO mktContact = mktContactService.getMktContactById(besRequirementVO.getContactId());
        if (mktContact == null) {
            mktContact = this.mktContactService.getLastContactForMerchant(besRequirementVO.getMerchantId());
            if (mktContact != null) {
                // 如果不存在联系人则添加联系人
                BesRequirementVO requirementVO = this.besRequirementDao.getBesRequirementById(besRequirementVO.getId());
                requirementVO.setContactId(mktContact.getId());
                this.besRequirementDao.updateBesRequirement(requirementVO);
            }
        }
        besRequirementVO.setMktContact(mktContact);
        besRequirementVO.setMerchantName(merchantName);
        //是否已回访
        if (this.sysOperationLogService.getSysOperationByTypeAndIds(besRequirementVO.getOperationLogIds(), "19")) {
            besRequirementVO.setReVisitedText("已回访");
        }
    }

    public List<Map<String, Object>>  getBesRequirementsMap (BesRequirementVO besRequirement){
		return besRequirementDao.getBesRequirementsMap(besRequirement);
	}
	@Override
	public BesRequirementVO getBesRequirementById(Integer id) {
		return besRequirementDao.getBesRequirementById(id);
	}
	
	@Value("${planFilePath}")
	private String planFilePath;
	/**
	 * 查找当前月 的工作计划
	 * @return 
	 */
	@Override
	public List<Map<String, String>> getMonthlyFesPlan(String realPath, BesRequirementVO besRequirementVO) {
		List<FesPlanVO> fesPlans = this.getFesPlanForReq(besRequirementVO.getMerchantId());
		if(fesPlans.size()>0){
			LOG.info("本月月报工作管理个数:　"+fesPlans.size());
			FesPlanVO fesPlanVO = (FesPlanVO)fesPlans.get(0);
			//查询附件关系
			FesPlanAttachmentVO fesPlanAttachment = new FesPlanAttachmentVO();
			fesPlanAttachment.setPlanId(fesPlanVO.getId());
			List<FesPlanAttachmentVO> fesPlanAttachments = this.fesPlanAttachmentService.getFesPlanAttachments(fesPlanAttachment );
			List<Map<String , String>> list = new  ArrayList<Map<String,String>>();
			for (FesPlanAttachmentVO fesPlanAttachmentVO : fesPlanAttachments) {
				Map<String , String> map = new HashMap<String, String>();
				Integer attachmentId = fesPlanAttachmentVO.getAttachmentId();
				SysAttachmentVO sysAttachmentById = sysAttachmentService.getSysAttachmentById(attachmentId);
				String fileFullPath = planFilePath+"/"+sysAttachmentById.getAttachmentName();
				sysAttachmentById.setFileFullPath(fileFullPath);
				LOG.info("fileFullPath= "+fileFullPath);
				map.put("fileFullPath", fileFullPath);
				map.put("attachmentSize", sysAttachmentById.getAttachmentSize());
				map.put("fileName", sysAttachmentById.getOriginalFileName());
				list.add(map);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 查找当前商户当月的工作计划
	 * select * from fes.fes_plan where  plan_item_type = '1' and is_enable = '1' and status != '3' 
		AND start_time >= '2014-12-1'  AND start_time <= '2014-12-31'   
	   ORDER BY  start_time desc 
	 */
	@Override
	public List<FesPlanVO> getFesPlanForReq(Integer merchantId) {
		FesPlanVO fesPlan = new FesPlanVO();
		fesPlan.setPlanItemType("1");//讲解月报
		fesPlan.setIsEnable("1");
		fesPlan.setNonStatus("3");//已放弃
		fesPlan.setFesBeginTime(DateUtil.fromMonth());
		fesPlan.setFesEndTime(DateUtil.toMonth());
		fesPlan.setMerchantId(merchantId);
		fesPlan.setSortColumns("start_time"); //按开始时间降序
		List<FesPlanVO> fesPlans = this.fesPlanService.getFesPlans(fesPlan);
		return fesPlans;
	}
	
	/**
	 *  回访 电话未接通
	 */
	@Override
	public List<Map<String, Object>> saveVisteFailTelCall(BesRequirementVO besRequirement, SysUserVO sessionUser) {
		
		final Integer reqId = besRequirement.getId();
		String visiteButtonName = besRequirement.getVisiteButtonName();
		Integer userId = sessionUser.getId();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(userId);
		String userName = sysUserVO.getUserName();
		BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		assertNotNull("联系人不能为空! ", besRequirementById.getContactId());
		String contactName = this.mktContactService.getMktContactById(besRequirementById.getContactId()).getName();
		String description = userName + " 回访了" +contactName  + ""
			+ ",电话" + visiteButtonName;
		//放弃或完成特殊处理
		Integer btnFlag = besRequirement.getBtnFlag();
		if(btnFlag!= null){
			if(1 == btnFlag){
				description = userName + "放弃了回访";
			}else{
				description = userName + "完成了回访";
				//保存提醒
				this.sysRemindService.saveSysRemind(this.getSysRemindVO(besRequirementById, sessionUser));
			}
	    	if(besRequirement.getRemark()!=null){ //有填写放弃原因
	    		description+= splitMark+besRequirement.getRemark();
	    	}
		}
		
	    List<Map<String, Object>> operationLogs = this.saveReqAndOperationLog(sessionUser, 2, description, new BesRequirementVO(){{setId(reqId);}});
	    if(btnFlag!= null ){  
	    	//完成和放弃都 更改后端需求的状态为完成
	    	this.updateStatusToComplete(besRequirement, reqId, userId);
	    }
		// 添加操作流水按钮信息
		this.handleOperationLogs(besRequirementById, operationLogs);
		return operationLogs;
	}
	private SysRemindVO getSysRemindVO(BesRequirementVO besRequirementById, SysUserVO user) {
		Integer merchantId = besRequirementById.getMerchantId();
		SysUserVO fesUser = this.sysUserMerchantService.getFesUserByMerchantId(merchantId);
		FastDateFormat fdf = FastDateFormat.getInstance("MM");
		String customDateTime = fdf.format(new Date());
		SysRemindVO sysRemindVO = new SysRemindVO();
		sysRemindVO.setUserId(fesUser.getId());
		sysRemindVO.setMerchantId(merchantId);
		sysRemindVO.setPriorityLevelType("01");
		sysRemindVO.setItemType("07");
		String content = customDateTime+"月月报讲解已完成。";
		sysRemindVO.setItemContent(content);
		sysRemindVO.setItemStatus("1");
		sysRemindVO.setIsEnable("1");
		sysRemindVO.setInsertBy(user.getId());
		sysRemindVO.setInsertTime(new Date());
		sysRemindVO.setUpdateBy(user.getId());
		sysRemindVO.setUpdateTime(new Date());
		return sysRemindVO;
	}
	private void updateStatusToComplete(BesRequirementVO besRequirement, Integer reqId, Integer userId) {
		BesRequirementVO besRequirement1 = new BesRequirementVO();
		besRequirement1.setId(reqId);
		besRequirement1.setHandlerId(besRequirement.getHandlerId());
		besRequirement1.setStatus("3"); // 已完成
		besRequirement1.setUpdateBy(userId);
		besRequirement1.setUpdateTime(new Date());
		this.updateBesRequirement(besRequirement1);
	}
	
	/**
	 * 电话接通或未接通时保存流水
	 */
	private List<Map<String, Object>> saveReqAndOperationLog(SysUserVO sessionUser, int flag, String description, BesRequirementVO besRequirement ) {
		
		Integer reqId = besRequirement.getId();
		Integer userId = sessionUser.getId();
		BesRequirementVO besRequirement1 = new BesRequirementVO();
		String fesLogType = "19";// 月报[回访]
		besRequirement1.setId(reqId);
		
		Integer contactId = besRequirement.getContactId();
		if(contactId!=null){
			besRequirement1.setContactId(contactId);
		}
		besRequirement1.setUpdateBy(userId);
		besRequirement1.setUpdateTime(new Date());
		besRequirement1.setIsEnable(Constant.Enable);
		
		// 封装流水
		BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		// 保存新增的操作流水记录
		this.addOperationLogs(reqId, besRequirement1, description, fesLogType, flag);
		// 更新后台需求流水
		this.besRequirementDao.updateBesRequirement(besRequirement1);
		// 封装操作流水 
		List<Map<String, Object>> operationLogs = this.encapsulateOperationLog(besRequirementById);
		return operationLogs;
	}
	
	/**
	 * 电话接通后的保存操作
	 * 
	 * 1. 保存问卷调研单
	 * 2. 保存通话记录
	 * 3. 生成流水
	 */
	@Override
	public List<Map<String, Object>> savePaperAndCallRecord(BesCallRecordVO besCallRecord,SysDocumentVO sysDocument,
			SysUserVO sessionUser, HttpServletRequest request) {
		
		final Integer reqId = besCallRecord.getRequirementId();
		final BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		final Integer merchantId = besRequirementById.getMerchantId();
		Map<String, Object> contactChangedMap = besCallRecord.getContactChangedMap();
		Integer contactId = null; 
		Object objectContactId = contactChangedMap.get("contactId");
		if(objectContactId!=null && !"".equals(objectContactId)){
			contactId = objectContactId.getClass().toString().contains("String")? 
					Integer.parseInt(objectContactId.toString()): (Integer)contactChangedMap.get("contactId");
		}
		//1. 保存通话记录
		Integer contactIdFromReq= besRequirementById.getContactId();
        boolean isChanged = contactId != null && !contactId.equals(contactIdFromReq);//是否变更
        contactId = isChanged ? contactId : contactIdFromReq;

        besCallRecord.setContactId(contactId);
		besCallRecord.setHandlerId(besRequirementById.getHandlerId());
		int saveBesCallRecord = besCallRecordService.saveBesCallRecord(besCallRecord, sessionUser);
		//3. 生成流水描述
		Integer userId = sessionUser.getId();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(userId);
		String userName = sysUserVO.getUserName();
		String formatPeriod = DurationFormatUtils.formatPeriod(besCallRecord.getBeginTime().getTime(), besCallRecord.getEndTime().getTime(), "mm:ss");
		String description = userName + " 回访了" + this.mktContactService.getMktContactById(contactId).getName() + ""
			+ ",通话用时" + formatPeriod;
		//变更联系人处理流水
		BesRequirementVO besReqForUpdate = new BesRequirementVO();
		besReqForUpdate.setId(reqId);
        if (isChanged) {  //修改后的联系人
            besReqForUpdate.setContactId(contactId);
            MktContactVO merchantContact = this.mktContactService.getMktContactById(contactId);
            String others = "变更了联系人为" + merchantContact.getName() + ",联系电话" + merchantContact.getMobilePhone();
            description += splitMark + others;
        }
        //2.  保存问卷调研单
		int saveSysDocument = this.sysDocumentService.saveSysDocument(sysDocument, request, sessionUser);
		//添加满意 反馈意见等流水
		List<SysDocumentDtlVO> sysDocumentDtlList = sysDocument.getSysDocumentDtlList();
		description = this.savePaperOperationLog(description, merchantId, userId,  sysDocumentDtlList);
		List<Map<String, Object>> operationLogs = this.saveReqAndOperationLog(sessionUser, 1, description, besReqForUpdate);
		//月报回访信息表添加 已回访 记录
		this.saveMonthlyReport(new BesMonthlyReportVO() {
			{
				setMerchantId(merchantId);
				setAccessFactor("2");
				setStatus("1");// 已回访
			}
		}, userId);
		// 添加操作流水按钮信息
		this.handleOperationLogs(this.getBesRequirementById(reqId), operationLogs);
		return operationLogs;
	}
	private void saveMonthlyReport(BesMonthlyReportVO besMonthlyReport, Integer userId) {
		besMonthlyReport.setInsertBy(userId);
		besMonthlyReport.setInsertTime(new Date());
		besMonthlyReport.setUpdateBy(userId);
		besMonthlyReport.setUpdateTime(new Date());
		besMonthlyReport.setIsEnable(Constant.Enable);
		besMonthlyReport.setOperatorTime(new Date());
		this.besMonthlyReportService.saveBesMonthlyReport(besMonthlyReport);
	}
/**
 * 保存评分操作日志
 * 保存满意度 月报信息
 */
	private String savePaperOperationLog(String description, Integer merchantId, Integer userId, List<SysDocumentDtlVO> sysDocumentDtlList) {

		final List<Map<String, Object>> contactCats = sysDictionaryService.querySysDictionaryByTypeStd("00000108");
		for (SysDocumentDtlVO sysDocumentDtlVO : sysDocumentDtlList) {
			if(sysDocumentDtlVO.getQuestionId()==24){
				List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlVO.getSysDocumentDtlOptionList();
				for (SysDocumentDtlOptionVO sysDocumentDtlOptionVO : sysDocumentDtlOptionList) {
					if(sysDocumentDtlOptionVO.getIsSelected().equals("1")){
						description += splitMark+"综合评分："+sysDocumentDtlOptionVO.getOptionContent();
						for (Map<String, Object> map : contactCats) {
							if(map.get("text").equals(sysDocumentDtlOptionVO.getOptionContent())){
								//更新或保存满意度记录
								String status = map.get("value").toString();
								BesMonthlyReportVO monthlyReport = 
									this.besMonthlyReportService.getMonthlyReport(merchantId, "1", DateUtil.fromMonth(), new Date());
								if(monthlyReport!=null){
									monthlyReport.setStatus(status);
									monthlyReport.setUpdateTime(new Date());
									monthlyReport.setUpdateBy(userId);
									this.besMonthlyReportService.updateBesMonthlyReport(monthlyReport);
								}else{
									BesMonthlyReportVO besMonthlyReport = new BesMonthlyReportVO();
									besMonthlyReport.setMerchantId(merchantId);
									besMonthlyReport.setAccessFactor("1");
									besMonthlyReport.setStatus(status);
									this.saveMonthlyReport(besMonthlyReport, userId);
								}
							}
						}
					}
				}
			}else if(sysDocumentDtlVO.getQuestionId()==25){
				List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlVO.getSysDocumentDtlOptionList();
				for (SysDocumentDtlOptionVO sysDocumentDtlOptionVO : sysDocumentDtlOptionList) {
					if(sysDocumentDtlOptionVO.getIsSelected().equals("1") && sysDocumentDtlOptionVO.getIsOpenTextarea().equals("1")){
						description += splitMark+"反馈意见："+sysDocumentDtlOptionVO.getComment();
					}
				}
            }else if (sysDocumentDtlVO.getQuestionId() == 21) { // 是否讲解
                List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlVO.getSysDocumentDtlOptionList();
                for (SysDocumentDtlOptionVO sysDocumentDtlOptionVO : sysDocumentDtlOptionList) {
                    if (sysDocumentDtlOptionVO.getIsSelected().equals("1")) {
                        BesMonthlyReportVO monthlyReport = this.besMonthlyReportService.getMonthlyReport(merchantId, "4", DateUtil.fromMonth(), new Date());
                        FesPlanVO planVO = this.fesPlanService.getCurrentMonthlyPlan(merchantId, DateUtil.fromMonth(), new Date());
                        boolean isExplain = sysDocumentDtlOptionVO.getOptionContent().equals("是");
                        String status = isExplain ? "1" : "3";
                        Date explainTime = isExplain ? planVO.getEndTime() : new Date();
                        if (monthlyReport != null) {
                            monthlyReport.setStatus(status);
                            monthlyReport.setUpdateBy(userId);
                            monthlyReport.setUpdateTime(new Date());
                            monthlyReport.setOperatorTime(explainTime);
                            this.besMonthlyReportService.updateBesMonthlyReport(monthlyReport);
                        }else {
                            BesMonthlyReportVO besMonthlyReportVO = new BesMonthlyReportVO();
                            besMonthlyReportVO.setMerchantId(merchantId);
                            besMonthlyReportVO.setAccessFactor("4");
                            besMonthlyReportVO.setStatus(status);
                            besMonthlyReportVO.setInsertBy(userId);
                            besMonthlyReportVO.setInsertTime(new Date());
                            besMonthlyReportVO.setUpdateBy(userId);
                            besMonthlyReportVO.setUpdateTime(new Date());
                            besMonthlyReportVO.setOperatorTime(explainTime);
                            besMonthlyReportVO.setIsEnable("1");
                            this.besMonthlyReportService.saveBesMonthlyReport(besMonthlyReportVO);
                        }
                    }
                }
			}
		}
		return description;
	}
	
	@Override
	public Map<String, Object> getContactMap(final Integer besReqId) {
		final Map<String, Object> contactMap = new HashMap<String, Object>();
		BesRequirementVO besRequirementById = this.getBesRequirementById(besReqId);
		Integer contactId = besRequirementById.getContactId();
		if(contactId!=null){
			MktContactVO mktContactById = this.mktContactService.getMktContactById(contactId);
			contactMap.put("id", mktContactById.getId());
			contactMap.put("name", mktContactById.getName());
			contactMap.put("mobile_phone", mktContactById.getMobilePhone());
		}else{
			LOG.info("月报需求联系人为空!");
		}
		return contactMap;
	}
	
	@Override
	public Map<String, Object> encapsulateChangeContact(final Integer merchantId, final Map<String, Object> contactMap) {
		final List<Map<String, Object>> contactCats = sysDictionaryService.querySysDictionaryByTypeStd("00000102");
		//过滤前端
		CollectionUtils.filter(contactCats, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>)object;
				if(map.get("value")!=null && map.get("value").equals("1")){
					map.put("isSelected", "1"); //更改行为,默认选中商户
					//添加联系人children

					final List<Map<String, Object>> contactsDropDownList = mktContactService.getContactsDropDownList(merchantId);
					map.put("children", contactsDropDownList);
					return true;
				}
				return false;
			}
		});
		
		final Map<String, Object> changeContact = new HashMap<String, Object>() {
			{
				put("contactCat", contactCats);
				put("mobile", contactMap.get("mobile_phone"));
			}
		};
		return changeContact;
	}
}

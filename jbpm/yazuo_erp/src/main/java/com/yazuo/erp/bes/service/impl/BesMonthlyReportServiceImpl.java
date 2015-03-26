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
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.dao.BesMonthlyReportDao;
import com.yazuo.erp.bes.dao.BesRequirementDao;
import com.yazuo.erp.bes.service.BesMonthlyReportService;
import com.yazuo.erp.bes.service.BesRequirementService;
import com.yazuo.erp.bes.vo.BesMonthlyReportVO;
import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.bes.vo.MonthlyConditionVO;
import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DateUtil;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.ResultSet;
import java.util.*;

@Service
public class BesMonthlyReportServiceImpl implements BesMonthlyReportService {
	private static final Log LOG = LogFactory.getLog(BesMonthlyReportServiceImpl.class);
	@Resource private BesMonthlyReportDao besMonthlyReportDao;
	@Resource private FesPlanService fesPlanService;
	@Resource private BesRequirementService besRequirementService;
	@Resource private BesRequirementDao besRequirementDao;
	@Resource private SynMerchantService synMerchantService;
	@Resource private SysOperationLogService sysOperationLogService;
	
	public int saveBesMonthlyReport (BesMonthlyReportVO besMonthlyReport) {
		return besMonthlyReportDao.saveBesMonthlyReport(besMonthlyReport);
	}
	public int batchInsertBesMonthlyReports (Map<String, Object> map){
		return besMonthlyReportDao.batchInsertBesMonthlyReports(map);
	}
	public int updateBesMonthlyReport (BesMonthlyReportVO besMonthlyReport){
		return besMonthlyReportDao.updateBesMonthlyReport(besMonthlyReport);
	}
	public int batchUpdateBesMonthlyReportsToDiffVals (Map<String, Object> map){
		return besMonthlyReportDao.batchUpdateBesMonthlyReportsToDiffVals(map);
	}
	public int batchUpdateBesMonthlyReportsToSameVals (Map<String, Object> map){
		return besMonthlyReportDao.batchUpdateBesMonthlyReportsToSameVals(map);
	}
	public int deleteBesMonthlyReportById (Integer id){
		return besMonthlyReportDao.deleteBesMonthlyReportById(id);
	}
	public int batchDeleteBesMonthlyReportByIds (List<Integer> ids){
		return besMonthlyReportDao.batchDeleteBesMonthlyReportByIds(ids);
	}
	public BesMonthlyReportVO getBesMonthlyReportById(Integer id){
		return besMonthlyReportDao.getBesMonthlyReportById(id);
	}
	public List<BesMonthlyReportVO> getBesMonthlyReports (BesMonthlyReportVO besMonthlyReport){
		return besMonthlyReportDao.getBesMonthlyReports(besMonthlyReport);
	}
	public List<Map<String, Object>>  getBesMonthlyReportsMap (BesMonthlyReportVO besMonthlyReport){
		return besMonthlyReportDao.getBesMonthlyReportsMap(besMonthlyReport);
	}

	@Override
	public int saveOrCheckSendMonthlyReport() {
        // 得到当天16点时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Date hour16 = DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);
        hour16 = DateUtils.setHours(hour16, 16);

        Date fromMonth = DateUtil.fromMonth();
        Date toMonth = DateUtil.toMonth();

        FesPlanVO fesPlan = new FesPlanVO();
        fesPlan.setFesBeginTime(fromMonth);
        fesPlan.setFesEndTime(toMonth);
        fesPlan.setPlanItemType("1");
        LOG.info(fromMonth);
        LOG.info(toMonth);
        List<FesPlanVO> fesPlans = fesPlanService.getFesPlans(fesPlan); //查询一个月内的所有工作计划
        for (FesPlanVO fesPlanVO : fesPlans) {
            Date startTime = fesPlanVO.getStartTime();
            LOG.info("工作计划时间");
            //计划前一天16点
            Calendar  tmpTime = new GregorianCalendar();
            tmpTime.setTime(startTime);
            tmpTime = DateUtils.truncate(tmpTime,Calendar.DAY_OF_MONTH);
            tmpTime.add(Calendar.DAY_OF_MONTH, -1);
            tmpTime.set(Calendar.HOUR_OF_DAY, 16);
            Date expiredTime = tmpTime.getTime();

            LOG.info(expiredTime);
            if (!expiredTime.after(hour16)) {
                Integer merchantId = fesPlanVO.getMerchantId();
                if (merchantId == null) {
                    continue;
                }
                //查询是否发送月报
                String sentAccessFactor = "3";//发送需求
                boolean isExist = this.existMonthlyReport(merchantId, sentAccessFactor, fromMonth, new Date());
                if (isExist) {
                    LOG.info("商户ID " + merchantId + " 发送了月报!");
                } else {
                    //没有发送月报保存一条未发送的记录
                    BesMonthlyReportVO besMonthlyReport = new BesMonthlyReportVO();
                    besMonthlyReport.setAccessFactor(sentAccessFactor);
                    besMonthlyReport.setStatus("3");//未发
                    besMonthlyReport.setMerchantId(merchantId);
                    besMonthlyReport.setOperatorTime(new Date());
                    besMonthlyReport.setIsEnable("1");
                    besMonthlyReport.setInsertTime(new Date());
                    besMonthlyReport.setInsertBy(Constant.DEFAULT_ADD_USER);
                    besMonthlyReport.setUpdateTime(new Date());
                    besMonthlyReport.setUpdateBy(Constant.DEFAULT_ADD_USER);
                    this.saveBesMonthlyReport(besMonthlyReport);
                    LOG.info(JsonResult.getJsonString("新增的未放送状态的月报: " + besMonthlyReport));
                }
            }
        }
        return 1;
    }


	@SuppressWarnings("serial")
	@Override
	public void checkIfCompletedWorkPlan() {
		Date fromMonth = DateUtil.fromMonth();
		Date toMonth = DateUtil.toMonth();
		FesPlanVO fesPlan = new FesPlanVO();
		fesPlan.setFesBeginTime(fromMonth);
		fesPlan.setFesEndTime(new Date());
		fesPlan.setPlanItemType("1");
		List<FesPlanVO> fesPlans = fesPlanService.getFesPlans(fesPlan); //查询一个月内的所有工作计划
		LOG.info("工作计划查询参数: "+JsonResult.getJsonString(fesPlan)+" 查询出来的个数: "+fesPlans.size());
		
		for (FesPlanVO fesPlanVO : fesPlans) {
            final Integer merchantId = fesPlanVO.getMerchantId();
            //创建需求
            if (fesPlanVO.getStatus().equals("4")) { // 已经完成
                //判断是否已经存在 回访需求 //TODO  目前一个商户一个月只有一个月报回访需求, 将来扩展
                boolean isExist = this.existRequirement(merchantId, fromMonth, toMonth);
                LOG.info("参数"+merchantId+ "  "+ fromMonth+ "  "+new Date()+"  " +isExist);
                if (!isExist) { //不存在需求
                	//创建需求
                    this.saveCreateBesReq(new Date(), merchantId, fesPlanVO.getContactId());
                }
            } else if(fesPlanVO.getStatus().equals("1")) { //未完成
                Date startTime = fesPlanVO.getStartTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                DateUtils.truncate(calendar, Calendar.DAY_OF_MONTH);
                String explainAccessFactor = "4";//讲解类型
                boolean existMonthlyReport = this.existMonthlyReport(merchantId, explainAccessFactor, fromMonth, new Date());
                if (!existMonthlyReport && startTime.before(calendar.getTime())) { //不存在 且 已过期
                    BesMonthlyReportVO besMonthlyReportVO = new BesMonthlyReportVO();
                    besMonthlyReportVO.setAccessFactor(explainAccessFactor);
                    besMonthlyReportVO.setMerchantId(merchantId);
                    besMonthlyReportVO.setOperatorTime(new Date());
                    besMonthlyReportVO.setStatus("3");//过期，未讲解
                    besMonthlyReportVO.setIsEnable("1");
                    besMonthlyReportVO.setInsertBy(Constant.DEFAULT_ADD_USER);
                    besMonthlyReportVO.setInsertTime(new Date());
                    besMonthlyReportVO.setUpdateBy(Constant.DEFAULT_ADD_USER);
                    besMonthlyReportVO.setUpdateTime(new Date());
                    this.besMonthlyReportDao.saveBesMonthlyReport(besMonthlyReportVO);
                }
            }
        }
    }
	private void saveCreateBesReq(Date now, final Integer merchantId, final Integer contactId) { 
		String merchantName = this.synMerchantService.getSynMerchantById(merchantId).getMerchantName();
		FastDateFormat fdf = FastDateFormat.getInstance("MM");
		String month = fdf.format(now);
		String title = merchantName + "商户" + month + "月月报讲解";
		BesRequirementVO besRequirement = new BesRequirementVO();
		besRequirement.setMerchantId(merchantId);
		besRequirement.setTitle(title);
		besRequirement.setContactCat("2");//前端
		besRequirement.setSourceCat("2");//前端
		besRequirement.setSourceContent("3");//ERP
		besRequirement.setResourceRemark("re_visite_151");
		besRequirement.setResourceExtraRemark("monthly_rpt_161");
		besRequirement.setStatus("1");//未指派
		besRequirement.setIsEnable("1");
		//保存工作计划流水
		List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService
				.getSysOperationLogs(new SysOperationLogVO() {
					{	
						setMerchantId(merchantId);
						setModuleType("bes");
						setFesLogType("17");//工作管理
						setBeginTime(DateUtil.fromMonth());
						setEndTime(new Date());
					}
				});

        List<Integer> operIds = new ArrayList<Integer>();
        for (SysOperationLogVO sysOperationLogVO : sysOperationLogs) {
            operIds.add(sysOperationLogVO.getId());
        }
        besRequirement.setOperationLogIds(operIds.toArray(new Integer[operIds.size()]));
		besRequirement.setContactId(contactId);//取工作计划的联系人
		besRequirement.setInsertBy(Constant.DEFAULT_ADD_USER);
		besRequirement.setInsertTime(new Date());
		besRequirement.setUpdateBy(Constant.DEFAULT_ADD_USER);
		besRequirement.setUpdateTime(new Date());
		besRequirementService.saveBesRequirement(besRequirement);
	}

    /**
     * 确定后端需求是否存在
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
	@Override
    public boolean existRequirement(int merchantId, Date beginTime, Date endTime) {
        BesRequirementVO searchRequirement = new BesRequirementVO();
        searchRequirement.setMerchantId(merchantId);
        searchRequirement.setStartTime(beginTime);
        searchRequirement.setEndTime(endTime);
        List<BesRequirementVO> besRequirements = besRequirementDao.getBesRequirements(searchRequirement);
        return besRequirements.size() > 0;
    }

    @Override
    public Page<Integer> queryMonthlyReports(List<MonthlyConditionVO> conditionVOs, String merchantName, Date beginTime, Date endTime, int checkDate) {
        return this.besMonthlyReportDao.getMerchantIds(conditionVOs,merchantName,beginTime,endTime,checkDate );
    }

    @Override
    public List<Map<String, Object>> getMonthlyReports(List<Integer> merchantIds) {
        return this.besMonthlyReportDao.getMonthlyReports(merchantIds);
    }

    /**
     * 确认月报信息是否存在
     */
    @Override
    public boolean existMonthlyReport(int merchantId,String accessFactor,Date beginTime,Date endTime) {
        BesMonthlyReportVO monthlyReportVO = this.getMonthlyReport(merchantId, accessFactor, beginTime, endTime);
        return monthlyReportVO != null;
    }

    /**
     * 查询月报信息
     */
    @Override
    public BesMonthlyReportVO getMonthlyReport(int merchantId,String accessFactor,Date beginTime,Date endTime) {
    	BesMonthlyReportVO monthlyReportVO = new BesMonthlyReportVO();
    	monthlyReportVO.setMerchantId(merchantId);
    	monthlyReportVO.setAccessFactor(accessFactor);
    	monthlyReportVO.setBeginTime(beginTime);
    	monthlyReportVO.setEndTime(endTime);
    	List<BesMonthlyReportVO> monthlyReportVOs = this.besMonthlyReportDao.getBesMonthlyReports(monthlyReportVO);
    	if(monthlyReportVOs.size() > 0){
    		return monthlyReportVOs.get(0);
    	}else{
    		return null;
    	}
    }

    @Override
    public Map<Integer, String> getMerchantContact(Date from, Date to) {
        List<Map<Integer, Object>> contactList = this.besMonthlyReportDao.getMerchantContact(from,to);
        Map<Integer, String> result = new HashMap<Integer, String>();
        for (Map<Integer, Object> row : contactList) {
            result.put((Integer)row.get("merchantid"), (String)row.get("name"));
        }
        return result;
    }

    @Deprecated
    protected Date getTimeForHour(Date date, int hour) {
        return null;
    }
    
    /**
     * 发送月报
     */
	@Override
	public boolean saveSendMonthlyRpt(Integer merchantId, SysUserVO sessionUser){
        String explainAccessFactor = "3";//讲解类型
		BesMonthlyReportVO besMonthlyReport = new BesMonthlyReportVO();
		besMonthlyReport.setAccessFactor(explainAccessFactor); // 类型发送
		besMonthlyReport.setStatus("1");// 正常
		besMonthlyReport.setMerchantId(merchantId);
		Date fromMonth = DateUtil.fromMonth();
        boolean existMonthlyReport = this.existMonthlyReport(merchantId, explainAccessFactor, fromMonth, new Date());
		if(!existMonthlyReport){
			Integer userId = sessionUser.getId();
			besMonthlyReport.setInsertBy(userId);
			besMonthlyReport.setInsertTime(new Date());
			besMonthlyReport.setUpdateBy(userId);
            besMonthlyReport.setUpdateTime(new Date());
            besMonthlyReport.setIsEnable(Constant.Enable);
            besMonthlyReport.setOperatorTime(new Date());
            int saveBesMonthlyReport = this.saveBesMonthlyReport(besMonthlyReport);
            Assert.assertTrue(saveBesMonthlyReport > 0);
            return saveBesMonthlyReport > 0;
        } else {
            BesMonthlyReportVO monthlyReportVO = this.getMonthlyReport(merchantId, explainAccessFactor, fromMonth, new Date());
            FesPlanVO fesPlanVO = this.fesPlanService.getMonthlyPlanVO(merchantId, fromMonth, new Date());
            Date startDate = fesPlanVO.getStartTime();
            Date sentTime = DateUtils.truncate(startDate, Calendar.DAY_OF_MONTH);
            sentTime = DateUtils.addDays(sentTime, -1);
            sentTime = DateUtils.setHours(sentTime, 16);
            if (sentTime.after(new Date()) && !monthlyReportVO.getStatus().equals("1")) { //状态不是 正常发送; 且时间未过，则更正状态信息
                monthlyReportVO.setStatus("1");
                monthlyReportVO.setOperatorTime(new Date());
                monthlyReportVO.setUpdateTime(new Date());
                monthlyReportVO.setUpdateBy(sessionUser.getId());
                this.besMonthlyReportDao.updateBesMonthlyReport(monthlyReportVO);
            }
            if (sentTime.before(new Date()) && monthlyReportVO.getStatus().equals("3")) {
                LOG.debug("商户" + merchantId + "月报延迟发送！");
                monthlyReportVO.setStatus("2");
                monthlyReportVO.setOperatorTime(new Date());
                monthlyReportVO.setUpdateTime(new Date());
                monthlyReportVO.setUpdateBy(sessionUser.getId());
                this.besMonthlyReportDao.updateBesMonthlyReport(monthlyReportVO);
            }
            return true;
        }
    }
}

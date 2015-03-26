/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
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

package com.yazuo.erp.bes.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.dto.ConditionResultDTO;
import com.yazuo.erp.bes.dto.MonthlyConditionDTO;
import com.yazuo.erp.bes.dto.MonthlyStatisticDTO;
import com.yazuo.erp.bes.service.BesMonthlyReportService;
import com.yazuo.erp.bes.vo.BesMonthlyReportVO;
import com.yazuo.erp.bes.vo.MonthlyConditionVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.service.SysConfigService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 商户月报信息表 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("besMonthlyReport")
public class BesMonthlyReportController extends BesBasicController {
	
	private static final Log LOG = LogFactory.getLog(BesMonthlyReportController.class);
	@Resource
	private BesMonthlyReportService besMonthlyReportService;

	@Resource
    private SysDictionaryService sysDictionaryService;

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private SysUserMerchantService userMerchantService;

    /**
	 * 保存修改商户月报信息表
	 */
	@RequestMapping(value = "saveBesMonthlyReport", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveBesMonthlyReport(@RequestBody BesMonthlyReportVO besMonthlyReport) {
		int count = besMonthlyReportService.saveBesMonthlyReport(besMonthlyReport);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 *  检测是否发送月报
	 */
	@RequestMapping(value = "checkSendMonthlyReport", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult checkSendMonthlyReport(@RequestBody BesMonthlyReportVO besMonthlyReport) {
		besMonthlyReportService.saveOrCheckSendMonthlyReport();
		return new JsonResult(true);
	}
	
	/**
	 *  检测是否完成工作计划
	 */
	@RequestMapping(value = "checkIfCompletedWorkPlan", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult checkIfCompletedWorkPlan(@RequestBody BesMonthlyReportVO besMonthlyReport) {
		besMonthlyReportService.checkIfCompletedWorkPlan();
		return new JsonResult(true);
	}
	
	/**
	 * 删除 商户月报信息表 
	 */
	@RequestMapping(value = "deleteBesMonthlyReport/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteBesMonthlyReport(@PathVariable int id) {
		int count = besMonthlyReportService.deleteBesMonthlyReportById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}

    /**
     * 月报统计信息
     * @return
     */
    @RequestMapping(value = "statistics")
    @ResponseBody
    public JsonResult statistics(@RequestBody MonthlyStatisticDTO monthlyStatisticDTO,HttpSession session) {
        String isSent = "00000110";//发送
        String isCallback = "00000109";//回访
        String isExplain = "00000111";//讲解
        String isSatisfied = "00000108";//满意度


        // 是否禁用等信息
        String callbackVal = monthlyStatisticDTO.getValue(isCallback);
        String explainVal = monthlyStatisticDTO.getValue(isExplain);
        // 1.当选择未讲解时，回访和满意两栏全部禁用
        boolean isCallbackDisable = "3".equals(explainVal);//未讲解
        // 2. 当选择未回访活放弃回访时，满意一栏禁用
        boolean isSatisfiedDisable = isCallbackDisable
                || ArrayUtils.contains(new String[]{"2", "3"}, callbackVal);
        // 禁用条件
        if (CollectionUtils.isNotEmpty(monthlyStatisticDTO.getConditions())) {
            Iterator<MonthlyConditionDTO> dictionaryVOIterator = monthlyStatisticDTO.getConditions().iterator();
            while (dictionaryVOIterator.hasNext()) {
                MonthlyConditionDTO conditionDTO = dictionaryVOIterator.next();
                if (isCallbackDisable && (isCallback.equals(conditionDTO.getType()) || isSatisfied.equals(conditionDTO.getType()))) {
                    dictionaryVOIterator.remove();
                    continue;
                }
                if (isSatisfiedDisable && isSatisfied.equals(conditionDTO.getType())) {
                    dictionaryVOIterator.remove();
                }
            }
        }

        // 返回条件集合
        JsonResult jsonResult = new JsonResult();
        List<Object> conditions = new ArrayList<Object>();

        //条件信息
        Map<String, Object[]> conditionValues = new HashMap<String, Object[]>();
        conditionValues.put(isExplain, new Object[]{"讲解", false});
        conditionValues.put(isCallback, new Object[]{"回访", isCallbackDisable});
        conditionValues.put(isSent, new Object[]{"发送", false});
        conditionValues.put(isSatisfied, new Object[]{"满意", isSatisfiedDisable});
        Map<String, List<SysDictionaryVO>> dictionariesMap = this.sysDictionaryService.queryDictionariesByTypes(new ArrayList<String>(conditionValues.keySet()));

        for (Map.Entry<String, Object[]> entry : conditionValues.entrySet()) {
            Map<String, Object> tmpCondition = new HashMap<String, Object>();
            tmpCondition.put("title", entry.getValue()[0]);
            tmpCondition.put("type", entry.getKey());
            List<SysDictionaryVO> dictionaryVOs = dictionariesMap.get(entry.getKey());
            boolean isDisable = (Boolean) entry.getValue()[1];
            String val = monthlyStatisticDTO.getValue(entry.getKey());
            List<ConditionResultDTO> conditionResultDTOs = this.getConditionDtos(dictionaryVOs, StringUtils.defaultIfEmpty(val, "0"), isDisable);
            tmpCondition.put("values", conditionResultDTOs);
            conditions.add(tmpCondition);
        }

        jsonResult.putData("conditions", conditions.toArray());

        Integer userId = this.getUserId(session);
        Integer checkDate = this.getOrUpdateCheckDate(monthlyStatisticDTO.getCheckDate(),userId);
        jsonResult.putData("checkDate", checkDate);

        //  条件查询 添加中文信息
        if (CollectionUtils.isNotEmpty(monthlyStatisticDTO.getConditions())) {
            for (MonthlyConditionDTO conditionDTO : monthlyStatisticDTO.getConditions()) {
                List<SysDictionaryVO> vos = dictionariesMap.get(conditionDTO.getType());
                if (CollectionUtils.isNotEmpty(vos)) {
                    for (SysDictionaryVO vo : vos) {
                        if (conditionDTO.getValue().equals(vo.getDictionaryKey())) {
                            conditionDTO.setTitle(vo.getDictionaryValue());
                            break;
                        }
                    }
                }
            }
            jsonResult.putData("selected", monthlyStatisticDTO.getConditions());
        }else{
           jsonResult.putData("selected", new Object[]{});
        }

        //用于过滤条件
        Map<String, String> dictionary = new HashMap<String, String>();
        dictionary.put(isSent, "3");
        dictionary.put(isCallback, "2");
        dictionary.put(isExplain, "4");
        dictionary.put(isSatisfied, "1");

        Integer pageSize = monthlyStatisticDTO.getPageSize();
        Integer pageNumber = monthlyStatisticDTO.getPageNumber();
        PageHelper.startPage(pageNumber, pageSize, true, true);

        // 条件过滤
        List<MonthlyConditionVO> monthlyConditionVOs = new ArrayList<MonthlyConditionVO>();
        if (CollectionUtils.isNotEmpty(monthlyStatisticDTO.getConditions())) {
            for (MonthlyConditionDTO dto : monthlyStatisticDTO.getConditions()) {
                if (!dto.getValue().equals("0")) {
                    MonthlyConditionVO vo = new MonthlyConditionVO();
                    String accessFactor = dictionary.get(dto.getType());
                    vo.setAccessFactor(accessFactor);
                    vo.setStatus(dto.getValue());
                    monthlyConditionVOs.add(vo);
                }
            }
        }

        // 分两步: 过滤merchant_id,有分页信息、排序
        Page<Integer> pager = this.besMonthlyReportService.queryMonthlyReports(monthlyConditionVOs,
                monthlyStatisticDTO.getMerchantName(),
                new Date(monthlyStatisticDTO.getBeginTime()),
                new Date(monthlyStatisticDTO.getEndTime()),checkDate);

        Map<String, Object> pagination = new HashMap<String, Object>();
        pagination.put(Constant.TOTAL_SIZE, pager.getTotal());
        if (pager.getResult().size() > 0) {
            // 对结果集进行处理
            List<Map<String, Object>> resultSet = this.besMonthlyReportService.getMonthlyReports(pager.getResult());
            Map<Integer, String> merchantIdToUserName = userMerchantService.getFesUserByMerchantIds(pager.getResult());
            Map<Integer, String> merchantIdToContact = this.besMonthlyReportService.getMerchantContact(new Date(monthlyStatisticDTO.getBeginTime()),
                    new Date(monthlyStatisticDTO.getEndTime()));
            resultSet = this.toZh(resultSet, dictionariesMap,merchantIdToUserName,merchantIdToContact);
            pagination.put(Constant.ROWS, resultSet);
        } else {
            pagination.put(Constant.ROWS, new Object[]{});
        }
        return jsonResult.setFlag(true).putData("resultSet", pagination);
    }

    /**
     * 对结果集进行处理
     * TODO 重新理顺
     * @param resultSet
     * @param dictionary
     * @return
     */
    private List<Map<String, Object>> toZh(List<Map<String, Object>> resultSet, Map<String, List<SysDictionaryVO>> dictionary,Map<Integer, String> merchantIdToUserName,Map<Integer, String> merchantIdToContact) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, String> columnToType = new HashMap<String, String>();
        columnToType.put("satisfied", "00000108");
        columnToType.put("explained", "00000111");
        columnToType.put("called", "00000109");
        columnToType.put("sent", "00000110");
        Set<String> allKeywords = new HashSet<String>(columnToType.keySet());
        allKeywords.add("calledtime");
        allKeywords.add("explainedtime");
        allKeywords.add("merchantname");
        for (Map<String, Object> row : resultSet) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            for (String keyword : allKeywords) {
                if (row.containsKey(keyword)) {//数据行
                    //需要中文处理
                    if (columnToType.keySet().contains(keyword)) {
                        String dictionaryType = columnToType.get(keyword);
                        String zhTitle = null;
                        Object val = row.get(keyword);
                        List<SysDictionaryVO> dictionaryVOs = dictionary.get(dictionaryType);
                        for (SysDictionaryVO vo : dictionaryVOs) {
                            if (vo.getDictionaryKey().equals(val)) {
                                zhTitle = vo.getDictionaryValue();
                                resultMap.put(keyword, zhTitle);
                                break;
                            }
                        }
                    } else {
                        resultMap.put(keyword, row.get(keyword));
                    }
                } else {
                    resultMap.put(keyword, null);
                }
            }
            Integer merchantId = (Integer) row.get("merchantid");
            resultMap.put("username", merchantIdToUserName.get(merchantId));
            resultMap.put("contact", merchantIdToContact.get(merchantId));
            resultList.add(resultMap);
        }
        return resultList;
    }

    private Integer getOrUpdateCheckDate(String checkDate,Integer userId){
        String key = BES_MONTHLY_CHECK_DATE;
        String value = null;
        if (StringUtils.isEmpty(checkDate)) {
            value = this.sysConfigService.getSysConfigValueByKey(key);
        } else {
            value = this.sysConfigService.setSysConfigValueByKey(key, checkDate, userId);
        }
        return Integer.parseInt(value);
    }

    /**
     * 转换条件，设置值
     * @param dictionaryVOs
     * @param val
     * @param isEnable
     * @return
     */
    private List<ConditionResultDTO> getConditionDtos(List<SysDictionaryVO> dictionaryVOs, String val, boolean isEnable) {
        List<ConditionResultDTO> sentDtos = this.toConditionResultDTOs(dictionaryVOs);
        return this.getConditionResultDTOS(sentDtos, val, isEnable);
    }

    /**
     * 设置条件的选中状态
     * @param dtos
     * @param val
     * @param isDisable
     * @return
     */
    private List<ConditionResultDTO> getConditionResultDTOS(List<ConditionResultDTO> dtos, String val, boolean isDisable) {
        if (CollectionUtils.isEmpty(dtos)) {
            return Collections.emptyList();
        }
        for (ConditionResultDTO dto : dtos) {
            if (isDisable) {
                dto.setEnable("0");//不可选
            }else if (dto.getValue().equals(val)) {
                dto.setEnable("1");//选择状态
            }else{
                dto.setEnable("2");//可选
            }
        }
        return dtos;
    }

    /**
     * 将VO对象转换成DTO对象
     */
    private List<ConditionResultDTO> toConditionResultDTOs(List<SysDictionaryVO> dictionaryVOs) {
        if (CollectionUtils.isEmpty(dictionaryVOs)) {
            return Collections.EMPTY_LIST;
        }
        List<ConditionResultDTO> result = new ArrayList<ConditionResultDTO>();
        ConditionResultDTO dto = new ConditionResultDTO();
        dto.setTitle("全部");
        dto.setValue("0");
        dto.setEnable("2");
        result.add(dto);
        for (SysDictionaryVO vo : dictionaryVOs) {
            dto = new ConditionResultDTO();
            dto.setTitle(vo.getDictionaryValue());
            dto.setValue(vo.getDictionaryKey());
            dto.setEnable("2");
            result.add(dto);
        }
        return result;
    }
}

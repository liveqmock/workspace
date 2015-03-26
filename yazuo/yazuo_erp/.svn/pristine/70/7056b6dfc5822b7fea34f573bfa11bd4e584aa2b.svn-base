/**
 * @Description 填单题目集相关接口实现
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.stereotype.Service;

import com.yazuo.erp.bes.service.BesRequirementService;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.system.dao.SysQuestionDao;
import com.yazuo.erp.system.dao.SysQuestionOptionDao;
import com.yazuo.erp.system.exception.SystemBizException;
import com.yazuo.erp.system.service.SysQuestionService;
import com.yazuo.erp.system.vo.SysQuestionOptionVO;
import com.yazuo.erp.system.vo.SysQuestionVO;
import static junit.framework.Assert.*;

@Service
public class SysQuestionServiceImpl implements SysQuestionService {

	@Resource
	private SysQuestionDao sysQuestionDao;

	@Resource private SysQuestionOptionDao sysQuestionOptionDao;
	@Resource private BesRequirementService besRequirementService;
	
	public int saveSysQuestion(SysQuestionVO sysQuestion) {
		return sysQuestionDao.saveSysQuestion(sysQuestion);
	}

	public int batchInsertSysQuestions(Map<String, Object> map) {
		return sysQuestionDao.batchInsertSysQuestions(map);
	}

	public int updateSysQuestion(SysQuestionVO sysQuestion) {
		return sysQuestionDao.updateSysQuestion(sysQuestion);
	}

	public int batchUpdateSysQuestionsToDiffVals(Map<String, Object> map) {
		return sysQuestionDao.batchUpdateSysQuestionsToDiffVals(map);
	}

	public int batchUpdateSysQuestionsToSameVals(Map<String, Object> map) {
		return sysQuestionDao.batchUpdateSysQuestionsToSameVals(map);
	}

	public int deleteSysQuestionById(Integer id) {
		return sysQuestionDao.deleteSysQuestionById(id);
	}

	public int batchDeleteSysQuestionByIds(List<Integer> ids) {
		return sysQuestionDao.batchDeleteSysQuestionByIds(ids);
	}

	public SysQuestionVO getSysQuestionById(Integer id) {
		return sysQuestionDao.getSysQuestionById(id);
	}

	public List<SysQuestionVO> getSysQuestions(SysQuestionVO sysQuestion) {
		return sysQuestionDao.getSysQuestions(sysQuestion);
	}

	public List<Map<String, Object>> getSysQuestionsMap(SysQuestionVO sysQuestion) {
		return sysQuestionDao.getSysQuestionsMap(sysQuestion);
	}

    @Override
    public List<Map<String, Object>> querySysQuestionList(SysQuestionVO sysQuestion) {
        return this.querySysQuestionList(sysQuestion, null);
    }


	/**
	 * @Title querySysQuestionList
	 * @Description 查询填单题目集
	 * @param sysQuestion
	 * @return
	 * @see com.yazuo.erp.system.service.SysQuestionService#querySysQuestionList(com.yazuo.erp.system.vo.SysQuestionVO)
	 */
	@Override
	public List<Map<String, Object>> querySysQuestionList(SysQuestionVO sysQuestion,Date planDate) {
		String documentType = sysQuestion.getDocumentType(); 
		// 填单类型，1-实施培训回访单，2-门店收银回访单，3-门店财务回访单 4-回访需求单
		if (!Arrays.asList("1","2","3","4").contains(documentType)) {
			throw new SystemBizException("填单类型异常，请检查");
		}
		List<Map<String, Object>> sysQuestionList = sysQuestionDao.getSysQuestionsMap(sysQuestion);
		
		//change to standard attribute;// 去掉手动转换，有前端处理
//		for (Map<String, Object> map : sysQuestionList) {
//			map.put("documentType", map.get("question_type"));
//			map.put("questionType", map.get("question_type"));
//		}

        if (planDate != null && "4".equals(documentType)) {  //替换 月报讲解时间
            replacePlanEndTime(sysQuestion, sysQuestionList,planDate );
        }

        if (null == sysQuestionList) {
			throw new SystemBizException("未查询到填单题目信息");
		}
		for (Map<String, Object> map : sysQuestionList) {
			String questionType = (String) map.get("question_type");
			if ("1".equals(questionType) || "2".equals(questionType)) {// 1-单选，2-多选，3-问答
				Integer questionId = (Integer) map.get("id");
				SysQuestionOptionVO sysQuestionOption = new SysQuestionOptionVO();
				sysQuestionOption.setQuestionId(questionId);
				List<SysQuestionOptionVO> sysQuestionOptionList = sysQuestionOptionDao.getSysQuestionOptions(sysQuestionOption);

				map.put("sysQuestionOptionList", sysQuestionOptionList);
			}
		}
		return sysQuestionList;
	}

    @Override
    public Date getExplainTime(Integer merchantId, Date planDate) {
        List<FesPlanVO> fesPlanVOs = this.besRequirementService.getFesPlanForReq(merchantId, planDate);
        if (fesPlanVOs.size() == 1) {
            return fesPlanVOs.get(0).getInsertTime();
        }else {
            return null;
        }
    }

	/**
	 *  替换 月报讲解时间
	 */
	private void replacePlanEndTime(SysQuestionVO sysQuestion, List<Map<String, Object>> sysQuestionList, Date planDate) {
			Integer merchantId = sysQuestion.getMerchantId();
			assertNotNull("商户ID能为空!", merchantId);
			List<FesPlanVO> fesPlanForReq = this.besRequirementService.getFesPlanForReq(merchantId,planDate);
			assertTrue("工作计划不存在!", fesPlanForReq.size()>0);
			final FesPlanVO fesPlanVO = fesPlanForReq.get(0);
			String endTime = "异常！工作计划未完成!";
        String month = "";
        if(fesPlanVO.getEndTime() != null){
		        endTime = FastDateFormat.getInstance("yyyy-MM-dd").format(fesPlanVO.getEndTime());
            month = FastDateFormat.getInstance("M").format(DateUtils.addMonths(fesPlanVO.getEndTime(),-1));
        }
			final String tempTime = endTime;
        final String monthStr = month;
        CollectionUtils.collect(sysQuestionList.iterator(), new Transformer() {
				@Override
				public Object transform(Object input) {
					@SuppressWarnings("unchecked")
					Map<String, Object> map =  (Map<String, Object>)input;
//					System.out.println(map);
					if(Integer.parseInt(map.get("id").toString())==21){
						map.put("title", map.get("title").toString().replace("@replace", tempTime));
                        map.put("title", map.get("title").toString().replace("@lastMonth", monthStr));
                        return map;
					}
					return input;
				} 
			});
	}
}

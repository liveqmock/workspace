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
package com.yazuo.erp.train.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.syn.vo.SynHealthDegreeVO;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;

/**
 * @Description TODO
 * @author gaoshan
 * @date 2014-11-20 下午1:39:26
 */
@Repository
public class TraLearningProgressDao extends BaseDAO {

	/**
	 * @Description 查询所有的学习进度
	 * @return
	 * @return List<TraLearningProgressVO>
	 * @throws
	 */
	public List<TraLearningProgressVO> getAllTraLearningProgresss() {
		StringBuffer sb = new StringBuffer(128);
		sb.append(" select * from train.tra_learning_progress l where l.course_status in ('0', '1') and l.course_end_time < now() ");
		return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<TraLearningProgressVO>(TraLearningProgressVO.class));
	}

	/**
	 * @Description 更新课程的结束时间
	 * @param learningProgressVO
	 * @return void
	 * @throws
	 */
	public int updateCourseStatus(TraLearningProgressVO learningProgressVO) {
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE train.tra_learning_progress SET ");
		sb.append("   course_status = ? ,                  ");
		sb.append("   update_by = ? ,                      ");
		sb.append("   update_time = now()                  ");
		sb.append(" WHERE                                  ");
		sb.append("   id = ?                               ");
		return jdbcTemplate.update(sb.toString(), learningProgressVO.getCourseStatus(), learningProgressVO.getUpdateBy(),
				learningProgressVO.getId());
	}

}

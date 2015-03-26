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
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description TODO
 * @author gaoshan
 * @date 2014-11-20 下午1:39:26
 */
@Repository
public class TraTeacherStudentDao extends BaseDAO {

	/**
	 * @Description 根据学生ID得到老师的ID
	 * @param studentId
	 * @return
	 * @return Integer
	 * @throws
	 */
	public List<TraTeacherStudentVO> getTeacherIdByStudentId(Integer studentId) { 
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM train.tra_teacher_student WHERE student_id= " + studentId + " AND is_enable='1' ");
		return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<TraTeacherStudentVO>(TraTeacherStudentVO.class));
	}

}

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
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.syn.vo.SynHealthDegreeVO;
import com.yazuo.erp.train.vo.TraStudentRecordVO;

/**
 * @Description TODO
 * @author gaoshan
 * @date 2014-11-20 下午1:39:26
 */
@Repository
public class TraStudentRecordDao extends BaseDAO {

	/**
	 * @Description 保存学习流水
	 * @param studentRecordVO
	 * @return void
	 * @throws
	 */
	public int saveTraStudentRecord(TraStudentRecordVO studentRecordVO) {
		StringBuffer sb = new StringBuffer(128);
		sb.append(" INSERT INTO                ");
		sb.append(" train.tra_student_record ( ");
		sb.append("    	 student_id,          ");
		sb.append("    	 teacher_id,          ");
		sb.append("    	 course_id,           ");
		sb.append("    	 courseware_id,       ");
		sb.append("    	 operating_type,      ");
		sb.append("    	 begin_time,          ");
		sb.append("    	 end_time,            ");
		sb.append("    	 description,         ");
		sb.append("    	 is_timeout,          ");
		sb.append("    	 insert_by,           ");
		sb.append("    	 insert_time,         ");
		sb.append("    	 update_by,           ");
		sb.append("    	 update_time,         ");
		sb.append("    	 learning_progress_id ");
		sb.append("    ) VALUES (             ");
		sb.append(studentRecordVO.getStudentId()).append(" , ");
		sb.append(studentRecordVO.getTeacherId() == null ? "null" : studentRecordVO.getTeacherId()).append(" , ");
		sb.append(studentRecordVO.getCourseId()).append(", ");
		sb.append(studentRecordVO.getCoursewareId() == null ? "null" : studentRecordVO.getCoursewareId()).append(", ");
		sb.append("'").append(studentRecordVO.getOperatingType()).append("'").append(", ");
		sb.append("'").append(studentRecordVO.getBeginTime()).append("'").append(", ");
		sb.append("'").append(studentRecordVO.getEndTime()).append("'").append(", ");
		sb.append("'").append(studentRecordVO.getDescription()).append("'").append(", ");
		sb.append("'").append(studentRecordVO.getIsTimeout()).append("'").append(", ");
		sb.append(studentRecordVO.getInsertBy()).append(", ");
		sb.append(" now(), ");
		sb.append(studentRecordVO.getUpdateBy()).append(" , ");
		sb.append(" now(), ");
		sb.append(studentRecordVO.getLearningProgressId()).append(" ) ");
		return jdbcTemplate.update(sb.toString());
	}

}

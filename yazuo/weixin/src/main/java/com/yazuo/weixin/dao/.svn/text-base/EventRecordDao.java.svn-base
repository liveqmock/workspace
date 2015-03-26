package com.yazuo.weixin.dao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.vo.EventRecordVO;

@Repository
public class EventRecordDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Log log = LogFactory.getLog("eventRecord");
	
	public void insertEventRecord(EventRecordVO eventRecord) {
		
		log.info("记录日志信息："+eventRecord.toString());
//		String sql = "INSERT INTO weixin.event_record(to_username, from_username, event_id, receive_content,reply_content,receive_type,reply_type,message_time,process_time) values (?,?,?,?,?,?,?,?,?)";
//		try {
//			return jdbcTemplate.update(sql, eventRecord.getToUsername(),
//					eventRecord.getFromUsername(), eventRecord.getEventId(),
//					eventRecord.getReceiveContent(),
//					eventRecord.getReplyContent(), eventRecord.getReceiveType(),
//					eventRecord.getReplyType(),eventRecord.getMessageTime(),eventRecord.getProcessTime()) == 1 ? true : false;
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//			return false;
//		}
	}
}

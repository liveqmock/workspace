package com.yazuo.weixin.service;

import java.util.List;

import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.SummaryDetail;

public interface AdministratorInterfaceService {
	public Message message(Message messageIn,String path);

	public void eventRecord(EventRecordVO eventRecord);
	
	public List<SummaryDetail> summaryDetail(String brandId, String startDate,
			String endDate, Integer detailType);
}

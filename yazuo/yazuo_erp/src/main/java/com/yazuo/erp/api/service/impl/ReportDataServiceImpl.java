package com.yazuo.erp.api.service.impl;

import com.yazuo.erp.api.dao.ReportDataDao;
import com.yazuo.erp.api.dao.SysReportParamsDao;
import com.yazuo.erp.api.service.ReportDataService;
import com.yazuo.erp.api.vo.MonthlyStatVO;
import com.yazuo.erp.api.vo.SysReportParams;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 */
@Service
public class ReportDataServiceImpl implements ReportDataService {

    @Resource
    private SysReportParamsDao sysReportParamsDao;
    @Resource
    private ReportDataDao reportDataDao;


    @Value("${elasticsearch.prefix}")
    private String esPrefix;

    @Override
    public Map<String, Object> getMarketingReportData(String key) {
        SysReportParams reportParams = this.sysReportParamsDao.get(key);
        Integer merchantId = reportParams.getMerchantId();
        boolean isExpired = DateUtils.addMonths(new Date(), -2).after(reportParams.getReportTime());
        Date currentMonth = reportParams.getReportTime();
        Date nextMonth = DateUtils.addMonths(currentMonth, 1);
        Map<String, Object> result = new HashMap<String, Object>();
        if (!isExpired) {
            Long t1 = System.currentTimeMillis();
            MonthlyStatVO statVO = this.reportDataDao.getStatForBrand(merchantId, currentMonth, nextMonth);
            result.put("month", currentMonth);
            result.put("storedValueOfMon", statVO.getStorePay());
            result.put("membershipNumOfMon", statVO.getNewMember());
            result.put("marketingIncome", statVO.getRealMarketingIncome());

            MonthlyStatVO formerStatVO = this.reportDataDao.getStatForBrand(merchantId, DateUtils.addMonths(currentMonth, -1), currentMonth);
            result.put("storedValueOfLastMon", formerStatVO.getStorePay());
            result.put("membershipNumOfLastMon", formerStatVO.getNewMember());

            result.put("storedValueOfFormer", this.reportDataDao.getStoreBalance(merchantId,nextMonth));
            result.put("OpenNumPercent", this.reportDataDao.getOpenNumPercent(merchantId, currentMonth));

            MonthlyStatVO currentYearStatVO = this.reportDataDao.getStatForBrand(merchantId, DateUtils.truncate(currentMonth,Calendar.YEAR), nextMonth);
            result.put("membershipNumOfYear", currentYearStatVO.getNewMember());
        }
        result.put("expired", isExpired);
        return result;
    }
}

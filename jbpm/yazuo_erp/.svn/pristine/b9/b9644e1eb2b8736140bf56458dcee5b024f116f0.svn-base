package com.yazuo.erp.bes.dto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.List;

/**
 *
 */
public class MonthlyStatisticDTO {
    private int pageSize;
    private int pageNumber;
    private long beginTime;
    private String checkDate;
    private String merchantName;
    List<MonthlyConditionDTO> conditions;

    public String getValue(String conditionType) {
        if (CollectionUtils.isEmpty(conditions)) {
            return null;
        }else {
            for (MonthlyConditionDTO dto : conditions) {
                if (conditionType.equals(dto.getType())) {
                    return dto.getValue();
                }
            }
        }
        return null;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 本月的结束日期
     * @return
     */
    public long getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.beginTime);
        return DateUtils.addMonths(calendar.getTime(),1).getTime();
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public List<MonthlyConditionDTO> getConditions() {
        return conditions;
    }

    public void setConditions(List<MonthlyConditionDTO> conditions) {
        this.conditions = conditions;
    }
}

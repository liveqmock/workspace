package com.yazuo.erp.superReport.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 超级报表监控服务类
 */
public interface NewSuperReportService {
    public enum Function {
        SEND_REPORT("发送日报", "SEND_REPORT"),
        VIEW_REPORT("查看日报", "VIEW_REPORT"),
        SEND_COMMEND("发送评论", "SEND_COMMEND"),
        VIEW_COMMEND("查看评论", "VIEW_COMMEND"),
        VIEW_NOTICE("查看通知", "VIEW_COMMEND");

        Function(String title, String val) {
           //TODO 设置值
        }
    }


    /** 得到用户统计信息*/
    JSONObject getUserInfo(String beginDate, String endDate);

    /** 今天或某个时间点用户统计信息 */
    JSONArray getUserInfoByDate(boolean isNew, String date);

    /** 按起始结束日期得到用户统计信息*/
    JSONArray getUserInfoByDate(boolean isNew, String fromDate, String endDate);

    /** 得到商户的统计信息*/
    JSONObject getBrandInfo(String beginDate, String endDate);

    /**今天或某个时间点商户统计信息*/
    JSONObject getBrandInfoByDate(boolean isNew, String date);
    
    /**统计某个时间段按各种方式统计个角色用户占比*/
    Object getUserChartByDatePeriod(String startTime, String endTime, String queryType, String queryFlag);
    
    /**统计某个时间段品牌各项数量*/
    JSONObject getBrandByDatePeriod(String startTime, String endTime);

    /**得到人数统计信息 */
    Object getUserCountInfo(String startTime,String endTime);

    /** 得到人次统计信息 */
    Object getUserTimesInfo(String startTime, String endTime);
    
    /**统计图表点击数*/
    Object getPicTableCount(String startTime, String endTime);
    
    /**统计发送数据量*/
    Object getSendCount(String startTime, String endTime);

    /** 得到指定功能与日期的人数统计信息*/
    JSONObject getUserCountByFunc(String FuncName, String beginDate, String endDate, String queryFlag);

    /** 得到指定功能与日期的人次统计信息 */
    JSONObject getUserTimesByFunc(String FuncName, String beginDate, String endDate, String queryFlag);
    
    /**得到指定功能与日期的图表点击数统计信息*/
    JSONObject getPicCountByFunc(String FuncName, String beginDate, String endDate, String queryFlag);
    
    /**得到指定功能与日期的发送通知统计信息*/
    JSONObject getSendAdviceByFunc(String FuncName, String beginDate, String endDate, String queryFlag);
}

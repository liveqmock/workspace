package com.yazuo.erp.superReport.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * 超级报表监控服务类
 */
public interface SuperReportService {
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


    /**
     * 得到用户统计信息
     *
     * @return
     */
    JSONObject getUserInfo();

    /**
     * 得到指定日期的用户统计信息
     *
     * @param isNew
     * @param date
     * @return
     */
    JSONArray getUserInfoByDate(boolean isNew, Date date);

    /**
     * 按起始日期得到用户统计信息
     *
     * @param isNew
     * @param fromDate
     * @param endDate
     * @return
     */
    JSONArray getUserInfoByDate(boolean isNew, Date fromDate, Date endDate);

    /**
     * 得到商户的统计信息
     *
     * @return
     */
    JSONObject getBrandInfo();

    /**
     * 得到指定日期的商户统计信息
     *
     * @param isNew
     * @param date
     * @return
     */
    JSONObject getBrandInfoByDate(boolean isNew, Date date);

    /**
     * 得到人数统计信息
     *
     * @return
     */
    JSONObject getUserCountInfo();

    /**
     * 得到人次统计信息
     *
     * @return
     */
    JSONObject getUserTimesInfo();

    /**
     * 得到指定功能与日期的人数统计信息
     *
     * @param FuncName
     * @param date
     * @return
     */
    JSONArray getUserCountByFunc(String FuncName, Date date);

    /**
     * 得到指定功能与日期的人次统计信息
     *
     * @param FuncName
     * @param date
     * @return
     */
    JSONObject getUserTimesByFunc(String FuncName, Date date);
}

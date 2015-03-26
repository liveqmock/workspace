package com.yazuo.erp.superReport.service.impl;

import com.yazuo.erp.superReport.service.SuperReportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SuperReportServiceImpl implements SuperReportService {
    Log log = LogFactory.getLog(this.getClass());
    @Value("${superReport.url.prefix}")
    private String superReportUrlPrefix;
    private String controllerUrlPrefix;

    @Override
    public JSONObject getUserInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",this.actionToJsonObject("/getUserAndMerchantTitleInfo.do"));
        jsonObject.put("statistics", this.actionToJsonObject("/getUserInfo.do"));
        return jsonObject;
    }

    @Override
    public JSONArray getUserInfoByDate(boolean isNew, Date date) {
        return this.actionToJsonArray("/getUserChart.do?isNew=" + isNew + "&time=" + this.formatDate(date));
    }

    @Override
    public JSONArray getUserInfoByDate(boolean isNew, Date fromDate, Date endDate) {
        return this.actionToJsonArray("/getUserChart.do?isNew" + isNew + "&startTime=" + this.formatDate(fromDate) + "&endTime=" + this.formatDate(endDate));
    }

    @Override
    public JSONObject getBrandInfo() {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("total", this.actionToJsonObject("/getUserAndMerchantTitleInfo.do"));
        jsonObject.put("stat", this.actionToJsonObject("/getMerchantInfo.do"));
        return jsonObject;
    }

    @Override
    public JSONObject getBrandInfoByDate(boolean isNew, Date date) {
        return this.actionToJsonObject("/getMerchantChart.do?isNew=" + isNew + "&time=" + this.formatDate(date));
    }

    @Override
    public JSONObject getUserCountInfo() {
        return this.actionToJsonObject("/getAccessUrlUserCountInfo.do");
    }

    @Override
    public JSONObject getUserTimesInfo() {
        return this.actionToJsonObject("/getAccessUrlTimesInfo.do");
    }

    @Override
    public JSONArray getUserCountByFunc(String funcName, Date date) {
        String url = "/getAccessUrlUserCountChart.do?time=" + this.formatDate(date);
        if (funcName != null) {
            url = url + "&path=" + funcName;
        }
        return this.actionToJsonArray(url);
    }

    @Override
    public JSONObject getUserTimesByFunc(String funcName, Date date) {
        String url = "/getAccessUrlTimesChart.do?time=" + this.formatDate(date);
        if (funcName != null) {
            url = url + "&path=" + funcName;
        }
        return this.actionToJsonObject(url);
    }

    /**
     * URL的帮助方法，消除冗余代码
     * @param actionUrl
     * @return
     */
    private JSONObject actionToJsonObject(String actionUrl) {
        String url = this.getUrl(actionUrl);
        return this.getJsonObject(url);
    }

    /**
     * 将指定URL的响应体转换为JSON对象
     * @param url
     * @return
     */
    private JSONObject getJsonObject(String url) {
        String jsonStr = this.getResponseBody(url);
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject(jsonStr);
        } catch (JSONException jsonException) {
            log.error("url:" + url + ",data:" + jsonStr);
            jsonException.printStackTrace();
        }
        return jsonObject;
    }
    /**
     * URL的帮助方法，消除冗余代码
     * @param actionUrl
     * @return
     */
    private JSONArray actionToJsonArray(String actionUrl) {
        String url = this.getUrl(actionUrl);
        return this.getJsonArray(url);
    }

    /**
     * 将指定URL的响应体转换为JSON数组对象
     * @param url
     * @return
     */
    private JSONArray getJsonArray(String url) {
        String jsonStr = this.getResponseBody(url);
        JSONArray jsonObject = null;
        try {
            jsonObject = JSONArray.fromObject(jsonStr);
        } catch (JSONException jsonException) {
            log.error("url:" + url + ",data:" + jsonStr);
            jsonException.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 返回响应体的字符串
     *
     * @param url 待请求的URL
     * @return 返回响应的字符串
     */
    private String getResponseBody(String url) {
        HttpGet getRequest = new HttpGet(url);
        HttpClient httpclient = new DefaultHttpClient();
        String resultStr = null;
        try {
            long beginning = System.currentTimeMillis();
            HttpResponse httpResponse = httpclient.execute(getRequest);
            long end = System.currentTimeMillis();
            log.debug("url:" + url + ",用时:" +(end-beginning)+"ms");
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                resultStr = EntityUtils.toString(httpResponse.getEntity());
            } else {
                log.error("返回状态码不是200: 状态码:" + httpResponse.getStatusLine().getStatusCode() + ",URL:" + url);
            }
        } catch (IOException e) {
            log.error("请求错误，请求URL：" + url);
            e.printStackTrace();
            getRequest.abort();
        }finally {
            getRequest.releaseConnection();
        }
        return resultStr;
    }

    /**
     * 容错: 配置URL时以/为后缀
     *
     * @return 超级报表的URL
     */
    private String getSuperReportUrlPrefix() {
        if (superReportUrlPrefix.endsWith("/")) {
            this.superReportUrlPrefix = this.superReportUrlPrefix.replaceFirst("\\/$", "");
        }
        return this.superReportUrlPrefix;
    }

    /**
     * 得到Controller前缀,包含superReport报表应用的URL前缀
     * @return 超级报表的URL
     */
    private String getControllerUrlPrefix() {
        if (this.controllerUrlPrefix == null) {
            this.controllerUrlPrefix = this.getSuperReportUrlPrefix()+ "/controller/statistics";
        }
        return this.controllerUrlPrefix;
    }

    /**
     * 为actionUrl添加controller与应用前缀
     * @param actionUrl
     * @return
     */
    private String getUrl(String actionUrl) {
        return this.getControllerUrlPrefix() + actionUrl;
    }

    /**
     * 格式化日期格式
     *
     * @param date
     * @return
     */
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}

/*
 * 文件名：ProductUpdateTask.java 版权：Copyright by www.yazuo.com 描述： 修改人：zhaohuaqin 修改时间：2014-8-1 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.erp.schedule;


import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.springframework.beans.factory.annotation.Value;

import com.yazuo.task.BaseTask;


public class MerchantProductUpdateTask extends BaseTask
{
    Log log = LogFactory.getLog(this.getClass());

    @Value("#{configProperties['config.productUrl'] }")
    String producturl;

    @Override
    public void execute1(Map params)
    {
        try
        {
            // 服务调用
            HttpClient httpclient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
            HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
            String urlpath = producturl;
            log.info("发送请求：" + urlpath);
            HttpGet httpGet = new HttpGet(urlpath);
            HttpResponse response;
            response = httpclient.execute(httpGet);

            // 结果处理
            if (response.getStatusLine().getStatusCode() == 200)
            {
                log.info("请求成功");
            }
        }
        catch (Exception e)
        {
            log.error("请求服务调用失败.", e);
            log.error(e.getMessage());
        }

    }

}

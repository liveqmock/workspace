/*
 * 文件名：CityJson.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年12月31日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.merchant.vo;

import java.net.URLDecoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yazuo.superwifi.util.PropertyUtil;

public class CityJson
{
    
    
    private static JSONArray cityArray=null;
    
    public JSONArray getJson() throws Exception{
        if (cityArray==null){
            String cityJson=PropertyUtil.getInstance().getProperty("city.properties","cityjson");
            cityJson=new String(cityJson.getBytes("ISO-8859-1"),"utf-8");
            cityArray= JSONObject.fromObject(cityJson).getJSONArray("citylist");
        }
        return cityArray;
    }
    
}

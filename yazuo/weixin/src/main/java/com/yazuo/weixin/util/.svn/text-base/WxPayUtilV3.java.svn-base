package com.yazuo.weixin.util;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yazuo.weixin.exception.WeixinRuntimeException;


/**
* @ClassName WxPayUtilV3
* @Description 微信支付v3版
* @author sundongfeng@yazuo.com
* @date 2015-1-9 下午5:43:23
* @version 1.0
*/
@Component("WxPayUtilV3")
@Scope("prototype")
public class WxPayUtilV3 {
	private String appid="";
	private String apikey="";
	private String noncestr="";
	private String prepay_id="";
	
	/*订单详情*/
	public SortedMap<String, String> packageMap = new TreeMap<String,String>();
	/*设置订单详情*/
	public void SetParameter(String key, String value) {
		packageMap.put(key, value);
	}
	public String GetParameter(String key) {
		return packageMap.get(key);
	}
	
	/**
	 * 生成预支付签名
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public String createSign(SortedMap<String, String> packageParams) throws WeixinRuntimeException {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getApikey());
		String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
//		String sign =  MD5SignUtil.Sign(CommonUtil.FormatBizQueryParaMap(packageParams, false), this.getApikey());
		return sign;
	}
	
	/**
	 * 	作用：设置标配的请求参数，生成签名，生成接口参数xml
	 */
	public String createXml()
	{
	   return CommonUtil.ArrayToXml(packageMap);
	}
	/**
	 * 检查订单详情参数值是否为空
	 */
	public boolean checkPackageMap(){
		if(StringUtils.isEmpty(packageMap.get("body"))//商品描述
				||StringUtils.isEmpty(packageMap.get("mch_id"))//商户号
				||StringUtils.isEmpty(packageMap.get("out_trade_no"))//商户系统内部的订单号,32 个字符内、可包含字母,确保在商户系统唯一。
				||StringUtils.isEmpty(packageMap.get("total_fee"))//订单总金额，单位为分
				||StringUtils.isEmpty(packageMap.get("notify_url"))//通知URL,在支付完成后,接收微信通知支付结果的URL
				||StringUtils.isEmpty(packageMap.get("spbill_create_ip"))//订单生成的机器IP指用户浏览器端IP，不是商户服务器IP，格式为IPV4 整型。
				||StringUtils.isEmpty(packageMap.get("trade_type"))//
				||StringUtils.isEmpty(packageMap.get("openid"))//
				){
			return false;
		} 
			return true;
	}
	public String CreateBizPackage() throws WeixinRuntimeException {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (checkPackageMap() == false) {
			throw new WeixinRuntimeException("生成package参数缺失！");
		}
		String timeStamp=Long.toString(new Date().getTime()/1000);
		map.put("appId", appid);
		map.put("timeStamp", timeStamp);
		map.put("nonceStr", CommonUtil.CreateNoncestr());
		map.put("package", "prepay_id="+prepay_id);
		map.put("signType", "MD5");
		map.put("paySign",createSign(map));
		return JSONObject.fromObject(map).toString();

	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	
}

package com.yazuo.weixin.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yazuo.weixin.exception.WeixinRuntimeException;

/**
 * 组装数据，发送支付请求
 * @author sunny
 *
 */
@Component("WxpayUtil")
@Scope("prototype")
public class WxPayUtil {
	private String appid="";
	private String appkey="";
	private String productid="";
	private String partnerkey="";//商户号
	private String noncestr="";
	private String timeStamp="";
	
	/*订单详情*/
	private HashMap<String,String> packageMap = new HashMap<String,String>();
	/*设置订单详情*/
	public void SetParameter(String key, String value) {
		packageMap.put(key, value);
	}
	public String GetParameter(String key) {
		return packageMap.get(key);
	}
	
	/**
	 * 检查订单详情参数值是否为空
	 */
	public boolean checkPackageMap(){
		if(StringUtils.isEmpty(packageMap.get("bank_type")) //银行通道类型,使用的微信公众号支付，此字段固定为WX，注意大写 
				||StringUtils.isEmpty(packageMap.get("body"))//商品描述
				||StringUtils.isEmpty(packageMap.get("partner"))//商户号,即注册时分配的partnerId
				||StringUtils.isEmpty(packageMap.get("out_trade_no"))//商户系统内部的订单号,32 个字符内、可包含字母,确保在商户系统唯一。
				||StringUtils.isEmpty(packageMap.get("total_fee"))//订单总金额，单位为分
				||StringUtils.isEmpty(packageMap.get("fee_type"))//现金支付币种,取值：1（人民币）,默认值是1，暂只支持1
				||StringUtils.isEmpty(packageMap.get("notify_url"))//通知URL,在支付完成后,接收微信通知支付结果的URL
				||StringUtils.isEmpty(packageMap.get("spbill_create_ip"))//订单生成的机器IP指用户浏览器端IP，不是商户服务器IP，格式为IPV4 整型。
				||StringUtils.isEmpty(packageMap.get("input_charset"))//字符编码
				){
			return false;
		} 
			return true;
	}
	/**
	 * 根据规则 组装订单详情 
	 * @throws WeixinRuntimeException 
	 */
	public String getOrderPackage() throws WeixinRuntimeException{
		if("".equals(partnerkey)||"null".equals(partnerkey)||null==partnerkey){
			throw new WeixinRuntimeException("商户号不能为空");
		}
		/*不编码排序组装*/
		String string1 =CommonUtil.FormatBizQueryParaMap( packageMap, false);
		/*拼接上key=partnerKey*/
		String stringSignTemp = MD5SignUtil.Sign(string1,partnerkey);
		/*编码，拼接签名*/
		String string2 =CommonUtil.FormatBizQueryParaMap( packageMap, true);
		System.out.println("package:"+string2+"&sign="+stringSignTemp);
		/*生成订单详情返回*/
		return string2+"&sign="+stringSignTemp;
	}
	
	/**
	 * 生成native url
	 * @return
	 * @throws Exception 
	 */
	public String getNativeUrl()throws WeixinRuntimeException{
		HashMap<String,String> map = new HashMap<String,String>();
		String nativeUrl = "";
		try {
			map.put("appid",appid); //需要外部传入
			map.put("timestamp",Long.toString(System.currentTimeMillis()/1000));
			map.put("noncestr", CommonUtil.CreateNoncestr());
			map.put("productid", URLEncoder.encode(productid, "utf-8"));//需要外部传入，是否需要编码？
			map.put("sign", createSign(map));
			nativeUrl = CommonUtil.FormatBizQueryParaMap(map, false);
		} catch ( Exception ex) {
			throw new WeixinRuntimeException(ex.getMessage());
		}
		String url = "weixin://wxpay/bizpayurl?"+nativeUrl;
		System.out.println(url);
		return url;
	}
	
	private String createSign(HashMap<String,String> signMap) throws WeixinRuntimeException{
		HashMap<String, String> bizParameters = new HashMap<String, String>();

		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				signMap.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> item = infoIds.get(i);
			if (item.getKey() != "") {
				bizParameters.put(item.getKey().toLowerCase(), item.getValue());
			}
		}

		if (appkey == "") {
			throw new WeixinRuntimeException("APPKEY为空！");
		}
		bizParameters.put("appkey", appkey);
		String bizString = CommonUtil.FormatBizQueryParaMap(bizParameters,
				false);
		//System.out.println(bizString);

		return SHA1Util.Sha1(bizString);
	}
	
	// 生成jsapi支付请求json
		/*
		 * "appId" : "wxf8b4f85f3a794e77", //公众号名称，由商户传入
		 * "timeStamp" : "189026618", //时间戳这里随意使用了一个值 
		 * "nonceStr" : "adssdasssd13d", //随机串 
		 * "package" : "bank_type=WX&body=XXX&fee_type=1&input_charset=GBK&notify_url=http%3a%2f
		 *             %2fwww.qq.com&out_trade_no=16642817866003386000&partner=1900000109&spbill_create_ip=127.0.0.1&total_fee=1&sign=BEEF37AD19575D92E191C1E4B1474CA9", //扩展字段，由商户传入 
		 * "signType": "SHA1", //微信签名方式:sha1 
		 * "paySign" : "7717231c335a05165b1874658306fa431fe9a0de" //微信签名
		 */
		public String CreateBizPackage() throws WeixinRuntimeException {
			HashMap<String, String> map = new HashMap<String, String>();
			if (checkPackageMap() == false) {
				throw new WeixinRuntimeException("生成package参数缺失！");
			}
			map.put("appId", appid);
			map.put("package", getOrderPackage());
			map.put("timestamp", timeStamp);
			map.put("noncestr", noncestr);
			map.put("paySign",createSign(map));
			map.put("signType", "sha1");
			return JSONObject.fromObject(map).toString();

		}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	public String getPartnerkey() {
		return partnerkey;
	}
	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}

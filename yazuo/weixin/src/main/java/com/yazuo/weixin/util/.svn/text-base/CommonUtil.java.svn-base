package com.yazuo.weixin.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;
/**
* @ClassName CommonUtil
* @Description  工具类
* @author sundongfeng@yazuo.com
* @date 2014-6-10 上午11:53:00
* @version 1.0
*/
public class CommonUtil {
	private static final Log log = LogFactory.getLog("wxpay");
	private static final Log weixinLog = LogFactory.getLog("weixin");
	
	
	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String FormatQueryParaMap(HashMap<String, String> parameters)
			throws WeixinRuntimeException {

		String buff = "";
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					parameters.entrySet());

			Collections.sort(infoIds,
					new Comparator<Map.Entry<String, String>>() {
						public int compare(Map.Entry<String, String> o1,
								Map.Entry<String, String> o2) {
							return (o1.getKey()).toString().compareTo(
									o2.getKey());
						}
					});

			for (int i = 0; i < infoIds.size(); i++) {
				Map.Entry<String, String> item = infoIds.get(i);
				if (item.getKey() != "") {
					buff += item.getKey() + "="
							+ URLEncoder.encode(item.getValue(), "utf-8") + "&";
				}
			}
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new WeixinRuntimeException(e.getMessage());
		}

		return buff;
	}

	public static String FormatBizQueryParaMap(HashMap<String, String> paraMap,
			boolean urlencode) throws WeixinRuntimeException {

		String buff = "";
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					paraMap.entrySet());

			Collections.sort(infoIds,
					new Comparator<Map.Entry<String, String>>() {
						public int compare(Map.Entry<String, String> o1,
								Map.Entry<String, String> o2) {
							return (o1.getKey()).toString().compareTo(
									o2.getKey());
						}
					});

			for (int i = 0; i < infoIds.size(); i++) {
				Map.Entry<String, String> item = infoIds.get(i);
				//System.out.println(item.getKey());
				if (item.getKey() != "") {
					
					String key = item.getKey();
					String val = item.getValue();
					if (urlencode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					buff += key.toLowerCase() + "=" + val + "&";
				}
			}

			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new WeixinRuntimeException(e.getMessage());
		}
		return buff;
	}

	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

	public static String ArrayToXml(Map<String, String> arr) {
		String xml = "<xml>";
		
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)) {
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";

			} else{
				xml += "<" + key + ">" + val + "</" + key + ">";
			}
		}

		xml += "</xml>";
		return xml;
	}
	/**
	 * 将请求中数据转换成Map
	 * @param request
	 * @return map
	 */
	 public static Map<String, String> convertString(HttpServletRequest request) {
	        Map<String, String> map = new HashMap<String, String>();
	        for(Iterator iter = request.getParameterMap().entrySet().iterator(); iter.hasNext();){
	            Map.Entry e = (Entry) iter.next();
	            String key = String.valueOf(e.getKey());
	            String value = request.getParameter(key);
	            
	            if(!StringUtils.isBlank(value)){
	                map.put(key, value);
	            }
	        }
	        return map;
	    }
	 /**
	  * 发送http请求
	  * @param url 请求地址 
	  * @param content 发送内容
	  * @param key 标识
	  * @return 结果
	 * @throws WeixinRuntimeException 
	  */
	 public static String postSendMessage(String url,String input,String key) throws WeixinRuntimeException{
		 String result="";
		 try {
				// 服务调用
				input = URLEncoder.encode(input, "UTF-8");
				HttpClient httpclient = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
				HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
				String urlpath = url + "?ciphertext=" + input;
				if (!StringUtil.isNullOrEmpty(key)) {
					urlpath += "&key=" + key;
				}
				
				long startTime = System.currentTimeMillis();
				weixinLog.info("发送请求：" + urlpath);
				HttpGet httpGet = new HttpGet(urlpath);
				HttpResponse response;
				response = httpclient.execute(httpGet);

				// 结果处理
				if (response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(response.getEntity());
					if(url.indexOf("getMerchantChildListByMerchantId")==-1&&url.indexOf("getAreaListByMerchantId")==-1){
						weixinLog.info("请求响应结果：" + result);
					}
					String crmInterface = url.substring((url.lastIndexOf("/")+1),url.lastIndexOf("."));
					weixinLog.info("--访用CRM【"+crmInterface+"】接口...耗时::" + (System.currentTimeMillis() - startTime) + "毫秒");
				}
			} catch (Exception e) {
				weixinLog.error("请求服务调用失败.",e);
				weixinLog.error(e.getMessage());
				throw new WeixinRuntimeException("调用服务异常.",e);
			}
		 return result;
	 }
	 
	 /**试用crm老接口 参数为publicKey参数
	  *  发送http请求
	  * @param url 请求地址 
	  * @param content 发送内容
	  * @param key 标识
	  * @return 结果
	  * */
	 public static String postSendMessageForCrmOldInterface(String url,String input,String key) throws WeixinRuntimeException{
		 String result="";
		 try {
				// 服务调用
				input = URLEncoder.encode(input, "UTF-8");
				HttpClient httpclient = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
				HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
				String urlpath = url + "?ciphertext=" + input + "&publicKey=" + key;
				
				long startTime = System.currentTimeMillis();
				weixinLog.info("发送请求：" + urlpath);
				HttpGet httpGet = new HttpGet(urlpath);
				HttpResponse response;
				response = httpclient.execute(httpGet);

				// 结果处理
				if (response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(response.getEntity());
					if(url.indexOf("getMerchantChildListByMerchantId")==-1&&url.indexOf("getAreaListByMerchantId")==-1){
						weixinLog.info("请求响应结果：" + result);
					}
					String crmInterface = url.substring((url.lastIndexOf("/")+1),url.lastIndexOf("."));
					weixinLog.info("--访用CRM【"+crmInterface+"】接口...耗时::" + (System.currentTimeMillis() - startTime) + "毫秒");
				}
			} catch (Exception e) {
				weixinLog.error("请求服务调用失败.",e);
				weixinLog.error(e.getMessage());
				throw new WeixinRuntimeException("调用服务异常.",e);
			}
		 return result;
	 }
	 /**
	  * 发送http请求
	  * @param urlStr
	  * @param content
	  * @return
	  */
	 public static String postMessage(String urlStr, String content){
			
			URL url = null;
			HttpURLConnection connection = null;
			StringBuffer buffer = new StringBuffer();
			try {
				url = new URL(urlStr);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("Content-Type", "text/html");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				connection.setUseCaches(false);
				connection.connect();

				DataOutputStream out = new DataOutputStream(
						connection.getOutputStream());
				if(StringUtils.isNotBlank(content)){
					out.write(content.getBytes("utf-8"));
				}
				out.flush();
				out.close();
				int statecode = connection.getResponseCode();
				if(statecode==200){
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							connection.getInputStream(), "utf-8"));
					String line = "";
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}
					reader.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
			return buffer.toString();
		}
	 
	 public static String postMessage(String url)throws WeixinRuntimeException{
		 String result="";
		 try {
				// 服务调用
				HttpClient httpclient = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
				HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
				String urlpath = url;
				log.info("发送请求：" + urlpath);
				HttpGet httpGet = new HttpGet(urlpath);
				HttpResponse response;
				response = httpclient.execute(httpGet);

				// 结果处理
				if (response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(response.getEntity());
					log.info("请求响应结果：" + result);
				}
			} catch (Exception e) {
				log.error("请求服务调用失败.",e);
				log.error(e.getMessage());
				throw new WeixinRuntimeException("调用服务异常.",e);
			}
		 return result;
	 }
	 /**
	  * 原始post发送消息
	  * @param urlStr
	  * @param content
	  * @return
	  */
	 public static String postMessage2(String urlStr, String content){
		 log.info("发送请求：" + urlStr+"\n 发送内容："+content);
			URL url = null;
			HttpURLConnection connection = null;
			StringBuffer buffer = new StringBuffer();
			try {
				url = new URL(urlStr);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("Content-Type", "text/html");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.connect();

				DataOutputStream out = new DataOutputStream(
						connection.getOutputStream());
				if(StringUtils.isNotBlank(content)){
					out.write(content.getBytes("utf-8"));
				}
				out.flush();
				out.close();
				int statecode = connection.getResponseCode();
				if(statecode==200){
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							connection.getInputStream(), "utf-8"));
					String line = "";
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}
					reader.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
			return buffer.toString();
		}
	 /**
	  * 生成签名
	  * @param signMap
	  * @param appkey
	  * @return
	  * @throws WeixinRuntimeException
	  */
	 public static String createSign(HashMap<String,String> signMap,String appkey) throws WeixinRuntimeException{
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

			return SHA1Util.Sha1(bizString);
		}
	 
	 
	 /**
		 * 获取用户token
		 * @param appid
		 * @param appsecret
		 * @return
		 */
		public static String getToken(String appid,String appsecret) throws Exception{
			String access_token="";
			String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
			url+="&appid="+appid+"&secret="+appsecret;
			HttpClient httpclient = new DefaultHttpClient();
			httpclient = WebClientDevWrapper.getAllowAllClient(httpclient);
			// 获得HttpGet对象
			HttpGet httpGet = new HttpGet(url);
			// 发送请求
			HttpResponse response;
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				access_token = EntityUtils.toString(response.getEntity());
				JSONObject requestObject = JSONObject.fromObject(access_token);
				access_token = requestObject.getString("access_token");
			}
			return access_token;
		}
		/**
		 * 获取用户token
		 * @param appid
		 * @param appsecret
		 * @return
		 */
		public static JSONObject getToken2(String appid,String appsecret) throws Exception{
			JSONObject requestObject= null;
			String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
			url+="&appid="+appid+"&secret="+appsecret;
			log.info("发送请求：" + url);
			HttpClient httpclient = new DefaultHttpClient();
			httpclient = WebClientDevWrapper.getAllowAllClient(httpclient);
			// 获得HttpGet对象
			HttpGet httpGet = new HttpGet(url);
			// 发送请求
			HttpResponse response;
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String access_token = EntityUtils.toString(response.getEntity());
				log.info("token2请求响应：" + access_token);
				requestObject = JSONObject.fromObject(access_token);
			}
			return requestObject;
		}
		/**
		 * 发货通知
		 * @param token
		 * @return
		 * @throws WeixinRuntimeException 
		 */
		public static String deliverState(String token,WxPayDeliverStateVo order,String appkey) throws WeixinRuntimeException{
			String url="https://api.weixin.qq.com/pay/delivernotify?access_token="+token;
			HashMap<String,String> mp = new HashMap<String,String>();
			mp.put("appid",order.getApp_id());
			mp.put("openid",order.getOpen_id());
			mp.put("transid",order.getTransaction_id());
			mp.put("out_trade_no",order.getOut_trade_no());
			mp.put("deliver_timestamp",String.valueOf(new Date().getTime()/1000));
			mp.put("deliver_status","1");
			mp.put("deliver_msg","ok");
			mp.put("app_signature",CommonUtil.createSign(mp,appkey));//生成签名
			mp.put("sign_method", "sha1");
			log.info("发货json:"+JSONObject.fromObject(mp).toString());
			String content = JSONObject.fromObject(mp).toString();
			String result = HttpClientCommonSSL.commonPostStream(url, content);
			log.info("发货返回:"+result);
			return result;
		}
		/*解析request请求*/
		public static String parseRequest(HttpServletRequest request){
			StringBuffer query =request.getRequestURL();
			query.append("?");
			Map<String, String[]>  map=request.getParameterMap();
			Set<Map.Entry<String, String[]>> setMap = map.entrySet();
			for (Map.Entry<String, String[]> sm : setMap) {
				String name = sm.getKey();
				String[] value=sm.getValue();
				if (StringUtils.isNotEmpty(value[0])) {
					try {
						query.append(name).append("=").append(URLEncoder.encode(value[0],"UTF-8")).append("&");
					} catch (UnsupportedEncodingException e) {
						log.error("code happen error.",e);
						e.printStackTrace();
					} 
				}
			}
			String requestPageUrl = query.substring(0,query.length()-1);
			requestPageUrl = requestPageUrl.substring(requestPageUrl.indexOf(request.getContextPath() + "/"));
			requestPageUrl = requestPageUrl.replace(request.getContextPath()+ "/", "/");
			log.info("parseUrl:"+requestPageUrl);
			return requestPageUrl;//访问路径
		}
		
		/**
		 * 发货通知——new 2014-10-21
		 * @param token
		 * @return
		 * @throws WeixinRuntimeException 
		 */
		public static String deliverState(String token,Map<String,String> map,String appkey) throws WeixinRuntimeException{
			String url="https://api.weixin.qq.com/pay/delivernotify?access_token="+token;
			HashMap<String,String> mp = new HashMap<String,String>();
			mp.put("appid",map.get("appid"));
			mp.put("openid",map.get("openid"));
			mp.put("transid",map.get("transid"));
			mp.put("out_trade_no",map.get("out_trade_no"));
			mp.put("deliver_timestamp",String.valueOf(new Date().getTime()/1000));
			mp.put("deliver_status","1");
			mp.put("deliver_msg","ok");
			mp.put("app_signature",CommonUtil.createSign(mp,appkey));//生成签名
			mp.put("sign_method", "sha1");
			log.info("发货json:"+JSONObject.fromObject(mp).toString());
			String content = JSONObject.fromObject(mp).toString();
			String result = HttpClientCommonSSL.commonPostStream(url, content);
			log.info("发货返回:"+result);
			return result;
		}
		
		/**
		 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
		 * @param strxml
		 * @return
		 * @throws JDOMException
		 * @throws IOException
		 */
		public static Map doXMLParse(String strxml) throws Exception {
			if(null == strxml || "".equals(strxml)) {
				return null;
			}
			
			Map m = new HashMap();
			InputStream in = String2Inputstream(strxml);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			Iterator it = list.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List children = e.getChildren();
				if(children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = getChildrenText(children);
				}
				
				m.put(k, v);
			}
			//关闭流
			in.close();
			return m;
		}
		/**
		 * 获取子结点的xml
		 * @param children
		 * @return String
		 */
		public static String getChildrenText(List children) {
			StringBuffer sb = new StringBuffer();
			if(!children.isEmpty()) {
				Iterator it = children.iterator();
				while(it.hasNext()) {
					Element e = (Element) it.next();
					String name = e.getName();
					String value = e.getTextNormalize();
					List list = e.getChildren();
					sb.append("<" + name + ">");
					if(!list.isEmpty()) {
						sb.append(getChildrenText(list));
					}
					sb.append(value);
					sb.append("</" + name + ">");
				}
			}
			
			return sb.toString();
		}
	  public static InputStream String2Inputstream(String str) {
			return new ByteArrayInputStream(str.getBytes());
		}
}

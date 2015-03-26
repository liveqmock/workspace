/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-01-08
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.controller.CreateButtonController;
import com.yazuo.weixin.dao.CreateMenuDao;
import com.yazuo.weixin.service.CreateManageService;
import com.yazuo.weixin.util.WebClientDevWrapper;
import com.yazuo.weixin.vo.FunctionMenu;

/**
 * @ClassName: CreateManageServiceImpl
 * @Description: 处理菜单的实现
 */
@Service
public class CreateManageServiceImpl implements CreateManageService {
	private static final Log LOG = LogFactory.getLog(CreateManageServiceImpl.class);

	@Resource
	private CreateMenuDao createMenuDao;
	
	public String createButton(String json, String appId, String appSecret)
			throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appId + "&secret=" + appSecret;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String access_token = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(access_token);
			access_token = requestObject.getString("access_token");
		
			return create(json, access_token);
		} else {
			return null;
		}
	}

	public String create(String json, String access_token) throws Exception {
		System.out.println(access_token);

		URL postUrl = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
						+ access_token);
		
		
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type",
				"text/html; charset=UTF-8");
		connection.connect();
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		LOG.info(json);
		out.write(json.getBytes("UTF-8"));
		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));// 设置编码,否则中文乱码
		String result = "";
		StringBuffer sb = new StringBuffer();
		while ((result = reader.readLine()) != null) {
			sb.append(result);
		}
		LOG.info("最后返回结果"+sb.toString());
		reader.close();
		connection.disconnect();
		return sb.toString();
	}

	@Override
	public FunctionMenu getMenuMsgByBrandId(Integer brandId) {
		FunctionMenu node = new FunctionMenu();
		node.setId(0);
		node.setMenuName("根目录");
		node.setMenuValue("");
		node.setMerchantId(brandId);
		node.setParentCode("0");
		getChildrenNode(node);
		return node;
	}

	private void getChildrenNode (FunctionMenu parentNode) {
		List<FunctionMenu> pList = null;
		if (parentNode.getParentCode().equals("0")) {
			pList = createMenuDao.queryAllParentMenu(parentNode.getMerchantId());
			for (FunctionMenu m : pList) {
				List<FunctionMenu> list = createMenuDao.queryMenuListByBrandId(parentNode.getMerchantId(), m.getParentCode());
				m.setChildrenList(list);
			}
			parentNode.setChildrenList(pList);
		}
		
//		List<FunctionMenu> pList = null;
//		if (parentNode.getParentCode().equals("0")) {
//			pList = createMenuDao.queryAllParentMenu(parentNode.getMerchantId());
//		} else {
//			pList = createMenuDao.queryMenuListByBrandId(parentNode.getMerchantId(), parentNode.getParentCode());
//		}
//		List<FunctionMenu> treeList = new ArrayList<FunctionMenu>();
//		for (FunctionMenu menu : pList) {
//			getChildrenNode(menu);
//			treeList.add(menu);
//		}
//		if(treeList.size()>0) parentNode.setChildrenList(treeList);
	}

	@Override
	public int insertMenu(List<FunctionMenu> list) {
		Integer merchantId = 0;
		if(list!=null && list.size()>0) {
			merchantId = list.get(0).getMerchantId();
			// 先将信息删除
			int count = createMenuDao.deleteMenu(merchantId);
			if (count >= 0) { // 再批量添加
				return createMenuDao.saveMenu(list);
			}
		}
		return 0;
	}

}

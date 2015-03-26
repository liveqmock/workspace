package com.yazuo.weixin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.yazuo.weixin.service.CreateManageService;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.vo.FunctionMenu;

/**
 * @ClassName: CreateButtonController
 * @Description: 自定义微信菜单管理
 * 
 */
@Controller
@RequestMapping("/weixin/createButton")
public class CreateButtonController {

	@Resource
	private CreateManageService createManageService;

	private static final Log LOG = LogFactory.getLog(CreateButtonController.class);
	
	// 后台操作日志
	private static final Log opLog = LogFactory.getLog("backstageOperate");

	/**
	 * 
	 * 创建一二级菜单
	 * 
	 */
	@RequestMapping(value = "createButton", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public String createButton(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value = "appId", required = false) String appId,
			@RequestParam(value = "appSecret", required = false) String appSecret, Integer brandId) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		opLog.info("--"+sdf.format(new Date())+"调用createButton(创建菜单)的用户UserId:"+(user !=null ? user.getUserId():0));
		try {
			String onename = request.getParameter("onename");
			String oneurl = request.getParameter("oneurl");
			String twoname = request.getParameter("twoname");
			String twourl = request.getParameter("twourl");
			String threename = request.getParameter("threename");
			String threeurl = request.getParameter("threeurl");

			Map<String, Object> button = new HashMap<String, Object>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			Map<String, Object> map3 = new HashMap<String, Object>();
			// 创建菜单数据
			List<FunctionMenu> menuList = new ArrayList<FunctionMenu>();
			FunctionMenu m1 = compositeMenu(brandId, appId, appSecret, onename, oneurl, "001");
			if(m1 !=null)menuList.add(m1); // 菜单一的一级菜单
			FunctionMenu m2 = compositeMenu(brandId, appId, appSecret, twoname, twourl, "002");
			if(m2 !=null)menuList.add(m2); // 菜单二的一级菜单
			FunctionMenu m3 = compositeMenu(brandId, appId, appSecret, threename, threeurl, "003");
			if(m3 !=null)menuList.add(m3);// 菜单三的一级菜单
			
			List list1 = new ArrayList();
			List list2 = new ArrayList();
			List list3 = new ArrayList();
			List list = new ArrayList();
			if(StringUtils.isBlank(oneurl)){
				for (int i = 1; i < 6; i++) {
					String one1url = request.getParameter("one" + i + "url");
					String one1name = request.getParameter("one" + i + "name");
					if(StringUtils.isNotBlank(one1name)&&StringUtils.isNotBlank(one1url)){
						Map<String,String> mp = new HashMap<String,String>();
						mp.put("name", one1name);
						if(!one1url.toLowerCase().startsWith("http")){
							mp.put("key", one1url);
							mp.put("type", "click");
						}else{
							mp.put("url",one1url);
							mp.put("type","view");
						}
						list1.add(mp);
					}
					// 一级菜单下的子菜单
					FunctionMenu m = compositeMenu(brandId, appId, appSecret, one1name, one1url, "00100"+i);
					if (m!=null) menuList.add(m); 
				}
				if(list1.size()>0){
					map1.put("name", onename);
					map1.put("sub_button", list1);
				}
				list.add(map1);
			}else{
				map1.put("name", onename);
				if(oneurl.toLowerCase().startsWith("http")){
					map1.put("type", "view");
					map1.put("url", oneurl);
				}else{
					map1.put("type", "click");
					map1.put("key", oneurl);
				}
				list.add(map1);
			}
			if(StringUtils.isBlank(twourl)){
				for (int j = 1; j < 6; j++) {
					String two1url = request.getParameter("two" + j + "url");
					String two1name = request.getParameter("two" + j + "name");
					if(StringUtils.isNotBlank(two1name)&&StringUtils.isNotBlank(two1url)){
						Map<String,String> mp = new HashMap<String,String>();
						mp.put("name", two1name);
						if(!two1url.toLowerCase().startsWith("http")){
							mp.put("key", two1url);
							mp.put("type", "click");
						}else{
							mp.put("url",two1url);
							mp.put("type","view");
						}
						list2.add(mp);
					}
					//一级菜单的子菜单
					FunctionMenu m = compositeMenu(brandId, appId, appSecret, two1name, two1url, "00200"+j); 
					if(m!=null)menuList.add(m);
				}
				if(list2.size()>0){
					map2.put("name", twoname);
					map2.put("sub_button", list2);
					list.add(map2);
				}
				}else{
				map2.put("name", twoname);
				if(twourl.toLowerCase().startsWith("http")){
					map2.put("type", "view");
					map2.put("url", twourl);
				}else{
					map2.put("type", "click");
					map2.put("key", twourl);
				}
				list.add(map2);
			}
			if(StringUtils.isBlank(threeurl)){
				for (int h = 1; h < 6; h++) {
					String three1url = request.getParameter("three" + h + "url");
					String three1name = request.getParameter("three" + h + "name");
					if(StringUtils.isNotBlank(three1name)&&StringUtils.isNotBlank(three1url)){
						Map<String,String> mp = new HashMap<String,String>();
						mp.put("name", three1name);
						if(!three1url.toLowerCase().startsWith("http")){
							mp.put("key", three1url);
							mp.put("type", "click");
						}else{
							mp.put("url",three1url);
							mp.put("type","view");
						}
						list3.add(mp);
					}
					//一级菜单的子菜单
					FunctionMenu m = compositeMenu(brandId, appId, appSecret, three1name, three1url, "00300"+h); 
					if(m!=null)menuList.add(m);
				}
				if(list3.size()>0){
					map3.put("name", threename);
					map3.put("sub_button", list3);
					list.add(map3);
				}
			}else{
				map3.put("name", threename);
				if(threeurl.toLowerCase().startsWith("http")){
					map3.put("type", "view");
					map3.put("url", threeurl);
				}else{
					map3.put("type", "click");
					map3.put("key", threeurl);
				}
				list.add(map3);
			}
			
			button.put("button", list);
			String json = JSONObject.fromObject(button).toString();
			LOG.info("json:"+json);
			String backJson = createManageService.createButton(json, appId, appSecret);
			// 取返回值
			JSONObject result = JSONObject.fromObject(backJson);
			int code = result.getInt("errcode"); // 返回的状态码
			if (code == 0) { // 微信创建菜单成功
				int count = createManageService.insertMenu(menuList);
			}
			LOG.info("微信返回值:"+backJson);
			return backJson;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value = "initMenuList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView initMenu(Integer brandId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("USER") == null) { // 判断是否登录，没有登录需要重新登录
			ModelAndView mav = new ModelAndView("backDoorLogin");
			return mav;
		}
		FunctionMenu menu = createManageService.getMenuMsgByBrandId(brandId);
		
		ModelAndView mav = new ModelAndView("create_menu/createButton");
		mav.addObject("list", menu.getChildrenList());
		FunctionMenu temp = menu.getChildrenList() !=null && menu.getChildrenList().size()>0 ? menu.getChildrenList().get(0) : new FunctionMenu();
		mav.addObject("appId", temp.getAppId());
		mav.addObject("appSecret", temp.getAppSecret());
		return mav;
	}
	
	private FunctionMenu compositeMenu(Integer brandId, String appId, String appSecret, String value, String name, String parentCode) {
		if (StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(value)) {
			FunctionMenu menu = new FunctionMenu();
			menu.setAppId(appId);
			menu.setAppSecret(appSecret);
			menu.setMenuName(name);
			
			menu.setMenuValue(value);
			menu.setMerchantId(brandId);
			menu.setParentCode(parentCode);
			if(!name.toLowerCase().startsWith("http")){
				menu.setMenuType(1);
			}else{
				menu.setMenuType(2);
			}
			return menu;
		}
		return null;
	}
}

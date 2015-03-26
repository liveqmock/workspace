package com.yazuo.weixin.external;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.util.CommonUtil;

/**
* @ClassName WeixinBrandParamController
* @Description 维护商户参数
* @author sundongfeng@yazuo.com
* @date 2014-10-8 上午8:33:15
* @version 1.0
*/
@Controller
@RequestMapping("/weixin/")
public class WeixinBrandParamController {
	// 后台操作日志
	private static final Log opLog = LogFactory.getLog("backstageOperate");
	SimpleDateFormat sdfOfLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	private static final Log log = LogFactory.getLog("external");
	
	@RequestMapping(value = "queryBrandParam", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryBrandParam(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,Model model ) {
			WxMallMerchantDict dict =null;
			try{
				log.info("queryBrandParam:"+brandId);
				dict = weixinMallMartService.queryMerchantParam(brandId);
			}catch(Exception ex){
				log.error("queryBrandParam error.",ex);
			}
			model.addAttribute("dict", dict);
			model.addAttribute("brandId", brandId);
			log.info("queryBrandParam");
			return "addBrandParam";
	}
	
	@RequestMapping(value = "addOrUpdateBrandParam", method = { RequestMethod.POST,RequestMethod.GET })
	public String addOrUpdateBrandParam(WxMallMerchantDict dict, HttpServletRequest request
			) throws IOException {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用addOrUpdateBrandParam(添加或修改商户参数)的用户UserId:"+(user !=null ? user.getUserId():0));
		try{
			
			boolean flag = weixinMallMartService.updateMerchantParam(dict);
			log.info("更新商户参数"+flag);
			return "backDoor"; 
		}catch(Exception ex){
			log.error("添加商户参数失败.",ex);
			return null;
		} 
	}
	
	@RequestMapping(value = "queryAccessTokenTime", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryAccessTokenTime(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,Model model ) {
			model.addAttribute("brandId", brandId);
			return "addAccesstokenTime";
	}
	
	/*更细accesstoken过期时间*/
	@RequestMapping(value = "updateAccessTokenTime", method = { RequestMethod.POST,RequestMethod.GET })
	public String updateAccessTokenTime(@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "expirestime") Integer expirestime, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用updateAccessTokenTime(设置更新tokenTime)的用户UserId:"+(user !=null ? user.getUserId():0));
		try{
			
			String url = serverIp+"/weixin-api/template/resetToken.do?brandId="+brandId+"&time="+expirestime;
			String res = CommonUtil.postMessage(url);
			log.info("更新过期时间"+res);
			return "backDoor"; 
		}catch(Exception ex){
			log.error("更新过期时间失败.",ex);
			return null;
		} 
	}
	/*删除accesstoken */
	@RequestMapping(value = "deleteAccessToken", method = { RequestMethod.POST,RequestMethod.GET })
	public void deleteAccessToken(HttpServletResponse res,
			@RequestParam(value = "brandId") Integer brandId) throws Exception {
		String  message = "删除失败";
		try{
			
			boolean flag = weixinMallMartService.deleteAccessToken(brandId);
			log.info("deleteAccessToken:"+flag);
			if(flag){
				message="删除成功";
			}
		}catch(Exception ex){
			log.error("删除token失败.",ex);
			message ="删除失败";
		} 
		res.getWriter().print(message);
		res.getWriter().flush();
		res.getWriter().close();
	}
}

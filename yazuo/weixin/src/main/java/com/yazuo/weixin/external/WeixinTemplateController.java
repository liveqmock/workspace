package com.yazuo.weixin.external;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.icu.text.SimpleDateFormat;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxTemplate;
import com.yazuo.weixin.user.vo.UserInfoVo;

@Controller
@RequestMapping("/weixin/template")
public class WeixinTemplateController {
	// 后台操作日志
	private static final Log opLog = LogFactory.getLog("backstageOperate");
	
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	@Autowired
	private MemcachedClient memcachedClient;
	private static final Log log = LogFactory.getLog("external");

	@RequestMapping(value = "queryTemplate", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryTemplate(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,Model model ) {
			try{
				List<Map<String, Object>> list = weixinMallMartService.queryTemplateParam(brandId);
				model.addAttribute("list", list);
				model.addAttribute("brandId", brandId);
				log.info("addWeixinTemplate");
				return "addWeixinTemplate";
			}catch(Exception ex){
				return "wx-xldPayError";
			}
	}
	
	
	@RequestMapping(value = "addOrUpdateTemplate", method = { RequestMethod.POST,RequestMethod.GET })
	public String addOrUpdateTemplate(HttpServletResponse res,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "temp1") String temp1,
			@RequestParam(value = "temp2") String temp2,
			@RequestParam(value = "temp3") String temp3,
			HttpServletRequest request
			) throws IOException {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		opLog.info("--"+sdf.format(new Date())+"调用addOrUpdateTemplate(设置模板)的用户UserId:"+(user !=null ? user.getUserId():0));
		try{
			List<WxTemplate> voList = new ArrayList<WxTemplate>();
			for(int i=1;i<4;i++){
				WxTemplate vo = new WxTemplate();
				vo.setBrandId(brandId);
				vo.setType(i+"");
				switch(i){
				case 1: 
					vo.setName("会员消费通知");vo.setTemplateId(temp1);
					break;
				case 2: 
					vo.setName("到期提醒通知");vo.setTemplateId(temp2);
					break;
				case 3: 
					vo.setName("优惠券到账通知"); vo.setTemplateId(temp3);
					break;
				}
				voList.add(vo);
			}
			weixinMallMartService.updateTemplate(voList, brandId);
			memcachedClient.delete(brandId+"_1");
			memcachedClient.delete(brandId+"_2");
			memcachedClient.delete(brandId+"_3");
			memcachedClient.set(brandId+"_1",0, temp1);
			memcachedClient.set(brandId+"_2",0, temp2);
			memcachedClient.set(brandId+"_3",0, temp3);
			return "backDoor"; 
		}catch(Exception ex){
			log.error("添加模版失败.",ex);
			return null;
		} 
	}
	
}
package com.yazuo.weixin.external;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.service.BrandManageService;
import com.yazuo.weixin.service.RegisterPageService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;

@Controller
@RequestMapping("/weixin/merchant")
public class WeixinForMerchantController {
	@Value("#{propertiesReader['merchantKey']}")
	private String merchantKey;
	@Autowired
	private WeixinPhonePageService weixinPhonePageService;
	@Autowired
	private WeixinManageService weixinManageService;
	@Autowired
	private RegisterPageService registerPageService;
	@Resource
	private BrandManageService brandManageService;
	
	private static final Log log = LogFactory.getLog("external");

	// -----------------------其他商户用户注册基本信息--------------------------
	@RequestMapping(value = "registerForMerchant", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView registerForMerchant(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "key",required=true) String key) {
		log.info("其他商户加入会员接口参数brandid:"+brandId+",weixinId:"+weixinId+",key:"+key);
		
		// 判断是否存在此key值
		long count = brandManageService.judgeRelativeExist(brandId, key);
		
		if(count>0){
			try{
				BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
				MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
						brandId, weixinId);
				if (member == null) {
					member = new MemberVO();
					member.setBrandId(brandId);
					member.setWeixinId(weixinId);
					member.setIsMember(false);
					weixinPhonePageService.insertMember(member);
					member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
							brandId, weixinId);
				}
				if (!member.getIsMember()) {// 新用户注册页面
					
					member.setDataSource(Constant.MEMBER_SCOPE_SOURCE_2); // 会员来源是第三方
					// 商户设置是否发送验证码注册会员
					boolean isAllowWeixinMember = registerPageService.isAllowWeixinMember(brandId);
					ModelAndView mav = new ModelAndView("/member/w-registerInfo");
					mav.addObject("business", business);
					mav.addObject("weixinId", weixinId);
					mav.addObject("member", member);
					mav.addObject("isAllowWeixinMember", isAllowWeixinMember);
					return mav;
					
				} else {
					ModelAndView mav = new ModelAndView("w-registerSuccess");// 已经是会员了跳转
					mav.addObject("business", business);
					mav.addObject("weixinId", weixinId);
					mav.addObject("member", member);
					return mav;
				}
			}catch(Exception ex){
				log.info("其他商户加入会员接口异常.",ex);
				ModelAndView mav = new ModelAndView("wx-Error");// 已经是会员了跳转
				mav.addObject("errorInfo", "服务报错了");
				return mav;
			}
		}else{
			ModelAndView mav = new ModelAndView("wx-Error");// 已经是会员了跳转
			mav.addObject("errorInfo", "服务报错了");
			return mav;
		}
	}
	
}
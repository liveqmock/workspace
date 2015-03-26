package com.yazuo.weixin.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.vo.ActivityConfigVO;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessManagerVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.ImageConfigVO;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.SubbranchVO;

@Controller
@RequestMapping("/weixin")
//@SessionAttributes("USER")
public class WeixinManageController {
	// 后台操作日志
	private static final Log opLog = LogFactory.getLog("backstageOperate");
	SimpleDateFormat sdfOfLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private WeixinManageService weixinManageService;
	@Autowired
	private WeixinPhonePageService weixinPhonePageService;
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址
	

	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "businessList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView businessList(@RequestParam(value="currentPage", required=false)Integer currentPage,
				BusinessVO business) {
		if (currentPage == null) {
			currentPage = 1;
		}
		Pagination<BusinessVO> pagination = weixinManageService.getBusinessPageByOrder(currentPage, business);
		ModelAndView mav = new ModelAndView("businessList");
		mav.addObject("businessList", pagination.getResultList());
		mav.addObject("currentPage", currentPage);
		mav.addObject("totalPage", pagination.getTotalPages());
		return mav;
	}

	@RequestMapping(value = "saveBusiness", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView saveBusiness(HttpServletResponse response,
			@RequestParam(value = "bussiness.token", required = false) String token,
			@RequestParam(value = "bussiness.weixinId", required = false) String weixinId,
			@RequestParam(value = "bussiness.title", required = false) String title,
			@RequestParam(value = "bussiness.brandId", required = false) String brandId,
			@RequestParam(value = "bussiness.tagName", required = false) String tagName,
			@RequestParam(value = "bussiness.companyWeiboId", required = false) String companyWeiboId,
			@RequestParam(value = "bussiness.voteStatus", required = false) Integer voteStatus,
			@RequestParam(value = "bussiness.salutatory", required = false) String salutatory,
			@RequestParam(value = "bussiness.birthdayMessage", required = false) String birthdayMessage,
			@RequestParam(value = "bussiness.pic1", required = false) MultipartFile bigImage,
			@RequestParam(value = "bussiness.pic2", required = false) MultipartFile smallImage,
			@RequestParam(value = "bussiness.isAllowWeixinMember", required = false) String isAllowWeixinMember
			,HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用saveBusiness(添加商家)的用户UserId:"+(user !=null ? user.getUserId():0));
		BusinessVO business = new BusinessVO();
		business.setBigimageName(brandId + "_big.jpg");
		business.setSmallimageName(brandId + "_small.jpg");
		business.setBrandId(Integer.valueOf(brandId));
		business.setTagName(tagName);
		business.setTagId(null);
		business.setToken(token.trim());
		business.setWeixinId(weixinId.trim());
		business.setTitle(title.trim());
		business.setSalutatory(salutatory.trim());
		business.setBigImage(bigImage);
		business.setSmallImage(smallImage);
		business.setCompanyWeiboId(companyWeiboId);
		business.setVoteStatus(voteStatus);
		business.setBirthdayMessage(birthdayMessage.trim());
		boolean isAllowWeixinMemberBol=false;
		if(isAllowWeixinMember!=null && "1".equals(isAllowWeixinMember)){
			isAllowWeixinMemberBol=true;
		}
		business.setIsAllowWeixinMember(isAllowWeixinMemberBol);
		if (weixinManageService.saveBusiness(business)) {
			ModelAndView mav = new ModelAndView("backDoor");
			mav.addObject("business", business);
			return mav;
		} else {
			try {
				response.getWriter().write("the brandId is exist");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return null;
		}

	}

	@RequestMapping(value = "modifyBusiness", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView modifyBusiness(HttpServletResponse response,
			@RequestParam(value = "bussiness.token", required = false) String token,
			@RequestParam(value = "bussiness.weixinId", required = false) String weixinId,
			@RequestParam(value = "bussiness.title", required = false) String title,
			@RequestParam(value = "bussiness.brandId", required = false) String brandId,
			@RequestParam(value = "bussiness.tagName", required = false) String tagName,
			@RequestParam(value = "bussiness.tagId", required = false) String tagId,
			@RequestParam(value = "bussiness.companyWeiboId", required = false) String companyWeiboId,
			@RequestParam(value = "bussiness.salutatory", required = false) String salutatory,
			@RequestParam(value = "bussiness.birthdayMessage", required = false) String birthdayMessage,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "bussiness.voteStatus", required = false) Integer voteStatus,
			@RequestParam(value = "bussiness.isAllowWeixinMember", required = false) String isAllowWeixinMember, 
			 HttpServletRequest request) {
			HttpSession session = request.getSession(true);
			UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用modifyBusiness(修改商家)的用户UserId:"+(user !=null ? user.getUserId():0));
		BusinessVO business = new BusinessVO();
		business.setBrandId(Integer.valueOf(brandId));
		business.setTagName(tagName);
		if(tagId==null||"null".equals(tagId)){
			business.setTagId(null);			
		}else{
			business.setTagId(Integer.parseInt(tagId));
		}
		business.setToken(token.trim());
		business.setWeixinId(weixinId.trim());
		business.setTitle(title.trim());
		business.setCompanyWeiboId(companyWeiboId);
		business.setSalutatory(salutatory.trim());
		business.setId(id);
		business.setVoteStatus(voteStatus);
		business.setBirthdayMessage(birthdayMessage.trim());
		boolean isAllowWeixinMemberBol=false;
		if(isAllowWeixinMember!=null && "1".equals(isAllowWeixinMember)){
			isAllowWeixinMemberBol=true;
		}
		business.setIsAllowWeixinMember(isAllowWeixinMemberBol);
		if (weixinManageService.modifyBusiness(business)) {
			ModelAndView mav = new ModelAndView("backDoor");
			return mav;
		} else {
			try {
				response.getWriter().write("the brandId is exist");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return null;
		}

	}

	@RequestMapping(value = "deleteBusiness", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void deleteBusiness(HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id, 
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用deleteBusiness(删除商家)的用户UserId:"+(user !=null ? user.getUserId():0));
		BusinessVO business = weixinManageService.getBusinessById(id);
		if (weixinManageService.deleteBusiness(business)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return;
	}

	@RequestMapping(value = "modifyImage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView modifyImage(HttpServletResponse response,
			@RequestParam(value = "bussiness.brandId", required = false) Integer brandId,
			@RequestParam(value = "bussiness.pic1", required = false) MultipartFile bigImage,
			@RequestParam(value = "bussiness.pic2", required = false) MultipartFile smallImage,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用modifyImage(图片管理)的用户UserId:"+(user !=null ? user.getUserId():0));
		
		BusinessVO business = new BusinessVO();
		business.setBrandId(brandId);
		business.setBigImage(bigImage);
		business.setSmallImage(smallImage);
		weixinManageService.modifyImage(business);
		ModelAndView mav = new ModelAndView("backDoor");
		return mav;

	}

	@RequestMapping(value = "importSubbranch", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void importSubbranch(HttpServletResponse response, @RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "companyWeiboId", required = false) String companyWeiboId) {
		BusinessVO business = new BusinessVO();
		business.setBrandId(brandId);
		business.setCompanyWeiboId(companyWeiboId);
		int count = weixinManageService.importSubbranch(business);
		try {
			response.getWriter().print(count);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return;

	}

	@RequestMapping(value = "subbranchList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView subbrandchList(@RequestParam(value = "id", required = false) Integer id) {

		BusinessVO business = weixinManageService.getBusinessById(id);
		ModelAndView mav = new ModelAndView("subbranchManager");
		mav.addObject("business", business);
		return mav;

	}

	@RequestMapping(value = "preferenceList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView preferenceList(@RequestParam(value = "id", required = false) Integer id) {

		BusinessVO business = weixinManageService.getBusinessById(id);
		ModelAndView mav = new ModelAndView("preferenceManager");
		mav.addObject("business", business);
		return mav;

	}

	@RequestMapping(value = "dishesList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView dishesList(@RequestParam(value = "id", required = false) Integer id) {

		BusinessVO business = weixinManageService.getBusinessById(id);

		ModelAndView mav = new ModelAndView("dishesManager");
		mav.addObject("business", business);
		return mav;

	}

	@RequestMapping(value = "imageList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView imageList(@RequestParam(value = "id", required = false) Integer id) {

		BusinessVO business = weixinManageService.getBusinessById(id);
		ModelAndView mav = new ModelAndView("imageManager");
		mav.addObject("business", business);
		mav.addObject("pictureUrl", dfsFilePath);
		mav.addObject("hasSmallImage", weixinPhonePageService.hasImage(business.getBrandId(), business.getSmallimageName()));
		mav.addObject("hasBigImage", weixinPhonePageService.hasImage(business.getBrandId(), business.getBigimageName()));
		return mav;

	}

	@RequestMapping(value = "modifySubbranch", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void modifySubbranch(HttpServletResponse response,
			@RequestParam(value = "subbranchEditid", required = false) Integer id,
			@RequestParam(value = "subbranchEdit.name", required = false) String name,
			@RequestParam(value = "subbranchEdit.address", required = false) String address,
			@RequestParam(value = "subbranchEdit.phoneNo", required = false) String phoneNo,
			@RequestParam(value = "subbranchEdit.pointX", required = false) Double pointX,
			@RequestParam(value = "subbranchEdit.pointY", required = false) Double pointY,
			@RequestParam(value = "subbranchEdit.isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "subbranchEdit.isNew", required = false) Boolean isNew,
			@RequestParam(value = "subbranchEdit.orderId", required = false) Integer orderId) {
		SubbranchVO subbranch = new SubbranchVO();
		subbranch.setId(id);
		subbranch.setName(name);
		subbranch.setAddress(address);
		subbranch.setPhoneNo(phoneNo);
		subbranch.setPointX(pointX);
		subbranch.setPointY(pointY);
		subbranch.setIsNew(isNew);
		subbranch.setIsRecommend(isRecommend);
		subbranch.setOrderId(orderId);
		if (weixinManageService.modifySubbranch(subbranch)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return;

	}

	@RequestMapping(value = "deleteSubbranch", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void deleteSubbranch(HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id) {
		SubbranchVO subbranch = new SubbranchVO();
		subbranch.setId(id);
		if (weixinManageService.deleteSubbranch(subbranch)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return;

	}

	@RequestMapping(value = "addSubbranch", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void addSubbranch(HttpServletResponse response,
			@RequestParam(value = "subbranchAddBrandId", required = false) Integer brandId,
			@RequestParam(value = "subbranchAddCompanyWeiboId", required = false) String companyWeiboId,
			@RequestParam(value = "subbranchAdd.name", required = false) String name,
			@RequestParam(value = "subbranchAdd.address", required = false) String address,
			@RequestParam(value = "subbranchAdd.phoneNo", required = false) String phoneNo,
			@RequestParam(value = "subbranchAdd.pointX", required = false) Double pointX,
			@RequestParam(value = "subbranchAdd.pointY", required = false) Double pointY,
			@RequestParam(value = "subbranchAdd.isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "subbranchAdd.isNew", required = false) Boolean isNew) {

		SubbranchVO subbranch = new SubbranchVO();
		subbranch.setName(name);
		subbranch.setAddress(address);
		subbranch.setPhoneNo(phoneNo);
		subbranch.setPointX(pointX);
		subbranch.setPointY(pointY);
		subbranch.setIsNew(isNew);

		subbranch.setIsRecommend(isRecommend);
		subbranch.setBrandId(brandId);
		subbranch.setCompanyWeiboId(companyWeiboId);
		if (weixinManageService.saveSubbranch(subbranch)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return;

	}

	@RequestMapping(value = "modifyPreference", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void modifyPreference(HttpServletResponse response,
			@RequestParam(value = "preferenceEdit.title", required = false) String title,
			@RequestParam(value = "preferenceEdit.content", required = false) String content,
			@RequestParam(value = "preferenceEdit.isNew", required = false) Boolean isNew,
			@RequestParam(value = "preferenceEdit.isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "preferenceEditid", required = false) Integer id,
			@RequestParam(value="preferenceEdit.sortNumber", required=false)Integer sortNumber,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用modifyPreference(修改优惠)的用户UserId:"+(user !=null ? user.getUserId():0));
		
		PreferenceVO preference = new PreferenceVO();
		preference.setId(id);
		preference.setTitle(title);
		preference.setContent(content.replace("\n", "<br>").replaceAll("@", "%"));
		preference.setIsNew(isNew);
		preference.setIsRecommend(isRecommend);
		preference.setSortNumber(sortNumber!=null ? sortNumber : 0);
		if (weixinManageService.modifyPreference(preference)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "addPreference", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void addPreference(HttpServletResponse response,
			@RequestParam(value = "preferenceAdd.title", required = false) String title,
			@RequestParam(value = "preferenceAdd.content", required = false) String content,
			@RequestParam(value = "preferenceAdd.isNew", required = false) Boolean isNew,
			@RequestParam(value = "preferenceAdd.isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "preferenceAddBrandId", required = false) Integer brandId,
			@RequestParam(value="preferenceAdd.sortNumber", required = false)Integer sortNumber,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用addPreference(添加优惠)的用户UserId:"+(user !=null ? user.getUserId():0));
		PreferenceVO preference = new PreferenceVO();
		preference.setBrandId(brandId);
		preference.setTitle(title);
		preference.setContent(content.replace("\n", "<br>").replaceAll("@", "%"));
		preference.setIsNew(isNew);
		preference.setIsRecommend(isRecommend);
		preference.setSortNumber(sortNumber!=null ? sortNumber : 0);
		if (weixinManageService.savePreference(preference)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "deletePreference", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void deletePreference(HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用deletePreference(删除优惠)的用户UserId:"+(user !=null ? user.getUserId():0));
		PreferenceVO preference = new PreferenceVO();
		preference.setId(id);
		if (weixinManageService.deletePreference(preference)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "modifyDishes", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void modifyDishes(HttpServletResponse response,
			@RequestParam(value = "dishesEdit.name", required = false) String name,
			@RequestParam(value = "dishesEdit.description", required = false) String description,
			@RequestParam(value = "dishesEdit.isNew", required = false) Boolean isNew,
			@RequestParam(value = "dishesEdit.isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "dishesEdit.recommendLevel", required = false) Integer recommendLevel,
			@RequestParam(value = "dishesEditid", required = false) Integer id) {
		DishesVO dishes = new DishesVO();
		dishes.setName(name);
		dishes.setDescription(description.replace("\n", "<br>"));
		dishes.setIsNew(isNew);
		dishes.setIsRecommend(isRecommend);
		dishes.setRecommendLevel(recommendLevel);
		dishes.setId(id);
		if (weixinManageService.modifyDishes(dishes)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "addDishes", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void addDishes(HttpServletResponse response, @RequestParam(value = "dishesAdd.name", required = true) String name,
			@RequestParam(value = "dishesAdd.description", required = true) String description,
			@RequestParam(value = "dishesAdd.isNew", required = false) Boolean isNew,
			@RequestParam(value = "dishesAdd.isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "dishesAdd.recommendLevel", required = false) Integer recommendLevel,
			@RequestParam(value = "dishesAddBrandId", required = false) Integer brandId) {
		DishesVO dishes = new DishesVO();
		dishes.setName(name);
		dishes.setDescription(description.replace("\n", "<br>"));
		dishes.setIsNew(isNew);
		dishes.setIsRecommend(isRecommend);
		dishes.setRecommendLevel(recommendLevel);
		dishes.setBrandId(brandId);
		if (weixinManageService.saveDishes(dishes)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "deleteDishes", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void deleteDishes(HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id) {
		DishesVO dishes = new DishesVO();
		dishes.setId(id);
		if (weixinManageService.deleteDishes(dishes)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "bossList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView bossList(@RequestParam(value = "id", required = false) Integer id) {

		BusinessVO business = weixinManageService.getBusinessById(id);

		ModelAndView mav = new ModelAndView("bossManager");
		mav.addObject("business", business);
		return mav;

	}

	@RequestMapping(value = "modifyBoss", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void modifyboss(HttpServletResponse response, @RequestParam(value = "dishesEdit.name", required = false) String name,
			@RequestParam(value = "dishesEdit.description", required = false) String description,
			@RequestParam(value = "dishesEdit.isNew", required = false) Boolean isNew,
			@RequestParam(value = "dishesEdit.isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "dishesEdit.recommendLevel", required = false) Integer recommendLevel,
			@RequestParam(value = "dishesEditid", required = false) Integer id) {
		DishesVO dishes = new DishesVO();
		dishes.setName(name);
		dishes.setDescription(description.replace("\n", "<br>"));
		dishes.setIsNew(isNew);
		dishes.setIsRecommend(isRecommend);
		dishes.setRecommendLevel(recommendLevel);
		dishes.setId(id);
		if (weixinManageService.modifyDishes(dishes)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "addBoss", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void addBoss(HttpServletResponse response,
			@RequestParam(value = "businessManagerAdd.phoneNo", required = true) String phoneNo,
			@RequestParam(value = "businessManagerAddBrandId", required = true) Integer brandId,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用addBoss(添加管理者)的用户UserId:"+(user !=null ? user.getUserId():0));
		BusinessManagerVO businessManager = new BusinessManagerVO();
		businessManager.setPhoneNo(phoneNo);
		businessManager.setBrandId(brandId);
		businessManager.setCreateTime(new Timestamp(System.currentTimeMillis()));
		businessManager.setStatus(0);
		if (weixinManageService.saveBusinessManager(businessManager)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "deleteBoss", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void deleteBoss(HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用deleteBoss(删除管理者)的用户UserId:"+(user !=null ? user.getUserId():0));
		BusinessManagerVO businessManager = new BusinessManagerVO();
		businessManager.setId(id);
		if (weixinManageService.deleteBusinessManager(businessManager)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "autoreplyList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView autoreplyList(@RequestParam(value = "id", required = false) Integer id) {

		BusinessVO business = weixinManageService.getBusinessById(id);

		ModelAndView mav = new ModelAndView("autoreplyManager");
		mav.addObject("business", business);
		return mav;

	}

	@RequestMapping(value = "addAutoreply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void addAutoreply(HttpServletResponse response,
			@RequestParam(value = "autoreplyAdd.keywordType", required = true) String keywordType,
			@RequestParam(value = "autoreplyAdd.keyword", required = true) String keyword,
			@RequestParam(value = "autoreplyAdd.replyType", required = true) String replyType,
			@RequestParam(value = "autoreplyAdd.replyContent", required = true) String replyContent,
			@RequestParam(value = "autoreplyAddBrandId", required = true) Integer brandId,
			@RequestParam(value="autoreplyAdd.eventType") Integer eventType,
			@RequestParam(value="specificType", required=false)Integer specificType
			, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用addAutoreply(添加自动回复)的用户UserId:"+(user !=null ? user.getUserId():0));
		while (!keyword.equals(keyword.replace("  ", " "))) {
			keyword = keyword.replace("  ", " ");
		}
		AutoreplyVO autoreply = new AutoreplyVO();
		autoreply.setBrandId(brandId);
		autoreply.setKeywordType(keywordType);
		autoreply.setKeyword(keyword);
		autoreply.setReplyContent(replyContent);
		autoreply.setReplyType(replyType);
		autoreply.setEventType(eventType); // 事件类型
		autoreply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		autoreply.setSpecificType(specificType);

		if (weixinManageService.saveAutoreply(autoreply)) {
			try {
				weixinManageService.loadAutoreplyData();
				response.getWriter().print("{\"success\":true}");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;
	}
	@RequestMapping(value = "checkNotKeyAutoreply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void checkNotKeyAutoreply(HttpServletResponse response,
			@RequestParam(value = "brandId", required = true) Integer brandId ) {
		if (weixinManageService.countAutoreplyNotKeyWord(brandId)>0) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}else{
			try {
				response.getWriter().print("0");
			} catch (IOException e) {
				log.error("code happen error.",e);
			}
		}
		return;
	}
	
	@RequestMapping(value = "addNotKeyAutoreply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void addNotKeyAutoreply(HttpServletResponse response,
			@RequestParam(value = "autoreplyAdd.keywordType", required = false) String keywordType,
			@RequestParam(value = "autoreplyAdd.keyword", required = false) String keyword,
			@RequestParam(value = "autoreplyAdd.replyType", required = true) String replyType,
			@RequestParam(value = "autoreplyAdd.replyContent", required = true) String replyContent,
			@RequestParam(value = "notKeyAutoreplyAddBrandId", required = true) Integer brandId, 
			@RequestParam(value="autoreplyAdd.eventType") Integer eventType
			, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用addNotKeyAutoreply(增加非关键字自定义回复)的用户UserId:"+(user !=null ? user.getUserId():0));

		AutoreplyVO autoreply = new AutoreplyVO();
		autoreply.setBrandId(brandId);
		autoreply.setKeywordType("");
		autoreply.setKeyword("");
		autoreply.setReplyContent(replyContent);
		autoreply.setReplyType(replyType);
		autoreply.setEventType(eventType); // 事件类型
		autoreply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		if (weixinManageService.saveAutoreply(autoreply)) {
			try {
				weixinManageService.loadAutoreplyData();
				response.getWriter().print("{\"success\":true}");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;
	}
	
	@RequestMapping(value = "settingAutoreply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object settingAutoreply(@RequestParam(value = "brandId", required = false) Integer brandId, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用settingAutoreply(增加菜单动态设置)的用户UserId:"+(user !=null ? user.getUserId():0));
		
		String [] keywords = request.getParameterValues("keywords"); // 参数
		return weixinManageService.saveManyAutoreply(keywords, brandId); // 保存信息
	}
	
	// 修改自动回复信息
	@RequestMapping(value = "updateAutoreply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void updateAutoreply(HttpServletResponse response,
			@RequestParam(value = "autoreplyAdd.replyContent", required = true) String replyContent,
			@RequestParam(value = "autoreplyAdd.replyType", required = false) String replyType,
			@RequestParam(value="specificType", required=false)Integer specificType,
			Integer replyId
			, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用updateAutoreply(修改自动回复)的用户UserId:"+(user !=null ? user.getUserId():0));

		AutoreplyVO autoreply = new AutoreplyVO();
		autoreply.setId(replyId);
		if(StringUtils.isNotEmpty(replyType)){
			autoreply.setReplyType(replyType);
		}
		autoreply.setReplyContent(replyContent);
		autoreply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		autoreply.setSpecificType(specificType);
		if (weixinManageService.updateAutoreply(autoreply)) {
			try {
				weixinManageService.loadAutoreplyData();
				response.getWriter().print("{\"success\":true}");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;
	}

	//编辑
	@RequestMapping(value = "getAutoreply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void getAutoreply (Integer id, HttpServletResponse response) {
	 	AutoreplyVO reply = weixinManageService.getAutoreply(id);
	 	Map<String, Object> map = new HashMap<String, Object>();
	 	map.put("success", true);
	 	map.put("data", reply);
	 	try {
			response.getWriter().print(JSONObject.fromObject(map));
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// 添加图片
	@RequestMapping(value = "addImageConfig", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView addImageConfig(HttpServletResponse response,
			@RequestParam(value = "imageConfigAdd.replyTitle", required = true) String replyTitle,
			@RequestParam(value = "imageConfigAdd.replyUrl", required = true) String replyUrl,
			@RequestParam(value = "imageConfigAdd.pic", required = false) MultipartFile image,
			@RequestParam(value = "imageConfigAdd.descripation", required = false) String descripation,
			@RequestParam(value = "autoreplyAddBrandId", required = true) Integer brandId,
			@RequestParam(value = "autoreplyConfigId", required = true) Integer autoreplyConfigId,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		
		opLog.info("--"+sdfOfLog.format(new Date())+"调用addImageConfig(自动回复图文)的用户UserId:"+(user !=null ? user.getUserId():0));
		
		ImageConfigVO imageConfig = new ImageConfigVO();
		imageConfig.setAutoreplyId(autoreplyConfigId);
		imageConfig.setBrandId(brandId);
		imageConfig.setDescripation(descripation);
		imageConfig.setIsDelete(false);
		imageConfig.setImage(image);
		String fileName = image.getOriginalFilename();// 文件的名字
		imageConfig.setImageName(String.valueOf(System.currentTimeMillis()) + fileName.substring(fileName.lastIndexOf(".")));
		imageConfig.setReplyTitle(replyTitle);
		imageConfig.setReplyUrl(replyUrl);
		imageConfig.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		weixinManageService.saveImageConfig(imageConfig);
		weixinManageService.loadAutoreplyData();// 这一点很重要
		ModelAndView mav = new ModelAndView("backDoor");
		return mav;
	}

	// 删除图片以及自动回复
	@RequestMapping(value = "deleteAutoreply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void deleteAutoreply(HttpServletResponse response,
			@RequestParam(value = "autoreplyConifgId", required = true) Integer autoreplyConifgId,
			@RequestParam(value = "autoreplyAdd.keywordType", required = true) String keywordType,
			@RequestParam(value = "autoreplyAdd.keyword", required = true) String keyword,
			@RequestParam(value = "autoreplyAdd.replyType", required = true) String replyType,
			@RequestParam(value = "autoreplyAddBrandId", required = true) Integer brandId,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用deleteAutoreply(删除自动回复)的用户UserId:"+(user !=null ? user.getUserId():0));
		
		while (!keyword.equals(keyword.replace("  ", " "))) {
			keyword = keyword.replace("  ", " ");
		}
		if (replyType.equals("news")) {// 回复为图文模式的将改自动回复的图片删除掉
			ImageConfigVO imageConfig = new ImageConfigVO();
			imageConfig.setAutoreplyId(autoreplyConifgId);
			imageConfig.setBrandId(brandId);
			imageConfig.setIsDelete(true);
			imageConfig.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			weixinManageService.delImageConfig(imageConfig);

		}

		AutoreplyVO autoreply = new AutoreplyVO();
		autoreply.setId(autoreplyConifgId);
		if (weixinManageService.deleteAutoreply(autoreply)) {
			try {
				weixinManageService.loadAutoreplyData();
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	// 查询已上传图片的张数
	@RequestMapping(value = "imageConfigList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void imageConfigList(HttpServletResponse response,
			@RequestParam(value = "autoreplyConifgId", required = true) Integer autoreplyConifgId) {

		AutoreplyVO autoreply = new AutoreplyVO();
		autoreply.setId(autoreplyConifgId);
		List<ImageConfigVO> imageList = weixinManageService.getImageConfigVOListByAutoply(autoreply);
		if (imageList == null) {
			try {
				response.getWriter().print("0");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().print("" + imageList.size());
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}

		return;

	}

	// 删除图片
	@RequestMapping(value = "delImage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void delImage(HttpServletResponse response,
			@RequestParam(value = "autoreplyConifgId", required = true) Integer autoreplyConifgId) {

		ImageConfigVO imageConfig = new ImageConfigVO();
		imageConfig.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		imageConfig.setIsDelete(true);
		imageConfig.setAutoreplyId(autoreplyConifgId);
		if (weixinManageService.delImageConfig(imageConfig)) {
			try {
				weixinManageService.loadAutoreplyData();
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	@RequestMapping(value = "activityConfigList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView activityConfigList(@RequestParam(value = "id", required = false) Integer id) {

		BusinessVO business = weixinManageService.getBusinessById(id);

		ModelAndView mav = new ModelAndView("activityManager");
		mav.addObject("business", business);
		return mav;

	}

	@RequestMapping(value = "addActivityConfig", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void addActivityConfig(HttpServletResponse response,
			@RequestParam(value = "activityConfigAdd.name", required = true) String name,
			@RequestParam(value = "activityConfigAddBrandId", required = true) Integer brandId,
			@RequestParam(value = "activityConfigAdd.probability", required = true) Integer probability,
			@RequestParam(value = "activityConfigAdd.frequency", required = true) Integer frequency,
			@RequestParam(value = "activityConfigAdd.timeUnit", required = true) Integer timeUnit,
			@RequestParam(value = "activityConfigAdd.countLimit", required = true) Integer countLimit,
			@RequestParam(value = "activityConfigAdd.startTime", required = true) String startTime,
			@RequestParam(value = "activityConfigAdd.endTime", required = true) String endTime,
			@RequestParam(value = "activityConfigAdd.activityId", required = true) Integer activityId,
			@RequestParam(value = "activityConfigAdd.replyContent", required = true) String replyContent) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Timestamp start = null;
		Timestamp end = null;
		ActivityConfigVO activityConfig = new ActivityConfigVO();
		try {
			start = new Timestamp(sdf.parse(startTime).getTime());
			end = new Timestamp(sdf.parse(endTime).getTime() + 23 * 3600 * 1000 + 3599 * 1000);
		} catch (ParseException e1) {

			try {
				response.getWriter().print("时间格式错误");
				return;
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			log.error(e1.getMessage());
			e1.printStackTrace();
		}
		// 只有一种奖项时 同一时间段只能建立一个活动 开通多奖项时注释掉
		// List<ActivityConfigVO> activityConfigList = weixinManageService
		// .getBusinessByBrandId(brandId).getActivityConfigList();
		// for (ActivityConfigVO activityConfig1 : activityConfigList) {
		// if
		// (!((start.after(activityConfig1.getEndTime())||start.before(activityConfig1.getStartTime()))||(end.after(activityConfig1.getEndTime())&&end.before(activityConfig1.getStartTime()))))
		// {
		// try {
		// response.getWriter().print("同时间段只可以有一种活动");
		// return;
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// e.printStackTrace();
		// }
		// return;
		// }
		// }

		activityConfig.setInsertTime(new Timestamp(System.currentTimeMillis()));
		activityConfig.setStartTime(start);
		activityConfig.setEndTime(end);
		activityConfig.setProbability(probability);
		activityConfig.setName(name);
		activityConfig.setBrandId(brandId);
		activityConfig.setActivityId(activityId);
		activityConfig.setTimeUnit(timeUnit);
		activityConfig.setFrequency(frequency);
		activityConfig.setCountLimit(countLimit);
		activityConfig.setAwardCount(0);
		activityConfig.setReplyContent(replyContent);
		if (weixinManageService.saveActivityConfig(activityConfig)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;
	}

	@RequestMapping(value = "modifyActivityConfig", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void modifyActivityConfig(HttpServletResponse response,
			@RequestParam(value = "activityConfigEdit.name", required = true) String name,
			@RequestParam(value = "activityConfigEdit.probability", required = true) Integer probability,
			@RequestParam(value = "activityConfigEdit.frequency", required = true) Integer frequency,
			@RequestParam(value = "activityConfigEdit.timeUnit", required = true) Integer timeUnit,
			@RequestParam(value = "activityConfigEdit.countLimit", required = true) Integer countLimit,
			@RequestParam(value = "activityConfigEdit.startTime", required = true) String startTime,
			@RequestParam(value = "activityConfigEdit.endTime", required = true) String endTime,
			@RequestParam(value = "activityConfigEdit.id", required = true) Integer id,
			@RequestParam(value = "activityConfigEdit.brandId", required = true) Integer brandId,
			@RequestParam(value = "activityConfigEdit.activityId", required = true) Integer activityId,
			@RequestParam(value = "activityConfigEdit.replyContent", required = true) String replyContent) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Timestamp start = null;
		Timestamp end = null;
		ActivityConfigVO activityConfig = new ActivityConfigVO();
		try {
			start = new Timestamp(sdf.parse(startTime).getTime());
			end = new Timestamp(sdf.parse(endTime).getTime() + 23 * 3600 * 1000 + 3500 * 1000);
		} catch (ParseException e1) {
			try {
				response.getWriter().print("时间格式错误");
				return;
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			log.error(e1.getMessage());
			e1.printStackTrace();
		}
		// 只有一种奖项时 同一时间段只能建立一个活动 开通多奖项时注释掉
		// List<ActivityConfigVO> activityConfigList = weixinManageService
		// .getBusinessByBrandId(brandId).getActivityConfigList();
		// for (ActivityConfigVO activityConfig1 : activityConfigList) {
		// if(activityConfig1.getId().intValue()==id.intValue()) continue;
		// if
		// (!((start.after(activityConfig1.getEndTime())||start.before(activityConfig1.getStartTime()))||(end.after(activityConfig1.getEndTime())&&end.before(activityConfig1.getStartTime()))))
		// {
		// try {
		// response.getWriter().print("同时间段只可以有一种活动");
		// return;
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// e.printStackTrace();
		// }
		// return;
		// }
		// }

		activityConfig.setStartTime(start);
		activityConfig.setEndTime(end);
		activityConfig.setProbability(probability);
		activityConfig.setName(name);
		activityConfig.setId(id);
		activityConfig.setActivityId(activityId);
		activityConfig.setTimeUnit(timeUnit);
		activityConfig.setFrequency(frequency);
		activityConfig.setCountLimit(countLimit);
		activityConfig.setReplyContent(replyContent);
		if (weixinManageService.modifyActivityConfig(activityConfig)) {
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;
	}

	@RequestMapping(value = "deleteActivityConfig", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void deleteActivityConfig(HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id) {
		ActivityConfigVO activityConfig = new ActivityConfigVO();
		activityConfig.setId(id);
		if (weixinManageService.deleteActivityConfig(activityConfig)) {
			try {
				weixinManageService.loadAutoreplyData();
				response.getWriter().print("1");
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return;

	}

	/**
	 * 
	 * 创建一二级菜单
	 * 
	 */
	@RequestMapping(value = "createButton", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void createButton(HttpServletResponse response, @RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "appId", required = false) String appId,
			@RequestParam(value = "appSecret", required = false) String appSecret) {
		try {
			response.getWriter().write(weixinManageService.createButton(json, appId, appSecret));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return;

	}
	@RequestMapping(value = "updateMemberRights", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView updateMemberRights(Integer id, @RequestParam(value="memberRights", required=false) String memberRights
			,HttpServletResponse response, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		opLog.info("--"+sdfOfLog.format(new Date())+"调用updateMemberRights(会员权益)的用户UserId:"+(user !=null ? user.getUserId():0));
		BusinessVO business = new BusinessVO();
		business.setId(id);
		business.setMemberRights(memberRights);
		// 更新会员权益
		boolean isSuccess = weixinManageService.updateMemberRight(business);
		if(isSuccess) {
			ModelAndView mav = new ModelAndView("backDoor");
			return mav;
		} else {
			try {
				response.getWriter().write("the brandId is exist");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return null;
		}
	}
	
}

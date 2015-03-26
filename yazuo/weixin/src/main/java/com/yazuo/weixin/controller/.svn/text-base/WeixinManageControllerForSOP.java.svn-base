package com.yazuo.weixin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessManagerVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.Story;
import com.yazuo.weixin.vo.SubbranchVO;

@Controller
@RequestMapping("/weixin/remoteInterface")
public class WeixinManageControllerForSOP {
	private static final Log opLog = LogFactory.getLog("backstageOperate");
	
	@Autowired
	private WeixinManageService weixinManageService;
	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "businessList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object businessList(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "title", required = false) String title) {
		Map<String,Object> map = null;
		if(title==null||title.equals("")){
			map=weixinManageService.getBusinessByPage(page, pagesize);
		}else{
//			try {
//				title=URLDecoder.decode(URLDecoder.decode(title,"utf-8"),"utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				log.error(e.getMessage());
//			}
			map=weixinManageService.getBusinessByTitle(title,page, pagesize);
		}
		return map;
	}

	@RequestMapping(value = "saveBusiness", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void saveBusiness(
			HttpServletResponse response,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "salutatory", required = false) String salutatory,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "companyWeiboId", required = false) String companyWeiboId,
			@RequestParam(value = "voteStatus", required = false) Integer voteStatus,
			@RequestParam(value = "birthdayMessage", required = false) String birthdayMessage,
			@RequestParam(value = "memberRights", required = false) String memberRights,
			@RequestParam(value = "bigImage", required = false) MultipartFile bigImage,
			@RequestParam(value = "smallImage", required = false) MultipartFile smallImage,
			@RequestParam(value = "isAllowWeixinMember", required = false) Boolean isAllowWeixinMember) {
		log.debug("调用saveBusiness接口.....sop");
		if(weixinManageService.getBusinessByBrandId(brandId)!=null){
			try {
				token=URLDecoder.decode(token,"utf-8");
				response.getWriter().print("2");
				return ;
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		try {
			token=URLDecoder.decode(token,"utf-8");
			title=URLDecoder.decode(title,"utf-8");
			salutatory=URLDecoder.decode(salutatory,"utf-8");
			weixinId=URLDecoder.decode(weixinId,"utf-8");
			birthdayMessage = URLDecoder.decode(birthdayMessage,"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		BusinessVO business = new BusinessVO();
		business.setBigimageName(brandId + "_big.jpg");
		business.setSmallimageName(brandId + "_small.jpg");
		business.setBrandId(brandId);
		business.setToken(token);
		business.setWeixinId(weixinId.trim());
		business.setTitle(title);
		business.setBigImage(bigImage);
		business.setSmallImage(smallImage);
		business.setCompanyWeiboId(companyWeiboId);
		business.setSalutatory(salutatory);
		business.setIsAllowWeixinMember(isAllowWeixinMember);
		business.setVoteStatus(voteStatus);
		business.setBirthdayMessage(birthdayMessage);
		business.setMemberRights(memberRights);
		if (weixinManageService.saveBusiness(business)) {
			try {
				response.getWriter().write("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		} else {
			try {
				response.getWriter().write("3");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		}

	}
	
	@RequestMapping(value = "updateBusiness", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void updateBusiness(
			HttpServletResponse response,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "salutatory", required = false) String salutatory,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "companyWeiboId", required = false) String companyWeiboId,
			@RequestParam(value = "voteStatus", required = false) Integer voteStatus,
			@RequestParam(value = "birthdayMessage", required = false) String birthdayMessage,
			@RequestParam(value = "memberRights", required = false) String memberRights,
			@RequestParam(value = "bigImage", required = false) MultipartFile bigImage,
			@RequestParam(value = "smallImage", required = false) MultipartFile smallImage,
			@RequestParam(value = "isAllowWeixinMember", required = false) Boolean isAllowWeixinMember) {

		log.debug("调用updateBusiness接口.....sop");
		try {
			token=URLDecoder.decode(token,"utf-8");
			title=URLDecoder.decode(title,"utf-8");
			salutatory=URLDecoder.decode(salutatory,"utf-8");
			weixinId=URLDecoder.decode(weixinId,"utf-8");
			birthdayMessage = URLDecoder.decode(birthdayMessage,"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		BusinessVO business = new BusinessVO();
		business.setBigimageName(brandId + "_big.jpg");
		business.setSmallimageName(brandId + "_small.jpg");
		business.setBrandId(brandId);
		business.setId(id);
		business.setToken(token);
		business.setWeixinId(weixinId.trim());
		business.setTitle(title);
		business.setBigImage(bigImage);
		business.setSmallImage(smallImage);
		business.setCompanyWeiboId(companyWeiboId);
		business.setSalutatory(salutatory);
		business.setIsAllowWeixinMember(isAllowWeixinMember);
		business.setVoteStatus(voteStatus);
		business.setBirthdayMessage(birthdayMessage);
		business.setMemberRights(memberRights);
		if (weixinManageService.updateBusiness(business)) {
			try {
				response.getWriter().write("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		} else {
			try {
				response.getWriter().write("3");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		}

	}


	@RequestMapping(value = "modifyBusiness", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void modifyBusiness(
			HttpServletResponse response,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "salutatory", required = false) String salutatory,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "companyWeiboId", required = false) String companyWeiboId,
			@RequestParam(value = "voteStatus", required = false) Integer voteStatus,
			@RequestParam(value = "birthdayMessage", required = false) String birthdayMessage,
			@RequestParam(value = "isAllowWeixinMember", required = false) Boolean isAllowWeixinMember) {

		try {
			title=URLDecoder.decode(URLDecoder.decode(title,"utf-8"),"utf-8");
			token=URLDecoder.decode(URLDecoder.decode(token,"utf-8"),"utf-8");
			weixinId=URLDecoder.decode(URLDecoder.decode(weixinId,"utf-8"),"utf-8");
			salutatory=URLDecoder.decode(URLDecoder.decode(salutatory,"utf-8"),"utf-8");
			birthdayMessage=URLDecoder.decode(URLDecoder.decode(birthdayMessage,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		BusinessVO business = new BusinessVO();
		business.setBrandId(Integer.valueOf(brandId));
		business.setToken(token);
		business.setWeixinId(weixinId.trim());
		business.setTitle(title);
		business.setCompanyWeiboId(companyWeiboId);
		business.setSalutatory(salutatory);
		business.setId(id);
		business.setIsAllowWeixinMember(isAllowWeixinMember);
		business.setVoteStatus(voteStatus);
		business.setBirthdayMessage(birthdayMessage);
		if (weixinManageService.modifyBusiness(business)) {
			try {
				response.getWriter().write("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		} else {
			try {
				response.getWriter().write("3");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		}
	}

	@RequestMapping(value = "deleteBusiness", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void deleteBusiness(HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id) {

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

	@RequestMapping(value = "modifyImage", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView modifyImage(
			HttpServletResponse response,
			@RequestParam(value = "bussiness.brandId", required = false) Integer brandId,
			@RequestParam(value = "bussiness.pic1", required = false) MultipartFile bigImage,
			@RequestParam(value = "bussiness.pic2", required = false) MultipartFile smallImage) {

		BusinessVO business = new BusinessVO();
		business.setBrandId(brandId);
		business.setBigImage(bigImage);
		business.setSmallImage(smallImage);
		weixinManageService.modifyImage(business);
		ModelAndView mav = new ModelAndView("weixinInfoManage");
		return mav;

	}

	@RequestMapping(value = "importSubbranch", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void importSubbranch(
			HttpServletResponse response,
			@RequestParam(value = "brandId", required = false) Integer brandId,
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

	@RequestMapping(value = "subbranchList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object subbrandchList(
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {

		Map<String,Object> map=weixinManageService.getSubbranchListByBrandId(brandId, page, pagesize);
		return map;
	}

	@RequestMapping(value = "preferenceList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object preferenceList(

			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {

		Map<String,Object> map=weixinManageService.getPreferenceListByBrandId(brandId, page, pagesize);
		return map;

	}

	@RequestMapping(value = "dishesList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object dishesList(
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {

		Map<String,Object> map=weixinManageService.getDishesListByBrandId(brandId, page, pagesize);
		return map;
	}

	@RequestMapping(value = "imageList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView imageList(
			@RequestParam(value = "weixinId", required = false) String weixinId) {

		BusinessVO business = weixinManageService
				.getBusinessByWeixinId(weixinId);
		ModelAndView mav = new ModelAndView("imageManager");
		mav.addObject("business", business);
		return mav;

	}

	@RequestMapping(value = "modifySubbranch", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void modifySubbranch(
			HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "phoneNo", required = false) String phoneNo,
			@RequestParam(value = "pointX", required = false) Double pointX,
			@RequestParam(value = "pointY", required = false) Double pointY,
			@RequestParam(value = "isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "isNew", required = false) Boolean isNew) {
		
		try {
			name=URLDecoder.decode(URLDecoder.decode(name,"utf-8"),"utf-8");
			address=URLDecoder.decode(URLDecoder.decode(address,"utf-8"),"utf-8");
			phoneNo=URLDecoder.decode(URLDecoder.decode(phoneNo,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		SubbranchVO subbranch = new SubbranchVO();
		subbranch.setId(id);
		subbranch.setName(name);
		subbranch.setAddress(address);
		subbranch.setPhoneNo(phoneNo);
		subbranch.setPointX(pointX);
		subbranch.setPointY(pointY);
		subbranch.setIsNew(isNew);
		subbranch.setIsRecommend(isRecommend);
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

	@RequestMapping(value = "deleteSubbranch", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void deleteSubbranch(HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id) {
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

	@RequestMapping(value = "addSubbranch", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void addSubbranch(
			HttpServletResponse response,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "companyWeiboId", required = false) String companyWeiboId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "phoneNo", required = false) String phoneNo,
			@RequestParam(value = "pointX", required = false) Double pointX,
			@RequestParam(value = "pointY", required = false) Double pointY,
			@RequestParam(value = "isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "isNew", required = false) Boolean isNew) {

		log.info("调用addSubbrach......sop");
		try {
			companyWeiboId=URLDecoder.decode(URLDecoder.decode(companyWeiboId,"utf-8"),"utf-8");
			name=URLDecoder.decode(URLDecoder.decode(name,"utf-8"),"utf-8");
			address=URLDecoder.decode(URLDecoder.decode(address,"utf-8"),"utf-8");
			phoneNo=URLDecoder.decode(URLDecoder.decode(phoneNo,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
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

	@RequestMapping(value = "modifyPreference", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void modifyPreference(
			HttpServletResponse response,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "isNew", required = false) Boolean isNew,
			@RequestParam(value = "isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "id", required = false) Integer id) {
		
		try {
			title=URLDecoder.decode(URLDecoder.decode(title,"utf-8"),"utf-8");
			content=URLDecoder.decode(URLDecoder.decode(content,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		PreferenceVO preference = new PreferenceVO();
		preference.setId(id);
		preference.setTitle(title);
		preference.setContent(content.replace("\n", "<br>"));
		preference.setIsNew(isNew);
		preference.setIsRecommend(isRecommend);
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

	@RequestMapping(value = "addPreference", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void addPreference(
			HttpServletResponse response,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "isNew", required = false) Boolean isNew,
			@RequestParam(value = "isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "brandId", required = false) Integer brandId) {
		try {
			title=URLDecoder.decode(URLDecoder.decode(title,"utf-8"),"utf-8");
			content=URLDecoder.decode(URLDecoder.decode(content,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		PreferenceVO preference = new PreferenceVO();
		preference.setBrandId(brandId);
		preference.setTitle(title);
		preference.setContent(content.replace("\n", "<br>"));
		preference.setIsNew(isNew);
		preference.setIsRecommend(isRecommend);
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

	@RequestMapping(value = "deletePreference", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void deletePreference(HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id) {
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

	@RequestMapping(value = "modifyDishes", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void modifyDishes(
			HttpServletResponse response,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "isNew", required = false) Boolean isNew,
			@RequestParam(value = "isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "recommendLevel", required = false) Integer recommendLevel,
			@RequestParam(value = "id", required = false) Integer id) {
		try {
			name=URLDecoder.decode(URLDecoder.decode(name,"utf-8"),"utf-8");
			description=URLDecoder.decode(URLDecoder.decode(description,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
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

	@RequestMapping(value = "addDishes", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void addDishes(
			HttpServletResponse response,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "isNew", required = false) Boolean isNew,
			@RequestParam(value = "isRecommend", required = false) Boolean isRecommend,
			@RequestParam(value = "recommendLevel", required = false) Integer recommendLevel,
			@RequestParam(value = "brandId", required = false) Integer brandId) {
		try {
			name=URLDecoder.decode(URLDecoder.decode(name,"utf-8"),"utf-8");
			description=URLDecoder.decode(URLDecoder.decode(description,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
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

	@RequestMapping(value = "deleteDishes", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void deleteDishes(HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id) {
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

	@RequestMapping(value = "businessManagerList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object businessManagerList(
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {

		Map<String,Object> map=weixinManageService.getBusinessManagerListByBrandId(brandId, page, pagesize);
		return map;
	}


	@RequestMapping(value = "addBoss", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void addBoss(
			HttpServletResponse response,
			@RequestParam(value = "phoneNo", required = true) String phoneNo,
			@RequestParam(value = "brandId", required = true) Integer brandId) {
		BusinessManagerVO businessManager = new BusinessManagerVO();
		businessManager.setPhoneNo(phoneNo);
		businessManager.setBrandId(brandId);
		businessManager
				.setCreateTime(new Timestamp(System.currentTimeMillis()));
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

	@RequestMapping(value = "deleteBoss", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void deleteBoss(HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id) {
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

	@RequestMapping(value = "autoreplyList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object autoreplyList(
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize) {
		Map<String,Object> map=weixinManageService.getAutoreplyListByBrandId(brandId, page, pagesize);
		return map;
	}

	@RequestMapping(value = "addAutoreply", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void addAutoreply(
			HttpServletResponse response,
			@RequestParam(value = "keywordType", required = true) String keywordType,
			@RequestParam(value = "keyword", required = true) String keyword,
			@RequestParam(value = "replyType", required = true) String replyType,
			@RequestParam(value = "replyContent", required = true) String replyContent,
//			@RequestParam(value = "autoreplyAdd.replyTitle", required = true) String replyTitle,
//			@RequestParam(value = "autoreplyAdd.replyUrl", required = true) String replyUrl,
//			@RequestParam(value = "autoreplyAdd.pic", required = false) MultipartFile image,
			@RequestParam(value = "brandId", required = true) Integer brandId) {
		
		try {
			replyContent=URLDecoder.decode(URLDecoder.decode(replyContent,"utf-8"),"utf-8");
			keyword=URLDecoder.decode(URLDecoder.decode(keyword,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		while (!keyword.equals(keyword.replace("  ", " "))) {
			keyword=keyword.replace("  ", " ");
		}
		AutoreplyVO autoreply=new AutoreplyVO();
		autoreply.setBrandId(brandId);
		autoreply.setKeywordType(keywordType);
		autoreply.setKeyword(keyword);
		autoreply.setReplyContent(replyContent);
		autoreply.setReplyType(replyType);
		autoreply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		//改动的
//		if(replyType.equals("text")){
//			autoreply.setImage(null);
//		}else {
////			autoreply.setImage(image);
////			autoreply.setImageName(String.valueOf(System.currentTimeMillis())+".jpg");
////			autoreply.setReplyTitle(replyTitle);
////			autoreply.setReplyUrl(replyUrl);
//		}
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
	
	@RequestMapping(value = "modifyAutoreply", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void modifyAutoreply(
			HttpServletResponse response,
			@RequestParam(value = "keywordType", required = true) String keywordType,
			@RequestParam(value = "keyword", required = true) String keyword,
			@RequestParam(value = "replyType", required = true) String replyType,
			@RequestParam(value = "replyContent", required = true) String replyContent,
			@RequestParam(value = "id", required = true) Integer id) {
		log.debug("调用modifyAutoreply接口.....sop");
		try {
			replyContent=URLDecoder.decode(URLDecoder.decode(replyContent,"utf-8"),"utf-8");
			keyword=URLDecoder.decode(URLDecoder.decode(keyword,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		while (!keyword.equals(keyword.replace("  ", " "))) {
			keyword=keyword.replace("  ", " ");
		}
		AutoreplyVO autoreply=new AutoreplyVO();
		autoreply.setId(id);
		autoreply.setKeywordType(keywordType);
		autoreply.setKeyword(keyword);
		autoreply.setReplyContent(replyContent);
		autoreply.setReplyType(replyType);
		autoreply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
//		if(replyType.equals("text")){
//			autoreply.setImage(null);
//		}else {
////			autoreply.setImage(image);
////			autoreply.setImageName(String.valueOf(System.currentTimeMillis())+".jpg");
////			autoreply.setReplyTitle(replyTitle);
////			autoreply.setReplyUrl(replyUrl);
//		}
		if (weixinManageService.updateAutoreply(autoreply)) {
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

	@RequestMapping(value = "deleteAutoreply", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void deleteAutoreply(HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id) {
		AutoreplyVO autoreply=new AutoreplyVO();
		autoreply.setId(id);
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
	
	
	
	@RequestMapping(value = "saveStory", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void saveStory(
			HttpServletResponse response,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam(value = "isDelete", required = false) boolean isDelete) {
		log.debug("调用saveStory接口.....sop");
		if(weixinManageService.getStoryByBrandId(brandId)!=null){
			try {
				response.getWriter().print("2");
				return ;
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		Story story = new Story();
		story.setBrandId(brandId);
		story.setTitle(title);
		story.setContent(content);
		story.setImage(image);
		story.setImageName("story.jpg");
		story.setDelete(isDelete);
		if (weixinManageService.saveStory(story)) {
			try {
				response.getWriter().write("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		} else {
			try {
				response.getWriter().write("3");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		}

	}
	
	@RequestMapping(value = "updateStory", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void updateStory(
			HttpServletResponse response,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam(value = "isDelete", required = false) boolean isDelete,
			@RequestParam(value = "id", required = false) Integer id) {

		log.debug("调用updateBusiness接口.....sop");
		
		Story story = new Story();
		story.setBrandId(brandId);
		story.setTitle(title);
		story.setContent(content);
		story.setImage(image);
		story.setImageName("story.jpg");
		story.setId(id);
		story.setDelete(isDelete);
		if (weixinManageService.updateStory(story)) {
			try { 
				response.getWriter().write("1");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		} else {
			try {
				response.getWriter().write("3");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return ;
		}

	}
	
	@RequestMapping(value = "getStory", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object getStory(
			HttpServletResponse response,
			@RequestParam(value = "brandId", required = false) Integer brandId) {
		log.debug("调用getStory接口.....sop");
		Story story = weixinManageService.getStoryByBrandId(brandId);
		if(story ==null){
			try {
				response.getWriter().print("2");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		
		return story;

	}

	
	@RequestMapping(value = "getWeixinStory", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView getWeixinStory(@RequestParam(value = "brandId", required = false) Integer brandId) {
		Story story = weixinManageService.getByBrandId(brandId);
		ModelAndView mav = new ModelAndView("addStoryManager");
		mav.addObject("story", story);
		return mav;

	}
	
	@RequestMapping(value = "weixinSaveStory", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView weixinSaveStory(
			HttpServletResponse response,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam(value = "isDelete", required = false) boolean isDelete,
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		opLog.info("--"+sdf.format(new Date())+"调用weixinSaveStory(品牌故事)的用户UserId:"+(user !=null ? user.getUserId():0));
		Story story = new Story();
		story.setBrandId(brandId);
		story.setTitle(title);
		story.setContent(content);
		story.setImage(image);
		story.setImageName("story.jpg");
		story.setId(id);
		story.setDelete(isDelete);
		boolean count = false; 
		if (id==null) { // 添加
			count = weixinManageService.saveStory(story);
		} else { // 修改
			count = weixinManageService.updateStory(story);
		}
		ModelAndView mav = new ModelAndView("backDoor");
		mav.addObject("flag", count);
		mav.addObject("message", count ?"保存成功!":"保存失败!");
		return mav;
	}
}

/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DeviceTokenUtil;
import com.yazuo.util.StringUtil;
import com.yazuo.util.StringUtils;
/**
 * 用户相关业务操作
 * @author kyy
 * @date 2014-6-5 上午9:42:16
 */
@Controller
@RequestMapping(value={"user","sysUser"})
public class SysUserController {
	
	private static final Log LOG = LogFactory.getLog(SysRoleController.class);
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysUserMerchantService sysUserMerchantService;
	@Resource
	private SysUserService userService;

	/**
	 * 保存修改角色
	 * 
	 * @param positionVO
	 * @throws
	 */
	@RequestMapping(value = "saveUser", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object saveUser(@RequestBody SysUserVO sysUserVO, HttpServletRequest request)throws IOException {
//		if(true)return null;  
		HttpSession session = request.getSession();
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		//更改密码
		String oldPassword = sysUserVO.getOldPassword();
		String newPassword = sysUserVO.getNewPassword();
		if(!StringUtil.isNullOrEmpty(oldPassword)&&!StringUtil.isNullOrEmpty(newPassword)){
			SysUserVO dbUser = this.userService.getSysUserByTelAndPWD(user);
			// 如果用户输入的密码和数据库密码一致，查到
			boolean flag = this.userService.toVerifyPassword(oldPassword, dbUser.getPassword());
			if(!flag){
				return new JsonResult(false).setMessage("旧密码错误!");
			}else{
				user.setPassword(newPassword);
				this.userService.updatePWD(user);
				session.setAttribute(Constant.SESSION_USER, user);
				sysUserVO.setPassword(null);
				return new JsonResult(true).setMessage("密码修改成功");
			}
			
		}
		// 转换时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long birth = Long.parseLong(sysUserVO.getBirthday());
		String date = sdf.format(new Date(birth * 1000));
		sysUserVO.setBirthday(date);
		Map<String, Object> map = null;
		if (sysUserVO.getId() != null) { // 修改 基本信息
			SysUserVO userVo = sysUserService.getSysUserById(sysUserVO.getId());
			 // 修改信息更换图像
			if (!StringUtils.isEmpty(sysUserVO.getFileName()) && !userVo.getUserImage().equals(sysUserVO.getFileName())) {
				String orginPath = request.getSession().getServletContext().getRealPath(DeviceTokenUtil.getPropertiesValue("userPhotoPath"));
				if (!StringUtils.isEmpty(userVo.getUserImage())) { // 原图片存在
					File file = new File(orginPath, userVo.getUserImage()); // 原图片
					// 路径为文件且不为空则进行删除 原图片 
				    if (file.isFile() && file.exists()) {  
				        file.delete();  
				    }
				}
			    // 把新图片移动到真正放用户头像的位置
			    sysUserService.moveFile(sysUserVO.getFileName(), request);
			}
			if (!StringUtils.isEmpty(sysUserVO.getFileName())) {
				sysUserVO.setUserImage(sysUserVO.getFileName());
			} else {
				sysUserVO.setUserImage(userVo.getUserImage());
			}
			sysUserVO.setUpdateBy(user.getId());
			sysUserVO.setUpdateTime(new Date());
	
			String [] darray =null;
			if (sysUserVO.getGroupId()!=null) {
				darray = new String[1];
				darray[0] = sysUserVO.getGroupId()+"";
			}
			
			// 判断页面是否输入旧密码
			if (StringUtil.isNullOrEmpty(sysUserVO.getOldPassword())) { //即不更改密码 
				LOG.info("不修改密码");
				sysUserVO.setPassword(userVo.getPassword());
				map = sysUserService.updateSysUser(sysUserVO, darray, user.getId());
			} else { // 更改密码
				//验证输入的旧密码是否正确
				boolean isVerify = sysUserService.toVerifyPassword(sysUserVO.getOldPassword(), userVo.getPassword());
				if (isVerify) { // 旧密码输入正确即更改信息
					sysUserVO.setPassword(sysUserVO.getPassword());
					map = sysUserService.updateSysUser(sysUserVO, darray, user.getId());
					LOG.info("输入的旧密码正确!");
				} else { // 提示输入的密码不正确
					map = new HashMap<String, Object>();
					map.put("flag", 0);
					map.put("message", "输入的旧密码不正确!");
					LOG.info("输入的旧密码不正确!");
				}
			}
			map = sysUserService.updateSysUser(sysUserVO, darray, user.getId());
			LOG.debug("修改成功!");
		} else { // 添加 (包括基本信息，权限组关系，员工管辖范围)
			// 上传图片
			if (!StringUtils.isEmpty(sysUserVO.getFileName())) {
				sysUserService.moveFile(sysUserVO.getFileName(), request);
			}
			// 设置保存信息
			sysUserVO.setUserImage(sysUserVO.getFileName());
			sysUserVO.setInsertBy(user.getId());
			sysUserVO.setInsertTime(new Date());
			sysUserVO.setUpdateBy(user.getId());
			sysUserVO.setUpdateTime(new Date());
			sysUserVO.setIsEnable(Constant.Enable);
			sysUserVO.setPassword(Constant.RESET_PWD);
			sysUserVO.setLoginFrequency(0);
			
			String [] darray = new String[1];
			darray[0] = sysUserVO.getGroupId()+"";
			map = sysUserService.saveSysUser(sysUserVO, darray, sysUserVO.getRoleIds(), sysUserVO.getGroup(), sysUserVO.getExceptUser(), user.getId());
			LOG.debug("添加成功!");
		}
		return map;
	}

	/**
	 * 用户上传图片 
	 */
	@RequestMapping(value = "uploadImage", method = { RequestMethod.POST, RequestMethod.GET })
	 @ResponseBody
	public Object uploadImage(@RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		return sysUserService.uploadImage(myfiles, request);
	}
	
	 @RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	 @ResponseBody
	 public Object list(Integer pageSize, Integer pageNumber, String userName, String tel, Integer positionId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);
		paramMap.put("tel", tel);
		paramMap.put("positionId", positionId);
		 //分页
		 PageHelper.startPage(pageNumber, pageSize, true);
		 
		 Page<SysUserVO> listMap = sysUserService.getComplexSysUsers(paramMap);
		 for (SysUserVO sysUserVO : listMap) {
			 LOG.debug("------------- "+sysUserVO.getPosition());
			 LOG.debug("------------- "+sysUserVO.getListRoles());
		}
		 LOG.debug("------------- "+listMap.getTotal());
		 // 返回页面的分页信息
		 Map<String, Object> pageMap = new HashMap<String, Object>();
		
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put(Constant.TOTAL_SIZE, listMap.getTotal());
		 dataMap.put(Constant.ROWS, listMap.getResult());
		 
		 pageMap.put("data", dataMap);
		 pageMap.put("flag", 1);
		 pageMap.put("message", "");
		 //格式化json
		 return pageMap;
	}

	 /**
	  * 根据组id获取组下面的所有人员
	  * @param groupId
	  */
	@RequestMapping(value = "getUserOfGroup", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getUserOfGroup(@RequestParam(value="groupId") Integer groupId) {
		 List<Map<String, String>> list = sysUserService.getUserByGroupId(groupId);
		 JsonResult json = new JsonResult();
		 json.setData(list);
		 json.setFlag(true);
		 json.setMessage("");
		 return json;
	}
	 
	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object edit(Integer id, String editContent, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (editContent.equals("baseSetting")) { // 基本信息设置
			// 用户信息
			SysUserVO user = sysUserService.getSysUserById(id);
			if (!StringUtils.isEmpty(user.getUserImage())) {
				user.setFileName(user.getUserImage());
				
				StringBuilder sb = new StringBuilder();
				sb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getLocalPort())
				.append("/").append(request.getContextPath()).append("/").append(DeviceTokenUtil.getPropertiesValue("userPhotoPath"))
				.append("/").append(user.getUserImage());
				LOG.info(request.getLocalAddr()+"  "+request.getServerName()+"  "+sb.toString());
				user.setUserImage(sb.toString());
			}
			Date date = null;
			try {
				date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(user.getBirthday());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			user.setBirthday(String.valueOf(date.getTime() / 1000));
			
			// 选中的部门（组）信息
			List<Map<String, String>> deptList = sysUserService.getGroupIdByUserId(id);
			Integer deptId = 0;
			if (deptList !=null && deptList.size() > 0) {
				Map<String, String> map1 = deptList.get(0);
				deptId = Integer.parseInt(String.valueOf(map1.get("group_id")));
				user.setGroupId(deptId);
				user.setGroupName(map1.get("group_name"));
			}
			map.put("data", user);
			user.setMacs(sysUserService.getSysWhiteLists(user));
		} else if (editContent.equals("roleSetting")) { // 权限组设置
			// 选中权限（角色）信息
			List<String> roleList = sysUserService.getRoleIdByUserId(id);
			map.put("data", roleList);
		} else if (editContent.equals("managerSetting")) { // 管辖范围
			// 员工管理信息
			// 管理组信息
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", id);
			paramMap.put("managerType", Constant.MANAGER_GROUP);
			List<Map<String, String>> groupList = sysUserService.getManagerByUserId(paramMap);
			List<Integer> glist = new ArrayList<Integer>();
			for (Map<String, String> m : groupList) {
				glist.add(Integer.parseInt(String.valueOf(m.get("group_id"))));
			}
			// 剔除人
			paramMap.put("managerType", Constant.MANAGER_MINUS__USER);
			List<Map<String, String>> userList = sysUserService.getManagerByUserId(paramMap);
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("group", glist);
			totalMap.put("exceptUser", userList);
			map.put("data", totalMap);
		}
		map.put("flag", 1);
		map.put("message", "");
		return map;
	}
	
	/**
	 * 修改权限组
	 * @param id
	 * @param roleIds
	 * @param request
	 */
	@RequestMapping(value = "updateRole", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object updateRole(Integer id, @RequestParam(value="roleIds[]") String [] roleIds, HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		int count = sysUserService.editRoleRelation(id, roleIds, user.getId(), true);
		
		Map<String, Object> map = new HashMap<String, Object>();
		//如果是修改自己的权限，属性为1
		if(user.getId().equals(id)){
			//需要从新修改session中的用户权限
			List<SysResourceVO> listPrivilege = sysUserService.getAllUserResourceByPrivilege(id);
			user.setListPrivilege(listPrivilege);
			map.put(Constant.SELF_ROLE_CHANGED, 1);
			session.setAttribute(Constant.SESSION_USER, user);
		}else{
			map.put(Constant.SELF_ROLE_CHANGED, 0);
		}
		String str =  count > 0 ? "成功" : "失败";
		map.put("message", "修改"+ str);
		map.put("flag", count > 0 ? 1 : 0);
		return map;
	}
	
	@RequestMapping(value = "updateManager", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object updateManager(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		// 组id
		List<Integer> groupList = null;
		if (paramMap.get("group") !=null) {
			groupList = (List<Integer>)paramMap.get("group");
		}
		// 被剔除的用户id和所在组id
		List<Map<String, Integer>> userList = null;
		if (paramMap.get("exceptUser") !=null) {
			userList = (List<Map<String, Integer>>)paramMap.get("exceptUser");
		}
		// 当前修改的用户id
		Integer currentId = Integer.parseInt(String.valueOf(paramMap.get("id")));
		int count = sysUserService.editManagerRelation(currentId, groupList, userList, user.getId(), true); 
		
		Map<String, Object> map = new HashMap<String, Object>();
		String str =  count >= 0 ? "成功" : "失败";
		map.put("message", "修改"+ str);
		map.put("flag", count > 0 ? 1 : 0);
		return map;
	}
	
	@RequestMapping(value = "delete", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object delete(@RequestParam(value="id[]") String [] idStr,@RequestParam(value="defineDelete") int defineDelete, HttpServletRequest request) {
		if (idStr.length ==1) { // 单个删除
			Integer id = Integer.parseInt(idStr[0]);
			Map<String, Object> map = new HashMap<String, Object>();
			// 判断该用户是否存在有效的师生关系
			long num = sysUserService.judgeExistsEnableRelation(id);
			if (num > 0 && defineDelete == 0) {
				map.put("flag", 2);
				map.put("message", "该用户存在"+ num + "个学生，是否解除师生关系?");
			} else if (num == 0 || defineDelete == 1) { // 确定删除
				SysUserVO userVo = sysUserService.getSysUserById(id);
				String userImage = userVo.getUserImage(); // 图片名称
				
				int count = sysUserService.deleteSysUser(id); // 删除用户，解除师生关系
				if (count > 0) { // 用户删除成功即将上传的图片删除
					String orginPath = request.getSession().getServletContext().getRealPath(DeviceTokenUtil.getPropertiesValue("userPhotoPath"));
					if(userImage!=null){
						File file = new File(orginPath, userImage); // 原图片
						// 路径为文件且不为空则进行删除 原图片 
					    if (file.isFile() && file.exists()) {  
					        file.delete();  
					    }
					}
					
				}
				map.put("flag", count > 0 ? "1" : 0);
				map.put("message", count > 0 ? "删除成功!" : "删除失败!");
			}
			return map;
		} else { // 批量删除
			Map<String, Object> map = sysUserService.deleteMany(idStr);
			return map;
		}
	}

	 /**
	  * 通过资源 remark 查询 所有拥有该资源的用户 
	  * url: user/getAllUsersByResourceCode.do
	  * @param getAllUsersByResourceCode: {remark: ''}
	  */
	@RequestMapping(value = "getAllUsersByResourceCode", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getAllUsersByResourceCode(@RequestBody SysResourceVO sysResourceVO) {

		List<SysUserVO> list = this.sysUserService.getAllUsersByResourceCode(sysResourceVO);
		return new JsonResult(true).setData(list);
	}
	
	/**
	 * 根据用户ID和商户ID查询前端顾问和商户的关系
	 */
	@RequestMapping(value = "isExistSysUserMerchant", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult queryFesPlanList(@RequestBody SysUserMerchantVO userMerchantVO) {
		boolean flag = sysUserMerchantService.isExistSysUserMerchant(userMerchantVO);
		return new JsonResult(true).setData(flag);
	}
}

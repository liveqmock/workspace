package com.yazuo.erp.project.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.project.service.LabelService;
import com.yazuo.erp.project.service.ProjectService;
import com.yazuo.erp.project.vo.Active;
import com.yazuo.erp.project.vo.Label;
import com.yazuo.erp.project.vo.Project;
import com.yazuo.util.DateUtil;
import com.yazuo.util.StringUtils;

@Controller
@RequestMapping("erp/project") 
public class ProjectController {

	private static final Log LOG = LogFactory.getLog("statement");
	
	@Resource
	private ProjectService projectService;
	@Resource
	private LabelService labelService;

	@RequestMapping(value = "toProjectItems", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView toProjectItems(HttpServletRequest request,
			  @RequestParam(value = "activeName", required = false) String activeName,
			  @RequestParam(value = "cityType", required = false) String cityType,
			  @RequestParam(value = "cateType", required = false) String cateType,
			  @RequestParam(value = "catType", required = false) String catType,
			  @RequestParam(value = "promoteType", required = false) String promoteType,
			  @RequestParam(value = "avgPriceType", required = false) String avgPriceType,
			  @RequestParam(value = "activeType", required = false) Integer activeType,
			  @RequestParam(value = "labelId", required = false) Integer labelId,
			  @RequestParam(value = "activeBegin", required = false) String activeBegin,
			  @RequestParam(value = "activeEnd", required = false) String activeEnd,
			  @RequestParam(value = "page", required = false) Integer page,
			  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		try {
			page = (page==null||page.intValue()==0)? page=1: page;//如果为空显示第一页
			pageSize = (pageSize==null||pageSize.intValue()==0)? Constant.pageSize: pageSize;//如果为空显示默认条数
			final ModelAndView mav = new ModelAndView("project/projectItems");
			List<Map<String, Object>> dbActiveTypes = projectService.getAllActiveTypes();
			List<Label> dbLabels = labelService.selectLabelListByObject(null);
			mav.addObject("dbActiveTypes", dbActiveTypes);
			mav.addObject("dbLabels", dbLabels);
			//根据活动查询对应的案例库
			Active active = new Active();
			active.setActiveBegin(StringUtils.isEmpty(activeBegin)? null: DateUtil.formateDate(activeBegin));
			active.setActiveEnd(StringUtils.isEmpty(activeEnd)? null: DateUtil.formateDate(activeEnd));
			active.setActiveName(activeName);//活动名和商户名页面时一个值
			active.setMerchantName(activeName);
			active.setActiveType(activeType);
			//所有输入条件都为null， 查询得到所有记录
			if (StringUtils.isEmpty(activeName)
					&& activeType==null
					&& labelId==null
					&& StringUtils.isEmpty(activeBegin)
					&& StringUtils.isEmpty(activeEnd)) {
				active = null;
			}
			List<Active> dbActives = projectService.selectActiveListByObject(active);
			Project project = new Project();
			if(labelId!=null) project.setLabelIds(new String[]{labelId.toString()});
			if(!StringUtils.isEmpty(cateType)) project.setCateType(cateType);
			if(!StringUtils.isEmpty(catType)) project.setCatType(catType);
			if(!StringUtils.isEmpty(avgPriceType)) project.setAvgPriceType(avgPriceType);
			if(!StringUtils.isEmpty(cityType)) project.setCityType(cityType);
			if(!StringUtils.isEmpty(promoteType)) project.setCityType(promoteType);
			Map<String, Object> dbProjectItems = projectService.selectListByPage(project, page, pageSize,dbActives,activeName);
			mav.addObject("dbProjectItems", dbProjectItems);
			mav.addObject("page", page);
			//记录请求参数值
			mav.addObject("activeName", activeName);
			mav.addObject("activeBegin", activeBegin);
			mav.addObject("activeEnd", activeEnd);
			mav.addObject("activeType", activeType);
			mav.addObject("labelId", labelId);
			//查询满足条件的数据字典
			Map<String, Object> mapDictionary = this.projectService.selectAllDictionarys();
			mav.addObject("mapDictionary", mapDictionary);
		    return mav;  
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("错误", e);
			return null;
		}
	}  
	
	@RequestMapping(value = "toAddProject", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView toAddProject(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("project/addProject");
		return mav;  
	}  
	
	@RequestMapping(value = "toSpeficAdd", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView toSpeficAdd(HttpServletRequest request,
			@RequestParam(value = "toView", required = false) String toView,
			@RequestParam(value = "projectId", required = false) Integer projectId,
			@RequestParam(value = "activeId", required = true) Integer activeId,
			@RequestParam(value = "typeId", required = true) Integer typeId) {
		final ModelAndView mav = new ModelAndView("project/speficAdd");
		mav.addObject("activeId", activeId);
		mav.addObject("typeId", typeId);
		if(projectId!=null&&projectId.intValue()!=0){
			Project project = new Project();
			project.setProjectId(projectId);
			Project dbProject = projectService.selectProjectByObject(project);
			mav.addObject("dbProject", dbProject);
			mav.addObject("toView", "false");
		} else if("true".equals(toView)){
			//'toView' is a flag to check if just show view or edit view.
			Project project = new Project();
			project.setActiveId(activeId);
			project.setActiveTypeId(typeId);
			Project dbProject = projectService.selectProjectByObject(project);
			mav.addObject("dbProject", dbProject);
			mav.addObject("toView", "true");
		}else{
			//添加案例
			mav.addObject("toView", "false");
			Active active = new Active();
			active.setActiveId(activeId);
			Active dbActive = projectService.selectActiveListByObject(active).get(0);
			mav.addObject("dbActiveName", dbActive.getActiveName());
		}
		mav.addObject("label_sperater", Constant.label_sperater);
		mav.addObject("imgLocationPath", projectService.getImagePath());
		List<Label> dbLabels = labelService.selectLabelListByObject(null);
		mav.addObject("dbLabels", dbLabels);
		//查询满足条件的数据字典
		Map<String, Object> mapDictionary = this.projectService.selectAllDictionarys();
		mav.addObject("mapDictionary", mapDictionary);
		return mav;  
	}  
	
	@RequestMapping(value = "searchProject", method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView searchProject(HttpServletRequest request,
		  @RequestParam(value = "queryId", required = false) Integer queryId,
		  @RequestParam(value = "queryName", required = false) String queryName,
		  @RequestParam(value = "page", required = false) Integer page,
		  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		try {
			Active active = new Active();
			if(queryId!=null){
				active.setActiveId(queryId);
			}
			if(!StringUtils.isEmpty(queryName)){
				active.setActiveName(queryName);
			}
			final ModelAndView mav = new ModelAndView("project/addProject");
			page = (page==null||page.intValue()==0)? page=1: page;//如果为空显示第一页
			pageSize = (pageSize==null||pageSize.intValue()==0)? Constant.pageSize: pageSize;//如果为空显示默认条数
			Map<String, Object> dbActiveItems = projectService.getActiveMap(active,page,pageSize);
			mav.addObject("dbActiveItems", dbActiveItems);
			mav.addObject("page", page);
			mav.addObject("queryId", queryId);
			mav.addObject("queryName", queryName);
		    return mav;  
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("错误", e);
			return null;
		}
	}  
	
	  @RequestMapping(value="/submitProject", method=RequestMethod.POST)  
	  public ModelAndView submitProject( @RequestParam(value = "activeId", required = true) Integer activeId,
			  @RequestParam(value = "projectComments", required = false) String projectComments,
			  @RequestParam(value = "typeId", required = false) Integer typeId,
			  @RequestParam(value = "projectLabels", required = false) String projectLabels,
			  @RequestParam(value = "projectId", required = false) Integer projectId,
			  @RequestParam(value = "cityType", required = false) String cityType,
			  @RequestParam(value = "cateType", required = false) String cateType,
			  @RequestParam(value = "catType", required = false) String catType,
			  @RequestParam(value = "newLabels", required = false) String newLabels,
			  @RequestParam(value = "avgPriceType", required = false) String avgPriceType,
			  @RequestParam(value = "promoteType", required = false) String promoteType,
			  @RequestParam(value = "imgPathChanged", required = false) String imgPathChanged,
			  @RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws IOException{
		    Project project = new Project();
	    	project.setActiveId(activeId);
	    	if(!StringUtils.isEmpty(imgPathChanged)){//图片路径改变
	    		String path = projectService.uploadImage(myfiles, request, activeId);  
	    		project.setActiveImgPath(path);
	    		project.setPathChanged(true);
	    	}
	    	project.setInsertTime(new Timestamp(System.currentTimeMillis()));
	    	project.setActiveTypeId(typeId);
	    	project.setProjectComments(projectComments);
	    	//掉用本地方法封装案例
			addCatsForProject(projectLabels, cityType, cateType, catType,
					avgPriceType,promoteType, project);
			//封装新增加的分类
			if(!StringUtils.isEmpty(newLabels)){
				project.setNewLabels(newLabels);
			}
		    if(projectId!=null&&projectId.intValue()!=0){
		    	//编辑操作
		    	project.setProjectId(projectId);
		    	projectService.updateProjectByID(project);
		    	return this.toProjectItems(request, null, null, null, null, null, null,null, null,null, null, null, null);
		    }else{
		    	projectService.addProject(project);
		    	return this.searchProject(request, null, null, null, null);
		    }
	    }
	/**
	 * 对案例增加分类
	 */
	private void addCatsForProject(String projectLabels, String cityType,
			String cateType, String catType, String avgPriceType,String promoteType,
			Project project) {
		if (!StringUtils.isEmpty(projectLabels)) {
			projectLabels = projectLabels.trim();
			String[] labelIds = projectLabels.indexOf(Constant.label_sperater) != -1 ? projectLabels
					.split(Constant.label_sperater): new String[] { projectLabels };
			project.setLabelIds(labelIds);
		}
		if(!StringUtils.isEmpty(avgPriceType)){
			project.setAvgPriceType(avgPriceType);
		}
		if(!StringUtils.isEmpty(cityType)){
			project.setCityType(cityType);
		}
		if(!StringUtils.isEmpty(cateType)){
			project.setCateType(cateType);
		}
		if(!StringUtils.isEmpty(catType)){
			project.setCatType(catType);
		}
		if(!StringUtils.isEmpty(promoteType)){
			project.setPromoteType(promoteType);;
		}
	}
	  
	  /**
		 * 删除
		 */
		@RequestMapping(value = "deleteProject", method = { RequestMethod.POST })
		@ResponseBody
		public Object deleteProject(Integer projectId, HttpServletRequest request) {
			final Map<String, Object> map = new HashMap<String, Object>();
			Integer flag = 0;
			int id = 0;//删除后返回值
			try {
				Project project = new Project();
				project.setProjectId(projectId);
				if (projectId.intValue()!=0) {
					Project dbProject = this.projectService.selectProjectByID(project);
					if(dbProject!=null){
						id = this.projectService.removeProjectByObject(dbProject);
						flag = 1;
					}
				}
			} catch (Exception e) {
				flag = 0;
				LOG.error("ajax请求--错误", e);
			}
			map.put("flag", flag);
			map.put("id", id);
			return map;
		}
}  



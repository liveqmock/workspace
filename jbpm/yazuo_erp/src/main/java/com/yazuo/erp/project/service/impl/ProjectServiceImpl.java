package com.yazuo.erp.project.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.project.dao.ActiveDao;
import com.yazuo.erp.project.dao.ProjectDao;
import com.yazuo.erp.project.dao.ProjectLabelDao;
import com.yazuo.erp.project.service.LabelService;
import com.yazuo.erp.project.service.ProjectService;
import com.yazuo.erp.project.vo.Active;
import com.yazuo.erp.project.vo.Label;
import com.yazuo.erp.project.vo.Project;
import com.yazuo.erp.project.vo.ProjectLabel;
import com.yazuo.util.DeviceTokenUtil;
import com.yazuo.util.StringUtils;

@Service
public class ProjectServiceImpl implements ProjectService{

	private static final Log LOG = LogFactory.getLog("statement");
	
	final static String imgLocationPath = DeviceTokenUtil.getPropertiesValue("imgLocationPath");
	@Resource
	private ActiveDao activeDao;

	@Resource
	private ProjectDao projectDao;
	
	@Resource
	private ProjectLabelDao projectLabelDao;
	
	@Resource
	private LabelService labelService;
	
	/**
	 * 返回满足条件的数据字典
	 */
	public Map<String, Object> selectAllDictionarys(){
		Map<String, Object> object = new HashMap<String, Object>();
		List<Map<String, Object>> listAvgPrice = this.projectDao.selectDictionarys(Constant.DIC_TYPE_AVGPRICE, null);
		List<Map<String, Object>> listCat = this.projectDao.selectDictionarys(Constant.DIC_TYPE_CAT, null);
		List<Map<String, Object>> listCate = this.projectDao.selectDictionarys(Constant.DIC_TYPE_CATE, null);
		List<Map<String, Object>> listCity = this.projectDao.selectDictionarys(Constant.DIC_TYPE_CITY, null);
		List<Map<String, Object>> listPromote = this.projectDao.selectDictionarys(Constant.DIC_TYPE_PROMOTE, null);
		object.put("listAvgPrice", listAvgPrice);
		object.put("listCat", listCat);
		object.put("listCate", listCate);
		object.put("listCity", listCity);
		object.put("listPromote", listPromote);
		return object;
	}
	
	/**
	 * 返回所有活动
	 */
	public List<Map<String, Object>> getAllActives(Active active){
		return activeDao.getAllActives(active);
	}
	
	/**
	 * 按分页方式每次返回一组数据
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getActiveMap(Active active, int page, int pageSize){
		Map<String, Object> map =  activeDao.getActiveMap(active, page, pageSize);
		@SuppressWarnings("unchecked")
		List<Active> list = (List)map.get("rows");
		for (Active iterActive: list) {
			Integer activeId = iterActive.getActiveId();
			Project project = new Project();
			project.setActiveId(activeId);
			if(this.projectDao.selectProjectByObject(project)!=null){
				iterActive.setHasProject(true);
			}else {
				iterActive.setHasProject(false);
			}
		}
		return map;
	}
	/**
	 * 上传营销活动图片到服务器
	 * @return upload file folder path.
	 */
	public String uploadImage(MultipartFile[] myfiles, HttpServletRequest request, Integer activeId)
			throws IOException {
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
		//如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
		//并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件  
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\文件夹中  
		String realPath = request.getSession().getServletContext().getRealPath(imgLocationPath)
				+ File.separator + "active" + activeId.intValue(); 
		String fileName = null;//默认只上传一个文件
		for(MultipartFile myfile : myfiles){
		    if(myfile.isEmpty()){  
		        LOG.info("文件未上传");  
		    }else{
		    	String originalFIleName = myfile.getOriginalFilename();
		        LOG.info("文件长度: " + myfile.getSize());  
		        LOG.info("文件类型: " + myfile.getContentType());  
		        LOG.info("文件名称: " + myfile.getName());  
		        LOG.info("文件原名: " + originalFIleName);  
		        LOG.info("========================================"); 
		        fileName = originalFIleName;
		        //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
		        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));  
		    }  
		}
		return "active" + activeId.intValue()+ "/" + fileName;
	}  
	
	@Override
	public String getImagePath(){
		return this.imgLocationPath;
	}
	@Override
	public Project selectProjectByID(Project Project){
		return projectDao.selectProjectByID(Project);
	}


	/**
	 * get list of active with by active object.
	 */
	public List<Active> selectActiveListByObject(Active active){
		return activeDao.selectActiveListByObject(active);
	}
	/**
	 * to search project object and cascaded labels.
	 */
	public Project selectProjectByObject(Project project){
		Project dbProject =  projectDao.selectProjectByObject(project);
		
		//对project对象增加labels
		Integer activeId = dbProject.getActiveId();
		if(activeId!=null){
			Active active = new Active();
			active.setActiveId(activeId);
			Active dbActive = activeDao.selectActiveListByObject(active).get(0);
			dbProject.setActive(dbActive);
		}
		this.addLabelsForProject(dbProject);
		//对project对象增加组分类如城市，注：尽管有n+1问题，但是对于仅有的4个组是可以接受的
		updateProjectByType(dbProject);
		return dbProject;
	}
	/**
	 * 对project对象增加组分类如城市，注：尽管有n+1问题，但是对于仅有的4个组是可以接受的
	 */
	private void updateProjectByType(Project dbProject) {
		List<Map<String, Object>> listCity = this.projectDao.selectDictionarysByKeys(Constant.DIC_TYPE_CITY, dbProject.getCityType());
		List<Map<String, Object>> listCat = this.projectDao.selectDictionarysByKeys(Constant.DIC_TYPE_CAT, dbProject.getCatType());
		List<Map<String, Object>> listCate = this.projectDao.selectDictionarysByKeys(Constant.DIC_TYPE_CATE, dbProject.getCateType());
		List<Map<String, Object>> listAvgPrice = this.projectDao.selectDictionarysByKeys(Constant.DIC_TYPE_AVGPRICE, dbProject.getAvgPriceType());
		String promoteType = dbProject.getPromoteType();
		if(StringUtils.isEmpty(promoteType)){
			promoteType = "0";//设置为空表示不查找
		}
		List<Map<String, Object>> listPromote = this.projectDao.selectDictionarysByKeys(Constant.DIC_TYPE_PROMOTE, promoteType );
		dbProject.setListAvgPrice(listAvgPrice);
		dbProject.setListCat(listCat);
		dbProject.setListCate(listCate);
		dbProject.setListCity(listCity);
		dbProject.setListPromote(listPromote);
	}
	
	/**
	 * 查询操作 为活动添加对应的标签
	 * @param dbProject
	 */
	private void addLabelsForProject(Project dbProject){
		Integer projectId = dbProject.getProjectId();
		ProjectLabel projectLabel = new ProjectLabel();
		projectLabel.setProjectId(projectId);
	    List<ProjectLabel> list = projectLabelDao.selectProjectLabelListByObject(projectLabel);
	    List<Label> labels = new ArrayList<Label>();
	    for (ProjectLabel dbPprojectLabe : list) {
	    	Integer labelId = dbPprojectLabe.getLabelId();
	    	Label label = new Label();
	    	label.setLabelId(labelId);
	    	Label dbLabel = labelService.selectLabelByID(label);
	    	labels.add(dbLabel);
		}
	    dbProject.setLabels(labels);
	}

	@Override
	public List<Project> selectProjectListByObject(Project project){
		return projectDao.selectProjectListByObject(project);
	}

	@Override
	public Map<String, Object> selectListByPage(Project project,int page, int pageSize,List<Active> dbActives, String activeName){
		Map<String, Object> map = projectDao.selectListByPage(project,page,pageSize,dbActives, activeName);
		@SuppressWarnings("unchecked")
		List<Project> list = (List)map.get("rows");
		//添加对应的组分类
		for (Project prject : list) {
			updateProjectByType(prject);
		}
		//添加案例对应的活动
		for (Project iterProject: list) {
			Integer activeId = iterProject.getActiveId();
			Active active = new Active();
			active.setActiveId(activeId);
			Active dbActive = this.activeDao.selectActiveListByObject(active).get(0);
			iterProject.setActive(dbActive);
			//为活动添加对应的标签
			this.addLabelsForProject(iterProject);
		}
		return map;
	}


	@Override
	public int selectProjectCount(Project project){
		return projectDao.selectProjectCount(project);
	}

	/**
	 * to save a project object to database and also save cascaded table 'project_label' and 'label'-
	 * if exists 'labels' input value in html view.
	 */
	public int addProject(Project project){
		int projectId = projectDao.addProject(project);
		//包含新增加的标签
		String[] labelIds = labelService.addNewLabels(project.getNewLabels());
		if(labelIds!=null){
			String[] existsLabelIds = project.getLabelIds();
			if(existsLabelIds!=null){
				String[] newArray=new String[labelIds.length+existsLabelIds.length];
				System.arraycopy(labelIds,0,newArray,0,labelIds.length);
				System.arraycopy(existsLabelIds,0,newArray,labelIds.length, existsLabelIds.length);
				project.setLabelIds(newArray);
			}else{
				project.setLabelIds(labelIds);
			}
		}
		//更新相应的label
		updateLabelForProject(project, projectId);
		return projectId;
	}
	/**
	 *  对当前的project更新labels
	 * @param project
	 * @param projectId
	 */
	private void updateLabelForProject(Project project, int projectId) {
		String[] labels = project.getLabelIds();
		//首先删除所有关联
		this.deleteProjectLabelByProjectId(projectId);
		if(labels!=null){
			for (String labelId : labels) {
				if(StringUtils.isEmpty(labelId)) continue;
				labelId = labelId.trim();
				Integer intLabelId = Integer.parseInt(labelId);
				ProjectLabel projectLabel = new ProjectLabel();
				projectLabel.setLabelId(intLabelId);
				projectLabel.setProjectId(projectId);
				// add 'ProjectLabel' to database.
				projectLabel.setInsertTime(new Timestamp(System.currentTimeMillis()));
				projectLabelDao.addProjectLabel(projectLabel);
			}
		}
	}
	/**
	 *  delete all 'ProjectLabel' which exists projectId. 
	 * @param projectId
	 */
	private void deleteProjectLabelByProjectId(int projectId){
		ProjectLabel projectLabel = new ProjectLabel();
		projectLabel.setProjectId(projectId);
		List<ProjectLabel> projectLabellist = projectLabelDao.selectProjectLabelListByObject(projectLabel);
		if(projectLabellist!=null&&projectLabellist.size()>0){
			projectLabelDao.removeProjectLabelList(projectLabellist);
		}
	}
	@Override
	public int addProjectList(List<Project>  projectlist){
		return projectDao.addProjectList(projectlist);
	}


	/**
	 * 删除案例和对应的案例在关联表中的引用
	 */
	public int removeProjectByObject(Project project){
		Integer projectId = project.getProjectId();
		ProjectLabel projectLabel = new ProjectLabel();
		projectLabel.setProjectId(projectId);
		List<ProjectLabel> dbProjectLabel = projectLabelDao.selectProjectLabelListByObject(projectLabel);
		projectLabelDao.removeProjectLabelList(dbProjectLabel);
		return projectDao.removeProjectByObject(project);
	}


	 /**
	  * 查询所有活动类型
	  */
	 public List<Map<String, Object>> getAllActiveTypes(){
		 return activeDao.getAllActiveTypes();
	 }
	@Override
	public int removeProjectList(List<Project>  projectlist){
		return projectDao.removeProjectList(projectlist);
	}


	/**
	 * 更新project和对应的label
	 */
	public int updateProjectByID(Project project){
		//包含新增加的标签
		String[] labelIds = labelService.addNewLabels(project.getNewLabels());
		if(labelIds!=null){
			String[] existsLabelIds = project.getLabelIds();
			if(existsLabelIds!=null){
				String[] newArray=new String[labelIds.length+existsLabelIds.length];
				System.arraycopy(labelIds,0,newArray,0,labelIds.length);
				System.arraycopy(existsLabelIds,0,newArray,labelIds.length, existsLabelIds.length);
				project.setLabelIds(newArray);
			}else{
				project.setLabelIds(labelIds);
			}
		}
		//更新相应的label
		updateLabelForProject(project, project.getProjectId());
		//默认path不更新
		if(project!=null&&project.getPathChanged()!=null&&project.getPathChanged()==true){
		}else{
			Project dbProject = projectDao.selectProjectByID(project);
			project.setActiveImgPath(dbProject.getActiveImgPath());
			
		}
		return projectDao.updateProjectByID(project);
	}


	@Override
	public int updateProjectByObject(Project updateProject , Project whereProject){
		return projectDao.updateProjectByObject( updateProject ,  whereProject);
	}


	@Override
	public int updateProjectList(List<Project> projectlist){
		return projectDao.updateProjectList(projectlist);
	}


	@Override
	public Integer selectProjectSeqNextVal(){
		return projectDao.selectProjectSeqNextVal();
	}


}

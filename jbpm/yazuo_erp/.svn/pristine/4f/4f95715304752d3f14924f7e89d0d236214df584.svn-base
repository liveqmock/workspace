package com.yazuo.erp.project.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.project.vo.Active;
import com.yazuo.erp.project.vo.Project;

public interface ProjectService {
	public Project selectProjectByID(Project Project);

	public Project selectProjectByObject(Project project);

	public List<Project> selectProjectListByObject(Project project);

	public int selectProjectCount(Project project) throws Exception;

	public int addProject(Project project);

	public int addProjectList(List<Project> projectlist);

	public int removeProjectByObject(Project project);

	public int removeProjectList(List<Project> projectlist);

	public int updateProjectByID(Project project);

	public int updateProjectByObject(Project updateProject, Project whereProject);

	public int updateProjectList(List<Project> projectlist);

	public Integer selectProjectSeqNextVal();

	public List<Map<String, Object>> getAllActives(Active active);

	public String uploadImage(MultipartFile[] myfiles,
			HttpServletRequest request, Integer activeId) throws IOException;

	public String getImagePath();

	public List<Active> selectActiveListByObject(Active active);

	public Map<String, Object> getActiveMap(Active active, int page,
			int pageSize);

	public Map<String, Object> selectListByPage(Project project, int page,
			int pageSize, List<Active> dbActives, String activeName);

	public List<Map<String, Object>> getAllActiveTypes();

	public Map<String, Object> selectAllDictionarys();
}

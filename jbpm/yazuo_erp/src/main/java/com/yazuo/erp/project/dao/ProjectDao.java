package com.yazuo.erp.project.dao;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.project.vo.Active;
import com.yazuo.erp.project.vo.Project;

public interface ProjectDao {
	public Project selectProjectByID(Project Project);

	public Project selectProjectByObject(Project project);

	public List<Project> selectProjectListByObject(Project project);

	public Map<String, Object> selectListByPage(Project project, int page,
			int pageSize, List<Active> dbActives, String activeName);

	public int selectProjectCount(Project project);

	public int addProject(Project project);

	public int addProjectList(List<Project> projectlist);

	public int removeProjectByObject(Project project);

	public int removeProjectList(List<Project> projectlist);

	public int updateProjectByID(Project project);

	public int updateProjectByObject(Project updateProject, Project whereProject);

	public int updateProjectList(List<Project> projectlist);

	public Integer selectProjectSeqNextVal();

	public List<Map<String, Object>> selectDictionarys(String dictionaryType,
			String key);

	public List<Map<String, Object>> selectDictionarysByKeys(
			String dictionaryType, String keys);
}

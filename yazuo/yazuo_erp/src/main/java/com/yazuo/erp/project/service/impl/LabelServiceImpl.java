package com.yazuo.erp.project.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.project.dao.LabelDao;
import com.yazuo.erp.project.service.LabelService;
import com.yazuo.erp.project.vo.Label;
import com.yazuo.util.StringUtils;

@Service
public class LabelServiceImpl implements LabelService{

	@Resource
	private LabelDao labelDao;

	@Override
	public Label selectLabelByID(Label Label){
		return labelDao.selectLabelByID(Label);
	}


	@Override
	public Label selectLabelByObject(Label label){
		return labelDao.selectLabelByObject(label);
	}
	/**
	 * 增加标签，返回新增加的标签主键id
	 * @param newLabels
	 * @return
	 */
	public String[] addNewLabels(String newLabels) {
		if (!StringUtils.isEmpty(newLabels)) {
			newLabels = newLabels.trim();
			String[] strKeys = new String[] { newLabels };
			String regEx = "[\\s\\p{Zs}]";
	        Pattern p = Pattern.compile(regEx);
	        Matcher m = p.matcher(newLabels);
	        if (m.find()) {
	        	newLabels = newLabels.replaceAll("[\\s\\p{Zs}]", Constant.logic_sperater);
	        	strKeys = newLabels.split(Constant.logic_sperater);
	        }
			String[] labelIds = new String[strKeys.length];
			for (int i = 0; i < strKeys.length; i++) {
				String labelName = strKeys[i];
				if (StringUtils.isEmpty(labelName))
					continue;
				Label newLabel = new Label();
				newLabel.setInsertTime(new Timestamp(System.currentTimeMillis()));
				newLabel.setLableName(labelName);
				Integer newLabelId = this.labelDao.addLabel(newLabel);
				labelIds[i] = newLabelId.toString();
			}
			return labelIds;
		}
		return null;
	}
	@Override
	public List<Label> selectLabelListByObject(Label label){
		return labelDao.selectLabelListByObject(label);
	}


	@Override
	public Map<String, Object> selectListByPage(Label label,int page, int pageSize){
		return labelDao.selectListByPage(label,page,pageSize);
	}


	@Override
	public int selectLabelCount(Label label){
		return labelDao.selectLabelCount(label);
	}


	@Override
	public int addLabel(Label label){
		return labelDao.addLabel(label);
	}


	@Override
	public int addLabelList(List<Label>  labellist){
		return labelDao.addLabelList(labellist);
	}


	@Override
	public int removeLabelByObject(Label label){
		return labelDao.removeLabelByObject(label);
	}


	@Override
	public int removeLabelList(List<Label>  labellist){
		return labelDao.removeLabelList(labellist);
	}


	@Override
	public int updateLabelByID(Label label){
		return labelDao.updateLabelByID(label);
	}


	@Override
	public int updateLabelByObject(Label updateLabel , Label whereLabel){
		return labelDao.updateLabelByObject( updateLabel ,  whereLabel);
	}


	@Override
	public int updateLabelList(List<Label> labellist){
		return labelDao.updateLabelList(labellist);
	}


	@Override
	public Integer selectLabelSeqNextVal(){
		return labelDao.selectLabelSeqNextVal();
	}


}

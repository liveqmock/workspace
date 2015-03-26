package com.yazuo.erp.project.dao;
import java.util.List;
import java.util.Map;

import com.yazuo.erp.project.vo.Label;
public interface LabelDao {
	public Label  selectLabelByID(Label Label);

	public Label  selectLabelByObject(Label label);

	public List<Label>  selectLabelListByObject(Label label);

	public Map<String, Object>  selectListByPage(Label label,int page, int pageSize);

	public int  selectLabelCount(Label label);

	public int  addLabel(Label label);

	public int  addLabelList(List<Label>  labellist);

	public int  removeLabelByObject(Label label);

	public int  removeLabelList(List<Label>  labellist);

	public int  updateLabelByID(Label label);

	public int  updateLabelByObject(Label updateLabel , Label whereLabel);

	public int  updateLabelList(List<Label> labellist);

	public Integer  selectLabelSeqNextVal();


}

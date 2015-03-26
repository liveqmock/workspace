package com.yazuo.erp.video.dao;
import java.util.List;
import java.util.Map;

import com.yazuo.erp.video.vo.VideoCat;
public interface VideoCatDao {
	public VideoCat  selectVideoCatByID(VideoCat VideoCat);

	public VideoCat  selectVideoCatByObject(VideoCat videoCat);

	public List<VideoCat>  selectVideoCatListByObject(VideoCat videoCat);

	public Map<String, Object>  selectListByPage(VideoCat videoCat,int page, int pageSize);

	public int  selectVideoCatCount(VideoCat videoCat);

	public int  addVideoCat(VideoCat videoCat);

	public int  addVideoCatList(List<VideoCat>  videoCatlist);

	public int  removeVideoCatByObject(VideoCat videoCat);

	public int  removeVideoCatList(List<VideoCat>  videoCatlist);

	public int  updateVideoCatByID(VideoCat videoCat);

	public int  updateVideoCatByObject(VideoCat updateVideoCat , VideoCat whereVideoCat);

	public int  updateVideoCatList(List<VideoCat> videoCatlist);

	public Integer  selectVideoCatSeqNextVal();


}

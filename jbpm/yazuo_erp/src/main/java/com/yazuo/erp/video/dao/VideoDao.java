package com.yazuo.erp.video.dao;
import java.util.List;
import java.util.Map;

import com.yazuo.erp.video.vo.Video;
public interface VideoDao {
	public Video  selectVideoByID(Video Video);

	public Video  selectVideoByObject(Video video);

	public List<Video>  selectVideoListByObject(Video video);

	public Map<String, Object>  selectListByPage(Video video,int page, int pageSize);

	public int  selectVideoCount(Video video);

	public int  addVideo(Video video);

	public int  addVideoList(List<Video>  videolist);

	public int  removeVideoByObject(Video video);

	public int  removeVideoList(List<Video>  videolist);

	public int  updateVideoByID(Video video);

	public int  updateVideoByObject(Video updateVideo , Video whereVideo);

	public int  updateVideoList(List<Video> videolist);

	public Integer  selectVideoSeqNextVal();

	public List<Video> selectVideoAndCatByObject(Video video);


}

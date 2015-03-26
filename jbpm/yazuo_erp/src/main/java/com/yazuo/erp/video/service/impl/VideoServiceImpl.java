package com.yazuo.erp.video.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.HttpClientUtil;
import com.yazuo.erp.video.dao.VideoDao;
import com.yazuo.erp.video.service.VideoService;
import com.yazuo.erp.video.vo.Video;
import com.yazuo.util.DeviceTokenUtil;

@Service
public class VideoServiceImpl implements VideoService{

	private static final Log LOG = LogFactory.getLog("statement");
	
	private static String viewVideoLocationPath = DeviceTokenUtil.getPropertiesValue("viewVideoLocationPath");
	private static String writeVideoLocationPath = DeviceTokenUtil.getPropertiesValue("writeVideoLocationPath");
	
	@Resource
	private VideoDao videoDao;

    @Value("${videoServerAppPath}")
    private String videoServerAppPath;

	@Override
	public Video selectVideoByID(Video Video){
		return videoDao.selectVideoByID(Video);
	}


	@Override
	public Video selectVideoByObject(Video video){
		return videoDao.selectVideoByObject(video);
	}


	@Override
	public List<Video> selectVideoListByObject(Video video){
		return videoDao.selectVideoListByObject(video);
	}
	@Override
	public List<Video> selectVideoAndCatByObject(Video video){
		return videoDao.selectVideoAndCatByObject(video);
	}


	@Override
	public Map<String, Object> selectListByPage(Video video,int page, int pageSize){
		return videoDao.selectListByPage(video,page,pageSize);
	}


	@Override
	public int selectVideoCount(Video video){
		return videoDao.selectVideoCount(video);
	}


	@Override
	public int addVideo(Video video){
		return videoDao.addVideo(video);
	}


	@Override
	public int addVideoList(List<Video>  videolist){
		return videoDao.addVideoList(videolist);
	}


	@Override
	public int removeVideoByObject(Video video){
		return videoDao.removeVideoByObject(video);
	}


	@Override
	public int removeVideoList(List<Video>  videolist){
		return videoDao.removeVideoList(videolist);
	}


	@Override
	public int updateVideoByID(Video video){
		return videoDao.updateVideoByID(video);
	}


	@Override
	public int updateVideoByObject(Video updateVideo , Video whereVideo){
		return videoDao.updateVideoByObject( updateVideo ,  whereVideo);
	}


	@Override
	public int updateVideoList(List<Video> videolist){
		return videoDao.updateVideoList(videolist);
	}


	@Override
	public Integer selectVideoSeqNextVal(){
		return videoDao.selectVideoSeqNextVal();
	}
	@Override
	public String getVideoLocationPath(){
		return this.viewVideoLocationPath;
	}
	
	@Override
	// 保存文件到指定的服务器地址
	public boolean saveFileConfig(Video video) {
		int videoId = this.videoDao.addVideo(video);
		 String url = this.videoServerAppPath + "file/uploadVideo.do";
		 MultipartFile[] files = new MultipartFile[1];
		 files[0] = video.getFile();
		 Object result = new HttpClientUtil().getUploadFileResponseBody(url, files ,
				 this.writeVideoLocationPath, this.videoServerAppPath, video.getTempFilePath());
		 JSONObject jsonResult = (JSONObject)result;
		 JSONArray jsonArray = (JSONArray)jsonResult.get("data");
		 JSONObject array0 = (JSONObject)jsonArray.get(0);
		 video.setVideoPath(array0.get("fileName").toString());
		 video.setVideoId(videoId);
		 this.videoDao.updateVideoByID(video);
		 //return FileUploaderUtil.uploadFile(myfiles, "videoPath", null, 0, request);
		 return true;
	}

	public void SaveFileFromInputStream(InputStream stream, String path,
			String filename) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			FileOutputStream fs = new FileOutputStream(path + filename);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

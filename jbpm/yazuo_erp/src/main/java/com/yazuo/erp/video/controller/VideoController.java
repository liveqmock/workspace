package com.yazuo.erp.video.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.HttpClientUtil;
import com.yazuo.erp.video.service.VideoCatService;
import com.yazuo.erp.video.service.VideoService;
import com.yazuo.erp.video.vo.Video;
import com.yazuo.erp.video.vo.VideoCat;
import com.yazuo.util.DeviceTokenUtil;

@Controller
@RequestMapping("erp/video") 
public class VideoController {

	private static final Log LOG = LogFactory.getLog("statement");
	
	@Resource
	private VideoCatService videoCatService;
	
	@Resource
	private VideoService videoService;

    @Value("${videoServerAppPath}")
    private String videoServerAppPath;

    @Value("${writeVideoLocationPath}")
    private String writeVideoLocationPath;
	
	@RequestMapping(value = "toUploadVideo", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
  public ModelAndView toUploadVideo(HttpServletResponse response,
			HttpServletRequest request) {
		
	final List<VideoCat> videoCatItems = videoCatService.selectVideoCatListByObject(null);
	final ModelAndView mav = new ModelAndView("video/uploadVideo");
	mav.addObject("videoCatItems", videoCatItems);
    return mav;  
  }  
	
	@RequestMapping(value = "toVideoItems", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
  public ModelAndView toVideoItems(HttpServletResponse response,HttpServletRequest request,
		  @RequestParam(value = "videoCatId", required = false) Integer videoCatId,
		  @RequestParam(value = "page", required = false) Integer page,
		  @RequestParam(value = "pageSize", required = false) Integer pageSize,
		  @RequestParam(value = "videoName", required = false) String videoName) {
		Video video = new Video();
		video.setVideoCatId(videoCatId == null ? 0 : videoCatId);
		video.setVideoName(videoName);
		page = (page==null||page.intValue()==0)? page=1: page;//如果为空显示第一页
		pageSize = (pageSize==null||pageSize.intValue()==0)? Constant.pageSize: pageSize;//如果为空显示默认条数
		return getVideoItemsView(video, page, pageSize,videoCatId);
	}  
	
	@RequestMapping(value = "toViewVideo", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView toViewVideo(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value = "videoId", required = true) Integer videoId) {
		Video video = new Video();
		video.setVideoId(videoId);
		final ModelAndView mav = new ModelAndView("video/viewVideo");
		final List<VideoCat> videoCatItems = videoCatService.selectVideoCatListByObject(null);
		mav.addObject("videoCatItems", videoCatItems);
		final Video dbVideo = videoService.selectVideoByID(video);
		VideoCat videoCat = new VideoCat();
		videoCat.setVideoCatId(dbVideo.getVideoCatId());
		VideoCat dbVideoCat = videoCatService.selectVideoCatByID(videoCat); 
		dbVideo.setCatDesc(dbVideoCat.getCatDesc());
		mav.addObject("video", dbVideo);
		final String videoPath = videoService.getVideoLocationPath();
		mav.addObject("videoPath", videoPath);
		return mav;
	
	}  

	@RequestMapping(value = "saveFileUpload", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView saveFileUpload(
			@RequestParam(value = "videoCat", required = false) Integer videoCat,
			@RequestParam(value = "videoName", required = false) String videoName,
			@RequestParam(value = "videoDesc", required = false) String videoDesc,
			@RequestParam(value = "presenter", required = false) String presenter,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request){
		final Video video = new Video();
		video.setInsertTime(new Timestamp(System.currentTimeMillis()));
		video.setVideoCatId(videoCat);
		video.setVideoName(videoName);
		video.setVideoDesc(videoDesc);
		video.setUploadTime(new Timestamp(System.currentTimeMillis()));
		video.setVideoPath(file.getOriginalFilename());
		video.setPresenter(presenter);
		video.setFile(file);
		String tempFilePath =  request.getSession().getAttribute(Constant.TEMP_FILE_PATH).toString();
		video.setTempFilePath(tempFilePath);
		try {
			this.videoService.saveFileConfig(video);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
		return getVideoItemsView(null, 1, Constant.pageSize, 0); //get all records if parameter is null.
	}
	
	/**
	 * below code control page jump to videoItems.jsp.
	 * @videCatId : option value 0 means all category.
	 * @return
	 */
	private ModelAndView getVideoItemsView(Video video, int page, int pageSize,Integer videoCatId) {
		final ModelAndView mav = new ModelAndView("video/videoItems");
		final List<VideoCat> videoCatItems = videoCatService.selectVideoCatListByObject(null);
		mav.addObject("videoCatItems", videoCatItems);
		mav.addObject("page", page);
		mav.addObject("videoCatId", videoCatId);
		mav.addObject("videoName", video !=null? video.getVideoName(): "");
		mav.addObject("countVideo", this.videoService.selectVideoCount(video));
		final Map<String, Object> resultMapPerPage = videoService.selectListByPage(video, page, pageSize);
		mav.addObject("resultMapPerPage", resultMapPerPage);
		return mav;
	}

	/**
	 * 验证视频管理密码是否正确
	 */
	@RequestMapping(value = "checkPassword", method = { RequestMethod.POST })
	@ResponseBody
	public Object checkPassword(String pwd, HttpServletRequest request) {
		final Map<String, Object> map = new HashMap<String, Object>();
	    Integer flag = 0;
		try {
			//TODO this business requirement is temporary solution.
			final String userPassword = DeviceTokenUtil.getPropertiesValue("user_password");
			if (userPassword.equals(pwd)) {
				request.getSession().setAttribute("legalUser", "1");
				flag = 1;
			}

		} catch (Exception e) {
			flag = 0;
			LOG.error("ajax请求--错误", e);
		}
		map.put("flag", flag);
		return map;
	}
	/**
	 * 删除视频
	 */
	@RequestMapping(value = "deleteVideo", method = { RequestMethod.POST })
	@ResponseBody
	public Object deleteVideo(Integer videoId, HttpServletRequest request) {
		final Map<String, Object> map = new HashMap<String, Object>();
		Integer flag = 0;
		int id = 0;//删除后返回值
		try {
			Video video = new Video();
			video.setVideoId(videoId);
			if (videoId.intValue()!=0) {
				Video dbVideo = this.videoService.selectVideoByID(video);
				if(dbVideo!=null){
					id = this.videoService.removeVideoByObject(video);
					String url = this.videoServerAppPath + "file/delete.do";
					new HttpClientUtil().getDeleteFileResponseBody(url, dbVideo.getVideoPath(), this.writeVideoLocationPath);
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
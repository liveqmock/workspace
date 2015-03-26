/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.exception.ErpException;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.util.DeviceTokenUtil;
import com.yazuo.util.StringUtils;

/**
 * 文件上传相关类
 * 
 * @author kyy
 * @date 2014-6-18 下午4:24:55
 */
public class FileUploaderUtil {

	private static final Log LOG = LogFactory.getLog(FileUploaderUtil.class);
	
	/***
	 * 
	 * @param inputStream  上传的文件流
	 * @param configPath 配置文件中路径
	 * @param maxFileSize  文件大小限制 （2m = 2*1024*1024）是转化后的不是
	 * @param originalFileName  文件原名
	 * @return 相对路径，绝对路径（盘符路径），文件名
	 */
	@Deprecated //改用 石山视频来实现
	public static Object writeUploadFile(ServletInputStream inputStream , String videoServerAppPath, String configPath, 
			long maxFileSize, String originalFileName, HttpServletRequest request) throws IOException {
		
		JsonResult json = new JsonResult();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuilder relPath = new StringBuilder();
		relPath.append(videoServerAppPath).append(configPath);
		String realPath = request.getSession().getServletContext().getRealPath(configPath);
		LOG.info("relPath: "+ relPath);
		LOG.info("realPath: "+ realPath);
			if (inputStream == null) {
				LOG.info("文件未上传");
			} else {
				String endSuffix = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.length());
				String fileName = UUID.randomUUID().toString() + "." + endSuffix;
				relPath.append("/").append(fileName);
				File file = new File(realPath, fileName);
				FileUtils.copyInputStreamToFile(inputStream, file);
				// 返回信息
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("absolutePath", file.getPath()); // 盘符路径 
				m.put("relativePath", relPath.toString()); // 全路径
				m.put("originalFileName", originalFileName);//test销售引荐-对接人见面.mp4
				m.put("fileName", fileName);//119378c2-3e13-4837-8b4a-229a8d20a52b.mp4
				list.add(m);
			}
		json.setFlag(true);
		json.setMessage("上传成功");
		json.setData(list);
		return json;
	}
	/***
	 * @Description 上线流程文件上传  直接保存到实际的路径
	 * @param myfile 上传的文件
	 * @param relPath 最终要保存文件实际路径
	 * @param maxFileSize 文件大小限制 （2m = 2*1024*1024）是转化后的不是MB
	 * @return 相对路径，绝对路径（盘符路径），文件名等信息
	 */
	public static JsonResult uploadFile(MultipartFile myfile, String basePath, String relPath, long maxFileSize)
			throws IOException {
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\文件夹中
		JsonResult json = new JsonResult();
		String fileName = null;// 默认只上传一个文件
		Map<String, Object> map = new HashMap<String, Object>();
		if (myfile.isEmpty()) {
			LOG.info("文件为空");
			return json.setFlag(false).setMessage("上传失败, 文件为空");
		} else {
			String originalFileName = myfile.getOriginalFilename();
			Long fileSize = myfile.getSize();
			String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.length());
			fileName = UUID.randomUUID().toString() + "." + fileSuffix;
			String fullPath = basePath+ (StringUtils.isEmpty(relPath)?"": relPath)+ "/" + fileName;
			File file = new File(fullPath);
			if (maxFileSize != 0 && fileSize > maxFileSize) { // 需要判断大小
				return json.setFlag(false).setMessage(
						"上传失败，上传的文件大小为" + fileSize / 1024 / 1024 + "MB, " + "最大文件不能超过" + maxFileSize / 1024 / 1024 + "MB!");
			} else {
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
			}
			// 返回信息
			map.put("fileSize", fileSize); // 文件大小
			map.put("fileContentType", myfile.getContentType()); // 文件大小
			map.put("fileSuffix", fileSuffix); // 文件后缀
//				m.put("absolutePath", file.getPath()); // 盘符路径
			map.put("attachmentPath", null); // D:/..
			map.put("originalFileName", originalFileName);// test销售引荐-对接人见面.mp4
			map.put("attachmentName", fileName);// 119378c2-3e13-4837-8b4a-229a8d20a52b.mp4
		}
		return json.setFlag(true).setMessage("上传成功").setData(map);
	}

	/***
	 * @Description 文件上传  先保存到临时路径
	 * @param myfiles 上传的文件
	 * @param pathKey 配置文件中路径的key值 
	 * @param finalPath 最终要保存文件的路径
	 * @param maxFileSize 文件大小限制 （2m = 2*1024*1024）是转化后的不是m
	 * @return 相对路径，绝对路径（盘符路径），文件名
	 */
	@Deprecated
	public static Object uploadFile(MultipartFile[] myfiles, String pathKey, String finalPath, long maxFileSize,
			HttpServletRequest request) throws IOException {
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\文件夹中
		// 临时预览
		JsonResult json = new JsonResult();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String relativePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort() + "/"
				+ request.getContextPath();
		LOG.info(request.getLocalAddr()+"  "+request.getServerName()+"  "+relativePath.toString());
		StringBuilder relPath = new StringBuilder();

		String configPath = DeviceTokenUtil.getPropertiesValue(pathKey); // 配置文件config中配置的路径
//		String configPath = pathKey; // 配置文件config中配置的路径
		if (!StringUtils.isEmpty(finalPath)) { // 前台传过的组合路径
			configPath += "/" + finalPath;
		}
		relPath.append(relativePath).append("/").append(configPath);

		String realPath = request.getSession().getServletContext().getRealPath(configPath);
		String fileName = null;// 默认只上传一个文件
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				LOG.info("文件未上传");
			} else {

				String originalFileName = myfile.getOriginalFilename();
				LOG.info("文件长度: " + myfile.getSize() + "文件类型: " + myfile.getContentType());
				LOG.info("文件名称: " + myfile.getName() + "文件原名: " + originalFileName);

				long fileSize = myfile.getSize();
				String endSuffix = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.length());
				fileName = UUID.randomUUID().toString() + "." + endSuffix;
				relPath.append("/").append(fileName);
				File file = new File(realPath, fileName);

				// 是需要判断大小
				if (maxFileSize != 0) { // 需要判断大小
					if (fileSize < maxFileSize) {
						// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
						FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);

					} else {
						throw new TrainBizException("上传的文件大小为" + fileSize / 1024 / 1024 + "m,最大文件不能超过" + maxFileSize / 1024
								/ 1024 + "m!");
					}
				} else { // 不需要判断大小
					FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
				}
				// 返回信息
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("absolutePath", file.getPath()); // 盘符路径 
				m.put("relativePath", relPath.toString()); // 相对路径//http://localhost:8080//yazuo_erp/user-upload-img/video/119378c2-3e13-4837-8b4a-229a8d20a52b.mp4
				m.put("originalFileName", originalFileName);//test销售引荐-对接人见面.mp4
				m.put("fileName", fileName);//119378c2-3e13-4837-8b4a-229a8d20a52b.mp4
				m.put("fileSize", fileSize);
				list.add(m);
			}
		}
		json.setFlag(true);
		json.setMessage("上传成功");
		json.setData(list);
		return json;
	}
}

/** 
* @author kyy
* @version 创建时间：2015-1-30 上午11:11:25 
* 类说明  java将图片上传到图片服务器上
*/ 
package com.yazuo.weixin.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class FastDFSUtil {
	private static final Log log = LogFactory.getLog("weixin");
	ResourceBundle bundle = ResourceBundle.getBundle("image-config");

	// 图片服务器地址
	private final String dfsServerIp = bundle.getString("dfs.server.ip"); // 服务器ip
	private final int dfsServerPort = Integer.parseInt(bundle.getString("dfs.server.port")); // 服务器端口

	public String uploadImage(MultipartFile file) {
		if(file==null || file.getSize() == 0){
			return "";
		}
		//fastDFS方式  
        ClassPathResource cpr = new ClassPathResource("fdfs_yazuo.conf");  
        try {
			ClientGlobal.init(cpr.getClassLoader().getResource("fdfs_yazuo.conf").getPath());
		} catch (FileNotFoundException e2) {
			log.info("未找到文件。"+ e2.getMessage());
			e2.printStackTrace();
		} catch (IOException e2) {
			log.info(e2.getMessage());
			e2.printStackTrace();
		} catch (MyException e2) {
			log.info(e2.getMessage());
			e2.printStackTrace();
		}  
		
		String originalFileName = file.getOriginalFilename(); // 图片原始名称带后缀
		String fileExtName = originalFileName.substring(originalFileName.lastIndexOf(".")+1); //图片名称不带后缀  
		byte[] buffer = null;
		try {
			buffer = file.getBytes();
		} catch (IOException e1) {
			log.info(e1.getMessage());
			e1.printStackTrace();
		}
		// 建立连接
		TrackerGroup group = new TrackerGroup(new InetSocketAddress[] { new InetSocketAddress(dfsServerIp, dfsServerPort) });
		TrackerClient tracker = new TrackerClient(group);
		TrackerServer trackerServer = null;
		try {
			trackerServer = tracker.getConnection();
		} catch (IOException e) {
			log.info("获取fastdfs连接失败");
			e.printStackTrace();
		}  
	    StorageServer storageServer = null;  
	    StorageClient1 client = new StorageClient1(trackerServer, storageServer);  
	    
	    //设置元信息  
        NameValuePair[] metaList = new NameValuePair[3];  
        metaList[0] = new NameValuePair("fileName", originalFileName);  
        metaList[1] = new NameValuePair("fileExtName", fileExtName);  
        metaList[2] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
        
        //上传文件
        String result = null;
        try {
        	result = client.upload_file1(buffer, fileExtName, metaList);
        	log.info("第一次调用返回结果："+result);
        	if (!StringUtil.isNullOrEmpty(result))trackerServer.close();
		} catch (IOException e) {
			log.info("第一次调用异常，异常信息："+e.getMessage());
			e.printStackTrace();
		} catch (MyException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
        
        if (StringUtil.isNullOrEmpty(result)) {
        	try {
				result = client.upload_file1(buffer, fileExtName, metaList);
				log.info("第二次调用返回结果："+result);
				trackerServer.close();
			} catch (Exception e) {
				log.info("第二次调用异常，异常信息："+e.getMessage());
				e.printStackTrace();
			}
        }
        return result;
	}
	
	public static void main (String [] args) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println("----------"+addr.toString() + "@@@@--"+addr.getHostAddress());
			
			String str = addr.getHostAddress();
			System.out.println("-------------"+InetAddress.getByName(str)+"-----");
					
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

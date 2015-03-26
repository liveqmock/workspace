/**
 * @Description 基于HttpClient的工具类
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
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description 基于HttpClient的工具类
 * @date 2014-7-14 上午10:59:38
 */
public class HttpClientUtil {

    Log log = LogFactory.getLog(this.getClass());
    
	 /**
     * 文件上传： URL重写，请求由指定的服务器处理 
     *
     * @param url 待请求的URL
     * @return JSON 相应对象
     */
    public Object getUploadFileResponseBody(String url, MultipartFile[] myfiles, String videoPath, 
    		String videoServerAppPath, String tempFilePath) {
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        String tempFileFullPath = null;
        for (MultipartFile multipartFile : myfiles) {
        	try {
//	            FileBody bin = new FileBody(new File("D:\\Downloads\\123.mp4"));
            	File file = writeTempFile(multipartFile, tempFilePath);
            	FileBody binFile = new FileBody(file);
	            reqEntity.addPart("myfiles", binFile);
	            tempFileFullPath = file.getPath();
	            reqEntity.addTextBody("tempFileFullPath", tempFileFullPath);
				//默认上传一个文件
				reqEntity.addTextBody("fileName", multipartFile.getOriginalFilename());
			} catch (IOException e) {
				log.error("IOException take place when upload file: "+e);
			} 
		}
        reqEntity.addTextBody("filePath", videoPath);
        reqEntity.addTextBody("videoServerAppPath", videoServerAppPath);
        //reqEntity.setCharset(Charset.forName(this.charset));
        // 生成 HTTP 实体
        HttpEntity httpEntity = reqEntity.build();
        httpPost.setEntity(httpEntity);
        String resultStr = execRequestAndGetResponse(url, httpPost, httpClient);
    	
		//FileUtils.writeByteArrayToFile(file, myfiles.getBytes());
		//上传完成后要删除临时文件
		boolean deleteFlag = FileUtils.deleteQuietly(new File(tempFileFullPath));
		log.info("临时文件删除(temp file delete success? ): "+tempFileFullPath+ (deleteFlag ? " 成功(Yes)":" 失败(No)"));
		
        return JSONObject.fromObject(resultStr);
    }
    
    /**
     * 先将要上传的文件通过MultipartFile写入到项目根目录下的临时文件夹中
     */
	private File writeTempFile(MultipartFile multipartFile, String tempFilePath) throws IOException {
		String originalFileName = multipartFile.getOriginalFilename();
//		String tempFileFullPath = new File("").getAbsolutePath()+"/temp";
//		String tempFileFullPath = "/home/weixin/tomcat_weixin";
//		log.info("上传文件到临时文件路径tempFilePath: "+tempFilePath);
		String endSuffix = originalFileName.substring(originalFileName.lastIndexOf('.') + 1, originalFileName.length());
		String fileName = UUID.randomUUID().toString() + "." + endSuffix;
		File file = new File(tempFilePath, fileName);
		FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
		return file;
	}
    /**
     * 文件删除： URL重写，请求由指定的服务器处理
     *
     * @param url 待请求的URL
     * @return JSON 相应对象
     */
    public Object getDeleteFileResponseBody(String url, String fileName, String filePath) {
    	HttpPost httpPost = new HttpPost(url);
    	HttpClient httpClient = new DefaultHttpClient();
    	MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
		reqEntity.addTextBody("fileName", fileName);
    	reqEntity.addTextBody("filePath", filePath);
    	// 生成 HTTP 实体
    	HttpEntity httpEntity = reqEntity.build();
    	httpPost.setEntity(httpEntity);
    	Object result = execRequestAndGetResponse(url, httpPost, httpClient);
    	log.info("文件删除 "+ result+ " true表示成功！");
    	return result;
    }
    
    /**
     * 
     * @Description 执行请求，返回执行结果
     * @param url 待请求的URL
     * @return JSON 相应对象
     */
	private String execRequestAndGetResponse(String url, HttpPost httpPost, HttpClient httpClient) {
		// 执行请求
        String resultStr = null;
        try {
            long beginning = System.currentTimeMillis();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            long end = System.currentTimeMillis();
            log.info("url:" + url + ",用时:" +(end-beginning)+"ms");
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                resultStr = EntityUtils.toString(httpResponse.getEntity());
                log.info("httpResponse: "+resultStr);
            } else {
                log.error("返回状态码不是200: 状态码:" + httpResponse.getStatusLine().getStatusCode() + ",URL:" + url);
            }
        } catch (IOException e) {
            log.error("请求错误，请求URL：" + url);
            e.printStackTrace();
            httpPost.abort();
        }finally {
            httpPost.releaseConnection();
        }
		return resultStr;
	}
	
}

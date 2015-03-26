package com.yazuo.superwifi.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.superwifi.exception.BussinessException;

public class UploadFileUtil {
	
    /**
     * 前端上传图片用的
     * @param file
     * @param picDfsServer
     * @param dfsTrackerHttpPort
     * @param picDfsPort
     * @return
     */
    public static JsonResult upLoadFiles(MultipartFile file,String picDfsServer,String dfsTrackerHttpPort,Integer picDfsPort){
        JsonResult resultInfo = new JsonResult(false, "上传失败");

        String fileid = null;
        String iconUrl = "http://"+ picDfsServer +":" + dfsTrackerHttpPort + "/";
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();

        try {
            ClientGlobal.init(System.getProperty("superwifi.root") + File.separatorChar + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar + "fdfs_yazuo.conf");
            TrackerGroup tg = new TrackerGroup(new InetSocketAddress[] { new InetSocketAddress(picDfsServer, picDfsPort) });
            TrackerClient tc = new TrackerClient(tg);

            TrackerServer ts = tc.getConnection();
            if (ts == null)
            {
                return new JsonResult(false, "连接文件服务器失败");
            }

            StorageServer ss = tc.getStoreStorage(ts);
            if (ss == null)
            {
                return new JsonResult(false, "连接文件服务器失败");
            }

            StorageClient1 sc1 = new StorageClient1(ts, ss);

            org.csource.common.NameValuePair[] meta_list = null;
            byte[] item = file.getBytes();
            fileid = sc1.upload_file1(item, extension.substring(1), meta_list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        resultInfo.setFlag(true);
        resultInfo.setMessage("上传成功");
        resultInfo.putData("imageName", fileid);
        resultInfo.putData("imageAddr", iconUrl + fileid);

        return resultInfo;
    }
    /**
     * 下载Excel时候先上传打包到服务器
     * @param inStream
     * @param uploadFileName
     * @param fileLength
     * @param picDfsServer
     * @param dfsTrackerHttpPort
     * @param picDfsPort
     * @return
     * @throws Exception
     */
    public static JsonResult upLoadFiles(InputStream inStream, String uploadFileName, long fileLength,String picDfsServer,String dfsTrackerHttpPort,Integer picDfsPort)throws Exception{
        JsonResult resultInfo = new JsonResult(false, "上传失败");
        byte[] fileBuff = getFileBuffer(inStream, fileLength);
        String fileid = "";  
        String fileExtName = "";  
        if (uploadFileName.contains(".")) {  
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);  
        } 
        String iconUrl = "http://"+ picDfsServer +":" + dfsTrackerHttpPort + "/";

        try {
            ClientGlobal.init(System.getProperty("superwifi.root") + File.separatorChar + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar + "fdfs_yazuo.conf");
            TrackerGroup tg = new TrackerGroup(new InetSocketAddress[] { new InetSocketAddress(picDfsServer, picDfsPort) });
            TrackerClient tc = new TrackerClient(tg);

            TrackerServer ts = tc.getConnection();
            if (ts == null)
            {
                return new JsonResult(false, "连接文件服务器失败");
            }

            StorageServer ss = tc.getStoreStorage(ts);
            if (ss == null)
            {
                return new JsonResult(false, "连接文件服务器失败");
            }

            StorageClient1 sc1 = new StorageClient1(ts, ss);
          //设置元信息  
            NameValuePair[] metaList = new NameValuePair[3];  
            metaList[0] = new NameValuePair("fileName", uploadFileName);  
            metaList[1] = new NameValuePair("fileExtName", fileExtName);  
            metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength)); 
          //上传文件  
           fileid = sc1.upload_file1(fileBuff, fileExtName, metaList);  
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultInfo.setFlag(true);
        resultInfo.setMessage("文件打包成功");
        resultInfo.putData("imageName", fileid);
        resultInfo.putData("imageAddr", iconUrl + fileid);

        return resultInfo;
    }
    
    private static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {  
        
        byte[] buffer = new byte[256 * 1024];  
        byte[] fileBuffer = new byte[(int) fileLength];  
      
        int count = 0;  
        int length = 0;  
      
        while((length = inStream.read(buffer)) != -1){  
            for (int i = 0; i < length; ++i)  
            {  
                fileBuffer[count + i] = buffer[i];  
            }  
            count += length;  
        }  
        return fileBuffer;  
    } 
    
    /**
     * 前端上传图片用的
     * @param file
     * @param picDfsServer
     * @param dfsTrackerHttpPort
     * @param picDfsPort
     * @return
     */
    public static String upLoadFiles(byte[] item,String extension,String picDfsServer,String dfsTrackerHttpPort,Integer picDfsPort){

        String fileid = null;
        String iconUrl = "http://"+ picDfsServer +":" + dfsTrackerHttpPort + "/";

        try {
            ClientGlobal.init(System.getProperty("superwifi.root") + File.separatorChar + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar + "fdfs_yazuo.conf");
            TrackerGroup tg = new TrackerGroup(new InetSocketAddress[] { new InetSocketAddress(picDfsServer, picDfsPort) });
            TrackerClient tc = new TrackerClient(tg);

            TrackerServer ts = tc.getConnection();
            if (ts == null)
            {
                throw new BussinessException("连接文件服务器失败");
            }

            StorageServer ss = tc.getStoreStorage(ts);
            if (ss == null)
            {
                throw new BussinessException("连接文件服务器失败");
            }

            StorageClient1 sc1 = new StorageClient1(ts, ss);

            org.csource.common.NameValuePair[] meta_list = null;
            fileid = sc1.upload_file1(item, extension.substring(1), meta_list);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return iconUrl+fileid;
    }
}

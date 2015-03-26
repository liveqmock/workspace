package com.baidu.ueditor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.hunter.ImageHunter;
import com.yazuo.superwifi.exception.ExceptionResolver;
import com.yazuo.superwifi.util.PropertyUtil;
import com.yazuo.superwifi.util.UploadFileUtil;

public class ActionEnter {
	
	private HttpServletRequest request = null;
	
	private String rootPath = null;
	private String contextPath = null;
	
	private String actionType = null;
	
	private ConfigManager configManager = null;
    private String picDfsServer=null;
    private Integer picDfsPort=null;
    private String dfsTrackerHttpPort=null;
	
	private static final Logger log = Logger.getLogger(ExceptionResolver.class);

	public ActionEnter ( HttpServletRequest request, String rootPath ) {
	    
	    try
        {
            this.picDfsServer=PropertyUtil.getInstance().getProperty("fdfs_yazuo.conf","dfs.server.ip");
            this.picDfsPort=Integer.valueOf(PropertyUtil.getInstance().getProperty("fdfs_yazuo.conf","dfs.server.port"));
            this.dfsTrackerHttpPort=PropertyUtil.getInstance().getProperty("fdfs_yazuo.conf","dfs.tracker.http.port");
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            log.error(e);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            log.error(e);
        }
		
		this.request = request;
		this.rootPath = rootPath;
		this.actionType = request.getParameter( "action" );
		this.contextPath = request.getContextPath();
		this.configManager = ConfigManager.getInstance( this.rootPath, this.contextPath, request.getRequestURI() );
		
	}
	
	public String exec () {
		
		String callbackName = this.request.getParameter("callback");
		
		if ( callbackName != null ) {

			if ( !validCallbackName( callbackName ) ) {
				return new BaseState( false, AppInfo.ILLEGAL ).toJSONString();
			}
			
			return callbackName+"("+this.invoke()+");";
			
		} else {
			return this.invoke();
		}

	}
	
	public String invoke() {
		
		if ( actionType == null || !ActionMap.mapping.containsKey( actionType ) ) {
			return new BaseState( false, AppInfo.INVALID_ACTION ).toJSONString();
		}
		
		if ( this.configManager == null || !this.configManager.valid() ) {
			return new BaseState( false, AppInfo.CONFIG_ERROR ).toJSONString();
		}
		
		State state = null;
		
		int actionCode = ActionMap.getType( this.actionType );
		
		Map<String, Object> conf = null;
		
		switch ( actionCode ) {
		
			case ActionMap.CONFIG:
				return this.configManager.getAllConfig().toString();
				
			case ActionMap.UPLOAD_IMAGE:
			case ActionMap.UPLOAD_SCRAWL:
			case ActionMap.UPLOAD_VIDEO:
			case ActionMap.UPLOAD_FILE:
//				conf = this.configManager.getConfig( actionCode );
//				state = new Uploader( request, conf ).doExec();
				
				try
                {
				    state=saveFile();
                }
                catch (Exception e)
                {
                    log.error("捕获到系统异常",e);
                    e.printStackTrace();
                    break;
                }
				break;
				
			case ActionMap.CATCH_IMAGE:
				conf = configManager.getConfig( actionCode );
				String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
				state = new ImageHunter( conf ).capture( list );
				break;
				
			case ActionMap.LIST_IMAGE:
			case ActionMap.LIST_FILE:
				conf = configManager.getConfig( actionCode );
				int start = this.getStartIndex();
				state = new FileManager( conf ).listFile( start );
				break;
				
		}
		log.info(state.toJSONString());
		return state.toJSONString();
		
	}
	
	public int getStartIndex () {
		
		String start = this.request.getParameter( "start" );
		
		try {
			return Integer.parseInt( start );
		} catch ( Exception e ) {
			return 0;
		}
		
	}
	
	/**
	 * callback参数验证
	 */
	public boolean validCallbackName ( String name ) {
		
		if ( name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" ) ) {
			return true;
		}
		
		return false;
		
	}
	
    private State saveFile()
        throws Exception
    {

        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if (isAjaxUpload)
        {
            upload.setHeaderEncoding("UTF-8");
        }

        FileItemStream fileStream = null;
        FileItemIterator iterator = upload.getItemIterator(request);

        while (iterator.hasNext())
        {
            fileStream = iterator.next();

            if (!fileStream.isFormField()) break;
            fileStream = null;
        }
        InputStream is = fileStream.openStream();

        byte[] item = input2byte(is);
        String extension = ".jpg";
        String id = UploadFileUtil.upLoadFiles(item, extension, picDfsServer, dfsTrackerHttpPort, picDfsPort);
        State state = new BaseState(true);
        state.putInfo("size", item.length);
        state.putInfo("title", id);
        state.putInfo("url", id);
        state.putInfo("type", ".jpg");
        state.putInfo("original", "original");
        return state;
    }

    public static final byte[] input2byte(InputStream inStream)
        throws IOException
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0)
        {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
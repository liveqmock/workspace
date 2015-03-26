package com.yazuo.weixin.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yazuo.weixin.member.service.ResourceManagerService;

/**
 * 自定义标签
 * */
@SuppressWarnings("serial")
public class CustomTag extends BodyTagSupport {

	 private static final Log log = LogFactory.getLog(CustomTag.class);

     // 请求路径
     private String url; // 前台页面传入的url值
     private Integer brandId; // 商户id

     // 警告信息
     private String warnning;

     public void setUrl(String url) {
             this.url = url;
     }

     public String getUrl() {
             return url;
     }

     public void setWarnning(String warnning) {
             this.warnning = warnning;
     }

     public String getWarnning() {
             return warnning;
     }

     public String getId() {
             return id;
     }

     public void setId(String id) {
             this.id = id;
     }

     public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public int doStartTag() throws JspException {
             return EVAL_BODY_BUFFERED;
     }

     public int doEndTag() throws JspException {
    	 ServletContext app = this.pageContext.getServletContext();
    	 ResourceManagerService resourceService = (ResourceManagerService) WebApplicationContextUtils.getWebApplicationContext(app)
    			 .getBean("resourceManagerServiceImpl");  
//    	 ResourceManagerService resourceService = (ResourceManagerService)WebApplicationContextUtils.getRequiredWebApplicationContext(app).getBean("resourceManagerService");
             List<Map<String, Object>> resources = resourceService.getResourceOfBrandId(this.brandId, null);
             boolean msgenable = true;
             for (Map<String, Object> map : resources) {
            	 String resourceUrl = map.get("resource_url")+"";
            	 if (StringUtil.isNullOrEmpty(resourceUrl)) continue;
                 if (this.getUrl().indexOf(resourceUrl) > -1 || resourceUrl.indexOf(this.getUrl()) > -1) {
                	  BodyContent bodycontent = getBodyContent();  
                      JspWriter out = bodycontent.getEnclosingWriter();  
                      if(bodycontent != null) {  
                         try {
							out.print(bodycontent.getString());
						} catch (IOException e) {
							log.info("异常信息："+ e.getMessage());
							e.printStackTrace();
						}  
                      }  
                     msgenable = false;
                     break;
                 }
             }
             if (msgenable && this.getWarnning() != null){
            	  BodyContent bodycontent = getBodyContent();  
                  JspWriter out = bodycontent.getEnclosingWriter();  
                  if(bodycontent != null) {  
                     try {
						out.print(this.getWarnning());
					} catch (IOException e) {
						log.info("异常信息："+ e.getMessage());
						e.printStackTrace();
					}  
                  }  
             }
         this.release();
         return EVAL_PAGE;
     }

     /**
      * Release all allocated resources
      */
     public void release() {
         super.release();
         this.setId(null);
         this.setUrl(null);
         this.setWarnning(null);
     }
}

/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2015-03-30 09:53:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.sf.json.JSONObject;
import com.yazuo.erp.base.Constant;
import net.sf.json.JSONArray;
import com.yazuo.erp.system.vo.SysUserVO;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"utf-8\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("    <title>雅座ERP</title>\r\n");
      out.write("    <meta name=\"description\" content=\"\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("    ");

        String path = request.getContextPath();
        int port = request.getServerPort();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
        if(port == 80){
            basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
        }
    
      out.write("\r\n");
      out.write("\r\n");
      out.write("    <!-- Place favicon.ico and apple-touch-icon(s) in the root directory -->\r\n");
      out.write("    <script>\r\n");
      out.write("        var VERSION=\"9.0.0.0.0\"; //版本号设置规则:all.asset.module.page.widget\r\n");
      out.write("        var PUBLISH_MODEL = \"development\";\r\n");
      out.write("        var BASE_PATH=\"");
      out.print(basePath);
      out.write("\";  //web根目录地址\r\n");
      out.write("        var WIDGET_BASE_PATH=\"");
      out.print(basePath);
      out.write("portal/origin/widget/\";  //widget根目录地址\r\n");
      out.write("        (function () {\r\n");
      out.write("            var versions = VERSION.split(\".\");\r\n");
      out.write("            var searchParams = location.search.slice(1),\r\n");
      out.write("                tempParam;\r\n");
      out.write("            searchParams = searchParams.split(\"&\");\r\n");
      out.write("            for (var i = 0; i < searchParams.length; i++) {\r\n");
      out.write("                tempParam = searchParams[i].split(\"=\");\r\n");
      out.write("                if (tempParam[0] === \"model\" && tempParam[1]) {\r\n");
      out.write("                    PUBLISH_MODEL = tempParam[1];\r\n");
      out.write("                    break;\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("            if (PUBLISH_MODEL === \"development\") {\r\n");
      out.write("                document.write('<link rel=\"stylesheet\" href=\"portal/origin/asset/base.css?v=' + versions[0] + '.' + versions[1] + '\">');\r\n");
      out.write("                document.write('<link rel=\"stylesheet\" href=\"portal/origin/widget/widget.css?v=' + versions[0] + '.' + versions[4] + '\">');\r\n");
      out.write("                document.write('<link rel=\"stylesheet\" href=\"portal/origin/asset/common.css?v=' + versions[0] + '.' + versions[1] + '\">');\r\n");
      out.write("                document.write('<link rel=\"stylesheet\" href=\"portal/origin/asset/index.css?v=' + versions[0] + '.' + versions[1] + '\">');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/underscore/underscore.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/underscore/underscore.string.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/jquery-1.11.1.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/avalon/avalon.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/avalon-ext.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/boot.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("            } else if (PUBLISH_MODEL === \"product\") {\r\n");
      out.write("                document.write('<link rel=\"stylesheet\" href=\"portal/origin/dist/asset.css?v=' + versions[0] + '.' + versions[1] + '\">');\r\n");
      out.write("                document.write('<link rel=\"stylesheet\" href=\"portal/origin/dist/widget.css?v=' + versions[0] + '.' + versions[4] + '\">');\r\n");
      out.write("                document.write('<link rel=\"stylesheet\" href=\"portal/origin/dist/page.css?v=' + versions[0] + '.' + versions[3] + '\">');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/underscore/underscore-min.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/underscore/underscore.string.min.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/jquery-1.11.1.min.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/asset/third/avalon/avalon.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/asset/avalon-ext.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/asset/boot.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/widget.js?v=' + versions[0] + '.' + versions[4] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/asset.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/asset/util.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/asset/common.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/module.js?v=' + versions[0] + '.' + versions[2] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/page.js?v=' + versions[0] + '.' + versions[3] + '\"><\\/script>');\r\n");
      out.write("                document.write('<script src=\"portal/origin/dist/asset/index.js?v=' + versions[0] + '.' + versions[1] + '\"><\\/script>');\r\n");
      out.write("            }\r\n");
      out.write("        }());\r\n");
      out.write("    </script>\r\n");
      out.write("    <script>\r\n");
      out.write("        ");

            SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
            JSONObject jsonSessionUser = sessionUser==null? null: JSONObject.fromObject(sessionUser);
            JSONArray privilege = sessionUser==null? null: JSONArray.fromObject(sessionUser.getListPrivilege());
        
      out.write("\r\n");
      out.write("        erp.setAppData('permission', ");
      out.print(privilege);
      out.write(");   //用户权限\r\n");
      out.write("        erp.setAppData('user', ");
      out.print(jsonSessionUser);
      out.write(");   //用户\r\n");
      out.write("    </script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div class=\"app\" ms-controller=\"app\">\r\n");
      out.write("        <div class=\"app-hd\">\r\n");
      out.write("            <div class=\"app-inner fn-clear\">\r\n");
      out.write("                <div class=\"app-inner-l fn-left\">\r\n");
      out.write("                    <a class=\"logo-l\" href=\"");
      out.print(basePath);
      out.write("\">雅座ERP</a>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"app-inner-r\">\r\n");
      out.write("                    <h1 class=\"page-title\">{{pageTitle}}</h1>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"app-bd\">\r\n");
      out.write("            <div class=\"app-inner\">\r\n");
      out.write("                <div class=\"app-inner-l fn-left\">\r\n");
      out.write("                    <div class=\"app-inner-l-inner\">\r\n");
      out.write("                        <div class=\"user-widget\" ms-module=\"userwidget\"></div>\r\n");
      out.write("                        <div class=\"page-nav-wrapper\"></div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <span class=\"page-nav-visible-h state-shown iconfont\" ms-click=\"clickNavVisibleHandler\">&#xe624;</span>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"app-inner-r\" ms-css-min-height=\"appBdMinHeight\">\r\n");
      out.write("                    <div id=\"page-wrapper\">\r\n");
      out.write("\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <!--<a href=\"#/video\" class=\"test-page-nav\">video</a>\r\n");
      out.write("                    <a href=\"#/video/test\" class=\"test-page-nav\" phref=\"#/video\">test</a>-->\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("    </div>\r\n");
      out.write("    <!-- 嵌入插件获得本机MAC地址 -->\r\n");
      out.write("    <embed type=\"application/npsoftpos\" width=0 height=0 id=\"softPos\"/>\r\n");
      out.write("    <script>\r\n");
      out.write("        require([\"index\",\"ready!\"],function(index){\r\n");
      out.write("            index.$init();\r\n");
      out.write("        });\r\n");
      out.write("    </script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
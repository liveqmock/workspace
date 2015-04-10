package org.apache.jsp.WEB_002dINF.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


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

      out.write("<!DOCTYPE html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<body>\r\n");
      out.write("\t<h1>storm统计</h1>\t\r\n");
      out.write("\r\n");
      out.write("  <div id=\"id\" hidden=\"true\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${message}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</div>\r\n");
      out.write("  统计访问次数: <div id=\"id1\"></div>\r\n");
      out.write("  \r\n");
      out.write("    </br>\r\n");
      out.write("  统计登录次数: <div id=\"id2\"></div>\r\n");
      out.write("  \r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<script>\r\n");
      out.write(" var map = document.getElementById(\"id\").innerHTML;\r\n");
      out.write(" map = eval('(' + map + ')'); //由JSON字符串转换为JSON对象\r\n");
      out.write(" //map = map.parseJSON(); //由JSON字符串转换为JSON对象\r\n");
      out.write(" //alert(map);\r\n");
      out.write(" \r\n");
      out.write(" parse(map.key1, \"id1\");\r\n");
      out.write(" parse(map.key2, \"id2\");\r\n");
      out.write("\r\n");
      out.write(" function parse(array, id){\r\n");
      out.write("\t for(var i=0;i<array.length;i++){\r\n");
      out.write("\t\t  //document.getElementById(id).innerHTML += (array[i]+'</br>')\r\n");
      out.write("\t\t  document.getElementById(id).innerHTML += (array[i]+'</br>')\r\n");
      out.write("\t\t} \r\n");
      out.write(" }\r\n");
      out.write("\r\n");
      out.write("</script>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

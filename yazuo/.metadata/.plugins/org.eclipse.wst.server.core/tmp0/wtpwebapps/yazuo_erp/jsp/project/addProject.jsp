<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
    <%@ include file="/common/meta.jsp"%>
    <link href="<%=basePath%>css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
    <script src="<%=basePath%>js/jquery.pagination.js"></script>
</head>
<body class="page-add-project">
    <div class="page">
        <div class="page-hd">
            <h3 class="page-title"><a href="<%=basePath%>erp/project/toProjectItems.do">案例库</a></h3>
        </div>
        <div class="page-bd">
            <div class="page-bd-inner">
                <div class="filter-box">
                    	活动ID <input type="text" class="query-input-id text-field" value="${queryId }" placeholder="输入活动ID" />&nbsp;&nbsp;
                    活动名称 <input type="text" class="query-input-name text-field" value="${queryName }" placeholder="输入活动名称" />&nbsp;&nbsp;
                     
                     <button type="button" class="query-btn btn-link-1">查询</button>
                </div>
                <div class="activity-list-summary">共&nbsp;<span class="num">${dbActiveItems.total}</span>&nbsp;个活动</div>
                <div class="activity-list-wrapper">
                    <c:choose>
                        <c:when test="${dbActiveItems.total eq 0}">
                            <div>没有找到相关活动</div>
                        </c:when>
                        <c:otherwise>
                            <table class="activity-list" cellspacing="0" cellpadding="0">
                                <thead>
                                    <tr>
                                        <th>活动ID</th>
                                        <th>活动名称</th>
                                        <th>活动类型</th>
                                        <th>商户</th>
                                        <th>活动开始</th>
                                        <th>活动结束</th>
                                        <th>ID</th>
                                        <th>案例状态</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${dbActiveItems.rows}" var="active" varStatus="status">
                                        <tr class="${status.count%2==0?'even':'odd'}">
                                        	<td>${active.activeId }</td>
                                            <td>${active.activeName }</td>
                                            <td>${active.typeName }</td>
                                            <td>${active.merchantName }</td>
                                            <td>${active.activeBegin }</td>
                                            <td>${active.activeEnd }</td>
                                            <td>${active.activeId }</td>
                                            <c:choose>
                                                <c:when  test="${active.hasProject && active.hasProject eq true}">
                                                    <td>已添加</td>
                                                    <td><a href="<%=basePath%>erp/project/toSpeficAdd.do?activeId=${active.activeId }&typeId=${active.activeType }&toView=true">查看</a></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>未添加</td>
                                                    <td><a href="<%=basePath%>erp/project/toSpeficAdd.do?activeId=${active.activeId }&typeId=${active.activeType }" class="add-project-l">添加</a></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="activity-list-pagination"></div>
            </div>
        </div>
    </div>
    <script>
        jQuery(function($){
            var paginationEl=$('.activity-list-pagination'),
                    filterBoxEl=$('.filter-box');
            var currentPage = ${page == null? 1: page},
                    totalSize = ${dbActiveItems.total == null? 0: dbActiveItems.total},
                    pageSize=10;

            //分页初始化
            paginationEl.pagination(totalSize, {
                prev_text:"上一页",
                next_text:"下一页",
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:currentPage-1,
                callback: function(currentPageNumber){
                    location.href="<%=basePath%>erp/project/searchProject.do?page="+(currentPageNumber+1)+"&pageSize="+pageSize+"&queryId=${queryId}&queryName=${queryName}";
                },
                items_per_page:pageSize //每页显示1项
            });
            //查询活动
            filterBoxEl.on('click','.query-btn',function(evt){
                var queryInputElId=$('.query-input-id',filterBoxEl),
                    queryId= _.str.trim(queryInputElId.val());
                	if(queryId!=""&&!isInt(queryId)){
                		alert("活动ID必须为整数");
                		return;
                	}
                var queryInputElName=$('.query-input-name',filterBoxEl),
                    queryName= _.str.trim(queryInputElName.val());
                window.location="<%=basePath%>erp/project/searchProject.do?queryId="+queryId+"&queryName="+queryName;
            });
            function isInt(value){ 
                return /^[0-9]+$/.test(value); 
            }
        });
    </script>
</body>
</html>


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
<body class="page-project-items">
    <div class="page">
        <div class="page-hd">
            <h3 class="page-title"><a href="<%=basePath%>erp/project/toProjectItems.do">案例库</a></h3>
        </div>
        <div class="page-bd">
            <div class="page-bd-tbar"><!-- <a href="#" onclick="window.history.back()" class="btn-link-1"><i class="icon-left-arrow">&#x3432;</i>&nbsp;返回</a> -->
                &nbsp;&nbsp;<a href="#" class="project-manage-l btn-link-1">案例管理</a>
                &nbsp;&nbsp;<a href="<%=basePath%>erp/project/searchProject.do" class="add-project-l btn-link-1">添加案例</a>
            </div>
            <div class="page-bd-inner">
                <div class="filter-box">
                    <form method="post" id="formId" action = "<%=basePath%>erp/project/toProjectItems.do">
                        <label>营销活动类型：</label><select name="activeType" class="yx-type-list">
                        	<option value="">全部</option>
                            <c:forEach items="${dbActiveTypes}" var="type">
                                <c:choose>
	                              <c:when test="${type.type_id eq activeType }">
                               		 <option value="${type.type_id}" selected="selected">${type.type_name}</option>
	                              </c:when>
	                              <c:otherwise>
                               		 <option value="${type.type_id}">${type.type_name}</option>
	                              </c:otherwise>
	                             </c:choose>
                            </c:forEach>
                        </select>&nbsp;&nbsp;&nbsp;&nbsp;<label>活动开始时间：</label>
                        <input type="text" class="start-date date-field text-field" name="activeBegin" readOnly value="${activeBegin }"/>&nbsp;-&nbsp;
                        <input type="text" class="end-date date-field text-field"  name="activeEnd" readOnly value="${activeEnd }"/>&nbsp;&nbsp;
                        <input type="text" class="query-input text-field" name="activeName" value="${activeName }" placeholder="输入活动名称或商户" />&nbsp;&nbsp;
                        <button type="submit" class="query-btn btn-link-1">查询</button>
                        <button type="button" id="form_reset" class="query-btn btn-link-1">清空</button>
                    </form>
                </div>
                <div class="tag-filter-wrapper">
	                <div class="cate-box fn-clear" tagname="cityType">
	                    <label class="fn-left">城市：</label>
	                    <div class="cate-list-wrapper">
	                        <ul class="cate-list fn-clear">
	                             <c:forEach items="${mapDictionary.listCity}" var="object">
	                                <li class="cate-item fn-left">
	                                    <a href="<%=basePath%>erp/project/toProjectItems.do?cityType=${object.dictionary_key}" 
	                                       class="label_href" citytype="${object.dictionary_key }">${object.dictionary_value }</a>
	                                </li>
	                            </c:forEach>
	                        </ul>
	                    </div>
	                </div>
	                <div class="cate-box fn-clear" tagname="cateType">
	                    <label class="fn-left">业态：</label>
	                    <div class="cate-list-wrapper">
	                        <ul class="cate-list fn-clear">
	                             <c:forEach items="${mapDictionary.listCate}" var="object">
	                                <li class="cate-item fn-left">
	                                    <a href="<%=basePath%>erp/project/toProjectItems.do?cateType=${object.dictionary_key}" 
	                                       class="label_href " catetype="${object.dictionary_key }">${object.dictionary_value }</a>
	                                </li>
	                            </c:forEach>
	                        </ul>
	                    </div>
	                </div>
	                <div class="cate-box fn-clear" tagname="catType">
	                    <label class="fn-left">分类：</label>
	                    <div class="cate-list-wrapper">
	                        <ul class="cate-list fn-clear">
	                             <c:forEach items="${mapDictionary.listCat}" var="object">
	                                <li class="cate-item fn-left">
	                                    <a href="<%=basePath%>erp/project/toProjectItems.do?catType=${object.dictionary_key}" 
	                                       class="label_href " cattype="${object.dictionary_key }">${object.dictionary_value }</a>
	                                </li>
	                            </c:forEach>
	                        </ul>
	                    </div>
	                </div>
	                <div class="cate-box fn-clear" tagname="avgPriceType">
	                    <label class="fn-left">人均：</label>
	                    <div class="cate-list-wrapper">
	                        <ul class="cate-list fn-clear">
	                             <c:forEach items="${mapDictionary.listAvgPrice}" var="object">
	                                <li class="cate-item fn-left">
	                                    <a href="<%=basePath%>erp/project/toProjectItems.do?avgPriceType=${object.dictionary_key}" 
	                                       class="label_href " avgpricetype="${object.dictionary_key }">${object.dictionary_value }元</a>
	                                </li>
	                            </c:forEach>
	                        </ul>
	                    </div>
	                </div>
	                <div class="cate-box fn-clear" tagname="promoteType">
	                    <label class="fn-left">优惠：</label>
	                    <div class="cate-list-wrapper">
	                        <ul class="cate-list fn-clear">
	                             <c:forEach items="${mapDictionary.listPromote}" var="object">
	                                <li class="cate-item fn-left">
	                                    <a href="<%=basePath%>erp/project/toProjectItems.do?promoteType=${object.dictionary_key}" 
	                                       class="label_href " promotetype="${object.dictionary_key }">${object.dictionary_value }</a>
	                                </li>
	                            </c:forEach>
	                        </ul>
	                    </div>
	                </div>
	                <div class="cate-box fn-clear" tagname="labelId">
                        <label class="fn-left">其他：</label>
                        <div class="cate-list-wrapper">
                            <ul class="cate-list fn-clear">
                                <c:forEach items="${dbLabels}" var="label">
                                    <li class="cate-item fn-left">
                                        <a href="<%=basePath%>erp/project/toProjectItems.do?labelId=${label.labelId }" 
                                        labelid=${label.labelId } class="label_href ">${label.lableName }</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="project-list-summary">共&nbsp;<span class="num">${dbProjectItems.total}</span>&nbsp;个案例</div>
                <div class="project-list-wrapper">
                    <c:choose>
                        <c:when test="${dbProjectItems.total eq 0}">
                            <div>没有找到相关案例</div>
                        </c:when>
                        <c:otherwise>
                            <ul class="project-list">
                                <c:forEach items="${dbProjectItems.rows}" var="project" varStatus="status">
                                    <li class="project-item ${status.count%2==0?'even':'odd'}">
                                        <div class="project-title">${project.active.activeName}&nbsp;</div>
                                        <div class="project-title">${project.projectName}</div>
                                        <div class="project-desc">
<a href="<%=basePath%>erp/project/toSpeficAdd.do?activeId=${project.active.activeId }&typeId=${project.active.activeType }&toView=true">${project.projectComments}</a></div>
                                        <div class="project-bbar" activetype="${project.active.activeType}">  <span class="active-type-name"></span>&nbsp;
<span class="label-list"><c:forEach items="${project.listCity}" var="map">${map.dictionary_value }&nbsp;&nbsp;</c:forEach></span>
<span class="label-list"><c:forEach items="${project.listCate}" var="map">${map.dictionary_value }&nbsp;&nbsp;</c:forEach></span>
<span class="label-list"><c:forEach items="${project.listCat}" var="map">${map.dictionary_value }&nbsp;&nbsp;</c:forEach></span>
<span class="label-list"><c:forEach items="${project.listAvgPrice}" var="map">${map.dictionary_value }元&nbsp;&nbsp;</c:forEach></span>
<span class="label-list"><c:forEach items="${project.listPromote}" var="map">${map.dictionary_value }&nbsp;&nbsp;</c:forEach></span>
										</div>
										 <div class="project-bbar"><span class="label-list">
                                            <c:forEach items="${project.labels}" var="addedlabel" varStatus="status">
                                                <a href="<%=basePath%>erp/project/toProjectItems.do?labelId=${addedlabel.labelId }">${addedlabel.lableName }</a><c:if test="${fn:length(project.labels)-1 != status.index}">&nbsp;
                                                </c:if><!-- do not show the last separator when text come to the end-->
                                            </c:forEach></span>
                                        </div>
                                        <div class="project-action">
                                            <a class="view-l btn-link-2" href="<%=basePath%>erp/project/toSpeficAdd.do?activeId=${project.active.activeId }&typeId=${project.active.activeType }&toView=true">查看</a>
                                            <a class="edit-l fn-hide btn-link-2" href="<%=basePath%>erp/project/toSpeficAdd.do?activeId=${project.active.activeId }&typeId=${project.active.activeType }&projectId=${project.projectId}">修改</a>
                                            <a class="delete-l fn-hide btn-link-2" href="javascript:;" projectid="${project.projectId}">删除</a>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="project-list-pagination"></div>
            </div>
        </div>
    </div>
    <div class="project-manage-dialog fn-hide">
        <div class="f-field fn-clear">
            <label class="fn-left">请输入案例管理密码：</label>
            <div class="fn-left"><input type="password" name="pwd" class="pwd text-field" /></div>
        </div>
        <div class="f-actions">
            <button type="button" class="f-submit">确&nbsp;定</button>&nbsp;&nbsp;<button type="button" class="f-cancel">取&nbsp;消</button>
        </div>
    </div>
    <script>
        jQuery(function($){
            var paginationEl=$('.project-list-pagination'),
            	yxTypeListEl = $('.yx-type-list'),
                projectManageLinkEl=$('.project-manage-l'),
                projectManageDialogEl=$('.project-manage-dialog'),
                projectListEl=$('.project-list'),
                startDateInputEl=$('.start-date'),
                endDateInputEl=$('.end-date');
                formEl=$('#formId');
            var currentPage = ${page == null? 1: page},
                totalSize = ${dbProjectItems.total == null? 0: dbProjectItems.total},
                pageSize=10;
                
            //高亮对应的tag
            var uri=new Uri(location.href);
            $('.cate-box').each(function () {
                var boxEl = $(this),
                    tagName = boxEl.attr('tagname'),
                    cateListEl=$('.cate-list', boxEl),
                    tagValue = uri.getQueryParamValue(tagName)||0;
                $('.cate-item a',cateListEl).removeClass('state-active').filter('[' + tagName.toLowerCase() + '="'+tagValue+'"]').addClass('state-active');
            });
            // reset active type name
            $('.project-item', projectListEl).each(function () {
            	var activeType = $('.project-bbar', this).attr('activetype');
            	$('.active-type-name',this).text($('option[value="' + activeType + '"]',yxTypeListEl).text());
            });
            $('.tag-filter-wrapper').on('click', '.cate-item a', function () {
                var meEl = $(this),
                    cateBoxEl = meEl.closest('.cate-box'),
                    extQuery = [];
                if (!meEl.hasClass('state-active')) {
                     $('.cate-box').not(cateBoxEl).each(function () {
                        var boxEl = $(this),
                            tagName = boxEl.attr('tagname'),
                            selectedLinkEl = $('.state-active', boxEl);
                        if (selectedLinkEl.length > 0) {
                            extQuery.push(tagName + '='+selectedLinkEl.attr(tagName.toLowerCase()));
                        }
                    });
                    //location.href = meEl.attr('href');
                    meEl.attr('href', meEl.attr('href') + '&' + extQuery.join('&'));
                } else {
                     $('.cate-box').not(cateBoxEl).each(function () {
                        var boxEl = $(this),
                            tagName = boxEl.attr('tagname'),
                            selectedLinkEl = $('.state-active', boxEl);
                        if (selectedLinkEl.length > 0) {
                            extQuery.push(tagName + '='+selectedLinkEl.attr(tagName.toLowerCase()));
                        }
                    });
                    location.href = location.href.split('?')[0] + '?' + extQuery.join('&');
                    return false;
                }
               
            });
            
            //初始化datepicker
            startDateInputEl.datepicker({
                dateFormat: "yy-mm-dd",
                onClose: function( selectedDate ) {
                    endDateInputEl.datepicker( "option", "minDate", selectedDate );
                }
            });
			//初始化隐藏 管理按钮
            $('.edit-l',projectListEl).hide();
            $('.delete-l',projectListEl).hide();
            $('.add-project-l').hide();
            endDateInputEl.datepicker({
                dateFormat: "yy-mm-dd",
                onClose: function( selectedDate ) {
                    startDateInputEl.datepicker( "option", "maxDate", selectedDate );
                }
            });
            //分页初始化
            paginationEl.pagination(totalSize, {
                prev_text:"上一页",
                next_text:"下一页",
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:currentPage-1,
                callback: function(currentPageNumber){
                	location.href = "<%=basePath%>erp/project/toProjectItems.do?page="+(currentPageNumber+1)+"&pageSize="+pageSize+"&activeName=${activeName}&activeType=${activeType}&activeBegin=${activeBegin}&activeEnd=${activeEnd}";                  
                },
                items_per_page:pageSize //每页显示1项
            });
            //案例管理弹框初始化
            projectManageDialogEl.dialog({
                modal: true,
                autoOpen:false,
                title:"案例管理密码",
                closeText:"&#10005;",
                width:500,
                height:300,
                close: function( event, ui ) {
                    //清空密码
                    $('.pwd',projectManageDialogEl).val("");
                }
            });
            //事件注册
            projectManageLinkEl.on('click',function(evt){
                if($(this).attr('href')=="#"){
                    projectManageDialogEl.dialog('open');
                    evt.preventDefault();
                }
            });
            projectManageDialogEl.on('click','.f-submit',function(evt){   //点击确定按钮
                var pwd= _.str.trim($('.pwd',projectManageDialogEl).val());
                if(pwd.length==0){
                    alert('请输入密码');
                    return;
                }
                $.ajax({
                    type: "POST",
                    url:'<%=basePath%>erp/video/checkPassword.do',
                    data:{
                        "pwd":pwd
                    },
                    dataType:"json",
                    success:function(data){
                        if(data.flag == 0){
                            alert("密码错误");
                        }else{
                            //show the delete button.
                            $('.edit-l',projectListEl).show();
                            $('.delete-l',projectListEl).show();
                            projectManageLinkEl.text("添加案例");
                            projectManageLinkEl.attr('href',"<%=basePath%>erp/project/searchProject.do");
                            projectManageDialogEl.dialog("close");
                        }
                    }
                });
            }).on('click','.f-cancel',function(evt){   //点击取消按钮
                projectManageDialogEl.dialog('close');
            });
            //删除事件注册
            projectListEl.on('click','.delete-l',function(evt){
                var meEl=$(this),
                        itemEl=meEl.closest('.project-item'),
                        projectId = meEl.attr('projectid');
                if(confirm("确定要删除本条案例么?")){
                    $.ajax({
                        type: "POST",
                        url:'<%=basePath%>erp/project/deleteProject.do',
                        data:{
                            "projectId": projectId
                        },
                        dataType:"json",
                        success:function(data){
                            if(data.flag == 0){
                                alert("删除错误");
                            }else{
                                itemEl.remove();
                            }
                        }
                    });
                }
            });
            //reset事件
            formEl.on("click", "#form_reset", function(){
            	$('.start-date').val("");
            	$('.end-date').val("");
            	$('.query-input').val("");
            	$('.yx-type-list').val("");
            });
            //新添加的逻辑，如果有用户session（输入过正确的密码），则为编辑状态
            var legalUser = '${legalUser}';
            if(legalUser&&legalUser=='1'){
                //show the delete button.
                $('.edit-l',projectListEl).show();
                $('.delete-l',projectListEl).show();
                projectManageLinkEl.text("添加案例");
                projectManageLinkEl.attr('href',"<%=basePath%>erp/project/searchProject.do");
            }
        });
    </script>
</body>
</html>


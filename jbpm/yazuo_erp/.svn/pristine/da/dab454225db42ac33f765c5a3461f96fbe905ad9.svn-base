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
    <!-- jqPagination styles -->
    <!--<link rel="stylesheet" href="<%=basePath%>css/jqpagination.css" />-->
    <script src="<%=basePath%>js/jquery.pagination.js"></script>
</head>
<body class="page-video-items">
    <div class="page">
        <div class="page-hd">
            <h3 class="page-title">雅座视频课件</h3>
        </div>
        <div class="page-bd">
            <div class="video-list-tbar page-bd-tbar">
                <a href="#" onclick="window.history.back()" class="btn-link-1 return-back"></a>&nbsp;&nbsp;
                <a href="#" class="video-manage-l btn-link-1">视频管理</a>
            </div>
            <div class="video-list-wrapper">
                <div class="video-list-cate fn-clear">
                    <label class="fn-left">视频分类标签：</label>
                    <div class="cate-list-wrapper fn-left">
                        <ul class="cate-list fn-clear">
                            <li class="cate-item fn-left">
                                <a href='<%=basePath%>erp/video/toVideoItems.do?videoCatId=0' videocatid="0">全部</a>
                            </li>
                            <c:forEach items="${videoCatItems}" var="videoCat">
                                <li class="cate-item fn-left">
                                    <a href='<%=basePath%>erp/video/toVideoItems.do?videoCatId=${videoCat.videoCatId }' videocatid="${videoCat.videoCatId }">${videoCat.catDesc }</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
	            <form method="post" id="formId" action = "<%=basePath%>erp/video/toVideoItems.do?videoCatId=0">
	           	    <div class="video-list-wrapper">
	               	 <div class="video-list-cate fn-clear">
	                	<label class="fn-left">视频搜索：</label>
	                    <div class="cate-list-wrapper fn-left">
	                    	<input type="text" class="text-field input-video-name" name="videoName" value="${videoName }"/> &nbsp; &nbsp;
	                    	<button type="submit" class="query-btn btn-link-1">查询</button>
	                    </div>
	                   </div>
	                </div>
                </form>
                <div class="video-list-summary">共&nbsp;<span class="num">${countVideo}</span>&nbsp;个视频课件</div>
                <ul class="video-list">
                    <c:forEach items="${resultMapPerPage.rows}" var="videoItem" varStatus="status">
                        <li class="video-item ${status.count%2==0?'even':'odd'}">
                            <div class="video-title">[${videoItem.catDesc==""?"无分类":videoItem.catDesc}]&nbsp;<a href="<%=basePath%>erp/video/toViewVideo.do?videoId=${videoItem.videoId}">${videoItem.videoName}</a></div>
                            <div class="video-desc">${videoItem.videoDesc}</div>
                            <div class="video-bbar">上传日期：${videoItem.uploadTime}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;讲师：${videoItem.presenter}</div>
                            <a class="delete-l btn-link-2" href="javascript:;" videoid="${videoItem.videoId}" videoname="${videoItem.videoName}">删除</a>
                        </li>
                    </c:forEach>
                </ul>
                <div class="video-list-pagination"></div>
            </div>
        </div>
    </div>
    <div class="video-manage-dialog fn-hide">
        <div class="f-field fn-clear">
            <label class="fn-left">请输入视频管理密码：</label>
            <div class="fn-left"><input type="password" name="pwd" class="pwd text-field" /></div>
        </div>
        <div class="f-actions">
            <button type="button" class="f-submit">确&nbsp;定</button>&nbsp;&nbsp;<button type="button" class="f-cancel">取&nbsp;消</button>
        </div>
    </div>
    <script>
        jQuery(function($){
            var paginationEl=$('.video-list-pagination'),
                videoManageLinkEl=$('.video-manage-l'),
                videoManageDialogEl=$('.video-manage-dialog'),
                videoListEl=$('.video-list');
            var currentPage = ${page},
                countVideo = ${countVideo},
                pageSize=10;
            //高亮对应的tag
            var cateListEl=$('.cate-list'),
                uri=new Uri(location.href),
                videoCatId=uri.getQueryParamValue('videoCatId')||0;
            $('.cate-item a',cateListEl).removeClass('state-active').filter('[videocatid="'+videoCatId+'"]').addClass('state-active');
            //分页初始化
            paginationEl.pagination(countVideo, {
                prev_text:"上一页",
                next_text:"下一页",
                num_edge_entries: 1, //边缘页数
                num_display_entries: 4, //主体页数
                current_page:currentPage-1,
                callback: function(currentPageNumber){
                    location.href="<%=basePath%>erp/video/toVideoItems.do?page="+(currentPageNumber+1)+"&pageSize="+pageSize+"&videoCatId=${videoCatId}&videoName=${videoName}";
                },
                items_per_page:pageSize //每页显示1项
            });
            //视频管理弹框初始化
            $('.return-back').hide();
            videoManageDialogEl.dialog({
                modal: true,
                autoOpen:false,
                title:"视频管理密码",
                closeText:"&#10005;",
                width:500,
                height:300,
                close: function( event, ui ) {
                    //清空密码
                    $('.pwd',videoManageDialogEl).val("");
                }
            });
            /**
            //事件注册
            videoManageLinkEl.on('click',function(evt){
                if($(this).attr('href')=="#"){
                    videoManageDialogEl.dialog('open');
                    evt.preventDefault();
                }
            });
            videoManageDialogEl.on('click','.f-submit',function(evt){   //点击确定按钮
                var pwd= _.str.trim($('.pwd',videoManageDialogEl).val());
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
                            $('.delete-l',videoListEl).show();
                            videoManageLinkEl.text("上传视频");
                            videoManageLinkEl.attr('href',"<%=basePath%>erp/video/toUploadVideo.do");
                            videoManageDialogEl.dialog("close");
                        }
                    }
                });
            }).on('click','.f-cancel',function(evt){   //点击取消按钮
                videoManageDialogEl.dialog('close');
            });
            */
          //show the delete button.
            $('.delete-l',videoListEl).show();
            videoManageLinkEl.text("上传视频");
            videoManageLinkEl.attr('href',"<%=basePath%>erp/video/toUploadVideo.do");
            videoManageDialogEl.dialog("close");
            //删除视频事件注册
            videoListEl.on('click','.delete-l',function(evt){
                var meEl=$(this),
                    itemEl=meEl.closest('.video-item'),
                    videoId = meEl.attr('videoid'),
                    videoName=meEl.attr('videoname');
                if(confirm("确定要删除视频["+videoName+"]么?")){
                    $.ajax({
                        type: "POST",
                        url:'<%=basePath%>erp/video/deleteVideo.do',
                        data:{
                            "videoId": videoId
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
        });
    </script>
</body>
</html>


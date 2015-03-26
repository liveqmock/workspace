<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
    <%@ include file="/common/meta.jsp"%>
<link href="<%=basePath%>/css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="<%=basePath%>/js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
    <!-- <link href="<%=basePath%>/js/videojs/video-js.css" rel="stylesheet">
    <script src="<%=basePath%>/js/videojs/video.js"></script> 
    <script>
        videojs.options.flash.swf = "<%=basePath%>/js/videojs/video-js.swf";
    </script>  -->
    <script src="<%=basePath%>/js/jwplayer.js"></script>
</head>
<body class="page-view-video">
    <div class="page">
        <div class="page-hd">
            <h3 class="page-title">雅座视频课件${videoPath }/${video.videoPath}</h3>
        </div>
        <div class="page-bd">
            <div class="page-bd-tbar">
                <div class="tbar-t">
                    <a href="#" onclick="window.history.back()" class="btn-link-1"><i class="icon-left-arrow">&#x3432;</i>&nbsp;返回</a>
                </div>
                <div class="tbar-b video-list-cate fn-clear">
                    <label class="fn-left">视频分类标签：</label>
                    <div class="cate-list-wrapper fn-left">
                        <ul class="cate-list fn-clear">
                            <li class="cate-item fn-left">
                                <a href='<%=basePath%>erp/video/toVideoItems.do?videoCatId=0'>全部</a>
                            </li>
                            <c:forEach items="${videoCatItems}" var="videoCat">
                                <li class="cate-item fn-left">
                                    <a href='<%=basePath%>erp/video/toVideoItems.do?videoCatId=${videoCat.videoCatId }'>${videoCat.catDesc }</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="page-bd-inner">
                <div class="video-detail-panel">
                    <h4 class="video-title">[${video.catDesc }] ${video.videoName }</h4>
                    <div class="video-meta">上传日期：${video.uploadTime }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;讲师：${video.presenter }</div>
                    <div class="video-desc">${video.videoDesc }</div>
                    <div id="video-container" class="player-panel">
                        <!--<video id="player-1" class="video-js vjs-default-skin"
                               controls preload="auto" width="640" height="264"
                               poster="http://www.html5rocks.com/en/tutorials/video/basics/star.png"
                               data-setup='{}'>
                            <source src="<%=basePath%>/js/videojs/1.mp4" type='video/mp4' />
                            <source src="rtmp://192.168.236.31:1935/start-project/1-1.mp4" type='video/mp4' />
                        </video>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        document.oncontextmenu = new Function("return false;");
        jQuery(function($){
        	jwplayer("video-container").setup({
	                sources: [
	                    {
	                    	//file: 'rtmp://192.168.236.31:1935/start-project/user-upload-video/video30/sales.mp4'
	                    	//file: 'rtmp://192.168.236.31:1935/start-project/1-1.mp4'
	                    	file: '${videoPath }/${video.videoPath}'
	                    }
	                ],
	                image: "bg.jpg",
	                autostart: false,
	                width: 640,
	                height: 480,
	                primary: "flash"
	        });
        });
    </script>
</body>
</html>


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
    <link href="<%=basePath%>/css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>/js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery/jquery.form.js"></script>
</head>
<body class="page-upload-video">
    <div class="page">
        <div class="page-hd">
            <h3 class="page-title">视频课件</h3>
        </div>
        <div class="page-bd">
            <div class="page-bd-tbar">
                <a href="#" onclick="window.history.back()" class="btn-link-1"><i class="icon-left-arrow">&#x3432;</i>&nbsp;返回</a>
            </div>
            <div class="page-bd-inner">
                <form method="post" id="publish-video-form" enctype="multipart/form-data" action = "<%=basePath%>erp/video/saveFileUpload.do">
                    <div class="form-title">上传视频</div>
                    <div class="form-bd">
                        <div class="f-field fn-clear">
                            <label class="fn-left">分类：</label>
                            <div class="ff-value fn-left">
                                <select name="videoCat" class="video-cat">
                                    <c:forEach items="${videoCatItems}" var="videoCat">
                                        <option value="${videoCat.catVal}">${videoCat.catDesc}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="f-field fn-clear">
                            <label class="fn-left">讲师：</label>
                            <div class="ff-value fn-left">
                                <input type="text" name="presenter" class="presenter text-field"/>
                            </div>
                        </div>
                        <div class="f-field fn-clear">
                            <label class="fn-left">名称：</label>
                            <div class="ff-value fn-left">
                                <input type="text" name="videoName" class="video-name text-field""/>
                            </div>
                        </div>
                        <div class="f-field fn-clear">
                            <label class="fn-left">描述：</label>
                            <div class="ff-value fn-left">
                                <textarea name="videoDesc" class="video-desc text-field""></textarea>
                            </div>
                        </div>
                        <div class="f-field fn-clear">
                            <label class="fn-left">&nbsp;</label>
                            <div class="ff-value fn-left">
                                <input type="file" name="file" class="video-file" />
                            </div>
                        </div>
                        <div class="f-field fn-clear">
                            <label class="fn-left">&nbsp;</label>
                            <div class="ff-value fn-left">
                                <button type="button" class="f-submit">提&nbsp;交</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>
        jQuery(function($){
            var formEl=$('#publish-video-form'),
                videoCatEl=$('.video-cat'),
                videoNameEl=$('.video-name'),
                videoDescEl=$('.video-desc'),
                presenterEl=$('.presenter'),
                fileEl=$('.video-file');
            formEl.on("click",".f-submit",function() {
                var jsonData = {
                    videoCat: _.str.trim(videoCatEl.val()),
                    videoName: _.str.trim(videoNameEl.val()),
                    videoDesc: _.str.trim(videoDescEl.val()),
                    presenter: _.str.trim(presenterEl.val()),
                    file: fileEl.val()
                },options;
                if(jsonData.file.length==0){
                    alert("请选择文件");
                    return false;
                }else{
                    $("#publish-video-form").submit();
                    //window.location="<%=basePath%>erp/video/toVideoItems.do?videoCatId=0";
                    /**
                    options = {
                        type: "POST",
                        url:'<%=basePath%>erp/video/saveFileUpload.do',
                        data:jsonData,
                        dataType:"json",
                        success:function(data){
                        	window.location="<%=basePath%>erp/video/toVideoItems.do?videoCatId=0";
                        }
                    };
                    formEl.ajaxSubmit(options);
                    **/
                }
            });
        });
    </script>
</body>
</html>


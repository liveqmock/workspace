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
</head>
<body class="page-spefic-add">
    <div class="page">
        <div class="page-hd">
            <h3 class="page-title"><a href="<%=basePath%>erp/project/toProjectItems.do">案例库</a></h3>
        </div>
        <div class="page-bd">
            <div class="page-bd-inner">
                <div class="view-panel fn-hide">
                    <div class="f-field fn-clear">
                        <label class="fn-left">活动名称：</label>
                        <div class="ff-value fn-left"> ${dbProject.active.activeName eq null ? dbActiveName:  dbProject.active.activeName}</div>
                    </div>
                    <div class="f-field fn-clear">
                        <label class="fn-left">案例评价：</label>
                        <div class="ff-value fn-left">
                            ${dbProject.projectComments }
                        </div>
                    </div>
                    <div class="f-field fn-clear">
                        <label class="fn-left">城市：</label>
                        <div class="ff-value fn-left">
                         <c:forEach items="${dbProject.listCity}" var="map">
                            ${map.dictionary_value }&nbsp;
                         </c:forEach>
                        </div>
                    </div>
                    <div class="f-field fn-clear">
                        <label class="fn-left">业态：</label>
                        <div class="ff-value fn-left">
                         <c:forEach items="${dbProject.listCate}" var="map">
                            ${map.dictionary_value }&nbsp;
                         </c:forEach>
                        </div>
                    </div>
                    <div class="f-field fn-clear">
                        <label class="fn-left">分类：</label>
                        <div class="ff-value fn-left">
                         <c:forEach items="${dbProject.listCat}" var="map">
                            ${map.dictionary_value }&nbsp;
                         </c:forEach>
                        </div>
                    </div>
                    <div class="f-field fn-clear">
                        <label class="fn-left">人均：</label>
                        <div class="ff-value fn-left">
                         <c:forEach items="${dbProject.listAvgPrice}" var="map">
                            ${map.dictionary_value }&nbsp;
                         </c:forEach>
                        </div>
                    </div>
                    <div class="f-field fn-clear">
                        <label class="fn-left">优惠：</label>
                        <div class="ff-value fn-left">
                         <c:forEach items="${dbProject.listPromote}" var="map">
                            ${map.dictionary_value }&nbsp;
                         </c:forEach>
                        </div>
                    </div>
                    
                    <div class="f-field fn-clear">
                        <label class="fn-left">其他：</label>
                        <div class="ff-value tag-list">
                            <c:forEach items="${dbProject.labels}" var="addedlabel">
                                <span class="tag-item">${addedlabel.lableName}</span><c:if test="${fn:length(dbProject.labels)-1 != status.index}"> </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="add-edit-panel fn-hide">
                    <form method="post" class="add-edit-form" enctype="multipart/form-data" action = "<%=basePath%>erp/project/submitProject.do">
                        <div class="form-hd">
                            <h4 class="form-title">上传案例&nbsp;<span class="summary">(<i class="icon-star">*</i>号为必填)</span></h4>
                        </div>
                        <div class="form-bd">
                            <div class="f-field fn-clear">
                                <label class="fn-left">活动名称：</label>
                                <div class="ff-value fn-left">${dbProject.active.activeName eq null ? dbActiveName:  dbProject.active.activeName}</div>
                            </div>
                            <div class="f-field fn-clear">
                                <label class="fn-left"><i class="icon-star">*</i>案例评价：</label>
                                <div class="ff-value fn-left">
                                    <textarea name="projectComments" class="project-comments text-field">${dbProject.projectComments }</textarea>
                                </div>
                            </div>
		                    <div class="f-field fn-clear">
		                        <label class="fn-left"><i class="icon-star">*</i>城市：</label>
		                        <div class="ff-value tag-list">
		                            <c:forEach items="${mapDictionary.listCity}" var="object">
<span class="tag-item <c:forEach items="${dbProject.listCity}" var="map"><c:if test="${map.dictionary_key eq object.dictionary_key}">state-selected</c:if></c:forEach>"
		                                 key="${object.dictionary_key}">${object.dictionary_value}</span>
		                            </c:forEach>
		                        <input class="cat-selected" type="hidden" name="cityType" value="<c:forEach items="${dbProject.listCity}" var="map">${map.dictionary_key }&nbsp;</c:forEach>"/>
		                        </div>
		                    </div>
		                    <div class="f-field fn-clear">
		                        <label class="fn-left"><i class="icon-star">*</i>业态：</label>
		                        <div class="ff-value tag-list">
		                            <c:forEach items="${mapDictionary.listCate}" var="object">
<span class="tag-item <c:forEach items="${dbProject.listCate}" var="map"><c:if test="${map.dictionary_key eq object.dictionary_key}">state-selected</c:if></c:forEach>"
		                                 key="${object.dictionary_key}">${object.dictionary_value}</span>
		                            </c:forEach>
		                        <input class="cat-selected" type="hidden" name="cateType" value="<c:forEach items="${dbProject.listCate}" var="map">${map.dictionary_key }&nbsp;</c:forEach>"/>
		                        </div>
		                    </div>
		                    <div class="f-field fn-clear">
		                        <label class="fn-left"><i class="icon-star">*</i>分类：</label>
		                        <div class="ff-value tag-list">
		                            <c:forEach items="${mapDictionary.listCat}" var="object">
<span class="tag-item <c:forEach items="${dbProject.listCat}" var="map"><c:if test="${map.dictionary_key eq object.dictionary_key}">state-selected</c:if></c:forEach>"
		                                 key="${object.dictionary_key}">${object.dictionary_value}</span>
		                            </c:forEach>
		                        <input class="cat-selected" type="hidden" name="catType" value="<c:forEach items="${dbProject.listCat}" var="map">${map.dictionary_key }&nbsp;</c:forEach>"/>
		                        </div>
		                    </div>
		                    <div class="f-field fn-clear">
		                        <label class="fn-left"><i class="icon-star">*</i>人均：</label>
		                        <div class="ff-value tag-list">
		                            <c:forEach items="${mapDictionary.listAvgPrice}" var="object">
<span class="tag-item <c:forEach items="${dbProject.listAvgPrice}" var="map"><c:if test="${map.dictionary_key eq object.dictionary_key}">state-selected</c:if></c:forEach>"
		                                 key="${object.dictionary_key}">${object.dictionary_value}</span>
		                            </c:forEach>
		                        <input class="cat-selected" type="hidden" name="avgPriceType" value="<c:forEach items="${dbProject.listAvgPrice}" var="map">${map.dictionary_key }&nbsp;</c:forEach>"/>
		                        </div>
		                    </div>
		                    <div class="f-field fn-clear">
		                        <label class="fn-left"><i class="icon-star"></i>优惠：</label>
		                        <div class="ff-value tag-list">
		                            <c:forEach items="${mapDictionary.listPromote}" var="object">
<span class="tag-item <c:forEach items="${dbProject.listPromote}" var="map"><c:if test="${map.dictionary_key eq object.dictionary_key}">state-selected</c:if></c:forEach>"
		                                 key="${object.dictionary_key}">${object.dictionary_value}</span>
		                            </c:forEach>
		                        <input class="cat-selected" type="hidden" name="promoteType" value="<c:forEach items="${dbProject.listPromote}" var="map">${map.dictionary_key }&nbsp;</c:forEach>"/>
		                        </div>
		                    </div>
		                   
                            <div class="other-tag-field f-field fn-clear">
                                <label class="fn-left">其他：</label>
                                <div class="ff-value tag-list">
                                    <c:forEach items="${dbLabels}" var="dblabel">
                                        <span class="tag-item 
                                        <c:forEach items="${dbProject.labels}" var="addedlabel" varStatus="status">
		                                	<c:if test="${addedlabel.lableName eq dblabel.lableName}">state-selected</c:if>	 
		                                </c:forEach>
                                        " key='${dblabel.labelId}' labelName='${dblabel.lableName}'>${dblabel.lableName}</span>
                                    </c:forEach>
                                </div>
                                <input class="cat-selected" type="hidden" name="projectLabels" 
 value="<c:forEach items="${dbProject.labels}" var="addedlabel" varStatus="status">${addedlabel.labelId }${label_sperater}</c:forEach>"/>
                            </div>
                            <div class="add-tag-field f-field fn-clear">
                                <label class="fn-left">添加标签：</label>
                                <div class="tag-wrapper fn-clear">
                                    <ul class="selected-tag-list">
                                       <!--<li class="selected-tag-item fn-left"><span class="tag-item-inner"><span class="tag-name">test</span><i class="icon-close">&#10005;</i></span></li>-->
                                    </ul>
                                    <input type="text" class="add-tag-input" />
                                </div>
                                <input class="new-tag-hidden" type="hidden" name="newLabels" value=""/>
                            </div>
                            <div class="f-field fn-clear">
                                <label class="fn-left">营销活动图片：</label>
                                <div class="ff-value fn-left">
                                    <input type="file" name="myfiles" class="file-input" />
                                </div>
                            </div>
                            <div class="f-field fn-clear">
                                <label class="fn-left">&nbsp;</label>
                                <div class="ff-value fn-left">
                                    <button type="submit" class="f-submit">提交</button>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="activeId" value="${activeId }"/>
                        <input type="hidden" name="typeId" value="${typeId }"/>
                        <input type="hidden" name="projectId" id="flagProjectId" value="${dbProject.projectId }"/>
                        <input type="hidden" name="imgPathChanged" id="imgPathChanged" value=""/>
                    </form>
                </div>
                <div class="cover-preview">
                <c:if test="${toView == true}">
                    <img src="<%=basePath%>${imgLocationPath}/${dbProject.activeImgPath }" alt=""/>
                </c:if>
                </div>
            </div>
        </div>
    </div>
    <script>
        jQuery(function($){
            var viewPanelEl=$('.view-panel'),
                addTagInputEl = $('.add-tag-input'),
                selectedTagListEl = $('.selected-tag-list'),
                otherTagFieldEl = $('.other-tag-field'),
                newTagHiddenEl = $('.new-tag-hidden'),
                addEditPanelEl=$('.add-edit-panel'),
                addEditFormEl=$('.add-edit-form'),
                tagListEl=$('.tag-list',addEditFormEl),
                tagSelectedEl=$('.cat-selected',addEditFormEl),
                tagSeparator="${label_sperater}"||" ",
                pageState=${toView};
            //高亮选中的标签
            _.each(tagSelectedEl.val().split(tagSeparator),function(tagName){
                $('.tag-item',tagListEl).each(function(){
                    var tagItemEl=$(this);
                    if(tagItemEl.text()==tagName){
                        tagItemEl.addClass('state-selected');
                        return false;
                    }
                });
            });
            //注册标签click事件
            addEditPanelEl.on('click','.tag-list .tag-item',function(evt){
                var tagItemEl=$(this);
                var tagListEl = tagItemEl.closest('.tag-list'),
                    tagSelected=[],
                    isExist = false;
                if (!tagItemEl.hasClass('state-selected')) {
                    $('.selected-tag-item', selectedTagListEl).each(function () {
                        if (tagItemEl.text() == $('.tag-name', this).text()) {
                            isExist = true;
                            return false;
                        }
                    });
                    if (!isExist) {
                        tagItemEl.addClass('state-selected');
                    } else {
                        alert('已添加相同标签');
                    }
                } else {
                    tagItemEl.removeClass('state-selected');
                }
                //tagItemEl.hasClass('state-selected')?tagItemEl.removeClass('state-selected'):tagItemEl.addClass('state-selected');
                $('.tag-item', tagListEl).each(function(){
                    var tagItemEl=$(this);
                    if(tagItemEl.hasClass('state-selected')){
                       tagSelected.push(tagItemEl.attr('key'));
                    }
                });
            	catSelectedEl = $('.cat-selected', tagListEl.closest('.f-field'));
            	catSelectedEl.val(tagSelected.join(tagSeparator));
            });
            //手动添加标签功能
            addTagInputEl.keyup(function (evt) {
                var keyCode = evt.keyCode,
                    val,
                    isExist = false;
                if(keyCode == 32) { //空格
                    val = addTagInputEl.val();
                    val = _.str.trim(val);
                    if (val.length > 0) {
                        $('.tag-item', otherTagFieldEl).each(function () {
                            if (val == $(this).text()) {
                                isExist = true;
                                return false;
                            }
                        });
                        $('.selected-tag-item', selectedTagListEl).each(function () {
                            if (val == $('.tag-name', this).text()) {
                                isExist = true;
                                return false;
                            }
                        });
                        if (!isExist) {
                            selectedTagListEl.append('<li class="selected-tag-item fn-left"><span class="tag-item-inner"><span class="tag-name">' + val + '</span><i class="icon-close">&#10005;</i></span></li>');
                            addTagInputEl.val("");  //清空输入
                        } else {
                            alert('已添加相同的标签');
                            addTagInputEl.val(val); 
                        }
                        //重构对应隐藏域值
                        updateNewTags();
                    }
                } else if (keyCode == 8) { //后退键
                	val = addTagInputEl.val();
                	if (val.length == 0) {
                		$('.selected-tag-item', selectedTagListEl).last().remove();
                		updateNewTags();
                	}
                }
            });
            selectedTagListEl.on('click', '.icon-close', function () {
                $(this).closest('.selected-tag-item').remove();
                updateNewTags();
            });
            //案例提交
            addEditFormEl.submit(function(){
                var fileInputEl=$('.file-input',addEditFormEl);
                var message ="";
                if(_.str.trim($('input[name=cityType]').val())==""){
                	message+="城市不能为空\n";
                }
                if(_.str.trim($('input[name=cateType]').val())==""){
                	message+="业态不能为空\n";
                }
                if(_.str.trim($('input[name=catType]').val())==""){
                	message+="分类不能为空\n";
                }
                if(_.str.trim($('input[name=avgPriceType]').val())==""){
                	message+="人均不能为空\n";
                }
                if(_.str.trim($('.project-comments').val())==""){
                	message+="评论不能为空\n";
                }
                if(_.str.trim(fileInputEl.val()).length==0&&$("#flagProjectId").val()==""){
                	message+="营销活动图片不能为空";
                }
                if(message!=""){
                	alert(message);
                	return false;
                }
                //如果图片文件有改动， 增加一个标识路径有更新
                if(_.str.trim(fileInputEl.val()).length!=0){
                	$("#imgPathChanged").val("true");
                }
                return true;
            });
            //页面状态切换 view/edit
            pageState?viewPanelEl.show():addEditPanelEl.show();
            
            //重构对应隐藏域值
            function updateNewTags(){
                var newTags = [];
	            $('.selected-tag-item', selectedTagListEl).each(function () {
	                newTags.push($('.tag-name', this).text());
	            });
	            newTagHiddenEl.val(newTags.join(tagSeparator));
	            //alert(newTags.join(tagSeparator));
            }
        });
    </script>
</body>
</html>


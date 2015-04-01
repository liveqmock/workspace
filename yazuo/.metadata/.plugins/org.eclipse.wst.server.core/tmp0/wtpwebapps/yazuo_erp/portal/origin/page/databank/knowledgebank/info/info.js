/**
 * 知识库-info
 */
define(['avalon', 'util', 'json', 'moment', '../../../../widget/pagination/pagination','../../../../widget/form/select'], function (avalon, util, JSON) {
     var win = this,
        erp = win.erp,
        pageMod = {},
        tempWidgetIdStore = [];
    _.extend(pageMod, {
        /**
         * 页面初始化
         * @param pageEl
         * @param pageName
         */
        "$init": function (pageEl, pageName, routeData) {
            var getTypeId = function (n) {//设置和获取widget的id;
                var widgetId = pageName + n;
                tempWidgetIdStore.push(widgetId);
                return widgetId;
            };
            var pageVm = avalon.define(pageName, function (vm) {
                vm.navCrumbs = [{
                    "text":"知识库",
                    "href":"#/databank/knowledgebank"
                }, {
                    "text":'知识详情'
                }];
                vm.permission = {//权限

                };
                vm.path=erp.BASE_PATH;
                vm.pageUserId = erp.getAppData('user').id;//用户id
                vm.fileId = parseInt(routeData.params["id"]);
                vm.userImgPath = erp['appData']['user']['userImagePath'];
                //页面展示变量
                vm.knowledgeTitle = '';//标题
                vm.kindText = '';//分类
                vm.businessTypeText = '';//业态
                vm.updatedTime = '';//更新时间
                vm.viewedTimes = '';//浏览次数
                vm.score = 0;//综合评分
                vm.description = '';//描述
                vm.analysis = '';//分析
                vm.resolution = '';//解决方案
                vm.successfulCase = '';//成功案例
                vm.talkingSkills = '';//话术
                vm.attachment = {};//附件
                vm.commentariesRows = [];//评论列表
                vm.approvedTrue = 0;//有用的数量
                vm.approvedFalse = 0;//无用的数量
                vm.trueRatio = '0%';//有用的比例
                vm.falseRatio = '0%';//无用的比例
                vm.commentCon = '';//评论内容
                //页面展示变量-end
                //页面逻辑变量
                vm.scoreAvail = true;//是否可以评分
                vm.provedAvail = true;//是否可以评价有用、无用
                vm.pageNumber = 1;//当前页码
                vm.grade = 5;//评分分数
                vm.overGrade = 5;
                vm.starLen = [1,2,3,4,5];//分数星星个数
                var time;//setTimeout定时器
                //页面逻辑变量end

                //添加评论内容
                vm.addComment = function(){
                    if(_.str.trim(vm.commentCon)){
                        util.c2s({
                            "url": erp.BASE_PATH + 'commentary/add.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify(
                                {
                                    userId:vm.pageUserId,
                                    content:vm.commentCon,
                                    knowledgeId:vm.fileId,
                                    score:vm.scoreAvail?vm.grade:''
                                }
                            ),
                            "success": function(responseData){
                                var data;
                                if(responseData.flag){
                                    var data = responseData.data;
                                    vm.commentCon = '';
                                    vm.score = data.score.toFixed(2);
                                    util.c2s({
                                        "url": erp.BASE_PATH + '/commentary/search.do',
                                        "type": "post",
                                        "contentType" : 'application/json',
                                        "data": JSON.stringify(
                                            {
                                                knowledgeId:pageVm.fileId,
                                                pageSize: 5,
                                                pageNumber: pageVm.pageNumber
                                            }
                                        ),
                                        "success": function(responseData){
                                            var data;
                                            if(responseData.flag){
                                                var paginationVm = avalon.getVm(getTypeId('paginationId'));
                                                data = responseData.data;
                                                console.log(data)
                                                paginationVm.total = data.totalSize;
                                                pageVm.commentariesRows = data.rows;//评论列表
                                                pageVm.scoreAvail = false;
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                };
                //有用无用评价
                vm.addCommentTrueOrFalse = function(type){
                    if(pageVm.provedAvail){
                        util.c2s({
                            "url": erp.BASE_PATH + 'knowledgeProve/add.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify(
                                {
                                    userId:vm.pageUserId,
                                    approved:!!type,
                                    knowledgeId:vm.fileId
                                }
                            ),
                            "success": function(responseData){
                                var data;
                                if(responseData.flag){
                                    data = responseData.data.knowledgeProved;
                                    pageVm.approvedTrue = data.true||0;//有用的数量
                                    pageVm.approvedFalse = data.false||0;//无用的数量
                                    pageVm.trueRatio = mathRatio(data.true+data.false,data.true);//有用的比例
                                    pageVm.falseRatio = mathRatio(data.true+data.false,data.false);//无用的比例
                                    pageVm.provedAvail = false;//是否有用、无用完毕
                                }
                            }
                        });
                    }
                };
                //改变分数
                vm.changeGrade = function(event,n){
                    if(vm.scoreAvail){
                        event = event||window.event;
                        if(event.type == 'click'){//判断事件类型
                            vm.grade = n;
                            vm.overGrade = n;
                        }else if(event.type == 'mouseover'){
                            clearTimeout(time);
                            vm.grade = n;
                        }else if(event.type == 'mouseout'){
                            time = setTimeout(function(){
                                vm.grade = vm.overGrade;
                            },200);

                        }
                    }
                };
                //分页
                vm.$paginationOpts = {
                    "paginationId": getTypeId('paginationId'),
                    "total": 0,
                    "perPages": 5,
                    "onJump": function (evt, vm) {
                        var paginationVm = avalon.getVm(getTypeId('paginationId'));
                        pageVm.pageNumber=paginationVm.currentPage;
                        updateList();
                        util.c2s({
                            "url": erp.BASE_PATH + '/commentary/search.do',
                            "type": "post",
                            "contentType" : 'application/json',
                            "data": JSON.stringify(
                                {
                                    knowledgeId:pageVm.fileId,
                                    pageSize: 5,
                                    pageNumber: paginationVm.currentPage
                                }
                            ),
                            "success": function(responseData){
                                var data;
                                if(responseData.flag){
                                    data = responseData.data;
                                    paginationVm.total = data.totalSize;
                                    pageVm.commentariesRows = data.rows;//评论列表
                                }
                            }
                        });
                    }
                };
                //分页end
            });
            avalon.scan(pageEl[0]);
            //递归分类方法
            function getKindText (arr,id){
                var newArr = [];
                _.map(arr,function(item,index){
                    if(item.id == id){
                        newArr.unshift(item.title);
                        var parentArr = getKindText(arr,item.parentId);
                        newArr = parentArr.concat(newArr);
                    }
                });
                return newArr;
            }
            //获取业态中文
            function getBusinessText(arr ,id){
                var text = '';
                _.map(arr,function(item,index){
                    if(item.id == id){
                        text = item.typeText;
                        return;
                    }
                });
                return text;
            }
            //计算占比
            function mathRatio(all,ratio){
                if(parseInt(all)){
                    return ((100*ratio)/(100*all)*100).toFixed(2)+'%';
                }else{
                    return '100%';
                }
            }
            /*页面初始化请求渲染*/
            function updateList(){
                var requestData=JSON.stringify(
                    {
                        id:pageVm.fileId,
                        pageSize:5,
                        pageNumber:pageVm.pageNumber
                    }
                );
                util.c2s({
                    "url": erp.BASE_PATH + 'knowledge/view.do',
                    "type": "post",
                    "contentType" : 'application/json',
                    "data": requestData,
                    "success": function(responseData){
                        var data;
                        if(responseData.flag){
                            data = responseData.data;
                            pageVm.knowledgeTitle = data.knowledge.title;
                            pageVm.kindText = getKindText(data.knowledgeKinds,data.knowledge.kindId).join("->");//分类
                            pageVm.businessTypeText = getBusinessText(data.businessTypes,data.knowledge.businessTypeId);//业态
                            pageVm.updatedTime = data.knowledge.updatedTime;//更新时间
                            pageVm.viewedTimes = data.knowledge.viewedTimes;//浏览次数
                            pageVm.score = data.stats.score.toFixed(2);//综合评分
                            pageVm.description = data.knowledge.description;//描述
                            pageVm.analysis = data.knowledge.analysis;//分析
                            pageVm.resolution = data.knowledge.resolution;//解决方案
                            pageVm.successfulCase = data.knowledge.successfulCase;//成功案例
                            pageVm.talkingSkills = data.knowledge.talkingSkills;//话术
                            pageVm.attachment = data.knowledge.attachment||{};//附件
                            pageVm.commentariesRows = data.commentaries.rows;//评论列表
                            pageVm.approvedTrue = data.stats.approved.true;//有用的数量
                            pageVm.approvedFalse = data.stats.approved.false;//无用的数量
                            pageVm.trueRatio = mathRatio(data.stats.approved.true+data.stats.approved.false,data.stats.approved.true);//有用的比例
                            pageVm.falseRatio = mathRatio(data.stats.approved.true+data.stats.approved.false,data.stats.approved.false);//无用的比例
                            pageVm.scoreAvail = data.scoreAvail;//是否可以评分
                            pageVm.provedAvail = data.provedAvail;//是可以评价有用无用
                            pageVm.grade = pageVm.overGrade = data.knowledge.score;//默认显示分数和次用户上一次评价分数
                            var paginationVm = avalon.getVm(getTypeId('paginationId'));
                            paginationVm.total = data.commentaries.totalSize;
                        }
                    }
                });
            }
            updateList();
            /*页面初始化请求渲染end*/
        },
        /**
         * 页面销毁[可选]
         */
        "$remove": function (pageEl, pageName) {
             _.each(tempWidgetIdStore, function (widgetId) {
                var vm = avalon.getVm(widgetId);
                vm && $(vm.widgetElement).remove();
            });
            tempWidgetIdStore.length = 0;
        }
    });
    return pageMod;
});



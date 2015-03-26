/**
 * grid组件
 */
define(["avalon", "text!./grid.html", "text!./grid.css", "../pagination/pagination"], function(avalon, sourceHTML, cssText) {
    var styleEl = $("#widget-style"),
        sourceTmpl = sourceHTML;
    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    if (!styleEl.data('widgetGrid')) {
        try {
            styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'grid/'));
        } catch (e) {
            styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'grid/');
        }
        styleEl.data('widgetGrid', true);
    }
    var widget = avalon.ui.grid = function(element, data, vmodels) {
        var opts = data.gridOptions,   //不考虑嵌套widget配置项继承的问题，可通过ms-controller解决
            elEl = $(element),
            tmpl = opts.getTemplate(sourceTmpl, opts),
            paginationId = data.gridId + '-pagination',
            columns = opts.columns;
        opts["columns"] = _.map(columns, function (itemData) {
            itemData.cls = itemData.cls ||  'cell-' + _.str.dasherize(itemData.dataIndex);
            itemData.headerWidget = itemData.headerWidget || null;
            if (itemData.renderer) {
                itemData["dataIndex"] = "__" + itemData["dataIndex"] + "__";
            }
            return itemData;
        });

        var vmodel = avalon.define(data.gridId, function (vm) {
            vm.$skipArray = ["widgetElement", "pageSize"];
            avalon.mix(vm, opts);
            vm.widgetElement = element;
            vm.gridData = [];
            vm.gridTotalSize = 0;
            vm.gridAllChecked = false;
            vm.gridSelected = [];
            vm._tempHeaderWidgetIds = [];
            vm.gridCheckAll = function () {
                if (this.checked) {
                    vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                        return itemData.id;
                    });
                } else {
                    vm.gridSelected.clear();
                }
            };
            /**
             * 表格左下脚一个全选的快捷方式
             */
            vm.scCheckAll = function () {
                vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                    return itemData.id;
                });
            };
            vm.$paginationOpts = {
                "paginationId": paginationId,
                "total": 0,
                "perPages": vm.pageSize,
                "onJump": function (evt, vm) {
                    vmodel.load();
                }
            };
            vm.$init = function() {
                elEl.addClass("ui-grid");
                elEl.html(tmpl.replace(/MS_OPTION_TBAR|MS_OPTION_MAIN/g, ''));
                //扫描
                avalon.scan(element, [vmodel].concat(vmodels));
                //init header widget
                vm._initHeaderWidget();
            };
            vm.$remove = function() {
                var paginationVm = avalon.vmodels[paginationId];
                paginationVm && $(paginationVm.widgetElement).remove();
                //销毁header widget vm
                _.each(vm._tempHeaderWidgetIds, function (widgetId) {
                    $(avalon.getVm(widgetId).widgetElement).remove();
                    delete avalon.vmodels[widgetId + "-host"];
                });
                vm._tempHeaderWidgetIds = null;
                elEl.off();
                element.innerHTML = "";
            };
            vm._initHeaderWidget = function () {
                var headEl = $("thead", elEl);
                _.each(vm.columns.$model, function (itemData) {
                    var widgetType,
                        widgetConfig,
                        widgetId,
                        headerWidget = itemData.headerWidget,
                        widgetVm,
                        hostEl;
                    if (headerWidget) {
                        widgetType = headerWidget.type;
                        widgetConfig = headerWidget.widgetConfig;
                        widgetId = widgetConfig[widgetType + "Id"] || generateID(widgetType);
                        widgetConfig[widgetType + "Id"] = widgetId;
                        widgetVm = avalon.define(widgetId + '-host', function (hostVm) {
                            hostVm.$widgetConfig = widgetConfig;
                        }); 
                        //准备结构
                        hostEl = $('[data-widget-type="' + widgetType + '"][data-data-index="' + itemData.dataIndex + '"]', headEl);
                        hostEl.attr("ms-widget", widgetType + ",$,$widgetConfig");
                        avalon.scan(hostEl[0], [widgetVm]);
                        //存储widgetId便于以后销毁
                        vm._tempHeaderWidgetIds.push(widgetId);
                    } 
                }); 
            };
            /**
             * @param {Number} pageNumber 跳到第几页
             */
            vm.load = function (pageNumber) {
                var paginationVm = avalon.vmodels[paginationId],
                    requestData = {
                        "pageSize": paginationVm.perPages
                    };
                if (pageNumber) {
                    requestData.pageNumber = pageNumber;
                } else {
                    requestData.pageNumber = paginationVm.currentPage;
                }
                vm.onLoad(requestData, function (data) {
                    var rows;
                    if (data) {
                        rows = data.rows;
                        paginationVm.total = data.totalSize;
                        if (pageNumber) {
                            paginationVm.currentPage = pageNumber;   //重设指定的页数
                        }
                        //判断是否存在记录id，如果没有，默认给每条记录生成唯一id
                        if (rows.length) {
                            if (!rows[0].hasOwnProperty("id")) {
                                rows = _.map(rows, function (itemData, i) {
                                    itemData.id = generateID();
                                    return itemData;
                                });
                            } else {
                                if (_.isNumber(rows[0].id)) {   //如果是number型id，转成string型，便宜选中操作
                                    rows = _.map(rows, function (itemData) {
                                        itemData.id = (itemData.id + '');
                                        return itemData;
                                    });
                                }
                            }
                            //内部转换renderer
                            _.each(rows, function (itemData, i) {
                                _.each(vm.columns.$model, function (columnData) {
                                    if (_.isFunction(columnData["renderer"])) {
                                        itemData[columnData["dataIndex"]] = columnData["renderer"](itemData[getRealDataIndexName(columnData["dataIndex"])], itemData, i);
                                    }
                                });
                                //设置行选控制属性
                                itemData["_selected"] = false;
                            });
                        }
                        vm.gridData = rows;
                        vm.gridTotalSize = data.totalSize;
                        vm.gridAllChecked = false;  //取消全选
                        vm.gridSelected.clear();
                    }
                });
            };
            /**
             * @param {String/Array} indexs 待选中的记录id
             * @param {Boolen} clear 是否需要清除其他选中，默认是false
             */
            vm.selectById = function (ids, clear) {
                if (clear) {
                    vm.gridSelected.clear();
                }
                if (ids === "all") {
                    vm.gridSelected = _.map(vm.gridData.$model, function (itemData) {
                        return itemData.id;
                    });
                } else {
                    ids = [].concat(ids);
                    _.each(ids, function (id) {
                        vm.gridSelected.ensure(id + "");
                    });
                }
            };
            /**
             * @param {String/Array} indexs 待清除选中的记录id
             */
            vm.unselectById = function (ids) {
                if (ids === "all") {
                    vm.gridSelected.clear();
                } else {
                    ids = [].concat(ids);
                    _.each(ids, function (id) {
                        vm.gridSelected.remove(id + "");
                    });
                }
            };
            /**
             * 获取选中的数据
             */
            vm.getSelectedData = function () {
                var result = [];
                _.each(vm.gridSelected.$model, function (id) {
                    var findData = _.find(vm.gridData.$model, function (itemData) {
                        return itemData.id == id;
                    });
                    findData && result.push(findData);
                });
                /*if (result.length === 1) {
                    result = result[0];
                }*/
                return result;
            };
            /**
             * 根据属性值获取整条记录信息
             * @param p {String} 属性名
             * @param v {Mix} 属性值
             */
            vm.getDataByParam = function (p, v) {
                var gridDataModel = vm.gridData.$model;
                return _.find(gridDataModel, function (itemData) {
                    return itemData[p] === v;
                });
            };
            /**
             * @param gridData {Array/Object} 待更新的表格数据，相同的id被覆盖，不存在的id新加
             */
            vm.updateData = function (gridData) {
                var gridDataModel = vm.gridData.$model;
                _.each([].concat(gridData), function (itemData) {
                    var atIndex = -1;
                    if (!_.isUndefined(itemData.id) && _.some(gridDataModel, function (itemData2, i) {
                        if (itemData2.id == itemData.id) {
                            atIndex = i;
                            return true;
                        }
                    })) {
                         //格式化
                        _.each(vm.columns.$model, function (columnData) {
                            if (_.isFunction(columnData["renderer"]) && itemData[getRealDataIndexName(columnData["dataIndex"])]) {
                                itemData[columnData["dataIndex"]] = columnData["renderer"](itemData[getRealDataIndexName(columnData["dataIndex"])], avalon.mix({}, vm.gridData[atIndex].$model, itemData), atIndex);
                            }
                        });
                        vm.gridData.set(atIndex, itemData);
                    } else {
                        //判断是否有id，没有的化生成一个唯一id
                        if (_.isUndefined(itemData.id)) {
                            itemData.id = generateID();
                        }
                         //格式化
                        _.each(vm.columns.$model, function (columnData) {
                            //if (_.isFunction(columnData["renderer"]) && itemData[columnData["dataIndex"]]) {
                            if (_.isFunction(columnData["renderer"])) { //未定义renderer且在column内声明的,itemData里相应引用为undefined
                                itemData[columnData["dataIndex"]] = columnData["renderer"](itemData[getRealDataIndexName(columnData["dataIndex"])], itemData, vm.gridData.size());
                                //有renderer方法的数据源重新复制一份，保证不影响原来的数据源
                            }
                        });
                        vm.gridData.push(itemData);
                    }
                });
            };
            /**
             * 根据id删除表格数据，可一次删除多条
             */
            vm.removeDataById = function (id) {
                _.each([].concat(id), function (itemId) {
                    var gridDataModel = vm.gridData.$model,
                        atIndex = -1;
                    if(_.some(gridDataModel, function (itemData, i) {
                        if (itemData.id == itemId) {
                            atIndex = i;
                            return true;
                        }
                    })) {
                        vm.gridData.removeAt(atIndex);
                    }
                });
            };
        });
        vmodel.columns.$watch('length', function () {
            _.each(_.range(0, vmodel.columns.size()), function (index) {
                vmodel.columns[index].cls = vmodel.columns[index].cls ||  'cell-' + _.str.dasherize(vmodel.columns[index].dataIndex);
                vmodel.columns[index].headerWidget = vmodel.columns[index].headerWidget || null; 
                if (vmodel.columns[index].renderer && (vmodel.columns[index].dataIndex.slice(0, 2) !== "__")) {
                    vmodel.columns[index].dataIndex = "__" + vmodel.columns[index].dataIndex + "__";
                }
            });
        });

        vmodel.gridSelected.$watch("length", function(n) {
            var gridDataModel = vmodel.gridData.$model,
                gridSelectedModel = vmodel.gridSelected.$model;
            vmodel.gridAllChecked = (n === vmodel.gridData.size());
            //控制行选样式
            avalon.nextTick(function () {
                _.each(gridDataModel, function (itemData, i) {
                    if (_.some(gridSelectedModel, function (id) {
                        return itemData.id == id;
                    })) {
                        vmodel.gridData.set(i, {
                            "_selected": true
                        });
                    } else {
                        vmodel.gridData.set(i, {
                            "_selected": false
                        });
                    }
                });
            });
        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        disableCheckAll: true,  //是否禁用全选
        disableCheckbox: false, //是否禁用选择框，默认不禁用
        disableBatchRemove: true,   //是否禁用批量删除
        recordUnit: "", //显示记录的单位
        tableLayout: "auto",   //自动布局
        pageSize: 10, //每页显示10条
        columns: [],
        /**
         * columns demo
         * columns: [{
         *      "dataIndex": "column1",
         *      "text": "测试",
         *      "html": true,
         *      "hideable": false,
         *      "renderer": function () {},
         *      "headerWidget": {
         *          "type": "select",
         *          "widgetConfig": {
         *              "selectId": "test", //可选
         *              "options": [{
         *                  "text": "option1",
         *                  "value": 1
         *              }]
         *          }
         *      }
         * }]
         */
        getTemplate: function(str, options) {
            return str;
        },
        onLoad: function (requestData, callback) {
            callback.call(this, null);
        },
        batchRemoveGridItem: avalon.noop
    };

    /* 私有函数 */
    //生成UUID http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript
    function generateID(key) {
        key = key || "grid";
        return key + Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    }

    /**
     * 获取真实的dataIndex值，eg. 传入__name__， 返回name
     */
    function getRealDataIndexName (innerDataIndexName) {
        return innerDataIndexName.slice(2).slice(0, -2);
    }
    return avalon;
});




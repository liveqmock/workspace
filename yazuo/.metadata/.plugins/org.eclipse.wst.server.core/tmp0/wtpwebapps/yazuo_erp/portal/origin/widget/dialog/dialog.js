/**
 * dialog组件，参考avalon ui的组件构建方案
 */
define(["avalon", "text!./dialog.html", "text!./dialog.css"], function(avalon, sourceHTML, cssText) {
    var styleEl = $('#widget-style'),
        template = sourceHTML,
        widgetArr = template.split("MS_OPTION_WIDGET"),
        _maskLayer = widgetArr[0], // 遮罩层html(string)
        maskLayerExist = false, // 页面不存在遮罩层就添加maskLayer节点，存在则忽略
        maskLayer = avalon.parseHTML(_maskLayer).firstChild, // 遮罩层节点(dom node)
        _widget = widgetArr[1].split("MS_OPTION_INNERWRAPPER")[0], // 动态添加dialog时,添加组件的html(string)
        dialogShows = [], // 存在层上层时由此数组判断层的个数，据此做相应的处理
        dialogNum = 0, // 保存页面dialog的数量，当dialogNum为0时，清除maskLayer
        maxZIndex = getMaxZIndex();// 保存body直接子元素中最大的z-index值， 保证dialog在最上层显示

    if (styleEl.length === 0) {
        styleEl = $('<style id="widget-style"></style>');
        styleEl.appendTo('head');
    }
    try {
        styleEl.html(styleEl.html() + avalon.adjustWidgetCssUrl(cssText, 'dialog/'));
    } catch (e) {
        styleEl[0].styleSheet.cssText += avalon.adjustWidgetCssUrl(cssText, 'dialog/');
    }
    var body = (document.compatMode && document.compatMode.toLowerCase() == "css1compat") ? document.documentElement : document.body;
    var widget = avalon.ui.dialog = function(element, data, vmodels) {
        dialogNum++;
        var options = data.dialogOptions;   //不考虑嵌套widget配置项继承的问题，可通过ms-controller解决
        options.template = options.getTemplate(template, options);
        var _footerArr = options.template.split("MS_OPTION_FOOTER"),
            _contentArr = _footerArr[0].split("MS_OPTION_CONTENT"),
            _headerArr = _contentArr[0].split("MS_OPTION_HEADER"),
            _innerWraperArr = _headerArr[0].split("MS_OPTION_INNERWRAPPER"),
            _content = _contentArr[1], // content wrapper html
            _lastHeader = _headerArr[1], // header html
            _lastFooter = _footerArr[1], // footer html
            _innerWrapper = _innerWraperArr[1], // inner wrapper html
            _lastContent = "", // dialog content html
            lastContent = "", // dialog content node
            $element = avalon(element);

        var vmodel = avalon.define(data.dialogId, function(vm) {
            avalon.mix(vm, options);
            vm.$skipArray = ["widgetElement", "autoOpen", "dynamicVmId"];
            vm.width = options.width;
            vm.widgetElement = element;
            // 配置了showClose为false，不显示关闭按钮
            vm.showClose = options.showClose;

            vm.open = function() {//open
                var len = 0;
                //document.body.style.overflow = "hidden";
                avalon.Array.ensure(dialogShows, vm);
                len = dialogShows.length;
                // 通过zIndex的提升来调整遮罩层，保证层上层存在时遮罩层始终在顶层dialog下面(顶层dialog zIndex-1)但是在其他dialog上面
                maskLayer.style.zIndex = 2 * len + maxZIndex -1;
                element.style.zIndex =  2 * len + maxZIndex;
                if (options.onOpen.call(vmodel) !== false) {
                    vm.toggle = true;
                }
                avalon.nextTick(function () {
                    resetCenter(vmodel, element);
                });

            };
            // 隐藏dialog
            vm.close = function() {//close
                avalon.Array.remove(dialogShows, vm);
                var len = dialogShows.length;
                // 重置maskLayer的z-index,当最上层的dialog关闭，通过降低遮罩层的z-index来显示紧邻其下的dialog
                maskLayer.style.zIndex = 2 * len + maxZIndex - 1;
                if (options.onClose.call(vmodel) !== false) {
                    vm.toggle = false;
                }
                //vmodel.toggle = false;
                /* 处理层上层的情况，因为maskLayer公用，所以需要其以将要显示的dialog的toggle状态为准 */
                if (len && dialogShows[len-1].modal) {
                    maskLayer.setAttribute("ms-visible", "toggle");
                    avalon.scan(maskLayer, dialogShows[len - 1]);
                }

            };
            vm.$watch("toggle", function(val) {
                var inputTextEl = $(':text,:password,textarea', element).eq(0);
                if (val) {
                    if (options.autoFocusInput && inputTextEl.length) {
                        setTimeout(function () {    //延时保证show出来后focus
                            setCursorEnd(inputTextEl[0]);
                        }, 100);
                    }
                }
            });
            vm.$watch("zIndex", function(val) {
                maxZIndex = val;
            });
            /**
             * desc: 可以动态改变dialog的内容
             * @param content: 要替换的content，可以是已经渲染ok的view也可以是未解析渲染的模板
             * @param noScan: 当content是模板时noScan设为false或者不设置，组件会自动解析渲染模板，如果是已经渲染ok的，将noScan设为true，组件将不再进行解析操作
             */
            vm.setContent = function(content, noScan) {
                _lastContent = content;
                lastContent.innerHTML = _lastContent;
                if (!noScan) {
                    avalon.scan(lastContent, [vmodel].concat(vmodels));
                }
            };
            // 动态修改dialog的title
            vm.setTitle = function(title) {
                vm.title = title;
            };
            // 重新渲染dialog
            vm.setModel = function(m) {
                // 这里是为了充分利用vm._ReanderView方法，才提前设置一下element.innerHTML
                if (!!m.$content) {
                    _lastContent = m.$content;
                    lastContent.innerHTML = _lastContent;
                }
                if (!!m.$title) {
                    vmodel.title = m.$title;
                }
                avalon.scan(element, [vmodel].concat(findModel(m)).concat(vmodels));
            };
            // 将零散的模板(dialog header、dialog content、 dialog footer、 dialog wrapper)组合成完整的dialog
            vm._RenderView = function() {
                var innerWrapper = ""; // 保存innerWraper元素节点
                // 用户只能通过data-dialog-width配置width，不可以通过ms-css-width来配置，配置了也无效
                element.setAttribute("ms-css-width", "width");
                lastContent = avalon.parseHTML(_content).firstChild;
                _lastContent = element.innerHTML;
                element.innerHTML = "";
                lastContent.innerHTML = _lastContent;
                innerWrapper = avalon.parseHTML(_innerWrapper).firstChild;
                innerWrapper.innerHTML = _lastHeader;
                innerWrapper.appendChild(lastContent);
                innerWrapper.appendChild(avalon.parseHTML(_lastFooter));
                element.appendChild(innerWrapper);
                if (!maskLayerExist) {
                    document.body.appendChild(maskLayer);
                    maskLayerExist = true;
                }
            };
            /**
             * 暴露出居中的方法
             */
            vm.resetCenter = function () {
                resetCenter(vmodel, element);
            };
            vm.$init = function() {
                $element.addClass("ui-dialog");
                element.setAttribute("ms-visible", "toggle");
                vm._RenderView();
                document.body.appendChild(element);
                // 当窗口尺寸发生变化时重新调整dialog的位置，始终使其水平垂直居中
                avalon(window).bind("resize", function() {
                    resetCenter(vmodel, element);
                });
                // 必须重新设置ms-visible属性，因为maskLayer为所有dialog所公用，第一次实例化dialog组件后maskLayer就失去了ms-visible属性
                maskLayer.setAttribute("ms-visible", "toggle");
                if (vmodel.modal) {
                    avalon.scan(maskLayer, [vmodel].concat(vmodels));
                }
                //去掉ui-hidden
                $element.removeClass('ui-hidden');
                avalon.scan(element, [vmodel].concat(vmodels));
                if (vm.autoOpen) {  //自动打开
                    vm.open();
                }
            };
            vm.$remove = function() {
                dialogNum--;
                element.innerHTML = "";
                //动态生成的弹框清除自动生成附属vm
                if (vm.dynamicVmId) {
                    delete avalon.vmodels[vm.dynamicVmId];
                }
                if (!dialogNum) {
                    maskLayer.parentNode.removeChild(maskLayer);
                    maskLayerExist = false;
                }
            };
        });
        return vmodel;
    };
    widget.version = 1.0;
    widget.defaults = {
        title: "&nbsp;", // dialog的title
        closeIconText: '<i class="icon-close ui-icon">&#xe605;</i>',
        autoFocusInput: true,   //自动选中第一个input:text
        autoOpen: false,
        onOpen: avalon.noop,
        onClose: avalon.noop,
        width: 480, // 默认dialog的width
        showClose: true,
        toggle: false, // 通过此属性的决定dialog的显示或者隐藏状态
        getTemplate: function(str, options) {
            return str;
        },
        modal: true,
        zIndex: maxZIndex
    };
    // 动态创建dialog
    avalon.dialog = function(config) {
        var widgetTmpl = _widget.replace("MS_OPTION_ID", config.id || "$" ).replace("MS_OPTION_OPTS", config.optName || "");
        var widget = avalon.parseHTML(widgetTmpl).firstChild;
        document.body.appendChild(widget);
        var vmodels = findModel(config.model, config.optName);
        avalon.scan(widget, vmodels);
        return avalon.vmodels[widget.msData["ms-widget-id"]];
    };
    function findModel(m, optName) {
        var vmodel,
            dynamicVmId;
        optName = optName || 'dialog';
        if (m) { // 如果m为字符串参数，说明是要在已存在的vmodels中查找对应id的vmodel
            if(avalon.type(m) === 'string') {
                vmodel = avalon.vmodels[m];
                if (!vmodel) {
                    throw new Error("您查找的"+m+"vm不存在");
                }
            }
        }
        // 如果传入的是avalon的vmodel格式的参数对象，直接返回，如果是普通的对象，将其转换为avalon的监控对象
        if (avalon.isPlainObject(m) && !m.$id && !m.$accessors) {
            dynamicVmId = 'dialog-dynamic-' + setTimeout("1");
            vmodel = avalon.define(dynamicVmId, function(vm) {
                vm[optName] = avalon.mix(m, {
                    "dynamicVmId": dynamicVmId
                });
                vm.$skipArray = [optName];
            });
        } else {
            vmodel = m; //认为是vm类型的参数
        }
        if (vmodel) {
            return [].concat(vmodel);
        } else {
            return [];
        }
    }
    // 调整弹窗水平、垂直居中
    function resetCenter(vmodel, target) {
        var bodyHeight = Math.max(body.clientHeight, body.scrollHeight),
                scrollTop = Math.max(document.body.scrollTop, document.documentElement.scrollTop),
                scrollLeft = Math.max(document.body.scrollLeft, document.documentElement.scrollLeft);
        if (vmodel.toggle) {
            //maskLayer.style.width = avalon(window).width() + "px";
            //maskLayer.style.height = bodyHeight + "px";
            var l = ((avalon(window).width() - target.offsetWidth) / 2) + scrollLeft;
            var t = ((avalon(window).height() - target.offsetHeight) / 2) + scrollTop;
            target.style.left = l + "px";
            target.style.top = t + "px";
        }
    }
    function getMaxZIndex() {
        var children;
        var maxIndex = 10, zIndex;
        if (!document.body) {
            return maxIndex + 1;
        }
        children = document.body.children;
        for(var i = 0, el; el = children[i++]; ) {
            if(el === maskLayer) {
                continue;
            }
            zIndex = ~~avalon(el).css("z-index");
            if (zIndex) {
                maxIndex = Math.max(maxIndex, zIndex);
            }
        }
        return maxIndex + 1;
    }

    /**
     * 设置光标位置到:text结尾
     */
    function setCursorEnd (el) {
        var start,
            end,
            value = el.value;
        start = value.length;
        end = value.length;
        el.focus && el.focus();
        if (el.setSelectionRange) { // W3C
            el.setSelectionRange(start, end);
        } else if (el.createTextRange) { // IE
            var oR = el.createTextRange();

            //Fix IE from counting the newline characters as two seperate characters
            var breakPos,
                i;
            //设置断点位置
            breakPos=start;
            for (i=0; i < breakPos; i++){
                if( el.value.charAt(i).search(/[\r\n]/) != -1 ) {
                    start = start - .5;
                }
            }
            //设置断点位置
            breakPos=end;
            for (i=0; i < breakPos; i++) {
                if( el.value.charAt(i).search(/[\r\n]/) != -1 ) {
                    end = end- .5;
                }
            }

            oR.moveEnd('textedit',-1);
            oR.moveStart('character',start);
            oR.moveEnd('character',end - start);
            oR.select();
        }
    }
    return avalon;
});

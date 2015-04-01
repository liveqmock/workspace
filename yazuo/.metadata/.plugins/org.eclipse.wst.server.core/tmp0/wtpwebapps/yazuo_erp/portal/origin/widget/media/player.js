/**
 * 音频和视频播放组件，依赖第三方库 jwplayer.com
 */
define(["avalon", "jwplayer"], function(avalon, jwplayer) {
    var counts = 0;
    var widget = avalon.ui.player = function(element, data, vmodels) {
        var elEl = $(element);
        var opts = data.playerOptions;
        elEl.addClass("ui-player");
        var vmodel = avalon.define(data.playerId, function(vm) {
            var playerId = 'player-' + counts;
            counts++;

            avalon.mix(vm, opts);
            vm.playerCore = null;   //存储播放核心对象
            vm.playerId = playerId;
            vm.$skipArray = ["widgetElement", "jwplayerOptions"];//这些属性不被监控
            vm.widgetElement = element;

            vm.$init = function() {
                //准备结构
                elEl.html('<div id="' + playerId + '"></div>');

                jwplayer(playerId).setup(_.extend({
                    //width: elEl.width(),
                    //height: elEl.height()
                    "file": "1.mp3", //占位使用
                    "primary": "flash"  //一些支持video标签的浏览器自身的bug不支持播放声音
                }, opts.jwplayerOptions));

                $('#' + playerId).addClass('player-host');

                vm.playerCore = jwplayer(playerId);
                avalon.scan(element, [vmodel].concat(vmodels));
            };
            vm.$remove = function() {
                vm.playerCore.remove();   //清理播放器
                vm.playerCore = null;
                elEl.empty();
            };
        });
        return vmodel;
    };
    widget.defaults = {
        "jwplayerOptions": {}
    };
    return avalon;
});

//From origin\asset\common.js
define('common',['avalon','util'],function(a,e){var c=this,d=document,b=c.erp;!function(e,f,g,h,i){function j(a,b){return b=b.split('.'),_.every(b,function(b){return a.hasOwnProperty(b)&&typeof a[b]==='object'?(a=a[b],!0):void 0;})?!0:!1;}function k(c,a){var b;return a=a.split('.'),_.each(a,function(a){b=c[a],c=b;}),b;}e=b.module,f=/[^, ]+/g,g=a.vmodels,h=function(){},i=c.dispatchEvent&&d.implementation.hasFeature('MutationEvents','2.0'),_.extend(a.bindingHandlers,{module:function(j,k){var c=j.value.match(f),b=j.element,d=c[0];(c[1]==='$'||!c[1])&&(c[1]=d+setTimeout('1')),j.value=c.join(',');var m=e[d];if(typeof m==='function'){k=b.vmodels||k;var o=c[2]||d;for(var r=0,n;n=k[r++];)if(n.hasOwnProperty(o)&&typeof n[o]==='object'){var p=n;break;}if(p){var l=p[o];l=l.$model||l;var q=l[d+'Id'];typeof q==='string'&&(c[1]=q);}var s=a.getWidgetData(b,c[0]);j[d+'Id']=c[1],j[d+'Options']=a.mix({},m.defaults,l||{},s),b.removeAttribute('ms-module');var i=m(b,j,k)||{};if(j.evaluator=h,b.msData||(b.msData={},b.msData['ms-module']=d),b.msData['ms-module-id']=i.$id||'',i.hasOwnProperty('$init')&&i.$init(),i.hasOwnProperty('$remove')){function t(){return b.msRetain||document.documentElement.contains(b)?void 0:(i.$remove(),b.msData={},delete g[i.$id],!1);}window.chrome?b.addEventListener('DOMNodeRemovedFromDocument',function(){setTimeout(t);}):a.tick(t);}}else k.length&&(b.vmodels=k);},widget:function(i,l){var c=i.value.match(f),b=i.element,e=c[0];(c[1]==='$'||!c[1])&&(c[1]=e+setTimeout('1')),i.value=c.join(',');var n=a.ui[e];if(typeof n==='function'){l=b.vmodels||l;var q=c[2]||e;for(var s=0,o;o=l[s++];)if(j(o,q)){var p=o;break;}if(p){var m=k(p,q);m=m.$model||m;var r=m[e+'Id'];typeof r==='string'&&(c[1]=r);}var t=a.getWidgetData(b,c[0]);i[e+'Id']=c[1],i[e+'Options']=a.mix({},n.defaults,m||{},t),b.removeAttribute('ms-widget');var d=n(b,i,l)||{};if(i.evaluator=h,b.msData['ms-widget-id']=d.$id||'',d.hasOwnProperty('$init')&&d.$init(),d.hasOwnProperty('$remove')){function u(){return b.msRetain||document.documentElement.contains(b)?void 0:(d.$remove(),b.msData={},delete g[d.$id],!1);}window.chrome?b.addEventListener('DOMNodeRemovedFromDocument',function(){setTimeout(u);}):a.tick(u);}}else l.length&&(b.vmodels=l);}}),b.module=e;}();var f=function(){var a=$('html');_.each([6,7,8,9],function(b){e.isIE(b)&&a.addClass('ie-'+b);});},g=function(){var c=0,a;$('body').on('focus','.input-text,textarea',function(){$(this).addClass('input-focus');}).on('blur','.input-text,textarea',function(){$(this).removeClass('input-focus');}).on('click','.mn-radio',function(){var a=$(this),b=a.parent();a.hasClass('mn-radio-state-readonly')||($('.mn-radio-state-selected',b).removeClass('mn-radio-state-selected'),a.addClass('mn-radio-state-selected'));}).on('click','.label-for',function(){var b=$(this),d=$('input',b),e=$('label',b),a=d.attr('id');a||(a='label-for-'+c,d.attr('id',a),c++),e.attr('for')||e.attr('for',a);}).on('focus','.input-text',function(){var c=$('#input-text-close'),e=$(this),g=e.offset();c.length||(c=$('<div id="input-text-close">&#10005;</div>'),c.appendTo('body')),c.data('input',e),clearTimeout(a),c.css({top:g.top+1,left:g.left+e.outerWidth()-32}).show();var f=b.msValueStore;f=_.reject(f,function(a){return!$.contains(d.body,a.element);}),b.msValueStore=f;}).on('blur','.input-text',function(){var b=$('#input-text-close');clearTimeout(a),a=setTimeout(function(){b.removeData('input').hide();},160);}).on('click','#input-text-close',function(){var c=$(this),d=c.data('input'),e=b.msValueStore;clearTimeout(a),_.some(e,function(a){var b,c;return a.element===d[0]?(b=a.text,c=a.vmodels,_.some(c,function(a){return a.hasOwnProperty(b)?(a[b]='',!0):void 0;}),!0):void 0;}),d.val(''),c.removeData('input').hide();});},h=function(){$('body').on('click','.force-refresh-link',function(){var b=$(this).attr('href').split('#')[1],c=a.History.getHash(window.location).slice(2);b===c&&a.router.navigate(b);});},i=function(a){f(),g(),h();var b=$('#page-wrapper'),c=$('.app-bd .app-inner');e.regRouter(b,c,function(){a&&a();});};return{setup:i};});
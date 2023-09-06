/**
 * jquery easyui Panel method [refresh] Override
 * 기존 panel의 refresh 메소드는 href(String) 값만 보낼수 있었지만
 * Override를 통해 Object 행태도 같이 보낼수 있도록 수정
 * since : 2021-04-06
 * author : 강세원
 */

$.extend($.fn.panel.methods, {
	refresh: function(jq, href){
		return jq.each(function(){
			var state = $.data(this, 'panel');
			state.isLoaded = false;
			if (href){
				if (typeof href == 'string'){
					state.options.href = href;
				} else if (typeof href == 'object'){
					//2021-04-06 refresh에 주소 및 param을 보내기 위해 수정 강세원
					$.extend(state.options, href);
				} else {
					state.options.queryParams = href;
				}
			}
			function loadData(target, params){
				var state = $.data(target, 'panel');
				var opts = state.options;
				if (param){opts.queryParams = params}
				if (!opts.href){return;}
				if (!state.isLoaded || !opts.cache){
					var param = $.extend({}, opts.queryParams);
					if (opts.onBeforeLoad.call(target, param) == false){return}
					state.isLoaded = false;
					// $(target).panel('clear');
					if (opts.loadingMessage){
						$(target).panel('clear');
						$(target).html($('<div class="panel-loading"></div>').html(opts.loadingMessage));
					}
					opts.loader.call(target, param, function(data){
						var content = opts.extractor.call(target, data);
						$(target).panel('clear');
						$(target).html(content);
						$.parser.parse($(target));
						opts.onLoad.apply(target, arguments);
						state.isLoaded = true;
					}, function(){
						opts.onLoadError.apply(target, arguments);
					});
				}
			}
			loadData(this);
		});
	}
})

/**
 * EasyUI JQuery datebox 용 날짜 formatter
 *  - EasyUI 기본형식이 mm/dd/yyyy 이기 때문에 yyyy-mm-dd 로 사용하기 위해 추가
 */
$.extend($.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
})

/**
 * EasyUI JQuery datebox 용 날짜 parser
 *  - EasyUI 기본형식이 mm/dd/yyyy 이기 때문에 yyyy-mm-dd 로 사용하기 위해 추가
 *    formatter만 변경해서는 정상적으로 동작하지 않고 parser와 같이 구성해야 함
 */
$.extend($.fn.datebox.defaults.parser = function(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
})

/**
 * EasyUI JQuery datebox용 날짜 유형 검사
 * 사용방법은 datebox 속성에 validType: ["date"]
 *
 * ex)
 *   $("#fromDate").datebox({validType: ["date"]});
 */
$.extend($.fn.validatebox.defaults.rules, {
	date: {
		validator: function(value, param){
			var pt = /^\d{4}-\d{2}-\d{2}$/;
			if (!pt.test(value)) {
				return false;
			}
			var ss = (value.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10) -1;
			var d = parseInt(ss[2],10);
			var dt = new Date(y, m, d);

			if (dt.getFullYear() == y&&dt.getMonth() == m&&dt.getDate() == d) {
				return true;
			} else {
				return false;
			}
		},
		message: paragonCmm.getLang("M.날짜만입력")
	}
})





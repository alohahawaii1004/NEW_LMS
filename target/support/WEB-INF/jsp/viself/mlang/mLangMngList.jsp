<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<script type="text/javascript" src="/js/vendor/jquery-easyui/extension/datagrid-cellediting/datagrid-cellediting.js"></script>
<section class="wrapper site-min-height">
	<h3><i class="fa fa-angle-right" id="content_header" data-term="L.다국어코드"> </i></h3>
	<div class="content-panel">
		<form name="form1" method="post">
		<table class="box">
		<tr>
			<td class="corner_lt"></td>
			<td class="border_mt"></td>
			<td class="corner_rt"></td>
		</tr>
		<tr>
			<td class="border_lm"></td>
			<td class="body">
				<table>
						<colgroup>
							<col style="width:10%" />
							<col style="width:32%" />
							<col style="width:15%" />
							<col style="width:32%" />
							<col style="width:*" />
						</colgroup>
						<tr>
							<th>코드</th>
							<td colspan="3"><input type="text" name="langCd" tabindex="1"  ID="langCd" onkeydown="MLANG.doSearch(true)" value="" data-type="search" class="text width_max" style="width:98.3%;" /></td>
							<td rowspan="4" class="verticalContainer">
									<span class="ui_btn medium icon"><i class="fa fa-search" onclick="MLANG.doSearch();"><a href="javascript:void(0);" style="width:31px;">검색</a></i></span>
									<span class="ui_btn medium icon"><i class="fa fa-minus" onclick="MLANG.doInit(document.form1)"><a href="javascript:void(0)"  >리셋</a></i></span>
							</td>
						</tr>
						<tr>
							<th>KO</th>
							<td><input type="text" class="text width_max" name="ko" id="ko" tabindex="2" onkeydown="MLANG.doSearch(true)" data-type="search"/></td>
							<th>EN</th>
							<td><input type="text" class="text width_max" name="en" id="en" tabindex="3"  onkeydown="MLANG.doSearch(true)" data-type="search"/></td>
						</tr>
						<tr>
							<th>JP</th>
							<td><input type="text" class="text width_max" name="ja" id="ja" onkeydown="MLANG.doSearch(true)" data-type="search"/></td>
							<th>CN</th>
							<td><input type="text" class="text width_max" name="zh" id="zh" onkeydown="MLANG.doSearch(true)" data-type="search"/></td>
						</tr>
						<tr>
							<th>Type</th>
							<td colspan="3" style="padding-top:5px;">
								<input type="radio" name="TYPE" id="TYPE" value=""  data-type="search"  onClick="MLANG.doSearch()" checked/>전체
								<input type="radio" name="TYPE" id="TYPE" value="L"  data-type="search" onClick="MLANG.doSearch()" />라벨
								<input type="radio" name="TYPE" id="TYPE" value="M" data-type="search"  onClick="MLANG.doSearch()" />메시지
								<input type="radio" name="TYPE" id="TYPE" value="B"  data-type="search" onClick="MLANG.doSearch()" />버튼
								<input type="radio" name="TYPE" id="TYPE" value="R"  data-type="search" onClick="MLANG.doSearch()" />라디오
								<input type="radio" name="TYPE" id="TYPE" value="D"  data-type="search" onClick="MLANG.doSearch()" />문서
								<input type="radio" name="TYPE" id="TYPE" value="C"  data-type="search" onClick="MLANG.doSearch()" />코드
							</td>
						</tr>
				</table>
				</td>
			<td class="border_rm"></td>
		</tr>
		<tr>
			<td class="corner_lb"></td>
			<td class="border_mb"></td>
			<td class="corner_rb"></td>
		</tr>
		</table>
		</form>
		<div class="buttonlist">
				<div class="left">
					<select name="ADD_TYPE" id="ADD_TYPE" style="border:1px solid #CCC9E1; padding:2px; width:120px;">
						<option value="l">라벨</option>
						<option value="M">메시지</option>
						<option value="B">버튼</option>
						<option value="R">라디오</option>
						<option value="D">문서</option>
						<option value="C">코드</option>
					</select>
					<input type="text" class="text" id="ADD_CODE" style="margin-top:1px; padding:3px;" size="40" onkeydown="MLANG.doProc(true)"/>
					<span class="ui_btn medium icon"><i class="fa fa-plus" onclick="void(0);"><a href="javascript:void(0);" onClick="MLANG.doProc();" data-term="B.ADD"></a></i></span>
				</div>
				<div class="right">
<%-- 					<span class="ui_btn medium icon"><i class="fa fa-plus" onclick="void(0);"><a href="javascript:void(0);" onClick="doSysAttch();" data-term="B.ADD" data-term="B.시스템적용"></a></i></span> --%>
					<span class="ui_btn medium icon" id="openPopup" style="display:none;"><i class="fa fa-list-ul" onClick="paragonCmm.openWindow('/viself/mlang/mLangMngList.popup',1024,600,'Lang List',true,true);"><a href="javascript:void(0);">POPUP</a></i></span>
				</div>
			</div>
			<table id="listTable" class="easyui-datagrid"
				data-options="singleSelect:true,
							  striped:true,
							  fitColumns:false,
							  rownumbers:true,
							  multiSort:true,
							  pagination:true,
							  pageList:[10,50,100,200],
							  pagePosition:'bottom',
							  selectOnCheck:false,
							  checkOnSelect:false,
							  url:'',
							  method:'post'"
				style="height:auto">
				<thead>
				<tr>
					<th data-options="field:'langUid',width:0,hidden:true"></th>
					<th data-options="field:'langCd',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">코드</th>
					<th data-options="field:'ko',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">KO</th>
					<th data-options="field:'en',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">EN</th>
					<th data-options="field:'ja',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">JA</th>
					<th data-options="field:'zh',width:300,halign:'CENTER',align:'LEFT',editor:'text',sortable:true">ZH</th>
				</tr>
			</table>
		</div>
</section>
<script>
	function Mlang(){


		var doSearch = function(isCheckEnter){
			if (isCheckEnter && event.keyCode != 13) {
				return;
			}
			$("#listTable").datagrid("load", paragonCmm.getSearchQueryParams());
		}
		var modifyLang = function(row,index,changes){
			changes["langUid"] = row["langUid"];
			$.ajax({
				url: paragonCmm.getUrl("/viself/mlang/MLangMng/updateData/json")
				, type: "POST"
				, dataType: "json"
				, data: changes
			})
			.done(function(data) {

				if(Object.keys(data).length > 0){
					doSearch();
				}else{
					alert("동일한코드명이존재합니다");
					reject();
				}
		    })
		    .fail(function() {
		    });
		}
		var doInit = function(frm){
			frm.reset();
		}
		var doProc = function(isCheckEnter){
			if (isCheckEnter && event.keyCode != 13) {
				return;
			}
			var msg = paragonCmm.getLang("M.저장내용");
			confirm(msg, function(r){
				if($("#ADD_CODE").val() != "" && r){
					var data = {};
					data["langCd"] = $("#ADD_TYPE").val() + "." + $("#ADD_CODE").val()
					$.ajax({
						url: paragonCmm.getUrl("/viself/mlang/MLangMng/saveData/json")
						, type: "POST"
						, dataType: "json"
						, data: data
					})
					.done(function(data) {
						if(Object.keys(data).length > 0){
							alert("처리되었습니다.");

							$("#ko").val("");
							$("#en").val("");
							$("#zh").val("");
							$("#ja").val("");
							$("#langCd").val($("#ADD_CODE").val());
							$("#ADD_CODE").val("");
							$("input:radio[name='TYPE']").eq(0).prop("checked",true);
							doSearch();
						}else{
							alert(paragonCmm.getLang("M.동일한코드가존재합니다확인해주세요"));
						}

				    })
				    .fail(function() {
				    	alert(paragonCmm.getLang("M.처리중_오류발생","L.수정"));
				    });
				}
			});
		}
		var reject = function(){
		    $('#listTable').datagrid('rejectChanges');
		}

		var loadGrid = function(){
			$('#listTable').datagrid({
				url:'/viself/mlang/MLangMng/list/json',
				loadFilter:paragonCmm.easyuiLoadFilter,
				onBeforeLoad:function() { paragonCmm.showBackDrop(); },
				onLoadSuccess:function(json) {
					paragonCmm.hideBackDrop();
				},
				onLoadError:function() { paragonCmm.hideBackDrop(); },
				 onAfterEdit:function(index,row, changes){
				    	if(Object.keys(changes).length > 0){//객체의 키의 갯수(IE:8은 동작안함)
				    		var message = paragonCmm.getLang("M.값_변경_저장");
				    		confirm(message, function(r){
				    			if(r){
						    		modifyLang(row,index,changes );
					    		}else{
					    			reject()
					    		}
				    		})
				    	}
				    }
			});

			$('#listTable').datagrid('enableCellEditing').datagrid('gotoCell', {
			    index: 0,
			    field: 'langCd'

			});
		}
		var init = function(){
			$('#listTabs-LIST').css('width','100%');
			loadGrid();
			if(opener){
				$(window).bind('resize', $('.datagrid-f').datagrid('resize'));
			}else{
				$("#openPopup").show();
			}
		}
		return{
	    	init : init,
	    	loadGrid:loadGrid,
	    	doProc:doProc,
	    	doSearch:doSearch
	    }
	}
	var MLANG = new Mlang();
	MLANG.init();
</script>
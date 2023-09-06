<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/js/vendor/jquery-easyui/extension/datagrid-cellediting/datagrid-cellediting.js"></script>
<div class="content-panel">
<form id="excelUploadForm1" method="post" class="uploadArea">
	<input type="hidden" name="relUid" id="relUid" data-type='search' value=""/>
	<input type="hidden" name="reUpload" id="reUpload" value="N"/>
	<table class="basic">
		<tr>
			<th class="th2">
					Excel Upload
					<span class="ui_btn small icon" id="formDownloadBtn"><i class="fa fa-download"><a href="javascript:void(0);" data-term="L.양식다운로드"></a></i></span>
			</th>
			<td class="thd" id="excelUploadFileArea"></td>
		</tr>
	</table>
</form>
<div class="buttonlist">
     <div class = "right">
    	<span class="ui_btn small icon listArea" style="display:none;" id="reLoadBtn"><i class="fa fa-redo"><a href="javascript:void(0);">RELOAD</a></i></span>
    	<span class="ui_btn small icon uploadArea" id="uploadProcBtn"><i class="fa fa-check"><a href="javascript:void(0);">UPLOAD</a></i></span>
     </div>
</div>
<table id="allInvstTmpTable" class="listArea" style="width:auto;height:auto; display: none;">
	<thead>
	<tr>
		<th data-options="field:'tempUid',width:0,hidden:true"></th>
		<!-- <th data-options="field:'CK',width:10,halign:'center',align:'center',checkbox:true"></th> -->
		<th data-options="field:'year',width:80,halign:'CENTER',align:'LEFT',styler:function(val,row,idx){
			if(typeof row.year != 'stirng' && row.year == null){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">연도</th>
		<th data-options="field:'corpNm',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.corpNm != 'stirng' && row.corpNm == null){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">법인</th>
		<th data-options="field:'prjNm',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.prjNm != 'stirng' && row.prjNm == null){  return 'background-color:red;'}
		}">프로젝트명</th>
		<th data-options="field:'reqDeptGrp',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.reqDeptGrpCnt != 'stirng' && row.reqDeptGrpCnt == '0'){  return 'background-color:red;'}
		}">발의부서실</th>
		<th data-options="field:'reqDeptTem',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.reqDeptTemCnt != 'stirng' && row.reqDeptTemCnt == '0'){  return 'background-color:red;'}
		}">발의부서팀</th>
		<th data-options="field:'tpNm1',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.tp1Cnt != 'stirng' && row.tp1Cnt == '0'){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">분류1</th>
		<th data-options="field:'tpNm2',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.tp2Cnt != 'stirng' && row.tp2Cnt == '0'){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">분류2</th>
		<th data-options="field:'tpNm3',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.tp3Cnt != 'stirng' && row.tp3Cnt == '0'){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">분류3</th>
		<th data-options="field:'tpNm4',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.tp4Cnt != 'stirng' && row.tp4Cnt == '0'){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">분류4</th>
		<th data-options="field:'tpNm5',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.tp5Cnt != 'stirng' && row.tp5Cnt == '0'){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">분류5</th>
		<th data-options="field:'invstItm',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.invstItm != 'stirng' && row.invstItm == null){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">투자품목</th>
		<th data-options="field:'cucy',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.cucyCnt != 'stirng' && row.cucyCnt == '0'){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">통화</th>
		<th data-options="field:'vol',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.vol != 'stirng' && row.vol == null){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}, formatter:formatPrice">수량</th>
		<th data-options="field:'unit',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true,styler:function(val,row,idx){
			if(typeof row.unitCnt != 'stirng' && row.unitCnt == '0'){ $('#reUpload').val('Y'); return 'background-color:red;'}
		}">단위</th>
		<th data-options="field:'price',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.단가"></span></th>
		<th data-options="field:'totInvst',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.총투자금액"></span></th>
		<th data-options="field:'prjEdDte',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true"><span data-term="L.종료시점"></span></th>
		<th data-options="field:'fwdYn',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true"><span data-term="L.이월여부"></span></th>
		<th data-options="field:'yearly',width:100,halign:'CENTER',align:'LEFT',sortable:true, formatter:formatPrice"><span data-term="L.연간"></span></th>
		<th data-options="field:'month1',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.1월"></span></th>
		<th data-options="field:'month2',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.2월"></span></th>
		<th data-options="field:'month3',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.3월"></span></th>
		<th data-options="field:'month4',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.4월"></span></th>
		<th data-options="field:'month5',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.5월"></span></th>
		<th data-options="field:'month6',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.6월"></span></th>
		<th data-options="field:'month7',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.7월"></span></th>
		<th data-options="field:'month8',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.8월"></span></th>
		<th data-options="field:'month9',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.9월"></span></th>
		<th data-options="field:'month10',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.10월"></span></th>
		<th data-options="field:'month11',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.11월"></span></th>
		<th data-options="field:'month12',width:100,halign:'CENTER',align:'LEFT',editor:'text',sortable:true, formatter:formatPrice"><span data-term="L.12월"></span></th>
	</tr>
</table>
<div class="buttonlist">
     <div class = "right">
    	<span class="ui_btn medium icon listArea" style="display:none;" id="confirmBtn"><i class="fa fa-check"><a href="javascript:void(0);" data-term="L.적용"></a></i></span>
     </div>
</div>
</div>
<script>

//그리드 숫자값 천단위 콤마 추카
function formatPrice(val){
	
	if(val != null){
		var retVal = val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
	
    return retVal;
}

"use strict";
function ExcelUpload(){
	var EXCEL_UPLOAD_URL = "/ims/allinvst/excelUpload/json";
	var TMP_GRID_URL = "/ims/allinvst/AllInvst/listTmp/json";
	var TMP_CONFIRM_URL = "/ims/allinvst/doConfirmTmp/json";

	var uploadForm 		= "#excelUploadForm1";
	var uploadArea 		= ".uploadArea";
	var listArea 		= ".listArea";
	var fileAreaId 		= "excelUploadFileArea";
	var formDownloadBtn	= "#formDownloadBtn";
	var reLoadBtn 		= "#reLoadBtn";
	var uploadProcBtn 	= "#uploadProcBtn";
	var confirmBtn 		= "#confirmBtn";
	var tmpGrid 		= "#allInvstTmpTable";
	var $excelUploadModal = $('#excelUploadModal');
	var $grid = $('#allInvstTmpTable');
	var callbackFunc ;
	
	var doConfirm = function(){
		
		var msg = "적용하시면 증가된 차수로 투자계획등록에 적용됩니다. \n 계속하시겠습니까?";
		var failMsg = "입력되지 않은 사항이 있습니다. \n 다시 입력하여 업로드해주세요.";

		//validation
		var reUpload = $('#reUpload').val();
		if(reUpload == 'Y'){
			alert(failMsg);
			return;
		}
		
		confirm(msg , function(r){
			
			var data = $(uploadForm).serializeJSON();
						
				paragonCmm.submitAjax(TMP_CONFIRM_URL,data, function(json){
					
					if(json.errYn === "E"){
						//-- 오류처리
						alert(json.msg);
						return false;
					}
					if(typeof callbackFunc === "function"){
						callbackFunc();				//-- 콜백함수 호출
						$excelUploadModal.window('close');
					}
				
				});
				
		});
	}
	
	var doUpload = function(){
		var data = $(uploadForm).serializeJSON();

		paragonCmm.submitAjax(EXCEL_UPLOAD_URL,data, function(json){
			if(json.errYn === "E"){
				//-- 오류처리
				alert(json.msg);
				return false;
			}
			loadGrid();
		});
	}
	var initData = function(json){

	}
	var reject = function(){
	    $(tmpGrid).datagrid('rejectChanges');
	}
	var modifyLang = function(row,index,changes){
		var key = "";
		$.each(changes,function(keys, vals){
			key = keys;
		});
		if(key.startsWith("month")){
			var sum = 0;
			$.each(row,function(keys, vals){
				if(keys.startsWith("month")){
					sum += parseFloat(("" == vals||null == vals?0:vals));
				}
			});
			changes["yearly"] = ""+sum;
		}
		changes["tempUid"] = row["tempUid"];
		$.ajax({
			url: "/ims/allinvst/AllInvst/update/json"
			, type: "POST"
			, dataType: "json"
			, data: changes
		})
		.done(function(data) {

			if(Object.keys(data).length > 0){
				reLoadGrid();
			}else{
				alert(paragonCmm.getLang("M.동일한코드명"));
				reject();
			}
	    })
	    .fail(function() {
	    });
	}
	var reLoadGrid = function(){
		$(tmpGrid).datagrid("load", {relUid:$("#relUid").val()});
	}
	var loadGrid = function(){
		$(uploadArea).hide();
		$(listArea).show();
		$(tmpGrid).datagrid({
			url:TMP_GRID_URL,
			singleSelect:true,
			striped:true,
			nowrap:false,
			fitColumns:false,
			rownumbers:true,
			multiSort:true,
			selectOnCheck:false,
			checkOnSelect:false,
			method:'post',
			queryParams:paragonCmm.getSearchQueryParams($(uploadForm)),
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
			},
			onDblClickRow:function(index, row){
				alert(row.cucyCnt);
			}
		});
		$(tmpGrid).datagrid('enableCellEditing').datagrid('gotoCell', {
		    index: 0,
		    field: 'corpNm'

		});
	}
	var loadForm = function(){
		var relUid = paragonCmm.getRandomUUID();
		$("#relUid").val(relUid);

		var options = {}
		options.relUid = relUid;			//-- 관계 키 UID
		options.fileTpCd = "CMM/CMM";		//-- 파일 유형 코드
		options.defaultRelUid = "";				//-- 기본 로드 첨부파일(수정시)
		htmlUtils.loadFileHtml(fileAreaId, options);	//-- 첨부파일 로드
	}
	var attchmentEvent = function(){
		$(uploadProcBtn).off();
		$(uploadProcBtn).on("click",function(){
			doUpload();
		});
		$(formDownloadBtn).off();
		$(formDownloadBtn).on("click",function(){
			htmlUtils.formDownload("투자계획등록_양식.xlsx");
		});
		$(reLoadBtn).off();
		$(reLoadBtn).on("click",function(){
			reLoadGrid();
		});
		$(confirmBtn).off();
		$(confirmBtn).on("click",function(){
			doConfirm();
		});
	}
	var init = function(initFunc, callFunc){
		attchmentEvent();
		loadForm();									//-- 양식 로드

		if(typeof initFunc === "function"){
			initData(initFunc());					//-- 초기 데이터 및 설정
		}
		if(typeof callFunc === "function"){
			callbackFunc = callFunc;				//-- 콜백함수 설정
		}

	}
	return{
    	init : init
    }
}
var excelUpload = new ExcelUpload();
</script>
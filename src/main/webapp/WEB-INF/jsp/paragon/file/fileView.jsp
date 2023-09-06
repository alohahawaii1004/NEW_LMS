<%@page import="java.util.List"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.vertexid.commons.utils.*" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/include/includeTagLibs.jsp" %>
<%
    //-- 검색값 셋팅
    String relUid						= StringUtils.defaultString((String)request.getAttribute("relUid") , null);
    String defaultRelUid				= StringUtils.defaultString((String)request.getAttribute("defaultRelUid"), null);
    String fileTpCd						= StringUtils.defaultString((String)request.getAttribute("fileTpCd"), null);
    String requiredYn                   = StringUtils.defaultString((String)request.getAttribute("requiredYn"), null);
    String maxFileSize                  = StringUtils.defaultString((String)request.getAttribute("maxFileSize"), null);
    String fileFilter                   = StringUtils.defaultString((String)request.getAttribute("allowExt"), null);
    String fileRFilter                  = StringUtils.defaultString((String)request.getAttribute("denyExt"), null);
    String maxFileCount                 = StringUtils.defaultString((String)request.getAttribute("maxFileCount"), null);

    String singleClass              	= (!"1".equals(maxFileCount) ? "" : "single");

    String pdfConvYn					= StringUtils.defaultString((String)request.getAttribute("pdfConvYn"));
//     singleClass = ""; //REVIEW :  싱글/멀티 첨부 상관없이 멀티로만 첨부하기 위해 값을 없애 놓았음

    //-- 결과값 셋팅
	List<Object> defaultViewResult    = (List<Object>)request.getAttribute("defaultViewResult");
	List<Object> viewResult           = (List<Object>)request.getAttribute("viewResult");

    String isNew = "false";

    if(!relUid.equals(defaultRelUid)){
    	isNew = "true";
    }

    if(viewResult != null && viewResult.size() > 0){

    }else{
    	viewResult = defaultViewResult;
    }
    //-- 기타값 셋팅
    String ctrlUuid             = IDUtils.getRandomUUID(); //컨트롤을 식별할 고유 아이디 생성!
    //-- pdf 변환 boolean
    boolean pdfConv = "Y".equals(pdfConvYn)?true:false;

    String userAgent = request.getHeader("User-Agent");
    String height = "115px";
    if("1".equals(maxFileCount)){
    	height = "35px";
    }
    String margin = "40px";
    if("1".equals(maxFileCount)){
    	margin = "0px";
    }

    Map<String, Object> optionMap = new HashMap<>();
    optionMap.put("relUid", relUid);
    optionMap.put("fileTpCd", fileTpCd);
%>
<div id="fileView_<%=ctrlUuid%>" class="row">
    <ul style="list-style: none;" class="col-sm-11 align-middle ul_file_list">
        <%
        if(viewResult != null && viewResult.size() > 0){
            for(int i=0; i <  viewResult.size();i++){
                ParamMap rowMap = (ParamMap)viewResult.get(i);
                String byteNm	  = "KB";
                float fileSize = Integer.parseInt(rowMap.getString("fileSize")) / 1024;
                if(1024 < fileSize) {
                    fileSize = fileSize / 1024;
                    byteNm = "MB";
                }
        %>
        <li>
            <span id='<%=rowMap.getString("atchUid") %>' class='file_Element_<%=ctrlUuid%> p-1' >
	  	        <i class="fa fa-save" style="color:#666"></i>
	  	        <a href="javascript:htmlUtils.fileDownload('<%=rowMap.getString("atchUid") %>')" title="<%=rowMap.getString("fileNm") %>">&nbsp;<%=rowMap.getString("fileNm") %></a>
            </span>
        </li>
        <%
            } // end of for
        }
        %>
    </ul>
    <span class="col-sm-1 align-middle text-right">
        <%
            //        if (viewResult != null && viewResult.size() > 1) {
            if (viewResult != null && !viewResult.isEmpty()) {
        %>
        <button type="button" class="btn btn-outline-primary btn-xs" id="allDnBtn_<%=ctrlUuid%>" data-dn-info='<%= JsonUtil.getJsonStringFromMap(optionMap)%>'
                onclick="htmlUtils.multiDownload({objId:'allDnBtn_<%=ctrlUuid%>'})"><i class="fa fa-download"></i></button>
        <%
            }
        %>
    </span>
</div>

package com.vertexid.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ExcelUpload {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private int keyIdx = -1;
	/**
     * logger
	 * @throws Exception
     */

	public Map<String, Object> excelUpload(ParamMap<String, Object> options) {

		Map<String, Object> outParams = new HashMap<String, Object>();
		String fullPath 			= options.getString("fullPath");
		String startSheetIdxStr 	= options.getString("startSheetIdx");	//-- 시작 시트 인덱스
		String startHeaderIdxStr 	= options.getString("startHeaderIdx");	//-- 시작 헤더 인덱스
		String startColumnIdxStr 	= options.getString("startColumnIdx");	//-- 시작 column id 인덱스
		String startRowIdxStr 		= options.getString("startRowIdx");			//-- 시작 row 인덱스
		int startSheetIdx = StringUtils.isNumeric(startSheetIdxStr)?Integer.parseInt(startSheetIdxStr): 0;
		int startHeaderIdx = StringUtils.isNumeric(startHeaderIdxStr)?Integer.parseInt(startHeaderIdxStr): 0;
		int startColumnIdx = StringUtils.isNumeric(startColumnIdxStr)?Integer.parseInt(startColumnIdxStr): 1;
		int startRowIdx = StringUtils.isNumeric(startRowIdxStr)?Integer.parseInt(startRowIdxStr): 5;

		Logger log = LoggerFactory.getLogger(ExcelUpload.class);
		//파일가져오기
		InputStream is;
		try {
			is = new FileInputStream(new File(fullPath));
			//파일을 담을 Excel
			List<String> colNames =  new ArrayList<String>();
			List<String> colIds =  new ArrayList<String>();
			List<Object> colModel = new ArrayList<Object>();
			DataFormatter formatter = new DataFormatter();
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(is);
			} catch (IOException e) {
				log.error(e.getMessage());
			}

			int rowIdx=0;
			int colIdx=0;
			XSSFSheet sheet= workbook.getSheetAt(startSheetIdx);
			//Row Cnt
			int rows=sheet.getPhysicalNumberOfRows();
			//Row 컬럼명
			XSSFRow colNmRow = sheet.getRow(startHeaderIdx);
			if(colNmRow !=null){
				//셀의 수
				int cells = colNmRow.getPhysicalNumberOfCells();

				for(colIdx=0;colIdx <= cells;colIdx++){
					XSSFCell cell = colNmRow.getCell(colIdx);
					String value="";
					if(cell==null){
						continue;
					}else{
						value= formatter.formatCellValue(cell)+"";

					}
					colNames.add(value);
				}
			}
			log.debug("컬럼 한글명 :"+colNames);

			//--컬럼 ID 추출
			XSSFRow colIdRow =sheet.getRow(startColumnIdx);
			if(colIdRow !=null){
				//셀의 수
				int cells=colIdRow.getPhysicalNumberOfCells();
				for(colIdx=0 ; colIdx <= cells ; colIdx++){
					Map<String, Object> colDr = new ParamMap<String, Object>();
					XSSFCell cell = colIdRow.getCell(colIdx);
					String value="";
					if(cell==null){
						continue;
					}else{
						value = formatter.formatCellValue(cell)+"";
					}
					colIds.add(value);
					colDr.put("name", value);
					colDr.put("editable", "true");
					colDr.put("align", "center");
					colModel.add(colDr);
				}
			}
			log.debug("컬럼 명문명 :"+colModel);

			outParams.put("colModel",colModel);
			outParams.put("colNames",colNames);

			List<Object> dt = new ArrayList<>();

			for(rowIdx=startRowIdx ; rowIdx < rows ; rowIdx++){
				//행을읽는다
				XSSFRow row=sheet.getRow(rowIdx);
				if(row !=null){
					//셀의 수
					int cells=row.getPhysicalNumberOfCells();
					boolean isRow = true;
					ParamMap<String, Object> dr = new ParamMap<String, Object>();
					for(colIdx=0;colIdx<=cells;colIdx++){
						//셀값을 읽는다
						XSSFCell cell=row.getCell(colIdx);
						String value="";
						//셀이 빈값일경우를 위한 널체크
						if(cell==null){
							continue;
						}else{
							value= cellValue(row.getCell(colIdx));
						}
						if(colIdx == 0 && StringUtils.isBlank(value)) {
							isRow = false;
							break;
						}
						dr.put(colIds.get(colIdx), value);
					}
					if(!dr.isEmpty()) {		//-- 뛰어 넘기 하지 않았을때만 add
						dt.add(dr);
					}
				}
			}
			log.debug("rowData : "+ dt);
			outParams.put("rowData", dt);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		log.debug("outParams : "+ outParams);

		return outParams;
	}

	private String cellValue(XSSFCell cell) {
		Logger log = LoggerFactory.getLogger(ExcelUpload.class);
        String value = "";
        if (cell != null){
            switch (cell.getCellType()) { //cell 타입에 따른 데이타 저장
            case FORMULA:
                value = cell.getCellFormula();

                log.debug("Formula is " + cell.getCellFormula());
                switch(cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                    	//value = cell.getNumericCellValue()+"";
                    	//log.debug("Last evaluated as: " + cell.getNumericCellValue());
                    	cell.setCellType(CellType.STRING );	//숫자가 지수포 표현될때 String으로 변환
                    	value = cell.getStringCellValue()+"";
                    	log.debug("Last evaluated as \"" + cell.getRichStringCellValue() + "\"");
                        break;
                    case STRING:
                    	value = cell.getRichStringCellValue()+"";
                    	log.debug("Last evaluated as \"" + cell.getRichStringCellValue() + "\"");
                        break;
                }

                break;
            case  NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    //you should change this to your application date format
                    SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    value = "" + objSimpleDateFormat.format(cell.getDateCellValue());
                } else {
                    value = ("" + String.format("%.0f", new Double(cell.getNumericCellValue()))).replaceAll(",", "");;
                }
                break;
            case STRING:
                value = "" + cell.getStringCellValue();
                break;
            case BLANK:
                //value=""+cell.getBooleanCellValue();
                value = "";
                break;
            case ERROR:
                value = "" + cell.getErrorCellValue();
                break;
            default:
            }
        }

        return value.trim();
    }



	/**
     * 연차유지를 위한 엑셀업로드 소스
	 * @throws Exception
     */

	public Map<String, Object> rstExcelUpload(ParamMap<String, Object> options) {

		Map<String, Object> outParams = new HashMap<String, Object>();
		String fullPath 			= options.getString("fullPath");
		String startSheetIdxStr 	= options.getString("startSheetIdx");	//-- 시작 시트 인덱스
		String startHeaderIdxStr 	= options.getString("startHeaderIdx");	//-- 시작 헤더 인덱스
		String startRowIdxStr 		= options.getString("startRowIdx");			//-- 시작 row 인덱스
		int startSheetIdx = StringUtils.isNumeric(startSheetIdxStr)?Integer.parseInt(startSheetIdxStr): 0;
		int startHeaderIdx = StringUtils.isNumeric(startHeaderIdxStr)?Integer.parseInt(startHeaderIdxStr): 0;
		int startRowIdx = StringUtils.isNumeric(startRowIdxStr)?Integer.parseInt(startRowIdxStr): 1;

		Logger log = LoggerFactory.getLogger(ExcelUpload.class);
		//파일가져오기
		InputStream is;
		try {
			is = new FileInputStream(new File(fullPath));
			//파일을 담을 Excel
			List<String> colNames =  new ArrayList<String>();
			List<Object> colModel = new ArrayList<Object>();
			DataFormatter formatter = new DataFormatter();
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(is);
			} catch (IOException e) {
				log.error(e.getMessage());
			}

			int rowIdx=0;
			int colIdx=0;
			XSSFSheet sheet= workbook.getSheetAt(startSheetIdx);
			//Row Cnt
			int rows=sheet.getPhysicalNumberOfRows();

			//Row 컬럼명
			XSSFRow colNmRow = sheet.getRow(startHeaderIdx);
			if(colNmRow !=null){
				//셀의 수
				int cells = colNmRow.getPhysicalNumberOfCells();

				for(colIdx=0;colIdx <= cells;colIdx++){
					XSSFCell cell = colNmRow.getCell(colIdx);
					String value="";
					if(cell==null){
						continue;
					}else{
						value= formatter.formatCellValue(cell)+"";

					}
					colNames.add(value);
				}
			}
			log.debug("컬럼 한글명 :"+colNames);
			
			outParams.put("colNames",colNames);

			List<Object> dt = new ArrayList<>();

			for(rowIdx=startRowIdx ; rowIdx < rows ; rowIdx++){
				//행을읽는다
				XSSFRow row=sheet.getRow(rowIdx);
				if(row !=null){
					//셀의 수
					int cells=row.getPhysicalNumberOfCells();
					boolean isRow = true;
					ParamMap<String, Object> dr = new ParamMap<String, Object>();
					for(colIdx=0;colIdx<=cells;colIdx++){
						//셀값을 읽는다
						XSSFCell cell=row.getCell(colIdx);
						String value="";
						//셀이 빈값일경우를 위한 널체크
						if(cell==null){
							value= "";
							//continue;
							
						}else{
							value= cellValue(row.getCell(colIdx));
						}
						if(colIdx == 0 && StringUtils.isBlank(value)) {
							isRow = false;
							break;
						}
						dr.put(Integer.toString(colIdx), value);
					}
					if(!dr.isEmpty()) {		//-- 뛰어 넘기 하지 않았을때만 add
						dt.add(dr);
					}
				}
			}
			log.debug("컬럼 모델 :"+colModel);
			log.debug("rowData : "+ dt);
			outParams.put("rowData", dt);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		log.debug("outParams : "+ outParams);

		return outParams;
	}

	/**
	 * 업로드한 엑셀을 읽어 들인다.
	 * @param options
	 * @return
	 */
	@SuppressWarnings("resource")
	public Map<String, Object> getExcelUploadList(ParamMap<String, Object> options) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String fullPath 			= options.getString("fullPath");

		// Header List
		Map<String, Object> hederMap = new HashMap<String, Object>();
		List<String> headerNms =  new ArrayList<String>();

		//파일가져오기
		InputStream is;
		try {
			is = new FileInputStream(new File(fullPath));
		} catch (FileNotFoundException e1) {
			log.error("Error.Input parameters............." + e1.getMessage());
			throw new RuntimeException("Excel file not found!!!");
		}

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException("Excel file read error!!!");
		}

		XSSFSheet sheet= workbook.getSheetAt(0);

		//-- 데이터가 시작되는 시작 Row
		int startRowIdx = -1;

		//-- header구하기
		hederMap = getHeaderNms(sheet, (Map<String, String>)options.get("headerNms"));
		startRowIdx = (int) hederMap.get("startRowIdx");
		headerNms = (List<String>) hederMap.get("headerNms");

		log.debug("컬럼 한글명 :"+headerNms);
		rtnMap.put("headerNms",headerNms);

		List<Object> dataRows = new ArrayList<>();

		//-- poi에서 인식한 row갯수
		int rows=sheet.getPhysicalNumberOfRows();
		for (int rowIdx=startRowIdx ; rowIdx < rows ; rowIdx++) {

			XSSFRow excelRow=sheet.getRow(rowIdx);
			if(excelRow !=null){
				String keyVal = cellValue(excelRow.getCell(keyIdx));
				//업로드한 엑셀의 키번호가 없을 경우 이후 row는 읽지 않음.
				if(StringUtils.isEmpty(keyVal)){
					break;
				}
				ParamMap<String, Object> row = new ParamMap<String, Object>();
				for(int colIdx=0;colIdx < headerNms.size();colIdx++){	//-- 구히진 헤더 갯수만큼 반복진행
					String headerNm = headerNms.get(colIdx);
					if(StringUtils.isNotBlank(headerNm)) {
						XSSFCell cell=excelRow.getCell(colIdx);
						String value="";
						value= cellValue(cell);
						row.put(headerNm, value);
					}
				}
				if(!row.isEmpty()) {		//-- 뛰어 넘기 하지 않았을때만 add
					dataRows.add(row);
				}
			}
		}

		rtnMap.put("rows",dataRows);

		return rtnMap;
	}

	private Map<String, Object> getHeaderNms(XSSFSheet sheet, Map<String, String> headerNms) {
		Map<String, Object> hederMap = new HashMap<String, Object>();
		List<String> colNames =  new ArrayList<String>();
		DataFormatter formatter = new DataFormatter();
		String keyValue = headerNms.get("keyValue");
		int colIdx=0;
		int startHeaderIdx = -1;


		//-- 헤더시작 row 구하기 (두번째 줄까지 확인.)
		for(int i=0; i < 2;i++) {
			XSSFRow tmpRow = sheet.getRow(i);
			if(tmpRow != null){
				int cells = tmpRow.getPhysicalNumberOfCells();
				for(colIdx=0;colIdx <= cells;colIdx++){
					XSSFCell fstCell = tmpRow.getCell(colIdx);
					if(fstCell==null){
						continue;
					}else {
						String val = (formatter.formatCellValue(fstCell)+"").trim();
						if(headerNms.containsKey(val)) {
							startHeaderIdx = i;
							break;
						}
					}

				}

			}

			if(startHeaderIdx < 0) {
				break;
			}

		}
		if(startHeaderIdx < 0) {
			throw new RuntimeException("Header 컬럼이 잘못 되었습니다");
		}
		hederMap.put("startRowIdx", startHeaderIdx);

		XSSFRow colNmRow = sheet.getRow(startHeaderIdx);
		if(colNmRow !=null){
			//셀의 수
			int cells = colNmRow.getPhysicalNumberOfCells();

			for(colIdx=0;colIdx <= cells;colIdx++){
				XSSFCell cell = colNmRow.getCell(colIdx);
				String value="";
				if(cell==null){	//-- 컬럼이 빈값이지만 값이 있다고 판단하여 무한대로 컬럼읽어 들임 문제 방지를 위해 코딩.
					break;
				}else{
					String needChg = (formatter.formatCellValue(cell)+"").trim();
					if(needChg != null || !needChg.equals("")) {
						if(headerNms.containsKey(needChg)) {
							value = headerNms.get(needChg);
						}
						if(keyValue.equals(value)) {	//-- 키 인덱스 구하기
							keyIdx = colIdx;
						}
					}

				}
				colNames.add(value);	//-- 빈값도 List에 집어 넣어 추후 Index가 꼬이지 않게 함.
			}
		}
		hederMap.put("headerNms", colNames);
		return hederMap;
	}

}

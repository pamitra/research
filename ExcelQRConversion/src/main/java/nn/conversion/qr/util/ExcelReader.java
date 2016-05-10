package nn.conversion.qr.util;

import java.awt.print.Book;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;

import nn.conversion.qr.model.Eatery;
import nn.conversion.qr.model.NNAlumniMember;

public class ExcelReader {
	private String headerdetails;

	public List<NNAlumniMember> readBooksFromExcelFileAsMembers(String excelFilePath) throws IOException {
	    List<NNAlumniMember> listMembers = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = ExcelFactory.getWorkbook(inputStream, excelFilePath);
	    Sheet nnSheet = workbook.getSheetAt(1);
	    Iterator<Row> iterator = nnSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        NNAlumniMember member = new NNAlumniMember();
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	 
	            switch (columnIndex) {
	            case 0:
	            	member.setName((String) getCellValue(nextCell));
	                break;
	            case 1:
	            	member.setBatch((String) getCellValue(nextCell));
	                break;
	            case 2:
	            	member.setSerialNo((String) getCellValue(nextCell));
	            	break;

	            }
	 
	 
	        }
	        listMembers.add(member);
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return listMembers;
	}
	
	
	public List<String> readBooksFromExcelFileAsString(String excelFilePath,int sheetNo) throws IOException {
	    List<String> listMembers = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
//	    headerdetails = new String();
	    Workbook workbook = ExcelFactory.getWorkbook(inputStream, excelFilePath);
	    Sheet nnSheet = workbook.getSheetAt(sheetNo);
	    Iterator<Row> iterator = nnSheet.iterator();
	    int rowNum = 0;
	    int lastcell = 0;
	    
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        if (! (rowNum>0)) {
				lastcell = nextRow.getLastCellNum();
			}
			Iterator<Cell> cellIterator = nextRow.cellIterator();
	        StringBuilder memDetails = new StringBuilder();
	        
	        Map<String,String> ContactsMap = new HashMap<String,String>();
	        int i =0;
	        	while (cellIterator.hasNext()&& i<lastcell) {
		            Cell nextCell = cellIterator.next();
		            String cellVal = getCellValue(nextCell);
		            if("".equals(getCellValue(nextCell))||null== getCellValue(nextCell)||"BLANK".equals(getCellValue(nextCell))){
		            	cellVal = "NULL";
		            }
		            if(cellVal.contains(",")){
		            	cellVal = cellVal.replaceAll("\\,","[COMMA]");
		            }
		            if(nextCell.getColumnIndex()==0){
		            	continue;
		            }	            
		            if (i != lastcell-1) {
						memDetails = memDetails.append(cellVal + ",");
					}else{
						memDetails = memDetails.append(cellVal);
					}
		            i++;
	        	}
	        
	 
	        
	        if (rowNum>0) {
				
				
		        if (headerdetails.length()>0) {
					String[] hDetails = headerdetails.split("\\,");
					String[] mDetails = memDetails.toString().split("\\,");
					if (hDetails.length == mDetails.length) {
						for (int itr = 0; itr < hDetails.length; itr++) {
							ContactsMap.put(hDetails[itr], mDetails[itr]);
						}
					} 
				}
		        String contactString = ContactsMap.toString().trim();
		        contactString = contactString.substring(1, contactString.length()-1);
		        System.out.println("contactString -->>"+contactString);
		        listMembers.add(contactString.toString());
			}else{
		        headerdetails = memDetails.toString();
		     }
			rowNum++;
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return listMembers;
	}
	
	
	private String getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return String.valueOf(cell.getBooleanCellValue());
	 
	    case Cell.CELL_TYPE_NUMERIC:
	    	
	    	String resultString = null;
	    	
	          StringBuffer buffer = new StringBuffer();

	          if(HSSFDateUtil.isCellDateFormatted(cell)){
	        	  String dateFormat = "dd-MMM-yyyy";
	        	  SimpleDateFormat date_formatter = new SimpleDateFormat(dateFormat);
	        	  Date dt = cell.getDateCellValue();
	        	  String dateValue = date_formatter.format(dt);
	        	  resultString = dateValue;
	          }else{
	        	  Double dVal = cell.getNumericCellValue();
	        	  String formattingString = "##########";
			      DecimalFormat formatter = new DecimalFormat(formattingString);
			      FieldPosition fPosition = new FieldPosition(0);
		          formatter.format(dVal, buffer, fPosition);
		          resultString =buffer.toString();
	          }
	          	          
	          return String.valueOf(resultString);
	          
	    }
	 
	    return null;
	}
	
}

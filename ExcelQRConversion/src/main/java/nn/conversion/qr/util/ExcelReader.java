package nn.conversion.qr.util;

import java.awt.print.Book;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import nn.conversion.qr.model.Eatery;
import nn.conversion.qr.model.NNAlumniMember;

public class ExcelReader {

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
	
	
	public List<String> readBooksFromExcelFileAsString(String excelFilePath) throws IOException {
	    List<String> listMembers = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = ExcelFactory.getWorkbook(inputStream, excelFilePath);
	    Sheet nnSheet = workbook.getSheetAt(1);
	    Iterator<Row> iterator = nnSheet.iterator();
	    
	    
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        int lastcell = nextRow.getLastCellNum();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        StringBuilder memDetails = new StringBuilder();
	        for(int i =0; i<lastcell; i++){
	        	while (cellIterator.hasNext()) {
		            Cell nextCell = cellIterator.next();
		            if (i != lastcell-1) {
						memDetails = memDetails.append(getCellValue(nextCell) + "-");
					}
	        	}
	        
	 
	        }
	        listMembers.add(memDetails.toString());
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
	    	Double dVal = cell.getNumericCellValue();
	    	int intVal = dVal.intValue();
	        return String.valueOf(intVal);
	    }
	 
	    return null;
	}
	
}

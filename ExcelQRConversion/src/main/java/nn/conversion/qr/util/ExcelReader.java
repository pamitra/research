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

	public List<NNAlumniMember> readBooksFromExcelFile(String excelFilePath) throws IOException {
	    List<NNAlumniMember> listMembers = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = ExcelFactory.getWorkbook(inputStream, excelFilePath);
	    Sheet nnSheet = workbook.getSheetAt(1);
	    Iterator<Row> iterator = nnSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        int lastcell = nextRow.getLastCellNum();
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
	            	Double batchyear = (double) getCellValue(nextCell);
	            	int batch = batchyear.intValue();
	            	member.setBatch((String) String.valueOf(batch));
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
	
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	
}

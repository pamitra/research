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

public class ExcelReader2 {

	public List<Eatery> readBooksFromExcelFile(String excelFilePath) throws IOException {
	    List<Eatery> listEateries = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = ExcelFactory.getWorkbook(inputStream, excelFilePath);
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        Eatery eatery = new Eatery();
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	 
	            switch (columnIndex) {
	            case 0:
	            	eatery.setRestaurant((String) getCellValue(nextCell));
	                break;
	            case 1:
	            	eatery.setLocation((String) getCellValue(nextCell));
	                break;
	            case 2:
	            	eatery.setSpeciality((String) getCellValue(nextCell));
	            	break;
	            case 3:
	            	eatery.setBudget((String) getCellValue(nextCell));
	                break;
	            }
	 
	 
	        }
	        listEateries.add(eatery);
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return listEateries;
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

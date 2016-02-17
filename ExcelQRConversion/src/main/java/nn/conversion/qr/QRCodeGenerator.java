package nn.conversion.qr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nn.conversion.qr.util.ExcelReader;

public class QRCodeGenerator {
	
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static boolean pdfsuccess = false;
	
	public String generateQR(String excelFilePath, int sheetNo){

		System.out.println("excel file path is "+ excelFilePath);
		String[] pathParts = excelFilePath.split("\\"+FILE_SEPARATOR);
		String xlfilename = pathParts[pathParts.length -1 ].trim();
		String pdfFilename = xlfilename.substring(0, xlfilename.indexOf("."));
		String pdfFile = excelFilePath.replace(xlfilename, pdfFilename+".pdf");
				
		List<String> members = generateQRinputFromExcel(excelFilePath,sheetNo);
		try{
			pdfsuccess = new ContactsQRPdfGenerator().createPdf(pdfFile,members);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdfFile;
	}
	
	public static List<String> generateQRinputFromExcel(String excelFilePath, int sheetNo){
//		 String excelFilePath = "C:\\punchbag\\QRtestData.xls";
		    ExcelReader reader = new ExcelReader();
		    List<String> listMembers = new ArrayList<String>();
			try {
				listMembers = reader.readBooksFromExcelFileAsString(excelFilePath, sheetNo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(listMembers);
		    return listMembers;
	}
}

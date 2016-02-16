package nn.conversion.qr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import nn.conversion.qr.model.Eatery;
import nn.conversion.qr.model.NNAlumniMember;
import nn.conversion.qr.util.ExcelReader;

public class Main {
	
	public static void main(String[] args){

		String excelFilePath = "C:\\punchbag\\QRtestData.xls";
		String pdfFilename = "C:\\punchbag\\QRContactCodes.pdf";
				
		List<NNAlumniMember> members = generateQRinputFromExcel(excelFilePath);
		try{
			new ContactsQRPdfGenerator().createPdf(pdfFilename,members);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<NNAlumniMember> generateQRinputFromExcel(String excelFilePath){
//		 String excelFilePath = "C:\\punchbag\\QRtestData.xls";
		    ExcelReader reader = new ExcelReader();
		    List<NNAlumniMember> listMembers = new ArrayList<NNAlumniMember>();
			try {
				listMembers = reader.readBooksFromExcelFile(excelFilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(listMembers);
		    return listMembers;
	}

}

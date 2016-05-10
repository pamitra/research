package nn.conversion.qr;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.WriterException;

import nn.conversion.qr.model.Eatery;
import nn.conversion.qr.model.NNAlumniMember;

public class ContactsQRPdfGenerator {

    /** The resulting PDF file. */
    public static final String RESULT = "C:/punchbag/QRContacts.pdf";
    // color codes
        private static final int BLACK = 0xFF000000;
        private static final int WHITE = 0xFFFFFFFF;
        
        private static String det = " ";
     /**
     * Main method.
     * 
     * @param args
     *            no arguments needed
     * @throws DocumentException
     * @throws IOException
     * @throws com.google.zxing.WriterException 
     * @throws WriterException 
     */
    /*public static void main(String[] args) throws IOException,
            DocumentException, WriterException, com.google.zxing.WriterException {
        new QRCodeFirstTable().createPdf(RESULT);
    }*/

    /**
     * Creates a PDF with information about the movies
     * 
     * @param filename
     *            the name of the PDF file that will be created.
     * @throws DocumentException
     * @throws IOException
     * @throws com.google.zxing.WriterException 
     * @throws WriterException 
     */
    public boolean createPdf(String filename, List<String> members) throws IOException,
            DocumentException, WriterException, com.google.zxing.WriterException {
    	boolean executionstatus;
        File f=new File(filename);
        if(!f.exists()) {
            f.createNewFile();
        }
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        document.add(createMainTable(members));
        document.close();
        System.out.println("File was successfully saved to ----- " + new File(filename).getAbsolutePath());
        executionstatus = true;
        return executionstatus;
    }

    /**
     * Creates our first table
     * 
     * @return our first table
     * @throws IOException
     * @throws MalformedURLException
     * @throws BadElementException
     * @throws com.google.zxing.WriterException 
     * @throws WriterException 
     */
    public static PdfPTable createSubTable(boolean align_left, String header, String QRinput, String[] details)
            throws BadElementException, MalformedURLException, IOException, WriterException, com.google.zxing.WriterException {
        BaseColor bc = new BaseColor(150, 150, 150);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        // the cell object
        PdfPCell cell, cell1, cell2;
        Font font = new Font();
        font.setColor(BaseColor.WHITE);
        Chunk c = new Chunk(header, font);
        Paragraph head = new Paragraph(c);
        head.setFont(font);
        cell = new PdfPCell(head);
        cell.setColspan(2);
        cell.setBackgroundColor(bc);
        cell.setPadding(2f);
        table.addCell(cell);
        if(QRinput.contains("[COMMA]")){
        	QRinput = QRinput.replaceAll("\\[COMMA\\]", ",");
        }
        Image img = getQRCodeImage(QRinput, "UTF-8", 40, 40);

        font = new Font();
        font.setSize(10f);
        StringBuilder qrdata = new StringBuilder();
        for(String detail: details){
        	if(detail.contains("[COMMA]")){
        		detail = detail.replaceAll("\\[COMMA\\]", ",");
        	}
        	qrdata = qrdata.append("\n"+detail.trim()+"\n"); 
        }
        
        c = new Chunk(qrdata.toString(), font);
        Paragraph body = new Paragraph(c);
        cell1 = new PdfPCell(body);
        cell1.setBorderWidthRight(0);
        cell1.setPadding(10f);

        cell2 = new PdfPCell();
        cell2.addElement(img);
        cell2.setBorderWidthLeft(0);
        cell2.setPadding(10f);

        // now we add a cell
        table.addCell(cell1);
        table.addCell(cell2);
        System.out.println("Sub Table was created");
        return table;
    }

    public static PdfPTable createMainTable(List<String> entries) throws BadElementException,
            MalformedURLException, IOException, WriterException, com.google.zxing.WriterException {
        // a table with two columns
        PdfPTable table = new PdfPTable(1);
        System.out.println("Main Table was created");
        
        String[] details = null;
        String header = "";
        String QRinput;
        
        
        for (String entry: entries) {        	
        	//getting table content
        	QRinput = entry; /*Name=Sanghamitra Bose, Registration Number=NN1975000264, Batch=1975, Membership Type=01. Life, Mobile=9831292552, Mail Id=NULL, Card Number=201603270001*/
        	details = entry.split("\\,");
        	QRinput = details[0]+","+details[1]+","+details[6];
        	
        	try {
				for(String detl : details){//"Name=Romit"
//					det = detl;
					if("Name".equalsIgnoreCase(detl.substring(0,detl.indexOf("=")).trim())){
						header = detl.split("\\=")[1].trim();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("error is in --->> "+ entry);
				e.printStackTrace();
			}
        	
        	System.out.println("header ="+header);
            // cell object
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorderWidth(0);
            cell1.setPadding(10f);
            cell1.addElement(createSubTable(false,header,QRinput,details ));
            table.addCell(cell1);
        }
        return table;
    }

    /**
     * To Generate QR Code Image from text
     * 
     * @param qrCodeData
     *            - text for QR Code image
     * @param charset
     * @param qrCodeheight
     *            - QR Code image height
     * @param qrCodewidth
     *            - QR Code image width
     * @return
     * @throws WriterException
     * @throws IOException
     * @throws com.google.zxing.WriterException
     */
    public static BufferedImage createQRCode(String qrCodeData, String charset,
            int qrCodeheight, int qrCodewidth) throws WriterException,
            IOException, com.google.zxing.WriterException {
        // Generate BitMatrix
        com.google.zxing.common.BitMatrix matrix = new MultiFormatWriter()
                .encode(new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // Converting BitMatrix to Buffered Image
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        System.out.println("qr code image was generated for "+qrCodeData );
        return image;
    }

    public static Image getQRCodeImage(String qrCodeData, String charset, int qrCodeheight, int qrCodewidth) throws WriterException,
            IOException, com.google.zxing.WriterException, BadElementException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage bi = createQRCode(qrCodeData, charset, qrCodeheight, qrCodewidth);
        ImageIO.write(bi, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        Image image = Image.getInstance(imageInByte);
        System.out.println("Image was generated for qr code image");
        return image;
    }

}
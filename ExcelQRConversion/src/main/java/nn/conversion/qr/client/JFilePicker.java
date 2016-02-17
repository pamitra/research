package nn.conversion.qr.client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nn.conversion.qr.QRCodeGenerator;
import nn.conversion.qr.util.ExcelReader;
 
public class JFilePicker extends JPanel {
    private String xltextFieldLabel;
    private String buttonLabel;
     
    private JLabel xllabel, sheetLbl;
    private JTextField xltextField,sheeNumTxtfield;
    private JButton button;
    private JButton generateQrButton;
     
    private JFileChooser fileChooser;
     
    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;
     
    public JFilePicker(String textFieldLabel, String buttonLabel) {
        this.xltextFieldLabel = textFieldLabel;
        this.buttonLabel = buttonLabel;
         
        fileChooser = new JFileChooser();
         
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
 
        // creates the GUI
        xllabel = new JLabel(xltextFieldLabel);
        sheetLbl = new JLabel("worksheet No");
        xltextField = new JTextField(30);
        sheeNumTxtfield = new JTextField(20);
        button = new JButton(buttonLabel);
        generateQrButton = new JButton("getQR");
        generateQrButton.setEnabled(false);
         
        
        //setup multi-option combo box
        JPanel jPanel = new JPanel();
        JLabel comboLabel = new JLabel("Select Column(s): ");
        String[] columns = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        JComboBox<String> columnComboBox = new JComboBox<String>(columns);
//        columnComboBox.
        columnComboBox.setEditable(false);
        jPanel.add(comboLabel);
        jPanel.add(columnComboBox);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);      
            }
        });
         
        add(xllabel);
        add(xltextField);
        add(sheetLbl);
        add(sheeNumTxtfield);
        add(button);
        add(generateQrButton);
        add(jPanel);
        
        generateQrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	qrButtonActionPerformed(evt);            
            }
        });
        
    }
     
    private void buttonActionPerformed(ActionEvent evt) {
        if (mode == MODE_OPEN) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                xltextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (mode == MODE_SAVE) {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                xltextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        
        
        button.setEnabled(false);
        generateQrButton.setEnabled(true);
    }
    
	private void qrButtonActionPerformed(ActionEvent evt) {
		JFrame frame = new JFrame( "QR code Generator " );
		QRCodeGenerator generator = new QRCodeGenerator();
		String xlfile = xltextField.getText();
		String sheetNo = sheeNumTxtfield.getText();
		String pdfFile = null;
		String extension = xlfile.substring(xlfile.indexOf("."));
		if(".xls".equals(extension)||".xlsx".equals(extension)){
			pdfFile = generator.generateQR(xltextField.getText(),Integer.parseInt(sheetNo));
			JOptionPane.showMessageDialog ( frame, "QR code pdf generated at: "+pdfFile, "QR Generator Window", JOptionPane.INFORMATION_MESSAGE);
			if(QRCodeGenerator.pdfsuccess){
				button.setEnabled(true);
		        generateQrButton.setEnabled(false);
			}
		}else{
			button.setEnabled(true);
	        generateQrButton.setEnabled(false);
			JOptionPane.showMessageDialog (frame, "Input file is not an excel file !", "error", 
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
	}

 
    public void addFileTypeFilter(String extension, String description) {
        FileTypeFilter filter = new FileTypeFilter(extension, description);
        fileChooser.setFileFilter(filter);
    }
     
    public void setMode(int mode) {
        this.mode = mode;
    }
     
    public String getSelectedFilePath() {
        return xltextField.getText();
    }
     
    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }
}

package nn.conversion.qr.client;

import java.awt.FlowLayout;
import java.io.File;
 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
 
public class ContactsFilePickerClient extends JFrame {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContactsFilePickerClient() {
        super("Test using JFilePicker");
         
        setLayout(new FlowLayout());
 
        // set up a file picker component
        JFilePicker filePicker = new JFilePicker("Pick a file", "Browse...");
        filePicker.setMode(JFilePicker.MODE_SAVE);
        filePicker.addFileTypeFilter(".xls","excel 2007");
        filePicker.addFileTypeFilter(".xlsx", "excel 2010/2013");
         
        // access JFileChooser class directly
        JFileChooser fileChooser = filePicker.getFileChooser();
        fileChooser.setCurrentDirectory(new File("C:/"));
        
         
        // add the component to the frame
        add(filePicker);
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 100);
        setLocationRelativeTo(null);    // center on screen
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContactsFilePickerClient().setVisible(true);
            }
        });
    }
 
}

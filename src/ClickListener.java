import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

public class ClickListener implements ActionListener {

    DataHandler data_handler;

    public ClickListener(DataHandler data_handler) {
        this.data_handler = data_handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser file_chooser = new JFileChooser();
        file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int return_value = file_chooser.showOpenDialog(null);
        if (return_value == JFileChooser.APPROVE_OPTION) {
            
            File selected_folder = file_chooser.getSelectedFile();
            System.out.println("Selected folder: " + selected_folder);
            
            //data_handler.refresh_list(false);
            
        }
    }
}

// //KeyAdapter for tile index input to consumer non-digit characters
// char_consumer = new KeyAdapter() {
//     @Override
//     public void keyTyped(KeyEvent e) {
//         char c = e.getKeyChar();
//         //System.out.println(idx_input.getText().length() + " outside");
//         // Ignore non-digit characters
//         if (!Character.isDigit(c)) {
//             //System.out.println(idx_input.getText().length() + " inside");
//             e.consume();
//         }
//     }
// };

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
public class AddListener implements ActionListener {

    DataHandler data_handler;
    TileListHandler TL_handler;

    public AddListener(DataHandler data_handler, TileListHandler TL_handler) {
        this.data_handler = data_handler;
        this.TL_handler = TL_handler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser file_chooser = new JFileChooser();
        file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int return_value = file_chooser.showOpenDialog(null);
        if (return_value == JFileChooser.APPROVE_OPTION) {
            
            File selected_folder = file_chooser.getSelectedFile();
            System.out.println("Selected folder: " + selected_folder);
            
            File[] files = selected_folder.listFiles();
            if(files != null) TL_handler.loadTiles(files);
            
        }
    }
}

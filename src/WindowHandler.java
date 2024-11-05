
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandler implements WindowListener, KeyListener {

    Panel panel;

    public WindowHandler(Panel panel){
        this.panel = panel;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //prompt to save when there are changes
        if(panel.grid.has_changes){
            Saver temp = new Saver(new DataHandler(panel));
            temp.save();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        if(e.isControlDown()){

            switch(code) {

                case KeyEvent.VK_S -> {
                    Saver temp = new Saver(new DataHandler(panel));
                    temp.save();
                }
                case KeyEvent.VK_Z -> {
                    //UNDO
                    if(!panel.data_handler.is_undoing){
                        panel.data_handler.panel.grid.performUndo();
                        panel.data_handler.is_undoing = true;
                    }
                }
                case KeyEvent.VK_X -> {
                    //REDO
                    if(!panel.data_handler.is_redoing){
                        panel.data_handler.panel.grid.performRedo();
                        panel.data_handler.is_redoing = true;
                    }
                }
            }
        }

        if(code == KeyEvent.VK_SPACE){
            //BUCKET HOTKEY
            if(!panel.grid.bucket){
                GUI.Labels.BUCKET.setForeground(Color.GREEN);
                GUI.Labels.BUCKET.setText("On");
                panel.grid.bucket = true;
            }
            else{
                GUI.Labels.BUCKET.setForeground(Color.RED);
                GUI.Labels.BUCKET.setText("Off");
                panel.grid.bucket = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.isControlDown()){

            int code = e.getKeyCode();

            switch(code) {

                case KeyEvent.VK_Z -> {
                    //UNDO
                    panel.data_handler.is_undoing = false;
                }
                case KeyEvent.VK_X -> {
                    //REDO
                    panel.data_handler.is_redoing = false;
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
}

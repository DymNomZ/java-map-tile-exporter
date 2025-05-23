import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandler implements MouseMotionListener, MouseListener, MouseWheelListener{

    public int mouse_x = 0, mouse_y = 0, clicked_x, clicked_y, recent_x = 0, recent_y = 0;
    public int scale_factor = 1, wheel_rotation;
    public int tile_x = 0, tile_y = 0; //for cursor
    public boolean paint_control = false, is_pressed = false, is_dragged = false, is_clicked = false;
    public boolean left_pressed = false, right_pressed = false;

    Panel panel;

    public MouseHandler(){
        
    }

    public MouseHandler(int SCREEN_WIDTH, int SCREEN_HEIGHT, Panel panel){
        //sets default coords of mouse
        mouse_x = SCREEN_WIDTH / 2;
        mouse_y = SCREEN_HEIGHT / 2;
        recent_x = mouse_x;
        recent_y = mouse_y;

        this.panel = panel;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        //update mouse coords to mouse position on screen
        is_dragged = true;
        mouse_x = e.getX();
        mouse_y = e.getY();
        recent_x = mouse_x;
        recent_y = mouse_y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicked_x = e.getX();
        clicked_y = e.getY();
        is_pressed = true;
        switch(e.getButton()){
            case 1 -> left_pressed = true;
            case 2 -> paint_control = !paint_control;
            case 3 -> right_pressed = true;
        }
        //Exit focus on TextFields
        panel.requestFocusInWindow();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //reset
        is_pressed = false;
        is_dragged = false;
        switch(e.getButton()){
            case 1 -> left_pressed = false;
            case 3 -> right_pressed = false;
        }
        mouse_x = recent_x;
        mouse_y = recent_y;
    }    

    int get_scale_factor(){
        return scale_factor;
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
        //handle scaling
        wheel_rotation = e.getWheelRotation();
        if(wheel_rotation > 0){
            
            if(scale_factor == 2) scale_factor = 1;
            else if(scale_factor == 3) scale_factor = 2;
        }
        else if(wheel_rotation < 0){
            if(scale_factor == 1) scale_factor = 2;
           else if(scale_factor == 2) scale_factor = 3; 
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //for tile_handler, position of mouse on the screen
        tile_x = e.getX();
        tile_y = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("I am clicked");
        is_clicked = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}

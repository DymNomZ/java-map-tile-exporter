import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandler implements MouseMotionListener, MouseListener, MouseWheelListener{

    public int mouse_x, mouse_y, clicked_x, clicked_y, recent_x, recent_y;
    public int scale_factor = 1, wheel_rotation;
    public boolean is_pressed = false, is_dragged = false;
    public boolean left_pressed = false, right_pressed = false, middle_pressed = false;

    public MouseHandler(int SCREEN_WIDTH, int SCREEN_HEIGHT){
        mouse_x = SCREEN_WIDTH / 2;
        mouse_y = SCREEN_HEIGHT / 2;
        recent_x = mouse_x;
        recent_y = mouse_y;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
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
            case 2 -> middle_pressed = true;
            case 3 -> right_pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        is_pressed = false;
        is_dragged = false;
        switch(e.getButton()){
            case 1 -> left_pressed = false;
            case 2 -> middle_pressed = false;
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
        
        wheel_rotation = e.getWheelRotation();
        if(wheel_rotation > 0){
            if(scale_factor == 3) scale_factor = 2;
            else if(scale_factor == 2) scale_factor = 1;
        }
        else if(wheel_rotation < 0){
            if(scale_factor == 1) scale_factor = 2;
            else if(scale_factor == 2) scale_factor = 3; 
        }
        //System.out.println(scale_factor);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}

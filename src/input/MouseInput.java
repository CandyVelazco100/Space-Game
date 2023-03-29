// 
// Decompiled by Procyon v0.5.36
// 

package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class MouseInput extends MouseAdapter
{
    public static int X;
    public static int Y;
    public static boolean MLB;
    
    @Override
    public void mousePressed(final MouseEvent e) {
        if (e.getButton() == 1) {
            MouseInput.MLB = true;
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
        if (e.getButton() == 1) {
            MouseInput.MLB = false;
        }
    }
    
    @Override
    public void mouseDragged(final MouseEvent e) {
        MouseInput.X = e.getX();
        MouseInput.Y = e.getY();
    }
    
    @Override
    public void mouseMoved(final MouseEvent e) {
        MouseInput.X = e.getX();
        MouseInput.Y = e.getY();
    }
}

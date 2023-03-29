// 
// Decompiled by Procyon v0.5.36
// 

package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener
{
    private boolean[] keys;
    public static boolean UP;
    public static boolean LEFT;
    public static boolean RIGHT;
    public static boolean SHOOT;
    
    public KeyBoard() {
        this.keys = new boolean[256];
        KeyBoard.UP = false;
        KeyBoard.LEFT = false;
        KeyBoard.RIGHT = false;
        KeyBoard.SHOOT = false;
    }
    
    public void update() {
        KeyBoard.UP = this.keys[38];
        KeyBoard.LEFT = this.keys[37];
        KeyBoard.RIGHT = this.keys[39];
        KeyBoard.SHOOT = this.keys[80];
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        this.keys[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
}

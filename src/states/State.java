// 
// Decompiled by Procyon v0.5.36
// 

package states;

import java.awt.Graphics;

public abstract class State
{
    private static State currentState;
    
    static {
        State.currentState = null;
    }
    
    public static State getCurrentState() {
        return State.currentState;
    }
    
    public static void changeState(final State newState) {
        State.currentState = newState;
    }
    
    public abstract void update(final float p0);
    
    public abstract void draw(final Graphics p0);
}

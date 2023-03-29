// 
// Decompiled by Procyon v0.5.36
// 

package graphics;

import math.Vector2D;
import java.awt.image.BufferedImage;

public class Animation
{
    private BufferedImage[] frames;
    private int velocity;
    private int index;
    private boolean running;
    private Vector2D position;
    private long time;
    private long lastTime;
    
    public Animation(final BufferedImage[] frames, final int velocity, final Vector2D position) {
        this.frames = frames;
        this.velocity = velocity;
        this.position = position;
        this.index = 0;
        this.running = true;
        this.lastTime = System.currentTimeMillis();
    }
    
    public void update(final float dt) {
        this.time += System.currentTimeMillis() - this.lastTime;
        this.lastTime = System.currentTimeMillis();
        if (this.time > this.velocity) {
            this.time = 0L;
            ++this.index;
            if (this.index >= this.frames.length) {
                this.running = false;
                this.index = 0;
            }
        }
    }
    
    public boolean isRunning() {
        return this.running;
    }
    
    public Vector2D getPosition() {
        return this.position;
    }
    
    public BufferedImage getCurrentFrame() {
        return this.frames[this.index];
    }
}

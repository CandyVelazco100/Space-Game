// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

public class Chronometer
{
    private long delta;
    private long lastTime;
    private long time;
    private boolean running;
    
    public Chronometer() {
        this.delta = 0L;
        this.lastTime = System.currentTimeMillis();
        this.running = false;
    }
    
    public void run(final long time) {
        this.running = true;
        this.time = time;
    }
    
    public void update() {
        if (this.running) {
            this.delta += System.currentTimeMillis() - this.lastTime;
        }
        if (this.delta >= this.time) {
            this.running = false;
            this.delta = 0L;
        }
        this.lastTime = System.currentTimeMillis();
    }
    
    public boolean isRunning() {
        return this.running;
    }
}

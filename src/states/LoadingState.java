// 
// Decompiled by Procyon v0.5.36
// 

package states;

import graphics.Text;
import math.Vector2D;
import java.awt.Paint;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.Graphics;
import graphics.Assets;
import graphics.Loader;
import java.awt.Font;

public class LoadingState extends State
{
    private Thread loadingThread;
    private Font font;
    
    public LoadingState(final Thread loadingThread) {
        (this.loadingThread = loadingThread).start();
        this.font = Loader.loadFont("/fonts/futureFont.ttf", 38);
    }
    
    @Override
    public void update(final float dt) {
        if (Assets.loaded) {
            State.changeState(new MenuState());
            try {
                this.loadingThread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void draw(final Graphics g) {
        final GradientPaint gp = new GradientPaint(250.0f, 275.0f, Color.WHITE, 750.0f, 325.0f, Color.BLUE);
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(gp);
        final float percentage = Assets.count / Assets.MAX_COUNT;
        g2d.fillRect(250, 275, (int)(500.0f * percentage), 50);
        g2d.drawRect(250, 275, 500, 50);
        Text.drawText(g2d, "SPACE SHIP GAME", new Vector2D(500.0, 250.0), true, Color.WHITE, this.font);
        Text.drawText(g2d, "LOADING...", new Vector2D(500.0, 340.0), true, Color.WHITE, this.font);
    }
}

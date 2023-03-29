// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import java.awt.Graphics;
import graphics.Text;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import math.Vector2D;

public class Message
{
    private float alpha;
    private String text;
    private Vector2D position;
    private Color color;
    private boolean center;
    private boolean dead;
    private boolean fade;
    private Font font;
    private final float deltaAlpha = 0.01f;
    
    public Message(final Vector2D position, final boolean fade, final String text, final Color color, final boolean center, final Font font) {
        this.font = font;
        this.text = text;
        this.position = new Vector2D(position);
        this.fade = fade;
        this.color = color;
        this.center = center;
        this.dead = false;
        if (fade) {
            this.alpha = 1.0f;
        }
        else {
            this.alpha = 0.0f;
        }
    }
    
    public void draw(final Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(3, this.alpha));
        Text.drawText(g2d, this.text, this.position, this.center, this.color, this.font);
        g2d.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.position.setY(this.position.getY() - 1.0);
        if (this.fade) {
            this.alpha -= 0.01f;
        }
        else {
            this.alpha += 0.01f;
        }
        if (this.fade && this.alpha < 0.0f) {
            this.dead = true;
        }
        if (!this.fade && this.alpha > 1.0f) {
            this.fade = true;
            this.alpha = 1.0f;
        }
    }
    
    public boolean isDead() {
        return this.dead;
    }
}

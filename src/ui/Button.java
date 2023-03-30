// 
// Decompiled by Procyon v0.5.36
// 

package ui;

import graphics.Text;
import graphics.Assets;
import java.awt.Color;
import math.Vector2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics;
import input.MouseInput;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Button
{
    private BufferedImage mouseOutImg;
    private BufferedImage mouseInImg;
    private boolean mouseIn;
    private Rectangle boundingBox;
    private Action action;
    private String text;
    
    public Button(final BufferedImage mouseOutImg, final BufferedImage mouseInImg, final int x, final int y, final String text, final Action action) {
        this.mouseInImg = mouseInImg;
        this.mouseOutImg = mouseOutImg;
        this.text = text;
        this.boundingBox = new Rectangle(x, y, mouseInImg.getWidth(), mouseInImg.getHeight());
        this.action = action;
    }
    
    public void update() {
        if (this.boundingBox.contains(MouseInput.X, MouseInput.Y)) {
            this.mouseIn = true;
        }
        else {
            this.mouseIn = false;
        }
        if (this.mouseIn && MouseInput.MLB) {
            this.action.doAction();
        }
    }
    
    public void draw(final Graphics g) {
        if (this.mouseIn) {
            g.drawImage(this.mouseInImg, this.boundingBox.x, this.boundingBox.y, null);
        }
        else {
            g.drawImage(this.mouseOutImg, this.boundingBox.x, this.boundingBox.y, null);
        }
        Text.drawText(g, this.text, new Vector2D(this.boundingBox.getX() + this.boundingBox.getWidth() / 2.0, this.boundingBox.getY() + this.boundingBox.getHeight()), true, Color.BLACK, Assets.fontMed);
    }
}

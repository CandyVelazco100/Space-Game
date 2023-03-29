// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import java.awt.Graphics;
import math.Vector2D;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
    protected BufferedImage texture;
    protected Vector2D position;
    
    public GameObject(final Vector2D position, final BufferedImage texture) {
        this.position = position;
        this.texture = texture;
    }
    
    public abstract void update(final float p0);
    
    public abstract void draw(final Graphics p0);
    
    public Vector2D getPosition() {
        return this.position;
    }
    
    public void setPosition(final Vector2D position) {
        this.position = position;
    }
}

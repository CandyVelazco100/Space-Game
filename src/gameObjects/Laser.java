// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Graphics;
import states.GameState;
import java.awt.image.BufferedImage;
import math.Vector2D;

public class Laser extends MovingObject
{
    public Laser(final Vector2D position, final Vector2D velocity, final double maxVel, final double angle, final BufferedImage texture, final GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.angle = angle;
        this.velocity = velocity.scale(maxVel);
    }
    
    @Override
    public void update(final float dt) {
        this.position = this.position.add(this.velocity);
        if (this.position.getX() < 0.0 || this.position.getX() > 1000.0 || this.position.getY() < 0.0 || this.position.getY() > 600.0) {
            this.Destroy();
        }
        this.collidesWith();
    }
    
    @Override
    public void draw(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g;
        (this.at = AffineTransform.getTranslateInstance(this.position.getX() - this.width / 2, this.position.getY())).rotate(this.angle, this.width / 2, 0.0);
        g2d.drawImage(this.texture, this.at, null);
    }
    
    public Vector2D getCenter() {
        return new Vector2D(this.position.getX() + this.width / 2, this.position.getY() + this.width / 2);
    }
}

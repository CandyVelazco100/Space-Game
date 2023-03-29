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

public class Meteor extends MovingObject
{
    private Size size;
    
    public Meteor(final Vector2D position, final Vector2D velocity, final double maxVel, final BufferedImage texture, final GameState gameState, final Size size) {
        super(position, velocity, maxVel, texture, gameState);
        this.size = size;
        this.velocity = velocity.scale(maxVel);
    }
    
    @Override
    public void update(final float dt) {
        final Vector2D playerPos = new Vector2D(this.gameState.getPlayer().getCenter());
        final int distanceToPlayer = (int)playerPos.subtract(this.getCenter()).getMagnitude();
        if (distanceToPlayer < 75 + this.width / 2 && this.gameState.getPlayer().isShieldOn()) {
            final Vector2D fleeForce = this.fleeForce();
            this.velocity = this.velocity.add(fleeForce);
        }
        if (this.velocity.getMagnitude() >= this.maxVel) {
            final Vector2D reversedVelocity = new Vector2D(-this.velocity.getX(), -this.velocity.getY());
            this.velocity = this.velocity.add(reversedVelocity.normalize().scale(0.009999999776482582));
        }
        this.velocity = this.velocity.limit(6.0);
        this.position = this.position.add(this.velocity);
        if (this.position.getX() > 1000.0) {
            this.position.setX(-this.width);
        }
        if (this.position.getY() > 600.0) {
            this.position.setY(-this.height);
        }
        if (this.position.getX() < -this.width) {
            this.position.setX(1000.0);
        }
        if (this.position.getY() < -this.height) {
            this.position.setY(600.0);
        }
        this.angle += 0.05;
    }
    
    private Vector2D fleeForce() {
        Vector2D desiredVelocity = this.gameState.getPlayer().getCenter().subtract(this.getCenter());
        desiredVelocity = desiredVelocity.normalize().scale(6.0);
        final Vector2D v = new Vector2D(this.velocity);
        return v.subtract(desiredVelocity);
    }
    
    public void Destroy() {
        this.gameState.divideMeteor(this);
        this.gameState.playExplosion(this.position);
        this.gameState.addScore(20, this.position);
        super.Destroy();
    }
    
    @Override
    public void draw(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g;
        (this.at = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY())).rotate(this.angle, this.width / 2, this.height / 2);
        g2d.drawImage(this.texture, this.at, null);
    }
    
    public Size getSize() {
        return this.size;
    }
}

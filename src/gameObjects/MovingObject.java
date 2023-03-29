// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import java.util.ArrayList;
import graphics.Assets;
import java.awt.image.BufferedImage;
import graphics.Sound;
import states.GameState;
import java.awt.geom.AffineTransform;
import math.Vector2D;

public abstract class MovingObject extends GameObject
{
    protected Vector2D velocity;
    protected AffineTransform at;
    protected double angle;
    protected double maxVel;
    protected int width;
    protected int height;
    protected GameState gameState;
    private Sound explosion;
    protected boolean Dead;
    
    public MovingObject(final Vector2D position, final Vector2D velocity, final double maxVel, final BufferedImage texture, final GameState gameState) {
        super(position, texture);
        this.velocity = velocity;
        this.maxVel = maxVel;
        this.gameState = gameState;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.angle = 0.0;
        this.explosion = new Sound(Assets.explosion);
        this.Dead = false;
    }
    
    protected void collidesWith() {
        final ArrayList<MovingObject> movingObjects = this.gameState.getMovingObjects();
        for (int i = 0; i < movingObjects.size(); ++i) {
            final MovingObject m = movingObjects.get(i);
            if (!m.equals(this)) {
                final double distance = m.getCenter().subtract(this.getCenter()).getMagnitude();
                if (distance < m.width / 2 + this.width / 2 && movingObjects.contains(this) && !m.Dead && !this.Dead) {
                    this.objectCollision(m, this);
                }
            }
        }
    }
    
    private void objectCollision(final MovingObject a, final MovingObject b) {
        Player p = null;
        if (a instanceof Player) {
            p = (Player)a;
        }
        else if (b instanceof Player) {
            p = (Player)b;
        }
        if (p != null && p.isSpawning()) {
            return;
        }
        if (a instanceof Meteor && b instanceof Meteor) {
            return;
        }
        if (!(a instanceof PowerUp) && !(b instanceof PowerUp)) {
            a.Destroy();
            b.Destroy();
            return;
        }
        if (p != null) {
            if (a instanceof Player) {
                ((PowerUp)b).executeAction();
                b.Destroy();
            }
            else if (b instanceof Player) {
                ((PowerUp)a).executeAction();
                a.Destroy();
            }
        }
    }
    
    protected void Destroy() {
        this.Dead = true;
        if (!(this instanceof Laser) && !(this instanceof PowerUp)) {
            this.explosion.play();
        }
    }
    
    protected Vector2D getCenter() {
        return new Vector2D(this.position.getX() + this.width / 2, this.position.getY() + this.height / 2);
    }
    
    public boolean isDead() {
        return this.Dead;
    }
}

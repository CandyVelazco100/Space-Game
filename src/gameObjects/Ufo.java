// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Graphics;
import graphics.Assets;
import states.GameState;
import java.awt.image.BufferedImage;
import graphics.Sound;
import math.Vector2D;
import java.util.ArrayList;

public class Ufo extends MovingObject
{
    private ArrayList<Vector2D> path;
    private Vector2D currentNode;
    private int index;
    private boolean following;
    private Chronometer fireRate;
    private Sound shoot;
    
    public Ufo(final Vector2D position, final Vector2D velocity, final double maxVel, final BufferedImage texture, final ArrayList<Vector2D> path, final GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.path = path;
        this.index = 0;
        this.following = true;
        this.fireRate = new Chronometer();
        this.shoot = new Sound(Assets.ufoShoot);
    }
    
    private Vector2D pathFollowing() {
        this.currentNode = this.path.get(this.index);
        final double distanceToNode = this.currentNode.subtract(this.getCenter()).getMagnitude();
        if (distanceToNode < 160.0) {
            ++this.index;
            if (this.index >= this.path.size()) {
                this.following = false;
            }
        }
        return this.seekForce(this.currentNode);
    }
    
    private Vector2D seekForce(final Vector2D target) {
        Vector2D desiredVelocity = target.subtract(this.getCenter());
        desiredVelocity = desiredVelocity.normalize().scale(this.maxVel);
        return desiredVelocity.subtract(this.velocity);
    }
    
    @Override
    public void update(final float dt) {
        Vector2D pathFollowing;
        if (this.following) {
            pathFollowing = this.pathFollowing();
        }
        else {
            pathFollowing = new Vector2D();
        }
        pathFollowing = pathFollowing.scale(0.016666666666666666);
        this.velocity = this.velocity.add(pathFollowing);
        this.velocity = this.velocity.limit(this.maxVel);
        this.position = this.position.add(this.velocity);
        if (this.position.getX() > 1000.0 || this.position.getY() > 600.0 || this.position.getX() < -this.width || this.position.getY() < -this.height) {
            this.Destroy();
        }
        if (!this.fireRate.isRunning()) {
            Vector2D toPlayer = this.gameState.getPlayer().getCenter().subtract(this.getCenter());
            toPlayer = toPlayer.normalize();
            double currentAngle = toPlayer.getAngle();
            currentAngle += Math.random() * Constants.UFO_ANGLE_RANGE - Constants.UFO_ANGLE_RANGE / 2.0;
            if (toPlayer.getX() < 0.0) {
                currentAngle = -currentAngle + 3.141592653589793;
            }
            toPlayer = toPlayer.setDirection(currentAngle);
            final Laser laser = new Laser(this.getCenter().add(toPlayer.scale(this.width)), toPlayer, 15.0, currentAngle + 1.5707963267948966, Assets.redLaser, this.gameState);
            this.gameState.getMovingObjects().add(0, laser);
            this.fireRate.run(Constants.UFO_FIRE_RATE);
            this.shoot.play();
        }
        if (this.shoot.getFramePosition() > 8500) {
            this.shoot.stop();
        }
        this.angle += 0.05;
        this.collidesWith();
        this.fireRate.update();
    }
    
    public void Destroy() {
        this.gameState.addScore(40, this.position);
        this.gameState.playExplosion(this.position);
        super.Destroy();
    }
    
    @Override
    public void draw(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g;
        (this.at = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY())).rotate(this.angle, this.width / 2, this.height / 2);
        g2d.drawImage(this.texture, this.at, null);
    }
}

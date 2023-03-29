// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Graphics;
import input.KeyBoard;
import graphics.Assets;
import states.GameState;
import java.awt.image.BufferedImage;
import graphics.Animation;
import graphics.Sound;
import math.Vector2D;

public class Player extends MovingObject
{
    private Vector2D heading;
    private Vector2D acceleration;
    private boolean accelerating;
    private boolean spawning;
    private boolean visible;
    private boolean shieldOn;
    private boolean doubleScoreOn;
    private boolean fastFireOn;
    private boolean doubleGunOn;
    private Chronometer spawnTime;
    private Chronometer flickerTime;
    private long shieldTime;
    private long doubleScoreTime;
    private long fastFireTime;
    private long doubleGunTime;
    private long fireSpeed;
    private long fireRate;
    private Sound shoot;
    private Sound loose;
    private Animation shieldEffect;
    
    public Player(final Vector2D position, final Vector2D velocity, final double maxVel, final BufferedImage texture, final GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.accelerating = false;
        this.heading = new Vector2D(0.0, 1.0);
        this.acceleration = new Vector2D();
        this.fireRate = 0L;
        this.spawnTime = new Chronometer();
        this.flickerTime = new Chronometer();
        this.shieldTime = 0L;
        this.fastFireTime = 0L;
        this.doubleGunTime = 0L;
        this.fireSpeed = 0L;
        this.shoot = new Sound(Assets.playerShoot);
        this.loose = new Sound(Assets.playerLoose);
        this.shieldEffect = new Animation(Assets.shieldEffect, 80, null);
        this.visible = true;
    }
    
    @Override
    public void update(final float dt) {
        this.fireRate += (long)dt;
        if (this.shieldOn) {
            this.shieldTime += (long)dt;
        }
        if (this.doubleScoreOn) {
            this.doubleScoreTime += (long)dt;
        }
        if (this.fastFireOn) {
            this.fireSpeed = 150L;
            this.fastFireTime += (long)dt;
        }
        else {
            this.fireSpeed = 300L;
        }
        if (this.doubleGunOn) {
            this.doubleGunTime += (long)dt;
        }
        if (this.shieldTime > 12000L) {
            this.shieldTime = 0L;
            this.shieldOn = false;
        }
        if (this.doubleScoreTime > 10000L) {
            this.doubleScoreOn = false;
            this.doubleScoreTime = 0L;
        }
        if (this.fastFireTime > 14000L) {
            this.fastFireOn = false;
            this.fastFireTime = 0L;
        }
        if (this.doubleGunTime > 12000L) {
            this.doubleGunOn = false;
            this.doubleGunTime = 0L;
        }
        if (!this.spawnTime.isRunning()) {
            this.spawning = false;
            this.visible = true;
        }
        if (this.spawning && !this.flickerTime.isRunning()) {
            this.flickerTime.run(200L);
            this.visible = !this.visible;
        }
        if (KeyBoard.SHOOT && this.fireRate > this.fireSpeed && !this.spawning) {
            if (this.doubleGunOn) {
                Vector2D leftGun = this.getCenter();
                Vector2D rightGun = this.getCenter();
                Vector2D temp = new Vector2D(this.heading);
                temp.normalize();
                temp = temp.setDirection(this.angle - 1.2999999523162842);
                temp = temp.scale(this.width);
                rightGun = rightGun.add(temp);
                temp = temp.setDirection(this.angle - 1.899999976158142);
                leftGun = leftGun.add(temp);
                final Laser l = new Laser(leftGun, this.heading, 15.0, this.angle, Assets.blueLaser, this.gameState);
                final Laser r = new Laser(rightGun, this.heading, 15.0, this.angle, Assets.blueLaser, this.gameState);
                this.gameState.getMovingObjects().add(0, l);
                this.gameState.getMovingObjects().add(0, r);
            }
            else {
                this.gameState.getMovingObjects().add(0, new Laser(this.getCenter().add(this.heading.scale(this.width)), this.heading, 15.0, this.angle, Assets.blueLaser, this.gameState));
            }
            this.fireRate = 0L;
            this.shoot.play();
        }
        if (this.shoot.getFramePosition() > 8500) {
            this.shoot.stop();
        }
        if (KeyBoard.RIGHT) {
            this.angle += 0.1;
        }
        if (KeyBoard.LEFT) {
            this.angle -= 0.1;
        }
        if (KeyBoard.UP) {
            this.acceleration = this.heading.scale(0.2);
            this.accelerating = true;
        }
        else {
            if (this.velocity.getMagnitude() != 0.0) {
                this.acceleration = this.velocity.scale(-1.0).normalize().scale(0.1);
            }
            this.accelerating = false;
        }
        this.velocity = this.velocity.add(this.acceleration);
        this.velocity = this.velocity.limit(this.maxVel);
        this.heading = this.heading.setDirection(this.angle - 1.5707963267948966);
        this.position = this.position.add(this.velocity);
        if (this.position.getX() > 1000.0) {
            this.position.setX(0.0);
        }
        if (this.position.getY() > 600.0) {
            this.position.setY(0.0);
        }
        if (this.position.getX() < -this.width) {
            this.position.setX(1000.0);
        }
        if (this.position.getY() < -this.height) {
            this.position.setY(600.0);
        }
        this.spawnTime.update();
        this.flickerTime.update();
        this.collidesWith();
    }
    
    public void setShield() {
        if (this.shieldOn) {
            this.shieldTime = 0L;
        }
        this.shieldOn = true;
    }
    
    public void setDoubleScore() {
        if (this.doubleScoreOn) {
            this.doubleScoreTime = 0L;
        }
        this.doubleScoreOn = true;
    }
    
    public void setFastFire() {
        if (this.fastFireOn) {
            this.fastFireTime = 0L;
        }
        this.fastFireOn = true;
    }
    
    public void setDoubleGun() {
        if (this.doubleGunOn) {
            this.doubleGunTime = 0L;
        }
        this.doubleGunOn = true;
    }
    
    public void Destroy() {
        this.spawning = true;
        this.gameState.playExplosion(this.position);
        this.spawnTime.isRunning();
        this.loose.play();
        if (!this.gameState.subtractLife(this.position)) {
            this.gameState.gameOver();
            super.Destroy();
        }
        this.resetValues();
    }
    
    private void resetValues() {
        this.angle = 0.0;
        this.velocity = new Vector2D();
        this.position = GameState.PLAYER_START_POSITION;
    }
    
    @Override
    public void draw(final Graphics g) {
        if (!this.visible) {
            return;
        }
        final Graphics2D g2d = (Graphics2D)g;
        final AffineTransform at1 = AffineTransform.getTranslateInstance(this.position.getX() + this.width / 2 + 5.0, this.position.getY() + this.height / 2 + 10.0);
        final AffineTransform at2 = AffineTransform.getTranslateInstance(this.position.getX() + 5.0, this.position.getY() + this.height / 2 + 10.0);
        at1.rotate(this.angle, -5.0, -10.0);
        at2.rotate(this.angle, this.width / 2 - 5, -10.0);
        if (this.accelerating) {
            g2d.drawImage(Assets.speed, at1, null);
            g2d.drawImage(Assets.speed, at2, null);
        }
        if (this.shieldOn) {
            final BufferedImage currentFrame = this.shieldEffect.getCurrentFrame();
            final AffineTransform at3 = AffineTransform.getTranslateInstance(this.position.getX() - currentFrame.getWidth() / 2 + this.width / 2, this.position.getY() - currentFrame.getHeight() / 2 + this.height / 2);
            at3.rotate(this.angle, currentFrame.getWidth() / 2, currentFrame.getHeight() / 2);
            g2d.drawImage(this.shieldEffect.getCurrentFrame(), at3, null);
        }
        (this.at = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY())).rotate(this.angle, this.width / 2, this.height / 2);
        if (this.doubleGunOn) {
            g2d.drawImage(Assets.doubleGunPlayer, this.at, null);
        }
        else {
            g2d.drawImage(this.texture, this.at, null);
        }
    }
    
    public boolean isSpawning() {
        return this.spawning;
    }
    
    public boolean isShieldOn() {
        return this.shieldOn;
    }
    
    public boolean isDoubleScoreOn() {
        return this.doubleScoreOn;
    }
}

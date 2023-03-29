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
import math.Vector2D;
import java.awt.image.BufferedImage;
import graphics.Sound;
import ui.Action;

public class PowerUp extends MovingObject
{
    private long duration;
    private Action action;
    private Sound powerUpPick;
    private BufferedImage typeTexture;
    
    public PowerUp(final Vector2D position, final BufferedImage texture, final Action action, final GameState gameState) {
        super(position, new Vector2D(), 0.0, Assets.orb, gameState);
        this.action = action;
        this.typeTexture = texture;
        this.duration = 0L;
        this.powerUpPick = new Sound(Assets.powerUp);
    }
    
    void executeAction() {
        this.action.doAction();
        this.powerUpPick.play();
    }
    
    @Override
    public void update(final float dt) {
        this.angle += 0.1;
        this.duration += (long)dt;
        if (this.duration > 10000L) {
            this.Destroy();
        }
        this.collidesWith();
    }
    
    @Override
    public void draw(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g;
        (this.at = AffineTransform.getTranslateInstance(this.position.getX() + Assets.orb.getWidth() / 2 - this.typeTexture.getWidth() / 2, this.position.getY() + Assets.orb.getHeight() / 2 - this.typeTexture.getHeight() / 2)).rotate(this.angle, this.typeTexture.getWidth() / 2, this.typeTexture.getHeight() / 2);
        g.drawImage(Assets.orb, (int)this.position.getX(), (int)this.position.getY(), null);
        g2d.drawImage(this.typeTexture, this.at, null);
    }
}

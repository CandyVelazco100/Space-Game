// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import graphics.Assets;
import java.awt.image.BufferedImage;

public enum PowerUpTypes
{
    SHIELD("SHIELD", 0, "SHIELD", Assets.shield), 
    LIFE("LIFE", 1, "+1 LIFE", Assets.life), 
    SCORE_X2("SCORE_X2", 2, "SCORE x2", Assets.doubleScore), 
    FASTER_FIRE("FASTER_FIRE", 3, "FAST FIRE", Assets.fastFire), 
    SCORE_STACK("SCORE_STACK", 4, "+1000 SCORE", Assets.star), 
    DOUBLE_GUN("DOUBLE_GUN", 5, "DOUBLE GUN", Assets.doubleGun);
    
    public String text;
    public BufferedImage texture;
    
    private PowerUpTypes(final String name, final int ordinal, final String text, final BufferedImage texture) {
        this.text = text;
        this.texture = texture;
    }
}

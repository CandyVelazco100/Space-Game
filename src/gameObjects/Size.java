// 
// Decompiled by Procyon v0.5.36
// 

package gameObjects;

import graphics.Assets;
import java.awt.image.BufferedImage;

public enum Size
{
    BIG("BIG", 0, 2, Assets.meds), 
    MED("MED", 1, 2, Assets.smalls), 
    SMALL("SMALL", 2, 2, Assets.tinies), 
    TINY("TINY", 3, 0, (BufferedImage[])null);
    
    public int quantity;
    public BufferedImage[] textures;
    
    private Size(final String name, final int ordinal, final int quantity, final BufferedImage[] textures) {
        this.quantity = quantity;
        this.textures = textures;
    }
}

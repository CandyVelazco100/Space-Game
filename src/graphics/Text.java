// 
// Decompiled by Procyon v0.5.36
// 

package graphics;

import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Color;
import math.Vector2D;
import java.awt.Graphics;

public class Text
{
    public static void drawText(final Graphics g, final String text, final Vector2D pos, final boolean center, final Color color, final Font font) {
        g.setColor(color);
        g.setFont(font);
        final Vector2D position = new Vector2D(pos.getX(), pos.getY());
        if (center) {
            final FontMetrics fm = g.getFontMetrics();
            position.setX(position.getX() - fm.stringWidth(text) / 2);
            position.setY(position.getY() - fm.getHeight() / 2);
        }
        g.drawString(text, (int)position.getX(), (int)position.getY());
    }
}

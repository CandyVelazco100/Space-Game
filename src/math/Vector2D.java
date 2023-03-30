// 
// Decompiled by Procyon v0.5.36
// 

package math;

public class Vector2D
{
    private double x;
    private double y;
    
    public Vector2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2D(final Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }
    
    public Vector2D() {
        this.x = 0.0;
        this.y = 0.0;
    }
    
    public Vector2D add(final Vector2D v) {
        return new Vector2D(this.x + v.getX(), this.y + v.getY());
    }
    
    public Vector2D subtract(final Vector2D v) {
        return new Vector2D(this.x - v.getX(), this.y - v.getY());
    }
    
    public Vector2D scale(final double value) {
        return new Vector2D(this.x * value, this.y * value);
    }
    
    public Vector2D limit(final double value) {
        if (this.getMagnitude() > value) {
            return this.normalize().scale(value);
        }
        return this;
    }
    
    public Vector2D normalize() {
        final double magnitude = this.getMagnitude();
        return new Vector2D(this.x / magnitude, this.y / magnitude);
    }
    
    public double getMagnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    public Vector2D setDirection(final double angle) {
        final double magnitude = this.getMagnitude();
        return new Vector2D(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }
    
    public double getAngle() {
        return Math.asin(this.y / this.getMagnitude());
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
}

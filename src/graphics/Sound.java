// 
// Decompiled by Procyon v0.5.36
// 

package graphics;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Clip;

public class Sound
{
    private Clip clip;
    private FloatControl volume;
    
    public Sound(final Clip clip) {
        this.clip = clip;
        this.volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
    }
    
    public void play() {
        this.clip.setFramePosition(0);
        this.clip.start();
    }
    
    public void loop() {
        this.clip.setFramePosition(0);
        this.clip.loop(-1);
    }
    
    public void stop() {
        this.clip.stop();
    }
    
    public int getFramePosition() {
        return this.clip.getFramePosition();
    }
    
    public void changeVolume(final float value) {
        this.volume.setValue(value);
    }
}

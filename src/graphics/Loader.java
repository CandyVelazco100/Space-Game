// 
// Decompiled by Procyon v0.5.36
// 

package graphics;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.FontFormatException;
import java.awt.Font;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Loader{
	public static BufferedImage ImageLoader(String path){
		try {
			return ImageIO.read(Loader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Font loadFont(String path, int size) {
			try {
				return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
			} catch (FontFormatException  e) {
				e.printStackTrace();
				return null;
			}catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public static Clip loadSound(String path) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Loader.class.getResource(path)));
			return clip;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		return null;
	}
}
// 
// Decompiled by Procyon v0.5.36
// 

package graphics;

import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets
{
    public static boolean loaded;
    public static float count;
    public static float MAX_COUNT;
    public static BufferedImage player;
    public static BufferedImage doubleGunPlayer;
    public static BufferedImage speed;
    public static BufferedImage[] exp;
    public static BufferedImage blueLaser;
    public static BufferedImage greenLaser;
    public static BufferedImage redLaser;
    public static BufferedImage[] bigs;
    public static BufferedImage[] meds;
    public static BufferedImage[] smalls;
    public static BufferedImage[] tinies;
    public static BufferedImage ufo;
    public static BufferedImage[] numbers;
    public static BufferedImage life;
    public static Font fontBig;
    public static Font fontMed;
    public static Clip backgroundMusic;
    public static Clip explosion;
    public static Clip playerLoose;
    public static Clip playerShoot;
    public static Clip ufoShoot;
    public static Clip powerUp;
    public static BufferedImage blueBtn;
    public static BufferedImage greyBtn;
    public static BufferedImage orb;
    public static BufferedImage doubleScore;
    public static BufferedImage doubleGun;
    public static BufferedImage fastFire;
    public static BufferedImage shield;
    public static BufferedImage star;
    public static BufferedImage[] shieldEffect;
    
    static {
        Assets.loaded = false;
        Assets.count = 0.0f;
        Assets.MAX_COUNT = 57.0f;
        Assets.exp = new BufferedImage[9];
        Assets.bigs = new BufferedImage[4];
        Assets.meds = new BufferedImage[2];
        Assets.smalls = new BufferedImage[2];
        Assets.tinies = new BufferedImage[2];
        Assets.numbers = new BufferedImage[11];
        Assets.shieldEffect = new BufferedImage[3];
    }
    
    public static void init() {
        Assets.player = loadImage("/ships/player.png");
        Assets.doubleGunPlayer = loadImage("/ships/doubleGunPlayer.png");
        Assets.speed = loadImage("/effects/fire08.png");
        Assets.blueLaser = loadImage("/lasers/laserBlue01.png");
        Assets.greenLaser = loadImage("/lasers/laserGreen11.png");
        Assets.redLaser = loadImage("/lasers/laserRed01.png");
        Assets.ufo = loadImage("/ships/ufo.png");
        Assets.life = loadImage("/others/life.png");
        Assets.fontBig = loadFont("/fonts/futureFont.ttf", 42);
        Assets.fontMed = loadFont("/fonts/futureFont.ttf", 20);
        for (int i = 0; i < 3; ++i) {
            Assets.shieldEffect[i] = loadImage("/effects/shield" + (i + 1) + ".png");
        }
        for (int i = 0; i < Assets.bigs.length; ++i) {
            Assets.bigs[i] = loadImage("/meteors/big" + (i + 1) + ".png");
        }
        for (int i = 0; i < Assets.meds.length; ++i) {
            Assets.meds[i] = loadImage("/meteors/med" + (i + 1) + ".png");
        }
        for (int i = 0; i < Assets.smalls.length; ++i) {
            Assets.smalls[i] = loadImage("/meteors/small" + (i + 1) + ".png");
        }
        for (int i = 0; i < Assets.tinies.length; ++i) {
            Assets.tinies[i] = loadImage("/meteors/tiny" + (i + 1) + ".png");
        }
        for (int i = 0; i < Assets.exp.length; ++i) {
            Assets.exp[i] = loadImage("/explosion/" + i + ".png");
        }
        for (int i = 0; i < Assets.numbers.length; ++i) {
            Assets.numbers[i] = loadImage("/numbers/" + i + ".png");
        }
        Assets.backgroundMusic = loadSound("/sounds/backgroundMusic.wav");
        Assets.explosion = loadSound("/sounds/explosion.wav");
        Assets.playerLoose = loadSound("/sounds/playerLoose.wav");
        Assets.playerShoot = loadSound("/sounds/playerShoot.wav");
        Assets.ufoShoot = loadSound("/sounds/ufoShoot.wav");
        Assets.powerUp = loadSound("/sounds/powerUp.wav");
        Assets.greyBtn = loadImage("/ui/grey_button.png");
        Assets.blueBtn = loadImage("/ui/blue_button.png");
        Assets.orb = loadImage("/powers/orb.png");
        Assets.doubleScore = loadImage("/powers/doubleScore.png");
        Assets.doubleGun = loadImage("/powers/doubleGun.png");
        Assets.fastFire = loadImage("/powers/fastFire.png");
        Assets.star = loadImage("/powers/star.png");
        Assets.shield = loadImage("/powers/shield.png");
        Assets.loaded = true;
    }
    
    public static BufferedImage loadImage(final String path) {
        ++Assets.count;
        return Loader.ImageLoader(path);
    }
    
    public static Font loadFont(final String path, final int size) {
        ++Assets.count;
        return Loader.loadFont(path, size);
    }
    
    public static Clip loadSound(final String path) {
        ++Assets.count;
        return Loader.loadSound(path);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package states;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.io.IOException;
import io.ScoreData;
import io.JSONParser;
import gameObjects.PowerUp;
import ui.Action;
import gameObjects.PowerUpTypes;
import gameObjects.Ufo;
import java.awt.image.BufferedImage;
import gameObjects.Size;
import gameObjects.Meteor;
import java.awt.Color;
import graphics.Assets;
import gameObjects.Chronometer;
import graphics.Sound;
import gameObjects.Message;
import graphics.Animation;
import gameObjects.MovingObject;
import java.util.ArrayList;
import gameObjects.Player;
import math.Vector2D;

public class GameState extends State
{
    public static final Vector2D PLAYER_START_POSITION;
    private Player player;
    private ArrayList<MovingObject> movingObjects;
    private ArrayList<Animation> explosions;
    private ArrayList<Message> messages;
    private int score;
    private int lives;
    private int meteors;
    private int waves;
    private Sound backgroundMusic;
    private Chronometer ufoSpawner;
    private long gameOverTimer;
    private long powerUpSpawner;
    private boolean gameOver;
    
    static {
        PLAYER_START_POSITION = new Vector2D(500 - Assets.player.getWidth() / 2, 300 - Assets.player.getHeight() / 2);
    }
    
    public GameState() {
        this.movingObjects = new ArrayList<MovingObject>();
        this.explosions = new ArrayList<Animation>();
        this.messages = new ArrayList<Message>();
        this.score = 0;
        this.lives = 3;
        this.waves = 1;
        this.player = new Player(GameState.PLAYER_START_POSITION, new Vector2D(), 7.0, Assets.player, this);
        this.gameOverTimer = 0L;
        this.gameOver = false;
        this.movingObjects.add(this.player);
        this.meteors = 1;
        this.startWave();
        (this.backgroundMusic = new Sound(Assets.backgroundMusic)).loop();
        this.backgroundMusic.changeVolume(-10.0f);
        this.ufoSpawner = new Chronometer();
        this.powerUpSpawner = 0L;
        this.gameOver = false;
    }
    
    public void addScore(int value, final Vector2D position) {
        Color c = Color.WHITE;
        String text = "+" + value + " score";
        if (this.player.isDoubleScoreOn()) {
            c = Color.YELLOW;
            value *= 2;
            text = "+" + value + " score" + " (X2)";
        }
        this.score += value;
        this.messages.add(new Message(position, true, text, c, false, Assets.fontMed));
    }
    
    public void divideMeteor(final Meteor meteor) {
        final Size size = meteor.getSize();
        final BufferedImage[] textures = size.textures;
        Size newSize = null;
        switch (size) {
            case BIG: {
                newSize = Size.MED;
                break;
            }
            case MED: {
                newSize = Size.SMALL;
                break;
            }
            case SMALL: {
                newSize = Size.TINY;
                break;
            }
            default: {
                return;
            }
        }
        for (int i = 0; i < size.quantity; ++i) {
            this.movingObjects.add(new Meteor(meteor.getPosition(), new Vector2D(0.0, 1.0).setDirection(Math.random() * 3.141592653589793 * 2.0), 2.0 * Math.random() + 1.0, textures[(int)(Math.random() * textures.length)], this, newSize));
        }
    }
    
    private void startWave() {
        this.messages.add(new Message(new Vector2D(500.0, 300.0), false, "WAVE " + this.waves, Color.WHITE, true, Assets.fontBig));
        for (int i = 0; i < this.meteors; ++i) {
            final double x = (i % 2 == 0) ? (Math.random() * 1000.0) : 0.0;
            final double y = (i % 2 == 0) ? 0.0 : (Math.random() * 600.0);
            final BufferedImage texture = Assets.bigs[(int)(Math.random() * Assets.bigs.length)];
            this.movingObjects.add(new Meteor(new Vector2D(x, y), new Vector2D(0.0, 1.0).setDirection(Math.random() * 3.141592653589793 * 2.0), 2.0 * Math.random() + 1.0, texture, this, Size.BIG));
        }
        ++this.meteors;
    }
    
    public void playExplosion(final Vector2D position) {
        this.explosions.add(new Animation(Assets.exp, 50, position.subtract(new Vector2D(Assets.exp[0].getWidth() / 2, Assets.exp[0].getHeight() / 2))));
    }
    
    private void spawnUfo() {
        final int rand = (int)(Math.random() * 2.0);
        final double x = (rand == 0) ? (Math.random() * 1000.0) : 1000.0;
        final double y = (rand == 0) ? 600.0 : (Math.random() * 600.0);
        final ArrayList<Vector2D> path = new ArrayList<Vector2D>();
        double posX = Math.random() * 1000.0 / 2.0;
        double posY = Math.random() * 600.0 / 2.0;
        path.add(new Vector2D(posX, posY));
        posX = Math.random() * 500.0 + 500.0;
        posY = Math.random() * 600.0 / 2.0;
        path.add(new Vector2D(posX, posY));
        posX = Math.random() * 1000.0 / 2.0;
        posY = Math.random() * 300.0 + 300.0;
        path.add(new Vector2D(posX, posY));
        posX = Math.random() * 500.0 + 500.0;
        posY = Math.random() * 300.0 + 300.0;
        path.add(new Vector2D(posX, posY));
        this.movingObjects.add(new Ufo(new Vector2D(x, y), new Vector2D(), 3.0, Assets.ufo, path, this));
    }
    
    private void spawnPowerUp() {
        final int x = (int)((1000 - Assets.orb.getWidth()) * Math.random());
        final int y = (int)((600 - Assets.orb.getHeight()) * Math.random());
        final int index = (int)(Math.random() * PowerUpTypes.values().length);
        final PowerUpTypes p = PowerUpTypes.values()[index];
        final String text = p.text;
        Action action = null;
        final Vector2D position = new Vector2D(x, y);
        switch (p) {
            case LIFE: {
                action = new Action() {
                    @Override
                    public void doAction() {
                        final GameState this$0 = GameState.this;
                        GameState.access$2(this$0, this$0.lives + 1);
                        GameState.this.messages.add(new Message(position, false, text, Color.GREEN, false, Assets.fontMed));
                    }
                };
                break;
            }
            case SHIELD: {
                action = new Action() {
                    @Override
                    public void doAction() {
                        GameState.this.player.setShield();
                        GameState.this.messages.add(new Message(position, false, text, Color.DARK_GRAY, false, Assets.fontMed));
                    }
                };
                break;
            }
            case SCORE_X2: {
                action = new Action() {
                    @Override
                    public void doAction() {
                        GameState.this.player.setDoubleScore();
                        GameState.this.messages.add(new Message(position, false, text, Color.YELLOW, false, Assets.fontMed));
                    }
                };
                break;
            }
            case FASTER_FIRE: {
                action = new Action() {
                    @Override
                    public void doAction() {
                        GameState.this.player.setFastFire();
                        GameState.this.messages.add(new Message(position, false, text, Color.BLUE, false, Assets.fontMed));
                    }
                };
                break;
            }
            case SCORE_STACK: {
                action = new Action() {
                    @Override
                    public void doAction() {
                        final GameState this$0 = GameState.this;
                        GameState.access$6(this$0, this$0.score + 1000);
                        GameState.this.messages.add(new Message(position, false, text, Color.MAGENTA, false, Assets.fontMed));
                    }
                };
                break;
            }
            case DOUBLE_GUN: {
                action = new Action() {
                    @Override
                    public void doAction() {
                        GameState.this.player.setDoubleGun();
                        GameState.this.messages.add(new Message(position, false, text, Color.ORANGE, false, Assets.fontMed));
                    }
                };
                break;
            }
        }
        this.movingObjects.add(new PowerUp(position, p.texture, action, this));
    }
    
    @Override
    public void update(final float dt) {
        if (this.gameOver) {
            this.gameOverTimer += (long)dt;
        }
        this.powerUpSpawner += (long)dt;
        for (int i = 0; i < this.movingObjects.size(); ++i) {
            final MovingObject mo = this.movingObjects.get(i);
            mo.update(dt);
            if (mo.isDead()) {
                this.movingObjects.remove(i);
                --i;
            }
        }
        for (int i = 0; i < this.explosions.size(); ++i) {
            final Animation anim = this.explosions.get(i);
            anim.update(dt);
            if (!anim.isRunning()) {
                this.explosions.remove(i);
            }
        }
        if (this.gameOverTimer > 2000L) {
            try {
                final ArrayList<ScoreData> dataList = JSONParser.readFile();
                dataList.add(new ScoreData(this.score));
                JSONParser.writeFile(dataList);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.backgroundMusic.stop();
            State.changeState(new MenuState());
        }
        if (this.powerUpSpawner > 8000L) {
            this.spawnPowerUp();
            this.powerUpSpawner = 0L;
        }
        if (!this.ufoSpawner.isRunning()) {
            this.ufoSpawner.run(10000L);
            this.spawnUfo();
        }
        this.ufoSpawner.update();
        for (int i = 0; i < this.movingObjects.size(); ++i) {
            if (this.movingObjects.get(i) instanceof Meteor) {
                return;
            }
        }
        this.startWave();
    }
    
    @Override
    public void draw(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        for (int i = 0; i < this.messages.size(); ++i) {
            this.messages.get(i).draw(g2d);
            if (this.messages.get(i).isDead()) {
                this.messages.remove(i);
            }
        }
        for (int i = 0; i < this.movingObjects.size(); ++i) {
            this.movingObjects.get(i).draw(g);
        }
        for (int i = 0; i < this.explosions.size(); ++i) {
            final Animation anim = this.explosions.get(i);
            g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPosition().getX(), (int)anim.getPosition().getY(), null);
        }
        this.drawScore(g);
        this.drawLives(g);
    }
    
    private void drawScore(final Graphics g) {
        final Vector2D pos = new Vector2D(850.0, 25.0);
        final String scoreToString = Integer.toString(this.score);
        for (int i = 0; i < scoreToString.length(); ++i) {
            g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))], (int)pos.getX(), (int)pos.getY(), null);
            pos.setX(pos.getX() + 20.0);
        }
    }
    
    private void drawLives(final Graphics g) {
        if (this.lives < 1) {
            return;
        }
        final Vector2D livePosition = new Vector2D(25.0, 25.0);
        g.drawImage(Assets.life, (int)livePosition.getX(), (int)livePosition.getY(), null);
        g.drawImage(Assets.numbers[10], (int)livePosition.getX() + 40, (int)livePosition.getY() + 5, null);
        final String livesToString = Integer.toString(this.lives);
        final Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
        for (int i = 0; i < livesToString.length(); ++i) {
            final int number = Integer.parseInt(livesToString.substring(i, i + 1));
            if (number <= 0) {
                break;
            }
            g.drawImage(Assets.numbers[number], (int)pos.getX() + 60, (int)pos.getY() + 5, null);
            pos.setX(pos.getX() + 20.0);
        }
    }
    
    public ArrayList<MovingObject> getMovingObjects() {
        return this.movingObjects;
    }
    
    public ArrayList<Message> getMessages() {
        return this.messages;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean subtractLife(final Vector2D position) {
        --this.lives;
        final Message lifeLostMesg = new Message(position, false, "-1 LIFE", Color.RED, false, Assets.fontMed);
        this.messages.add(lifeLostMesg);
        return this.lives > 0;
    }
    
    public void gameOver() {
        final Message gameOverMsg = new Message(GameState.PLAYER_START_POSITION, true, "GAME OVER", Color.WHITE, true, Assets.fontBig);
        this.messages.add(gameOverMsg);
        this.gameOver = true;
    }
    
    static /* synthetic */ void access$2(final GameState gameState, final int lives) {
        gameState.lives = lives;
    }
    
    static /* synthetic */ void access$6(final GameState gameState, final int score) {
        gameState.score = score;
    }
}

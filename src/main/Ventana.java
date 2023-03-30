// 
// Decompiled by Procyon v0.5.36
// 

package main;
import states.LoadingState;
import graphics.Assets;
import java.awt.Color;
import states.State;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.Component;
import input.MouseInput;
import input.KeyBoard;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import javax.swing.JFrame;

public class Ventana extends JFrame implements Runnable{
    private static final long serialVersionUID = 1L;
    private Canvas canvas;
    private Thread thread;
    private boolean running;
    private BufferStrategy bs;
    private Graphics g;
    private final int FPS = 60;
    private double TT;
    private double delta;
    private int AVERAGEFPS;
    private KeyBoard keyBoard;
    private MouseInput mouseInput;
    
    public Ventana() {
        this.running = false;
        this.TT = 1.6666666E7;
        this.delta = 0.0;
        this.AVERAGEFPS = 60;
        this.setTitle("Space Ship Game");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.canvas = new Canvas();
        this.keyBoard = new KeyBoard();
        this.mouseInput = new MouseInput();
        this.canvas.setPreferredSize(new Dimension(1000, 600));
        this.canvas.setMaximumSize(new Dimension(1000, 600));
        this.canvas.setMinimumSize(new Dimension(1000, 600));
        this.canvas.setFocusable(true);
        this.add(this.canvas);
        this.canvas.addKeyListener(this.keyBoard);
        this.canvas.addMouseListener(this.mouseInput);
        this.canvas.addMouseMotionListener(this.mouseInput);
        this.setVisible(true);
    }
    
    public static void main(final String[] args) {
        new Ventana().start();
    }
    
    private void update(final float dt) {
        this.keyBoard.update();
        State.getCurrentState().update(dt);
    }
    
    private void draw() {
        this.bs = this.canvas.getBufferStrategy();
        if (this.bs == null) {
            this.canvas.createBufferStrategy(3);
            return;
        }
        (this.g = this.bs.getDrawGraphics()).setColor(Color.BLACK);
        this.g.fillRect(0, 0, 1000, 600);
        State.getCurrentState().draw(this.g);
        this.g.setColor(Color.BLACK);
        this.g.drawString(new StringBuilder().append(this.AVERAGEFPS).toString(), 10, 20);
        this.g.dispose();
        this.bs.show();
    }
    
    private void init() {
        final Thread loadingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Assets.init();
            }
        });
        State.changeState(new LoadingState(loadingThread));
    }
    
    @Override
    public void run() {
        long now = 0L;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0L;
        this.init();
        while (this.running) {
            now = System.nanoTime();
            this.delta += (now - lastTime) / this.TT;
            time += now - lastTime;
            lastTime = now;
            if (this.delta >= 1.0) {
                this.update((float)(this.delta * this.TT * 9.999999974752427E-7));
                this.draw();
                --this.delta;
                ++frames;
            }
            if (time >= 1000000000L) {
                this.AVERAGEFPS = frames;
                frames = 0;
                time = 0L;
            }
        }
        this.stop();
    }
    
    private void start() {
        (this.thread = new Thread(this)).start();
        this.running = true;
    }
    
    private void stop() {
        try {
            this.thread.join();
            this.running = false;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

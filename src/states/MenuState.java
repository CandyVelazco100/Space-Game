// 
// Decompiled by Procyon v0.5.36
// 

package states;

import java.awt.Graphics;
import java.util.Iterator;
import ui.Action;
import graphics.Assets;
import ui.Button;
import java.util.ArrayList;

public class MenuState extends State
{
    private ArrayList<Button> buttons;
    
    public MenuState() {
        (this.buttons = new ArrayList<Button>()).add(new Button(Assets.greyBtn, Assets.blueBtn, 500 - Assets.greyBtn.getWidth() / 2, 300 - Assets.greyBtn.getHeight() * 2, "PLAY", new Action() {
            @Override
            public void doAction() {
                State.changeState(new GameState());
            }
        }));
        this.buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, 500 - Assets.greyBtn.getWidth() / 2, 300 + Assets.greyBtn.getHeight() * 2, "EXIT", new Action() {
            @Override
            public void doAction() {
                System.exit(0);
            }
        }));
        this.buttons.add(new Button(Assets.greyBtn, Assets.blueBtn, 500 - Assets.greyBtn.getWidth() / 2, 300, "HIGHEST SCORES", new Action() {
            @Override
            public void doAction() {
                State.changeState(new ScoreState());
            }
        }));
    }
    
    @Override
    public void update(final float dt) {
        for (final Button b : this.buttons) {
            b.update();
        }
    }
    
    @Override
    public void draw(final Graphics g) {
        for (final Button b : this.buttons) {
            b.draw(g);
        }
    }
}

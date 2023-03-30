// 
// Decompiled by Procyon v0.5.36
// 

package states;

import graphics.Text;
import java.awt.Color;
import math.Vector2D;
import java.util.Arrays;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import io.JSONParser;
import ui.Action;
import graphics.Assets;
import java.util.Comparator;
import io.ScoreData;
import java.util.PriorityQueue;
import ui.Button;

public class ScoreState extends State
{
    private Button returnButton;
    private PriorityQueue<ScoreData> highScores;
    private Comparator<ScoreData> scoreComparator;
    private ScoreData[] auxArray;
    
    public ScoreState() {
        this.returnButton = new Button(Assets.greyBtn, Assets.blueBtn, Assets.greyBtn.getHeight(), 600 - Assets.greyBtn.getHeight() * 2, "RETURN", new Action() {
            @Override
            public void doAction() {
                State.changeState(new MenuState());
            }
        });
        this.scoreComparator = new Comparator<ScoreData>() {
            @Override
            public int compare(final ScoreData e1, final ScoreData e2) {
                return (e1.getScore() < e2.getScore()) ? -1 : ((e1.getScore() > e2.getScore()) ? 1 : 0);
            }
        };
        this.highScores = new PriorityQueue<ScoreData>(10, this.scoreComparator);
        try {
            final ArrayList<ScoreData> dataList = JSONParser.readFile();
            for (final ScoreData d : dataList) {
                this.highScores.add(d);
            }
            while (this.highScores.size() > 10) {
                this.highScores.poll();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(final float dt) {
        this.returnButton.update();
    }
    
    @Override
    public void draw(final Graphics g) {
        this.returnButton.draw(g);
        Arrays.sort(this.auxArray = this.highScores.toArray(new ScoreData[this.highScores.size()]), this.scoreComparator);
        final Vector2D scorePos = new Vector2D(300.0, 100.0);
        final Vector2D datePos = new Vector2D(700.0, 100.0);
        Text.drawText(g, "SCORE", scorePos, true, Color.BLUE, Assets.fontBig);
        Text.drawText(g, "DATE", datePos, true, Color.BLUE, Assets.fontBig);
        scorePos.setY(scorePos.getY() + 40.0);
        datePos.setY(datePos.getY() + 40.0);
        for (int i = this.auxArray.length - 1; i > -1; --i) {
            final ScoreData d = this.auxArray[i];
            Text.drawText(g, Integer.toString(d.getScore()), scorePos, true, Color.WHITE, Assets.fontMed);
            Text.drawText(g, d.getDate(), datePos, true, Color.WHITE, Assets.fontMed);
            scorePos.setY(scorePos.getY() + 40.0);
            datePos.setY(datePos.getY() + 40.0);
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package io;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreData{
    private String date;
    private int score;
    
    public ScoreData(final int score) {
        this.score = score;
        final Date today = new Date(System.currentTimeMillis());
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.date = format.format(today);
    }
    
    public ScoreData() {
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setDate(final String date) {
        this.date = date;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public void setScore(final int score) {
        this.score = score;
    }
}
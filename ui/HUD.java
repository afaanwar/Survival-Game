package com.helwan.survivalgame.ui;

import java.awt.*;

public class HUD {
    private int health;
    private int score;

    public HUD() {
        health = 100;
        score = 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Health: " + health, 20, 30);
        g.drawString("Score: " + score, 20, 60);
    }
}

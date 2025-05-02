package com.helwan.survivalgame.entities;

import java.awt.*;

public class Enemy {
    private int x, y;
    private int size;
    private int speed;
    private int directionX, directionY;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = 30;
        this.speed = 2;
        this.directionX = 1;
        this.directionY = 1;
    }

    public void update() {
        x += speed * directionX;
        y += speed * directionY;

        // Simple boundary check and direction change
        if (x < 0 || x > 770) {
            directionX = -directionX;
        }
        if (y < 0 || y > 570) {
            directionY = -directionY;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

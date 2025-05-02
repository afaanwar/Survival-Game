package com.helwan.survivalgame.entities;

import java.awt.*;

public class Item {
    private int x, y;
    private int size;

    public Item(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = 20;
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

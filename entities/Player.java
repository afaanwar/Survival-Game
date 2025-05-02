package com.helwan.survivalgame.entities;

import com.helwan.survivalgame.engine.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player {
    private int x, y;
    private int speed;
    private BufferedImage sprite;
    private int health;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 5;
        this.health = 3;
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream("/player.png"));
        } catch (IOException | IllegalArgumentException e) {
            sprite = null;
            System.out.println("Player sprite not found, using default.");
        }
    }

    public void update(InputManager input) {
        if (input.isKeyPressed(KeyEvent.VK_W) || input.isKeyPressed(KeyEvent.VK_UP)) {
            y -= speed;
        }
        if (input.isKeyPressed(KeyEvent.VK_S) || input.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += speed;
        }
        if (input.isKeyPressed(KeyEvent.VK_A) || input.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= speed;
        }
        if (input.isKeyPressed(KeyEvent.VK_D) || input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += speed;
        }
    }

    public void render(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 30, 30);
        }
    }

    public void renderAt(Graphics g, int drawX, int drawY) {
        if (sprite != null) {
            g.drawImage(sprite, drawX, drawY, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(drawX, drawY, 30, 30);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth() {
        if (health > 0) {
            health--;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }
}

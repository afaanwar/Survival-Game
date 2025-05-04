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
    private int health;

    private static BufferedImage spriteSheet;
    private static BufferedImage[] frames;

    private static final int FRAME_WIDTH = 32;  // width of one frame in the sprite sheet
    private static final int FRAME_HEIGHT = 32; // height of one frame in the sprite sheet

    private static int FRAMES_PER_ROW;
    private static int TOTAL_FRAMES;

    private int currentFrame;
    private long lastFrameTime;
    private long frameDuration = 100; // milliseconds per frame


    private static final int SCALE_WIDTH = 96;  // scaled width for rendering
    private static final int SCALE_HEIGHT = 96; // scaled height for rendering


    public int currentDirection;

    public enum Direction {
        DOWN, LEFT, RIGHT, UP
    }

    static {
        try {
            spriteSheet = ImageIO.read(Player.class.getResourceAsStream("/textures/Player/player.png"));
            int sheetWidth = spriteSheet.getWidth();
            int sheetHeight = spriteSheet.getHeight();

            FRAMES_PER_ROW = sheetWidth / FRAME_WIDTH;
            int rows = sheetHeight / FRAME_HEIGHT;
            TOTAL_FRAMES = FRAMES_PER_ROW * rows;

            frames = new BufferedImage[TOTAL_FRAMES];
            for (int i = 0; i < TOTAL_FRAMES; i++) {
                int frameX = (i % FRAMES_PER_ROW) * FRAME_WIDTH;
                int frameY = (i / FRAMES_PER_ROW) * FRAME_HEIGHT;
                frames[i] = spriteSheet.getSubimage(frameX, frameY, FRAME_WIDTH, FRAME_HEIGHT);
            }
        } catch (IOException | IllegalArgumentException e) {
            spriteSheet = null;
            frames = null;
            System.out.println("Player sprite sheet not found or invalid, using default.");
        }
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 5;
        this.health = 3;
        this.currentDirection = 0;
        currentFrame = 0;
        lastFrameTime = System.currentTimeMillis();
    }

    public void update(InputManager input) {
        boolean moving = false;

        if (input.isKeyPressed(KeyEvent.VK_W) || input.isKeyPressed(KeyEvent.VK_UP)) {
            y -= speed;
            moving = true;
        }
        if (input.isKeyPressed(KeyEvent.VK_S) || input.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += speed;
            moving = true;
        }
        if (input.isKeyPressed(KeyEvent.VK_A) || input.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= speed;
            moving = true;
        }
        if (input.isKeyPressed(KeyEvent.VK_D) || input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += speed;
            moving = true;
        }

        if (moving) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameTime > frameDuration) {
                currentFrame = (currentFrame + 1) % FRAMES_PER_ROW;
                lastFrameTime = currentTime;
            }
        } else {
            currentFrame = 0; // reset to first frame when not moving
        }
    }

    public void render(Graphics g) {
        if (frames != null) {
            g.drawImage(frames[currentDirection], x, y, SCALE_WIDTH, SCALE_HEIGHT, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 30, 30);
        }
    }

    public void renderAt(Graphics g, int drawX, int drawY) {
        if (frames != null) {
            g.drawImage(frames[currentDirection], drawX, drawY, SCALE_WIDTH, SCALE_HEIGHT, null);
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

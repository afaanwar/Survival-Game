package com.helwan.survivalgame.scenes;

import com.helwan.survivalgame.Game;
import com.helwan.survivalgame.entities.Player;
import com.helwan.survivalgame.entities.Enemy;
import com.helwan.survivalgame.entities.Item;
import com.helwan.survivalgame.engine.AudioManager;
import com.helwan.survivalgame.engine.InputManager;
import com.helwan.survivalgame.story.StoryManager;
import com.helwan.survivalgame.ui.DialogManager;
import com.helwan.survivalgame.ui.HUD;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene {
    private Player player;
    private List<Enemy> enemies;
    private List<Item> items;
    private boolean paused;
    private HUD hud;
    private DialogManager dialogManager;
    private StoryManager storyManager;
    private boolean dialogVisible;
    private List<Rectangle> redBoxes;

    private static final int TILE_SIZE = 64;
    private static final int MAP_WIDTH = 50;
    private static final int MAP_HEIGHT = 50;

    private int[][] mapTiles;
    private int cameraX, cameraY;

    public GameScene(Game game) {
        super(game);
        player = new Player(TILE_SIZE * 2, TILE_SIZE * 2);
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        paused = false;
        hud = new HUD();
        dialogManager = new DialogManager();
        storyManager = new StoryManager();
        dialogVisible = false;
        redBoxes = new ArrayList<>();

        mapTiles = new int[MAP_HEIGHT][MAP_WIDTH];
        generateMap();

        cameraX = 0;
        cameraY = 0;
    }

    private void generateMap() {
        // 0 = floor, 1 = wall, 2 = door, 3 = hazard (red box), 4 = start, 5 = end
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (x == 0 || y == 0 || x == MAP_WIDTH - 1 || y == MAP_HEIGHT - 1) {
                    mapTiles[y][x] = 1; // walls around edges
                } else {
                    mapTiles[y][x] = 0; // floor
                }
            }
        }

        // Add some walls inside
        for (int x = 10; x < 40; x++) {
            mapTiles[10][x] = 1;
            mapTiles[20][x] = 1;
            mapTiles[30][x] = 1;
        }

        // Add doors
        mapTiles[10][25] = 2;
        mapTiles[20][15] = 2;
        mapTiles[30][35] = 2;

        // Add hazards (red boxes)
        mapTiles[15][15] = 3;
        mapTiles[25][25] = 3;
        mapTiles[35][35] = 3;

        // Start and end
        mapTiles[2][2] = 4;
        mapTiles[47][47] = 5;
    }

    private boolean isWalkable(int tile) {
        return tile == 0 || tile == 2 || tile == 4 || tile == 5;
    }

    private void updateCamera() {
        cameraX = player.getX() - game.getWidth() / 2;
        cameraY = player.getY() - game.getHeight() / 2;

        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        int maxX = MAP_WIDTH * TILE_SIZE - game.getWidth();
        int maxY = MAP_HEIGHT * TILE_SIZE - game.getHeight();
        if (cameraX > maxX) cameraX = maxX;
        if (cameraY > maxY) cameraY = maxY;
    }

    @Override
    public void update() {
        InputManager input = InputManager.getInstance();

        if (dialogVisible) {
            if (input.isKeyPressed(KeyEvent.VK_ENTER)) {
                dialogManager.hideDialog();
                dialogVisible = false;
                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
            return;
        }

        if (paused) {
            if (input.isKeyPressed(KeyEvent.VK_P) || input.isKeyPressed(KeyEvent.VK_ENTER) || input.isKeyPressed(KeyEvent.VK_ESCAPE) || input.isKeyPressed(KeyEvent.VK_SPACE)) {
                paused = false;
                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
            return;
        }

        int newX = player.getX();
        int newY = player.getY();

        if (input.isKeyPressed(KeyEvent.VK_W) || input.isKeyPressed(KeyEvent.VK_UP)) {
            newY -= player.getSpeed();
        }
        if (input.isKeyPressed(KeyEvent.VK_S) || input.isKeyPressed(KeyEvent.VK_DOWN)) {
            newY += player.getSpeed();
        }
        if (input.isKeyPressed(KeyEvent.VK_A) || input.isKeyPressed(KeyEvent.VK_LEFT)) {
            newX -= player.getSpeed();
        }
        if (input.isKeyPressed(KeyEvent.VK_D) || input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            newX += player.getSpeed();
        }

        int tileX = newX / TILE_SIZE;
        int tileY = newY / TILE_SIZE;

        if (tileX >= 0 && tileX < MAP_WIDTH && tileY >= 0 && tileY < MAP_HEIGHT && isWalkable(mapTiles[tileY][tileX])) {
            player.setPosition(newX, newY);
        }

        updateCamera();

        // Check collision with hazards
        int playerTileX = player.getX() / TILE_SIZE;
        int playerTileY = player.getY() / TILE_SIZE;
        if (mapTiles[playerTileY][playerTileX] == 3) {
            player.reduceHealth();
            mapTiles[playerTileY][playerTileX] = 0; // remove hazard
            if (!player.isAlive()) {
                game.changeScene(new com.helwan.survivalgame.scenes.GameOverScene(game));
                return;
            }
        }

        // Check if reached end
        if (mapTiles[playerTileY][playerTileX] == 5) {
            game.changeScene(new com.helwan.survivalgame.scenes.WinScene(game));
            return;
        }

        // Update HUD
        hud.setHealth(player.getHealth());
        hud.setScore(storyManager.getCurrentQuest() != null ? storyManager.getCurrentQuest().getTitle().length() * 10 : 0);
    }

    @Override
    public void render() {
        Graphics g = game.getGraphics();
        if (g == null) return;

        // Clear background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, game.getWidth(), game.getHeight());

        // Draw visible map tiles
        int startX = cameraX / TILE_SIZE;
        int startY = cameraY / TILE_SIZE;
        int endX = (cameraX + game.getWidth()) / TILE_SIZE + 1;
        int endY = (cameraY + game.getHeight()) / TILE_SIZE + 1;

        for (int y = startY; y < endY && y < MAP_HEIGHT; y++) {
            for (int x = startX; x < endX && x < MAP_WIDTH; x++) {
                int tile = mapTiles[y][x];
                int drawX = x * TILE_SIZE - cameraX;
                int drawY = y * TILE_SIZE - cameraY;

                switch (tile) {
                    case 0: // floor
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                        break;
                    case 1: // wall
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                        break;
                    case 2: // door
                        g.setColor(Color.ORANGE);
                        g.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                        break;
                    case 3: // hazard (red box)
                        g.setColor(Color.RED);
                        g.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                        break;
                    case 4: // start
                        g.setColor(Color.GREEN);
                        g.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                        break;
                    case 5: // end
                        g.setColor(Color.BLUE);
                        g.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                        break;
                }
            }
        }

        // Draw player
        int playerDrawX = player.getX() - cameraX;
        int playerDrawY = player.getY() - cameraY;
        player.renderAt(g, playerDrawX, playerDrawY);

        // Draw HUD and dialogs
        hud.render(g);
        dialogManager.render(g);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, game.getWidth(), game.getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("Paused", game.getWidth() / 2 - 70, game.getHeight() / 2);
        }
    }
    @Override
    public void onEnter() {
        AudioManager.getInstance().playMusic("game_music");
        showCurrentQuestDialog();
    }

    @Override
    public void onExit() {
        AudioManager.getInstance().stopMusic();
    }

    private void showCurrentQuestDialog() {
        if (storyManager.isStoryComplete()) {
            dialogManager.showDialog("Congratulations! You have completed the story.");
        } else {
            dialogManager.showDialog("Quest: " + storyManager.getCurrentQuest().getTitle() + " - " + storyManager.getCurrentQuest().getDescription());
        }
        dialogVisible = true;
    }
}
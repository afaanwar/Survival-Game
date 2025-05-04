package com.helwan.survivalgame.scenes;

import com.helwan.survivalgame.Game;
import com.helwan.survivalgame.engine.AudioManager;
import com.helwan.survivalgame.engine.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScene extends Scene {
    private String[] options = {"Start Game", "Load Game", "Exit"};
    private int selectedOption = 0;

    public MenuScene(Game game) {
        super(game);
    }

    @Override
    public void onEnter() {
        AudioManager.getInstance().playMusic("menu_music");
    }

    @Override
    public void onExit() {
        AudioManager.getInstance().stopMusic();
    }

    @Override
    public void update() {
        InputManager input = InputManager.getInstance();

        if (input.isKeyPressed(KeyEvent.VK_UP)) {
            selectedOption--;
            if (selectedOption < 0) {
                selectedOption = options.length - 1;
            }
            input.consumeKey(KeyEvent.VK_UP);
        } else if (input.isKeyPressed(KeyEvent.VK_DOWN)) {
            selectedOption++;
            if (selectedOption >= options.length) {
                selectedOption = 0;
            }
            input.consumeKey(KeyEvent.VK_DOWN);
        } else if (input.isKeyPressed(KeyEvent.VK_ENTER)) {
            switch (selectedOption) {
                case 0:
                    game.changeScene(new GameScene(game));
                    break;
                case 1:
                    // TODO: Implement load game
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
            input.consumeKey(KeyEvent.VK_ENTER);
        }
    }

    @Override
    public void render() {
        Graphics g = game.getGraphics();
        if (g == null) return;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, game.getWidth(), game.getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Survival Game", 280, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 350, 200 + i * 40);
        }
    }
}

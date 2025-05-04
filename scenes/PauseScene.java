package com.helwan.survivalgame.scenes;

import com.helwan.survivalgame.Game;
import com.helwan.survivalgame.engine.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseScene extends Scene {

    public PauseScene(Game game) {
        super(game);
    }

    @Override
    public void onEnter() {
        // Pause music or other actions
    }

    @Override
    public void onExit() {
        // Resume music or other actions
    }

    @Override
    public void update() {
        InputManager input = InputManager.getInstance();
        if (input.isKeyPressed(KeyEvent.VK_P)) {
            game.changeScene(new GameScene(game));
            input.consumeKey(KeyEvent.VK_P);
            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }
    }

    @Override
    public void render() {
        Graphics g = game.getGraphics();
        if (g == null) return;

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, game.getWidth(), game.getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("Paused", 350, 300);
    }
}

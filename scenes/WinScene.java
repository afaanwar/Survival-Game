package com.helwan.survivalgame.scenes;

import com.helwan.survivalgame.Game;
import com.helwan.survivalgame.engine.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WinScene extends Scene {

    public WinScene(Game game) {
        super(game);
    }

    @Override
    public void onEnter() {
        // Play win music or sound
    }

    @Override
    public void onExit() {
        // Stop win music or sound
    }

    @Override
    public void update() {
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_ENTER)) {
            game.changeScene(new MenuScene(game));
            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }
    }

    @Override
    public void render() {
        Graphics g = game.getGraphics();
        if (g == null) return;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, game.getWidth(), game.getHeight());

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("You Win!", 320, 300);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press Enter to return to Menu", 280, 350);
    }
}

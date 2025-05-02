package com.helwan.survivalgame;

import com.helwan.survivalgame.engine.AudioManager;
import com.helwan.survivalgame.engine.GameLoop;
import com.helwan.survivalgame.engine.InputManager;
import com.helwan.survivalgame.engine.SceneManager;
import com.helwan.survivalgame.scenes.MenuScene;

import javax.swing.JFrame;

public class Game extends JFrame {
    private SceneManager sceneManager;
    private GameLoop gameLoop;

    public Game() {
        setTitle("Survival Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        sceneManager = new SceneManager();
        gameLoop = new GameLoop(sceneManager);

        addKeyListener(InputManager.getInstance());
        getContentPane().addKeyListener(InputManager.getInstance());
    }

    public void start() {
        sceneManager.setScene(new MenuScene(this));
        setVisible(true);
        getContentPane().requestFocusInWindow();
        AudioManager.getInstance().playMusic("background_music");
        gameLoop.start();
    }

    public void changeScene(com.helwan.survivalgame.scenes.Scene scene) {
        sceneManager.setScene(scene);
    }
}

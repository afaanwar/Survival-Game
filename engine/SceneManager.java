package com.helwan.survivalgame.engine;

import com.helwan.survivalgame.scenes.Scene;

public class SceneManager {
    private Scene currentScene;

    public void setScene(Scene scene) {
        if (currentScene != null) {
            currentScene.onExit();
        }
        currentScene = scene;
        currentScene.onEnter();
    }

    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public void render() {
        if (currentScene != null) {
            currentScene.render();
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}

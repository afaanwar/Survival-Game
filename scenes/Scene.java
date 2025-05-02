package com.helwan.survivalgame.scenes;

public abstract class Scene {
    protected com.helwan.survivalgame.Game game;

    public Scene(com.helwan.survivalgame.Game game) {
        this.game = game;
    }

    public abstract void onEnter();
    public abstract void onExit();
    public abstract void update();
    public abstract void render();
}

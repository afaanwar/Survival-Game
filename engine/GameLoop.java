package com.helwan.survivalgame.engine;

public class GameLoop implements Runnable {
    private boolean running;
    private SceneManager sceneManager;

    public GameLoop(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.running = false;
    }

    public void start() {
        if (running) return;
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            update();
            render();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        sceneManager.update();
    }

    private void render() {
        sceneManager.render();
    }
}

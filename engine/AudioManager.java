package com.helwan.survivalgame.engine;

public class AudioManager {
    private static AudioManager instance;

    private AudioManager() {
        // Initialize audio system
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playSound(String soundName) {
        // Play sound effect
        System.out.println("Playing sound: " + soundName);
    }

    public void playMusic(String musicName) {
        // Play background music
        System.out.println("Playing music: " + musicName);
    }

    public void stopMusic() {
        // Stop background music
        System.out.println("Stopping music");
    }
}

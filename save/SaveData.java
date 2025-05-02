package com.helwan.survivalgame.save;

import com.helwan.survivalgame.entities.Player;

import java.io.Serializable;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int playerX;
    private int playerY;
    private int playerHealth;
    private int currentQuestIndex;

    public SaveData(Player player, int currentQuestIndex) {
        this.playerX = player.getX();
        this.playerY = player.getY();
        this.playerHealth = 100; // Placeholder, add health to Player class if needed
        this.currentQuestIndex = currentQuestIndex;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getCurrentQuestIndex() {
        return currentQuestIndex;
    }
}

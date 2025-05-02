package com.helwan.survivalgame.save;

import com.helwan.survivalgame.entities.Player;

import java.io.*;

public class SaveManager {
    private static final String SAVE_FILE = "savegame.dat";

    public static void saveGame(Player player, int currentQuestIndex) {
        SaveData data = new SaveData(player, currentQuestIndex);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(data);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveData loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            SaveData data = (SaveData) ois.readObject();
            System.out.println("Game loaded successfully.");
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No save file found or failed to load.");
            return null;
        }
    }
}

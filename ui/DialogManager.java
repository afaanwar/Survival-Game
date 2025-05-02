package com.helwan.survivalgame.ui;

import java.awt.*;

public class DialogManager {
    private String currentDialog;
    private boolean visible;

    public DialogManager() {
        currentDialog = "";
        visible = false;
    }

    public void showDialog(String dialog) {
        currentDialog = dialog;
        visible = true;
    }

    public void hideDialog() {
        visible = false;
    }

    public void render(Graphics g) {
        if (!visible) return;

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(50, 400, 700, 150);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(currentDialog, 70, 450);
    }
}

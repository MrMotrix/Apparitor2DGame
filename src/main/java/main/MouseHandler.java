package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private GamePanel gamePanel;
    private Point lastClick;

    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.lastClick = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gamePanel.pauseState) { //s'active que si le jeu est en pause
            lastClick = new Point(e.getX(), e.getY());
        }
    }
    public Point getLastClick() {
        return lastClick;
    }

    public void resetLastClick() {
        lastClick = null;
    }
}
package main;

import javax.swing.text.DefaultEditorKit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;

    public KeyHandler() {
    }
   
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 90) {
            this.upPressed = true;
        }
        if (code == 83) {
            this.downPressed = true;
        }
        if (code == 68) {
            this.rightPressed = true;
        }
        if (code == 81) {
            this.leftPressed = true;
        }

    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 90) {
            this.upPressed = false;
        }
        if (code == 83) {
            this.downPressed = false;
        }
        if (code == 68) {
            this.rightPressed = false;
        }
        if (code == 81) {
            this.leftPressed = false;
        }

    }
}

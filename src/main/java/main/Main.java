package main;

import javax.swing.JFrame;
import java.nio.file.Paths;
import tools.SpriteLibrary;
public class Main {


    public static void main(String[] args) {
        String basePath = Paths.get("src/main/resources").toAbsolutePath().toString()+"/";
        SpriteLibrary spriteLibrary = SpriteLibrary.getInstance(basePath);
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Apparitor 2D Game");

        GamePanel panel = new GamePanel();
        window.add(panel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.startGameThread();
    }

}

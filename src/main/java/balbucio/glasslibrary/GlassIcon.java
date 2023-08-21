package balbucio.glasslibrary;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class GlassIcon {

    public static ImageIcon CLOSE_ICON;
    public static ImageIcon MAXIMIZE_ICON;
    public static ImageIcon MINIMIZE_ICON;
    public static ImageIcon CONFIG_ICON;
    public static Image BG_GALAXY;

    static {
        try {
            CLOSE_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(read(GlassIcon.class.getResourceAsStream("/icons/close.png")).getSource()));
            MAXIMIZE_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(read(GlassIcon.class.getResourceAsStream("/icons/maximize.png")).getSource()));
            MINIMIZE_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(read(GlassIcon.class.getResourceAsStream("/icons/minimize.png")).getSource()));
            CONFIG_ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(read(GlassIcon.class.getResourceAsStream("/icons/config.png")).getSource()));
            BG_GALAXY = Toolkit.getDefaultToolkit().createImage(read(GlassIcon.class.getResourceAsStream("/icons/minimize.png")).getSource());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

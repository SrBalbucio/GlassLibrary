import balbucio.glasslibrary.GlassFrame;
import balbucio.glasslibrary.GlassLibrary;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ThemedWindowTest {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException, FontFormatException {
        UIManager.setLookAndFeel(new FlatArcDarkContrastIJTheme());
        GlassLibrary glassLibrary = new GlassLibrary();
        if(glassLibrary.isSupported()) {
            GlassFrame frame = new GlassFrame("GlassFrame Test", GlassFrame.Config.builder()
                    .instance(glassLibrary)
                    .updateInterval(900)
                    .build());
            frame.menuBar().getConfig().setDoubleClickToMaximize(true);
            frame.menuBar().getConfig().setConfigIcon(true);
            frame.menuBar().getConfig().setConfigClickEvent(() -> glassLibrary.showGlassMessage("Config",
                    "Voce abriu o menu de configuração, este é um teste\n" +
                            "do GlassMessage com um texto bem grandao. Se voce \n" +
                            "deseja sair clica em Ok."));
            Font font = Font.createFont(Font.TRUETYPE_FONT, ThemedWindowTest.class.getResourceAsStream("/Lato-Regular.ttf"));
            frame.menuBar().getConfig().setFont(font.deriveFont(20f));
            frame.menuBar().update();
            frame.setSize(640, 480);
            frame.setAutomaticColorChange(true);
            BoxLayout layout = new BoxLayout(frame.getComponentPanel(), BoxLayout.Y_AXIS);
            frame.layout(layout);
            JPanel text1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel lb1 = new JLabel("THIS IS A GLASS FRAME, SO FUCK");
            lb1.setFont(lb1.getFont().deriveFont(24f));
            text1.add(lb1);

            frame.addComponent(text1, null);
            frame.addComponent(new JButton("Botao"), null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }
}

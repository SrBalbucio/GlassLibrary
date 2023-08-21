import balbucio.glasslibrary.GlassFrame;
import balbucio.glasslibrary.GlassLibrary;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;

import javax.swing.*;
import java.awt.*;

public class ThemedWindowTest {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatArcDarkContrastIJTheme());
        GlassLibrary glassLibrary = new GlassLibrary();
        if(glassLibrary.isSupported()) {
            GlassFrame frame = new GlassFrame("GlassFrame Test");
            frame.setSize(640, 480);
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

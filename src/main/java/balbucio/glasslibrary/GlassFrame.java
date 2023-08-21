package balbucio.glasslibrary;

import balbucio.glasslibrary.component.GlassMenuBar;
import balbucio.glasslibrary.window.effect.SwingAcrylic;

import javax.swing.*;
import java.awt.*;

public class GlassFrame extends JFrame{

    private JPanel rootpane;
    private GlassMenuBar menuBar;

    public GlassFrame(String title) throws HeadlessException {
        super(title);
        this.rootpane = new JPanel();
        rootpane.setName("RootPane");
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.menuBar = new GlassMenuBar(title, this);
        this.add(menuBar, BorderLayout.NORTH);
        this.add(rootpane, BorderLayout.CENTER);
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            menuBar.scale();
            SwingAcrylic.processFrame(this, 255, 0x990500);
        });
    }

    public void layout(LayoutManager manager){
        rootpane.setLayout(manager);
    }

    public void addComponent(Component c, Object o){
        rootpane.add(c, o);
    }
}

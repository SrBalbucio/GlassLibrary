package balbucio.glasslibrary;

import balbucio.glasslibrary.component.GlassMenuBar;
import balbucio.glasslibrary.component.GlassPane;
import balbucio.glasslibrary.utils.FrameColor;
import balbucio.glasslibrary.utils.FrameUtils;
import balbucio.glasslibrary.window.ComponentResizer;
import balbucio.glasslibrary.window.effect.SwingAcrylic;
import balbucio.responsivescheduler.ResponsiveScheduler;
import com.sun.jna.platform.WindowUtils;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class GlassFrame extends JFrame implements ComponentListener, Runnable {
    @Getter
    @Setter
    private boolean automaticColorChange = false; // trocar automaticamente a cor dos textos
    private GlassPane rootpane;
    private GlassMenuBar menuBar;
    private Config config;

    public GlassFrame(String title){
        this(title, Config.builder().build());
    }

    public GlassFrame(String title, Config config) throws HeadlessException {
        super(title);
        this.config = config;
        this.rootpane = new GlassPane();
        rootpane.setName("RootPane");
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.menuBar = new GlassMenuBar(title, this);
        this.add(menuBar, BorderLayout.NORTH);
        this.add(rootpane, BorderLayout.CENTER);
        SwingUtilities.invokeLater(this);
    }

    @Override
    public void setUndecorated(boolean undecorated) {
        super.setUndecorated(true);
    }

    public GlassMenuBar menuBar(){
        return menuBar;
    }

    public GlassPane getComponentPanel(){
        return rootpane;
    }

    public void layout(LayoutManager manager){
        rootpane.setLayout(manager);
    }

    public void addComponent(Component c, Object o){
        rootpane.add(c, o);
    }

    private void switchLabelColor(JComponent c, Color color){
        c.setForeground(color);
        Arrays.asList(c.getComponents()).forEach(ci -> {
            switchLabelColor((JComponent) ci, color);
        });
    }

    private long lastUpdate = System.currentTimeMillis();

    @Override
    public void componentResized(ComponentEvent e) {
        menuBar.scale();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        if((System.currentTimeMillis() - lastUpdate) > config.updateInterval) {
            if (automaticColorChange) {
                ResponsiveScheduler.run(() -> {
                    if(config.getInstance().isSupported()) {
                        FrameColor color = FrameUtils.averageFrameColor((JFrame) e.getComponent());
                        boolean b = color.isColorCloserToBlack();
                        Arrays.asList(((JFrame) e.getComponent()).getComponents())
                                .forEach(c -> switchLabelColor((JComponent) c, b ? Color.WHITE : Color.BLACK));
                        lastUpdate = System.currentTimeMillis();
                    }
                });
            }
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void run() {
        ComponentResizer cr = new ComponentResizer();
        cr.setMinimumSize(new Dimension(640, 480));
        cr.registerComponent(this);
        cr.setSnapSize(new Dimension(10, 10));
        setVisible(true);
        menuBar.scale();
        if(config.getInstance().isSupported()) {
            SwingAcrylic.processFrame(this, config.opacity, config.background);
        }
    }

    @Builder
    @Data
    public static class Config{
        GlassLibrary instance = new GlassLibrary();
        int opacity = 255;
        int background = 0x990500;
        Dimension minimumSize = new Dimension(640, 480);
        long updateInterval = 500;
        int borderRadius = 15;
    }
}

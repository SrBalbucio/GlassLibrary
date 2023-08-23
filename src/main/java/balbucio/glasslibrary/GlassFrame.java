package balbucio.glasslibrary;

import balbucio.glasslibrary.component.GlassMenuBar;
import balbucio.glasslibrary.component.GlassPane;
import balbucio.glasslibrary.utils.FrameColor;
import balbucio.glasslibrary.utils.FrameUtils;
import balbucio.glasslibrary.window.effect.SwingAcrylic;
import balbucio.responsivescheduler.ResponsiveScheduler;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.util.Arrays;

public class GlassFrame extends JFrame{

    @Getter
    @Setter
    private boolean automaticColorChange = false; // trocar automaticamente a cor dos textos
    private GlassPane rootpane;
    private GlassMenuBar menuBar;

    public GlassFrame(String title){
        this(title, 255, 0x990500);
    }

    public GlassFrame(String title, int opacity, int background) throws HeadlessException {
        super(title);
        this.rootpane = new GlassPane();
        rootpane.setName("RootPane");
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.menuBar = new GlassMenuBar(title, this);
        this.add(menuBar, BorderLayout.NORTH);
        this.add(rootpane, BorderLayout.CENTER);
        this.addComponentListener(new ComponentAdapter() {

            long lastUpdate = System.currentTimeMillis();
            @Override
            public void componentMoved(ComponentEvent e) {
                if((System.currentTimeMillis() - lastUpdate) > 500) {
                    if (automaticColorChange) {
                        ResponsiveScheduler.run(() -> {
                            FrameColor color = FrameUtils.averageFrameColor((JFrame) e.getComponent());
                            boolean b = color.isColorCloserToBlack();
                            Arrays.asList(((JFrame) e.getComponent()).getComponents())
                                    .forEach(c -> switchLabelColor((JComponent) c, b ? Color.WHITE : Color.BLACK));
                            lastUpdate = System.currentTimeMillis();
                        });
                    }
                }
            }
        });
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            menuBar.scale();
            SwingAcrylic.processFrame(this, opacity, background);
        });
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
}

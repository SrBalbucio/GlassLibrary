package balbucio.glasslibrary.component;

import balbucio.glasslibrary.GlassFrame;
import balbucio.glasslibrary.GlassIcon;
import balbucio.glasslibrary.window.listener.ClickListener;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GlassMenuBar extends JPanel implements MouseListener, MouseMotionListener {

    private JPanel right;
    private JPanel left;
    private JLabel close;
    private JLabel maximize;
    private JLabel minimize;
    private JLabel cfg;
    private JLabel title;
    private GlassFrame frame;
    private Point mouseDownCompCoords;
    @Getter
    @Setter
    private Config config;

    public GlassMenuBar(String titleApp, GlassFrame frame) {
        this(titleApp, frame, Config.builder()
                .doubleClickToMaximize(true)
                .build());
    }

    public GlassMenuBar(String titleApp, GlassFrame frame, Config config) {
        this.config = config;
        this.setName("GlassMenuBar");
        this.frame = frame;
        this.setLayout(new BorderLayout());

        create(titleApp);

        this.add(right, BorderLayout.WEST);
        if (config.centerComponent != null) {
            this.add(config.centerComponent, BorderLayout.CENTER);
        }
        this.add(left, BorderLayout.EAST);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    public void maximize() {
        if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Rectangle bounds = env.getMaximumWindowBounds();
            frame.setMaximizedBounds(bounds);
            frame.setExtendedState((frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
        } else {
            frame.setExtendedState(JFrame.NORMAL);
        }
    }

    public void update() {
        if (config.getFont() != null) {
            title.setFont(config.getFont());
        }
        cfg.setVisible(config.isConfigIcon());
    }

    public void scale() {
        int x = (right.getHeight() - title.getHeight()) / 4;
        title.setBorder(new EmptyBorder(x, 5, x, 0));
    }

    private void create(String titleApp) {
        right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(new Color(0, 0, 0, 0));
        this.title = new JLabel(titleApp);
        title.setForeground(Color.WHITE);
        if (config.getFont() != null) {
            title.setFont(config.getFont());
        }
        right.add(title);

        left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setBackground(new Color(0, 0, 0, 0));
        this.minimize = new JLabel(GlassIcon.MINIMIZE_ICON);
        minimize.addMouseListener(new ClickListener() {
            @Override
            public void click(MouseEvent evt) {
                frame.setState(Frame.ICONIFIED);
            }
        });
        this.maximize = new JLabel(GlassIcon.MAXIMIZE_ICON);
        maximize.addMouseListener(new ClickListener() {
            @Override
            public void click(MouseEvent evt) {
                maximize();
            }
        });
        this.close = new JLabel(GlassIcon.CLOSE_ICON);
        close.addMouseListener(new ClickListener() {
            @Override
            public void click(MouseEvent evt) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.cfg = new JLabel(GlassIcon.CONFIG_ICON);
        cfg.addMouseListener(new ClickListener() {
            @Override
            public void click(MouseEvent evt) {
                if (config.isConfigIcon() && config.configClickEvent != null) {
                    config.getConfigClickEvent().run();
                }
            }
        });
        cfg.setVisible(config.isConfigIcon());
        if (config.buttons != null) {
            config.buttons.forEach(c -> left.add(c));
        }
        left.add(cfg);
        left.add(minimize);
        left.add(maximize);
        left.add(close);
    }

    int count = 0;
    long time = System.currentTimeMillis();

    @Override
    public void mouseClicked(MouseEvent e) {
        if (config.isDoubleClickToMaximize()) {
            if (count == 1) {
                if ((System.currentTimeMillis() - time) < 500) {
                    maximize();
                    count = 0;
                } else {
                    time = System.currentTimeMillis();
                    count = 0;
                }
            }
            count++;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDownCompCoords = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point currCoords = e.getLocationOnScreen();
        frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Data
    @Builder
    public static class Config {

        boolean doubleClickToMaximize = true;
        boolean configIcon = false;
        Font font = null;
        Component centerComponent;
        List<Component> buttons;
        Runnable configClickEvent = null;
    }
}

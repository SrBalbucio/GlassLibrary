package balbucio.glasslibrary.component;

import balbucio.glasslibrary.GlassFrame;
import balbucio.glasslibrary.GlassIcon;
import balbucio.glasslibrary.window.listener.ClickListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;

public class GlassMenuBar extends JPanel {

    private JPanel right;
    private JPanel left;
    private JLabel close;
    private JLabel maximize;
    private JLabel minimize;
    private JLabel title;
    private GlassFrame frame;
    private Point mouseDownCompCoords;

    public GlassMenuBar(String titleApp, GlassFrame frame){
        this.setName("GlassMenuBar");
        this.frame = frame;
        this.setLayout(new BorderLayout());

        right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(new Color(0,0,0,0));
        this.title = new JLabel(titleApp);
        title.setForeground(Color.WHITE);
        right.add(title);

        left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setBackground(new Color(0,0,0,0));
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
                if(frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else{
                    frame.setExtendedState(JFrame.NORMAL);
                }
            }
        });
        this.close = new JLabel(GlassIcon.CLOSE_ICON);
        close.addMouseListener(new ClickListener() {
            @Override
            public void click(MouseEvent evt) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        left.add(minimize);
        left.add(maximize);
        left.add(close);

        this.add(right, BorderLayout.WEST);
        this.add(left, BorderLayout.EAST);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });
    }

    public void scale(){
        int x = (right.getHeight() - title.getHeight()) / 4;
        title.setBorder(new EmptyBorder(x,5,x,0));
    }
}

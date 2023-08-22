package balbucio.glasslibrary.dialog;

import balbucio.glasslibrary.utils.StringUtils;
import balbucio.glasslibrary.window.effect.SwingAcrylic;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GlassMessage extends JDialog {

    private String title;
    private String message;

    public GlassMessage(Frame owner, String title, String message) {
        super(owner, title);
        this.title = title;
        this.message = message;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.setOpacity(0.8f);
        JPanel icon = new JPanel();
        JPanel text = new JPanel();
        List<String> lines = StringUtils.breakTextIntoLines(message, text.getFontMetrics(text.getFont()), 330);
        for (String line : lines) {
            JLabel label = new JLabel(line);
            label.setSize(new Dimension(label.getFontMetrics(label.getFont()).stringWidth(line), label.getFontMetrics(label.getFont()).getHeight()));
            text.add(label);
        }

        this.add(text, BorderLayout.CENTER);
        BoxLayout layout = new BoxLayout(text, BoxLayout.Y_AXIS);
        text.setLayout(layout);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton close = new JButton("Ok");
        buttons.add(close);
        text.add(buttons);
        SwingUtilities.invokeLater(() -> {
            AtomicInteger size = new AtomicInteger();
            Arrays.asList(text.getComponents()).forEach(c -> size.addAndGet(c.getHeight()));
            this.setSize(new Dimension(440, size.addAndGet(80)));
            this.setVisible(true);
        });
    }
}

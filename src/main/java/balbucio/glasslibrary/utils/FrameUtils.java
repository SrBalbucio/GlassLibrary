package balbucio.glasslibrary.utils;

import javax.swing.*;
import java.awt.*;

public class FrameUtils {

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Robot robot;
    public static final int variant = 6;

    public static FrameColor averageFrameColor(JFrame frame) {
        int totalPixels = 0;
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        int totalAlpha = 0;

        int variationW = frame.getWidth() / variant;
        int variationH = frame.getHeight() / (variant / 2);

        int w = frame.getWidth() / variationW;
        for (int i = 0; i < w; i++) {
            Color color;
            int h = frame.getHeight() / variationH;
            for (int i1 = 0; i1 < h; i1++) {
                totalPixels++;
                color = robot.getPixelColor(frame.getX()+(i*variationW), frame.getY()+(i1*variationH));
                totalRed += color.getRed();
                totalBlue += color.getBlue();
                totalGreen += color.getGreen();
                totalAlpha += color.getAlpha();
            }
            totalPixels++;
            color = robot.getPixelColor(frame.getX()+(i*variationW), frame.getY());
            totalRed += color.getRed();
            totalBlue += color.getBlue();
            totalGreen += color.getGreen();
            totalAlpha += color.getAlpha();
        }

        int averageRed = totalRed / totalPixels;
        int averageGreen = totalGreen / totalPixels;
        int averageBlue = totalBlue / totalPixels;
        int averageAlpha = totalAlpha / totalPixels;

        return new FrameColor(averageRed, averageGreen, averageBlue, averageAlpha);
    }
}

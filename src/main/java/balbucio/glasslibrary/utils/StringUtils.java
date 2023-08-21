package balbucio.glasslibrary.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<String> breakTextIntoLines(String text, FontMetrics metrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder lineBuilder = new StringBuilder();

        for (String word : words) {
            int wordWidth = metrics.stringWidth(word);

            if (lineBuilder.length() == 0 || metrics.stringWidth(lineBuilder.toString() + " " + word) <= maxWidth) {
                if (lineBuilder.length() > 0) {
                    lineBuilder.append(" ");
                }
                lineBuilder.append(word);
            } else {
                lines.add(lineBuilder.toString());
                lineBuilder = new StringBuilder(word);
            }
        }

        if (lineBuilder.length() > 0) {
            lines.add(lineBuilder.toString());
        }

        return lines;
    }
}

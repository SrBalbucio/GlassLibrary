package balbucio.glasslibrary.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrameColor {

    private int red;
    private int green;
    private int blue;
    private int alpha;

    public boolean isColorCloserToBlack() {
        int redDiff = Math.abs(red - 0);
        int greenDiff = Math.abs(green - 0);
        int blueDiff = Math.abs(blue - 0);

        return (redDiff + greenDiff + blueDiff) < (255 - redDiff - greenDiff - blueDiff);
    }
}

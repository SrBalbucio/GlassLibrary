import balbucio.glasslibrary.GlassFrame;
import balbucio.glasslibrary.GlassLibrary;

public class WindowTest {

    public static void main(String[] args) {
        GlassLibrary glassLibrary = new GlassLibrary();
        if(glassLibrary.isSupported()) {
            GlassFrame frame = new GlassFrame("GlassFrame Test");
            frame.setSize(640, 480);
        }
    }
}

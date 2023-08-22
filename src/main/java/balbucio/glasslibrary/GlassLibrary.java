package balbucio.glasslibrary;

import balbucio.glasslibrary.dialog.GlassMessage;
import balbucio.glasslibrary.window.effect.SwingAcrylic;

import javax.swing.*;import java.awt.*;

public class GlassLibrary {

    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice gd = ge.getDefaultScreenDevice();

    public GlassLibrary(){

    }

    public void showGlassMessage(String title, String message){
        showGlassMessage(null, title, message);
    }

    public void showGlassMessage(JFrame owner, String title, String message){
        new GlassMessage(owner, title, message);
    }

    public boolean isSupported(){
        if(!gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT)){
            return false;
        }
        if(!SwingAcrylic.isSupported()){
            return false;
        }
        return true;
    }
}

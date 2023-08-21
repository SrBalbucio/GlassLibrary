package balbucio.glasslibrary;

import balbucio.glasslibrary.window.effect.SwingAcrylic;

import java.awt.*;

public class GlassLibrary {

    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice gd = ge.getDefaultScreenDevice();

    public GlassLibrary(){

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

package balbucio.glasslibrary.window.effect;

import balbucio.glasslibrary.component.GlassMenuBar;
import balbucio.glasslibrary.component.GlassPane;
import balbucio.glasslibrary.window.effect.jna.*;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * SwingAcrylic
 *
 * Native Windows 10 acrylic effect
 * for Java Swing Applications
 *
 * Supported Windows 10 1803 (April 2018 Update)
 * and higher (until it was removed by Microsoft)
 *
 * This is a port of an existing code to Java:
 * https://github.com/riverar/sample-win32-acrylicblur (C#)
 * https://ru.stackoverflow.com/a/858167 (Python)
 *
 * https://github.com/krlvm/SwingAcrylic
 * @author krlvm
 */
public class SwingAcrylic {

    public static final int MIN_BUILD = 17134;

    /**
     * Automatically make window and its children background
     * transparent and apply native acrylic effect
     *
     * @param frame - frame
     * @param opacity - transparency opacity
     * @param background - blur background (BGR)
     */
    public static void processFrame(JFrame frame, int opacity, int background) {
        addTransparencyToBackground(frame);
        enableAcrylic(frame, opacity, background);
    }

    public static void processFrame(JDialog frame, int opacity, int background) {
        addTransparencyToBackground(frame);
        enableAcrylic(frame, opacity, background);
    }

    /**
     * Manually enable native acrylic on window,
     * transparent background is required
     *
     * @param frame - frame
     */
    public static boolean enableAcrylic(JFrame frame, int opacity, int background) {
        WinDef.HWND hwnd = new WinDef.HWND(Native.getWindowPointer(frame));

        AccentPolicy policy = new AccentPolicy();
        policy.AccentState = AccentState.ACCENT_ENABLE_BLURBEHIND;
        policy.GradientColor = (opacity << 24) | (background & 0xFFFFFF);
        policy.write();

        WindowCompositionAttributeData data = new WindowCompositionAttributeData();
        data.Attribute = WindowCompositionAttribute.WCA_ACCENT_POLICY;
        data.Data = policy.getPointer();
        data.SizeOfData = policy.size();
        data.write();

        return SAUser32.INSTANCE.SetWindowCompositionAttribute(hwnd, data.getPointer());
    }

    public static boolean enableAcrylic(JDialog frame, int opacity, int background) {
        WinDef.HWND hwnd = new WinDef.HWND(Native.getWindowPointer(frame));

        AccentPolicy policy = new AccentPolicy();
        policy.AccentState = AccentState.ACCENT_ENABLE_BLURBEHIND;
        policy.GradientColor = (opacity << 24) | (background & 0xFFFFFF);
        policy.write();

        WindowCompositionAttributeData data = new WindowCompositionAttributeData();
        data.Attribute = WindowCompositionAttribute.WCA_ACCENT_POLICY;
        data.Data = policy.getPointer();
        data.SizeOfData = policy.size();
        data.write();

        return SAUser32.INSTANCE.SetWindowCompositionAttribute(hwnd, data.getPointer());
    }

    /**
     * Automatically make component and its children background
     * transparent, then you have to call enableAcrylic method
     *
     * @param component - a Java Swing component
     */
    public static void addTransparencyToBackground(Component component) {
        component.setBackground(new Color(0, 0, 0, 1));
        if(component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                if(child instanceof GlassMenuBar gm){
                    gm.setBackground(new Color(0, 0, 0, 40));
                } else if(child instanceof JButton){
                } else if(child instanceof GlassPane gp){
                    Arrays.asList(gp.getComponents()).forEach(c -> addTransparencyToBackground(c));
                } else {
                    addTransparencyToBackground(child);
                }
            }
        }
    }

    public static boolean isSupported() {
        if(!System.getProperty("os.name").equals("Windows 10") && !System.getProperty("os.name").equals("Windows 11")) {
            return false;
        }

        Kernel32 kernel = Kernel32.INSTANCE;
        WinNT.OSVERSIONINFOEX vex = new WinNT.OSVERSIONINFOEX();
        if (kernel.GetVersionEx(vex)) {
            try {
                int build = Integer.parseInt(vex.dwBuildNumber.toString());
                return build >= SwingAcrylic.MIN_BUILD;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }
}

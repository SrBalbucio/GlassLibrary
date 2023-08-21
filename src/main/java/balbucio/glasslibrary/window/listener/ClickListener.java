package balbucio.glasslibrary.window.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class ClickListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        click(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }



    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    public abstract void click(MouseEvent evt);
}

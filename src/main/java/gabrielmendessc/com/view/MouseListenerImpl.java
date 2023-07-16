package gabrielmendessc.com.view;

import gabrielmendessc.com.QuadRect;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MouseListenerImpl implements MouseListener {

    private final List<Point> addedPointList = new ArrayList<>();
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        System.out.println("Pressionado " + e.getButton());

        switch (e.getButton()) {

            case MouseEvent.BUTTON1 -> {

                Point point = new Point(e.getX(), e.getY());

                AppScreen.quadTree.insert(point);

                addedPointList.add(point);

            }

            case MouseEvent.BUTTON2 -> {

                List<Point> pointList = AppScreen.quadTree.query(new QuadRect(e.getX(), e.getY(), 50, 50));
                pointList.forEach(point -> {

                    AppScreen.quadTree.remove(point);
                    addedPointList.remove(point);

                });

                AppScreen.xMouse = e.getX();
                AppScreen.yMouse = e.getY();

            }

            case MouseEvent.BUTTON3 -> {

                Point point = addedPointList.remove(addedPointList.size() - 1);

                AppScreen.quadTree.remove(point);

            }

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}

package gabrielmendessc.com.view;

import gabrielmendessc.com.Main;
import gabrielmendessc.com.QuadNode;
import gabrielmendessc.com.QuadRect;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class AppScreen extends JPanel {

    public static QuadNode<Point> quadTree;
    public static List<Point> addedPointList = Collections.synchronizedList(new ArrayList<>());

    public static int xMouse = 0;
    public static int yMouse = 0;

    public AppScreen() {

        quadTree = new QuadNode<>(50, 50, 700, 700, 4);
        setSize(800, 800);
        setVisible(true);

    }

    public void simulate() {

        addedPointList.forEach(point -> {

            if (!point.isMoving()) {

                point.setMoving(true);
                point.setDir(new Random().nextInt(0, 3));

            }

            double rectX = point.getX() - 15;
            double rectY = point.getY() - 15;
            double rectWidth = point.getWidth() + 30;
            double rectHeight = point.getHeight() + 30;
            List<Point> pointList = quadTree.query(new QuadRect(rectX, rectY, rectWidth, rectHeight));
            pointList.removeIf(o -> o.equals(point));

            QuadRect quadCollision = new QuadRect(point.getX(), point.getY(), point.getWidth(), point.getHeight());

            List<Point> intersectedPointList = pointList.stream().filter(o -> new QuadRect(o.getX(), o.getY(), o.getWidth(), o.getHeight()).intersects(quadCollision)).toList();
            intersectedPointList.forEach(intersectedPoint -> {

                System.out.println("Intersected!");
                intersectedPoint.setDir(new Random().nextInt(0, 3));

            });
            if (!intersectedPointList.isEmpty()) {

                point.setDir(new Random().nextInt(0, 3));

            }

            quadTree.remove(point);

            switch (point.getDir()) {
                case 0 -> point.setX((int) (point.getX() - 5));
                case 1 -> point.setY((int) (point.getY() - 5));
                case 2 -> point.setX((int) (point.getX() + 5));
                case 3 -> point.setY((int) (point.getY() + 5));
            }

            if (point.getX() > 750) {
                point.setX(50);
            }

            if (point.getX() < 50) {
                point.setX(750);
            }

            if (point.getY() > 750) {
                point.setY(50);
            }

            if (point.getY() < 50) {
                point.setY(750);
            }

            quadTree.insert(point);

        });

    }

    @Override
    public void paintComponent(Graphics g) {

        paintQuadNode(g, quadTree);

        if (xMouse > 0 && yMouse > 0) {

            g.setColor(Color.BLUE);

            g.drawRect(xMouse, yMouse, 50, 50);

        }

        g.setColor(Color.BLACK);
        g.drawString("Total: " + addedPointList.size(), 55, 65);
        g.drawString("FPS: " + Main.FPS, 600, 65);

    }

    private void paintQuadNode(Graphics g, QuadNode<Point> quadNode) {

        if (Objects.nonNull(quadNode)) {

            try {

                g.setColor(Color.BLACK);
                g.drawRect((int) quadNode.getX(), (int) quadNode.getY(), (int) quadNode.getWidth(), (int) quadNode.getHeight());

                g.setColor(Color.RED);
                quadNode.getObjectList().forEach(object -> g.fillOval((int) object.getX(), (int) object.getY(), (int) object.getWidth(), (int) object.getHeight()));

                for (QuadNode<Point> quadNodeChild : quadNode.getQuadNodeChildren()) {

                    paintQuadNode(g, quadNodeChild);

                }

            } catch (Exception e) {

                throw e;

            }

        }

    }

}

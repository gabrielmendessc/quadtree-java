package gabrielmendessc.com.view;

import gabrielmendessc.com.Main;
import gabrielmendessc.com.QuadNode;
import gabrielmendessc.com.QuadRect;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AppScreen extends JPanel {

    public static QuadNode<Point> quadTree;
    public static List<Point> addedPointList = Collections.synchronizedList(new ArrayList<>());
    public static Set<Point> intersectedPointSet = Collections.synchronizedSet(new HashSet<>());

    public static int xMouse = 0;
    public static int yMouse = 0;

    public AppScreen() {

        quadTree = new QuadNode<>(new QuadRect(50, 50, 700, 700) , 4, 7);
        setSize(800, 800);
        setVisible(true);

    }

    public void simulate() {

        intersectedPointSet.clear();

        addedPointList.forEach(point -> {

            double rectX = point.getX() - 15;
            double rectY = point.getY() - 15;
            double rectWidth = point.getWidth() + 30;
            double rectHeight = point.getHeight() + 30;
            Set<Point> pointList = quadTree.query(new QuadRect(rectX, rectY, rectWidth, rectHeight));
            pointList.removeIf(o -> o.equals(point));

            Set<Point> intersectedSet = pointList.stream().filter(o -> o.getQuadRect().intersects(point.getQuadRect())).collect(Collectors.toSet());
            if (!intersectedSet.isEmpty()) {

                intersectedPointSet.addAll(intersectedSet);
                intersectedPointSet.add(point);

            }

            quadTree.remove(point);

            double bottomThreshold = 750 - point.getHeight();
            double topThreshold = 50;
            double rightThreshold = 750 - point.getWidth();
            double leftThreshold = 50;

            double finalYPos = point.getY() + point.getYVel();
            double finalXPos = point.getX() + point.getXVel();

            if (finalYPos > bottomThreshold) {

                double aftermatchFrame = (bottomThreshold - finalYPos) / (point.getY() - finalYPos);

                point.setY(bottomThreshold + ((point.getYVel() * aftermatchFrame) * -1));
                point.setYVel(point.getYVel() * -1);

                intersectedPointSet.add(point);

            } else if (finalYPos < topThreshold) {

                double aftermatchFrame = (topThreshold - finalYPos) / (point.getY() - finalYPos);

                point.setY(topThreshold + ((point.getYVel() * aftermatchFrame) * -1));
                point.setYVel(point.getYVel() * -1);

                intersectedPointSet.add(point);

            } else {

                point.setY(point.getY() + point.getYVel());

            }

            if (finalXPos > rightThreshold) {

                double aftermatchFrame = (rightThreshold - finalXPos) / (point.getX() - finalXPos);

                point.setX(rightThreshold + ((point.getXVel() * aftermatchFrame) * -1));
                point.setXVel(point.getXVel() * -1);

                intersectedPointSet.add(point);

            } else if (finalXPos < leftThreshold) {

                double aftermatchFrame = (leftThreshold - finalXPos) / (point.getX() - finalXPos);

                point.setX(leftThreshold + ((point.getXVel() * aftermatchFrame) * -1));
                point.setXVel(point.getXVel() * -1);

                intersectedPointSet.add(point);

            } else {

                point.setX(point.getX() + point.getXVel());

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

        g.setColor(Color.YELLOW);
        intersectedPointSet.forEach(point -> g.fillRect((int) point.getX(), (int) point.getY(), (int) point.getWidth(), (int) point.getHeight()));

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
                quadNode.getObjectSet().forEach(object -> g.fillRect((int) object.getX(), (int) object.getY(), (int) object.getWidth(), (int) object.getHeight()));

                for (QuadNode<Point> quadNodeChild : quadNode.getQuadNodeChildren()) {

                    paintQuadNode(g, quadNodeChild);

                }

            } catch (Exception e) {

                throw e;

            }

        }

    }

}

package gabrielmendessc.com.view;

import gabrielmendessc.com.QuadNode;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

public class AppScreen extends JPanel {

    public static QuadNode<Point> quadTree;
    public static int xMouse = 0;
    public static int yMouse = 0;

    public AppScreen() {

        quadTree = new QuadNode<>(50, 50, 700, 700, 4);
        setSize(800, 800);
        setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {

        paintQuadNode(g, quadTree);

        if (xMouse > 0 && yMouse > 0) {

            g.setColor(Color.BLUE);

            g.drawRect(xMouse, yMouse, 50, 50);

        }

    }

    private void paintQuadNode(Graphics g, QuadNode<Point> quadNode) {

        if (Objects.nonNull(quadNode)) {

            try {

                g.setColor(Color.BLACK);
                g.drawRect((int) quadNode.getX(), (int) quadNode.getY(), (int) quadNode.getWidth(), (int) quadNode.getHeight());

                g.setColor(Color.RED);
                quadNode.getObjectList().forEach(object -> g.fillOval((int) object.getX(), (int) object.getY(), 5, 5));

                for (QuadNode<Point> quadNodeChild : quadNode.getQuadNodeChildren()) {

                    paintQuadNode(g, quadNodeChild);

                }

            } catch (Exception e) {

                throw e;

            }

        }

    }

}

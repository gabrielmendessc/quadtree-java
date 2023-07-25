package gabrielmendessc.com.view;

import gabrielmendessc.com.Findable;

import java.util.Objects;

public class Point implements Findable<Point> {

    private int x;
    private int y;
    private int dir;
    private boolean isMoving;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean find(Point point) {
        return Objects.equals(x, (int) point.getX()) && Objects.equals(y, (int) point.getY());
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return 10;
    }

    @Override
    public double getHeight() {
        return 10;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
}

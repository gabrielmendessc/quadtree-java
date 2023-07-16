package gabrielmendessc.com.view;

import gabrielmendessc.com.Findable;

import java.util.Objects;

public class Point implements Findable<Point> {

    private int x;
    private int y;

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
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

}

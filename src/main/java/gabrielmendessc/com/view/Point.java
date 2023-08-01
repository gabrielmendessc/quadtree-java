package gabrielmendessc.com.view;

import gabrielmendessc.com.QuadObject;
import gabrielmendessc.com.QuadRect;

public class Point implements QuadObject {

    private int x;
    private int y;
    private int dir;
    private boolean isMoving;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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
        return 30;
    }

    @Override
    public double getHeight() {
        return 30;
    }

    @Override
    public QuadRect getQuadRect() {
        return new QuadRect(getX(), getY(), getWidth(), getHeight());
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

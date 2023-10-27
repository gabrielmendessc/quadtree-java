package gabrielmendessc.com.view;

import gabrielmendessc.com.QuadObject;
import gabrielmendessc.com.QuadRect;

public class Point implements QuadObject {

    private double x;
    private double y;
    private double xVel;
    private double yVel;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getXVel() {
        return xVel;
    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public double getXCenter() { return x + (getWidth() / 2); }

    public double getYCenter() { return y + (getHeight() / 2); }

}

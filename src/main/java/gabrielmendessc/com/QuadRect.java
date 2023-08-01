package gabrielmendessc.com;

public class QuadRect {

    private double x;
    private double y;
    private double width;
    private double height;

    public QuadRect(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(QuadRect quadRect) {

        if (isXInside(quadRect) && isYInside(quadRect)) {

            return true;

        }

        if (isWidthInside(quadRect) && (isYInside(quadRect) || isHeightInside(quadRect))) {

            return true;

        }

        return isHeightInside(quadRect) && (isXInside(quadRect) || isWidthInside(quadRect));

    }

    private boolean isHeightInside(QuadRect quadRect) {

        return (quadRect.getY() + quadRect.getHeight()) >= y && quadRect.getY() <= (y + height);

    }


    private boolean isWidthInside(QuadRect quadRect) {

        return (quadRect.getX() + quadRect.getWidth()) >= x && quadRect.getX() <= (x + width);

    }

    private boolean isXInside(QuadRect quadRect) {

        return quadRect.getX() >= x && quadRect.getX() <= (x + width);

    }


    private boolean isYInside(QuadRect quadRect) {

        return quadRect.getY() >= y && quadRect.getY() < (y + height);

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

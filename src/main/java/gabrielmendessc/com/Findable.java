package gabrielmendessc.com;

import java.awt.Rectangle;

public interface Findable<T> {

    boolean find(T t);

    double getX();

    double getY();

    double getWidth();

    double getHeight();

}

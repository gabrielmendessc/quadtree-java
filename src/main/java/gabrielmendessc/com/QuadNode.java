package gabrielmendessc.com;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class QuadNode<T extends Findable<T>> {

    private final QuadRect quadRect;
    private final int maxObjects;
    private QuadNode<T>[] quadNodeChildren;
    private List<T> objectList = new ArrayList<>();

    public QuadNode(double x, double y, double width, double height, int maxObjects) {
        this.quadRect = new QuadRect(x, y, width, height);
        this.maxObjects = maxObjects;
        this.quadNodeChildren = new QuadNode[4];
    }

    public void insert(T object) {

        if (Objects.nonNull(quadNodeChildren[0])) {

            int quadrant = getQuadrant(object.getX(), object.getY());
            if (!Objects.equals(quadrant, -1)) {

                quadNodeChildren[quadrant].insert(object);

                return;

            }

        }

        if (object.getX() < getX() || object.getX() > (getX() + getWidth())) {

            throw new RuntimeException("Object position out of bound!");

        }

        if (object.getY() < getY() || object.getY() > (getY() + getHeight())) {

            throw new RuntimeException("Object position out of bound!");

        }


        objectList.add(object);

        if (objectList.size() > maxObjects) {

            slice();

        }

    }

    public void remove(T object) {

        removeFromNode(object);

    }

    public List<T> query(QuadRect quadRect) {

       return queryFromNode(new ArrayList<>(), quadRect);

    }

    private List<T> queryFromNode(List<T> objectList, QuadRect quadRect) {

        if (Objects.isNull(quadNodeChildren[0])) {

            objectList.addAll(this.objectList.stream()
                    .filter(o -> o.getX() >= quadRect.getX() && o.getX() < (quadRect.getX() + quadRect.getWidth()) && o.getY() >= quadRect.getY() && o.getY() < (quadRect.getY() + quadRect.getHeight()))
                    .toList());

        } else {

            List<Integer> quadrantList = getQuadrant(quadRect);
            if (!quadrantList.isEmpty()) {

                quadrantList.forEach(quadrant -> objectList.addAll(quadNodeChildren[quadrant].queryFromNode(objectList, quadRect)));

            }

        }

        return objectList;

    }

    private boolean removeFromNode(T object) {

        if (Objects.isNull(quadNodeChildren[0])) {

            return removeValue(object);

        }

        int quadrant = getQuadrant(object.getX(), object.getY());
        if (!Objects.equals(quadrant, -1)) {

            if (quadNodeChildren[quadrant].removeFromNode(object)) {

                return tryMerge();

            }

        } else {

            removeValue(object);

        }

        return false;

    }

    private boolean removeValue(T object) {

        return objectList.removeIf(o -> o.find(object));

    }

    private boolean tryMerge() {

        List<T> mergedObjectList = new ArrayList<>();

        for (QuadNode<T> quadNodeChild : quadNodeChildren) {

            if (Objects.nonNull(quadNodeChild)) {

                if (Objects.isNull(quadNodeChild.getQuadNodeChildren()[0])) {

                    mergedObjectList.addAll(quadNodeChild.getObjectList());

                } else {

                    return false;

                }

            }

        }

        if (mergedObjectList.size() <= maxObjects) {

            this.objectList = mergedObjectList;
            quadNodeChildren = new QuadNode[4];

            return true;

        }

        return false;

    }

    private void slice() {

        quadNodeChildren[0] = new QuadNode<>(getX(), getY(), getWidth() / 2, getHeight() / 2, maxObjects);
        quadNodeChildren[1] = new QuadNode<>(getX() + (getWidth() / 2), getY(), getWidth() / 2, getHeight() / 2, maxObjects);
        quadNodeChildren[2] = new QuadNode<>(getX(), getY() + (getHeight() / 2), getWidth() / 2, getHeight() / 2, maxObjects);
        quadNodeChildren[3] = new QuadNode<>(getX() + (getWidth() / 2), getY() + (getHeight() / 2), getWidth() / 2, getHeight() / 2, maxObjects);

        Iterator<T> iterator = objectList.iterator();
        while (iterator.hasNext()) {

            insert(iterator.next());

            iterator.remove();

        }

    }

    private List<Integer> getQuadrant(QuadRect quadRect) {

        List<Integer> quadrantList = new ArrayList<>();
        for (int i = 0; i < quadNodeChildren.length; i++) {

            if (Objects.nonNull(quadNodeChildren[i]) && quadNodeChildren[i].getRectangle().contains(quadRect)) {

                quadrantList.add(i);

            }

        }

        return quadrantList;

    }

    private int getQuadrant(double x, double y) {

        if (isTopLeft(x, y)) {

            return 0;

        } else if (isTopRight(x, y)) {

            return 1;

        } else if (isBottomLeft(x, y)) {

            return 2;

        } else if (isBottomRight(x, y)) {

            return 3;

        }

        return -1;

    }

    private boolean isTopLeft(double x, double y) {
        return x >= getX() && (x < (getX() + (getWidth() / 2))) && (y >= getY()) && (y < (getY() + (getHeight() / 2)));
    }

    private boolean isTopRight(double x, double y) {
        return x >= (getX() + (getWidth() / 2)) && x <= (getX() + getWidth()) && (y >= getY()) && (y < (getY() + (getHeight() / 2)));
    }

    private boolean isBottomLeft(double x, double y) {
        return x >= getX() && (x < (getX() + (getWidth() / 2))) && (y >= (getY() + (getWidth() / 2))) && y <= (getY() + getHeight());
    }

    private boolean isBottomRight(double x, double y) {
        return x >= (getX() + (getWidth() / 2)) && x <= (getX() + getWidth()) && (y >= (getY() + (getWidth() / 2))) && y <= (getY() + getHeight());
    }

    public QuadRect getRectangle() {
        return quadRect;
    }

    public double getX() {
        return quadRect.getX();
    }

    public double getY() {
        return quadRect.getY();
    }

    public double getWidth() {
        return quadRect.getWidth();
    }

    public double getHeight() {
        return quadRect.getHeight();
    }

    public List<T> getObjectList() {
        return objectList;
    }

    public QuadNode<T>[] getQuadNodeChildren() {
        return quadNodeChildren;
    }

}

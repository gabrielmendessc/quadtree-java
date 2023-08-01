package gabrielmendessc.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class QuadNode<T extends QuadObject> {

    private final QuadRect quadRect;
    private final int maxObjects;
    private final int maxDepth;
    private final int depth;
    private QuadNode<T>[] quadNodeChildren;
    private Set<T> objectSet = Collections.synchronizedSet(new HashSet<>());

    public QuadNode(QuadRect quadRect, int maxObjects, int maxDepth) {
        this.quadRect = quadRect;
        this.maxObjects = maxObjects;
        this.quadNodeChildren = new QuadNode[4];
        this.maxDepth = maxDepth;
        this.depth = 1;
    }

    private QuadNode(QuadRect quadRect, int maxObjects, int maxDepth, int depth) {
        this.quadRect = quadRect;
        this.maxObjects = maxObjects;
        this.quadNodeChildren = new QuadNode[4];
        this.maxDepth = maxDepth;
        this.depth = depth;
    }

    public synchronized void insert(T object) {

        if (Objects.nonNull(quadNodeChildren[0])) {

            List<Integer> quadrantList = getQuadrant(object.getQuadRect());
            if (!quadrantList.isEmpty()) {

                quadrantList.forEach(quadrant -> quadNodeChildren[quadrant].insert(object));

            }

            return;

        }

        if (!object.getQuadRect().intersects(quadRect)) {

              throw new RuntimeException("Object position out of bound!");

        }

        objectSet.add(object);

        if (objectSet.size() > maxObjects && depth < maxDepth) {

            slice();

        }

    }

    public synchronized boolean remove(T object) {

        if (Objects.isNull(quadNodeChildren[0])) {

            return removeValue(object);

        }

        List<Integer> quadrantList = getQuadrant(object.getQuadRect());
        if (!quadrantList.isEmpty()) {

            boolean wasAnyChildrenObjectRemoved = false;
            for (QuadNode<T> quadNodeChild : quadNodeChildren) {

                if (quadNodeChild.remove(object)) {

                    wasAnyChildrenObjectRemoved = true;

                }

            }

            if (wasAnyChildrenObjectRemoved) {

                return tryMerge();

            }

        } else {

            removeValue(object);

        }

        return false;

    }

    public synchronized Set<T> query(QuadRect quadRect) {

        Set<T> objectSet = new HashSet<>();

        if (Objects.isNull(quadNodeChildren[0])) {

            objectSet.addAll(this.objectSet.stream()
                    .filter(o -> quadRect.intersects(o.getQuadRect()))
                    .toList());

        } else {

            getQuadrant(quadRect).forEach(quadrant -> objectSet.addAll(quadNodeChildren[quadrant].query(quadRect)));

        }

        return objectSet;

    }

    private boolean removeValue(T object) {

        return objectSet.remove(object);

    }

    private boolean tryMerge() {

        Set<T> mergedObjectSet = Collections.synchronizedSet(new HashSet<>());

        for (QuadNode<T> quadNodeChild : quadNodeChildren) {

            if (Objects.nonNull(quadNodeChild)) {

                if (Objects.isNull(quadNodeChild.getQuadNodeChildren()[0])) {

                    mergedObjectSet.addAll(quadNodeChild.getObjectSet());

                } else {

                    return false;

                }

            }

        }

        if (mergedObjectSet.size() <= maxObjects) {

            this.objectSet = mergedObjectSet;
            quadNodeChildren = new QuadNode[4];

            return true;

        }

        return false;

    }

    private void slice() {

        quadNodeChildren[0] = new QuadNode(new QuadRect(getX(), getY(), getWidth() / 2, getHeight() / 2), maxObjects, maxDepth, depth + 1);
        quadNodeChildren[1] = new QuadNode(new QuadRect(getX() + (getWidth() / 2), getY(), getWidth() / 2, getHeight() / 2), maxObjects, maxDepth, depth + 1);
        quadNodeChildren[2] = new QuadNode(new QuadRect(getX(), getY() + (getHeight() / 2), getWidth() / 2, getHeight() / 2), maxObjects, maxDepth, depth + 1);
        quadNodeChildren[3] = new QuadNode(new QuadRect(getX() + (getWidth() / 2), getY() + (getHeight() / 2), getWidth() / 2, getHeight() / 2), maxObjects, maxDepth, depth + 1);

        Iterator<T> iterator = objectSet.iterator();
        while (iterator.hasNext()) {

            insert(iterator.next());

            iterator.remove();

        }

    }

    private List<Integer> getQuadrant(QuadRect quadRect) {

        List<Integer> quadrantList = new ArrayList<>();
        for (int i = 0; i < quadNodeChildren.length; i++) {

            if (Objects.nonNull(quadNodeChildren[i]) && quadNodeChildren[i].getQuadRect().intersects(quadRect)) {

                quadrantList.add(i);

            }

        }

        return quadrantList;

    }

    public QuadRect getQuadRect() {
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

    public Set<T> getObjectSet() {
        return objectSet;
    }

    public QuadNode[] getQuadNodeChildren() {
        return quadNodeChildren;
    }

}

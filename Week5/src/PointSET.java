import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private TreeSet<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        if (!contains(p))
            pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point2d : pointSet) {
            point2d.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        TreeSet<Point2D> rangePointSet = new TreeSet<Point2D>();
        for (Point2D point2d : pointSet) {
            if (rect.contains(point2d))
                rangePointSet.add(point2d);
        }
        return rangePointSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        Point2D nearestPoint = null;
        double minDistance = 0;
        for (Point2D point2d : pointSet) {
            if (nearestPoint == null || p.distanceTo(point2d) < minDistance) {
                nearestPoint = point2d;
                minDistance = p.distanceTo(point2d);
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        PointSET pointSET = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            pointSET.insert(new Point2D(x, y));
        }
        pointSET.draw();

    }

}

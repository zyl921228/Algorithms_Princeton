import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private int n;
    private Node root;
    private TreeSet<Point2D> rangePointSet;
    private double minDistance;
    private Point2D nearestPoint;

    private class Node {
        private Point2D point2d;
        private Node left;
        private Node right;
        private boolean orient;

        public Node(Point2D point2dArg, Node leftArg, Node rightArg, boolean orientArg) {
            point2d = point2dArg;
            left = leftArg;
            right = rightArg;
            orient = orientArg;
        }
    }

    // construct an empty set of points
    public KdTree() {
        n = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // number of points in the set
    public int size() {
        return n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        root = insert(root, p, VERTICAL);

    }

    private Node insert(Node cNode, Point2D p, boolean orient) {
        // if Node position is null, insert Node
        if (cNode == null) {
            n++;
            return new Node(p, null, null, orient);
        }
        // if already contains, terminate
        if (p.equals(cNode.point2d))
            return cNode;

        // compare x & y coordinates
        if (orient == VERTICAL) {
            if (p.x() < cNode.point2d.x())
                cNode.left = insert(cNode.left, p, HORIZONTAL);
            else
                cNode.right = insert(cNode.right, p, HORIZONTAL);
        } else {
            if (p.y() < cNode.point2d.y())
                cNode.left = insert(cNode.left, p, VERTICAL);
            else
                cNode.right = insert(cNode.right, p, VERTICAL);
        }
        return cNode;

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        Node cNode = root;
        boolean orient = VERTICAL;
        // main loop - contains
        while (true) {
            // if Node position is null, doesn't contain, return false
            if (cNode == null)
                return false;
            // if contains, return true
            if (p.equals(cNode.point2d))
                return true;
            // compare x & y coordinates
            if (orient == VERTICAL) {
                if (p.x() < cNode.point2d.x())
                    cNode = cNode.left;
                else
                    cNode = cNode.right;
            } else {
                if (p.y() < cNode.point2d.y())
                    cNode = cNode.left;
                else
                    cNode = cNode.right;
            }
            orient = !orient;
        }

        // main loop - contains - end
    }

    // draw all points to standard draw
    public void draw() {

        draw(root, null, null, null);

    }

    private void draw(Node node, Node pNode, Node ppNode, Node pppNode) {
        if (node == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point2d.draw();
        StdDraw.setPenRadius();
        if (pNode == null) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point2d.x(), 0, node.point2d.x(), 1);
        }
        if (pNode != null && ppNode == null) {
            StdDraw.setPenColor(StdDraw.BLUE);
            double border1 = 0;
            if (node.point2d.x() < pNode.point2d.x()) {
                border1 = 0;
            } else {
                border1 = 1;
            }
            StdDraw.line(pNode.point2d.x(), node.point2d.y(), border1, node.point2d.y());
        }
        if (pNode != null && ppNode != null && pppNode == null) {
            StdDraw.setPenColor(StdDraw.RED);
            double border1 = 0;
            if (node.point2d.y() < pNode.point2d.y()) {
                border1 = 0;
            } else {
                border1 = 1;
            }
            StdDraw.line(node.point2d.x(), pNode.point2d.y(), node.point2d.x(), border1);
        }
        if (pppNode != null && node.orient == HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.BLUE);
            double border1 = pppNode.point2d.x(), border2 = pNode.point2d.x();
            if (node.point2d.x() < border1 && node.point2d.x() < border2) {
                border1 = Math.min(border1, border2);
                border2 = 0;
            } else if (node.point2d.x() >= border1 && node.point2d.x() >= border2) {
                border1 = Math.max(border1, border2);
                border2 = 1;
            }
            StdDraw.line(border1, node.point2d.y(), border2, node.point2d.y());
        }
        if (pppNode != null && node.orient == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            double border1 = pppNode.point2d.y(), border2 = pNode.point2d.y();
            if (node.point2d.y() < border1 && node.point2d.y() < border2) {
                border1 = Math.min(border1, border2);
                border2 = 0;
            } else if (node.point2d.y() >= border1 && node.point2d.y() >= border2) {
                border1 = Math.max(border1, border2);
                border2 = 1;
            }
            StdDraw.line(node.point2d.x(), border1, node.point2d.x(), border2);
        }
        StdOut.println(node.point2d);
        if (node.left != null)
            draw(node.left, node, pNode, ppNode);
        if (node.right != null)
            draw(node.right, node, pNode, ppNode);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        rangePointSet = new TreeSet<Point2D>();
        range(rect, root);
        return rangePointSet;
    }

    private void range(RectHV rect, Node node) {
        if (node == null)
            return;
        if (rect.contains(node.point2d))
            rangePointSet.add(node.point2d);
        if (node.orient == VERTICAL) // Vertical
        {
            if (node.point2d.x() < rect.xmin()) {
                range(rect, node.right);
            } else if (node.point2d.x() > rect.xmax()) {
                range(rect, node.left);
            } else {
                range(rect, node.left);
                range(rect, node.right);
            }
        } else { // Horizontal
            if (node.point2d.y() < rect.ymin()) {
                range(rect, node.right);
            } else if (node.point2d.y() > rect.ymax()) {
                range(rect, node.left);
            } else {
                range(rect, node.left);
                range(rect, node.right);
            }
        }

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        nearestPoint = null;
        minDistance = 0;
        nearest(p, root);
        return nearestPoint;
    }

    private void nearest(Point2D p, Node node) {
        if (node == null)
            return;
        // for root node
        if (nearestPoint == null) {
            nearestPoint = node.point2d;
            minDistance = node.point2d.distanceTo(p);
        }
        // check if it's nearer than current
        if (node.point2d.distanceTo(p) < minDistance) {
            nearestPoint = node.point2d;
            minDistance = node.point2d.distanceTo(p);
        }
        if (node.orient == VERTICAL) // Vertical
        {
            if (p.x() < node.point2d.x()) {
                nearest(p, node.left);
                if (node.point2d.x() - p.x() < minDistance)
                    nearest(p, node.right);
            } else {
                nearest(p, node.right);
                if (p.x() - node.point2d.x() < minDistance)
                    nearest(p, node.left);
            }
        } else { // Horizontal
            if (p.y() < node.point2d.y()) {
                nearest(p, node.left);
                if (node.point2d.y() - p.y() < minDistance)
                    nearest(p, node.right);
            } else {
                nearest(p, node.right);
                if (p.y() - node.point2d.y() < minDistance)
                    nearest(p, node.left);
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        KdTree kdTree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            kdTree.insert(new Point2D(x, y));
        }
        kdTree.draw();

        StdOut.println(kdTree.size());
    }

}

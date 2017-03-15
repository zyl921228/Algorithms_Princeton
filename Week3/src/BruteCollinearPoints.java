import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final Point[] pointsList;
    private final LineSegment[] segmentsList;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        pointsList = points.clone();
        ArrayList<LineSegment> sList = new ArrayList<LineSegment>();
        Arrays.sort(pointsList);
        for (int i = 0; i < pointsList.length; i++) {
            if (i < pointsList.length - 1 && pointsList[i].compareTo(pointsList[i + 1]) == 0)
                throw new IllegalArgumentException();
            for (int j = i + 1; j < pointsList.length; j++)
                for (int k = j + 1; k < pointsList.length; k++)
                    for (int l = k + 1; l < pointsList.length; l++) {
                        if ((Double.compare(pointsList[i].slopeTo(pointsList[j]),
                                pointsList[j].slopeTo(pointsList[k])) == 0)
                                && (Double.compare(pointsList[j].slopeTo(pointsList[k]),
                                        pointsList[k].slopeTo(pointsList[l])) == 0)) {
                            sList.add(new LineSegment(pointsList[i], pointsList[l]));
                        }
                    }
        }
        segmentsList = new LineSegment[sList.size()];
        sList.toArray(segmentsList);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentsList.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segmentsList.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

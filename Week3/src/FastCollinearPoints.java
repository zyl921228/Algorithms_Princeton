import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final Point[] pointsList;
    private final LineSegment[] segmentsList;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        pointsList = points.clone();
        Arrays.sort(pointsList);
        for (int i = 0; i < pointsList.length; i++) {
            if (i < pointsList.length - 1 && pointsList[i].compareTo(pointsList[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
        ArrayList<LineSegment> sList = new ArrayList<LineSegment>();
        for (int i = 0; i < pointsList.length - 1; i++) {
            Arrays.sort(pointsList);
            Point iPoint = pointsList[i];
            if (i > 0)
                Arrays.sort(pointsList, 0, i, iPoint.slopeOrder());
            Arrays.sort(pointsList, i + 1, pointsList.length, iPoint.slopeOrder());
            ArrayList<Double> downSlope = new ArrayList<Double>();
            ArrayList<Double> upSlope = new ArrayList<Double>();
            for (int p = 0; p < i; p++) {
                downSlope.add(iPoint.slopeTo(pointsList[p]));
            }

            for (int p = i + 1; p < pointsList.length; p++) {
                upSlope.add(iPoint.slopeTo(pointsList[p]));
            }
            int head = 0, tail = 0;
            while (tail <= upSlope.size()) {
                if (tail < upSlope.size()
                        && Double.compare((double) upSlope.get(head), (double) upSlope.get(tail)) == 0) {

                } else {
                    if (tail - head >= 3) {
                        if (!downSlope.contains(iPoint.slopeTo(pointsList[i + tail])))
                            sList.add(new LineSegment(iPoint, pointsList[i + tail]));
                    }
                    head = tail;
                }
                tail++;
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
        // Arrays.sort(points);
        // Arrays.sort(points,points[0].slopeOrder());
        for (Point p : points) {
            p.draw();
            // StdOut.println(p.toString());
            // StdOut.println(p.slopeTo(points[0]));
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

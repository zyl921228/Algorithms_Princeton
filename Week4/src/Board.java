import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int n;
    private final int[][] blocksArr;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        blocksArr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocksArr[i][j] = blocks[i][j];
            }
        }
    }

    private void exch(int[][] array, int i1, int j1, int i2, int j2) {
        int tmp = array[i2][j2];
        array[i2][j2] = array[i1][j1];
        array[i1][j1] = tmp;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int numHamming = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if ((i != n - 1 || j != n - 1) && (blocksArr[i][j] != n * i + j + 1))
                    numHamming++;
            }
        return numHamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int numManhattan = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int bValue = blocksArr[i][j];
                if (blocksArr[i][j] != 0) {
                    int ir = (bValue - 1) / n;
                    int jr = (bValue - 1) % n;
                    numManhattan = numManhattan + Math.abs(i - ir) + Math.abs(j - jr);
                }
            }
        return numManhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] blocksArrTwin;
        blocksArrTwin = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocksArrTwin[i][j] = blocksArr[i][j];
            }
        }
        int iex1 = 0, jex1 = 0, iex2 = 0, jex2 = 0;
        boolean isFirstFound = false;
        boolean isSecondFound = false;
        for (int i = 0; i < n; i++) {
            if (isSecondFound)
                break;
            for (int j = 0; j < n; j++) {
                if (blocksArrTwin[i][j] != 0) {
                    if (!isFirstFound) {
                        iex1 = i;
                        jex1 = j;
                        isFirstFound = true;
                    } else {
                        iex2 = i;
                        jex2 = j;
                        exch(blocksArrTwin, iex1, jex1, iex2, jex2);
                        isSecondFound = true;
                        break;
                    }
                }
            }
        }
        // make twins - end
        return new Board(blocksArrTwin);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        return ((Board) y).toString().equals(this.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> pqNebr = new ArrayList<Board>();
        int[][] blocksArrNebr = blocksArr.clone();
        int i0 = 0, j0 = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocksArrNebr[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        }
        // left
        if (j0 > 0) {
            int[][] blocksArrNebrIn = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocksArrNebrIn[i][j] = blocksArrNebr[i][j];
                }
            }
            exch(blocksArrNebrIn, i0, j0, i0, j0 - 1);
            pqNebr.add(new Board(blocksArrNebrIn));
        }
        // right
        if (j0 < n - 1) {
            int[][] blocksArrNebrIn = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocksArrNebrIn[i][j] = blocksArrNebr[i][j];
                }
            }
            exch(blocksArrNebrIn, i0, j0, i0, j0 + 1);
            pqNebr.add(new Board(blocksArrNebrIn));
        }
        // top
        if (i0 > 0) {
            int[][] blocksArrNebrIn = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocksArrNebrIn[i][j] = blocksArrNebr[i][j];
                }
            }
            exch(blocksArrNebrIn, i0, j0, i0 - 1, j0);
            pqNebr.add(new Board(blocksArrNebrIn));
        }
        // bottom
        if (i0 < n - 1) {
            int[][] blocksArrNebrIn = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocksArrNebrIn[i][j] = blocksArrNebr[i][j];
                }
            }
            exch(blocksArrNebrIn, i0, j0, i0 + 1, j0);
            pqNebr.add(new Board(blocksArrNebrIn));
        }

        return pqNebr;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(n);
        for (int i = 0; i < n; i++) {
            sBuilder.append("\n");
            for (int j = 0; j < n; j++) {
                sBuilder.append(" " + blocksArr[i][j] + " ");
            }
        }
        sBuilder.append("\n\n");
        return sBuilder.toString();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.twin());
    }

}

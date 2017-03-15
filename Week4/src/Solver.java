import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean isSolvable = true;
    private Node finalNode;
    private ArrayList<Board> finalSequence = new ArrayList<Board>();

    private class Node implements Comparable<Node> {
        private Board nodeBoard;
        private Node preNode;
        private int numOfMoves;
        private int priority;

        public Node(Board nodeBoardArg, Node preNodeArg, int numOfMovesArg) {
            nodeBoard = nodeBoardArg;
            preNode = preNodeArg;
            numOfMoves = numOfMovesArg;
            priority = numOfMoves + nodeBoard.manhattan();
        }

        @Override
        public int compareTo(Node o) {
            return this.priority - o.priority;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // ensure initial is not null
        if (initial == null)
            throw new NullPointerException();
        // n-by-n array
        int n = initial.dimension();
        // create goalBoard
        int[][] goalBoardArray = new int[n][n];
        int count = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goalBoardArray[i][j] = count++;
            }
        }
        goalBoardArray[n - 1][n - 1] = 0;
        Board goalBoard = new Board(goalBoardArray);
        // main loop
        MinPQ<Node> pqNode;
        MinPQ<Node> pqNodeTwin;
        pqNode = new MinPQ<Node>();
        pqNode.insert(new Node(initial, null, 0));
        pqNodeTwin = new MinPQ<Node>();
        pqNodeTwin.insert(new Node(initial.twin(), null, 0));
        boolean isTwin = false;
        while (true) {
            Node minNode;
            if (isTwin) {
                minNode = pqNodeTwin.delMin();
            } else {
                minNode = pqNode.delMin();
            }
            if (minNode.nodeBoard.equals(goalBoard)) {
                finalNode = minNode;
                break;
            }
            for (Board boardNebr : minNode.nodeBoard.neighbors()) {
                if (minNode.preNode == null || !boardNebr.equals(minNode.preNode.nodeBoard)) {
                    if (isTwin)
                        pqNodeTwin.insert(new Node(boardNebr, minNode, minNode.numOfMoves + 1));
                    else
                        pqNode.insert(new Node(boardNebr, minNode, minNode.numOfMoves + 1));
                }
            }
            isTwin = !isTwin;
        }

        if (isTwin) {
            isSolvable = false;
            return;
        }

        // put optimal sequence into stack
        Node currentNode = finalNode;
        finalSequence.add(currentNode.nodeBoard);
        while (currentNode.preNode != null) {
            currentNode = currentNode.preNode;
            finalSequence.add(currentNode.nodeBoard);
        }
        Collections.reverse(finalSequence);

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return finalSequence.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable())
            return finalSequence;
        else
            return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}

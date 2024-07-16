package solver;

import model.Cell;
import model.MainBoard;
import model.Piece;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;



public class Backtracking implements SolveAlgorithm {

    class Result {
        Cell c; int id;

        public Result(Cell c, int id) {
            this.c = c;
            this.id = id;
        }

        public int getId() {
            return id;
        }
        public String toString() {
            return c.toString();
        }
    }

    @Override
    public void solve(MainBoard mb, List<Piece> pieces) throws InterruptedException {
        Collections.sort(pieces);

        Stack<Result> stack = new Stack<>();
        walkThroughBoard(mb, pieces, 0, stack);

        stack.sort(Comparator.comparingInt(Result::getId));
        System.out.println(stack);
    }

    public boolean walkThroughBoard(MainBoard mb,
                                   List<Piece> pieces,
                                   int currentPieceIndex,
                                   Stack<Result> result) throws InterruptedException {

        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();

        if (currentPieceIndex == pieces.size()) return mb.isSolved();

        Piece p = pieces.get(currentPieceIndex);
        List<Cell> fittingCells = p.getFittingCells();

        for (Cell c : fittingCells) {
            mb.overlapPiece(p, c);
            result.push(new Result(c, p.getId()));

            if (walkThroughBoard(mb, pieces, currentPieceIndex+1, result)) {
                return true;
            }

            mb.removePiece(p, c);
            result.pop();
        }

        return false;
    }
}

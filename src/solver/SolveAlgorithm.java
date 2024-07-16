package solver;

import model.MainBoard;
import model.Piece;

import java.util.List;

public interface SolveAlgorithm {
    void solve(MainBoard mb, List<Piece> pieces) throws InterruptedException;
}

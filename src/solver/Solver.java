package solver;

import model.Cell;
import model.MainBoard;
import model.Piece;
import parser.FileInputParser;
import parser.InputParser;
import resource.ResourceLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Solver {
    private List<String> resourcePaths;
    private Iterator<String> levelIterator;

    public Solver(String folderName) throws IOException, URISyntaxException {
        resourcePaths = ResourceLoader.getResourcePaths(folderName);
        levelIterator = resourcePaths.iterator();
    }

    public boolean hasNextLevel() {
        return levelIterator.hasNext();
    }

    public void solve() throws InterruptedException {
        String path = levelIterator.next();
        solveOne(path);
    }

    public static void solveOne(String path) throws InterruptedException {
        System.out.println(path);

        InputParser ip = new FileInputParser(path);

        MainBoard mb = ip.getBoard();
        System.out.println(mb);

        List<Piece> pieces = ip.getPieces();
        pieces.stream().forEach(x -> x.parseTopLeftCorners(mb));

        List<Cell> result = new ArrayList<>();
        walkThroughBoard(mb, pieces, 0, result);
        System.out.println(result);

        System.out.println(System.lineSeparator());
    }

    public static boolean walkThroughBoard(MainBoard mb, List<Piece> pieces, int currentPieceIndex, List<Cell> result) throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();

        if (currentPieceIndex == pieces.size()) return mb.isSolved();

        Piece p = pieces.get(currentPieceIndex);
        List<Cell> fittingCells = p.getFittingCells();
        for (Cell c : fittingCells) {
            mb.overlapPiece(p, c);
            result.add(c);

            if (walkThroughBoard(mb, pieces, currentPieceIndex+1, result)) {
                return true;
            }

            mb.removePiece(p, c);
            result.remove(result.size()-1);
        }

        return false;
    }
}

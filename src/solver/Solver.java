package solver;

import model.Cell;
import model.MainBoard;
import model.Piece;
import parser.FileInputParser;
import parser.InputParser;
import resource.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Solver {
    private List<String> resourcePaths;
    private Iterator<String> levelIterator;
    private SolveAlgorithm algorithm;

    public Solver(String folderName, SolveAlgorithm algorithm) throws IOException, URISyntaxException {
        this(algorithm);
        resourcePaths = ResourceLoader.getResourcePaths(folderName);
        levelIterator = resourcePaths.iterator();
    }

    public Solver(SolveAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean hasNextLevel() {
        return levelIterator.hasNext();
    }

    public void solve() throws InterruptedException, FileNotFoundException {
        String path = levelIterator.next();
        solveOne(path);
    }

    public void solveOne(String path) throws InterruptedException, FileNotFoundException {
        System.out.println(path);

        InputParser ip = new FileInputParser(path);

        MainBoard mb = ip.getBoard();
        System.out.println(mb);

        List<Piece> pieces = ip.getPieces();
//        pieces.stream().forEach(System.out::println);

        algorithm.solve(mb, pieces);

        System.out.println(System.lineSeparator());
    }
}

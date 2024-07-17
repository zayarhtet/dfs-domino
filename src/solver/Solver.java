package solver;

import model.MainBoard;
import model.Piece;
import parser.FileInputParser;
import parser.InputParser;
import resource.ClassPathResourceLoader;
import resource.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Solver {
    private List<String> resourcePaths;
    private Iterator<String> levelIterator;
    private SolveAlgorithm algorithm;
    private ResourceLoader resourceLoader;

    public Solver(String folderName, SolveAlgorithm algorithm, ResourceLoader resourceLoader) throws IOException, URISyntaxException {
        this(algorithm, resourceLoader);
        resourcePaths = this.resourceLoader.getResourcePaths(folderName);
        levelIterator = resourcePaths.iterator();
    }

    public Solver(SolveAlgorithm algorithm, ResourceLoader rl) {
        this.algorithm = algorithm;
        this.resourceLoader = rl;
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

        InputParser ip = new FileInputParser(path, resourceLoader);

        MainBoard mb = ip.getBoard();
        System.out.println(mb);

        List<Piece> pieces = ip.getPieces();
//        pieces.stream().forEach(System.out::println);

        algorithm.solve(mb, pieces);

        System.out.println(System.lineSeparator());
    }
}

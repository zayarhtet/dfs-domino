package solver;

import model.MainBoard;
import model.Piece;
import parser.FileInputParser;
import parser.InputParser;
import resource.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

public class Solver {
    private List<String> resourcePaths;
    private Iterator<String> levelIterator;
    public Solver(String folderName) throws IOException, URISyntaxException {
        resourcePaths = ResourceLoader.getResourcePaths(folderName);
        System.out.println(resourcePaths.size());
        levelIterator = resourcePaths.iterator();
    }

    public void solve() {
        String path = levelIterator.next();

        InputParser ip = new FileInputParser(path);
        MainBoard mb = ip.getBoard();
        System.out.println(mb.toString());
        List<Piece> pieces = ip.getPieces();
        pieces.stream().forEach(x -> System.out.println(x.toString()));

        System.out.println(System.lineSeparator());
    }

    public boolean hasNextLevel() {
        return levelIterator.hasNext();
    }
}

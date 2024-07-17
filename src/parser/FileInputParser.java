package parser;

import model.Board;
import model.Cell;
import model.MainBoard;
import model.Piece;
import resource.ResourceLoader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FileInputParser implements InputParser {
    private int maxDepth;
    private String boardString;
    private String pieceString;

    private MainBoard mb;

    public FileInputParser(String filename, ResourceLoader resourceLoader) throws FileNotFoundException {
        InputStream is = resourceLoader.loadResource(filename);

        try (Scanner sc = new Scanner(is)) {
            String line = readNextLine(sc);
            maxDepth = Integer.parseInt(line);

            boardString = readNextLine(sc);
            pieceString = readNextLine(sc);
        } catch (Exception e) {
            System.out.println("Error while loading the level");
        }
    }

    private String readNextLine(Scanner sc) {
        String line = "";
        while (sc.hasNextLine() && line.trim().isEmpty()) {
            line = sc.nextLine();
        }
        return line;
    }

    @Override
    public MainBoard getBoard() {
        String [] rows = boardString.split(",");
        int rowLength = rows.length;
        int colLength = rows[0].length();

        MainBoard mb = new MainBoard(rowLength, colLength, maxDepth);
        parseBoard(rows, mb);

        this.mb = mb;
        return this.mb;
    }

    @Override
    public List<Piece> getPieces() {
        String [] rawPieces = pieceString.split(" ");
        List<Piece> pieces = new ArrayList<>();

        IntStream.range(0, rawPieces.length).forEach(x -> {
            String [] rows = rawPieces[x].split(",");
            Piece p = new Piece(rows.length, rows[0].length());
            parseBoard(rows, p);
            p.parseTopLeftCorners(mb);
            pieces.add(p);
        });
        return pieces;
    }

    public void parseBoard(String[] rows, Board b) {
        IntStream.range(0, b.getTotalRow()).parallel().forEach(i -> {
            String [] cells = rows[i].split("");
            IntStream.range(0, cells.length).parallel().forEach(j -> {
                try {
                    b.setCell(new Cell(i,j), parseInt(cells[j]));
                } catch (NumberFormatException e) {
                    System.out.println("board cell cannot be parsed.");
                }
            });
        });
    }

    public int parseInt(String target) {
        if (target.equals("X")) return 1;
        else if (target.equals(".")) return 0;
        return Integer.parseInt(target);
    }
}

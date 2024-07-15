package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Piece extends Board implements Comparable<Piece> {
    private static int idCounter = 0;

    private final int id;
    private final List<Cell> fittingCells;

    public Piece(int row, int col) {
        super(row, col);
        id = idCounter++;
        fittingCells = new ArrayList<>();
    }

    public void parseTopLeftCorners(MainBoard b) {
        final int mainRows = b.totalRow;
        final int mainCols = b.totalCol;
        final int pieceRows = totalRow;
        final int pieceCols = totalCol;

        IntStream.rangeClosed(0, mainCols - pieceCols).forEach(x -> {
            IntStream.rangeClosed(0, mainRows - pieceRows).forEach(y -> {
                fittingCells.add(new Cell(x, y));
            });
        });
    }

    public List<Cell> getFittingCells() { return fittingCells; }

    public int getId() { return id; }

    public int getSize() { return fittingCells.size(); }

    @Override
    public int compareTo(Piece other) {
        return Integer.compare(this.getSize(), other.getSize());
    }

    @Override
    public String toString() {
        System.out.println(fittingCells);
        return "Piece: \n" + super.toString();
    }
}

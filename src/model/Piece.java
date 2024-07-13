package model;

import java.util.ArrayList;
import java.util.List;

public class Piece extends Board {
    private List<Cell> fittingCells;

    public Piece(int row, int col) {
        super(row, col);
    }

    public void parseTopLeftCorners(MainBoard b) {
        fittingCells = new ArrayList<>();
        int mainRows = b.totalRow;
        int mainCols = b.totalCol;
        int pieceRows = totalRow;
        int pieceCols = totalCol;

        for (int y = 0; y <= mainRows - pieceRows; y++) {
            for (int x = 0; x <= mainCols - pieceCols; x++) {
                fittingCells.add(new Cell(x, y));
            }
        }
    }

    public List<Cell> getFittingCells() { return fittingCells; }

    @Override
    public String toString() {
        return "Piece: \n" + super.toString();
    }
}

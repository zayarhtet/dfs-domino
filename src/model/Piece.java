package model;

public class Piece extends Board {

    private Cell topLeftPoint;

    public Piece(int row, int col) {
        super(row, col);
    }

    public void setTopLeftPoint(int row, int col) {
        topLeftPoint = new Cell(row, col);
    }

    @Override
    public String toString() {
        return "Piece: \n" + super.toString();
    }
}

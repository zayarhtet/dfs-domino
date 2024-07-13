package model;

public class Cell {
    public int row;
    public int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}

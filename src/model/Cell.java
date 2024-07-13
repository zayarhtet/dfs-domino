package model;

public class Cell {
    public int x;
    public int y;

    public Cell(int row, int col) {
        this.x = row;
        this.y = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

package model;

import java.util.stream.IntStream;

public class Board {
    protected final int totalRow, totalCol;

    public final int [][] board;

    public Board(int row, int col) {
        totalRow = row;
        totalCol = col;
        board = new int[row][col];
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public int getTotalRow() {
        return totalRow;
    }

    public int getTotalCol() {
        return totalCol;
    }

    public void setCell(Cell target, int value) {
        board[target.x][target.y] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board [\n");
        IntStream.range(0, totalRow).forEach(i -> {
            sb.append("  [");
            IntStream.range(0, totalCol).forEach(j -> {
                sb.append(board[i][j]);
                if (j < totalCol - 1) { sb.append(", "); }
            });
            sb.append("]\n");
        });
        sb.append("]");
        return sb.toString();
    }
}

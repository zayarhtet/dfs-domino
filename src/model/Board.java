package model;

import java.util.stream.IntStream;

public class Board implements Cloneable {

    protected final int totalRow, totalCol;

    protected int [][] board;

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

    public void addCell(Cell target, int value) {
        board[target.row][target.col] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board [\n");
        for (int i = 0; i < totalRow; i++) {
            sb.append("  [");
            for (int j = 0; j < totalCol; j++) {
                sb.append(board[i][j]);
                if (j < totalCol - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board cloned = (Board) super.clone();
        cloned.board = new int[this.totalRow][this.totalCol];

        IntStream.range(0, this.totalRow).forEach(i -> System.arraycopy(this.board[i], 0, cloned.board[i], 0, this.totalCol));

        return cloned;
    }
}

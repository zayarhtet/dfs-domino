package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainBoard extends Board {
    public final int maxDepth;

    public MainBoard(int row, int col, int depth) {
        super(row, col);
        maxDepth = depth;
    }

    public void overlapPiece(Piece piece, Cell topLeftCorner) {
        IntStream.range(0, piece.totalRow).forEach(i -> {
            final int finalI = i;
            IntStream.range(0, piece.totalCol).forEach(j -> {
                int boardRow = topLeftCorner.y + finalI;
                int boardCol = topLeftCorner.x + j;
                board[boardRow][boardCol] += piece.getCell(finalI, j);
                board[boardRow][boardCol] %= maxDepth;
            });
        });
    }

    public void removePiece(Piece piece, Cell topLeftCorner) {
        IntStream.range(0, piece.totalRow).forEach(i -> {
            final int finalI = i;
            IntStream.range(0, piece.totalCol).forEach(j -> {
                int boardRow = topLeftCorner.y + finalI;
                int boardCol = topLeftCorner.x + j;
                board[boardRow][boardCol] -= piece.getCell(finalI, j);
                if (board[boardRow][boardCol] < 0) {
                    board[boardRow][boardCol] += maxDepth;
                }
            });
        });
    }

    public boolean isSolved() {
        for (int[] row : board) {
            for (int cell : row) if (cell != 0) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("maxDepth: " + maxDepth + "\n");
        sb.append("MainBoard: \n");
        sb.append(super.toString());
        return sb.toString();
    }
}
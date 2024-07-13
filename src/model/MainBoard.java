package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainBoard extends Board {
    public final int maxDepth;

    public MainBoard(int row, int col, int depth) {
        super(row, col);
        maxDepth = depth;
    }

    public boolean overlapPiece(Piece piece, Cell topLeftCorner) {
        IntStream.range(0, piece.getTotalRow()).forEach(i -> {
            final int finalI = i;
            IntStream.range(0, piece.getTotalCol()).forEach(j -> {
                int boardRow = finalI + topLeftCorner.y;
                int boardCol = j + topLeftCorner.x;
                board[boardRow][boardCol] += piece.getCell(finalI, j);
                board[boardRow][boardCol] %= maxDepth;
            });
        });
        return true;
    }

    public void removePiece(Piece piece, Cell topLeft) {
        for (int i = 0; i < piece.totalRow; i++) {
            for (int j = 0; j < piece.totalCol; j++) {
                board[topLeft.y + i][topLeft.x + j] -= piece.getCell(i, j);
                if (board[topLeft.y + i][topLeft.x + j] < 0) {
                    board[topLeft.y + i][topLeft.x + j] += maxDepth;
                }
            }
        }
    }

    public boolean isSolved() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != 0) return false;
            }
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
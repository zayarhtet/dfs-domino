package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainBoard extends Board {
    private final int maxDepth;

    public MainBoard(int row, int col, int depth) {
        super(row, col);
        maxDepth = depth;
    }

    public boolean overlapPiece(Piece piece, Cell topLeftCorner) {
        if (piece.getTotalRow() > (totalRow - topLeftCorner.row ) || piece.getTotalCol() > (totalCol - topLeftCorner.col)) {
            return false;
        }

        IntStream.range(0, piece.getTotalRow()).forEach(i -> {
            final int finalI = i;
            IntStream.range(0, piece.getTotalCol()).forEach(j -> {
                int boardRow = finalI + topLeftCorner.row;
                int boardCol = j + topLeftCorner.col;
                board[boardRow][boardCol] += 1;
                board[boardRow][boardCol] %= maxDepth;
            });
        });
        return true;
    }

    public boolean isSolved() {
        int answer = Arrays.stream(board).map(row ->
                        Arrays.stream(row).reduce(0, Integer::sum)
                    ).reduce(0, Integer::sum);

        return answer == 0;
    }

    @Override
    public MainBoard clone() throws CloneNotSupportedException {
//        final MainBoard newBoard = new MainBoard(totalRow, totalCol, maxDepth);
//        IntStream.range(0, totalRow).forEach(i -> {
//            final int finalI = i;
//            IntStream.range(0, totalCol).forEach(j -> {
//                newBoard.addCell(new Cell(finalI, j), getCell(finalI, j));
//            });
//        });
//        return newBoard;
        MainBoard cloned = (MainBoard) super.clone();
        return cloned;
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
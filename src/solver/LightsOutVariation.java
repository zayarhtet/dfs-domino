package solver;

import java.util.ArrayList;
import java.util.List;

public class LightsOutVariation {
    static int[][] board;
    static int maxDepth;

    public static void main(String[] args) {
        // Example board and pieces
        int[][] board = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] piece1 = {
                {1, 1},
                {1, 1}
        };

        int[][] piece2 = {
                {0, 2},
                {2, 0}
        };

        maxDepth = 10;

        List<int[][]> pieces = new ArrayList<>();
        pieces.add(piece1);
        pieces.add(piece2);

        LightsOutVariation.board = board;

        if (solve(board, pieces)) {
            System.out.println("Solution found:");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }
    }

    static boolean solve(int[][] board, List<int[][]> pieces) {
        int rows = board.length;
        int cols = board[0].length;
        int pieceCount = pieces.size();
        int n = rows * cols;

        // Initialize the matrix (A | b)
        int[][] matrix = new int[n][pieceCount + 1];

        // Fill the matrix with the board and pieces information
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                matrix[index][pieceCount] = (maxDepth - board[i][j]) % maxDepth;

                for (int k = 0; k < pieces.size(); k++) {
                    int[][] piece = pieces.get(k);
                    if (i + piece.length <= rows && j + piece[0].length <= cols) {
                        for (int pi = 0; pi < piece.length; pi++) {
                            for (int pj = 0; pj < piece[0].length; pj++) {
                                if (piece[pi][pj] != 0) {
                                    int cellIndex = (i + pi) * cols + (j + pj);
                                    matrix[cellIndex][k] = (matrix[cellIndex][k] + piece[pi][pj]) % maxDepth;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Solve the system using Gaussian elimination
        if (!gaussianElimination(matrix, n, pieceCount)) {
            return false;
        }

        // Extract the solution
        int[] solution = new int[pieceCount];
        for (int i = 0; i < pieceCount; i++) {
            solution[i] = matrix[i][pieceCount];
        }

        // Apply the solution to the board
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == 1) {
                int[][] piece = pieces.get(i);
                placePiece(board, piece, i / cols, i % cols);
            }
        }

        return true;
    }

    static boolean gaussianElimination(int[][] matrix, int n, int pieceCount) {
        for (int i = 0; i < n; i++) {
            // Search for maximum in this column
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(matrix[k][i]) > Math.abs(matrix[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Swap maximum row with current row
            int[] temp = matrix[i];
            matrix[i] = matrix[maxRow];
            matrix[maxRow] = temp;

            // Make all rows below this one 0 in current column
            for (int k = i + 1; k < n; k++) {
                if (matrix[k][i] != 0) {
                    int factor = matrix[k][i] / matrix[i][i];
                    for (int j = i; j <= pieceCount; j++) {
                        matrix[k][j] = (matrix[k][j] - factor * matrix[i][j] + maxDepth) % maxDepth;
                    }
                }
            }
        }

        // Solve equation matrix[n-1][n-1] * x[n-1] = matrix[n-1][n]
        for (int i = n - 1; i >= 0; i--) {
            if (matrix[i][i] == 0) {
                return false; // No solution
            }

            matrix[i][pieceCount] = (matrix[i][pieceCount] / matrix[i][i]) % maxDepth;
            for (int k = i - 1; k >= 0; k--) {
                matrix[k][pieceCount] = (matrix[k][pieceCount] - matrix[k][i] * matrix[i][pieceCount] + maxDepth) % maxDepth;
            }
        }

        return true;
    }

    static void placePiece(int[][] board, int[][] piece, int row, int col) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                board[row + i][col + j] = (board[row + i][col + j] + piece[i][j]) % maxDepth;
            }
        }
    }

    static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}

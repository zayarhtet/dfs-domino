package solver;

import java.util.*;

class Piece {
    int[][] shape;
    int rows, cols;

    Piece(int[][] shape) {
        this.shape = shape;
        this.rows = shape.length;
        this.cols = shape[0].length;
    }
}

class Position {
    int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class LightsOutSolver {

    private static final int maxDepth = 5; // Example max depth, you can change this as needed

    public static void main(String[] args) {
        int[][] mainBoard = {
                {1, 2, 3, 4},
                {2, 3, 4, 5},
                {3, 4, 5, 6},
                {4, 5, 6, 7}
        };
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new int[][]{
                {1, 1},
                {1, 1}
        }));
        pieces.add(new Piece(new int[][]{
                {2, 2},
                {2, 2}
        }));

        List<Position> solution = findPositions(mainBoard, pieces);
        if (solution.isEmpty()) {
            System.out.println("No solution found.");
        } else {
            for (Position pos : solution) {
                System.out.println(pos);
            }
        }
    }

    public static List<Position> findPositions(int[][] mainBoard, List<Piece> pieces) {
        int rows = mainBoard.length;
        int cols = mainBoard[0].length;
        int numEquations = rows * cols;
        int numVariables = pieces.size() * rows * cols;

        int[][] matrix = new int[numEquations][numVariables + 1];
        int[] boardVector = new int[numEquations];

        // Initialize the board vector
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardVector[i * cols + j] = mainBoard[i][j];
            }
        }

        // Fill the matrix with the effects of placing each piece at each possible position
        int variableIndex = 0;
        for (Piece piece : pieces) {
            for (int x = 0; x <= rows - piece.rows; x++) {
                for (int y = 0; y <= cols - piece.cols; y++) {
                    for (int i = 0; i < piece.rows; i++) {
                        for (int j = 0; j < piece.cols; j++) {
                            int boardIndex = (x + i) * cols + (y + j);
                            matrix[boardIndex][variableIndex] = piece.shape[i][j];
                        }
                    }
                    variableIndex++;
                }
            }
        }

        // Print the matrix for debugging
        System.out.println("Initial matrix:");
        printMatrix(matrix, boardVector);

        // Perform Gaussian Elimination
        if (gaussianElimination(matrix, boardVector)) {
            // Extract solution
            List<Position> result = new ArrayList<>();
            variableIndex = 0;
            for (Piece piece : pieces) {
                for (int x = 0; x <= rows - piece.rows; x++) {
                    for (int y = 0; y <= cols - piece.cols; y++) {
                        if (boardVector[variableIndex] != 0) {
                            result.add(new Position(x, y));
                        }
                        variableIndex++;
                    }
                }
            }
            return result;
        } else {
            return Collections.emptyList(); // No solution found
        }
    }

    private static boolean gaussianElimination(int[][] matrix, int[] boardVector) {
        int numRows = matrix.length;
        int numCols = matrix[0].length - 1;

        for (int i = 0; i < numRows; i++) {
            // Find pivot
            int pivotRow = i;
            while (pivotRow < numRows && matrix[pivotRow][i] == 0) {
                pivotRow++;
            }
            if (pivotRow == numRows) {
                continue; // No pivot found, skip this column
            }

            // Swap rows
            int[] temp = matrix[i];
            matrix[i] = matrix[pivotRow];
            matrix[pivotRow] = temp;

            int tempValue = boardVector[i];
            boardVector[i] = boardVector[pivotRow];
            boardVector[pivotRow] = tempValue;

            // Normalize pivot row
            int pivotValue = matrix[i][i];
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = (matrix[i][j] * modInverse(pivotValue, maxDepth)) % maxDepth;
            }
            boardVector[i] = (boardVector[i] * modInverse(pivotValue, maxDepth)) % maxDepth;

            // Eliminate other rows
            for (int k = 0; k < numRows; k++) {
                if (k != i) {
                    int factor = matrix[k][i];
                    for (int j = 0; j < numCols; j++) {
                        matrix[k][j] = (matrix[k][j] - factor * matrix[i][j] + maxDepth) % maxDepth;
                    }
                    boardVector[k] = (boardVector[k] - factor * boardVector[i] + maxDepth) % maxDepth;
                }
            }
        }

        // Check for consistency
        for (int i = 0; i < numRows; i++) {
            boolean allZero = true;
            for (int j = 0; j < numCols; j++) {
                if (matrix[i][j] != 0) {
                    allZero = false;
                    break;
                }
            }
            if (allZero && boardVector[i] != 0) {
                return false; // No solution
            }
        }

        return true;
    }

    private static int modInverse(int a, int mod) {
        int t = 0, newT = 1;
        int r = mod, newR = a;

        while (newR != 0) {
            int quotient = r / newR;
            int temp = newT;
            newT = t - quotient * newT;
            t = temp;

            temp = newR;
            newR = r - quotient * newR;
            r = temp;
        }

        if (r > 1) throw new ArithmeticException("No inverse exists");
        if (t < 0) t = t + mod;

        return t;
    }

    private static void printMatrix(int[][] matrix, int[] boardVector) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols - 1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("| " + boardVector[i]);
            System.out.println();
        }
    }
}

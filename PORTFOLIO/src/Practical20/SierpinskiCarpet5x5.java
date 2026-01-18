package Practical20;

/**
 * Practical 20 – Recursive Sierpiński Carpet (5x5 variation).
 *
 * Supported sizes: 5, 25, 125
 *
 * Time Complexity:
 * - Initialisation: O(N^2)
 * - Recursion: T(N) = 20T(N/5) + O(N^2) ⇒ O(N^2)
 * - Printing: O(N^2)
 *
 * Sub-optimal:
 * - Printing or converting the board inside recursion would increase overhead.
 */
public class SierpinskiCarpet5x5 {

    private final char[][] board;
    private final int size;

    public SierpinskiCarpet5x5(int size) {
        this.size = size;
        board = new char[size][size];
        initialise();
        carve(0, 0, size, 0);
    }

    // Initialise board with '.'
    private void initialise() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = '.';
            }
        }
    }

    // Recursive carving using 5x5 subdivision
    private void carve(int row, int col, int currentSize, int level) {
        if (currentSize < 5) return;

        int subSize = currentSize / 5;
        char mark = (char) ('0' + level);

        // Remove blocks 7, 9, 13, 17, 19
        int[][] remove = {{1,1},{1,3},{2,2},{3,1},{3,3}};

        for (int[] p : remove) {
            for (int r = row + p[0] * subSize; r < row + (p[0] + 1) * subSize; r++) {
                for (int c = col + p[1] * subSize; c < col + (p[1] + 1) * subSize; c++) {
                    board[r][c] = mark;
                }
            }
        }

        // Recurse into remaining 20 sub-arrays
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (isRemoved(r, c)) continue;
                carve(row + r * subSize, col + c * subSize, subSize, level + 1);
            }
        }
    }

    private boolean isRemoved(int r, int c) {
        return (r == 1 && c == 1) || (r == 1 && c == 3) ||
                (r == 2 && c == 2) || (r == 3 && c == 1) ||
                (r == 3 && c == 3);
    }

    // Print board
    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) System.out.print(cell);
            System.out.println();
        }
    }

    // Generate 5x5, 25x25, 125x125 carpets
    public static void main(String[] args) {
        int[] sizes = {5, 25, 125};

        for (int s : sizes) {
            System.out.println("\nSierpiński Carpet " + s + "x" + s + "\n");
            new SierpinskiCarpet5x5(s).printBoard();
        }
    }
}

/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #15
 * 1 - 5026231225 - Harbima R
 * 2 - 5026231134 - M Artha Maulana S
 * 3 - 5026231171 - Redo Adika D
 */

package sudoku;
import java.util.Random;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
   // All variables have package access
   // The numbers on the puzzle
   int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   // The clues - isGiven (no need to guess) or need to guess
   boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

   // Constructor
   public Puzzle() {
      super();
   }

   // Generate a new puzzle given the number of cells to be guessed, which can be used
   //  to control the difficulty level.
   // This method shall set (or update) the arrays numbers and isGiven
   // public void newPuzzle(int cellsToGuess) {
   //    // I hardcode a puzzle here for illustration and testing.
   //    int[][] hardcodedNumbers =
   //       {{5, 3, 4, 6, 7, 8, 9, 1, 2},
   //        {6, 7, 2, 1, 9, 5, 3, 4, 8},
   //        {1, 9, 8, 3, 4, 2, 5, 6, 7},
   //        {8, 5, 9, 7, 6, 1, 4, 2, 3},
   //        {4, 2, 6, 8, 5, 3, 7, 9, 1},
   //        {7, 1, 3, 9, 2, 4, 8, 5, 6},
   //        {9, 6, 1, 5, 3, 7, 2, 8, 4},
   //        {2, 8, 7, 4, 1, 9, 6, 3, 5},
   //        {3, 4, 5, 2, 8, 6, 1, 7, 9}};

   //    // Copy from hardcodedNumbers into the array "numbers"
   //    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
   //       for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
   //          numbers[row][col] = hardcodedNumbers[row][col];
   //       }
   //    }

   //    // Need to use input parameter cellsToGuess!
   //    // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
   //    boolean[][] hardcodedIsGiven =
   //       {{true, true, true, true, true, false, true, true, true},
   //        {true, true, true, true, true, true, true, true, false},
   //        {true, true, true, true, true, true, true, true, true},
   //        {true, true, true, true, true, true, true, true, true},
   //        {true, true, true, true, true, true, true, true, true},
   //        {true, true, true, true, true, true, true, true, true},
   //        {true, true, true, true, true, true, true, true, true},
   //        {true, true, true, true, true, true, true, true, true},
   //        {true, true, true, true, true, true, true, true, true}};

   //    // Copy from hardcodedIsGiven into array "isGiven"
   //    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
   //       for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
   //          isGiven[row][col] = hardcodedIsGiven[row][col];
   //       }
   //    }
   // }

   //(For advanced students) use singleton design pattern for this class


   public void newPuzzle(int cellsToGuess) {
      Random numberGenerator = new Random();

      // Reset semua angka di puzzle
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
           for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
               numbers[row][col] = 0;
               isGiven[row][col] = false;
          }
       }

       // Generate angka random untuk mengisi sebagian grid
       for (int i = 0; i < cellsToGuess; i++) {
           int row = numberGenerator.nextInt(SudokuConstants.GRID_SIZE);
           int col = numberGenerator.nextInt(SudokuConstants.GRID_SIZE);
           int value = numberGenerator.nextInt(9) + 1; // Angka dari 1 hingga 9

           // Pastikan tidak ada angka duplikat di baris, kolom, atau subgrid
           if (isSafeToPlace(row, col, value)) {
               numbers[row][col] = value;
               isGiven[row][col] = true; // Tandai sebagai angka yang sudah diberikan
           } else {
               i--; // Ulangi iterasi jika angka tidak valid
           }
       }
   }

// Helper method untuk memeriksa apakah angka valid di posisi tertentu
private boolean isSafeToPlace(int row, int col, int value) {
    // Cek baris dan kolom
    for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
        if (numbers[row][i] == value || numbers[i][col] == value) {
            return false;
        }
    }

    // Cek subgrid
    int startRow = row - row % SudokuConstants.SUBGRID_SIZE;
    int startCol = col - col % SudokuConstants.SUBGRID_SIZE;
    for (int i = 0; i < SudokuConstants.SUBGRID_SIZE; i++) {
        for (int j = 0; j < SudokuConstants.SUBGRID_SIZE; j++) {
            if (numbers[startRow + i][startCol + j] == value) {
                return false;
            }
        }
    }
    return true;
}

}
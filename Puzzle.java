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
    public void tutorialPuzzle(int cellsToGuess) {
       // I hardcode a puzzle here for illustration and testing.
       int[][] hardcodedNumbers =
          {{5, 3, 4, 6, 7, 8, 9, 1, 2},
           {6, 7, 2, 1, 9, 5, 3, 4, 8},
           {1, 9, 8, 3, 4, 2, 5, 6, 7},
           {8, 5, 9, 7, 6, 1, 4, 2, 3},
           {4, 2, 6, 8, 5, 3, 7, 9, 1},
           {7, 1, 3, 9, 2, 4, 8, 5, 6},
           {9, 6, 1, 5, 3, 7, 2, 8, 4},
           {2, 8, 7, 4, 1, 9, 6, 3, 5},
           {3, 4, 5, 2, 8, 6, 1, 7, 9}};
 
       // Copy from hardcodedNumbers into the array "numbers"
       for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
          for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
             numbers[row][col] = hardcodedNumbers[row][col];
          }
       }
 
       // Need to use input parameter cellsToGuess!
       // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
       boolean[][] hardcodedIsGiven =
          {{true, true, true, true, true, false, true, true, true},
           {true, true, true, true, true, true, true, true, false},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true}};
 
       // Copy from hardcodedIsGiven into array "isGiven"
       for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
          for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
             isGiven[row][col] = hardcodedIsGiven[row][col];
          }
       }
    }
 
    public void newPuzzle(int cellsToGuess) {
        Random random = new Random();
    
        // Atur semua sel sebagai diberikan terlebih dahulu
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                isGiven[row][col] = true;
            }
        }
    
        // Pilih sel acak untuk dihapus hingga jumlah yang diinginkan
        int removedCells = 0;
        while (removedCells < cellsToGuess) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);
    
            if (isGiven[row][col]) { // Pastikan tidak menghapus sel yang sudah kosong
                isGiven[row][col] = false;
                removedCells++;
            }
        }
    
    
        // Langkah 1: Tukar subgrid secara acak
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i += SudokuConstants.SUBGRID_SIZE) {
            int subgrid1 = random.nextInt(SudokuConstants.SUBGRID_SIZE) + i;
            int subgrid2 = random.nextInt(SudokuConstants.SUBGRID_SIZE) + i;
            if (subgrid1 != subgrid2) {
                swapSubgrids(subgrid1, subgrid2);
            }
        }
    
        // Langkah 2: Tukar baris di dalam setiap subgrid
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i += SudokuConstants.SUBGRID_SIZE) {
            int row1 = random.nextInt(SudokuConstants.SUBGRID_SIZE) + i;
            int row2 = random.nextInt(SudokuConstants.SUBGRID_SIZE) + i;
            if (row1 != row2) {
                swapRows(row1, row2);
            }
        }
    
        // Langkah 3: Tukar kolom di dalam setiap subgrid
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i += SudokuConstants.SUBGRID_SIZE) {
            int col1 = random.nextInt(SudokuConstants.SUBGRID_SIZE) + i;
            int col2 = random.nextInt(SudokuConstants.SUBGRID_SIZE) + i;
            if (col1 != col2) {
                swapColumns(col1, col2);
            }
        }
    }
    
    private void swapSubgrids(int subgrid1, int subgrid2) {
        // Tukar seluruh baris antara dua subgrid yang dipilih
        int startRow1 = (subgrid1 / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        int startRow2 = (subgrid2 / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
    
        for (int i = 0; i < SudokuConstants.SUBGRID_SIZE; i++) {
            swapRows(startRow1 + i, startRow2 + i);
        }
    }
    
    private void swapRows(int row1, int row2) {
        // Tukar dua baris secara langsung
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            int temp = numbers[row1][col];
            numbers[row1][col] = numbers[row2][col];
            numbers[row2][col] = temp;
        }
    }
    
    private void swapColumns(int col1, int col2) {
        // Tukar dua kolom secara langsung
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            int temp = numbers[row][col1];
            numbers[row][col1] = numbers[row][col2];
            numbers[row][col2] = temp;
        }
    }
    
    public boolean isPlayable() {
        // Periksa validitas baris dan kolom
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (!isValidSet(getRow(i)) || !isValidSet(getColumn(i))) {
                return false;
            }
        }
    
        // Periksa validitas setiap subgrid
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row += SudokuConstants.SUBGRID_SIZE) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col += SudokuConstants.SUBGRID_SIZE) {
                if (!isValidSet(getSubgrid(row, col))) {
                    return false;
                }
            }
        }
    
        return true;
    }
    
    private boolean isValidSet(int[] values) {
        // Periksa apakah setiap nilai dalam set unik dan berada dalam rentang yang valid
        boolean[] seen = new boolean[SudokuConstants.GRID_SIZE + 1];
        for (int value : values) {
            if (value < 1 || value > SudokuConstants.GRID_SIZE || seen[value]) {
                return false;
            }
            seen[value] = true;
        }
        return true;
    }
    
    private int[] getRow(int row) {
        // Ambil seluruh nilai dari baris tertentu
        return numbers[row];
    }
    
    private int[] getColumn(int col) {
        // Ambil seluruh nilai dari kolom tertentu
        int[] column = new int[SudokuConstants.GRID_SIZE];
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            column[row] = numbers[row][col];
        }
        return column;
    }
    
    private int[] getSubgrid(int startRow, int startCol) {
        // Ambil seluruh nilai dari subgrid tertentu
        int[] subgrid = new int[SudokuConstants.GRID_SIZE];
        int index = 0;
        for (int i = 0; i < SudokuConstants.SUBGRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.SUBGRID_SIZE; j++) {
                subgrid[index++] = numbers[startRow + i][startCol + j];
            }
        }
        return subgrid;
    }
    
 } 

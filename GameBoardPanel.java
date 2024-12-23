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
 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;
 
 public class GameBoardPanel extends JPanel {
     private static final long serialVersionUID = 1L;  // untuk mencegah peringatan serial
     private MoveListener moveListener;
 
     // Method untuk menetapkan listener
     public void setMoveListener(MoveListener listener) {
         this.moveListener = listener;
     }
 
     // Panggil listener ketika langkah dilakukan
     public void handleMove(boolean isCorrect) {
         if (moveListener != null) {
             moveListener.onMove(isCorrect);
         }
     }
 
     // Interface untuk listener
     public interface MoveListener {
         void onMove(boolean isCorrect);
     }
 
     // Contoh ketika pemain mengisi angka
     public void playerMove(int row, int col, int value) {
         boolean isCorrect = checkMove(row, col, value); // Periksa apakah langkah benar
         handleMove(isCorrect); // Panggil listener
     }
 
     private boolean checkMove(int row, int col, int value) {
         // Bandingkan nilai input dengan nilai yang benar di puzzle
         return value == puzzle.numbers[row][col];
     }
 
     // Define named constants for UI sizes
     public static final int CELL_SIZE = 60;   // Cell width/height in pixels
     public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
     public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
                                               // Board width/height in pixels
 
     // Define properties
     /** The game board composes of 9x9 Cells (customized JTextFields) */
     private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
     /** It also contains a Puzzle with array numbers and isGiven */
     private Puzzle puzzle = new Puzzle();
     private int guess;
 
     /** Constructor */
     public GameBoardPanel() {
         super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel
 
         // Allocate the 2D array of Cell, and added into JPanel.
         for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
             for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                 cells[row][col] = new Cell(row, col);
                 super.add(cells[row][col]);   // JPanel
             }
         } 
 
         CellInputListener listener = new CellInputListener();
 
         for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
             for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                 if (cells[row][col].isEditable()) {
                     cells[row][col].addActionListener(listener);
                 }
             }
         }
         super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
     }
 
     public void newGame() {
         puzzle.tutorialPuzzle(2);
 
         for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
             for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                 cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
             }
         }
     }
 
     public boolean isSolved() {
         for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
             for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                 if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                     return false;
                 }
             }
         }
         return true;
     }
 
     private class CellInputListener implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent e) {
             Cell sourceCell = (Cell) e.getSource();
 
             try {
                 int numberIn = Integer.parseInt(sourceCell.getText());
 
                 if (numberIn < 1 || numberIn > 9) {
                     throw new NumberFormatException("Invalid input: Number must be between 1 and 9.");
                 }
 
                 System.out.println("You entered " + numberIn);
 
                 boolean isCorrect = checkMove(sourceCell.row, sourceCell.col, numberIn);
                 if (isCorrect) {
                     sourceCell.status = CellStatus.CORRECT_GUESS;
                 } else {
                     sourceCell.status = CellStatus.WRONG_GUESS;
                 }
                 sourceCell.paint();
 
                 playerMove(sourceCell.row, sourceCell.col, numberIn);
 
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(
                     null, 
                     "Please enter a valid number (1–9).", 
                     "Invalid Input", 
                     JOptionPane.ERROR_MESSAGE
                 );
                 sourceCell.setText("");
             }
         }
     }
 
     public void newGame(String diff) {
 
         switch(diff) {
             case "Expert": guess = 60;
                 break;
             case "Difficult": guess = 45;
                 break;
             case "Intermediate": guess = 30;
                 break;
             case "Easy": guess = 20;
                 break;
             default: guess = 45;
         } 
         puzzle.newPuzzle(guess);
 
         for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
             for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                 cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
             }
         }
     }

     public void resetGame() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                // Hanya bersihkan sel yang isGiven = false
                if (!puzzle.isGiven[row][col]) {
                    cells[row][col].setText(""); // Hapus nilai dari sel
                    cells[row][col].status = CellStatus.TO_GUESS; // Set status ulang ke TO_GUESS
                    cells[row][col].paint(); // Perbarui tampilan sel
                }
            }
        }
    }
    

     public int getCellsToGuess() {
        return guess; // Mengembalikan jumlah cell yang diisi saat permainan dimulai
    }
     
 }
 

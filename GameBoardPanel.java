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
    private static final long serialVersionUID = 1L;  // to prevent serial warning

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

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        // .........
        // [TODO 3]
        CellInputListener listener = new CellInputListener();

        // [TODO 4] Adds this common listener to all editable cells
        // .........
        // [TODO 4]
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Generate a new puzzle; and reset the game board of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame() {
        // Generate a new puzzle
        puzzle.newPuzzle(2);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
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

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Dapatkan referensi dari JTextField yang memicu event ini
            Cell sourceCell = (Cell) e.getSource();

            try {
                // Ambil angka yang dimasukkan
                int numberIn = Integer.parseInt(sourceCell.getText());

                // Recheck apabila input angka dalam rentang 1–9
                if (numberIn < 1 || numberIn > 9) {
                    throw new NumberFormatException("Invalid input: Number must be between 1 and 9.");
                }

                // Notif Angka yang Dimasukkan
                System.out.println("You entered " + numberIn);

                // Cek apakah jawaban benar
                if (numberIn == sourceCell.number) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                }
                sourceCell.paint(); // Re-paint cell berdasarkan status

                // Cek apakah puzzle sudah selesai
                if (isSolved()) {
                   JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Please enter a valid number (1–9).", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE
                );
                sourceCell.setText(""); // Kosongkan input jika tidak valid
            }
        }
    }

    public void resetGame(String diff){
        int filled;
        
        switch(diff){
            case "Easy": filled = 60;
                break;
            case "Intermediate": filled = 45;
                break;
            case "Difficult" : filled = 30;
                break;
            case "Expert" : filled = 20;
                break;
            default: filled = 45;    // Diff awal
        } puzzle.newPuzzle(filled); // New Puzzle dengan diff yang ditentukan player              

        for(int row = 0; row<SudokuConstants.GRID_SIZE; ++row){
            for(int col = 0; col<SudokuConstants.GRID_SIZE; ++col){
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }
}
/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #15
 * 1 - 5026231225 - Harbima Razan A
 * 2 - 5026231134 - M Artha Maulana S
 * 3 - 5026231171 - Redo Adika D
 */

package sudoku;
import java.awt.*;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel();
   // JButton btnNewGame = new JButton("New Game");
   JButton btnResetGame = new JButton("Reset Game");

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);

      // Add a button to the south to re-start the game via board.newGame()
      JPanel buttonPanel = new JPanel();
      // buttonPanel.add(btnNewGame);
      cp.add(buttonPanel, BorderLayout.SOUTH);

      // Add action listener for the "New Game" button
      // btnNewGame.addActionListener(e -> board.newGame());

      String[] diff = {"Easy", "Intermediate", "Difficult", "Expert"};  // Seluruh Difficulties yang ada
      JComboBox<String> diffSelector = new JComboBox<>(diff); // Panel yang berisi seluruh tingkat kesulitan untuk dipilih
      buttonPanel.add(diffSelector); // Tambahkan dropdown
      // buttonPanel.add(btnNewGame);
      buttonPanel.add(btnResetGame);
      cp.add(buttonPanel, BorderLayout.SOUTH);

      // Tombol Reset Game
      btnResetGame.addActionListener(e -> {
         int response = JOptionPane.showConfirmDialog(this, "Are You Sure?", "Game Restart Confirmation", JOptionPane.YES_NO_OPTION);
         if (response == JOptionPane.YES_OPTION) {
             // Ambil tingkat kesulitan yang dipilih
             String selectedDifficulty = (String) diffSelector.getSelectedItem();
             board.resetGame(selectedDifficulty); // Panggil resetGame dengan tingkat kesulitan
         }
      });
      // Initialize the game board to start the game
      board.newGame();

      

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
      setVisible(true);
   }

   /** The entry main() entry method */
   public static void main(String[] args) {
      // [TODO 1] Create and run the main Sudoku program
      SwingUtilities.invokeLater(() -> new SudokuMain());
   }


}

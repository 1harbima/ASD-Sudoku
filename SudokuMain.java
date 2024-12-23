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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Program utama Sudoku
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L; // untuk mencegah peringatan serial

    // variabel privat
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnPauseGame = new JButton("Pause Game");
    JButton btnReset = new JButton("Reset"); // Tombol baru untuk Reset

    // Komponen terkait Timer
    private Timer gameTimer;
    private int elapsedSeconds = 0;
    private int elapsedMinutes = 0;
    private JLabel timerLabel = new JLabel("Time: 0s");

    // Komponen skor
    private int score = 0;
    private JLabel scoreLabel = new JLabel("Score: 0");

    // Timer untuk tips interval
    private Timer tipsPopupTimer;

    // Dropdown tingkat kesulitan
    private JComboBox<String> diffSelector;

    public SudokuMain() {
        showWelcomeScreen(); // Tampilkan welcome screen sebelum permainan dimulai

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Panel tengah untuk papan permainan
        cp.add(board, BorderLayout.CENTER);

        // Panel bawah untuk tombol dan pilihan tingkat kesulitan
        JPanel buttonPanel = new JPanel();
        String[] diff = {"Easy", "Intermediate", "Difficult", "Expert"};
        diffSelector = new JComboBox<>(diff);
        buttonPanel.add(diffSelector);
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnPauseGame);
        buttonPanel.add(btnReset); // Tambahkan tombol Reset ke panel
        cp.add(buttonPanel, BorderLayout.SOUTH);

        // Panel atas untuk timer dan skor
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(timerLabel);
        topPanel.add(scoreLabel);
        cp.add(topPanel, BorderLayout.NORTH);

        // Tambahkan fungsionalitas tombol New Game
        btnNewGame.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to start a new " + diffSelector.getSelectedItem() + " game?", "Confirm New Game", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                newGame();
            }
        });

        btnReset.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset your game? You will start from zero.", "Confirm Reset", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
        // Reset waktu
        elapsedSeconds = 0;
        elapsedMinutes = 0;
        timerLabel.setText("Time: 0s");

        // Reset skor
        score = 0;
        scoreLabel.setText("Score: 0");

        // Reset papan permainan
        board.resetGame();

        // Restart timer
        gameTimer.restart();
    }
}); 


        // Tambahkan fungsionalitas tombol Pause
        btnPauseGame.addActionListener(e -> {
            if (gameTimer.isRunning()) {
                gameTimer.stop();
                btnPauseGame.setText("Resume Game");
            } else {
                gameTimer.start();
                btnPauseGame.setText("Pause Game");
            }
        });

        // Timer untuk menampilkan tips otomatis setiap 30 detik
        tipsPopupTimer = new Timer(30000, e -> {
            String[] tips = {
                "Tip 1: Focus on the number that appears most frequently in the grid. Solve that number first.",
                "Tip 2: Check each row, column, and subgrid for missing numbers.",
                "Tip 3: Use the process of elimination. Find cells that have only one possible number.",
                "Tip 4: Avoid guessing! Each move should be based on logic.",
                "Tip 5: If you're stuck, double-check the numbers you've already filled. There might be a mistake.",
                "Tip 6: Work systematically. Start solving from the top-left corner to the bottom-right.",
                "Tip 7: Practice makes perfect. The more puzzles you solve, the better you'll get!",
                "Tip 8: Careful with your inputs! Every wrong input will cost you your score.",
                "Tip 9: Time is money! The quicker you input the right answer, the higher the score you will get."
            };
            int randomIndex = (int) (Math.random() * tips.length); // Pilih tips secara acak
            JOptionPane.showMessageDialog(this, tips[randomIndex], "Tips and Tricks", JOptionPane.INFORMATION_MESSAGE);
        });
        tipsPopupTimer.start(); // Mulai timer untuk menampilkan tips

        // Inisialisasi Timer
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.isSolved()) {
                    gameTimer.stop();
                    tipsPopupTimer.stop(); // Hentikan timer tips saat permainan selesai
                    timerLabel.setText("Puzzle Solved in: " + elapsedMinutes + "m " + elapsedSeconds + "s");
                    JOptionPane.showMessageDialog(SudokuMain.this, "Congratulations! Puzzle Solved in " + elapsedMinutes + " minutes " + elapsedSeconds + " seconds with a score of " + score + "!", "Game Completed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    elapsedSeconds++;
                    if (elapsedSeconds == 60) {
                        elapsedMinutes++;
                        elapsedSeconds = 0;
                    }
                    timerLabel.setText("Time: " + elapsedMinutes + "m " + elapsedSeconds + "s");
                }
            }
        });
        gameTimer.start();

        // Inisialisasi papan permainan dengan pelacakan skor
        board.setMoveListener((isCorrect) -> {
            if (isCorrect) {
                if(elapsedMinutes > 59){
                    score += 100;
                } else score += 1000 - (elapsedMinutes * 15 + elapsedSeconds / 6); // Tambah skor untuk gerakan yang benar
            } else {
                score -= 85; // Kurangi skor untuk gerakan yang salah
            }
            scoreLabel.setText("Score: " + score); // Perbarui label skor
        });

        board.newGame();

        setTitle("Group 15 Sudoku Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
    }

    private void showWelcomeScreen() {
      // Membuat panel kustom
      JPanel welcomePanel = new JPanel(new BorderLayout());
  
      // Tambahkan gambar
      ImageIcon icon = new ImageIcon(("C:/Users/harbi/Downloads/goofygato.jpg")); // Custom Welcome Page
      JLabel imageLabel = new JLabel(icon);
      welcomePanel.add(imageLabel, BorderLayout.CENTER);
  
      // Tambahkan teks
      JLabel textLabel = new JLabel("<html><div style='text-align: center;'>"
          + "Welcome to Sudoku!<br><br>"
          + "Solve the puzzle by filling in the grid so that every row, column,<br>"
          + "and subgrid contains the numbers 1 to 9 without repetition.<br><br>"
          + "Good luck and have fun!</div></html>", JLabel.CENTER);
      textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
      welcomePanel.add(textLabel, BorderLayout.SOUTH);
  
      // Tampilkan dialog
      JOptionPane.showMessageDialog(this, welcomePanel, "Welcome", JOptionPane.INFORMATION_MESSAGE);
  }
  

    private void newGame() {
        elapsedSeconds = 0;
        elapsedMinutes = 0;
        score = 0; // Reset skor ke 0
        timerLabel.setText("Time: 0s");
        scoreLabel.setText("Score: 0"); // Perbarui label skor untuk mencerminkan reset
        String selectedDifficulty = (String) diffSelector.getSelectedItem();
        board.newGame(selectedDifficulty); // Reset papan permainan dengan tingkat kesulitan yang dipilih
        gameTimer.restart();
        tipsPopupTimer.restart(); // Mulai ulang timer tips saat permainan baru dimulai
    }

    /** Metode utama untuk memulai program */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuMain());
    }
}

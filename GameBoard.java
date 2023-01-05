package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JPanel {

    private Wordle wordle;
    Cell[][] wordleGrid;

    public GameBoard() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        wordle = new Wordle();
        wordleGrid = wordle.getBoard();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                wordleGrid[i][j] = new Cell();
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                wordle.getBoard()[i][j] = new Cell();
            }
        }
        addKeyListener(new KeyAdapter() {
            public void keyPress(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    playTurn();
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        wordle.reset();
        repaint();
        requestFocusInWindow();
    }

    public void setGuess(String testGuess) {
        // if (wordle.isValidWord(testGuess)) {
        wordle.setGuess(testGuess);
        // }
    }

    public Wordle getWordle() {
        return wordle;
    }

    public void playTurn() {
        wordle.checkNewGuess();
        wordle.isGameOver();
        repaint();
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                wordleGrid[i][j].setX(50 + j * 70);
                wordleGrid[i][j].setY(50 + i * 70);
                if (wordle.getBoard()[i][j].getColor().equals(Color.GREEN)) {
                    g.setColor(Color.GREEN);
                    g.fillRect(70 * j+50, 50 + 70 * i, 70, 70);
                } else if (wordle.getBoard()[i][j].getColor().equals(Color.YELLOW)) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(70 * j+50, 50 + 70 * i, 70, 70);
                }
                g.setColor(Color.BLACK);
                char c = wordle.getBoard()[i][j].getLetter();
                if (c != '\u0000') {
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                }
                g.drawString(String.valueOf(c), 75+70 * j, 95+70 * i);
                wordleGrid[i][j].paintComponent(g);
            }
        }
    }

    public void save() {
        wordle.save();
    }

    public void load() {
        wordle.load();
    }
}

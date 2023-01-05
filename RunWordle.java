package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RunWordle implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Wordle");
        frame.setLocation(300, 300);
        GameBoard board = new GameBoard();

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        final JTextField inputField = new JTextField(10);
        inputField.setPreferredSize(new Dimension(10, 20));
        inputField.setMaximumSize(new Dimension(10, 20));
        inputField.isFocusable();
        inputField.addActionListener(e -> {
            board.setGuess(inputField.getText());
            board.playTurn();
            board.repaint();
        });
        control_panel.add(inputField);

        final JButton instructions = new JButton("Instructions");
        instructions.setFocusable(false);
        instructions.addActionListener(e -> {
            JLabel label = new JLabel(
                    "<html>The goal of Wordle is to guess the secret five-letter word." +
                            " Input a five letter word and press enter."
                            +
                            "<br/>If the letter at a particular index of a guess matches" +
                            " the letter at the same index of the secret word, then it becomes green."
                            +
                            "<br/>Else, if the letter at a particular index of the" +
                            " input matches the letter at a different index of the" +
                            " secret word, then it becomes yellow. "
                            +
                            "<br/>Otherwise, it becomes gray. The game is over when" +
                            " the player has guessed the secret word.<html>"
            );
            JOptionPane.showMessageDialog(null, label, "How To Play", JOptionPane.PLAIN_MESSAGE);
        }
        );
        control_panel.add(instructions);

        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save());
        status_panel.add(save);

        final JButton load = new JButton("Load");
        load.addActionListener(e -> {
            board.reset();
            board.load();
            board.repaint();
            frame.setVisible(true);
        });
        status_panel.add(load);

        // Main playing area

        frame.add(board, BorderLayout.CENTER);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        board.reset();
    }
}

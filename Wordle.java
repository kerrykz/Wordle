package org.cis1200.wordle;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Wordle {

    private Cell[][] board = new Cell[6][5];
    private int numTurns;
    private boolean gameOver;

    private boolean done;
    private String targetWord = "CRANE";
    private String guess = "";
    private ArrayList<String> listOfWords = new ArrayList<String>();
    private BufferedReader br;
    private boolean winner;

    public Wordle() {
        reset();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public Wordle(String target) {
        reset(target);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public void makeWordList() {
        try {
            br = new BufferedReader(new FileReader("./files/allWords.txt"));
            if (br == null) {
                throw new IllegalArgumentException();
            }
            String currLine = br.readLine();
            while (currLine != null) {
                listOfWords.add(currLine.toUpperCase());
                currLine = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File allWords.txt not found");
        } catch (IOException e) {
            System.out.println("IO Error");
        }

    }

    public boolean isValidWord(String word) {
        if (word != null && word.length() == 5 && listOfWords.contains(word.toUpperCase())) {
            return true;
        }
        return false;
    }

    public void setGuess(String word) {
        if (isValidWord(word)) {
        guess = word.toUpperCase();
        }
    }

    public void checkNewGuess() {
        System.out.println(targetWord);
        if (isValidWord(guess) && !done) {
            ArrayList<Color> guessColors = new ArrayList<Color>();
            char[] targetChars = targetWord.toCharArray();
            numTurns++;
            char[] guessChars = guess.toCharArray();
            ArrayList<Character> tempTargetChars = new ArrayList<Character>();
            ArrayList<Character> tempGuessChars = new ArrayList<Character>();
            for (char c : targetChars) {
                tempTargetChars.add(c);
            }
            for (char c : guessChars) {
                tempGuessChars.add(c);
            }
            for (int i = 0; i < 5; i++) {
                guessColors.add(i, null);
                if (tempGuessChars.get(i) != null
                        && tempGuessChars.get(i) == tempTargetChars.get(i)) {
                    guessColors.set(i, Color.GREEN);
                    tempGuessChars.set(i, null);
                    tempTargetChars.set(i, null);
                }
            }
            for (int i = 0; i < 5; i++) {
                if (tempGuessChars.get(i) != null && tempTargetChars.contains(tempGuessChars.get(i))
                        && guessColors.get(i) != Color.GREEN) {
                    guessColors.set(i, Color.YELLOW);
                    for (int j = 0; j < 5; j++) {
                        if (tempGuessChars.get(i) == tempTargetChars.get(j)) {
                            tempTargetChars.set(j, null);
                            break;
                        }
                    }
                    tempGuessChars.set(i, null);
                }
            }
            for (int i = 0; i < 5; i++) {
                if (guessColors.get(i) == null) {
                    guessColors.set(i, Color.GRAY);
                }
            }
            for (int i = 0; i < 5; i++) {
                board[numTurns - 1][i].setColor(guessColors.get(i));
                board[numTurns - 1][i].setLetter(guess.toCharArray()[i]);
                System.out.println(board[numTurns - 1][i].getColor().toString());
                System.out.println(board[numTurns - 1][i].getLetter());
            }
            if (isGameOver()) {
                done = true;
            }
        }
        guess = "";
    }

    public String getCurrentGuess() {
        return guess.toUpperCase();
    }

    public Cell[][] getBoard() {
        return board;
    }

    public boolean isGameOver() {
        if (guess.toUpperCase().equals(targetWord)) {
            gameOver = true;
            winner = true;
        } else if (numTurns > 5) {
            gameOver = true;
            winner = false;
        }
        return gameOver;
    }

    public boolean winner() {
        return winner;
    }

    public void reset() {
        makeWordList();
        numTurns = 0;
        gameOver = false;
        done = false;
        board = new Cell[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Cell();
            }
        }
        int randomIndex = (int) Math.round(Math.random() * (listOfWords.size() - 1));
        for (int i = randomIndex; i < listOfWords.size(); i++) {
            if (listOfWords.get(i).toCharArray().length == 5) {
                targetWord = listOfWords.get(i).toUpperCase();
                break;
            }
        }
    }

    public void reset(String target) {
        numTurns = 0;
        gameOver = false;
        done = false;
        board = new Cell[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Cell();
            }
        }
        targetWord = target;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("files/saveState.txt"));
            writer.write(Integer.toString(numTurns));
            writer.write("\n");
            writer.write(targetWord);
            for (int i = 0; i < numTurns; i++) {
                writer.write("\n");
                for (int j = 0; j < 5; j++) {
    //                writer.write("\n");
                    writer.write(Character.toString(board[i][j].getLetter()));
    //                writer.write("\n");
    //                writer.write(Integer.toString(board[i][j].getColor().getRGB()));
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    public void load() {
        try {
            reset();
            BufferedReader reader = new BufferedReader(new FileReader("files/saveState.txt"));
            numTurns = Integer.parseInt(reader.readLine());
    //        System.out.println(numTurns);
            targetWord = reader.readLine();
            System.out.println(targetWord);
     /*       board = new Cell[6][5];
            for (int i = 0; i < numTurns; i++) {
                for (int j = 0; j < 5; j++) {
                    board[i][j] = new Cell();
                    board[i][j].setLetter(reader.readLine().toCharArray()[0]);
                    board[i][j].setColor(Color.decode(reader.readLine()));
                }
            }
     */
            int a = numTurns;
            numTurns = 0;
            for (int i = 0; i < a; i++) {
                setGuess(reader.readLine());
                checkNewGuess();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

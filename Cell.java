
package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private char letter = '\u0000';
    private Color color = Color.BLACK;
    int x = 0;
    int y = 0;

    public Cell() {
//        super("", CENTER);
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public char getLetter() {
        return letter;
    }

    public Color getColor() {
        return color;
    }

    public void setLetter(char l) {
        letter = l;
    }

    public void setColor(Color c) {
        color = c;
        setBackground(c);
    }

    public void setX(int num) {
        x = num;
    }

    public void setY(int num) {
        y = num;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(x, y, 70, 70);
        g.setColor(getColor());
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        g.drawString(Character.toString(letter).toUpperCase(), x + 25, y + 45);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));

    }
}
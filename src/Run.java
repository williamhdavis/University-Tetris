/**
 * Created by William Davis on 15/10/2015.
 */

import javax.swing.*;
import GUI.Board;

public class Run extends JFrame
{
    /*
     * The Run constructor is used to construct the overall game window.
     */
    public Run()
    {
        super("William Davis - University Tetris");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        Board game = new Board();
        this.add(game);
        this.pack();
        game.startClock();
    }

    /*
     * The main method is used to create a standalone window of Tetris.
     */
    public static void main(String[] args)
    {
        new Run();
    }
}
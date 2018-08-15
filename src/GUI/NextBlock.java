/**
 * Created by William on 19/10/2015.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import Data.Config;
import Data.State;
import Data.Coord;
import GUI.Squares.*;
import GUI.Tetriminos.*;

public class NextBlock extends JPanel
{
    // The state variable is used to store an instance of the games state class.
    State state;
    // The grid variable is used to store a list of instances of squares.
    Square[][] grid;

    /*
     * The NextBlock constructor is used to prepare the user interface showing the next block to be spawned.
     * state - An instance of the games state.
     */
    public NextBlock(State state)
    {
        super(new BorderLayout());
        this.state = state;
        JPanel space = new JPanel(new GridLayout(6, 6));
        space.setPreferredSize(new Dimension(Config.blockWidth * 6, Config.blockWidth * 6));
        this.grid = new Square[4][4];
        int i = 0;
        while(i < 6)
        {
            int j = 0;
            while(j < 6)
            {
                if((0 < i) && (i < 5) && (0 < j) && (j < 5))
                {
                    this.grid[i - 1][j - 1] = new Square();
                    space.add(grid[i - 1][j - 1]);
                }
                else
                {
                    space.add(new Square());
                }
                ++j;
            }
            ++i;
        }
        this.add(space, BorderLayout.EAST);
    }

    /*
     *  The restart method is used to reset the next block display to blank ready for a new game.
     */
    public void restart()
    {
        int i = 0;
        while(i < this.grid.length)
        {
            int j = 0;
            while(j < this.grid[i].length)
            {
                this.grid[i][j].clear();
                ++j;
            }
            ++i;
        }
    }

    /*
     * The update method is used to update the the block shown in the next block window.
     */
    public void update()
    {
        Block block;
        Coord spawn = new Coord(1, 1);
        switch(this.state.nextBlock())
        {
            case 6: block = new Block_Z();
                    break;
            case 5: block = new Block_T();
                    spawn.adjust(0, 1);
                    break;
            case 4: block = new Block_S();
                    break;
            case 3: block = new Block_O();
                    break;
            case 2: block = new Block_L();
                    spawn.adjust(0, 1);
                    break;
            case 1: block = new Block_J();
                    spawn.adjust(0, 1);
                    break;
            default: block = new Block_I();
                    break;
        }
        this.restart();
        int i = 0;
        while(i < block.getCoords().length)
        {
            this.grid[block.getCoords()[i].getY() + spawn.getY()][block.getCoords()[i].getX() + spawn.getX()].set(block.getColor());
            ++i;
        }
    }
}

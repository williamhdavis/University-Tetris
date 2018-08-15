/**
 * Created by William Davis on 14/10/2015.
 */
package GUI.Squares;

import javax.swing.*;
import java.awt.*;
import Data.Config;

public class BorderBar extends JPanel
{
    /*
     * The BorderBar constructor is used to make a grid of border blocks to fill an area.
     * rows - The number of rows to be in the grid.
     * cols - The number of columns to be in the grid.
     * mode - The border mode for each block in the grid.
     */
    public BorderBar(int rows, int cols, int mode)
    {
        super(new GridLayout(rows, cols));
        int i = 0;
        while(i < rows * cols)
        {
            this.add(new Border(mode));
            ++i;
        }
        this.setPreferredSize(new Dimension(cols * Config.blockWidth, rows * Config.blockWidth));
    }

    /*
     * The BorderBar constructor is used to make a grid of border blocks to fill an area with the default color.
     * rows - The number of rows to be in the grid.
     * cols - The number of columns to be in the grid.
     */
    public BorderBar(int rows, int cols)
    {
        this(rows, cols, 0);
    }
}

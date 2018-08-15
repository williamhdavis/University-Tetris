/**
 * Created by William Davis on 18/10/2015.
 */
package GUI.Score;

import javax.swing.*;
import java.awt.*;
import Data.Config;

public class Character extends JComponent
{
    /*
     * The characters variable is used to store the dimensions for rectangles to make each character used in the score board.
     * Characters stored: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, E, L, V, :
     */
    private int[][][] characters = {
            {{1, 0, 3, 1}, {0, 1, 1, 5}, {4, 1, 1, 5}, {1, 6, 3, 1}, {1, 4, 1, 1}, {2, 3, 1, 1}, {3, 2, 1, 1}},
            {{1, 1, 1, 1}, {2, 0, 1, 6}, {1, 6, 3, 1}},
            {{0, 1, 1, 1}, {1, 0, 3, 1}, {4, 1, 1, 2}, {3, 3, 1, 1}, {2, 4, 1, 1}, {1, 5, 1, 1}, {0, 6, 5, 1}},
            {{0, 0, 5, 1}, {3, 1, 1, 1}, {2, 2, 1, 1}, {3, 3, 1, 1}, {4, 4, 1, 2}, {1, 6, 3, 1}, {0, 5, 1, 1}},
            {{0, 3, 1, 1}, {1, 2, 1, 1}, {2, 1, 1, 1}, {0, 4, 3, 1}, {3, 0, 1, 7}, {4, 4, 1, 1}},
            {{0, 0, 5, 1}, {0, 1, 1, 1}, {0, 2, 4, 1}, {4, 3, 1, 3}, {1, 6, 3, 1}, {0, 5, 1, 1}},
            {{2, 0, 2, 1}, {1, 1, 1, 1}, {0, 2, 1, 4}, {1, 6, 3, 1}, {4, 4, 1, 2}, {1, 3, 3, 1}},
            {{0, 0, 5, 1}, {4, 1, 1, 1}, {3, 2, 1, 1}, {2, 3, 1, 1}, {1, 4, 1, 3}},
            {{1, 0, 3, 1}, {0, 1, 1, 2}, {4, 1, 1, 2}, {1, 3, 3, 1}, {0, 4, 1, 2}, {4, 4, 1, 2}, {1, 6, 3, 1}},
            {{1, 3, 3, 1}, {0, 1, 1, 2}, {1, 0, 3, 1}, {4, 1, 1, 4}, {3, 5, 1, 1}, {1, 6, 2, 1}},
            {{0, 0, 1, 7}, {1, 0, 4, 1}, {1, 3, 3, 1}, {1, 6, 4, 1}},
            {{0, 0, 1, 7}, {1, 6, 4, 1}},
            {{0, 0, 1, 4}, {1, 4, 1, 2}, {2, 6, 1, 1}, {3, 4, 1, 2}, {4, 0, 1, 4}},
            {{0, 1, 1, 2}, {0, 4, 1, 2}}
    };

    //This stores the width of the character in pixels.
    private int width;
    //This stores the size in pixels of each "square" that makes up a character.
    private int scale;
    //This stores the current character to be displayed.
    private int value;

    /*
     * The Character constructor takes the configured width for the character and stores it. Calculating a suitable scale as well as setting its graphical size.
     */
    public Character()
    {
        this.width = Config.blockWidth;
        this.scale = (int)(this.width / 5.0) - 1;
        this.value = 0;
        this.setPreferredSize(new Dimension(this.width, this.width * 2));
    }

    /*
     * The setValue method is used to change the character to be displayed.
     * value - The value the character should display.
     */
    public void setValue(int value)
    {
        if((value >= -1) && (value <= characters.length))
        {
            this.value = value;
        }
    }

    /*
     * The paintComponent method is used to draw the character onto the games user interface.
     * Uses the current character value to pick which design set to use.
     * g - A graphics object used to draw.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, width * 2);
        if(this.value != -1)
        {
            g.setColor(Color.WHITE);
            for(int[] block:this.characters[this.value])
            {
                g.fillRect((block[0] * this.scale) + (int)(this.width / 10.0), (block[1] * this.scale) + (int)((this.width * 2) / 5.0), block[2] * this.scale, block[3] * this.scale);
            }
        }
    }
}

/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public class Block_I extends Block
{
    // The cordSet variable is used to store all the possible coordinates for the line piece.
    private static Coord[][] coordSet = new Coord[][]{{new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(-1, 0)},
                                               {new Coord(1, 0), new Coord(1, 1), new Coord(1, -1), new Coord(1, -2)},
                                               {new Coord(0, -1), new Coord(1, -1), new Coord(2, -1), new Coord(-1, -1)},
                                               {new Coord(0, 0), new Coord(0, 1), new Coord(0, -1), new Coord(0, -2)}};
    // The rotation variable is used to track the current rotation of the line piece.
    private int rotation = 0;

    /*
     * The Block_I constructor is used to prepare the line piece using the Block constructor.
     */
    public Block_I()
    {
        super(new Color(255, 0, 0), coordSet[0]);
    }

    /*
     * The testRotate method is used to retrieve the coordinates of the line piece if rotated by 90 degrees clockwise.
     */
    @Override
    public Coord[] testRotate()
    {
        int rot = this.rotation;
        if(rot == 0)
        {
            rot = 3;
        }
        else
        {
            rot -= 1;
        }
        return coordSet[rot];
    }

    /*
     * The rotate method is used to apply the test rotation over the stored coordinates and update the current rotation variable.
     */
    @Override
    public void rotate()
    {
        this.coords = testRotate();
        if(this.rotation == 0)
        {
            this.rotation = 3;
        }
        else
        {
            this.rotation -= 1;
        }
    }
}

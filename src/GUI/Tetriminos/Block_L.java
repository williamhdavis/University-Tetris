/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public class Block_L extends Block
{
    /*
     * The Block_L constructor is used to prepare the L piece using the Block constructor.
     */
    public Block_L()
    {
        super(new Color(255, 0, 255), new Coord[]{new Coord(0, 0), new Coord(1, 0), new Coord(-1, 0), new Coord(1, -1)}, new Coord(0, 1));
    }
}

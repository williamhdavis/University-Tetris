/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public class Block_J extends Block
{
    /*
     * The Block_J constructor is used to prepare the J piece using the Block constructor.
     */
    public Block_J()
    {
        super(new Color(255, 230, 0), new Coord[]{new Coord(0, 0), new Coord(1, 0), new Coord(-1, 0), new Coord(-1, -1)}, new Coord(0, 1));
    }
}

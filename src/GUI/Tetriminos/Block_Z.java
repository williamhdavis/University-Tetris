/**
 * Created by William Davis on 07/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public class Block_Z extends Block
{
    /*
     * The Block_Z constructor is used to prepare the Z piece using the Block constructor.
     */
    public Block_Z()
    {
        super(new Color(255, 127, 0), new Coord[]{new Coord(0, 0), new Coord(-1, 0), new Coord(0, 1), new Coord(1, 1)});
    }
}

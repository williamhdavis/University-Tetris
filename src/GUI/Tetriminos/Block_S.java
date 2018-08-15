/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public class Block_S extends Block
{
    /*
     * The Block_S constructor is used to prepare the S piece using the Block constructor.
     */
    public Block_S()
    {
        super(new Color(0, 230, 255), new Coord[]{new Coord(0, 0), new Coord(1, 0), new Coord(0, 1), new Coord(-1, 1)});
    }
}

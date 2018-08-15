/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public class Block_T extends Block
{
    /*
     * The Block_T constructor is used to prepare the T piece using the Block constructor.
     */
    public Block_T()
    {
        super(new Color(127, 230, 0), new Coord[]{new Coord(0, 0), new Coord(-1, 0), new Coord(1, 0), new Coord(0, -1)}, new Coord(0, 1));
    }
}

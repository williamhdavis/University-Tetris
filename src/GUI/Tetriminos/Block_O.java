/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public class Block_O extends Block
{
    /*
     * The Block_O constructor is used to prepare the square piece using the Block constructor.
     */
    public Block_O()
    {
        super(new Color(0, 0, 255), new Coord[]{new Coord(0, 0), new Coord(1, 0), new Coord(0, 1), new Coord(1, 1)});
    }

    /*
     * The testRotate method is overrided so that the square cannot be rotated. Returning the current coordinates.
     */
    @Override
    public Coord[] testRotate()
    {
        return this.coords;
    }

    /*
     * The rotate method is overrided so that the square cannot be rotated.
     */
    @Override
    public void rotate()
    {}
}

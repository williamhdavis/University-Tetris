/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Tetriminos;

import java.awt.*;
import Data.Coord;

public abstract class Block
{
    // The coords variable is used to store the set of coordinates that make up a full block.
    protected Coord[] coords;
    // The origin variable is used to store the current location of the origin of the block relative to the spawn point.
    protected Coord origin;
    // The color variable is used to store the color of the shape.
    protected Color color;

    /*
     * The Block constructor is used to prepare the block by storing the configuration of the shape.
     * color - The color the shape should be.
     * coords - The set of coordinates that make the block.
     * origin - The location of the blocks origin relative to the spawn point.
     */
    public Block(Color color, Coord[] coords, Coord origin)
    {
        this.color = color;
        this.coords = coords;
        this.origin = origin;
    }

    /*
     * The Block constructor is used to prepare the block by storing the configuration of the shape but providing a default value for the starting origin.
     * color - The color the shape should be.
     * coords - The set of coordinated that make the block.
     */
    public Block(Color color, Coord[] coords)
    {
        this.color = color;
        this.coords = coords;
        this.origin = new Coord(0, 0);
    }

    /*
     * The getOrigin method is used to access the stored origin of the block relative to the spawn point.
     */
    public Coord getOrigin()
    {
        return this.origin;
    }

    /*
     * The getCoords method is used to access the stored coordinated that make up the block.
     */
    public Coord[] getCoords()
    {
        return this.coords;
    }

    /*
     * The testRotate method is used to retrieve the coordinates of the current shape if rotated by 90 degrees clockwise.
     */
    public Coord[] testRotate()
    {
        Coord[] hold = new Coord[this.coords.length];
        int i = 0;
        while(i < this.coords.length)
        {
            int x = this.coords[i].getX();
            int y = this.coords[i].getY();
            hold[i] = new Coord(y * -1, x);
            ++i;
        }
        return hold;
    }

    /*
     * The rotate method is used to apply the test rotation over the stored coordinates.
     */
    public void rotate()
    {
        this.coords = this.testRotate();
    }

    /*
     * The getColor method is used to access the blocks stored color.
     */
    public Color getColor()
    {
        return this.color;
    }
}

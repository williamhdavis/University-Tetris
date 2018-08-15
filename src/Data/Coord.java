/**
 * Created by William Davis on 06/10/2015.
 */
package Data;

public class Coord
{
    // Used to store the x axis of a coordinate.
    private int x;
    // Used to store the y axis of a coordinate.
    private int y;

    /*
     * The Coord constructor takes two integers and stores them as the x and y axis' of a coordinate.
     * x - The x axis to be stored.
     * y - The y axis to be stored.
     */
    public Coord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /*
     * The getX method is used to return the stored value for the x axis.
     */
    public int getX()
    {
        return this.x;
    }

    /*
     * The getY method is used to return the stored value for the y axis.
     */
    public int getY()
    {
        return this.y;
    }

    /*
     * The adjust method is used to change both the stored axis values.
     * changeX - The amount to change the x axis by.
     * changeY - The amount to change the y axis by.
     */
    public void adjust(int changeX, int changeY)
    {
        this.adjustX(changeX);
        this.adjustY(changeY);
    }

    /*
     * The adjustX method is used to change the stored x axis value.
     * change - The amount to change the x axis by.
     */
    public void adjustX(int change)
    {
        this.x += change;
    }

    /*
     * The adjustY method is used to change the stored y axis value.
     * change - The amount to change the y axis by.
     */
    public void adjustY(int change)
    {
        this.y += change;
    }
}

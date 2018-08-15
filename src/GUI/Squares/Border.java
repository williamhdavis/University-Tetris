/**
 * Created by William Davis on 08/10/2015.
 */
package GUI.Squares;

import java.awt.*;

public class Border extends Square
{
    /*
     * The Border constructor is used to create a square and give it one of two colors that are used for the borders.
     * mode - The color mode desired for this border square.
     */
    public Border(int mode)
    {
        super();
        if(mode == 1)
        {
            this.set(new Color(200, 200, 200));
        }
        else
        {
            this.set(Color.GRAY);
        }
    }
}

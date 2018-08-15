/**
 * Created by William Davis on 06/10/2015.
 */
package GUI.Squares;

import javax.swing.*;
import java.awt.*;
import Data.Config;

public class Square extends JComponent
{
    // The w variable is used to store the squares width in pixels.
    private int w;
    // The h variable is used to store the height of the detailing on the square in pixels.
    private int h;
    // The color variable is used to store the blocks current color.
    private Color color;
    // The state variable is used to track the current mode of the block.
    private int state;

    /*
     * The Square constructor is used  to set the default state of the square. Starting it as a blank area.
     */
    public Square()
    {
        this.w = Config.blockWidth;
        this.h = (int)(this.w * 0.2);
        this.state = 0;
        this.color = Color.BLACK;
        this.setMinimumSize(new Dimension(w, w));
    }

    /*
     * The set method is used to give the square a new color.
     * color - The color to change the square to.
     */
    public void set(Color color)
    {
        this.color = color;
        this.state = -1;
    }

    /*
     * The setState method is used to change the active state of the square.
     * state - The new state for the square to be in.
     */
    public void setState(int state)
    {
        this.state = state;
        if(this.state > -1)
        {
            this.color = Color.BLACK;
        }
    }

    /*
     * The clear method is used to return the square to its default blank state. Returning its old color.
     */
    public Color clear()
    {
        Color clr = this.color;
        this.color = Color.BLACK;
        this.state = 0;
        return clr;
    }

    /*
     * The isEnabled method is used to check if the squares state is that of an occupied square.
     */
    public boolean isEnabled()
    {
        return (this.state == -1) || (this.state == -3);
    }

    /*
     * The paintComponent method is used to draw the square and the current states detail onto the user interface.
     * g - A graphics object used to draw.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(this.state <= -2)
        {
            // Game over state.
            g.setColor(new Color(200, 200, 200));
        }
        else
        {
            // "Normal" state.
            g.setColor(color);
        }
        g.fillRect(0, 0, w, w);
        if(this.state < 0)
        {
            // Add block detail.
            g.setColor(new Color(255, 255, 255, 120));
            g.fillPolygon(new int[]{0, w, w - h, h}, new int[]{0, 0, h, h}, 4);
            g.setColor(new Color(255, 255, 255, 80));
            g.fillPolygon(new int[]{0, 0, h, h}, new int[]{0, w, w - h, h}, 4);
            g.setColor(new Color(0, 0, 0, 120));
            g.fillPolygon(new int[]{0, w, w - h, h}, new int[]{w, w, w - h, w - h}, 4);
            g.setColor(new Color(0, 0, 0, 80));
            g.fillPolygon(new int[]{w, w, w - h, w - h}, new int[]{0, w, w - h, h}, 4);
        }
        else if(this.state == 1)
        {
            // Destroy animation, frame 1.
            g.setColor(new Color(255, 255, 255, 200));
            g.fillRect(h, h, w - (h * 2), w - (h * 2));
        }
        else if(this.state == 2)
        {
            // Destroy animation, frame 2.
            g.setColor(new Color(255, 255, 255, 200));
            g.fillRect(h * 2, h * 2, w - (h * 4), w - (h * 4));
        }
    }
}

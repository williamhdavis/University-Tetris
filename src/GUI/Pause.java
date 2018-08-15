/**
 * Created by William Davis on 20/10/2015.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Data.Config;
import Data.State;

public class Pause extends JComponent implements MouseListener
{
    // The state variable is used to store an instance of the games state class.
    State state;
    // The w variable is used to store the width of the pause button.
    int w;

    /*
     * The Pause constructor is used to prepare the pause button for adding to the interface.
     * state - An instance of the games state.
     */
    public Pause(State state)
    {
        this.state = state;
        this.w = Config.blockWidth * 2;
        this.setPreferredSize(new Dimension(this.w, this.w));
        this.addMouseListener(this);
    }

    /*
     * The paintComponent method is used to draw the pause button in its current state onto the user interface.
     * g - A graphics object used to draw.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.w, this.w);
        g.setColor(Color.WHITE);
        if(this.state.keys[4])
        {
            g.fillPolygon(new int[]{w / 5, w - (w / 5), w / 5}, new int[]{w / 5, w / 2, w - (w / 5)}, 3);
        }
        else
        {
            g.fillRect(w / 5, w / 5, w / 5, w - (w / 5 * 2));
            g.fillRect(w - (w / 5 * 2), w / 5, w / 5, w - (w / 5 * 2));
        }
    }

    /*
     * The mouseClicked method is used to process mouse clicks on the pause button. Pausing or unpausing the game.
     * e - The mouse click event.
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(!this.state.isGameOver())
        {
            this.state.keys[4] = !this.state.keys[4];
        }
    }

    /*
     * The mousePressed method is unused but is required to implement the MouseListener. It would be used to process mouse button down events.
     * e - The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e)
    {}

    /*
     * The mouseReleased method is unused but is required to implement the MouseListener. It would be used to process mouse button up events.
     * e - The mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {}

    /*
     * The mouseEntered method is unused but is required to implement the MouseListener. It would be used to process the mouse entering the window.
     * e - The mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e)
    {}

    /*
     * The mouseExited method is unused but is required to implement the MouseListener. It would be used to process the mouse leaving the window.
     * e - The mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e)
    {}
}

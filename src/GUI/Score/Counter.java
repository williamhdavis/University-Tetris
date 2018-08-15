/**
 * Created by William Davis on 18/10/2015.
 */
package GUI.Score;

import javax.swing.*;
import java.awt.*;
import Data.Config;
import Data.Score;

public class Counter extends JPanel
{
    //The score variable is used to store an instance of the current games score data.
    protected Score score;
    //The digits variable is used to store a set of characters to be used to display the current score.
    protected Character[] digits;

    /*
     * The Counter constructor is used to set up the counter. Creating it with the configured display size.
     * score - An instance of the score class that provides access to the games current score.
     */
    public Counter(Score score)
    {
        super(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.score = score;
        this.setPreferredSize(new Dimension(Config.blockWidth * (Config.scoreZones + 2), Config.blockWidth * 2));
        JPanel holder = new JPanel(new GridLayout(1, Config.scoreZones));
        holder.setPreferredSize(new Dimension(Config.blockWidth * Config.scoreZones, Config.blockWidth * 2));
        //Create and initialise a set of characters.
        this.digits = new Character[Config.scoreZones];
        int i = 0;
        while(i < this.digits.length)
        {
            this.digits[i] = new Character();
            holder.add(this.digits[i]);
            ++i;
        }
        this.add(holder);
    }

    /*
     * The paintComponent method is used to draw an updated score to the user interface.
     * g - A graphics object used to draw.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Config.blockWidth * (Config.scoreZones + 2), Config.blockWidth * 2);
        int score = this.score.getScore();
        int i = Config.scoreZones;
        while(i > 0)
        {
            this.digits[i - 1].setValue(score % 10);
            score /= 10;
            --i;
        }
    }
}

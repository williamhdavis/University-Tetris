/**
 * Created by William on 19/10/2015.
 */
package GUI.Score;

import java.awt.*;
import Data.Config;
import Data.Score;

public class Levels extends Counter
{
    //The marker variable is used to track where the level text ends and where the number starts.
    private int marker;

    /*
     * The Levels constructor is used to set the level text and record where it ends.
     * score - An instance of the score class that provides access to the games current score.
     */
    public Levels(Score score)
    {
        super(score);
        if(this.digits.length >= 8)
        {
            //Text is set to LEVEL:
            this.digits[0].setValue(11);
            this.digits[1].setValue(10);
            this.digits[2].setValue(12);
            this.digits[3].setValue(10);
            this.digits[4].setValue(11);
            this.digits[5].setValue(13);
            this.marker = 6;
        }
        else if(this.digits.length >= 5)
        {
            //Text is set to LV:
            this.digits[0].setValue(11);
            this.digits[1].setValue(12);
            this.digits[2].setValue(13);
            this.marker = 3;
        }
        else
        {
            this.marker = 0;
        }
    }

    /*
     * The paintComponent method is used to draw an updated level to the user interface.
     * g - A graphics object used to draw.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Config.blockWidth * (Config.scoreZones + 2), Config.blockWidth * 2);
        int level = this.score.getLevel();
        int i = Config.scoreZones - 1;
        while(i >= this.marker)
        {
            if(level == 0)
            {
                this.digits[i].setValue(-1);
            }
            else
            {
                this.digits[i].setValue(level % 10);
            }
            level /= 10;
            --i;
        }
    }
}

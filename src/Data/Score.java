/**
 * Created by William Davis on 18/10/2015.
 */
package Data;

public class Score
{
    // The state variable is used to store an instance of the current state of the game.
    private State state;
    // The score variable is used to store the players current overall score for the current game.
    private int score;
    // The lines variable is used to store the number of lines that have been destroyed in the current game.
    private int lines;

    /*
     * The Score constructor is used to prepare the score class to track the players score.
     */
    public Score(State state)
    {
        this.state = state;
        this.score = 0;
        this.lines = 0;
    }

    /*
     * The restart method is used to set the current score and line counts back to zero for  new game.
     */
    public void restart()
    {
        this.score = 0;
        this.lines = 0;
    }

    /*
     * The getScore method is used to retrieve the current score.
     */
    public int getScore()
    {
        return this.score;
    }

    /*
     * The getLevel method is used to get the current level of the game.
     */
    public int getLevel()
    {
        return (this.lines / 5) + 1;
    }

    /*
     * The lines method is used to score line breaks. Scoring differently depending on the score mode.
     * lines - The number of lines broken.
     */
    public void lines(int lines)
    {
        if(this.state.isComplexScore())
        {
            int multi;
            if(lines == 4)
            {
                multi = 800;
            }
            else if(lines == 3)
            {
                multi = 500;
            }
            else if(lines == 2)
            {
                multi = 300;
            }
            else
            {
                multi = 100;
            }
            this.score += multi * this.getLevel();
            this.lines += lines;
        }
        else
        {
            this.score += lines * 10;
        }
    }

    /*
     * The softDrop method is used to score points at the end of a soft drop.
     * blocks - The number of rows that were soft dropped through.
     */
    public void softDrop(int blocks)
    {
        if(this.state.isComplexScore())
        {
            this.score += blocks;
        }
    }

    /*
     * The hardDrop method is used to score points at the end of a hard drop.
     * blocks - The number of rows that were hard dropped though.
     */
    public void hardDrop(int blocks)
    {
        if(this.state.isComplexScore())
        {
            this.score += blocks * 2;
        }
    }
}

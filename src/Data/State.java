/**
 * Created by William Davis on 20/11/2015.
 */
package Data;

import java.util.Random;

public class State
{
    /*
     * The updateTimes variable is used to track various updates through the game. Ensuring that events happen at suitable intervals by setting the earliest time they should next happen.
     * 0) The next update time for the overall user interface.
     * 1) The next time the block should be moved left.
     * 2) The next time the block should be moved right.
     * 3) The next time the block should be rotated.
     * 4) The next time the block should be dropped.
     * 5) The next time a new block should be spawned.
     * 6) The next time to change the state of lines.
     * 7) The next time a part of the game over animation should happen.
     */
    public long[] updateTimes = {0, -1, -1, -1, -1, 0, -1, -1};
    /*
     * The keys variable is used to track what user inputs are being received. Using true to mean the user is inputting.
     * 0) The user wants to move the block left.
     * 1) The user wants to move the block right.
     * 2) The user wants to rotate the block.
     * 3) The user wants to soft drop the block.
     * 4) The user has paused the game.
     */
    public boolean[] keys = {false, false, false, false, false};
    // The lineMode variable is used to track the current state of lines being destroyed.
    public int lineMode = 0;
    //The score variable is used to store an instance of the score class.
    public Score score;

    // The block variable is used to store the next block type.
    private int block;
    // The gameOver variable is used to store whether the game has been lost.
    private boolean gameOver = false;
    // The blockState variable is used to store whether the current block is locked in place or not.
    private boolean blockState = false;
    // The advancedScoring variable is used to store whether the game should use simple or complex scoring.
    private boolean advancedScoring = false;

    /*
     * The State constructor is used to generate the first block and create an instance to track the games score.
     */
    public State()
    {
        newBlock();
        this.score = new Score(this);
    }

    /*
     * The newBlock method is used to generate a new random block to be the next block.
     */
    public void newBlock()
    {
        Random rand = new Random();
        this.block = rand.nextInt(7);
    }

    /*
     * The nextBlock method is used to get the chosen next block.
     */
    public int nextBlock()
    {
        return this.block;
    }

    /*
     * The isGameOver method is used to check if the game has ended.
     */
    public boolean isGameOver()
    {
        return this.gameOver;
    }

    /*
     * The startGame method is used to reset crucial game values so that a new game can begin.
     */
    public void startGame()
    {
        this.updateTimes[7] = -1;
        this.score.restart();
        this.newBlock();
        this.lockBlock();
        this.gameOver = false;
    }

    /*
     * The endGame method is used to end the game and trigger the end game animation.
     */
    public void endGame()
    {
        this.updateTimes[7] = 0;
        this.gameOver = true;
    }

    /*
     * The isBlockLocked method is used to check if the current block has been locked into the game grid.
     */
    public boolean isBlockLocked()
    {
        return this.blockState;
    }

    /*
     * The lockBlock method is used to lock the current block to the game grid.
     */
    public void lockBlock()
    {
        this.blockState = true;
    }

    /*
     * The unlockBlock method is used to release the current block from the game grid.
     */
    public void unlockBlock()
    {
        this.blockState = false;
    }

    /*
     * The isComplexScore method is used to check if the game is in simple or complex scoring mode.
     */
    public boolean isComplexScore()
    {
        return this.advancedScoring;
    }

    /*
     * The toggleScoring method is used to switch the scoring mode to the mode that it is not.
     */
    public void toggleScoring()
    {
        this.advancedScoring = !this.advancedScoring;
    }

    /*
     * The isManualDrop method is used to check if the manual drop rate is faster than the current automatic drop rate.
     */
    public boolean isManualDrop()
    {
        long manual = (long)(Config.dropDelay[2] * 1000000000);
        long automatic = (long)((Config.dropDelay[0] - (Config.dropDelay[1] * this.score.getLevel())) * 1000000000);
        return (automatic > manual);
    }

    /*
     * The getDropRate method is used to retrieve the active drop speed between the manual and automatic drop rate.
     */
    public long getDropRate()
    {
        long manual = (long)(Config.dropDelay[2] * 1000000000);
        long automatic = (long)((Config.dropDelay[0] - (Config.dropDelay[1] * this.score.getLevel())) * 1000000000);
        if((this.keys[3]) && (automatic > manual))
        {
            return manual;
        }
        else
        {
            return automatic;
        }
    }
}

/**
 * Created by William Davis on 15/10/2015.
 */
package Data;

public class Config
{
    // The gameRows variable is used to set the number of rows in the play space.
    public static final int gameRows = 20;
    // The gameColumns variable is used to set the number of columns in the play space.
    public static final int gameColumns = 10;
    // The borderThickness variable is used to set the thickness of the blocks around the user interface components.
    public static final int borderThickness = 1;
    // The scoreZones variable is used to set the number of characters in the score and level components.
    public static final int scoreZones = 8;
    // The blockWidth variable is used to set the number of pixels to be used for each individual square in the user interface.
    public static final int blockWidth = 25;
    // The updatesPerSecond is used to set the overall frame rate in seconds of the user interface.
    public static final double updatesPerSecond = 0.01;
    /*
     * The dropDelay variable is used to set the drop rate for blocks. Each is in seconds.
     * 0) The base automatic drop rate.
     * 1) The per level increase in automatic drop rate for complex score mode.
     * 2) The manual drop rate for soft drops.
     */
    public static final double[] dropDelay = {0.8, 0.03, 0.03};
    /*
     * The moveDelay variable is used to set the sideways and rotational movement speed. Each in seconds.
     * 0) The delay after the initial sideways move to allow easy one block movements.
     * 1) The delay for sideways movement after the initial move.
     * 2) The delay for the rotation of the piece.
     */
    public static final double[] moveDelay = {0.1, 0.06, 0.15};
    // The clearDelay variable is used to set the spawn delay for a new block being spawned after the last was locked in.
    public static final double clearDelay = 0.5;
    /*
     * The animateDelay variable is used to set animation delays for the game.
     * 0) The delay for block destruction when a line is destroyed.
     * 1) The delay for the end game block change where active squares "turn to stone."
     */
    public static final double[] animateDelay = {0.1, 0.04};
    // The maxFrameSkip variable is used to set the number of frames that can be skipped before a user interface reload is required.
    public static final int maxFrameSkip = 5;
    // The spawn variable is used to set the initial default spawn location for blocks on the grid.
    public static final Coord spawn = new Coord(4, 0);
}

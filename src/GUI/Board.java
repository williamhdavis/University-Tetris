/**
 * Created by William on 15/10/2015.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Data.Config;
import Data.State;
import GUI.Squares.BorderBar;
import GUI.Score.Counter;
import GUI.Score.Levels;

public class Board extends JPanel implements KeyListener, MouseListener
{
    // The state variable is used to store an instance of the games state class.
    private State state;
    // The gameZone variable is used to store an instance of the game grid.
    private Grid gameZone;
    // The nextZone variable is used to store an instance of the next block display.
    private NextBlock nextZone;
    // The running variable is used to store whether the game is running.
    private boolean running = false;
    // The softDrop variable is used to store the number of blocks soft dropped at once.
    private int softDrop = 0;
    // The holderLower variable is used to store a instance to a JFrame so the level display can be changed.
    JPanel holderLower;
    // The levelsComp variable is used to store an instance of the current levels display.
    JComponent levelsComp;

    /*
     * The Board constructor is used to prepare and layout the Tetris game. Adding bindings to the window.
     */
    public Board()
    {
        super(new BorderLayout());
        this.state = new State();
        this.setPreferredSize(new Dimension(((Config.gameColumns + Config.borderThickness * 3 + Config.scoreZones + 2) * Config.blockWidth), (Config.gameRows + Config.borderThickness * 2) * Config.blockWidth));
        // Build the play area.
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(new BorderBar(Config.borderThickness, Config.gameColumns), BorderLayout.NORTH);
        wrapper.add(new BorderBar(Config.borderThickness, Config.gameColumns), BorderLayout.SOUTH);
        this.gameZone = new Grid(this.state);
        wrapper.add(this.gameZone, BorderLayout.CENTER);
        JPanel gameBoard = new JPanel(new BorderLayout());
        gameBoard.setPreferredSize(new Dimension((Config.gameColumns + Config.borderThickness * 2) * Config.blockWidth, (Config.gameRows + Config.borderThickness * 2) * Config.blockWidth));
        gameBoard.add(new BorderBar((Config.gameRows + Config.borderThickness * 2), Config.borderThickness), BorderLayout.WEST);
        gameBoard.add(new BorderBar((Config.gameRows + Config.borderThickness * 2), Config.borderThickness), BorderLayout.EAST);
        gameBoard.add(wrapper, BorderLayout.CENTER);
        // Build the pause area.
        JPanel pauseBox = new JPanel(new BorderLayout());
        pauseBox.add(new Pause(this.state), BorderLayout.NORTH);
        pauseBox.add(new BorderBar(4, 2), BorderLayout.SOUTH);
        // Build the next block area.
        JPanel nextBlockBox = new JPanel(new BorderLayout());
        this.nextZone = new NextBlock(this.state);
        nextBlockBox.add(this.nextZone, BorderLayout.WEST);
        nextBlockBox.add(new BorderBar(6, Config.scoreZones - 6), BorderLayout.CENTER);
        nextBlockBox.add(pauseBox, BorderLayout.EAST);
        // Wrap the pause area and next block area.
        JPanel holderUpper = new JPanel(new BorderLayout());
        holderUpper.add(new BorderBar(Config.borderThickness, Config.scoreZones + 2), BorderLayout.NORTH);
        holderUpper.add(nextBlockBox, BorderLayout.CENTER);
        holderUpper.add(new BorderBar(1, Config.scoreZones + 2), BorderLayout.SOUTH);
        // Build and wrap the levels area.
        this.holderLower = new JPanel(new BorderLayout());
        this.holderLower.add(new BorderBar(1, Config.scoreZones + 2), BorderLayout.NORTH);
        this.addLevel();
        this.holderLower.add(new BorderBar((Config.gameRows - 12) + Config.borderThickness, Config.scoreZones + 2), BorderLayout.SOUTH);
        // Wrap the pause area, next block area and the levels area again, creating and adding the score area.
        JPanel holder = new JPanel(new BorderLayout());
        holder.add(holderUpper, BorderLayout.NORTH);
        holder.add(new Counter(this.state.score), BorderLayout.CENTER);
        holder.add(this.holderLower, BorderLayout.SOUTH);
        // Create the overall side bar.
        JPanel sideBar = new JPanel(new BorderLayout());
        sideBar.add(new BorderBar(Config.gameRows + (2 * Config.borderThickness), Config.borderThickness), BorderLayout.EAST);
        sideBar.add(holder, BorderLayout.WEST);
        // Add the play area and the side bar to the window.
        this.add(gameBoard, BorderLayout.WEST);
        this.add(sideBar, BorderLayout.EAST);
        // Prepare the window for keyboard and mouse input.
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
    }

    /*
     * The addLevel method is used to add either a level display or a cover to the game window based on the current score mode.
     */
    private void addLevel()
    {
        if(this.state.isComplexScore())
        {
            this.levelsComp = new Levels(this.state.score);
        }
        else
        {
            this.levelsComp = new BorderBar(2, Config.scoreZones + 2, 1);
        }
        this.holderLower.add(this.levelsComp, BorderLayout.CENTER);
    }

    /*
     * The changeLevel method is used to remove the current item in the level space of the user interface and add a new item in the same space.
     */
    private void changeLevel()
    {
        this.holderLower.remove(this.levelsComp);
        this.addLevel();
    }

    /*
     * The reload method is used to repaint the window.
     */
    private void reload()
    {
        this.revalidate();
        this.repaint();
    }

    /*
     * The startClock method is used to activate the game. Running a constant timer loop until the game ends.
     */
    public void startClock()
    {
        // Prepare for the clock to start.
        this.requestFocus();
        this.state.startGame();
        this.running = true;
        this.state.updateTimes[0] = System.nanoTime();
        int skips = 0;
        while(this.running)
        {
            // Clock active.
            long time = System.nanoTime();
            if(time >= this.state.updateTimes[0])
            {
                this.state.updateTimes[0] = time + (long)(Config.updatesPerSecond * 1000000000);
                if(this.state.keys[4] != this.gameZone.isPaused())
                {
                    this.gameZone.pause();
                }
                if(!this.state.keys[4])
                {
                    this.update(time);
                }
                if((skips > Config.maxFrameSkip) || (time < this.state.updateTimes[0]))
                {
                    this.reload();
                    skips = 0;
                }
                else
                {
                    ++skips;
                }
            }
            else
            {
                int sleep = (int)((this.state.updateTimes[0] - time) / 1000000);
                if(sleep > 0)
                {
                    try
                    {
                        Thread.sleep(sleep);
                    }
                    catch(InterruptedException e)
                    {}
                }
            }
        }
    }

    /*
     * The restart method is used to set the game back to the default start configuration.
     */
    public void restart()
    {
        this.gameZone.restart();
        this.nextZone.restart();
        this.state.startGame();
    }

    /*
     * The update method checks for user inputs and manages all block movements, spawning, line detection and detecting the end of the game.
     * now - The current time in nano seconds.
     */
    private void update(long now)
    {
        if(!this.state.isGameOver())
        {
            // Block to be moved left.
            if(this.state.keys[0])
            {
                if(this.state.updateTimes[1] == -1)
                {
                    this.gameZone.blockLeft();
                    this.state.updateTimes[1] = now + (long)(Config.moveDelay[0] * 1000000000);
                }
                else if(this.state.updateTimes[1] <= now)
                {
                    this.gameZone.blockLeft();
                    this.state.updateTimes[1] = now + (long)(Config.moveDelay[1] * 1000000000);
                }
            }
            // Block to be moved right.
            if(this.state.keys[1])
            {
                if(this.state.updateTimes[2] == -1)
                {
                    this.gameZone.blockRight();
                    this.state.updateTimes[2] = now + (long)(Config.moveDelay[0] * 1000000000);
                }
                else if(this.state.updateTimes[2] <= now)
                {
                    this.gameZone.blockRight();
                    this.state.updateTimes[2] = now + (long)(Config.moveDelay[1] * 1000000000);
                }
            }
            // Block to be rotated.
            if(this.state.keys[2])
            {
                if(this.state.updateTimes[3] <= now)
                {
                    this.gameZone.rotate();
                    this.state.updateTimes[3] = now + (long)(Config.moveDelay[2] * 1000000000);
                }
            }
            // Block to be dropped.
            if((now + this.state.getDropRate() <= this.state.updateTimes[4]) || (now >= this.state.updateTimes[4]))
            {
                this.gameZone.blockDrop();
                this.state.updateTimes[4] = now + this.state.getDropRate();
                if((this.state.keys[3]) && (!this.state.isBlockLocked()) && (this.state.isManualDrop()))
                {
                    this.softDrop += 1;
                }
            }
            // Block to be spawned.
            if((this.state.updateTimes[5] == -1) && (this.state.isBlockLocked()))
            {
                this.state.updateTimes[5] = now + (long)(Config.clearDelay * 1000000000);
            }
            else if((this.state.updateTimes[5] != -1) && (this.state.updateTimes[5] <= now))
            {
                this.gameZone.Spawn();
                this.nextZone.update();
                this.state.updateTimes[5] = -1;
                this.state.updateTimes[4] = now + this.state.getDropRate();
                this.state.unlockBlock();
            }
            // Lines to be cleared.
            if((this.state.lineMode == 0) && (this.gameZone.isLineWaiting()))
            {
                this.state.lineMode = 1;
                this.state.updateTimes[6] =  now;;
            }
            else if((this.state.updateTimes[6] != -1) && (this.state.updateTimes[6] <= now))
            {
                this.gameZone.cycleLines();
                if(this.state.lineMode == 3)
                {
                    this.state.lineMode = 0;
                    this.state.updateTimes[6] =  -1;
                }
                else
                {
                    this.state.lineMode += 1;
                    this.state.updateTimes[6] =  now + (long)(Config.animateDelay[0] * 1000000000);
                }
            }
            // Soft drop cheat protection.
            if((this.state.isBlockLocked()) && (this.state.keys[3]) && (this.softDrop != 0))
            {
                scoreSoftDrop();
            }
        }
        else
        {
            // Run the game over animation.
            if((this.state.updateTimes[7] <= now) && (this.state.updateTimes[7] != -1))
            {
                this.gameZone.end();
                this.state.updateTimes[7] = now + (long)(Config.animateDelay[1] * 1000000000);
            }
        }
    }

    /*
     * The scoreSoftDrop method is used to add points for soft dropping to the score.
     */
    private void scoreSoftDrop()
    {
        this.state.score.softDrop(this.softDrop);
        this.softDrop = 0;
    }

    /*
     * The keyTyped method is unused but is required to implement the KeyListener. It would be used to process typing events for keys.
     * key - The key typed.
     */
    @Override
    public void keyTyped(KeyEvent key)
    {}

    /*
     * The keyPressed method is used to detect key down events and process them.
     * key - The key that has been pressed.
     */
    @Override
    public void keyPressed(KeyEvent key)
    {
        // "R" key pressed.
        if((key.getKeyCode() == 82) && (this.state.isGameOver()))
        {
            this.restart();
        }
        // "S" key pressed.
        if((key.getKeyCode() == 83) && (this.state.isGameOver()))
        {
            this.state.toggleScoring();
            this.changeLevel();
        }
        // "P" key pressed.
        if((key.getKeyCode() == 80) && (!this.state.isGameOver()))
        {
            this.state.keys[4] = !this.state.keys[4];
        }
        // "Space" key pressed.
        if(key.getKeyCode() == 32)
        {
            int i = 0;
            while(!this.state.isBlockLocked())
            {
                this.gameZone.blockDrop();
                ++i;
            }
            this.state.score.hardDrop(i);
        }
        // "Left" key pressed.
        if((key.getKeyCode() == 37) && (!this.state.keys[0]) && (!this.state.keys[1]))
        {
            this.state.keys[0] = true;
            this.state.updateTimes[1] = -1;
        }
        // "Right" key pressed.
        if((key.getKeyCode() == 39) && (!this.state.keys[1]) && (!this.state.keys[0]))
        {
            this.state.keys[1] = true;
            this.state.updateTimes[2] = -1;
        }
        // "Up" key pressed.
        if((key.getKeyCode() == 38) && (!this.state.keys[2]))
        {
            this.state.keys[2] = true;
        }
        // "Down" key pressed.
        if((key.getKeyCode() == 40) && (!this.state.keys[3]))
        {
            this.state.keys[3] = true;
        }
    }

    /*
     * The keyReleased method is used to detect key up events and process them.
     * key - The key that has been released.
     */
    @Override
    public void keyReleased(KeyEvent key)
    {
        // "Left" key released.
        if((key.getKeyCode() == 37) && (this.state.keys[0]))
        {
            this.state.keys[0] = false;
            this.state.updateTimes[1] = -1;
        }
        // "Right" key released.
        if((key.getKeyCode() == 39) && (this.state.keys[1]))
        {
            this.state.keys[1] = false;
            this.state.updateTimes[2] = -1;
        }
        // "Up" key released.
        if((key.getKeyCode() == 38) && (this.state.keys[2]))
        {
            this.state.keys[2] = false;
        }
        // "Down" key released.
        if((key.getKeyCode() == 40) && (this.state.keys[3]))
        {
            this.state.keys[3] = false;
            if(this.softDrop != 0)
            {
                this.scoreSoftDrop();
            }
        }
    }

    /*
     * The mouseClicked method is unused but is required to implement the MouseListener. It would be used to process mouse clicks.
     * button - The mouse button clicked.
     */
    @Override
    public void mouseClicked(MouseEvent button)
    {}

    /*
     *  The mousePressed method is used to detect mouse button down events and process them.
     *  button - The mouse button that has been pressed.
     */
    @Override
    public void mousePressed(MouseEvent button)
    {
        // Left mouse button pressed.
        if((button.getButton() == 1) && (!this.state.keys[0]) && (!this.state.keys[1]))
        {
            this.state.keys[0] = true;
            this.state.updateTimes[1] = -1;
        }
        // Right mouse button pressed.
        if((button.getButton() == 3) && (!this.state.keys[1]) && (!this.state.keys[0]))
        {
            this.state.keys[1] = true;
            this.state.updateTimes[2] = -1;
        }
        // Middle mouse button pressed.
        if((button.getButton() == 2) && (!this.state.keys[2]))
        {
            this.state.keys[2] = true;
        }
    }

    /*
     *  The mouseReleased method is used to detect mouse button up events and process them.
     *  button - The mouse button that has been released.
     */
    @Override
    public void mouseReleased(MouseEvent button)
    {
        // Left mouse button released.
        if((button.getButton() == 1) && (this.state.keys[0]))
        {
            this.state.keys[0] = false;
            this.state.updateTimes[1] = -1;
        }
        // Right mouse button released.
        if((button.getButton() == 3) && (this.state.keys[1]))
        {
            this.state.keys[1] = false;
            this.state.updateTimes[2] = -1;
        }
        // Middle mouse button released.
        if((button.getButton() == 2) && (this.state.keys[2]))
        {
            this.state.keys[2] = false;
        }
    }

    /*
     * The mouseEntered method is unused but is required to implement the MouseListener. It would be used to process the mouse entering the window.
     * button - The mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent button)
    {}

    /*
     * The mouseExited method is unused but is required to implement the MouseListener. It would be used to process the mouse leaving the window.
     * button - The mouse event.
     */
    @Override
    public void mouseExited(MouseEvent button)
    {}
}

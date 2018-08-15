/**
 * Created by William Davis on 15/10/2015.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Data.Config;
import Data.State;
import Data.Coord;
import GUI.Squares.Square;
import GUI.Tetriminos.*;

public class Grid extends JPanel
{
    // The state variable is used to store an instance of the games state class.
    private State state;
    // The segments variable is used to store a two dimensional list of squares that make up the play grid.
    private Square[][] segments;
    // The piece variable is used to store an instance of the current block.
    private Block piece;
    // The pieceLocation variable is used to store a coordinate of the offset of the block in the grid.
    private Coord pieceLocation;
    // The detectedLines variable is used to store a list of lines that have been found for destroying.
    private int[] detectedLines;
    // The paused variable is used to store whether the game is paused.
    private boolean paused = false;

    /*
     * The Grid constructor is used to prepare the grid for use in the game.
     * state - An instance of the games state.
     */
    public Grid(State state)
    {
        super(new GridLayout(Config.gameRows, Config.gameColumns));
        this.setPreferredSize(new Dimension(Config.gameColumns * Config.blockWidth, Config.gameRows * Config.blockWidth));
        this.segments = new Square[Config.gameRows][Config.gameColumns];
        // Create an instance for each square in the segments variable.
        int i = 0;
        while(i < this.segments.length)
        {
            int j = 0;
            while(j < this.segments[i].length)
            {
                this.segments[i][j] = new Square();
                this.add(this.segments[i][j]);
                ++j;
            }
            ++i;
        }
        this.state = state;
    }

    /*
     * The end method is used to change the next active row into a game over row.
     */
    public void end()
    {
        int i = 0;
        while(i < Config.gameRows)
        {
            boolean line = false;
            int j = 0;
            while(j < Config.gameColumns)
            {
                if(this.segments[i][j].isEnabled())
                {
                    line = true;
                    this.segments[i][j].setState(-4);
                }
                ++j;
            }
            if(line)
            {
                break;
            }
            ++i;
        }
        if(i == Config.gameRows)
        {
            this.state.updateTimes[7] = -1;
        }
    }

    /*
     * The restart method is used to return all squares to the starting state.
     */
    public void restart()
    {
        int i = 0;
        while(i < this.segments.length)
        {
            int j = 0;
            while(j < this.segments[i].length)
            {
                this.segments[i][j].clear();
                ++j;
            }
            ++i;
        }
    }

    /*
     * The Spawn method is used to spawn the next block in the game window.
     */
    public void Spawn()
    {
        if((this.piece == null) && (!this.state.isGameOver()))
        {
            //Select the block type and prepare to spawn it.
            switch(this.state.nextBlock())
            {
                case 6: this.piece = new Block_Z();
                        break;
                case 5: this.piece = new Block_T();
                        break;
                case 4: this.piece = new Block_S();
                        break;
                case 3: this.piece = new Block_O();
                        break;
                case 2: this.piece = new Block_L();
                        break;
                case 1: this.piece = new Block_J();
                        break;
                default: this.piece = new Block_I();
                        break;
            }
            this.state.newBlock();
            this.pieceLocation = new Coord(this.piece.getOrigin().getX() + Config.spawn.getX(), this.piece.getOrigin().getY() + Config.spawn.getY());
            // Detect if the spawn point is bocked by other blocks.
            boolean blocked = false;
            int i = 0;
            while(i < this.piece.getCoords().length)
            {
                if(this.segments[this.piece.getCoords()[i].getY() + this.pieceLocation.getY()][this.piece.getCoords()[i].getX() + this.pieceLocation.getX()].isEnabled())
                {
                    blocked = true;
                    break;
                }
                ++i;
            }
            if(blocked)
            {
                // If the spawn is blocked then the game is over.
                i = 0;
                while(i < this.piece.getCoords().length)
                {
                    try
                    {
                        int x = this.pieceLocation.getX() + this.piece.getCoords()[i].getX();
                        int y = this.pieceLocation.getY() + this.piece.getCoords()[i].getY() - 1;
                        if(!this.segments[y][x].isEnabled())
                        {
                            this.segments[y][x].set(this.piece.getColor());
                        }
                        else
                        {
                            break;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {}
                    ++i;
                }
                this.state.endGame();
                this.piece = null;
                this.pieceLocation = null;
            }
            else
            {
                // Spawn is clear so spawn the block.
                i = 0;
                while(i < this.piece.getCoords().length)
                {
                    this.segments[this.pieceLocation.getY() + this.piece.getCoords()[i].getY()][this.pieceLocation.getX() + this.piece.getCoords()[i].getX()].set(this.piece.getColor());
                    ++i;
                }
            }
        }
    }

    /*
     * The collisionCheck method is used to check if the next block position is obstructed.
     * future - The set of locations the block will occupy after the move.
     */
    private boolean collisionCheck(Coord[] future)
    {
        if(this.piece != null)
        {
            int i = 0;
            while(i < future.length)
            {
                if((future[i].getY() >= Config.gameRows) || (future[i].getX() < 0) || (future[i].getX() >= Config.gameColumns))
                {
                    return true;
                }
                else
                {
                    if(this.segments[future[i].getY()][future[i].getX()].isEnabled())
                    {
                        // Check to make sure the future position is not colliding with its own current position.
                        int j = 0;
                        boolean check = true;
                        while(j < this.piece.getCoords().length)
                        {
                            if((this.piece.getCoords()[j].getX() + this.pieceLocation.getX() == future[i].getX()) && (this.piece.getCoords()[j].getY() + this.pieceLocation.getY() == future[i].getY()))
                            {
                                check = false;
                                break;
                            }
                            ++j;
                        }
                        if(check)
                        {
                            return true;
                        }
                    }
                }
                ++i;
            }
        }
        return false;
    }

    /*
     * The slide method is used to move the current block on the grid.
     * changeX - The horizontal grid change.
     * changeY - The vertical grid change.
     */
    private void slide(int changeX, int changeY)
    {
        if((this.piece != null) && (!this.state.isGameOver()))
        {
            Coord[] newLocations = new Coord[this.piece.getCoords().length];
            int i = 0;
            while(i < this.piece.getCoords().length)
            {
                newLocations[i] = new Coord(this.piece.getCoords()[i].getX() + this.pieceLocation.getX() + changeX, this.piece.getCoords()[i].getY() + this.pieceLocation.getY() + changeY);
                ++i;
            }
            if(!collisionCheck(newLocations))
            {
                // Move each piece of the block.
                i = 0;
                while(i < this.piece.getCoords().length)
                {
                    this.segments[this.piece.getCoords()[i].getY() + this.pieceLocation.getY()][this.piece.getCoords()[i].getX() + this.pieceLocation.getX()].clear();
                    ++i;
                }
                i = 0;
                while(i < newLocations.length)
                {
                    this.segments[newLocations[i].getY()][newLocations[i].getX()].set(this.piece.getColor());
                    ++i;
                }
                this.pieceLocation.adjust(changeX, changeY);
            }
            else if((changeX == 0) && (changeY == 1))
            {
                // Lock the block in.
                this.piece = null;
                this.pieceLocation = null;
                state.lockBlock();
                this.detectLines();
            }
        }
    }

    /*
     * The blockDrop method is used to move the block down by one space.
     */
    public void blockDrop()
    {
        this.slide(0, 1);
    }

    /*
     * The blockLeft method is used to move the block left by one space.
     */
    public void blockLeft()
    {
        this.slide(-1, 0);
    }

    /*
     * The blockRight method is used to move the block right by one space.
     */
    public void blockRight()
    {
        this.slide(1, 0);
    }

    /*
     * The rotate method is used to rotate the current block on the grid.
     */
    public void rotate()
    {
        if((this.piece != null) && (!this.state.isGameOver()))
        {
            Coord[] tempCoords = this.piece.testRotate();
            Coord[] newLocations = new Coord[tempCoords.length];
            int safeCheck = 0;
            int i = 0;
            while(i < newLocations.length)
            {
                newLocations[i] = new Coord(tempCoords[i].getX() + this.pieceLocation.getX(), tempCoords[i].getY() + this.pieceLocation.getY());
                if((newLocations[i].getY() < 0) && (newLocations[i].getY() < safeCheck))
                {
                    safeCheck = Math.abs(newLocations[i].getY());
                }
                ++i;
            }
            // Fix the problem of rotating at the top of the game window.
            if(safeCheck > 0)
            {
                i = 0;
                while(i < newLocations.length)
                {
                    newLocations[i].adjustY(safeCheck);
                    ++i;
                }
            }
            // Update the grid user interface if the move is valid.
            if(!collisionCheck(newLocations))
            {
                i = 0;
                while(i < this.piece.getCoords().length)
                {
                    this.segments[this.piece.getCoords()[i].getY() + this.pieceLocation.getY()][this.piece.getCoords()[i].getX() + this.pieceLocation.getX()].clear();
                    ++i;
                }
                i = 0;
                while(i < newLocations.length)
                {
                    this.segments[newLocations[i].getY()][newLocations[i].getX()].set(this.piece.getColor());
                    ++i;
                }
                this.piece.rotate();
            }
            if(safeCheck > 0)
            {
                this.pieceLocation.adjustY(safeCheck);
            }
        }
    }

    /*
     * The isLineWaiting method is used to check if any lines have been marked for clearing.
     */
    public boolean isLineWaiting()
    {
        return this.detectedLines != null;
    }

    /*
     * The detectLines method is used to find and record filled lines.
     */
    private void detectLines()
    {
        ArrayList<Integer> lines = new ArrayList<Integer>();
        int i = 0;
        while(i < Config.gameRows)
        {
            boolean line = true;
            int j = 0;
            while(j < Config.gameColumns)
            {
                if(!this.segments[i][j].isEnabled())
                {
                    line = false;
                    break;
                }
                ++j;
            }
            if(line)
            {
                lines.add(i);
            }
            ++i;
        }
        if(lines.size() == 0)
        {
            this.detectedLines = null;
        }
        else
        {
            int[] found = new int[lines.size()];
            int k = 0;
            while(k < lines.size())
            {
                found[k] = lines.get(k);
                ++k;
            }
            this.detectedLines = found;
        }
    }

    /*
     * The cycleLines method is used to step through the animation frames of lines being destroyed. Dropping the above blocks when the lines are cleared.
     */
    public void cycleLines()
    {
        if(this.detectedLines != null)
        {
            if((this.state.lineMode == 1) || (this.state.lineMode == 2))
            {
                // Change animation frame.
                int i = 0;
                while(i < this.detectedLines.length)
                {
                    int j = 0;
                    while(j < this.segments[this.detectedLines[i]].length)
                    {
                        this.segments[this.detectedLines[i]][j].setState(this.state.lineMode);
                        ++j;
                    }
                    ++i;
                }
            }
            else if(this.state.lineMode == 3)
            {
                this.state.score.lines(this.detectedLines.length);
                int i = this.segments.length;
                int drop = 0;
                while(i > 0)
                {
                    boolean blank = false;
                    int l = 0;
                    while(l < this.detectedLines.length)
                    {
                        if(i - 1 == this.detectedLines[l])
                        {
                            blank = true;
                            break;
                        }
                        ++l;
                    }
                    if(blank)
                    {
                        // Clear a line.
                        int j = 0;
                        while(j < this.segments[i - 1].length)
                        {
                            this.segments[i - 1][j].setState(0);
                            ++j;
                        }
                        ++drop;
                    }
                    else
                    {
                        if(drop > 0)
                        {
                            //Move blocks above a cleared line down.
                            int j = 0;
                            while(j < this.segments[i - 1].length)
                            {
                                if(this.segments[i - 1][j].isEnabled())
                                {
                                    boolean check = true;
                                    if(this.piece != null)
                                    {
                                        int k = 0;
                                        while(k < this.piece.getCoords().length)
                                        {
                                            if((piece.getCoords()[k].getX() + this.pieceLocation.getX() == j) && (this.piece.getCoords()[k].getY() + this.pieceLocation.getY() == i - 1))
                                            {
                                                check = false;
                                                break;
                                            }
                                            ++k;
                                        }
                                    }
                                    if(check)
                                    {
                                        Color color = this.segments[i - 1][j].clear();
                                        this.segments[(i - 1) + drop][j].set(color);
                                    }
                                }
                                ++j;
                            }
                        }
                    }
                    --i;
                }
                this.detectedLines = null;
            }
        }
    }

    /*
     * The isPaused method is used to check if the grid is paused.
     */
    public boolean isPaused()
    {
        return this.paused;
    }

    /*
     * The pause method is used to pause the grid.
     */
    public void pause()
    {
        int mode;
        this.paused = !this.paused;
        if(this.paused)
        {
            mode = -3;
        }
        else
        {
            mode = -1;
        }
        int i = 0;
        while(i < this.segments.length)
        {
            int j = 0;
            while(j < this.segments[i].length)
            {
                if(this.segments[i][j].isEnabled())
                {
                    this.segments[i][j].setState(mode);
                }
                else
                {
                    this.segments[i][j].setState(mode + 1);
                }
                ++j;
            }
            ++i;
        }
    }
}

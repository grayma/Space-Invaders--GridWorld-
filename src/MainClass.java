/*
 * Defender.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.actor.*;
import info.gridworld.grid.*;

/**
 * This is the MainClass that starts the program and manages the grid.
 */
public class MainClass extends ActorWorld {
    Defender defender = null;
    boolean clientBombActive = false;
    boolean invaderBombActive = false;
    InvaderGroup group = null;
    public int invaderPause = 30;
    
    /**
     * Constructor.  Sets up grid, adds instructions, and adds all actors (invaders, shields, and the defender) to the grid.
     */
    public MainClass() {
        super();
        group = new InvaderGroup(this);
        setMessage("Raise the speed all the way up.  Hit run.\nControls:  W (firing), A (move left), D (move right).");
        setGrid(new BoundedGrid(20, 21));
        for (int i = 1; i < 20; i += 3) {
            Shield shield = new Shield();
            add(shield);
            shield.moveTo(new Location(17, i));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 5; j < 15; j++) {
                Invader invader = new Invader();
                add(invader);
                invader.moveTo(new Location(i, j));
                group.invaders[i][j-5] = invader;
            }
        }
        defender = new Defender(this);
        add(defender);
        defender.moveTo(new Location(19, 10));
        show();
    }

    /**
     * Main function that starts the program.
     */
    public static void main(String[] args) {
        MainClass mClass = new MainClass();
        mClass.show();
    }
    
    /**
     * Manages keyboard input.
     */
    public boolean keyPressed(String description, Location loc) {
        defender.work(description);
        return false;
    }
    
    int i = 0;
    /**
     * Moves the invader group and all other actors on the grid.
     */
    public void step() {
        if (i == invaderPause) {
            i = 0;
            group.groupAct();
        }
        else
            i++;
        super.step();
    }
}
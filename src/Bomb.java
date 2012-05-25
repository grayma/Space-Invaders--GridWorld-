/*
 * Bomb.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

/**
 * Base class for a bomb.
 */
public class Bomb extends Actor {
    int i = 0;
    public static final int CONSTANT_PAUSE = 7;
    MainClass parent = null;
    
    /**
     * Base Bomb constructor.
     */
    public Bomb(MainClass sender) {
        setColor(java.awt.Color.BLACK);
        parent = sender;
    }
    
    /**
     * Base acting method for a bomb.
     */
    public void act() {
        i++;
        if (i == CONSTANT_PAUSE)
            i = 0;
        else
            return;
        Location loc = getLocation().getAdjacentLocation(Location.NORTH);
        if (getGrid().isValid(loc)) {
            moveTo(loc);
        }
        else {
            removeSelfFromGrid();
        }
    }
}
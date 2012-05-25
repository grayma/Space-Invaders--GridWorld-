/*
 * Shield.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.actor.Actor;

/**
 * Shield class that can take 10 hits from bombs.
 */
public class Shield extends Actor {
    int hits = 0;
    public static final int HITPOINTS = 10;
    
    /**
     * Constructor.
     */
    public Shield() {
        setColor(java.awt.Color.BLUE);
    }
    
    /**
     * Prevents flipping behavior from overriding actors.
     */
    public void act() {
        
    }
    
    /**
     * Adds a hit to total hit count.  Deletes self if hit count has reached max.
     */
    public void addHit() {
        hits++;
        if (hits == HITPOINTS)
            removeSelfFromGrid();
    }
}
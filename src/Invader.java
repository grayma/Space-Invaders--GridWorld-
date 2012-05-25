/*
 * Invader.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.actor.Actor;

/**
 * Simple class to manage some basic values for an invader.
 */
public class Invader extends Actor {
    public boolean isDead = false;

    /**
     * Constructor.
     */
    public Invader() {
        setColor(java.awt.Color.GREEN);
    }
    
    /**
     * Prevents flipping behavior because of overriding the actor class.
     */
    public void act() {
        
    }
    
    /**
     * Overrides remove function so to change isDead variable.
     */
    public void removeSelfFromGrid() {
        isDead = true;
        super.removeSelfFromGrid();
    }
}
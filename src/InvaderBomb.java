/*
 * InvaderBomb.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;

/**
 * Bombs used by the invader (AI/Enemy).
 */
public class InvaderBomb extends Bomb {
    InvaderGroup groupParent = null;
    
    /**
     * Constructor for InvaderBomb.  Adds InvaderGroup parameter to constructor so the amount of
     * InvaderBombs on the grid can be tracked.
     */
    public InvaderBomb(MainClass sender, InvaderGroup group) {
        super(sender);
        groupParent = group;
    }
    
    /**
     * Takes care of collision detection for other actors in the grid.
     */
    public void act() {
        i++;
        if (i == CONSTANT_PAUSE)
            i = 0;
        else
            return;
        Location loc = getLocation().getAdjacentLocation(Location.SOUTH);
        if (getGrid().isValid(loc)) {
            Actor actor = getGrid().get(loc);
            if (actor instanceof Shield) {
                ((Shield)actor).addHit();
                removeSelfFromGrid();
            }
            if (actor instanceof Defender) {
                ((Defender)actor).removeSelfFromGrid();
                removeSelfFromGrid();
                parent.setMessage("You lose!");
                groupParent.done = true;
            }
            if (actor instanceof Bomb) {
                removeSelfFromGrid();
                actor.removeSelfFromGrid();
            }
            if (actor == null) {
                moveTo(loc);
            }
        }
        else {
            removeSelfFromGrid();
        }
    }
    
    /**
     * Removes the bomb from the grid but also tells the InvaderGroup parent class it's 
     * ok to fire another invader bomb.
     */
    public void removeSelfFromGrid() {
        parent.invaderBombActive = false;
        super.removeSelfFromGrid();
    }
}
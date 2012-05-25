/*
 * ClientBomb.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;

/**
 * Bombs used by client (player).
 */
public class ClientBomb extends Bomb {
    /**
     * Constructor for ClientBomb.
     */
    public ClientBomb(MainClass sender) {
        super(sender);
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
        Location loc = getLocation().getAdjacentLocation(Location.NORTH);
        if (getGrid().isValid(loc)) {
            Actor actor = getGrid().get(loc);
            if (actor instanceof Shield) {
                ((Shield)actor).addHit();
                removeSelfFromGrid();
            }
            if (actor instanceof Invader) {
                ((Invader)actor).removeSelfFromGrid();
                removeSelfFromGrid();
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
     * Removes the bomb from the grid but also tells the parent class it's ok to fire another
     * client bomb.
     */
    public void removeSelfFromGrid() {
        parent.clientBombActive = false;
        super.removeSelfFromGrid();
    }
}
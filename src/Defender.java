/*
 * Defender.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

/**
 * Defender (player) class.  Controlled by a single player using the W, A, and D keys for firing, moving
 * left, and moving right respectively.
 */
public class Defender extends Actor {
    MainClass parent = null;
    boolean isDead = false;
    
    /**
     * Constructor.  Requires parent class as a parameter for updating the message and keeping track of bombs
     * on the board.
     */
    public Defender(MainClass sender) {
        setColor(java.awt.Color.RED);
        parent = sender;
    }
    
    /**
     * No acting is done within this function, just to override base actor behavior (flipping).
     */
    public void act() {
        
    }
    
    /**
     * Fires a client bomb "from" the defender.
     */
    public void fire() {
        ClientBomb bomb = new ClientBomb(parent);
        if (!parent.clientBombActive)
            parent.clientBombActive = true;
        else
            return;
        Location loc = getLocation().getAdjacentLocation(0);
        Actor actor = getGrid().get(loc);
        if (actor instanceof Invader) { //will only happen if an invader is right in front of the defender
            actor.removeSelfFromGrid();
            parent.clientBombActive = false;
            return;
        }
        else if (actor == null) {
            parent.add(bomb);
            bomb.moveTo(loc);
        }
        return;
    }
    
    /**
     * Performs an action based on the parameter desc.
     * A = move left
     * D = move right
     * W = fire client bomb
     */
    public void work(String desc) {
        Location loc = null;
        if (isDead)
            return;
        if (desc.equals("A")) {
            loc = getLocation().getAdjacentLocation(270);
            if (getGrid().isValid(loc)) {
                Actor actor = getGrid().get(loc);
                if (actor instanceof Invader) {
                    removeSelfFromGrid();
                    isDead = true;
                }
                else
                    moveTo(loc);
            }
        }
        if (desc.equals("D")) {
            loc = getLocation().getAdjacentLocation(90);
            if (getGrid().isValid(loc)) {
                Actor actor = getGrid().get(loc);
                if (actor instanceof Invader) {
                    removeSelfFromGrid();
                    isDead = true;
                }
                else
                    moveTo(loc);
            }
        }
        if (desc.equals("W")) {
            fire();
        }
    }
}
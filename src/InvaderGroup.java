/*
 * Invader.java
 * Matthew Gray
 * AP CS Final Project
 */

import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;

/**
 * Class that controls group behavior of the invaders.
 */
public class InvaderGroup {
    public Invader[][] invaders = new Invader[3][10];
    boolean downFlag = false;
    int down = Location.SOUTH;
    int side = Location.EAST;
    public boolean done = false;
    boolean gameOver = false;
    MainClass parent = null;
    
    /**
     * Constructor that retrieves the parent (board) class as a parameter.
     */
    public InvaderGroup(MainClass sender) {
        parent = sender;
    }
    
    /**
     * Controls the behavior of all the invaders.
     */
    public void groupAct() {
        boolean game = true;
        if (gameOver) {
            return;
        }
        if (side == Location.EAST) {
            for (int i = 2; i >= 0; i--) {
                for (int j = 9; j >= 0; j--) {
                    if (invaders[i][j] != null && !invaders[i][j].isDead) {
                        doMove(i, j);
                        game = false;
                    }
                }
            }
        }
        else {
            for (int i = 2; i >= 0; i--) {
                for (int j = 0; j < 10; j++) {
                    if (invaders[i][j] != null && !invaders[i][j].isDead) {
                        doMove(i, j);
                        game = false;
                    }
                }
            }
        }
        if (game) {
            gameOver = true;
            parent.setMessage("You win!!!!!");
        }
        downFlag = false; //resets the down movement flag so invaders only move down once every 20 or so moves
    }
    
    /**
     * Manages acting with a single invader from the group.
     */
    void doMove(int i, int j) {
        if (done) {
            return;
        }
        Invader cInvader = invaders[i][j];
        if (cInvader.isDead) {
            invaders[i][j] = null;
            return;
        }
        //randomly generates an "invader" bomb
        if (!parent.invaderBombActive && cInvader.getLocation().getRow() < 20) {
            int randNum = (int)(Math.random()*60.999)+1;
            if (randNum == 38) { //38 is a randomly chosen number to be the magic "fire" command.
                int index = (int)(Math.random()*9)+1;
                Location bombLoc = cInvader.getLocation();
                bombLoc = new Location(bombLoc.getRow()+2, bombLoc.getCol());
                if (parent.getGrid().isValid(bombLoc) && parent.getGrid().get(bombLoc) instanceof Invader == false) {
                    Actor actor = parent.getGrid().get(bombLoc);
                    if (actor instanceof Shield || actor instanceof Defender) {
                        actor.removeSelfFromGrid();
                    }
                    else {
                        InvaderBomb invBomb = new InvaderBomb(parent, this);
                        parent.add(invBomb);
                        invBomb.moveTo(bombLoc);
                        parent.invaderBombActive = true;
                    }
                }
            }
        }
        Location loc = null;
        if (downFlag)
            loc = cInvader.getLocation().getAdjacentLocation(down);
        else
            loc = cInvader.getLocation().getAdjacentLocation(side);
        if (cInvader.getGrid().isValid(loc)) { //checks validity of location to side.
            Actor actor = cInvader.getGrid().get(loc);
            if (actor instanceof Defender) {
                Defender def = (Defender)actor;
                def.isDead = true;
                def.removeSelfFromGrid();
                parent.setMessage("You lose!");
            }
            if (actor instanceof Shield) {
                actor.removeSelfFromGrid();
            }
            if (actor instanceof Bomb) {
                cInvader.removeSelfFromGrid();
                ((Bomb)actor).removeSelfFromGrid();
            }
            if (actor instanceof Invader) {
                actor.removeSelfFromGrid();
            }
            if (cInvader.getLocation() != null)
                cInvader.moveTo(loc);
        }
        else { //invalid location to the side.  must go down.
            if (side == Location.EAST)
                side = Location.WEST;
            else
                side = Location.EAST;
            downFlag = true;
            parent.invaderPause--;
            Location downLoc = cInvader.getLocation().getAdjacentLocation(down);
            if (cInvader.getGrid().isValid(downLoc)) {
                cInvader.moveTo(downLoc);
            }
            else {
                done = true;
            }
        }
    }
}
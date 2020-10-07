/*
 Copyright 2009  by Sean Luke and Vittorio Zipparo
 Licensed under the Academic Free License version 3.0
 See the file "LICENSE" for more information
 */
package com.jhw.simulation.dots.agents.players;

import com.jhw.simulation.dots.sim.DotsSimulation_Sim;
import java.awt.Image;
import javax.swing.ImageIcon;
import sim.engine.*;
import sim.util.*;

/**
 * Player is an Agent and is also Steppable. The Player moves first, then the
 * IA.
 */
public class Player extends Agent implements Steppable {

    private static final long serialVersionUID = 1;

    private final int tag;

    /**
     * Creates a Pac assigned to the given tag, puts him in sim.agents at the
     * start location, and schedules him on the schedule.
     */
    public Player(DotsSimulation_Sim sim, int tag, Double2D startLoc, int team) {
        super(sim, startLoc, team);
        this.tag = tag;
    }

    /* Default policy implementation: Player is controlled through the joystick/keyboard
     * To changhe Player behavior derived classes should override this method
     */
    protected void doPolicyStep(SimState state) {
        int nextAction = getSim().getNextAction(tag);
        // player delays the next action until he can do it.  This requires a bit of special code
        if (isPossibleToDoAction(nextAction)) {
            performAction(nextAction);
        } else if (isPossibleToDoAction(super.getLastAction())) {
            performAction(super.getLastAction());
        }
    }

    /* Steps the Player.  This does various things.  First, we look up the action from the user (getNextAction).
     Then we determine if it's possible to do the action.  If not, we determine if it's possible to do the
     previous action.  Then we do those actions.  As a result we may have eaten an energizer or a dot.  If so
     we remove the dot or energizer, update the score, and possibly frighten the ghosts.  If we've eaten all
     the dots, we schedule an event to reset the level.  Next we check to see if we've encountered a ghost.
     If the ghost is frightened, we eat it and put him in jail.  Otherwise we die.
     */
    @Override
    public void step(SimState state) {
        if (!isStop()) {//si te estas muriendo no hagas nada
            super.step(state);//update all the values
            doPolicyStep(state);
        }
    }

    public int getTag() {
        return this.tag;
    }

    @Override
    public Image getImage() {
        switch (getLastAction()) {
            case Player.N:
                return new ImageIcon("media/icons/players/p" + (tag + 1) + "u" + getTeam() + ".png").getImage();
            case Player.W:
                return new ImageIcon("media/icons/players/p" + (tag + 1) + "l" + getTeam() + ".png").getImage();
            case Player.S:
                return new ImageIcon("media/icons/players/p" + (tag + 1) + "d" + getTeam() + ".png").getImage();
            case Player.E:
                return new ImageIcon("media/icons/players/p" + (tag + 1) + "r" + getTeam() + ".png").getImage();
            case Player.NOTHING:
                return new ImageIcon("media/icons/players/p" + (tag + 1) + "d" + getTeam() + ".png").getImage();//default down
        }
        return new ImageIcon("media/icons/frightened.png").getImage();//nunca debe ocurrir
    }
}

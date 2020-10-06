/*
 Copyright 2009  by Sean Luke and Vittorio Zipparo
 Licensed under the Academic Free License version 3.0
 See the file "LICENSE" for more information
 */
package com.jhw.simulation.dots.agents;

import com.jhw.simulation.dots.dots.DotsSimulation_Sim;
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
        super.step(state);//update all the values
        doPolicyStep(state);
        // now maybe we eat a dot or energizer...

        /*Bag nearby = sim.getDots().getNeighborsWithinDistance(new Double2D(location), 0.3);  // 0.3 seems reasonable.  We gotta be right on top anyway
         for (int i = 0; i < nearby.numObjs; i++) {
         Object obj = nearby.objs[i];
         if (sim.getDots().getObjectLocation(obj).equals(location)) // uh oh
         {
         sim.setScore(sim.getScore() + 40);// only 40 because there is a dot right below the energizer.  Total should appear to be 50
         sim.getDots().remove(obj);
         eatGhostScore = 200;  // reset

         // create a Steppable to turn off ghost frightening after the ghosts have had a chance to
         // be sufficiently frightened
         sim.schedule.scheduleOnce(new Steppable() // the pac goes first, then the ghosts, so they'll get frightened this timestep, so we turn it off first thing next time
         {
         @Override
         public void step(SimState state) {
         }
         }, -1);
         }
         if (obj instanceof Dot && sim.getDots().getObjectLocation(obj).equals(location)) {
         sim.setScore(sim.getScore() + 10);
         sim.getDots().remove(obj);
         }
         }
         if (nearby.numObjs > 0) {
         if (sim.getDots().size() == 0) // empty!
         {
         sim.schedule.scheduleOnceIn(0.25, new Steppable() // so it happens next
         {
         public void step(SimState state) {
         resetLevel();
         }
         });  // the Ghosts move a bit more
         }
         }

         // a ghost perhaps?
         nearby = sim.getAgents().getNeighborsWithinDistance(new Double2D(location), 0.3);  // 0.3 seems reasonable.  We gotta be right on top anyway
         for (int i = 0; i < nearby.numObjs; i++) {
         Object obj = nearby.objs[i];
         if (obj instanceof Ghost && location.distanceSq(sim.getAgents().getObjectLocation(obj)) <= 0.2) // within 0.4 roughly
         {
         Ghost m = (Ghost) obj;
         if (m.frightened > 0) // yum
         {
         sim.setScore(sim.getScore() + eatGhostScore);
         eatGhostScore *= 2;  // each Ghost is 2x more
         m.putInJail();
         } else // ouch
         {
         sim.schedule.scheduleOnceIn(0.5, new Steppable() // so it happens next.  Should be after resetLEvel(), so we do 0.5 rather than 0.25
         {
         @Override
         public void step(SimState state) {
         die();
         }
         });  // the ghosts move a bit more
         }
         }
         }*/
    }

    /**
     * Resets the level as a result of eating all the dots. To do this we first
     * clear out the entire schedule; this will eliminate everything because
     * resetLevel() was itself scheduled at a half-time timestep so it's the
     * only thing going on right now. Clever right? I know! So awesome. Anyway,
     * we then schedule a little pause to occur. Then afterwards we reset the
     * game.
     */
    public void resetLevel() {
        // clear out the schedule, we're done
        getSim().schedule.clear();

        // do a little pause
        getSim().schedule.scheduleOnce(
                new Steppable() {
                    public int count = 0;

                    public void step(SimState state) {
                        if (++count < WAIT_TIME * 2) {
                            getSim().schedule.scheduleOnce(this);
                        }
                    }
                });

        getSim().schedule.scheduleOnceIn(WAIT_TIME * 2,
                new Steppable() {
                    public void step(SimState state) {
                        getSim().setLevel(getSim().getLevel() + 1);
                        getSim().resetGame();
                    }
                });
    }

    /**
     * Dies as a result of encountering a monster. To do this we first clear out
     * the entire schedule; this will eliminate everything because die() was
     * itself scheduled at a half-time timestep so it's the only thing going on
     * right now. Clever right? I know! So awesome. Anyway, we then schedule a
     * little pause to occur. Then afterwards we schedule a period where the pac
     * spins around and around by changing his lastAction. Then finally we wait
     * a little bit more, then reset the agents so they're at their start
     * locations again.
     */
    public void die() {
        getSim().setDeaths(getSim().getDeaths() + 1);

        // okay so we're the last pac alive.  Let's do the little dance
        // clear out the schedule, we're done
        getSim().schedule.clear();

        // do a little pause
        getSim().schedule.scheduleOnce(
                new Steppable() {
                    public int count = 0;

                    public void step(SimState state) {
                        if (++count < WAIT_TIME) {
                            getSim().schedule.scheduleOnce(this);
                        }
                    }
                });

        // wait a little more.
        getSim().schedule.scheduleOnceIn(WAIT_TIME,
                new Steppable() {
                    @Override
                    public void step(SimState state) {
                        // remove the Ghosts
                        Bag b = getSim().getAgents().getAllObjects();
                        for (int i = 0; i < b.numObjs; i++) {
                            if (b.objs[i] != Player.this) {
                                b.remove(i);
                                i--;
                            }
                        }
                    }
                });

        // do a little spin
        getSim().schedule.scheduleOnceIn(WAIT_TIME + 1,
                new Steppable() {
                    public int count = 0;

                    public void step(SimState state) {
                        if (count % SPIN_SPEED == 0) {
                            setLastAction((getLastAction() + 1) % 4);
                        }  // spin around
                        if (++count < SPIN_TIME) {
                            getSim().schedule.scheduleOnce(this);
                        }
                    }
                });

        // wait a little more, then reset the agents.
        getSim().schedule.scheduleOnceIn(WAIT_TIME * 2 + SPIN_TIME,
                new Steppable() {
                    public void step(SimState state) {
                        getSim().resetAgents();
                    }
                });
    }

    public int getTag() {
        return this.tag;
    }

    @Override
    public Image getImage() {
        switch (getLastAction()) {
            case Player.N:
                return new ImageIcon("media/icons/p" + (tag + 1) + "u" + getTeam() + ".png").getImage();
            case Player.W:
                return new ImageIcon("media/icons/p" + (tag + 1) + "l" + getTeam() + ".png").getImage();
            case Player.S:
                return new ImageIcon("media/icons/p" + (tag + 1) + "d" + getTeam() + ".png").getImage();
            case Player.E:
                return new ImageIcon("media/icons/p" + (tag + 1) + "r" + getTeam() + ".png").getImage();
            case Player.NOTHING:
                return new ImageIcon("media/icons/p" + (tag + 1) + "d" + getTeam() + ".png").getImage();//default down
        }
        return new ImageIcon("media/icons/frightened.png").getImage();//nunca debe ocurrir
    }
}

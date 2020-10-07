package com.jhw.simulation.dots.agents.players;

import com.jhw.simulation.dots.agents.statics.Dot;
import com.jhw.simulation.dots.sim.DotsSimulation_Sim;
import java.awt.Image;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.*;
import sim.portrayal.*;
import sim.field.grid.*;

/**
 * An agent is either a Player or a IA -- something which is capable of moving
 * about. Agents have a previous action they've done which determines their
 * "orientation".
 *
 * <p>
 * Agents live in a Continuous 2D world but associate that world with the grid
 * environment of the maze. The continuous location of the agent is 'location'.
 * Agents can't actually be any* continuous location but rather are one of a set
 * of finely-discretized locations. The degree of discretization is
 * 'discretization', and it in turn affects the speed of the agent (as the agent
 * steps through the discretized locations to move from grid location to grid
 * location, how fine the discretization is determines how many steps he must
 * move to get from one grid location to another, and thus his speed going
 * through the maze).
 */
public abstract class Agent implements Oriented2D, Steppable {

    private static final long serialVersionUID = 1;

    /**
     * How long we wait while the Player dies (not spinning).
     */
    public static final int WAIT_TIME = 100;

    /**
     * How long we wait while the Player spins around while dying.
     */
    public static final int SPIN_TIME = 100;

    /**
     * How often the Player rotates 90 degrees while spinning.
     */
    public static final int SPIN_SPEED = 5;

    /**
     * The Action "Go North"
     */
    public static final int N = 0;

    /**
     * The Action "Go East"
     */
    public static final int E = 1;

    /**
     * The Action "Go South"
     */
    public static final int S = 2;

    /**
     * The Action "Go West"
     */
    public static final int W = 3;

    /**
     * The Action "Do Nothing"
     */
    public static final int NOTHING = -1;

    /**
     * The last action performed by the agent. Initially NOTHING.
     */
    private int lastAction = NOTHING;

    /**
     * The Player's discretization Standard(10).
     */
    public static final int PLAYER_DISCRETIZATION = 10;//mientras mas chiquito mas rapido

    /**
     * The location of the agent. We store it here to avoid having to do
     * multiple Continuous2D lookups, which are expensive.
     */
    private MutableDouble2D location;

    /**
     * The DotsSimulation_Sim simulation state
     */
    private final DotsSimulation_Sim sim;

    /**
     * The agent's discretization.
     */
    private int discretization = PLAYER_DISCRETIZATION;

    /**
     * The start location of the agent.
     */
    private final Double2D startLocation;

    /**
     * The team of the agent
     */
    private final int team;

    private boolean stop = false;

    /**
     * The agent's maximum velocity. Determined by discretization.
     */
    private double speed() {

        discretization = PLAYER_DISCRETIZATION + getDotsOwner();
        return 1.0 / discretization;
    }

    private double scale;

    private void updateScale() {
        scale = 1 - ((double) getDotsOwner() * 0.15d);
    }

    private int getDotsOwner() {
        int cont = 0;
        Bag obj = sim.getDots().getAllObjects();
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i) instanceof Dot) {
                Dot d = (Dot) obj.get(i);
                if (d.getOwner() == this) {
                    cont++;
                }
            }
        }
        return cont;
    }

    /**
     * Where the agent starts when the game is reset.
     */
    public Double2D getStartLocation() {
        return startLocation;
    }

    /**
     * Creates an agent, places it in its location in sim.agents, and schedules
     * it on sim.schedule.
     */
    public Agent(DotsSimulation_Sim sim, Double2D startLocation, int team) {
        this.sim = sim;
        this.team = team;
        this.startLocation = startLocation;
        this.location = new MutableDouble2D(startLocation);
        this.sim.getAgents().setObjectLocation(this, startLocation);
        sim.schedule.scheduleRepeating(this, 0, 1);  // schedule at time 0
    }

    /**
     * Returns the "orientation" of the agent.
     */
    @Override
    public double orientation2D() {
        switch (lastAction) {
            case N:
                return (Math.PI * 3.0) / 2;
            case E:
                return 0;
            case S:
                return Math.PI / 2;
            case W:
                return Math.PI;
        }
        return 0;
    }

    /**
     * Updates the location of the agent. Has a little wiring to make sure that
     * the x and y values are discretized to the discretization locations.
     */
    protected void changeLocation(double x, double y) {
        // locations can get off a bit because of double floating point error.  This keeps them in check:
        location.x = (((int) (Math.round(x * discretization)))) / (double) discretization;
        location.y = (((int) (Math.round(y * discretization)))) / (double) discretization;
        sim.getAgents().setObjectLocation(this, new Double2D(location));
    }

    protected MutableDouble2D nextCell(int nextAction) {
        double nx = 0, ny = 0;
        switch (nextAction) {
            case N:
                nx = location.x;
                ny = location.y - 1;
                break;
            case E:
                nx = location.x + 1;
                ny = location.y;
                break;
            case S:
                nx = location.x;
                ny = location.y + 1;
                break;
            case W:
                nx = location.x - 1;
                ny = location.y;
                break;
            default:
                throw new RuntimeException("default case should never occur");
        }
        return new MutableDouble2D(nx, ny);
    }

    /**
     * Performs a given action (N/W/S/E/NOTHING), moving the agent
     * appropriately.
     */
    protected void performAction(int action) {
        double x = location.x;
        double y = location.y;

        switch (action) {
            // we allow toroidal actions
            case N:
                y = sim.getAgents().sty(y - speed());
                break;
            case E:
                x = sim.getAgents().stx(x + speed());
                break;
            case S:
                y = sim.getAgents().sty(y + speed());
                break;
            case W:
                x = sim.getAgents().stx(x - speed());
                break;
            default:
                throw new RuntimeException("default case should never occur");
        }
        changeLocation(x, y);
        lastAction = action;
    }

    @Override
    public void step(SimState state) {//do nothing
        updateScale();
        eatOthers();
    }

    private void eatOthers() {
        Bag nearby = sim.getAgents().getNeighborsExactlyWithinDistance(new Double2D(location), 0.1);
        for (Object obj : nearby) {
            Agent ag = (Agent) obj;
            if (this.team == ag.getTeam() || ag.isStop()) {//si son del mismo equipos o se está stop, lo dejo 
                continue;
            }
            if (this.getScale() > ag.getScale()) {//si soy mas grande, comermelo
                ag.die();
            }
        }
    }

    /**
     * Determines if the agent can move with the given action (N/W/S/E/NOTHING)
     * without bumping into a wall.
     */
    protected boolean isPossibleToDoAction(int action) {
        if (action == NOTHING) {
            return false;  // no way
        }
        IntGrid2D maze = sim.getMaze();
        int[][] field = maze.field;

        // the Agents grid is discretized exactly on 1x1 boundaries so we can use floor rather than divide
        // the agent can straddle two locations at a time.  The basic location is x0, y0, and the straddled location is x1, y1.
        // It may be that x0 == y0.
        int x0 = (int) location.x;
        int y0 = (int) location.y;
        int x1 = location.x == x0 ? x0 : x0 + 1;
        int y1 = location.y == y0 ? y0 : y0 + 1;

        // for some actions we can only do the action if we're not straddling, or if our previous action was NOTHING
        if ((x0 == x1 && y0 == y1) || lastAction == NOTHING) {
            switch (action) {
                // we allow toroidal actions
                case N:
                    return (field[maze.stx(x0)][maze.sty(y0 - 1)] == 0);
                case E:
                    return (field[maze.stx(x0 + 1)][maze.sty(y0)] == 0);
                case S:
                    return (field[maze.stx(x0)][maze.sty(y0 + 1)] == 0);
                case W:
                    return (field[maze.stx(x0 - 1)][maze.sty(y0)] == 0);
            }
        } // for other actions we're continuing to do what we did last time.
        // assuming we're straddling, this should always be allowed unless our way is blocked
        else if (action == lastAction) {
            switch (action) {
                // we allow toroidal actions
                case N:  // use y0
                    return (field[maze.stx(x0)][maze.sty(y0)] == 0);
                case E:  // use x1
                    return (field[maze.stx(x1)][maze.sty(y0)] == 0);
                case S:  // use y1
                    return (field[maze.stx(x0)][maze.sty(y1)] == 0);
                case W:  // use x0
                    return (field[maze.stx(x0)][maze.sty(y0)] == 0);
            }
        } // last there are reversal actions.  Generally these are always allowed as well.
        else if ((action == N && lastAction == S)
                || (action == S && lastAction == N)
                || (action == E && lastAction == W)
                || (action == W && lastAction == E)) {
            return true;
        }

        return false;
    }

    /**
     * Die as result of has be eated.
     */
    public void die() {
        stop = true;
        //getSim().schedule.clear();

        // do a little pause of me
        getSim().schedule.scheduleOnce(
                new Steppable() {
            public int count = 0;

            @Override
            public void step(SimState state) {
                if (++count < WAIT_TIME) {
                    getSim().schedule.scheduleOnce(this);
                }
            }
        });

        // do a little spin
        getSim().schedule.scheduleOnceIn(WAIT_TIME,
                new Steppable() {
            private int count = 0;

            @Override
            public void step(SimState state) {
                if (count % SPIN_SPEED == 0) {
                    setLastAction((getLastAction() + 1) % 4);
                }  // spin around
                if (++count < SPIN_TIME) {
                    getSim().schedule.scheduleOnce(this);
                }
            }
        });

        Agent I = this;
        // wait a little more, then reset the agents.
        getSim().schedule.scheduleOnceIn(WAIT_TIME * 2 + SPIN_TIME,
                new Steppable() {
            @Override
            public void step(SimState state) {
                stop = false;
                sim.addDeath(I);
                location = new MutableDouble2D(startLocation);//go to the start place
                sim.getAgents().setObjectLocation(I, startLocation);
            }
        });
    }

    /**
     * Win as result of enter the portal when it's yours.
     */
    public void win() {
        stop = true;
        // okay we win the level. Let's do the little dance
        // clear out the schedule, we're done.
        getSim().schedule.clear();

        // do a little pause of me
        getSim().schedule.scheduleOnce(
                new Steppable() {
            public int count = 0;

            @Override
            public void step(SimState state) {
                if (++count < WAIT_TIME) {
                    getSim().schedule.scheduleOnce(this);
                }
            }
        });

        // do a little spin an decrease size
        getSim().schedule.scheduleOnceIn(WAIT_TIME,
                new Steppable() {
            private int count = 0;
            private final double decrease = 0.1;

            @Override
            public void step(SimState state) {
                if (count % SPIN_SPEED == 0) {
                    setLastAction((getLastAction() + 1) % 4);
                    if (scale >= decrease) {
                        scale -= decrease;
                    }
                }  // spin around
                if (++count < SPIN_TIME) {
                    getSim().schedule.scheduleOnce(this);
                }
            }
        });

        // wait a little more, then reset the agents.
        getSim().schedule.scheduleOnceIn(WAIT_TIME * 2 + SPIN_TIME,
                new Steppable() {
            @Override
            public void step(SimState state) {
                sim.winGame(Agent.this);
            }
        });
    }

    /**
     * Moves the ghost to the jail and sets him waiting. Resets his frightened
     * counter.
     */
    public void returnHome() {
        location = new MutableDouble2D(startLocation);
        //sim.getAgents().setObjectLocation(this, exitLocation);
        lastAction = W;
        stop = false;
    }

    public abstract Image getImage();

    public int getTeam() {
        return team;
    }

    public int getLastAction() {
        return lastAction;
    }

    protected void setLastAction(int lastAction) {
        this.lastAction = lastAction;
    }

    public MutableDouble2D getLocation() {
        return location;
    }

    public int getDiscretization() {
        return discretization;
    }

    protected void setDiscretization(int discretization) {
        this.discretization = discretization;
    }

    public DotsSimulation_Sim getSim() {
        return sim;
    }

    public double getScale() {
        return scale;
    }

    public boolean isStop() {
        return stop;
    }
}

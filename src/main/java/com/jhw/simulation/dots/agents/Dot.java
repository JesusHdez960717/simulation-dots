package com.jhw.simulation.dots.agents;

import com.jhw.simulation.dots.dots.DotsSimulation_Sim;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Double2D;

/**
 * Dots of the game.
 */
public class Dot implements Steppable {

    private static final long serialVersionUID = 1;

    // The DotsSimulation_Sim simulation state
    DotsSimulation_Sim sim;

    public Double2D location;

    private Agent owner = null;

    /**
     * The stoppable for this Player so he can remove himself when he dies if
     * it's multiplayer.
     */
    public Stoppable stopper;

    public Dot(DotsSimulation_Sim sim, Double2D startLocation) {
        this.location = startLocation;
        this.sim = sim;
        this.sim.getDots().setObjectLocation(this, startLocation);
        stopper = sim.schedule.scheduleRepeating(this, 0, 1);  // schedule at time 0
    }

    @Override
    public void step(SimState ss) {
        Bag nearby = sim.getAgents().getNeighborsExactlyWithinDistance(location, 0.1);
        if (nearby.size() == 1) {
            owner = (Player) nearby.get(0);
        } else if (nearby.size() > 1) {
            owner = null;
        }
    }

    public Agent getOwner() {
        return owner;
    }

}

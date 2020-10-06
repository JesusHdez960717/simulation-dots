/*
 Copyright 2009  by Sean Luke and Vittorio Zipparo
 Licensed under the Academic Free License version 3.0
 See the file "LICENSE" for more information
 */
package com.jhw.simulation.dots.agents;

import com.jhw.simulation.dots.sim.DotsSimulation_Sim;
import java.util.HashMap;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.Double2D;

/**
 * An empty class. Dots merely need to exist, not do anything.
 */
public class Portal implements Steppable {

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

    public Portal(DotsSimulation_Sim sim, Double2D startLocation) {
        this.location = startLocation;
        this.sim = sim;
        this.sim.getDots().setObjectLocation(this, startLocation);
        stopper = sim.schedule.scheduleRepeating(this, 0, 1);  // schedule at time 0
    }

    @Override
    public void step(SimState ss) {
        putOwner();
        teleportWinner();
    }

    public Agent getOwner() {
        return owner;
    }

    private void putOwner() {
        int maxFrec = 0;
        Agent ag = null;
        HashMap<Agent, Integer> h = new HashMap<>();
        Bag nearby = sim.getDots().getAllObjects();
        for (Object obj : nearby) {
            if (obj instanceof Dot) {
                Dot d = (Dot) obj;
                if (d.getOwner() != null) {
                    Agent ow = d.getOwner();
                    int frec = 1;
                    if (h.containsKey(ow)) {
                        frec = h.get(ow) + 1;
                    }

                    h.put(ow, frec);
                    if (frec > maxFrec) {
                        maxFrec = frec;
                        ag = ow;
                    }
                }
            }
        }

        int contMax = 0;
        for (Integer v : h.values()) {
            if (v == maxFrec) {
                contMax++;
            }
        }

        if (contMax == 1) {
            owner = ag;
        } else {
            owner = null;
        }

    }

    private void teleportWinner() {
        Bag nearby = sim.getAgents().getNeighborsExactlyWithinDistance(location, 0.1);
        for (Object obj : nearby) {
            Agent ag = (Agent) obj;
            if (ag == owner) {
                ag.win();
            }
        }
    }

}

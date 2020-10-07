/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.agents.powers;

import com.jhw.simulation.dots.sim.DotsSimulation_Sim;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

/**
 *
 * @author Yo
 */
public class PowerVisual implements Steppable {

    private final Double2D position;
    private final Power pow;
    private final long start;
    private final long duration = 1000;//duration of all powers
    private final DotsSimulation_Sim sim;

    public PowerVisual(DotsSimulation_Sim sim, Double2D position, Power pow) {
        this.sim = sim;
        this.position = position;
        this.pow = pow;
        this.start = sim.schedule.getSteps();
    }

    @Override
    public void step(SimState ss) {

    }

    public Power getPow() {
        return pow;
    }

}

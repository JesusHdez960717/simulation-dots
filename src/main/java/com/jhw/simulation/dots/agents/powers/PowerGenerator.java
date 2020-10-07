/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.agents.powers;

import com.jhw.simulation.dots.agents.powers.bosts.Cannon;
import com.jhw.simulation.dots.agents.powers.bosts.Defense;
import com.jhw.simulation.dots.agents.powers.bosts.Slow;
import com.jhw.simulation.dots.agents.powers.bosts.Speed;
import com.jhw.simulation.dots.agents.powers.bosts.Trap;
import com.jhw.simulation.dots.sim.DotsSimulation_Sim;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

/**
 *
 * @author Yo
 */
public class PowerGenerator implements Steppable {

    private final DotsSimulation_Sim sim;
    private final int cantAgents;
    public static final int CANT_POWERS = 1;

    public PowerGenerator(DotsSimulation_Sim sim) {
        this.sim = sim;
        cantAgents = sim.getIaPos().length + sim.getPlayersPos().length;
        this.sim.schedule.scheduleRepeating(this, 0, 1);  // schedule at time 0
    }

    @Override
    public void step(SimState ss) {
        if (sim.getPowers().allObjects.size() < cantAgents) {//si hay menos poderes que agentes genero 1
            generateRandomPower();
        }
    }

    private void generateRandomPower() {
        int pos = sim.random.nextInt();
        Double2D position = new Double2D(sim.getPortalPos()[pos][0], sim.getPortalPos()[pos][1]);
        Power p = getRandomPower();
        PowerVisual pv = new PowerVisual(sim, position, p);
    }

    private Power getRandomPower() {
        int wich = sim.random.nextInt();
        long steps = sim.schedule.getSteps();
        switch (wich) {
            case 0:
                return new Cannon(steps);
            case 1:
                return new Defense(steps);
            case 2:
                return new Slow(steps);
            case 3:
                return new Speed(steps);
            case 4:
                return new Trap(steps);
        }
        return null;
    }
}

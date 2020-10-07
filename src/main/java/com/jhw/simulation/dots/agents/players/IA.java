/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.agents.players;

import com.jhw.simulation.dots.sim.DotsSimulation_Sim;
import java.awt.Image;
import javax.swing.ImageIcon;
import sim.util.Double2D;

/**
 *
 * @author Yo
 */
public class IA extends Agent {

    private static final long serialVersionUID = 1;


    public IA(DotsSimulation_Sim sim, Double2D startLoc, int team) {
        super(sim, startLoc, team);
    }

    @Override
    public Image getImage() {
        switch (getLastAction()) {
            case Player.N:
                return new ImageIcon("media/icons/players/iau" + getTeam() + ".png").getImage();
            case Player.W:
                return new ImageIcon("media/icons/players/ial" + getTeam() + ".png").getImage();
            case Player.S:
                return new ImageIcon("media/icons/players/iad" + getTeam() + ".png").getImage();
            case Player.E:
                return new ImageIcon("media/icons/players/iar" + getTeam() + ".png").getImage();
            case Player.NOTHING:
                return new ImageIcon("media/icons/players/iad" + getTeam() + ".png").getImage();
        }
        return new ImageIcon("media/icons/frightened2.png").getImage();//nunca debe ocurrir
    }

}

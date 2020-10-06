/*
 Copyright 2009  by Sean Luke and Vittorio Zipparo
 Licensed under the Academic Free License version 3.0
 See the file "LICENSE" for more information
 */
package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.agents.Dot;
import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

/**
 * PlayerPortrayal draws the DotsSimulation_Sim as an Arc2D which changes its
 * angle depending on the current step of the game.
 */
public class DotsPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;

    public DotsPortrayal() {
        super(new ImageIcon("media/icons/help.png").getImage());//scale always 1
    }

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {        
        Dot d = (Dot) object;
        if (d.getOwner() == null) {
            image = new ImageIcon("media/icons/splash.png").getImage();
        } else {
            image = d.getOwner().getImage();
        }
        super.draw(object, g, info);
    }
}

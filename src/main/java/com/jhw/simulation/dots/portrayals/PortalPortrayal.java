/*
 Copyright 2009  by Sean Luke and Vittorio Zipparo
 Licensed under the Academic Free License version 3.0
 See the file "LICENSE" for more information
 */
package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.agents.Portal;
import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

/**
 * PlayerPortrayal draws the DotsSimulation_Sim as an Arc2D which changes its
 * angle depending on the current step of the game.
 */
public class PortalPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;
    private final float fixedScale = 3f;

    public PortalPortrayal() {
        super(new ImageIcon("media/icons/portal.gif"));//scale always 1
    }

    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        Portal p = (Portal) object;
        if (p.getOwner() == null) {
            image = new ImageIcon("media/icons/ghost.png").getImage();
        } else {
            image = new ImageIcon("media/icons/portal.gif").getImage();
        }
        scale = fixedScale;
        super.draw(object, g, info);
    }
}

package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.agents.powers.PowerVisual;
import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

public class PowerPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;

    public PowerPortrayal() {
        super(new ImageIcon("media/icons/lock.png"));//scale always 1
    }

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        PowerVisual pow = (PowerVisual) object;
        image = pow.getPow().getImage().getImage();
        super.draw(object, g, info);
    }
}

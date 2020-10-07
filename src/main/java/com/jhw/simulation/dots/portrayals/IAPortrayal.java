package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.agents.players.IA;
import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

public class IAPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;

    public IAPortrayal() {
        super(new ImageIcon("media/icons/p1.png"));//scale always 1
    }

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        IA p = (IA) object;
        scale = p.getScale();
        image = p.getImage();
        super.draw(object, g, info);
    }
}

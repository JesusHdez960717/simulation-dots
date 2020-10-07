package com.jhw.simulation.dots.portrayals;

import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

public class DeathPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;
    private final ImageIcon icon = new ImageIcon("media/icons/splash.png");
    private final float fixedScale = 1.5f;

    public DeathPortrayal() {
        super(new ImageIcon());
    }

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        image = icon.getImage();
        scale = fixedScale;
        super.draw(object, g, info);
    }
}

package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.agents.statics.Dot;
import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

public class DotsPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;
    private final Image none = new ImageIcon("media/icons/dots/none.png").getImage();
    private final float fixedScale = 2f;

    public DotsPortrayal() {
        super(new ImageIcon("media/icons/help.png").getImage());//scale always 1
    }

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        Dot d = (Dot) object;
        if (d.getOwner() == null) {
            image = none;
        } else {
            image = new ImageIcon("media/icons/dots/d" + d.getOwner().getTeam() + ".png").getImage();
        }
        scale = fixedScale;
        super.draw(object, g, info);
    }
}

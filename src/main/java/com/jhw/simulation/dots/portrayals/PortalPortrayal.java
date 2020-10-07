package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.agents.statics.Portal;
import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

public class PortalPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;
    //private final Image desact = new ImageIcon("media/icons/11.gif").getImage();
    private final Image desact = new ImageIcon("media/icons/portals/desactivated_portal.gif").getImage();
    private final float fixedScale = 3f;

    public PortalPortrayal() {
        super(new ImageIcon());//scale always 1
    }

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        Portal p = (Portal) object;
        if (p.getOwner() == null) {
            image = desact;
        } else {
            image = new ImageIcon("media/icons/portals/portal" + p.getOwner().getTeam() + ".gif").getImage();
        }
        scale = fixedScale;
        super.draw(object, g, info);
    }
}

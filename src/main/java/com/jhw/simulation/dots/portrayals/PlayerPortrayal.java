package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.agents.players.Player;
import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

public class PlayerPortrayal extends ImagePortrayal2D {

    private static final long serialVersionUID = 1;

    public PlayerPortrayal() {
        super(new ImageIcon());//scale always 1
    }

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        Player p = (Player) object;
        scale = p.getScale();
        image = p.getImage();
        super.draw(object, g, info);
    }
}

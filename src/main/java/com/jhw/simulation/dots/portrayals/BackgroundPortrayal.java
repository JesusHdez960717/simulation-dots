package com.jhw.simulation.dots.portrayals;

import java.awt.*;
import javax.swing.ImageIcon;
import sim.portrayal.*;
import sim.portrayal.simple.ImagePortrayal2D;

public class BackgroundPortrayal extends FieldPortrayal2D {

    private static final long serialVersionUID = 1;

    @Override
    public void draw(Object object, Graphics2D g, DrawInfo2D info) {
        ImageIcon back = new ImageIcon("media/icons/back.gif");
        Rectangle clip = g.getClipBounds();
        Color alphaWhite = new Color(1.0f, 1.0f, 1.0f, 0.65f);
        g.setColor(alphaWhite);
        g.fillRect(clip.x, clip.y, clip.width, clip.height);
        
        //g.drawImage(back.getImage(), 0, 0, clip.width, clip.height, null);

        //super.draw(object, g, info);
    }
}

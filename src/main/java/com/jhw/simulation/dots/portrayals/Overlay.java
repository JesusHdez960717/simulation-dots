package com.jhw.simulation.dots.portrayals;

import com.jhw.simulation.dots.sim.DotsSimulation_Sim;
import com.jhw.simulation.dots.sim.DotsSimulation_UI;
import sim.portrayal.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import sim.display.*;

/**
 * A FieldPortrayal2D which has no field, but rather draws some text on the
 * screen. We use this to draw score information etc.
 */
public class Overlay extends FieldPortrayal2D {

    private static final long serialVersionUID = 1;

    DotsSimulation_UI ui;
    public static final int GUTTER = 32;
    public static final int BORDER = 8;
    Font font = new Font("SansSerif", Font.BOLD, 18);
    Color color = new Color(33, 33, 222);

    public Overlay(DotsSimulation_UI ui) {
        this.ui = ui;
    }

    int firstTimeScoreY = 0;  // surprisingly, the bounds height doesn't stay the same, so we store it here

    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
        DotsSimulation_Sim pacman = (DotsSimulation_Sim) (ui.state);
        graphics.setFont(font);
        graphics.setColor(Color.ORANGE);

        // first figure out the Y location for the scores etc.  This is done ONCE and stored away because
        // the font numbers sometimes don't have the same bounding box vertically, causing the text to jump about.
        Rectangle2D bounds = new TextLayout("" + pacman.getScore(), font, graphics.getFontRenderContext()).getBounds();
        //if (firstTimeScoreY == 0) {
        firstTimeScoreY = (int) ((GUTTER + bounds.getHeight()) / 2);
        //}

        // if we're paused, say so
        if (((SimpleController) (ui.controller)).getPlayState() == SimpleController.PS_PAUSED) {
            bounds = new TextLayout("Paused", font, graphics.getFontRenderContext()).getBounds();
            graphics.drawString("Paused", (int) ((info.clip.width - bounds.getWidth()) / 2), firstTimeScoreY);
        } else // otherwise, show the scores
        {
            graphics.drawString("Deaths: " + pacman.getDeaths(), BORDER, firstTimeScoreY);
            graphics.drawString("Level: " + pacman.getLevel(), (int) ((info.clip.width - BORDER * 2) * 1 / 3 + BORDER), firstTimeScoreY);
            graphics.drawString("Score: " + pacman.getScore(), (int) ((info.clip.width - BORDER * 2) * 2 / 3 + BORDER), firstTimeScoreY);
        }

        // show the text at bottom
        String text = "Esc: Exit   P: Pause   R: Reset   Controls: \u2190\u2192\u2191\u2193";
        bounds = new TextLayout(text, font, graphics.getFontRenderContext()).getBounds();
        graphics.drawString(text, (int) ((info.clip.width - bounds.getWidth()) / 2),
                (int) ((info.clip.height - GUTTER + firstTimeScoreY)));
    }
}

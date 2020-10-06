/*
 Copyright 2006 by Sean Luke and George Mason University
 Licensed under the Academic Free License version 3.0
 See the file "LICENSE" for more information
 */
package com.jhw.simulation.dots.dots;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import sim.display.*;
import sim.engine.*;
import sim.portrayal.continuous.*;
import sim.portrayal.grid.*;
import com.jhw.simulation.dots.agents.Dot;
import com.jhw.simulation.dots.agents.IA;
import com.jhw.simulation.dots.agents.Player;
import com.jhw.simulation.dots.agents.Portal;
import com.jhw.simulation.dots.main.dots_Main;
import com.jhw.simulation.dots.portrayals.DotsPortrayal;
import com.jhw.simulation.dots.portrayals.IAPortrayal;
import com.jhw.simulation.dots.portrayals.MazeCellPortrayal;
import com.jhw.simulation.dots.portrayals.Overlay;
import com.jhw.simulation.dots.portrayals.PlayerPortrayal;
import com.jhw.simulation.dots.portrayals.PortalPortrayal;
import com.jhw.simulation.dots.utils.Utility_Class;
import com.jhw.simulation.dots.visual.Index_Panel;

/**
 * Creates the UI for the DotsSimulation_Sim game.
 */
public class DotsSimulation_UI extends GUIState {

    /**
     * The desired FPS
     */
    public static double FRAMES_PER_SECOND = 60;
    private Display2D display;

    private final ValueGridPortrayal2D mazePortrayal = new ValueGridPortrayal2D();
    private final ContinuousPortrayal2D agentPortrayal = new ContinuousPortrayal2D();
    private final ContinuousPortrayal2D dotPortrayal = new ContinuousPortrayal2D();

    private final Dimension dim;

    public DotsSimulation_UI(int level, Dimension screen) throws Exception {
        this(new DotsSimulation_Sim(System.currentTimeMillis(), level), screen);
    }

    public DotsSimulation_UI(SimState state, Dimension screen) {
        super(state);
        this.dim = screen;
        createController();
    }

    public Display2D getDisplay() {
        return display;
    }

    /**
     * Creates a SimpleController and starts it playing.
     *
     * @return
     */
    @Override
    public Controller createController() {
        SimpleController c = new SimpleController(this);
        c.pressPlay();
        return c;
    }

    public static String getName() {
        return "Dots 1.0";
    }

    @Override
    public void start() {
        super.start();
        setupPortrayals();
    }

    /**
     *
     * @param state
     */
    @Override
    public void load(SimState state) {
        super.load(state);
        setupPortrayals();
    }

    public void setupPortrayals() {
        DotsSimulation_Sim sim = (DotsSimulation_Sim) state;

        // Create the agent portrayal 
        agentPortrayal.setField(sim.getAgents());

        agentPortrayal.setPortrayalForClass(Player.class, new PlayerPortrayal());

        agentPortrayal.setPortrayalForClass(IA.class, new IAPortrayal());

        // Create the dot portrayal (also the energizers)
        dotPortrayal.setField(sim.getDots());

        // dots are small
        dotPortrayal.setPortrayalForClass(Dot.class, new DotsPortrayal());

        // portal are bigger
        dotPortrayal.setPortrayalForClass(Portal.class, new PortalPortrayal());

        // set up the maze portrayal
        mazePortrayal.setField(sim.getMaze());
        mazePortrayal.setPortrayalForAll(new MazeCellPortrayal(sim.getMaze()));

        // add the RateAdjuster
        scheduleRepeatingImmediatelyAfter(new RateAdjuster(FRAMES_PER_SECOND));

        // reschedule the displayer
        display.reset();

        // redraw the display
        display.repaint();
    }

    @Override
    public void init(final Controller c) {
        super.init(c);
        try {
            // make the displayer
            Dimension dim = getAdjustedDimension();
            //display = new Display2D(28 * 20, 35 * 20, this) {//standar 16x16 pix  28 x 35
            display = new Display2D(dim.width, dim.height, this) {//standar 16x16 pix  28 x 35
                public void createConsoleMenu() {
                }

                public void quit() {
                    super.quit();
                    ((SimpleController) c).doClose();
                }
            };
            c.registerFrame(dots_Main.getFrame());   // register the frame so it appears in the "Display" list

            display.insideDisplay.setOpaque(false);
            display.setBackdrop(Color.black);

            // Notice the order: first the background, then the dots, then the agents, then the overlay
            display.attach(mazePortrayal, "Maze");
            // display.attach( background, "Background");
            display.attach(dotPortrayal, "Dots", 8, 8, true);
            display.attach(agentPortrayal, "Agents", 8, 8, true);
            display.attach(new Overlay(this), "Overlay");

            // Some stuff to make this feel less like MASON
            // delete the header
            display.remove(display.header);
            // delete all listeners
            display.removeListeners();
            // delete the scroll bars
            display.display.setVerticalScrollBarPolicy(display.display.VERTICAL_SCROLLBAR_NEVER);
            display.display.setHorizontalScrollBarPolicy(display.display.HORIZONTAL_SCROLLBAR_NEVER);

            // add antialiasing and interpolation
            display.insideDisplay.setupHints(true, false, false);

            // Now we add in the listeners we want
            addListeners();
        } catch (Exception e) {
            e.printStackTrace();
            Utility_Class.jopError(e.getMessage());
        }
    }

    /**
     * Creates key listeners which issue requests to the simulation.
     */
    public void addListeners() {
        final DotsSimulation_Sim pacman = (DotsSimulation_Sim) state;
        final SimpleController cont = (SimpleController) controller;

        // Make us able to take focus -- this is by default true usually anyway
        display.setFocusable(true);

        // Make us request focus whenever our window comes up
        dots_Main.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                display.requestFocusInWindow();
            }
        });

        // the display frame has just been set visible so we need to request focus once
        display.requestFocusInWindow();

        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                switch (c) {
                    case KeyEvent.VK_UP:
                        pacman.getActions()[0] = Player.N;
                        break;
                    case KeyEvent.VK_DOWN:
                        pacman.getActions()[0] = Player.S;
                        break;
                    case KeyEvent.VK_LEFT:
                        pacman.getActions()[0] = Player.W;
                        break;
                    case KeyEvent.VK_RIGHT:
                        pacman.getActions()[0] = Player.E;
                        break;
                    case KeyEvent.VK_W:
                        pacman.getActions()[1] = Player.N;
                        break;
                    case KeyEvent.VK_S:
                        pacman.getActions()[1] = Player.S;
                        break;
                    case KeyEvent.VK_A:
                        pacman.getActions()[1] = Player.W;
                        break;
                    case KeyEvent.VK_D:
                        pacman.getActions()[1] = Player.E;
                        break;
                    case KeyEvent.VK_R:             // Reset the board.  Easiest way: stop and play, which calls start()
                        pressPause();
                        if (Utility_Class.jopContinue("Desea reiniciar el nivel, se perdera todo el progreso.")) {
                            pressStop();
                            pressPlay();
                        } else {
                            pressPause();
                        }
                        break;
                    case KeyEvent.VK_P:             // Pause or unpause the game
                        pressPause();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        actionScape(cont);
                        break;
                    default:
                        // do nothing
                        break;
                }
            }

        });
    }

    private void actionScape(SimpleController cont) {
        cont.pressPause();
        if (Utility_Class.jopContinue("Desea salir del juego. Se perderá el progreso actual.")) {
            cont.pressStop();
            dots_Main.changePanel(new Index_Panel());
        } else {
            cont.pressPause();
        }
    }

    public void pressPause() {
        final SimpleController cont = (SimpleController) controller;
        cont.pressPause();
    }

    public void pressPlay() {
        final SimpleController cont = (SimpleController) controller;
        cont.pressPlay();
    }

    public void pressStop() {
        final SimpleController cont = (SimpleController) controller;
        cont.pressStop();
    }

    @Override
    public void quit() {
        super.quit();
        display = null;
    }

    private Dimension getAdjustedDimension() throws IOException {
        int grid[][] = ((DotsSimulation_Sim) state).getGrid();
        int width = grid.length;
        int height = grid[0].length;
        Dimension screen = dim;
        int propWidth = (screen.width) / width;
        int propHeight = (screen.height - 50) / height;
        if (propHeight >= propWidth) {//width
            return new Dimension(propWidth * width, propWidth * height);
        } else {//he
            return new Dimension(propHeight * width, propHeight * height);
        }
    }

}

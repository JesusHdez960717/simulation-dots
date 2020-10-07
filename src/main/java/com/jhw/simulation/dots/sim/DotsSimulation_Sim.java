/*
 Copyright 2009  by Sean Luke and Vittorio Zipparo
 Licensed under the Academic Free License version 3.0
 See the file "LICENSE" for more information
 */
package com.jhw.simulation.dots.sim;

import com.jhw.simulation.dots.agents.others.Death;
import com.jhw.simulation.dots.agents.players.Agent;
import com.jhw.simulation.dots.agents.players.IA;
import com.jhw.simulation.dots.agents.players.Player;
import com.jhw.simulation.dots.agents.powers.PowerGenerator;
import com.jhw.simulation.dots.agents.statics.Dot;
import com.jhw.simulation.dots.agents.statics.Portal;
import com.jhw.simulation.dots.services.NavigationService;
import com.jhw.simulation.dots.services.ProgressService;
import com.jhw.simulation.dots.utils.GridReader;
import com.jhw.simulation.dots.utils.Utility_Class;
import java.io.*;
import sim.engine.*;
import sim.field.continuous.*;
import sim.field.grid.*;
import sim.util.*;

/**
 * DotsSimulation_Sim is the model for the game. The model contains three
 * fields: a Continuous2D for the agents, a Continuous2D for the dots, and an
 * IntGrid2D holding the maze (1 is wall, 0 is open space). The model holds an
 * array of "actions", one per player, in case we want to make this a
 * multiplayer game.
 *
 * <p>
 * Note that you can easily modify this code to have different kinds of Pacs
 * (internally we have invented AI Pacs. :-). Also if you just want one sim, not
 * two, change the Pacs array to be of size 1 (see the code below).
 */
public class DotsSimulation_Sim extends SimState {

    private static final long serialVersionUID = 1;

    /**
     * Holds the IA and the players.
     */
    private Continuous2D agents;

    /**
     * The players
     */
    private Player[] playersArr;

    /**
     * The IA
     */
    private IA[] iaArr;

    /**
     * Holds Dots, Powers and Portal.
     */
    private Continuous2D dots;

    /**
     * Hold the dots
     */
    private Dot[] dotsArr;

    /**
     * Hold the portals
     */
    private Portal[] portalsArr;

    /**
     * Holds Dots, Powers and Portal.
     */
    private Continuous2D powers;

    /**
     * The maze proper.
     */
    private IntGrid2D maze;

    /**
     * Desired actions from the user. Presently only actions[0] used.
     */
    private int[] actions;

    private PowerGenerator pw;
    /**
     * The current level.
     */
    private int level = 1;

    /**
     * The number of deaths so far.
     */
    private int deaths = 0;

    /**
     * The current score.
     */
    private int score = 0;

    private int[][] grid;
    private int[][] iaPos;
    private int[][] playersPos;
    private int[][] dotsPos;
    private int[][] portalPos;
    private int[][] powerPos;

    /**
     * Creates a Dots simulation with the given random number seed and level.
     */
    public DotsSimulation_Sim(long seed, int level) throws Exception {
        super(seed);
        this.level = level;
        this.load();
    }

    private void load() throws FileNotFoundException {
        String url = "levels/" + level + "/";
        grid = GridReader.readMaze(url + "maze.txt");
        dotsPos = GridReader.readCoordinates(url + "dots.txt");
        iaPos = GridReader.readCoordinates(url + "ia.txt");
        playersPos = GridReader.readCoordinates(url + "player.txt");
        portalPos = GridReader.readCoordinates(url + "portal.txt");
        powerPos = GridReader.readCoordinates(url + "power.txt");
    }

    /**
     * Resets the scores, loads the maze, creates the fields, adds the dots and
     * energizers, and resets the Player and Ghosts.
     */
    @Override
    public void start() {
        super.start();

        deaths = 0;
        score = 0;
        maze = new IntGrid2D(0, 0);
        maze.setTo(grid);

        agents = new Continuous2D(1.0, maze.getWidth(), maze.getHeight());
        dots = new Continuous2D(1.0, maze.getWidth(), maze.getHeight());
        powers = new Continuous2D(1.0, maze.getWidth(), maze.getHeight());

        resetGame();
    }

    /**
     * Resets the game board. Doesn't change the score or deaths or level number
     */
    public void resetGame() {
        dots.clear();
        schedule.clear();

        // add Dots
        dotsArr = new Dot[dotsPos.length];
        for (int i = 0; i < dotsPos.length; i++) {
            dotsArr[i] = new Dot(this, new Double2D(dotsPos[i][0], dotsPos[i][1]));
        }

        // add portal
        portalsArr = new Portal[portalPos.length];
        for (int i = 0; i < portalPos.length; i++) {
            portalsArr[i] = new Portal(this, new Double2D(portalPos[i][0], portalPos[i][1]));
        }

        resetAgents();
    }

    /**
     * Puts the agents back to their regular locations, and clears the schedule.
     */
    public void resetAgents() {
        agents.clear();
        powers.clear();

        iaArr = new IA[iaPos.length];
        for (int i = 0; i < iaPos.length; i++) {
            iaArr[i] = new IA(this, new Double2D(iaPos[i][0], iaPos[i][1]), iaPos[i][2]);
        }

        playersArr = new Player[playersPos.length];

        actions = new int[playersArr.length];
        for (int i = 0; i < playersArr.length; i++) {
            actions[i] = Agent.NOTHING;
        }

        for (int i = 0; i < playersArr.length; i++) {
            playersArr[i] = new Player(this, i, new Double2D(playersPos[i][0], playersPos[i][1]), playersPos[i][2]);
        }

//        pw = new PowerGenerator(this);
    }

    public Player pacClosestTo(MutableDouble2D location) {
        if (playersArr.length == 1) {
            return playersArr[0];
        }
        Player best = null;
        int count = 1;
        for (Player p : playersArr) {
            if (p != null) {
                if (best == null || (best.getLocation().distanceSq(location) > p.getLocation().distanceSq(location) && ((count = 1) == 1) || best.getLocation().distanceSq(location) == p.getLocation().distanceSq(location) && random.nextBoolean(1.0 / (++count)))) {
                    best = p;
                }
            }
        }
        return best;
    }

    /**
     * Returns the desired user action.
     */
    public int getNextAction(int tag) {
        return actions[tag];
    }

    public Continuous2D getAgents() {
        return agents;
    }

    public Continuous2D getDots() {
        return dots;
    }

    public IntGrid2D getMaze() {
        return maze;
    }

    public void setMaze(IntGrid2D maze) {
        this.maze = maze;
    }

    public int[] getActions() {
        return actions;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public Player[] getPacsArr() {
        return playersArr;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[][] getIaPos() {
        return iaPos;
    }

    public int[][] getPlayersPos() {
        return playersPos;
    }

    public int[][] getDotsPos() {
        return dotsPos;
    }

    public int[][] getPortalPos() {
        return portalPos;
    }

    public Continuous2D getPowers() {
        return powers;
    }

    public void winGame(Agent ag) {
        if (ag instanceof Player) {//you win the game
            Utility_Class.jopHelp("Felicidades, ha ganado el nivel.");
            ProgressService.progress.winLevel(level);
            NavigationService.navigateTo(NavigationService.LEVEL_SELECTOR);
        } else {//the IA win the game
            resetGame();
        }
    }

    public void addDeath(Agent a) {
        this.dots.setObjectLocation(new Death(), new Double2D(a.getLocation()));
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.controllers;

import com.jhw.simulation.dots.dots.DotsSimulation_UI;
import com.jhw.simulation.dots.main.Main;
import com.jhw.simulation.dots.services.ConfigurationService;
import com.jhw.simulation.dots.services.MusicService;
import javax.swing.*;

/**
 *
 * @author Yo
 */
public class GameController {

    private final String name;
    private final String song = "media/audio/principal.wav";
    private final String background;

    private final DotsSimulation_UI sim;

    public GameController(int level) throws Exception {
        name = "Level " + level;
        //song = "levels/" + level + "/song.wav";
        background = "levels/" + level + "/background.jpg";
        MusicService.playSong(song);
        sim = new DotsSimulation_UI(level, ConfigurationService.screenSize);
    }

    public JComponent getDisplay() {
        return sim.getDisplay();
    }

    public DotsSimulation_UI getSim() {
        return sim;
    }

    public String getName() {
        return name;
    }

    public String getSong() {
        return song;
    }

    public String getBackgroundUrl() {
        return background;
    }

    public ImageIcon getBackgroundImage() {
        return new ImageIcon(background);
    }

}

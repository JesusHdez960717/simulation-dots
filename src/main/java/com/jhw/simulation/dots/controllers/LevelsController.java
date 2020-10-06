/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.controllers;

import com.jhw.simulation.dots.utils.Progress_Class;
import com.jhw.simulation.dots.main.Main;
import com.jhw.simulation.dots.services.MusicService;
import com.jhw.simulation.dots.services.NavigationService;
import com.jhw.simulation.dots.services.ProgressService;
import javax.swing.ImageIcon;

/**
 *
 * @author Yo
 */
public class LevelsController {

    private final String name = "Levels";
    private final String song = "media/audio/levels.wav";
    private final String background = "media/pictures/background.jpg";

    public LevelsController() {
        MusicService.playSong(song);
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

    public int getAvatarIndex() {
        return ProgressService.getLevel() - 1;
    }

    public void actionBack() {
        NavigationService.backFrom(NavigationService.LEVEL_SELECTOR);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.controllers;

import com.jhw.simulation.dots.main.Main;
import com.jhw.simulation.dots.services.MusicService;
import com.jhw.simulation.dots.services.NavigationService;
import com.jhw.simulation.dots.services.ProgressService;
import com.jhw.simulation.dots.utils.Utility_Class;
import javax.swing.ImageIcon;

/**
 *
 * @author Yo
 */
public class IndexController {

    private final String name = "Principal";
    private final String song = "media/audio/principal.wav";
    private final String background = "media/pictures/background.jpg";

    public IndexController() {
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

    public void actionPlay() {
        int level = ProgressService.getLevel();
        if (Utility_Class.jopContinue("Desea jugar el nivel " + level)) {
            NavigationService.navigateToLevel(level);
        }
    }

    public void actionLevels() {
        NavigationService.navigateTo(NavigationService.LEVEL_SELECTOR);
    }

    public void actionQuit() {
        if (Utility_Class.jopContinue("Seguro desea salir???")) {
            System.exit(0);
        }
    }
}

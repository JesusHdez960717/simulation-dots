/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.controllers;

import com.jhw.simulation.dots.main.Main;
import com.jhw.simulation.dots.utils.Utility_Class;
import com.jhw.simulation.dots.visual.GameView_Panel;
import com.jhw.simulation.dots.visual.LevelsView_Panel;
import javax.swing.ImageIcon;

/**
 *
 * @author Yo
 */
public class Index_Controller {

    private final String name = "Principal";
    private final String song = "media/audio/principal.wav";
    private final String background = "media/pictures/background.jpg";

    public Index_Controller() {
        Main.changeAudio(song);
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
        try {
            if (Utility_Class.jopContinue("Desea jugar el nivel " + Main.progress.getLevel())) {
                Main.changePanel(new GameView_Panel());
                Main.hideCursor();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utility_Class.jopError("Error cargando el nivel. " + e.getMessage());
        }
    }

    public void actionLevels() {
        Main.changePanel(new LevelsView_Panel());
        Main.hideCursor();
    }

    public void actionQuit() {
        if (Utility_Class.jopContinue("Seguro desea salir???")) {
            Main.close();
        }
    }

    public void showCursor() {
        Main.showCursor();
    }
}

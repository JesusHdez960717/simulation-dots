/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.controllers;

import com.jhw.simulation.dots.dots_Main;
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
        dots_Main.changeAudio(song);
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
            if (Utility_Class.jopContinue("Desea jugar el nivel " + dots_Main.progress.getLevel())) {
                dots_Main.changePanel(new GameView_Panel());
                dots_Main.hideCursor();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utility_Class.jopError("Error cargando el nivel. " + e.getMessage());
        }
    }

    public void actionLevels() {
        dots_Main.changePanel(new LevelsView_Panel());
        dots_Main.hideCursor();
    }

    public void actionQuit() {
        if (Utility_Class.jopContinue("Seguro desea salir???")) {
            dots_Main.close();
        }
    }

    public void showCursor() {
        dots_Main.showCursor();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.controllers;

import com.jhw.simulation.dots.main.Progress_Class;
import com.jhw.simulation.dots.main.Main;
import com.jhw.simulation.dots.services.NavigationService;
import com.jhw.simulation.dots.utils.Utility_Class;
import com.jhw.swing.material.components.container.panel._PanelAvatarChooser;
import com.jhw.swing.util.AvatarPanelAvatarChooser;
import java.util.ArrayList;
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

    private Progress_Class getProgress() {
        return Main.progress;
    }

    public int getAvatarIndex() {
        return getProgress().getLevel() - 1;
    }

    public void setAvatars(_PanelAvatarChooser panelAvatarChooser) {
        int maxAv = getProgress().getMaxLevels();
        int actual = getProgress().getLevel();
        ArrayList<AvatarPanelAvatarChooser> avatar = new ArrayList<>(maxAv);
        for (int i = 1; i <= actual; i++) {
            avatar.add(new AvatarPanelAvatarChooser("" + i, "Level " + i, "levels/" + i + "/icon.png"));
        }

        for (int i = actual + 1; i <= maxAv; i++) {
            avatar.add(new AvatarPanelAvatarChooser("" + i, "Level " + i, "media/icons/lock.png"));
        }

        panelAvatarChooser.setAvatars(avatar);
    }

    public void actionSelectLevel(_PanelAvatarChooser panelAvatarChooser) {
        try {
            AvatarPanelAvatarChooser av = panelAvatarChooser.getSelectedAvatar();
            int level = Integer.parseInt(av.getId());
            if (level <= getProgress().getLevel()) {//acces the level
                if (Utility_Class.jopContinue("Desea jugar el nivel " + level)) {
                    NavigationService.navigateToLevel(level);
                }
            }
        } catch (Exception e) {
            Utility_Class.jopError("Error seleccionando el nivel. " + e.getMessage());
        }
    }

    public void actionBack() {
        NavigationService.backFrom(NavigationService.LEVEL_SELECTOR);
    }
}

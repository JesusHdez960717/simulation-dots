/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.main;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Yo
 */
public class Progress_Class implements Serializable {

    public static String URL_SALVAS = "progress.dat";
    private int level = 1;
    private int maxLevels = setMaxLevels();

    public Progress_Class() {
    }

    public Progress_Class(int lastLevelWin) {
        this.level = lastLevelWin;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevels() {
        return maxLevels;
    }

    public void winLastLevel() {
        int next = level + 1;
        if (next <= maxLevels) {//validate that not pass the last level
            this.level = next;
        }
        dots_Main.saveGame();//save the progress
    }

    public void winLevel(int level) {
        if (level + 1 > this.level && level <= maxLevels) {//validate that not pass the last level
            this.level = level + 1;
        }
        dots_Main.saveGame();//save the progress
    }

    private int setMaxLevels() {
        return new File("levels").list().length;
    }

    public float getProgress() {
        return level * 100 / maxLevels;
    }

}

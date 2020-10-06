/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.services;

import com.jhw.simulation.dots.utils.Progress_Class;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class ProgressService {

    public static Progress_Class progress = new Progress_Class();

    public static int getLevel() {
        return progress.getLevel();
    }

    public static int getMaxLevels() {
        return progress.getMaxLevels();
    }

    public static void loadGame() {
        try {
            FileInputStream fis = new FileInputStream(Progress_Class.URL_SALVAS);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Progress_Class a = (Progress_Class) ois.readObject();
            if (a == null) {
                a = new Progress_Class();
            } else {
                progress = a;
            }
            fis.close();
            ois.close();

        } catch (Exception e) {
            saveGame();
        }
    }

    public static void saveGame() {
        try {
            FileOutputStream fos = new FileOutputStream(Progress_Class.URL_SALVAS);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(progress);
            oos.close();
            fos.close();
        } catch (Exception e) {
        }
    }
}

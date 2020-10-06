/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.main;

import com.jhw.simulation.dots.services.NavigationService;
import com.jhw.simulation.dots.utils.Configuration_Class;
import com.jhw.simulation.dots.utils.WaveFile;
import com.jhw.swing.ui.MaterialLookAndFeel;
import java.io.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Yo
 */
public class Main {

    public static Configuration_Class cfg = new Configuration_Class();
    public static Progress_Class progress = new Progress_Class();
    private static WaveFile audio;

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MaterialLookAndFeel());
        loadGame();
        NavigationService.navigateTo(NavigationService.INDEX);
    }

    public static int getAudioAmplitude() {
        return audio.getSampleInt(audio.getClip().getFramePosition());
    }

    public static void changeAudio(String url) {
        try {
            if (audio != null) {
                audio.stop();
                audio = null;
            }
            audio = new WaveFile(new File(url));
            //audio.play();
        } catch (Exception e) {
        }
    }

    public static void close() {
        System.exit(0);
    }

    private static void loadGame() {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.main;

import com.jhw.simulation.dots.utils.Configuration_Class;
import com.jhw.simulation.dots.utils.WaveFile;
import com.jhw.simulation.dots.visual.Index_Panel;
import com.jhw.simulation.dots.visual.MainView_UI;
import com.jhw.swing.material.components.container.MaterialContainersFactory;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javafx.scene.control.CheckBox;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Yo
 */
public class Main {

    public static Configuration_Class cfg = new Configuration_Class();
    public static Progress_Class progress = new Progress_Class();
    private static WaveFile audio;
    private static MainView_UI main;

    public static void main(String[] args) {
        loadGame();
        main = new MainView_UI();
        changePanel(new Index_Panel());
        //new DotsSimulation_UI1().createController();
    }

    public static void changePanel(JComponent panel) {
        main.changePanel(panel);
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

    public static JFrame getFrame() {
        return main;
    }

    public static void close() {
        main.dispose();
        System.exit(0);
    }

    public static void hideCursor() {
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        main.getContentPane().setCursor(blankCursor);
    }

    public static void showCursor() {
        getFrame().getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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

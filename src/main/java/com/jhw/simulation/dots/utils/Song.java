package com.jhw.simulation.dots.utils;

import java.io.File;
import javax.sound.sampled.*;

/**
 *
 * @author TGD
 */
public class Song {

    private Clip Audio;

    public Song() {
    }

    public void load(String url) {
        try {
            stop();
            Audio = AudioSystem.getClip();
            File sonido = new File(url);
            AudioInputStream A = AudioSystem.getAudioInputStream(sonido);
            Audio.open(A);
            start();
        } catch (Exception e) {
        }
    }

    public void start() {
        try {
            stop();
            Audio.start();
            Audio.loop(Integer.MAX_VALUE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        try {
            Audio.stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

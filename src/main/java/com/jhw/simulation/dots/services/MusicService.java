/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.services;

import com.jhw.simulation.dots.utils.WaveFile;
import java.io.File;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class MusicService {

    private static WaveFile audio;

    public static int getAudioAmplitude() {
        return audio.getSampleInt(audio.getClip().getFramePosition());
    }

    public static void playSong(String url) {
        try {
            if (audio != null) {
                audio.stop();
                audio = null;
            }
            audio = new WaveFile(new File(url));
            audio.play();//este es el que le da play de verdad
        } catch (Exception e) {
        }
    }

}

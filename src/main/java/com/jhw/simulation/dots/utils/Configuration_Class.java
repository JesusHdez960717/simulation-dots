/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Yo
 */
public class Configuration_Class {

    private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public Configuration_Class() {
    }

    public Dimension getScreenSize() {
        return screen;
    }

}

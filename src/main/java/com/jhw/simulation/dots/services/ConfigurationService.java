/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.services;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Yo
 */
public class ConfigurationService {

    public static final Dimension screenSize;

    static {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }
}

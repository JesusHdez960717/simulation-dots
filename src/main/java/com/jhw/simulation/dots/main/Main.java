/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.main;

import com.jhw.simulation.dots.services.NavigationService;
import com.jhw.simulation.dots.services.ProgressService;
import com.jhw.swing.ui.MaterialLookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Yo
 */
public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MaterialLookAndFeel());
        ProgressService.loadGame();
        NavigationService.navigateTo(NavigationService.INDEX);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.visual;

import com.jhw.simulation.dots.controllers.IndexController;
import com.jhw.swing.material.components.container.panel._PanelGradient;
import java.awt.GridLayout;

/**
 *
 * @author Yo
 */
public class Index_Panel extends _PanelGradient {

    private final IndexController con = new IndexController();

    public Index_Panel() {
        initComponents();
        this.setName(con.getName());
        this.setIcon(con.getBackgroundImage());
    }

    private void initComponents() {
        buttonPlay = new javax.swing.JButton();
        buttonLevels = new javax.swing.JButton();
        buttonQuit = new javax.swing.JButton();

        buttonPlay.setText("play");
        buttonPlay.addActionListener((java.awt.event.ActionEvent evt) -> {
            con.actionPlay();
        });

        buttonLevels.setText("levels");
        buttonLevels.addActionListener((java.awt.event.ActionEvent evt) -> {
            con.actionLevels();
        });

        buttonQuit.setText("quit");
        buttonQuit.addActionListener((java.awt.event.ActionEvent evt) -> {
            con.actionQuit();
        });
        this.setLayout(new GridLayout(3, 1));
        this.add(buttonPlay);
        this.add(buttonLevels);
        this.add(buttonQuit);
    }

    private javax.swing.JButton buttonPlay;
    private javax.swing.JButton buttonLevels;
    private javax.swing.JButton buttonQuit;

}

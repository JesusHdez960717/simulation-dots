/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.visual;

import com.jhw.simulation.dots.controllers.Index_Controller;
import com.jhw.swing.material.components.container.MaterialContainersFactory;
import com.jhw.swing.material.components.container.panel.MaterialPanelBorder;

/**
 *
 * @author Yo
 */
public class Index_Panel extends javax.swing.JPanel {

    private final Index_Controller con = new Index_Controller();

    public Index_Panel() {
        initComponents();
        this.setName(con.getName());
        this.panelBackground.setIcon(con.getBackgroundImage());
        this.con.showCursor();
    }

    private void initComponents() {
        panelBackground = MaterialContainersFactory.buildPanelGradient();
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

    }

    private javax.swing.JButton buttonPlay;
    private javax.swing.JButton buttonLevels;
    private javax.swing.JButton buttonQuit;
    private MaterialPanelBorder panelBackground;

}

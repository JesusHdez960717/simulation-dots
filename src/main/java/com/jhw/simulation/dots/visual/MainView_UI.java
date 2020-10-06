/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.visual;

import com.jhw.swing.material.components.container.MaterialContainersFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Yo
 */
public class MainView_UI extends javax.swing.JFrame {

    /**
     * Creates new form Game_UI
     */
    public MainView_UI() {
        initComponents();
        //this.setSize(dots_Main.cfg.getScreenSize());
        this.setSize(new Dimension(800, 500));
        //changePanel(new Index_Panel());
        this.setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dots 1.0");
        setUndecorated(true);

        back = MaterialContainersFactory.buildPanelGradient();
        back.setLayout(new BorderLayout());

        setContentPane(back);
        pack();
    }

    private JPanel back;

    public void changePanel(JComponent panel) {
        back.removeAll();
        back.add(panel);
        back.revalidate();
    }
}

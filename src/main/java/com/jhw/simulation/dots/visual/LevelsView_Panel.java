/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.visual;

import com.jhw.simulation.dots.controllers.Levels_Controller;
import com.jhw.swing.material.components.button.MaterialButtonIcon;
import com.jhw.swing.material.components.button.MaterialButtonsFactory;
import com.jhw.swing.material.components.container.MaterialContainersFactory;
import com.jhw.swing.material.components.container.panel._PanelAvatarChooser;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Yo
 */
public class LevelsView_Panel extends javax.swing.JPanel {

    private final Levels_Controller con = new Levels_Controller();

    public LevelsView_Panel() {
        initComponents();
        this.setName(con.getName());
        this.buttonBack.setIcon(new ImageIcon("media/icons/back.png"));
        this.levelChooser.setIcon(con.getBackgroundImage());
        this.con.setAvatars(levelChooser);
        this.levelChooser.setAvatarIndex(con.getAvatarIndex());
        this.addListeners();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        levelChooser = _PanelAvatarChooser.from();
        this.add(levelChooser);

        buttonBack = MaterialButtonsFactory.buildIconTransparent();
        JPanel p = MaterialContainersFactory.buildPanelTransparent();
        p.setLayout(new BorderLayout());
        p.add(buttonBack, BorderLayout.WEST);
        this.add(p, BorderLayout.NORTH);

    }

    private MaterialButtonIcon buttonBack;
    private _PanelAvatarChooser levelChooser;

    private void addListeners() {
        levelChooser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                switch (c) {
                    case KeyEvent.VK_ENTER:
                        con.actionSelectLevel(levelChooser);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        con.actionBack();
                        break;
                    default:
                        // do nothing
                        break;
                }
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                levelChooser.requestFocusInWindow();
            }
        });

        buttonBack.addActionListener((java.awt.event.ActionEvent evt) -> {
            con.actionBack();
        });
    }

}

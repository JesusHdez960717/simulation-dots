/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.visual;

import com.jhw.simulation.dots.controllers.LevelsController;
import com.jhw.simulation.dots.services.NavigationService;
import com.jhw.simulation.dots.services.ProgressService;
import com.jhw.simulation.dots.utils.Utility_Class;
import com.jhw.swing.material.components.button.MaterialButtonIcon;
import com.jhw.swing.material.components.button.MaterialButtonsFactory;
import com.jhw.swing.material.components.container.MaterialContainersFactory;
import com.jhw.swing.material.components.container.panel._PanelAvatarChooser;
import com.jhw.swing.material.components.container.panel._PanelGradient;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.util.AvatarPanelAvatarChooser;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Yo
 */
public class LevelsView_Panel extends _PanelGradient {

    private final LevelsController con = new LevelsController();

    public LevelsView_Panel() {
        initComponents();
        this.setAvatars();
        this.levelChooser.setAvatarIndex(con.getAvatarIndex());
        this.addListeners();

        levelChooser.requestFocusInWindow();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setIcon(con.getBackgroundImage());

        levelChooser = _PanelAvatarChooser.from();
        levelChooser.setBackground(MaterialColors.TRANSPARENT);
        this.add(levelChooser);

        buttonBack = MaterialButtonsFactory.buildIconTransparent();
        this.buttonBack.setIcon(new ImageIcon("media/icons/back.png"));
        JPanel p = MaterialContainersFactory.buildPanelTransparent();
        p.setLayout(new BorderLayout());
        p.add(buttonBack, BorderLayout.CENTER);
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
                        actionSelectLevel();
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

    private void setAvatars() {
        int maxAv = ProgressService.getMaxLevels();
        int actual = ProgressService.getLevel();
        ArrayList<AvatarPanelAvatarChooser> avatar = new ArrayList<>(maxAv);
        for (int i = 1; i <= actual; i++) {
            avatar.add(new AvatarPanelAvatarChooser("" + i, "Level " + i, "levels/" + i + "/icon.png"));
        }

        for (int i = actual + 1; i <= maxAv; i++) {
            avatar.add(new AvatarPanelAvatarChooser("" + i, "Level " + i, "media/icons/lock.png"));
        }

        this.levelChooser.setAvatars(avatar);
    }

    private void actionSelectLevel() {
        try {
            AvatarPanelAvatarChooser av = this.levelChooser.getSelectedAvatar();
            int level = Integer.parseInt(av.getId());
            if (level <= ProgressService.getLevel()) {//acces the level
                if (Utility_Class.jopContinue("Desea jugar el nivel " + level)) {
                    NavigationService.navigateToLevel(level);
                }
            }
        } catch (Exception e) {
            Utility_Class.jopError("Error seleccionando el nivel. " + e.getMessage());
        }
    }

}

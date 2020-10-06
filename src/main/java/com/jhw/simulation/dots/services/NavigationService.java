/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.services;

import com.jhw.simulation.dots.main.Main;
import com.jhw.simulation.dots.utils.Utility_Class;
import com.jhw.simulation.dots.visual.GameView_Panel;
import com.jhw.simulation.dots.visual.Index_Panel;
import com.jhw.simulation.dots.visual.LevelsView_Panel;
import com.jhw.simulation.dots.visual.MainView_UI;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class NavigationService {

    public static final String INDEX = "INDEX";
    public static final String LEVEL_SELECTOR = "LEVEL_SELECTOR";
    public static final String GAME = "GAME";

    private static final Map<String, Action> navMap;
    private static final Map<String, Action> backMap;

    private static final MainView_UI main;

    static {
        main = new MainView_UI();
        navMap = new HashMap<>();
        navMap.put(INDEX, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(new Index_Panel());
                showCursor();
            }
        });
        navMap.put(LEVEL_SELECTOR, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(new LevelsView_Panel());
                hideCursor();
            }
        });

        backMap = new HashMap<>();
        backMap.put(LEVEL_SELECTOR, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateTo(INDEX);
            }
        });
        backMap.put(GAME, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateTo(LEVEL_SELECTOR);
            }
        });
    }

    public static MainView_UI frame() {
        return main;
    }

    private static void changePanel(JComponent panel) {
        main.changePanel(panel);
    }

    public static void navigateTo(String to) {
        navMap.get(to).actionPerformed(null);
    }

    public static void backFrom(String from) {
        backMap.get(from).actionPerformed(null);
    }

    public static void navigateToLevel(int level) {
        try {
            changePanel(new GameView_Panel(ProgressService.getLevel()));
            hideCursor();
        } catch (Exception ex) {
            ex.printStackTrace();
            Utility_Class.jopError("Error cargando el nivel. " + ex.getMessage());
        }
    }

    public static void hideCursor() {
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        main.getContentPane().setCursor(blankCursor);
    }

    public static void showCursor() {
        main.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

}

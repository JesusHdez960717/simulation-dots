/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.visual;

import com.jhw.simulation.dots.controllers.GameController;
import java.awt.BorderLayout;

/**
 *
 * @author Yo
 */
public class GameView_Panel extends javax.swing.JPanel {

    private final GameController con;

    public GameView_Panel(int level) throws Exception {
        con = new GameController(level);

        this.setName("Game");

        this.setLayout(new BorderLayout());
        this.add(con.getDisplay());
    }

}

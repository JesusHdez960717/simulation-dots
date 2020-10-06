/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.visual;

import com.jhw.simulation.dots.controllers.Game_Controller;
import com.jhw.simulation.dots.main.Main;
import java.awt.BorderLayout;

/**
 *
 * @author Yo
 */
public class GameView_Panel extends javax.swing.JPanel {

    private final Game_Controller con;

    public GameView_Panel() throws Exception {
        this(Main.progress.getLevel());//no deja llamarlo desde el controller con.getProgress
    }

    public GameView_Panel(int level) throws Exception {
        con = new Game_Controller(level);

        this.setName("Game");

        this.setLayout(new BorderLayout());
        this.add(con.getDisplay());
        con.hideCursor();
    }

}

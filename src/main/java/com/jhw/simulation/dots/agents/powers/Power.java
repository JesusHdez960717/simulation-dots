/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.agents.powers;

import javax.swing.ImageIcon;

/**
 *
 * @author Yo
 */
public abstract class Power {

    protected long timeStart;
    protected long duration;//in steps

    public Power(long start) {
        this.timeStart = start;
    }

    public abstract ImageIcon getImage();
}

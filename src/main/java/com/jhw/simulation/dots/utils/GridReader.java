/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.simulation.dots.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Yo
 */
public class GridReader {

    public static int[][] readMaze(String url) throws FileNotFoundException {
        Scanner in = new Scanner(new File(url));
        int width = in.nextInt();
        int heigth = in.nextInt();
        int grid[][] = new int[width][heigth];
        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                grid[j][i] = in.nextInt();
            }
        }
        return grid;
    }

    public static int[][] readCoordinates(String url) throws FileNotFoundException {
        Scanner in = new Scanner(new File(url));
        int width = in.nextInt();
        int heigth = in.nextInt();
        int grid[][] = new int[width][heigth];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {
                grid[i][j] = in.nextInt();
            }
        }
        return grid;
    }
}

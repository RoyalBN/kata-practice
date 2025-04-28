package org.example;

import org.example.models.Grid;

public class ChristmasLights {

    private int[][] grid;

    public void createGrid(int width, int height) {
        this.grid = new int[width][height];
    }

    public int getGridSize() {
        return grid.length;
    }

    public int getLightValueAt(int row, int column) {
        return grid[row][column];
    }



}

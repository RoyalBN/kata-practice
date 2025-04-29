package org.example;

import org.example.models.Grid;

public class ChristmasLights {

    private Grid grid;

    public void createGrid(int width, int height) {
        this.grid = new Grid();
        this.grid.createGrid(width, height);
    }

    public int getGridSize() {
        return grid.getGridSize();
    }

    public int getLightValueAt(int row, int column) {
        return grid.getLightValueAt(row, column);
    }

    public void turnOnLightAt(int row, int column) {
        grid.turnOnLightAt(row, column);
    }

    public void turnOffLightAt(int row, int column) {
        grid.turnOffLightAt(row, column);
    }

    public void turnOnLightOnRange(int beginRow, int beginColumn, int endRow, int endColumn) {
        grid.turnOnLightOnRange(beginRow, beginColumn, endRow, endColumn);
    }

    public void turnOffLightOnRange(int beginRow, int beginColumn, int endRow, int endColumn) {
        grid.turnOffLightOnRange(beginRow, beginColumn, endRow, endColumn);
    }
}

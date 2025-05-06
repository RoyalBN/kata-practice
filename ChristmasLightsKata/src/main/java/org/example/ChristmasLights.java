package org.example;

import org.example.models.Grid;
import org.example.models.GridInterface;
import org.example.models.LightOperation;

public class ChristmasLights {

    private GridInterface grid;

    public ChristmasLights(GridInterface grid) {
        this.grid = grid;
    }

    public void createGrid(int width, int height) {
        this.grid.createGrid(width, height);
    }

    public int getGridSize() {
        return grid.getGridSize();
    }

    public void applyOperationAt(int row, int column, LightOperation operation) {
        grid.applyOperationAt(row, column, operation);
    }

    public void applyOperationOnRange(int beginRow, int beginColumn, int endRow, int endColumn, LightOperation operation) {
        grid.applyOperationOnRange(beginRow, beginColumn, endRow, endColumn, operation);
    }


    public int getLightValueAt(int row, int column) {
        return grid.getLightValueAt(row, column);
    }
}

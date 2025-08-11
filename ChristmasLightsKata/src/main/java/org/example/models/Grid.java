package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Grid implements GridInterface {

    private int width;
    private int height;
    private int[][] grid;

    @Override
    public void createGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new int[width][height];
    }

    @Override
    public int getGridSize() {
        return grid.length;
    }

    @Override
    public int getLightValueAt(int row, int column) {
        return grid[row][column];
    }

    public void turnOnLightAt(int row, int column) {
        grid[row][column] += 1;
    }

    public void turnOffLightAt(int row, int column) {
        grid[row][column] = 0;
    }

    public void turnOnLightOnRange(int beginRow, int beginColumn, int endRow, int endColumn) {
        for (int i = beginRow; i <= endRow; i++) {
            for (int j = beginColumn; j <= endColumn; j++) {
                grid[i][j] += 1;
            }
        }
    }

    public void turnOffLightOnRange(int beginRow, int beginColumn, int endRow, int endColumn) {
        for (int i = beginRow; i <= endRow; i++) {
            for (int j = beginColumn; j <= endColumn; j++) {
                grid[i][j] = grid[i][j] == 0 ? 0 : grid[i][j] - 1;
            }
        }
    }

    public void toggleLightAt(int row, int column) {
        grid[row][column] += 2;
    }

    public void toggleLightOnRange(int beginRow, int beginColumn, int endRow, int endColumn) {
        for (int i = beginRow; i <= endRow; i++) {
            for (int j = beginColumn; j <= endColumn; j++) {
                grid[i][j] += 2;
            }
        }
    }

    @Override
    public void applyOperationAt(int row, int column, LightOperation operation) {
        validateOperation(operation);
        validateIndex(row, column);

        switch (operation) {
            case ON -> turnOnLightAt(row, column);
            case OFF -> turnOffLightAt(row, column);
            case TOGGLE -> toggleLightAt(row, column);
        }
    }

    private void validateIndex(int row, int column) {
        if(row < 0 || row >= width || column < 0 || column >= height) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    @Override
    public void applyOperationOnRange(int beginRow, int beginColumn, int endRow, int endColumn, LightOperation operation) {
        validateOperation(operation);
        validateIndex(beginRow, beginColumn);

        switch (operation) {
            case ON -> turnOnLightOnRange(beginRow, beginColumn, endRow, endColumn);
            case OFF -> turnOffLightOnRange(beginRow, beginColumn, endRow, endColumn);
            case TOGGLE -> toggleLightOnRange(beginRow, beginColumn, endRow, endColumn);
        }
    }

    private static void validateOperation(LightOperation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Unknown operation: null");
        }
    }

    @Override
    public int countBrightness() {
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                count += grid[i][j];
            }
        }
        return count;
    }
}

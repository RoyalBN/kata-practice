package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Grid {

    private int width;
    private int height;
    private int[][] grid;

    public void createGrid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new int[width][height];
    }

    public int getGridSize() {
        return grid.length;
    }

    public int getLightValueAt(int row, int column) {
        return grid[row][column];
    }

    public void setLightValueAt(int row, int column) {
        grid[row][column] = 1;
    }

    public void turnOnLightOnRange(int beginRow, int beginColumn, int endRow, int endColumn) {
        for (int i = beginRow; i <= endRow; i++) {
            for (int j = beginColumn; j <= endColumn; j++) {
                grid[i][j] = 1;
            }
        }
    }
}

package org.example.models;

public interface GridInterface {
    void createGrid(int width, int height);
    int getGridSize();
    int getLightValueAt(int row, int column);
    void applyOperationAt(int row, int column, LightOperation operation);
    void applyOperationOnRange(int beginRow, int beginColumn, int endRow, int endColumn, LightOperation operation);
}

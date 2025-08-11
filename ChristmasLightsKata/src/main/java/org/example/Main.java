package org.example;

import org.example.models.Grid;
import org.example.models.LightOperation;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Grid grid = new Grid();
        ChristmasLights christmasLights = new ChristmasLights(grid);
        christmasLights.createGrid(1000, 1000);
        christmasLights.applyOperationOnRange(887, 9, 959, 629, LightOperation.ON);
        christmasLights.applyOperationOnRange(454, 398, 844, 448, LightOperation.ON);
        christmasLights.applyOperationOnRange(539, 243, 559, 965, LightOperation.OFF);
        christmasLights.applyOperationOnRange(370, 819, 676, 868, LightOperation.OFF);
        christmasLights.applyOperationOnRange(145, 40, 370, 997, LightOperation.OFF);
        christmasLights.applyOperationOnRange(301, 3, 808, 453, LightOperation.OFF);
        christmasLights.applyOperationOnRange(351, 678, 951, 908, LightOperation.ON);
        christmasLights.applyOperationOnRange(720, 196, 897, 994, LightOperation.TOGGLE);
        christmasLights.applyOperationOnRange(831, 394, 904, 860, LightOperation.TOGGLE);

        System.out.println("Number of lights on: " + christmasLights.countLights());
    }
}
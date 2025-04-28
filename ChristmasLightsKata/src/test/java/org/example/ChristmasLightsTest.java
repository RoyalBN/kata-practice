package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  - Deploy one million Christmas lights in 1000 x 1000 grid
 *  - Lights are numbered from 0 to 999 in each direction
 *  - The lights at each corner are at 0,0, 0,999, 999,999, and 999,0 :
 *      [0,0]   .....  [999,0]
 *      .....           .....
 *      .....           .....
 *      [0,999] .....  [999,999]
 *
 *  - Each coordinate pair represents opposite corners of a rectangle, inclusive
 *  /!| The lights all start turned off.
 *
 *  - [INIT] before a test name concerns the initialization of the grid
 *  - [ON] before a test name concerns the Turn-ON functionality
 *  - [OFF] before a test name concerns the Turn-OFF functionality
 *  - [TOGGLE] before a test name concerns the TOGGLE functionality
 */

class ChristmasLightsTest {

    private int GRID_WIDTH = 1000;
    private int GRID_HEIGHT = 1000;
    private ChristmasLights christmasLights;

    @BeforeEach
    void setUp() {
        christmasLights = new ChristmasLights();
        christmasLights.createGrid(GRID_WIDTH, GRID_HEIGHT);
    }

    @Test
    @DisplayName("[INIT] Create 1000 x 1000 Grid")
    void should_create_a_grid_when_creating_new_class_instance() {
        // Act & Assert
        int gridSize = christmasLights.getGridSize();
        assertThat(gridSize).isEqualTo(1000);
    }

    @Test
    @DisplayName("[INIT] All lights should be turned off at start")
    void should_initialize_grid_with_all_lights_turned_off() {
        // Act & Assert
        int gridSize = christmasLights.getGridSize();

        assertThat(gridSize).isEqualTo(1000);
        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,999)).isEqualTo(0);
    }

    @Test
    @DisplayName("[ON] Turn on a single light")
    void should_turn_on_a_single_light() {
        // Act & Assert
        christmasLights.turnOnLightAt(1,1);
        assertThat(christmasLights.getLightValueAt(1,1)).isEqualTo(1);
    }

    @Test
    @DisplayName("[ON] Turn on a single row of lights")
    void should_turn_on_a_single_row_of_lights() {
        // Act & Assert
        christmasLights.turnOnLightOnRange(0, 0, 0, 999);

        // First line --> value = 1
        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(1);

        // Second line --> value = 0
        assertThat(christmasLights.getLightValueAt(1,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(0);
    }

    @Test
    @DisplayName("[ON] Turn on a single column of lights")
    void should_turn_on_a_single_column_of_lights() {
        // Act & Assert
        christmasLights.turnOnLightOnRange(0, 999, 0, 0);

        // First column --> value = 1
        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(1);

        // Second column --> value = 0
        assertThat(christmasLights.getLightValueAt(0,1)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,1)).isEqualTo(0);
    }

    @Test
    @DisplayName("[ON] Turn on all lights")
    void should_turn_on_all_lights() {
        // Act & Assert
        christmasLights.turnOnLightOnRange(0, 999, 0, 999);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(999,999)).isEqualTo(1);
    }

    // [OFF] Turn off a single light
    // [OFF] Turn off a single row of lights
    // [OFF] Turn off a single column of lights
    // [OFF] Turn off all lights

    // [TOGGLE] Toggle a single light
    // [TOGGLE] Toggle a single row of lights
    // [TOGGLE] Toggle a single column of lights
    // [TOGGLE] Toggle all lights

}
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
 *  - Concrete exemple of th grid :
 *
 *         column 0   column 1   column 2   column 3
 *       +----------+----------+----------+----------+
 *  row 0| (0,0)    | (0,1)    | (0,2)    | (0,3)    |
 *       +----------+----------+----------+----------+
 *  row 1| (1,0)    | (1,1)    | (1,2)    | (1,3)    |
 *       +----------+----------+----------+----------+
 *  row 2| (2,0)    | (2,1)    | (2,2)    | (2,3)    |
 *       +----------+----------+----------+----------+
 *  row 3| (3,0)    | (3,1)    | (3,2)    | (3,3)    |
 *       +----------+----------+----------+----------+
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
        christmasLights.turnOnLightOnRange(0, 0, 999, 0);

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
        christmasLights.turnOnLightOnRange(0, 0, 999, 999);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(999,999)).isEqualTo(1);
    }

    @Test
    @DisplayName("[ON] Turn on lights from [887,9] through [959,629]")
    void should_turn_on_lights_from_range_887_9_through_959_629() {
        // Act & Assert
        christmasLights.turnOnLightOnRange(887, 9, 959, 629);

        assertThat(christmasLights.getLightValueAt(887,9)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(959,629)).isEqualTo(1);

        assertThat(christmasLights.getLightValueAt(886,9)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(960,629)).isEqualTo(0);
    }


    @Test
    @DisplayName("[ON] Turn on lights from [454,398] through [844,448]")
    void should_turn_on_lights_from_range_454_398_through_844_448() {
        // Act & Assert
        christmasLights.turnOnLightOnRange(454, 398, 844, 448);

        assertThat(christmasLights.getLightValueAt(454,398)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(844,448)).isEqualTo(1);

        assertThat(christmasLights.getLightValueAt(453,398)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(845,448)).isEqualTo(0);
    }

    @Test
    @DisplayName("[ON] Turn on from [351,678] through [951,908]")
    void should_turn_on_lights_from_range_351_678_through_951_908() {
        // Act & Assert
        christmasLights.turnOnLightOnRange(351, 678, 951, 908);

        assertThat(christmasLights.getLightValueAt(351,678)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(951,908)).isEqualTo(1);

        assertThat(christmasLights.getLightValueAt(350,678)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(952,908)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off a single light")
    void should_turn_off_a_single_light() {
        // Act & Assert
        christmasLights.turnOffLightAt(1,1);
        assertThat(christmasLights.getLightValueAt(1,1)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off a single row of lights")
    void should_turn_off_a_single_row_of_lights() {
        // Act & Assert
        christmasLights.turnOffLightOnRange(0, 0, 0, 999);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off a single column of lights")
    void should_turn_off_a_single_column_of_lights() {
        // Act & Assert
        christmasLights.turnOffLightOnRange(0, 0, 999, 0);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off all lights")
    void should_turn_off_all_lights() {
        // Act & Assert
        christmasLights.turnOffLightOnRange(0, 0, 999, 999);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,999)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [539,243] through [559,965]")
    void should_turn_off_lights_from_range_539_243_through_559_965() {
        // Act & Assert
        christmasLights.turnOffLightOnRange(539, 243, 559, 965);

        assertThat(christmasLights.getLightValueAt(539,243)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(559,965)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [370,819] through [676,868]")
    void should_turn_off_lights_from_range_370_819_through_676_868() {
        // Act & Assert
        christmasLights.turnOffLightOnRange(370, 819, 676, 868);

        assertThat(christmasLights.getLightValueAt(370,819)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(676,868)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [145,40] through [370,997]")
    void should_turn_off_lights_from_range_145_40_through_370_997() {
        // Act & Assert
        christmasLights.turnOffLightOnRange(145, 40, 370, 997);

        assertThat(christmasLights.getLightValueAt(145,40)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(370,997)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [301,3] through [808,453]")
    void should_turn_off_lights_from_range_301_3_through_808_453() {
        // Act & Assert
        christmasLights.turnOffLightOnRange(301, 3, 808, 453);

        assertThat(christmasLights.getLightValueAt(301,3)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(808,453)).isEqualTo(0);
    }


    // [TOGGLE] Toggle a single light
    // [TOGGLE] Toggle a single row of lights
    // [TOGGLE] Toggle a single column of lights
    // [TOGGLE] Toggle all lights

    // [TOGGLE] Toggle from [720,196] through [897,994]
    // [TOGGLE] Toggle from [831,394] through [904,860]


}
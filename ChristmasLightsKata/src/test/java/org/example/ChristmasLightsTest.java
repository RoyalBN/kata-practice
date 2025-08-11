package org.example;

import org.example.models.Grid;
import org.example.models.GridInterface;
import org.example.models.LightOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;

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
 *  - [ERROR] before a test name concerns an error
 */

class ChristmasLightsTest {

    private int GRID_WIDTH = 1000;
    private int GRID_HEIGHT = 1000;
    private ChristmasLights christmasLights;

    private GridInterface grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
        christmasLights = new ChristmasLights(grid);
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
        christmasLights.applyOperationAt(1,1, LightOperation.ON);
        assertThat(christmasLights.getLightValueAt(1,1)).isEqualTo(1);
    }

    @Test
    @DisplayName("[ON] Turn on a single row of lights")
    void should_turn_on_a_single_row_of_lights() {
        // Act & Assert
        christmasLights.applyOperationOnRange(0, 0, 0, 999, LightOperation.ON);

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
        christmasLights.applyOperationOnRange(0, 0, 999, 0, LightOperation.ON);

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
        christmasLights.applyOperationOnRange(0, 0, 999, 999, LightOperation.ON);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(999,999)).isEqualTo(1);
    }

    @Test
    @DisplayName("[ON] Turn on lights from [887,9] through [959,629]")
    void should_turn_on_lights_from_range_887_9_through_959_629() {
        // Act & Assert
        christmasLights.applyOperationOnRange(887, 9, 959, 629, LightOperation.ON);

        assertThat(christmasLights.getLightValueAt(887,9)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(959,629)).isEqualTo(1);

        assertThat(christmasLights.getLightValueAt(886,9)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(960,629)).isEqualTo(0);
    }


    @Test
    @DisplayName("[ON] Turn on lights from [454,398] through [844,448]")
    void should_turn_on_lights_from_range_454_398_through_844_448() {
        // Act & Assert
        christmasLights.applyOperationOnRange(454, 398, 844, 448, LightOperation.ON);

        assertThat(christmasLights.getLightValueAt(454,398)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(844,448)).isEqualTo(1);

        assertThat(christmasLights.getLightValueAt(453,398)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(845,448)).isEqualTo(0);
    }

    @Test
    @DisplayName("[ON] Turn on from [351,678] through [951,908]")
    void should_turn_on_lights_from_range_351_678_through_951_908() {
        // Act & Assert
        christmasLights.applyOperationOnRange(351, 678, 951, 908, LightOperation.ON);

        assertThat(christmasLights.getLightValueAt(351,678)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(951,908)).isEqualTo(1);

        assertThat(christmasLights.getLightValueAt(350,678)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(952,908)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off a single light")
    void should_turn_off_a_single_light() {
        // Act & Assert
        christmasLights.applyOperationAt(1,1, LightOperation.OFF);
        assertThat(christmasLights.getLightValueAt(1,1)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off a single row of lights")
    void should_turn_off_a_single_row_of_lights() {
        // Act & Assert
        christmasLights.applyOperationOnRange(0, 0, 0, 999, LightOperation.OFF);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off a single column of lights")
    void should_turn_off_a_single_column_of_lights() {
        // Act & Assert
        christmasLights.applyOperationOnRange(0, 0, 999, 0, LightOperation.OFF);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off all lights")
    void should_turn_off_all_lights() {
        // Act & Assert
        christmasLights.applyOperationOnRange(0, 0, 999, 999, LightOperation.OFF);

        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(999,999)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [539,243] through [559,965]")
    void should_turn_off_lights_from_range_539_243_through_559_965() {
        // Act & Assert
        christmasLights.applyOperationOnRange(539, 243, 559, 965, LightOperation.OFF);

        assertThat(christmasLights.getLightValueAt(539,243)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(559,965)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [370,819] through [676,868]")
    void should_turn_off_lights_from_range_370_819_through_676_868() {
        // Act & Assert
        christmasLights.applyOperationOnRange(370, 819, 676, 868, LightOperation.OFF);

        assertThat(christmasLights.getLightValueAt(370,819)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(676,868)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [145,40] through [370,997]")
    void should_turn_off_lights_from_range_145_40_through_370_997() {
        // Act & Assert
        christmasLights.applyOperationOnRange(145, 40, 370, 997, LightOperation.OFF);

        assertThat(christmasLights.getLightValueAt(145,40)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(370,997)).isEqualTo(0);
    }

    @Test
    @DisplayName("[OFF] Turn off from [301,3] through [808,453]")
    void should_turn_off_lights_from_range_301_3_through_808_453() {
        // Act & Assert
        christmasLights.applyOperationOnRange(301, 3, 808, 453, LightOperation.OFF);

        assertThat(christmasLights.getLightValueAt(301,3)).isEqualTo(0);
        assertThat(christmasLights.getLightValueAt(808,453)).isEqualTo(0);
    }

    @Test
    @DisplayName("[TOGGLE] Toggle a single light")
    void should_toggle_a_single_light() {
        // Arrange
        christmasLights.applyOperationAt(1,1, LightOperation.ON);

        // Act
        christmasLights.applyOperationAt(1,1, LightOperation.TOGGLE);

        // Assert
        assertThat(christmasLights.getLightValueAt(1,1)).isEqualTo(3);
    }

    @Test
    @DisplayName("[TOGGLE] Toggle a single row of lights")
    void should_toggle_a_single_row_of_lights() {
        // Arrange
        christmasLights.applyOperationOnRange(0, 0, 0, 999, LightOperation.ON);

        // Act
        christmasLights.applyOperationOnRange(0, 0, 0, 999, LightOperation.TOGGLE);

        // Assert
        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(3);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(3);
    }

    @Test
    @DisplayName("[TOGGLE] Toggle a single column of lights")
    void should_toggle_a_single_column_of_lights() {
        // Arrange
        christmasLights.applyOperationOnRange(0, 0, 999, 0, LightOperation.ON);

        // Act
        christmasLights.applyOperationOnRange(0, 0, 999, 0, LightOperation.TOGGLE);

        // Assert
        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(3);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(3);
    }

    @Test
    @DisplayName("[TOGGLE] Toggle all lights")
    void should_toggle_all_lights() {
        // Arrange & Act
        christmasLights.applyOperationOnRange(0, 0, 999, 999, LightOperation.TOGGLE);

        // Assert
        assertThat(christmasLights.getLightValueAt(0,0)).isEqualTo(2);
        assertThat(christmasLights.getLightValueAt(0,999)).isEqualTo(2);
        assertThat(christmasLights.getLightValueAt(999,0)).isEqualTo(2);
        assertThat(christmasLights.getLightValueAt(999,999)).isEqualTo(2);

        assertThat(christmasLights.countBrightness()).isEqualTo(2000000);
    }

    @Test
    @DisplayName("[TOGGLE] Toggle lights from [720,196] through [897,994]")
    void should_toggle_lights_from_range_720_196_through_897_994() {
        // Arrange
        christmasLights.applyOperationOnRange(720, 196, 897, 994, LightOperation.ON);
        assertThat(christmasLights.getLightValueAt(720,196)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(897,994)).isEqualTo(1);

        // Act
        christmasLights.applyOperationOnRange(720, 196, 897, 994, LightOperation.TOGGLE);

        // Assert
        assertThat(christmasLights.getLightValueAt(720,196)).isEqualTo(3);
        assertThat(christmasLights.getLightValueAt(897,994)).isEqualTo(3);
    }

    @Test
    @DisplayName("[TOGGLE] Toggle from [831,394] through [904,860]")
    void should_toggle_lights_from_range_831_394_through_904_860() {
        // Arrange
        christmasLights.applyOperationOnRange(831, 394, 904, 860, LightOperation.ON);
        assertThat(christmasLights.getLightValueAt(831,394)).isEqualTo(1);
        assertThat(christmasLights.getLightValueAt(904,860)).isEqualTo(1);

        // Act
        christmasLights.applyOperationOnRange(831, 394, 904, 860, LightOperation.TOGGLE);

        // Assert
        assertThat(christmasLights.getLightValueAt(831,394)).isEqualTo(3);
        assertThat(christmasLights.getLightValueAt(904,860)).isEqualTo(3);
    }

    @Test
    @DisplayName("[ERROR] Throw IllegalArgumentException when invalid operation on specific index")
    void should_throw_illegalArgumentException_when_invalid_operation_on_specific_index() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> christmasLights.applyOperationAt(0, 999, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown operation: null");
    }

    @Test
    @DisplayName("[ERROR] Throw IllegalArgumentException when invalid operation on specific range")
    void should_throw_illegalArgumentException_when_invalid_operation_on_specific_range() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> christmasLights.applyOperationOnRange(0, 0, 999, 999, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown operation: null");
    }

    @Test
    @DisplayName("[ERROR] Throw ArrayIndexOutOfBoundsException when invalid index")
    void should_throw_arrayIndexOutOfBoundsException_when_invalid_index() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> christmasLights.applyOperationAt(-1, -1, LightOperation.ON));

        assertThat(thrown)
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage("Invalid index");
    }

    @Test
    @DisplayName("[ERROR] Throw ArrayIndexOutOfBoundsException when invalid range")
    void should_throw_arrayIndexOutOfBoundsException_when_invalid_range() {
        // Act & Assert
        Throwable thrown = catchThrowable(() -> christmasLights.applyOperationOnRange(-1, -1, -1, -1, LightOperation.ON));

        assertThat(thrown)
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage("Invalid index");

    }

}
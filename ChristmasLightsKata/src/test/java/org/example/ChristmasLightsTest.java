package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
 *  - [ON] before a test name concerns the Turn-ON functionality
 *  - [OFF] before a test name concerns the Turn-OFF functionality
 *  - [TOGGLE] before a test name concerns the TOGGLE functionality
 */

class ChristmasLightsTest {

    @BeforeEach
    void setUp() {
    }

    // All lights should be turned off at start

    // [ON] Turn on a single light
    // [ON] Turn on a single row of lights
    // [ON] Turn on a single column of lights
    // [ON] Turn on all lights

    // [OFF] Turn off a single light
    // [OFF] Turn off a single row of lights
    // [OFF] Turn off a single column of lights
    // [OFF] Turn off all lights

    // [TOGGLE] Toggle a single light
    // [TOGGLE] Toggle a single row of lights
    // [TOGGLE] Toggle a single column of lights
    // [TOGGLE] Toggle all lights

}
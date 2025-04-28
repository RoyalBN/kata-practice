package org.example;

import org.junit.jupiter.api.BeforeEach;

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
 */

class ChristmasLightsTest {

    @BeforeEach
    void setUp() {
    }
}
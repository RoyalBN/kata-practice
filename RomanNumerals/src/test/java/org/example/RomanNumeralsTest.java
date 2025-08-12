package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**

 Write a method String convert(int) that takes a number and converts it to the according String representation.

 Examples:
 1 ➔ I
 2 ➔ II
 3 ➔ III
 4 ➔ IV
 5 ➔ V
 9 ➔ IX
 21 ➔ XXI
 50 ➔ L
 100 ➔ C
 500 ➔ D
 1000 ➔ M
**/


class RomanNumeralsTest {

    @Test
    @DisplayName("0 --> Roman numerals --> empty string")
    void should_return_empty_string_when_given_0() {
        // Arrange
        int number = 0;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("");
    }

    @Test
    @DisplayName("1 --> Roman numerals --> I")
    void should_return_I_when_given_1() {
        // Arrange
        int number = 1;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("I");
    }

    @Test
    @DisplayName("4 --> Roman numerals --> IV")
    void should_return_IV_when_given_4() {
        // Arrange
        int number = 4;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("IV");

    }

    @Test
    @DisplayName("9 --> Roman numerals --> IX")
    void should_return_IX_when_given_9() {
        // Arrange
        int number = 9;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("IX");
    }

    @Test
    @DisplayName("16 --> Roman numerals --> XVI")
    void should_return_XVI_when_given_16() {
        // Arrange
        int number = 16;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("XVI");
    }

    @Test
    @DisplayName("21 --> Roman numerals --> XXI")
    void should_return_XXI_when_given_21() {
        // Arrange
        int number = 21;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("XXI");
    }

    @Test
    @DisplayName("50 --> Roman numerals --> L")
    void should_return_L_when_given_50() {
        // Arrange
        int number = 50;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("L");
    }

    @Test
    @DisplayName("76 --> Roman numerals --> LXXVI")
    void should_return_LXXVI_when_given_76() {
        // Arrange
        int number = 76;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("LXXVI");
    }

    @Test
    @DisplayName("99 --> Roman numerals --> XCIX")
    void should_return_XCIX_when_given_99() {
        // Arrange
        int number = 99;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("XCIX");
    }

    @Test
    @DisplayName("100 --> Roman numerals --> C")
    void should_return_C_when_given_100() {
        // Arrange
        int number = 100;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("C");
    }

    @Test
    @DisplayName("499 --> Roman numerals --> CDXCIX")
    void should_return_CDXCIX_when_given_499() {
        // Arrange
        int number = 499;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("CDXCIX");
    }

    @Test
    @DisplayName("500 --> Roman numerals --> D")
    void should_return_D_when_given_500() {
        // Arrange
        int number = 500;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("D");
    }

    @Test
    @DisplayName("999 --> Roman numerals --> CMXCIX")
    void should_return_CMXCIX_when_given_999() {
        // Arrange
        int number = 999;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("CMXCIX");
    }

    @Test
    @DisplayName("1000 --> Roman numerals --> M")
    void should_return_M_when_given_1000() {
        // Arrange
        int number = 1000;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("M");
    }

    @Test
    @DisplayName("1984 --> Roman numerals --> MCMLXXXIV")
    void should_return_MCMLXXXIV_when_given_1984() {
        // Arrange
        int number = 1984;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("MCMLXXXIV");
    }

    @Test
    @DisplayName("24454 --> Roman numerals --> MMMMMMMMMMMMMMMMMMMMMMMMCDLIV")
    void should_return_MMMMMMMMMMMMMMMMMMMMMMMMCDLIV_when_given_24454() {
        // Arrange
        int number = 24454;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("MMMMMMMMMMMMMMMMMMMMMMMMCDLIV");
    }

    @Test
    @DisplayName("-1 --> Roman numerals --> empty string")
    void should_return_Not_a_number_when_given_minus_1() {
        // Arrange
        int number = -1;

        // Act
        RomanNumerals.convert(number);

        // Assert
        assertThat(RomanNumerals.convert(number)).isEqualTo("");
    }
}
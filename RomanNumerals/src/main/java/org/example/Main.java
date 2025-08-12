package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        int[] numbersToConvert = new int[]{1, 2, 3, 4, 5, 9, 21, 50, 100, 500, 1000, 10000, 24452};
        for (int numberToConvert : numbersToConvert) {
            logger.info(() -> String.format("%d --> Roman numerals --> %s", numberToConvert, RomanNumerals.convert(numberToConvert)));
        }
    }
}
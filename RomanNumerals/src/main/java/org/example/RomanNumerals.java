package org.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class RomanNumerals {

    public static String convert(int number) {
        String convertedNumber = "";
        int remain = number;

        LinkedHashMap<Integer, String> romanNumerals = new LinkedHashMap<>();

        romanNumerals.put(1000, "M");
        romanNumerals.put(900, "CM");
        romanNumerals.put(500, "D");
        romanNumerals.put(400, "CD");
        romanNumerals.put(100, "C");
        romanNumerals.put(90, "XC");
        romanNumerals.put(50, "L");
        romanNumerals.put(40, "XL");
        romanNumerals.put(10, "X");
        romanNumerals.put(9, "IX");
        romanNumerals.put(5, "V");
        romanNumerals.put(4, "IV");
        romanNumerals.put(1, "I");

        while(remain > 0) {
            for (Map.Entry<Integer, String> entry : romanNumerals.entrySet()) {
                if(remain >= entry.getKey()) {
                    convertedNumber += entry.getValue();
                    remain -= entry.getKey();
                    break;
                }
            }
        }
        return convertedNumber;
    }

}

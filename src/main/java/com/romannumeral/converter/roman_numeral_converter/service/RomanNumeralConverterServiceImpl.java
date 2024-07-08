package com.romannumeral.converter.roman_numeral_converter.service;

import org.springframework.stereotype.Service;

@Service
public class RomanNumeralConverterServiceImpl implements RomanNumeralConverterService
{
    private static final int[] numbers = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static final String[] romanNumerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    @Override public String convertIntegerToRomanNumeral(int input)
    {
        StringBuilder romanNumeralBuilder = new StringBuilder();
        int times = 0;

        //iterate over until the numbers array length to 0
        for (int i = numbers.length - 1; i >= 0; i--)
        {
            times = input / numbers[i];
            input %= numbers[i];
            //loop until the input fits in the given number range and select the appropriate roman numeral accordingly
            while (times > 0)
            {
                romanNumeralBuilder.append(romanNumerals[i]);
                times --;
            }
        }
        return romanNumeralBuilder.toString();
    }
}

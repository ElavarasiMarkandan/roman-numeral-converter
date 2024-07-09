package com.romannumeral.converter.roman_numeral_converter.service;

import org.springframework.stereotype.Service;
// Implements RomanNumeralConverterService to provide logic for converting integer to roman numeral

@Service
public class RomanNumeralConverterServiceImpl implements RomanNumeralConverterService
{
    private static final int[] numbers = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
    private static final String[] romanNumerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM",
            "M" };

    /**
     * Reference - https://simple.wikipedia.org/wiki/Roman_numerals - Static variables are defined for numbers & roman numerals
     * to help in converting the integer input to its corresponding roman numeral representation
     *
     * Space Complexity - O(1) - Since the input is an integer, the memory used is going to remain constant
     * Time Complexity - O(1) -Since the number of roman numerals is finite,
     *                          the iteration can only happen a defined number of times, so the time complexity is O(1)
     * This method takes integer as input and returns String Roman numeral representation as output
     */

    @Override public String convertIntegerToRomanNumeral(int input)
    {
        StringBuilder romanNumeralBuilder = new StringBuilder();
        int times = 0;

        //iterate over until the numbers array length to 0
        for (int i = numbers.length - 1; i >= 0; i--)
        {
            times = input / numbers[i];// check whether the given input fits in the number array value
            input %= numbers[i];// This condition results the remainder, once the given input fits in the number array value
            //loop until the input fits in the given number range and select the appropriate roman numeral accordingly
            while (times > 0)
            {
                romanNumeralBuilder.append(romanNumerals[i]);
                times--;
            }
        }
        return romanNumeralBuilder.toString();
    }
}

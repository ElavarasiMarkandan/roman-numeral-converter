package com.romannumeral.converter.roman_numeral_converter.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


class RomanNumeralConverterServiceImplTest
{
    @Test
    void convertIntegerToRomanNumeral_Success()
    {
        RomanNumeralConverterServiceImpl romanNumeralConverterService = new RomanNumeralConverterServiceImpl();
        assertEquals(romanNumeralConverterService.convertIntegerToRomanNumeral(1), "I");
        assertEquals(romanNumeralConverterService.convertIntegerToRomanNumeral(10), "X");
        assertEquals(romanNumeralConverterService.convertIntegerToRomanNumeral(50), "L");
        assertEquals(romanNumeralConverterService.convertIntegerToRomanNumeral(100), "C");
    }
}

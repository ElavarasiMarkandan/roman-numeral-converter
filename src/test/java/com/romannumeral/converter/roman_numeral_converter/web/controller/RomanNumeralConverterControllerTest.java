package com.romannumeral.converter.roman_numeral_converter.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.romannumeral.converter.roman_numeral_converter.service.RomanNumeralConverterService;

@WebMvcTest(RomanNumeralConverterController.class)
class RomanNumeralConverterControllerTest
{
    @MockBean
    RomanNumeralConverterService romanNumeralConverterService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void convertIntegerToRomanNumeral_Success() throws Exception
    {
        Mockito.when(romanNumeralConverterService.convertIntegerToRomanNumeral(101)).thenReturn("CI");

        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=101")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("CI")));
    }
}

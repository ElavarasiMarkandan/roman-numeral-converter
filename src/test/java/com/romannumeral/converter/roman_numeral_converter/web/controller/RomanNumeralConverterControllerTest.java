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
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

//  Unit tests - To test validation and exception handling at controller layer

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

    @Test
    void convertIntegerToRomanNumeral_OutOfRange_LessThanMinError() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=0")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Invalid input, enter an integer value in the range from 1 to 255")));
    }

    @Test
    void convertIntegerToRomanNumeral_OutOfRange_GreaterThanMaxError() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=256")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Invalid input, enter an integer value in the range from 1 to 255")));
    }

    @Test
    void convertIntegerToRomanNumeral_InvalidType() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=XX")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(ErrorMessage.INPUT_TYPE_MISMATCH)));
    }

    @Test
    void convertIntegerToRomanNumeral_internalServerError() throws Exception
    {
        Mockito.when(romanNumeralConverterService.convertIntegerToRomanNumeral(200)).thenThrow(RuntimeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=200"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString(ErrorMessage.INTERNAL_SERVER_ERROR)));
    }
}

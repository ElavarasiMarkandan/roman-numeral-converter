package com.romannumeral.converter.roman_numeral_converter.web.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// Response DTO for RomanNumeralConverterController
@AllArgsConstructor
@NoArgsConstructor
public class RomanNumeralResponseDTO
{
    private String input;
    private String output;

    public String getInput()
    {
        return input;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output = output;
    }

    @Override public String toString()
    {
        return "RomanNumeralResponseDTO{" +
                "input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}

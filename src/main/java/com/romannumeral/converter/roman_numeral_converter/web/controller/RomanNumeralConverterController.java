package com.romannumeral.converter.roman_numeral_converter.web.controller;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romannumeral.converter.roman_numeral_converter.service.RomanNumeralConverterService;
import com.romannumeral.converter.roman_numeral_converter.web.dto.RomanNumeralResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/romannumeral")
@Api(value = "REST api to convert Integer to Roman Numeral")
@Validated
public class RomanNumeralConverterController
{
    @Autowired
    RomanNumeralConverterService romanNumeralConverterService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "GET endpoint that accepts an int request and responds with the converted romannumeral in JSON format", response = RomanNumeralResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = ErrorMessage.INPUT_TYPE_MISMATCH),
            @ApiResponse(code = 400, message = ErrorMessage.INVALID_DATA),
            @ApiResponse(code = 500, message = ErrorMessage.INTERNAL_SERVER_ERROR)
    })
    public RomanNumeralResponseDTO convertIntegerToRomanNumeral(@RequestParam("query") @Min(value = 1)
                                                                    @Max(value = 255) int input){
        String romanNumeral = romanNumeralConverterService.convertIntegerToRomanNumeral(input);
        RomanNumeralResponseDTO romanNumeralResponseDTO = new RomanNumeralResponseDTO(String.valueOf(input), romanNumeral);
        return romanNumeralResponseDTO;
    }
}

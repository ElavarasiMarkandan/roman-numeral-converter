package com.romannumeral.converter.roman_numeral_converter.acceptance;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romannumeral.converter.roman_numeral_converter.web.dto.ErrorResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.dto.RomanNumeralResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

// Acceptance tests can be integrated into the CI/CD pipeline with test cases
// required to confirm the application ready to deploy to next stage
public class IntegerToRomanNumeralConverterAT
{
    //Acceptance Test Case #1 - Validate response content type is application/json
    @Test
    public void testDefaultContentTypeIsJson() throws IOException
    {
        String expectedContentType = "application/json";
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=200");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assertions.assertEquals(expectedContentType, ContentType.getOrDefault(response.getEntity()).getMimeType());
    }

    //Acceptance Test Case #2 - Happy path case to validate conversion of a valid int to roman numeral
    @Test
    public void testIntegerToRomanNumeralConversion_Success() throws IOException
    {
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=255");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        RomanNumeralResponseDTO romanNumeralResponseDTO = retrieveResourceFromResponse(response,
                                                                                       RomanNumeralResponseDTO.class);

        Assertions.assertEquals("255", romanNumeralResponseDTO.getInput());
        Assertions.assertEquals("CCLV", romanNumeralResponseDTO.getOutput());
    }

    //Acceptance Test Case #3 - Error case to validate out of range conversions. Valid range 1 to 3999
    @Test
    public void testIntegerToRomanNumeralConversion_ValidationError_OutOfRange() throws IOException
    {
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=256");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        ErrorResponseDTO errorResponseDTO = retrieveResourceFromResponse(response, ErrorResponseDTO.class);

        Assertions.assertEquals(400, errorResponseDTO.getStatusCode());
        Assertions.assertTrue(errorResponseDTO.getErrorMessage().contains(ErrorMessage.INVALID_DATA));
    }

    //Acceptance Test Case #4 - Error case to validate invalid data type inputs. Valid data type int, range 1 to 3999
    @Test
    public void testIntegerToRomanNumeralConversion_ValidationError_InvalidDataType() throws IOException
    {
        HttpUriRequest request = new HttpGet("http://localhost:8080/romannumeral?query=xx");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        ErrorResponseDTO errorResponseDTO = retrieveResourceFromResponse(response, ErrorResponseDTO.class);

        Assertions.assertEquals(400, errorResponseDTO.getStatusCode());
        Assertions.assertTrue(errorResponseDTO.getErrorMessage().contains(ErrorMessage.INPUT_TYPE_MISMATCH));
    }

    // helper method to parse json response body from Httpresponse
    public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz)
            throws IOException
    {

        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

}

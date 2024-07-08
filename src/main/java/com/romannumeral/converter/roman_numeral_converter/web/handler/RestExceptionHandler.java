package com.romannumeral.converter.roman_numeral_converter.web.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.romannumeral.converter.roman_numeral_converter.web.dto.ErrorResponseDTO;
import com.romannumeral.converter.roman_numeral_converter.web.error.ErrorMessage;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException)
    {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                                                                 ErrorMessage.INVALID_DATA);
        LOG.error(errorResponseDTO.toString(), constraintViolationException);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ErrorMessage.INPUT_TYPE_MISMATCH);
        LOG.error(errorResponseDTO.toString(), methodArgumentTypeMismatchException);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException runtimeException){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorMessage.INTERNAL_SERVER_ERROR);
        LOG.error(errorResponseDTO.toString(), runtimeException);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

}
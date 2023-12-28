/**
 * @author : Oswaldo Montes
 * @created : December 27, 2023
 **/
package com.koombea.web.app.ontoppractice.controllers;

import com.koombea.web.app.ontoppractice.shared.utils.ErrorResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    @ExceptionHandler({ServerWebInputException.class, MissingRequestHeaderException.class, MissingPathVariableException.class, MissingServletRequestPartException.class})
    public ResponseEntity badRequestException(ErrorResponse exception) {
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponseUtil.createSingletonErrorDocument(exception));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity notFoundException(ErrorResponse exception) {
        return ResponseEntity.status(NOT_FOUND).body(ErrorResponseUtil.createSingletonErrorDocument(exception));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity methodNotSupportedException(ErrorResponse exception) {
        return ResponseEntity.status(METHOD_NOT_ALLOWED).body(ErrorResponseUtil.createSingletonErrorDocument(exception));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity internalServerException(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponseUtil.createSingletonErrorDocument(exception, String.valueOf(INTERNAL_SERVER_ERROR.value())));
    }
}
